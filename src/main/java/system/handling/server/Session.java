package system.handling.server;

import system.database.DatabaseSystem;
import system.database.table.Tables;
import system.tools.skills.TimeManager;

import java.sql.ResultSet;
import java.util.HashMap;

public class Session{

    public static HashMap<String, Session> sessions = new HashMap<>();

    private final String sessionId;

    public Session(final String sessionId){
        this.sessionId = sessionId;

        if(!Session.sessions.containsKey(this.sessionId)){
            Session.sessions.put(sessionId, this);
        }

    }

    public Session(final String sessionId, SessionProperties[] preloadProperties){
        this.sessionId = sessionId;

        if(preloadProperties.length == 0){
            return;
        }

        StringBuilder statementBuilder = new StringBuilder();
        statementBuilder.append("SELECT ");

        for(SessionProperties singleProperty : preloadProperties){
            statementBuilder.append(singleProperty.getColumnName()).append(", ");
        }

        Tables sessionTable = Tables.IVY_USER_SESSION;
        statementBuilder.setLength(statementBuilder.length() - 2);
        statementBuilder.append(" FROM ").append(sessionTable.getTableName()).append(" WHERE ivy_user_session__id='").append(this.sessionId).append("'");
        String databaseStatement = new String(statementBuilder);

        try{

            ResultSet resultSet = DatabaseSystem.query(databaseStatement);

            if(resultSet.next()){
                for(SessionProperties singleProperty : preloadProperties){
                    String columnName = singleProperty.getColumnName();
                    switch(singleProperty){
                        case SESSIONTOKEN:
                            this.sessionToken = resultSet.getString(columnName);
                            break;
                        case SESSIONUSERID:
                            this.sessionUserId = resultSet.getString(columnName);
                            break;
                    }
                }
            }

        }catch(Exception exception){
            exception.printStackTrace();
        }

        if(!Session.sessions.containsKey(this.sessionId)){
            Session.sessions.put(sessionId, this);
        }

    }

    public String getSessionId(){
        return this.sessionId;
    }

    public String sessionToken;

    public String getSessionToken(){

        if(sessionToken != null){
            return sessionToken;
        }

        String columnName = SessionProperties.SESSIONTOKEN.getColumnName();
        String tableName = Tables.IVY_USER_SESSION.getTableName();

        try{
            ResultSet resultSet = DatabaseSystem.query("SELECT " + columnName + " FROM " + tableName + " WHERE ivy_user_session__id ='" + this.sessionId + "'");
            if(resultSet.next()){
                this.sessionToken = resultSet.getString(columnName);
            }
        }catch(Exception exception){
            return null;
        }finally{
            return this.sessionToken;
        }

    }


    public String sessionUserId;

    public String getSessionUserId(){

        if(sessionUserId != null){
            return sessionUserId;
        }

        String columnName = SessionProperties.SESSIONUSERID.getColumnName();
        String tableName = Tables.IVY_USER_SESSION.getTableName();

        try{
            ResultSet resultSet = DatabaseSystem.query("SELECT " + columnName + " FROM " + tableName + " WHERE ivy_user_session__id ='" + this.sessionId + "'");
            if(resultSet.next()){
                this.sessionUserId = resultSet.getString(columnName);
            }
        }catch(Exception exception){
            return null;
        }finally{
            return this.sessionUserId;
        }

    }


    public TimeManager sessionStarted;

    public TimeManager getSessionStarted(){

        if(sessionStarted != null){
            return sessionStarted;
        }



        return null;
    }


    public TimeManager sessionExpired;

    public TimeManager getSessionExpired(){

        if(sessionExpired != null){
            return sessionExpired;
        }

        return null;

    }


}
