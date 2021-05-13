package system.handling.human.user;

import system.database.table.Tables;

public enum UserProperties{

    FIRSTNAME(Tables.IVY_USER, "ivy_user__firstname"),
    SECONDNAME(Tables.IVY_USER, "ivy_user__secondname"),
    FAMILYNAME(Tables.IVY_USER, "ivy_user__familyname"),
    EMAILADDRESS(Tables.IVY_USER, "ivy_user__email_address"),
    CUSTOMERNUMBER(Tables.IVY_USER, "ivy_user__customer_number"),
    USERNAME(Tables.IVY_USER, "ivy_user__username"),
    PASSWORDHASH(Tables.IVY_USER, "ivy_user__password"),
    ACCOUNTTOKEN(Tables.IVY_USER, "ivy_user__account_token"),
    BIRTHDAYDATE(Tables.IVY_USER, "ivy_user__birthday"),
    LANGUAGE(Tables.IVY_USER, "ivy_user__language_id"),
    PROFILEPICTUREURL(Tables.IVY_USER, "ivy_user__profile_picture_url"),
    CREATEDTIMESTAMP(Tables.IVY_USER, "ivy_user__created_timestamp"),
    GROUP(Tables.IVY_USER, "ivy_user__group_id");

    private final String columnName;
    private final Tables table;

    UserProperties(final Tables table, final String columnName){
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
