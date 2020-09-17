package script

import com.yehao.boot.rules.function.FunctionPredicate
import com.yehao.boot.rules.function.model.FilterModel
import com.yehao.boot.rules.function.model.Recall

public class GFunctionPredicate implements FunctionPredicate<Recall, FilterModel>{

    @Override
    boolean apply(Recall recall, FilterModel filterModel) {
        println "Hello, $recall! "
        return filterModel.getWhiteBrands().contains(recall.getId());
    }
}


def say(name){
    name = 'Hello' + name;
    print((String)name)
    return name;
}