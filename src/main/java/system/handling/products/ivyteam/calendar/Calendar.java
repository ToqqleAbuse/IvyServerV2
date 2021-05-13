package system.handling.products.ivyteam.calendar;

import system.database.DatabaseSystem;
import system.database.table.Tables;

import java.sql.ResultSet;

public class Calendar{


    private final String calendarId;

    public Calendar(final String calendarId){
        this.calendarId = calendarId;
    }


    public Calendar(final String calendarId, final CalendarProperties[] preloadProperties){
        this.calendarId = calendarId;

        if(preloadProperties.length == 0){
            return;
        }

        StringBuilder statementBuilder = new StringBuilder();
        statementBuilder.append("SELECT ");

        for(CalendarProperties singleProperty : preloadProperties){
            statementBuilder.append(singleProperty.getColumnName() + ", ");
        }

        Tables calendarTable = Tables.IVY_CALENDAR;
        statementBuilder.setLength(statementBuilder.length() - 2);
        statementBuilder.append(" FROM " + calendarTable.getTableName() + " WHERE ivy_calendar__id='" + this.calendarId + "'");
        String databaseStatement = new String(statementBuilder);

        try{

            ResultSet resultSet = DatabaseSystem.query(databaseStatement);

            if(resultSet.next()){
                for(CalendarProperties singleProperty : preloadProperties){

                }
            }


        }catch(Exception exception){
            exception.printStackTrace();
        }

    }

    public String getCalendarId(){
        return this.calendarId;
    }


}
