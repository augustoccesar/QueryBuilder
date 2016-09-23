package br.com.augustoccesar.querybuilder.executors.mapper;

import br.com.augustoccesar.querybuilder.query.Column;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by augustoccesar on 8/9/16.
 */
public class EntityReader {
    public static List<Column> getColumns(String prefix, Class clazz) {
        // TODO recover by annotation if it exists
        ArrayList<Column> list = new ArrayList<>();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            list.add(new Column(clazz, field.getType(), prefix, field.getName()));
        }
        return list;
    }
}
