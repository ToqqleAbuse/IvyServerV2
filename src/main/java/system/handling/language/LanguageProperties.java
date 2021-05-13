package system.handling.language;

import system.database.table.Tables;

public enum LanguageProperties{

    NAME(Tables.IVY_LANGUAGE, "ivy_language__name"),
    CODE(Tables.IVY_LANGUAGE, "ivy_language__code"),
    CREATEDTIMESTAMP(Tables.IVY_LANGUAGE, "ivy_language__created_timestamp");



    private final String columnName;
    private final Tables table;

    LanguageProperties(final Tables table, final String columnName){
        this.columnName = columnName;
        this.table = table;
    }

    public String getColumnName(){
        return this.columnName;
    }

    public Tables getTable(){
        return this.table;
    }

}
