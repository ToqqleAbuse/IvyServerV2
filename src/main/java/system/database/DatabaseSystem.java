package system.database;

import system.database.table.Tables;
import system.io.logs.LogSystem;
import system.io.logs.LogTypes;
import system.startup.StartupHandler;
import system.tools.skills.Console;
import system.tools.skills.ISP;
import system.tools.utils.StringUtils;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class DatabaseSystem implements StartupHandler{

    @Override
    public void __init__() throws Exception{
        DatabaseConnection.connect();
        Tables.createTable();
        Tables.createDefaultValues();
    }

    @Override
    public boolean async(){
        return false;
    }

    @Override
    public boolean systemRequirement(){
        return false;
    }

    @Override
    public String systemName(){
        return "Database-System";
    }


    public static String generateUniqueId(){
        long nanoTimestamp = System.nanoTime();
        return ISP.getMd5(String.valueOf(nanoTimestamp)) + ">>" + StringUtils.randomString(14);
    }

    public static void update(final String stm){
        LogSystem logSystem = new LogSystem(LogTypes.DATABASELOG);
        logSystem.log("update > " + stm);
        DatabaseConnection.executor.execute(() -> syncUpdate(stm));
    }

    public static void syncUpdate(String update){
        try{
            DatabaseConnection.con.prepareStatement(update).executeUpdate();
        }catch(Exception exception){
            exception.printStackTrace();
        }
    }

    public static ResultSet query(final String qry){

        LogSystem logSystem = new LogSystem(LogTypes.DATABASELOG);
        logSystem.log("query > " + qry);

        try{
            final PreparedStatement ps = DatabaseConnection.con.prepareStatement(qry);
            return ps.executeQuery();
        }catch(SQLException exception){
            exception.printStackTrace();
            return null;
        }
    }


    public static void dropTable(final Tables table){
        DatabaseSystem.update("DROP TABLE " + table.getTableName());
    }

    public static void insert(final Tables table, final boolean checkForDuplicates, final String... values){
        DatabaseSystem.insertArray(table, checkForDuplicates, values);
    }

    public static void insertArray(final Tables table, final boolean checkForDuplicates, final String[] values){
        final String[] columns = table.getTableColumns();
        StringBuilder columnNamesBuilder = new StringBuilder();
        for(int i = 0; i < columns.length; i++){
            String value = columns[i].split(" ")[0];
            columnNamesBuilder.append(value).append(", ");
        }

        String columnNames = new String(columnNamesBuilder);
        columnNames = StringUtils.cutOff(columnNames, 2);

        final String[] newValues = values;
        StringBuilder columnValuesBuilder = new StringBuilder();
        for(int i = 0; i < newValues.length; i++){
            columnValuesBuilder.append("'" + newValues[i] + "', ");
        }

        String columnValues = new String(columnValuesBuilder);
        columnValues = StringUtils.cutOff(columnValues, 2);

        if(!checkForDuplicates){
            Console.print("INSERT INTO " + table.getTableName() + " (" + columnNames + ") VALUES (" + columnValues + ")");

            DatabaseSystem.update("INSERT INTO " + table.getTableName() + " (" + columnNames + ") VALUES (" + columnValues + ")");
        }else{
            String primaryKeyColumn = table.getTableColumns()[0].split(" ")[0];
            DatabaseSystem.update("INSERT INTO " + table.getTableName() + " (" + columnNames + ") SELECT * FROM (SELECT " + columnValues + ") AS tmp WHERE NOT EXISTS (SELECT " + primaryKeyColumn + " FROM " + table.getTableName() + " WHERE " + primaryKeyColumn + "='" + values[0] + "')");
        }
    }

    public static void insert(final Tables table, final boolean checkForDuplicates, final List<String[]> listValues){

        final String[] columns = table.getTableColumns();
        StringBuilder columnNamesBuilder = new StringBuilder();
        for(int i = 0; i < columns.length; i++){
            String value = columns[i].split(" ")[0];
            columnNamesBuilder.append(value).append(", ");
        }

        String columnNames = StringUtils.cutOff(columnNamesBuilder.toString(), 2);
        StringBuilder columnValuesBuilder = new StringBuilder();

        listValues.forEach(singleRow -> {
            StringBuilder singleValuesBuilder = new StringBuilder();
            singleValuesBuilder.append("(");
            for(String singleValue : singleRow){
                singleValuesBuilder.append("'" + singleValue + "'").append(", ");
            }
            columnValuesBuilder.append(StringUtils.cutOff(singleValuesBuilder.toString(), 2)).append("), ");
        });

        String columnValues = StringUtils.cutOff(columnValuesBuilder.toString(), 2);
        String sqlStatement = "INTO " + table.getTableName() + " (" + columnNames + ") VALUES " + columnValues;

        if(!checkForDuplicates){
            DatabaseSystem.update("INSERT " + sqlStatement);
        }else{
            DatabaseSystem.update("REPLACE " + sqlStatement);
        }

    }


}
