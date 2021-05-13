package system.handling.language;

import system.database.DatabaseSystem;
import system.database.table.DefaultEntries;
import system.database.table.Tables;
import system.tools.skills.TimeManager;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class Language implements DefaultEntries{

    @Override
    public Tables getTable(){
        return Tables.IVY_LANGUAGE;
    }

    @Override
    public List<String[]> getValues(){
        List<String[]> forReturn = new ArrayList<>();

        for(DefaultLanguages defaultLanguages : DefaultLanguages.values()){
            String[] values = {
                    defaultLanguages.getLanguageId(), defaultLanguages.getLanguageName(), defaultLanguages.getLanguageCode(), new TimeManager(System.currentTimeMillis()).getDateFormat("YYYY-MM-dd HH:mm:ss"),
            };
            forReturn.add(values);
        }

        return forReturn;
    }

    public enum DefaultLanguages{

        GERMAN("EB4608197FC40F80D14DBEF721BC7A64>>U9GrWN3kab2xZQ", "german", "de-DE"),
        ENGLISH("DDF488EDB9914116A7CA1A2FC2182EEB>>VliLRzcjRbKzrb", "english", "en-EN");

        final private String languageId;
        final private String languageName;
        final private String languageCode;

        DefaultLanguages(final String languageId, final String languageName, final String languageCode){
            this.languageId = languageId;
            this.languageName = languageName;
            this.languageCode = languageCode;
        }

        public String getLanguageId(){
            return this.languageId;
        }

        public String getLanguageName(){
            return this.languageName;
        }

        public String getLanguageCode(){
            return this.languageCode;
        }

    }

    private final String languageId;

    public Language(final String languageId){
        this.languageId = languageId;
    }

    public Language(final String languageId, final LanguageProperties[] preloadProperties){
        this.languageId = languageId;

        if(preloadProperties.length == 0){
            return;
        }

        StringBuilder statementBuilder = new StringBuilder();
        statementBuilder.append("SELECT ");

        for(LanguageProperties singleProperty : preloadProperties){
            statementBuilder.append(singleProperty.getColumnName() + ", ");
        }

        Tables userTable = Tables.IVY_LANGUAGE;
        statementBuilder.setLength(statementBuilder.length() - 2);
        statementBuilder.append(" FROM " + userTable.getTableName() + " WHERE ivy_language__id='" + this.languageName + "'");
        String databaseStatement = new String(statementBuilder);

        try{
            ResultSet resultSet = DatabaseSystem.query(databaseStatement);

            if(resultSet.next()){
                for(LanguageProperties singleProperty : preloadProperties){
                    String columnName = singleProperty.getColumnName();
                    switch(singleProperty){
                        case CODE:
                            this.languageCode = resultSet.getString(columnName);
                            break;
                        case CREATEDTIMESTAMP:
                            this.languageCreatedTimestamp = new TimeManager(Long.valueOf(resultSet.getString(columnName)));
                            break;
                        case NAME:
                            this.languageName = resultSet.getString(columnName);
                            break;
                    }
                }
            }

        }catch(Exception exception){
            exception.printStackTrace();
        }

    }

    public String getLanguageId(){
        return this.languageId;
    }

    private String languageName;

    public String getLanguageName(){

        if(languageName != null){
            return languageName;
        }

        String columnName = LanguageProperties.NAME.getColumnName();
        String tableName = Tables.IVY_LANGUAGE.getTableName();

        try{

            ResultSet resultSet = DatabaseSystem.query("SELECT " + columnName + " FROM " + tableName + " WHERE ivy_language__id='" + this.languageId + "'");
            if(resultSet.next()){
                this.languageName = resultSet.getString(columnName);
            }

        }catch(Exception exception){
            return null;
        }finally{
            return this.languageName;
        }

    }

    private String languageCode;

    public String getLanguageCode(){

        if(languageCode != null){
            return languageCode;
        }

        String columnName = LanguageProperties.CODE.getColumnName();
        String tableName = Tables.IVY_LANGUAGE.getTableName();

        try{

            ResultSet resultSet = DatabaseSystem.query("SELECT " + columnName + " FROM " + tableName + " WHERE ivy_language__id='" + this.languageId + "'");
            if(resultSet.next()){
                this.languageCode = resultSet.getString(columnName);
            }

        }catch(Exception exception){
            return null;
        }finally{
            return this.languageCode;
        }

    }

    private TimeManager languageCreatedTimestamp;

    public TimeManager getLanguageCreatedTimestamp(){

        if(languageCreatedTimestamp != null){
            return languageCreatedTimestamp;
        }

        String columnName = LanguageProperties.CREATEDTIMESTAMP.getColumnName();
        String tableName = Tables.IVY_LANGUAGE.getTableName();

        try{

            ResultSet resultSet = DatabaseSystem.query("SELECT " + columnName + " FROM " + tableName + " WHERE ivy_language__id='" + this.languageId + "'");
            if(resultSet.next()){
                this.languageCreatedTimestamp = new TimeManager(Long.valueOf(resultSet.getString(columnName)));
            }

        }catch(Exception exception){
            return null;
        }finally{
            return this.languageCreatedTimestamp;
        }

    }

}
