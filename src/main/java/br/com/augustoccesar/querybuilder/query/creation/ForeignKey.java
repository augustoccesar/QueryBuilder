package br.com.augustoccesar.querybuilder.query.creation;

/**
 * Created by augustoccesar on 6/20/16.
 */
public class ForeignKey {
    private String columnName;
    private String referencesTable;
    private String referencesColumn;

    public ForeignKey column(String columnName) {
        this.columnName = columnName;
        return this;
    }

    public ForeignKey references(String referencesTable, String referencesColumn) {
        this.referencesTable = referencesTable;
        this.referencesColumn = referencesColumn;
        return this;
    }

    public String getColumnName() {
        return columnName;
    }

    public String getReferencesTable() {
        return referencesTable;
    }

    public String getReferencesColumn() {
        return referencesColumn;
    }
}
