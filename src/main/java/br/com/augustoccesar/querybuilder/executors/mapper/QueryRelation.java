package br.com.augustoccesar.querybuilder.executors.mapper;

import java.util.HashMap;

/**
 * Created by augustoccesar on 8/9/16.
 */
public class QueryRelation {
    private ClassTable parentClass;
    private HashMap<String, ClassTable> includes;

    public QueryRelation(ClassTable parentClass) {
        this.parentClass = parentClass;
    }

    public QueryRelation include(String relationName, ClassTable clazz) {
        if (includes == null)
            includes = new HashMap<>();
        includes.put(relationName, clazz);
        return this;
    }

    public ClassTable getParentClass() {
        return parentClass;
    }

    public HashMap<String, ClassTable> getIncludes() {
        return includes;
    }
}
