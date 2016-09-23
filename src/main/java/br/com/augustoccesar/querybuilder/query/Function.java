package br.com.augustoccesar.querybuilder.query;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by augustoccesar on 9/23/16.
 */
public class Function {
    private String name;
    private String alias;
    private Parameters parameters;

    public Function(String name) {
        this.name = name;
    }

    // Constructors

    public Function(String name, String alias) {
        this.name = name;
        this.alias = alias;
    }

    public Function(String name, Parameters parameters) {
        this.name = name;
        this.parameters = parameters;
    }

    public Function(String name, String alias, Parameters parameters) {
        this.name = name;
        this.alias = alias;
        this.parameters = parameters;
    }

    public static Function build(String name) {
        return new Function(name);
    }

    // Builders

    public static Function build(String name, String alias) {
        return new Function(name, alias);
    }

    public static Function build(String name, Parameters parameters) {
        return new Function(name, parameters);
    }

    public static Function build(String name, String alias, Parameters parameters) {
        return new Function(name, alias, parameters);
    }

    public String getName() {
        return name;
    }

    // Getters and Setters

    public void setName(String name) {
        this.name = name;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public Parameters getParameters() {
        return parameters;
    }

    public void setParameters(Parameters parameters) {
        this.parameters = parameters;
    }

    public static class Parameters {
        private ArrayList<String> parametersList;

        public Parameters(String... parameters) {
            if (this.parametersList == null) {
                this.parametersList = new ArrayList<>();
            }

            this.parametersList.addAll(Arrays.asList(parameters));
        }

        public static Parameters build(String... parameters) {
            return new Parameters(parameters);
        }

        public ArrayList<String> getParametersList() {
            return parametersList;
        }
    }
}
