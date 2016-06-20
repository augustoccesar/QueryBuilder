package br.com.augustoccesar.querybuilder.configurations;

/**
 * Created by augustoccesar on 6/13/16.
 */
public class Configuration {
    private static Configuration ourInstance = new Configuration();
    private Database database;

    public static Database getDatabase() {
        return ourInstance.database;
    }

    public static void setDatabase(Database database) {
        ourInstance.database = database;
    }
}
