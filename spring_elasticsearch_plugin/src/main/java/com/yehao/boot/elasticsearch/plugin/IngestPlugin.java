package com.yehao.boot.elasticsearch.plugin;

import org.elasticsearch.action.ActionRequest;
import org.elasticsearch.action.ActionResponse;
import org.elasticsearch.action.GenericAction;
import org.elasticsearch.action.support.ActionFilter;
import org.elasticsearch.cluster.metadata.IndexNameExpressionResolver;
import org.elasticsearch.cluster.node.DiscoveryNodes;
import org.elasticsearch.common.settings.ClusterSettings;
import org.elasticsearch.common.settings.IndexScopedSettings;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.settings.SettingsFilter;
import org.elasticsearch.common.util.concurrent.ThreadContext;
import org.elasticsearch.plugins.ActionPlugin;
import org.elasticsearch.plugins.Plugin;
import org.elasticsearch.plugins.ScriptPlugin;
import org.elasticsearch.rest.RestController;
import org.elasticsearch.rest.RestHandler;
import org.elasticsearch.script.ScriptContext;
import org.elasticsearch.script.ScriptEngine;

import java.util.Collection;
import java.util.List;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

/**
 * @author : LiuYong at 2019-09-05
 * @package: com.yehao.boot.elasticsearch.plugin
 */
public class IngestPlugin extends Plugin implements ScriptPlugin, ActionPlugin {

    /**
     * Actions added by this plugin.
     */
    @Override
    public List<ActionHandler<? extends ActionRequest, ? extends ActionResponse>> getActions() {
        return null;
    }

    /**
     * Client actions added by this plugin. This defaults to all of the {@linkplain GenericAction} in
     * {@linkplain ActionPlugin#getActions()}.
     */
    @Override
    public List<GenericAction> getClientActions() {
        return null;
    }

    /**
     * Action filters added by this plugin.
     */
    @Override
    public List<ActionFilter> getActionFilters() {
        return null;
    }

    /**
     * Rest handlers added by this plugin.
     *
     * @param settings
     * @param restController
     * @param clusterSettings
     * @param indexScopedSettings
     * @param settingsFilter
     * @param indexNameExpressionResolver
     * @param nodesInCluster
     */
    @Override
    public List<RestHandler> getRestHandlers(Settings settings, RestController restController, ClusterSettings clusterSettings, IndexScopedSettings indexScopedSettings, SettingsFilter settingsFilter, IndexNameExpressionResolver indexNameExpressionResolver, Supplier<DiscoveryNodes> nodesInCluster) {
        return null;
    }

    /**
     * Returns headers which should be copied through rest requests on to internal requests.
     */
    @Override
    public Collection<String> getRestHeaders() {
        return null;
    }

    /**
     * Returns headers which should be copied from internal requests into tasks.
     */
    @Override
    public Collection<String> getTaskHeaders() {
        return null;
    }

    /**
     * Returns a function used to wrap each rest request before handling the request.
     * <p>
     * Note: Only one installed plugin may implement a rest wrapper.
     *
     * @param threadContext
     */
    @Override
    public UnaryOperator<RestHandler> getRestHandlerWrapper(ThreadContext threadContext) {
        return null;
    }

    /**
     * Returns a {@link ScriptEngine} instance or <code>null</code> if this plugin doesn't add a new script engine.
     *
     * @param settings Node settings
     * @param contexts The contexts that {@link ScriptEngine#compile(String, String, ScriptContext, Map)} may be called with
     */
    @Override
    public ScriptEngine getScriptEngine(Settings settings, Collection<ScriptContext<?>> contexts) {
        return new ExpertScriptEngine();
    }

    /**
     * Return script contexts this plugin wants to allow using.
     */
    @Override
    public List<ScriptContext> getContexts() {
        return null;
    }
}
