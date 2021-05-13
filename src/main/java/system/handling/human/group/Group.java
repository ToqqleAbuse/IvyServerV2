package system.handling.human.group;

import system.database.DatabaseSystem;
import system.database.table.DefaultEntries;
import system.database.table.Tables;
import system.tools.skills.TimeManager;

import java.awt.*;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class Group implements DefaultEntries{

    @Override
    public Tables getTable(){
        return Tables.IVY_GROUP;
    }

    @Override
    public List<String[]> getValues(){
        List<String[]> forReturn = new ArrayList<>();

        for(DefaultGroups defaultGroups : DefaultGroups.values()){
            String[] values = {
                    defaultGroups.getGroupId(), defaultGroups.getGroupName(), String.valueOf(defaultGroups.getGroupShortcut()), Integer.toHexString(defaultGroups.getGroupColor().getRGB() & 0xffffff), (defaultGroups.getStaffGroup() ? "1" : "0"), new TimeManager(System.currentTimeMillis()).getDateFormat("YYYY-MM-dd HH:mm:ss"),
            };
            forReturn.add(values);
        }

        return forReturn;
    }

    public enum DefaultGroups{

        CEO("11786FB137498697DE2A01BDF1B6E9BF>>5EhwIimp8DwNd5", "CEO", 'C', true, Color.decode("#613ba8")),
        CAO("44C2802453CAFAC6DDAB713A7E937199>>I8LixhiWw7G2Df", "CAO", 'C', true, Color.decode("#911350")),
        CBO("F750D188097FF2756062389BCCF9019D>>sAqFCkAI0SlOzO", "CBO", 'C', true, Color.decode("#2a613b")),
        CUSTOMER("7F189E8634571A90096442A0849559AA>>XNph4WqLnoAFjd", "{{ivy_words:customer}}", 'K', false, Color.decode("#ffffff"));

        final private String groupId;
        final private String groupName;
        final private char groupShortcut;
        final private Color groupColor;
        final private boolean staffGroup;


        DefaultGroups(final String groupId, final String groupName, final char groupShortcut, final boolean staffGroup, final Color groupColor){
            this.groupId = groupId;
            this.groupName = groupName;
            this.groupShortcut = groupShortcut;
            this.staffGroup = staffGroup;
            this.groupColor = groupColor;
        }

        public String getGroupId(){
            return this.groupId;
        }

        public String getGroupName(){
            return this.groupName;
        }

        public boolean getStaffGroup(){
            return this.staffGroup;
        }

        public char getGroupShortcut(){
            return this.groupShortcut;
        }

        public Color getGroupColor(){
            return this.groupColor;
        }
    }

    final private String groupId;

    public Group(final String groupId){
        this.groupId = groupId;
    }

    public Group(final String groupId, final GroupProperties[] preloadProperties){
        this.groupId = groupId;

        if(preloadProperties.length == 0){
            return;
        }

        StringBuilder statementBuilder = new StringBuilder();
        statementBuilder.append("SELECT ");

        for(GroupProperties singleProperty : preloadProperties){
            statementBuilder.append(singleProperty.getColumnName() + ", ");
        }

        Tables groupTable = Tables.IVY_GROUP;
        statementBuilder.setLength(statementBuilder.length() - 2);
        statementBuilder.append(" FROM " + groupTable.getTableName() + " WHERE ivy_group__id='" + this.groupId + "'");
        String databaseStatement = new String(statementBuilder);

        try{
            ResultSet resultSet = DatabaseSystem.query(databaseStatement);

            if(resultSet.next()){
                for(GroupProperties singleProperty : preloadProperties){
                    String columnName = singleProperty.getColumnName();
                    switch(singleProperty){
                        case COLOR:
                            this.groupColor = Color.decode(resultSet.getString(columnName));
                            break;
                        case CREATEDTIMESTAMP:
                            this.groupCreatedTimestamp = new TimeManager(Long.valueOf(resultSet.getString(columnName)));
                            break;
                        case NAME:
                            this.groupName = resultSet.getString(columnName);
                            break;
                        case SHORTCUT:
                            this.groupShortcut = resultSet.getString(columnName).charAt(0);
                            break;
                    }
                }
            }

        }catch(Exception exception){
            exception.printStackTrace();
        }

    }

    public String getGroupId(){
        return this.groupId;
    }


    private String groupName;

    public String getGroupName(){

        if(groupName != null){
            return groupName;
        }

        String columnName = GroupProperties.NAME.getColumnName();
        String tableName = Tables.IVY_GROUP.getTableName();

        try{
            ResultSet resultSet = DatabaseSystem.query("SELECT " + columnName + " FROM " + tableName + " WHERE ivy_group__id='" + this.groupId + "'");
            if(resultSet.next()){
                this.groupName = resultSet.getString(columnName);
            }
        }catch(Exception exception){
            return null;
        }finally{
            return this.groupName;
        }
    }

    private Character groupShortcut;

    public Character getGroupShortcut(){
        if(groupShortcut != null){
            return groupShortcut;
        }

        String columnName = GroupProperties.SHORTCUT.getColumnName();
        String tableName = Tables.IVY_GROUP.getTableName();

        try{
            ResultSet resultSet = DatabaseSystem.query("SELECT " + columnName + " FROM " + tableName + " WHERE ivy_group__id='" + this.groupId + "'");
            if(resultSet.next()){

                this.groupShortcut = resultSet.getString(columnName).charAt(0);
            }
        }catch(Exception exception){
            return null;
        }finally{
            return this.groupShortcut;
        }
    }

    private Boolean staffGroup;

    public Boolean isStaffGroup(){

        if(staffGroup != null){
            return staffGroup;
        }

        String columnName = GroupProperties.STAFFGROUP.getColumnName();
        String tableName = Tables.IVY_GROUP.getTableName();

        try{
            ResultSet resultSet = DatabaseSystem.query("SELECT " + columnName + " FROM " + tableName + " WHERE ivy_group__id='" + this.groupId + "'");
            if(resultSet.next()){
                this.staffGroup = resultSet.getBoolean(columnName);
            }
        }catch(Exception exception){
            return null;
        }finally{
            return this.staffGroup;
        }

    }


    private Color groupColor;

    public Color getGroupColor(){

        if(groupColor != null){
            return groupColor;
        }

        String columnName = GroupProperties.COLOR.getColumnName();
        String tableName = Tables.IVY_GROUP.getTableName();

        try{
            ResultSet resultSet = DatabaseSystem.query("SELECT " + columnName + " FROM " + tableName + " WHERE ivy_group__id='" + this.groupId + "'");
            if(resultSet.next()){
                this.groupColor = Color.decode(resultSet.getString(columnName));
            }
        }catch(Exception exception){
            return null;
        }finally{
            return this.groupColor;
        }
    }

    private TimeManager groupCreatedTimestamp;

    public TimeManager getGroupCreatedTimestamp(){

        if(groupCreatedTimestamp != null){
            return groupCreatedTimestamp;
        }

        String columnName = GroupProperties.CREATEDTIMESTAMP.getColumnName();
        String tableName = Tables.IVY_GROUP.getTableName();

        try{
            ResultSet resultSet = DatabaseSystem.query("SELECT " + columnName + " FROM " + tableName + " WHERE ivy_group__id='" + this.groupId + "'");
            if(resultSet.next()){
                this.groupCreatedTimestamp = new TimeManager(Long.valueOf(resultSet.getString(columnName)));
            }
        }catch(Exception exception){
            return null;
        }finally{
            return this.groupCreatedTimestamp;
        }
    }

}
