package com.yehao.boot.elasticsearch.plugin;

import org.apache.lucene.index.LeafReaderContext;
import org.apache.lucene.index.PostingsEnum;
import org.apache.lucene.index.Term;
import org.elasticsearch.script.ScriptContext;
import org.elasticsearch.script.ScriptEngine;
import org.elasticsearch.script.SearchScript;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.Map;

/**
 * @author : LiuYong at 2020-08-01
 * @package: com.yehao.boot.elasticsearch.plugin
 */
public class ExpertScriptEngine implements ScriptEngine {

    /**
     * The language name used in the script APIs to refer to this scripting backend.
     */
    @Override
    public String getType() {
        return "expert_scripts";
    }

    /**
     * Compiles a script.
     *
     * @param name    the name of the script. {@code null} if it is anonymous (inline). For a stored script, its the identifier.
     * @param scriptSource    actual source of the script
     * @param context the context this script will be used for
     * @param params  compile-time parameters (such as flags to the compiler)
     * @return A compiled script of the FactoryType from {@link ScriptContext}
     */
    @Override
    public <FactoryType> FactoryType compile(String name, String scriptSource, ScriptContext<FactoryType> context, Map<String, String> params) {
        if (context.equals(SearchScript.CONTEXT) == false) {
            throw new IllegalArgumentException(getType() + " scripts cannot be used for context [" + context.name + "]");
        }
        // we use the script "source" as the script identifier
        if ("pure_df".equals(scriptSource)) {
            SearchScript.Factory factory = (p, lookup) -> new SearchScript.LeafFactory() {
                final String field;
                final String term;
                {
                    if (p.containsKey("field") == false) {
                        throw new IllegalArgumentException("Missing parameter [field]");
                    }
                    if (p.containsKey("term") == false) {
                        throw new IllegalArgumentException("Missing parameter [term]");
                    }
                    field = p.get("field").toString();
                    term = p.get("term").toString();
                }

                @Override
                public SearchScript newInstance(LeafReaderContext context) throws IOException {
                    PostingsEnum postings = context.reader().postings(new Term(field, term));
                    if (postings == null) {
                        // the field and/or term don't exist in this segment, so always return 0
                        return new SearchScript(p, lookup, context) {
                            @Override
                            public double runAsDouble() {
                                return 0.0d;
                            }
                        };
                    }
                    return new SearchScript(p, lookup, context) {
                        int currentDocid = -1;
                        @Override
                        public void setDocument(int docid) {
                            // advance has undefined behavior calling with a docid <= its current docid
                            if (postings.docID() < docid) {
                                try {
                                    postings.advance(docid);
                                } catch (IOException e) {
                                    throw new UncheckedIOException(e);
                                }
                            }
                            currentDocid = docid;
                        }
                        @Override
                        public double runAsDouble() {
                            if (postings.docID() != currentDocid) {
                                // advance moved past the current doc, so this doc has no occurrences of the term
                                return 0.0d;
                            }
                            try {
                                return postings.freq();
                            } catch (IOException e) {
                                throw new UncheckedIOException(e);
                            }
                        }
                    };
                }

                @Override
                public boolean needs_score() {
                    return false;
                }
            };
            return context.factoryClazz.cast(factory);
        }
        throw new IllegalArgumentException("Unknown script name " + scriptSource);
    }

    @Override
    public void close() {
        // optionally close resources
    }
}
