package system.handling.products.ivyteam.calendar;

import system.database.table.Tables;

public enum CalendarProperties{

    NAME(Tables.IVY_CALENDAR, "ivy_calendar__name"),
    OWNERID(Tables.IVY_CALENDAR, "ivy_calendar__owner_id"),
    CREATEDDATETIME(Tables.IVY_CALENDAR, "ivy_calendar__created_datetime");

    private final String columnName;
    private final Tables table;

    CalendarProperties(final Tables table, final String columnName){
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
