package system.handling.server;

import system.database.table.Tables;

public enum SessionProperties{


    SESSIONTOKEN(Tables.IVY_USER_SESSION, "ivy_user_session__token"),
    SESSIONUSERID(Tables.IVY_USER_SESSION, "ivy_user_session__user_id");

    private final String columnName;
    private final Tables table;

    SessionProperties(final Tables table, final String columnName){
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

