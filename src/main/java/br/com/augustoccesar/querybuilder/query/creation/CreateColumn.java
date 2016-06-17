package br.com.augustoccesar.querybuilder.query.creation;

/**
 * Created by augustoccesar on 6/13/16.
 */
public class CreateColumn {
    private String name;
    private ColumnTypes type;
    private Integer size;
    private boolean primaryKey = false;
    private boolean unique = false;
    private boolean nullable = true;

    // Constructors

    public CreateColumn(String name, ColumnTypes type) {
        this.name = name;
        this.type = type;
    }

    public CreateColumn(String name, ColumnTypes type, Integer size) {
        this.name = name;
        this.type = type;
        this.size = size;
    }

    // Getters and Setters

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ColumnTypes getType() {
        return type;
    }

    public void setType(ColumnTypes type) {
        this.type = type;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public boolean isPrimaryKey() {
        return primaryKey;
    }

    public CreateColumn primaryKey(boolean primaryKey) {
        this.primaryKey = primaryKey;
        return this;
    }

    public boolean isUnique() {
        return unique;
    }

    public CreateColumn unique(boolean unique) {
        this.unique = unique;
        return this;
    }

    public boolean isNullable() {
        return nullable;
    }

    public CreateColumn nullable(boolean nullable) {
        this.nullable = nullable;
        return this;
    }
}
