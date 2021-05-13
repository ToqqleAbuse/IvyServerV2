package system.handling.human.group;

import system.database.table.Tables;

public enum GroupProperties{

    NAME(Tables.IVY_GROUP, "ivy_group__name"),
    COLOR(Tables.IVY_GROUP, "ivy_group__shortcut"),
    SHORTCUT(Tables.IVY_GROUP, "ivy_group__shortcut"),
    STAFFGROUP(Tables.IVY_GROUP, "ivy_group__staff_group"),
    CREATEDTIMESTAMP(Tables.IVY_GROUP, "ivy_group__created_timestamp");

    private final String columnName;
    private final Tables table;

    GroupProperties(final Tables table, final String columnName){
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

