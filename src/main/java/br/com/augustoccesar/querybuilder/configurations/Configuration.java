package br.com.augustoccesar.querybuilder.configurations;

/**
 * Created by augustoccesar on 6/13/16.
 */
public class Configuration {
    private static Configuration ourInstance = new Configuration();
    private Databases database;

    public static Databases getDatabase() {
        return ourInstance.database;
    }

    public static void setDatabase(Databases database) {
        ourInstance.database = database;
    }
}
