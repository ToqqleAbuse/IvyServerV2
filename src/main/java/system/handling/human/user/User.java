package system.handling.human.user;

import system.database.DatabaseSystem;
import system.database.table.Tables;
import system.handling.human.group.GroupProperties;
import system.handling.human.group.Group;
import system.handling.human.permissions.Permission;
import system.handling.language.Language;
import system.handling.language.LanguageProperties;
import system.tools.skills.ISP;
import system.tools.skills.TimeManager;

import java.net.URL;
import java.sql.ResultSet;
import java.util.*;

public class User{

    private final String userId;

    public User(final String userId){
        this.userId = userId;
    }

    private UserProperties[] preloadProperties;

    private List<UserProperties> preloadPropertiesList(){
        List<UserProperties> forReturn = new ArrayList<>();

        if(preloadProperties == null){
            return forReturn;
        }

        Arrays.stream(preloadProperties).forEach(element -> forReturn.add(element));
        return forReturn;

    }

    public User(final String userId, final UserProperties[] preloadProperties){
        this.userId = userId;
        this.preloadProperties = preloadProperties;

        if(preloadProperties.length == 0){
            return;
        }

        StringBuilder statementBuilder = new StringBuilder();
        statementBuilder.append("SELECT ");

        for(UserProperties singleProperty : preloadProperties){
            statementBuilder.append(singleProperty.getColumnName() + ", ");
        }

        Tables userTable = Tables.IVY_USER;
        statementBuilder.setLength(statementBuilder.length() - 2);
        statementBuilder.append(" FROM " + userTable.getTableName() + " WHERE ivy_user__id='" + this.userId + "'");
        String databaseStatement = new String(statementBuilder);

        try{
            ResultSet resultSet = DatabaseSystem.query(databaseStatement);

            if(resultSet.next()){
                for(UserProperties singleProperty : preloadProperties){
                    String columnName = singleProperty.getColumnName();
                    switch(singleProperty){
                        case FIRSTNAME:
                            this.firstname = resultSet.getString(columnName);
                            break;
                        case SECONDNAME:
                            this.secondname = resultSet.getString(columnName);
                            break;
                        case FAMILYNAME:
                            this.familyname = resultSet.getString(columnName);
                            break;
                        case BIRTHDAYDATE:
                            this.birthdayDate = new TimeManager(Long.parseLong(resultSet.getString(columnName)));
                            break;
                        case ACCOUNTTOKEN:
                            this.accountToken = resultSet.getString(columnName);
                            break;
                        case CREATEDTIMESTAMP:
                            this.createdTimeStamp = new TimeManager(Long.parseLong(resultSet.getString(columnName)));
                            break;
                        case CUSTOMERNUMBER:
                            this.customerNumber = resultSet.getInt(columnName);
                            break;
                        case EMAILADDRESS:
                            this.emailaddress = resultSet.getString(columnName);
                            break;
                        case GROUP:
                            this.group = new Group(resultSet.getString(columnName));
                            break;
                        case LANGUAGE:
                            this.language = new Language(resultSet.getString(columnName));
                            break;
                        case PASSWORDHASH:
                            this.passwordHash = resultSet.getString(columnName);
                            break;
                        case PROFILEPICTUREURL:
                            this.profilePictureUrl = new URL(resultSet.getString(columnName));
                            break;
                        case USERNAME:
                            this.username = resultSet.getString(columnName);
                            break;
                    }
                }
            }

        }catch(Exception exception){
            exception.printStackTrace();
        }
    }

    public String getUserId(){
        return this.userId;
    }

    private String firstname;

    public String getFirstname(){

        if(firstname != null){
            return firstname;
        }

        String columnName = UserProperties.FIRSTNAME.getColumnName();
        String tableName = Tables.IVY_USER.getTableName();

        try{
            ResultSet resultSet = DatabaseSystem.query("SELECT " + columnName + " FROM " + tableName + " WHERE ivy_user__id='" + this.userId + "'");
            if(resultSet.next()){
                this.firstname = resultSet.getString(columnName);
            }
        }catch(Exception exception){
            return null;
        }finally{
            return this.firstname;
        }
    }


    private String secondname;

    public String getSecondname(){

        if(secondname != null){
            return secondname;
        }

        String columnName = UserProperties.SECONDNAME.getColumnName();
        String tableName = Tables.IVY_USER.getTableName();

        try{
            ResultSet resultSet = DatabaseSystem.query("SELECT " + columnName + " FROM " + tableName + " WHERE ivy_user__id='" + this.userId + "'");
            if(resultSet.next()){
                this.secondname = resultSet.getString(columnName);
            }
        }catch(Exception exception){
            return null;
        }finally{
            return this.secondname;
        }

    }


    private String familyname;

    public String getFamilyname(){

        if(familyname != null){
            return familyname;
        }

        String columnName = UserProperties.FAMILYNAME.getColumnName();
        String tableName = Tables.IVY_USER.getTableName();

        try{
            ResultSet resultSet = DatabaseSystem.query("SELECT " + columnName + " FROM " + tableName + " WHERE ivy_user__id='" + this.userId + "'");
            if(resultSet.next()){
                this.familyname = resultSet.getString(columnName);
            }
        }catch(Exception exception){
            return null;
        }finally{
            return this.familyname;
        }

    }


    private int customerNumber;

    public int getCustomerNumber(){

        if(customerNumber != 0){
            return customerNumber;
        }

        String columnName = UserProperties.CUSTOMERNUMBER.getColumnName();
        String tableName = Tables.IVY_USER.getTableName();

        try{
            ResultSet resultSet = DatabaseSystem.query("SELECT " + columnName + " FROM " + tableName + " WHERE ivy_user__id='" + this.userId + "'");
            if(resultSet.next()){
                this.customerNumber = resultSet.getInt(columnName);
            }
        }catch(Exception exception){
            return -1;
        }finally{
            return this.customerNumber;
        }

    }

    private String username;

    public String getUsername(){

        if(username != null){
            return username;
        }

        String columnName = UserProperties.USERNAME.getColumnName();
        String tableName = Tables.IVY_USER.getTableName();

        try{
            ResultSet resultSet = DatabaseSystem.query("SELECT " + columnName + " FROM " + tableName + " WHERE ivy_user__id='" + this.userId + "'");
            if(resultSet.next()){
                this.username = resultSet.getString(columnName);
            }
        }catch(Exception exception){
            return null;
        }finally{
            return this.username;
        }

    }

    private String passwordHash;

    public String getPasswordHash(){

        if(passwordHash != null){
            return passwordHash;
        }

        String columnName = UserProperties.PASSWORDHASH.getColumnName();
        String tableName = Tables.IVY_USER.getTableName();

        try{
            ResultSet resultSet = DatabaseSystem.query("SELECT " + columnName + " FROM " + tableName + " WHERE ivy_user__id='" + this.userId + "'");
            if(resultSet.next()){
                this.passwordHash = resultSet.getString(columnName);
            }
        }catch(Exception exception){
            return null;
        }finally{
            return this.passwordHash;
        }

    }

    private String accountToken;

    public String getAccountToken(){

        if(accountToken != null){
            return accountToken;
        }

        String columnName = UserProperties.ACCOUNTTOKEN.getColumnName();
        String tableName = Tables.IVY_USER.getTableName();

        try{
            ResultSet resultSet = DatabaseSystem.query("SELECT " + columnName + " FROM " + tableName + " WHERE ivy_user__id='" + this.userId + "'");
            if(resultSet.next()){
                this.accountToken = resultSet.getString(columnName);
            }
        }catch(Exception exception){
            return null;
        }finally{
            return this.accountToken;
        }

    }

    private TimeManager birthdayDate;

    public TimeManager getBirthdayDate(){

        if(birthdayDate != null){
            return birthdayDate;
        }

        String columnName = UserProperties.BIRTHDAYDATE.getColumnName();
        String tableName = Tables.IVY_USER.getTableName();

        try{
            ResultSet resultSet = DatabaseSystem.query("SELECT " + columnName + " FROM " + tableName + " WHERE ivy_user__id='" + this.userId + "'");
            if(resultSet.next()){
                this.birthdayDate = new TimeManager(Long.valueOf(resultSet.getString(columnName)));
            }
        }catch(Exception exception){
            return null;
        }finally{
            return this.birthdayDate;
        }

    }


    private Language language;

    public Language getLanguage(){

        if(language != null){
            return language;
        }

        String columnName = UserProperties.LANGUAGE.getColumnName();
        String tableName = Tables.IVY_USER.getTableName();

        try{
            ResultSet resultSet = DatabaseSystem.query("SELECT " + columnName + " FROM " + tableName + " WHERE ivy_user__id='" + this.userId + "'");
            if(resultSet.next()){
                this.language = new Language(resultSet.getString(columnName));
            }
        }catch(Exception exception){
            return null;
        }finally{
            return this.language;
        }

    }

    public Language getLanguage(LanguageProperties[] properties){

        if(language != null){
            return language;
        }

        String columnName = UserProperties.LANGUAGE.getColumnName();
        String tableName = Tables.IVY_USER.getTableName();

        try{
            ResultSet resultSet = DatabaseSystem.query("SELECT " + columnName + " FROM " + tableName + " WHERE ivy_user__id='" + this.userId + "'");
            if(resultSet.next()){
                this.language = new Language(resultSet.getString(columnName), properties);
            }
        }catch(Exception exception){
            return null;
        }finally{
            return this.language;
        }

    }


    private URL profilePictureUrl;

    public URL getProfilePictureUrl(){

        if(profilePictureUrl != null){
            return profilePictureUrl;
        }

        String columnName = UserProperties.PROFILEPICTUREURL.getColumnName();
        String tableName = Tables.IVY_USER.getTableName();

        try{
            ResultSet resultSet = DatabaseSystem.query("SELECT " + columnName + " FROM " + tableName + " WHERE ivy_user__id='" + this.userId + "'");
            if(resultSet.next()){
                this.profilePictureUrl = new URL(resultSet.getString(columnName));
            }
        }catch(Exception exception){
            return null;
        }finally{
            return this.profilePictureUrl;
        }

    }


    private TimeManager createdTimeStamp;

    public TimeManager getCreatedTimeStamp(){

        if(createdTimeStamp != null){
            return createdTimeStamp;
        }

        String columnName = UserProperties.CREATEDTIMESTAMP.getColumnName();
        String tableName = Tables.IVY_USER.getTableName();

        try{
            ResultSet resultSet = DatabaseSystem.query("SELECT " + columnName + " FROM " + tableName + " WHERE ivy_user__id='" + this.userId + "'");
            if(resultSet.next()){
                this.createdTimeStamp = new TimeManager(Long.valueOf(resultSet.getString(columnName)));
            }
        }catch(Exception exception){
            return null;
        }finally{
            return this.birthdayDate;
        }

    }


    private String emailaddress;

    public String getEmailAddress(){

        if(emailaddress != null){
            return emailaddress;
        }

        String columnName = UserProperties.EMAILADDRESS.getColumnName();
        String tableName = Tables.IVY_USER.getTableName();

        try{

            ResultSet resultSet = DatabaseSystem.query("SELECT " + columnName + " FROM " + tableName + " WHERE ivy_user__id='" + this.userId + "'");
            if(resultSet.next()){
                this.emailaddress = resultSet.getString(columnName);
            }

        }catch(Exception exception){
            return null;
        }finally{
            return this.emailaddress;
        }

    }

    private Group group;

    public Group getGroup(){
        if(group != null){
            return group;
        }

        String columnName = UserProperties.GROUP.getColumnName();
        String tableName = Tables.IVY_USER.getTableName();

        try{
            ResultSet resultSet = DatabaseSystem.query("SELECT " + columnName + " FROM " + tableName + " WHERE ivy_user__id='" + this.userId + "'");
            if(resultSet.next()){
                this.group = new Group(resultSet.getString(columnName));
            }
        }catch(Exception exception){
            return null;
        }finally{
            return this.group;
        }
    }

    public Group getGroup(GroupProperties[] preloadProperties){
        if(group != null){
            return group;
        }

        String columnName = UserProperties.GROUP.getColumnName();
        String tableName = Tables.IVY_USER.getTableName();

        try{
            ResultSet resultSet = DatabaseSystem.query("SELECT " + columnName + " FROM " + tableName + " WHERE ivy_user__id='" + this.userId + "'");
            if(resultSet.next()){
                this.group = new Group(resultSet.getString(columnName), preloadProperties);
            }
        }catch(Exception exception){
            return null;
        }finally{
            return this.group;
        }
    }

    public boolean isStaff(){
        return getGroup().getGroupId() != Group.DefaultGroups.CUSTOMER.getGroupId();
    }

    public Map<Permission, Boolean> permissions;

    public Map<Permission, Boolean> getPermissions(){

        if(permissions != null){
            return this.permissions;
        }

        try{

            HashMap<Permission, Boolean> forReturn = new HashMap<>();
            HashMap<Permission, Boolean> defaultPermissions = new HashMap<>();

            for(Permission permission : Permission.values()){
                defaultPermissions.put(permission, permission.getPermissionMainValue());
            }

            defaultPermissions.forEach((key, value) -> forReturn.put(key, value));

            ResultSet resultSet = DatabaseSystem.query("SELECT ivy_user_permission__permission_id FROM " + Tables.IVY_USER_PERMISSION.getTableName() + " WHERE ivy_user_permission__user_id='" + this.userId + "'");
            while(resultSet.next()){
                forReturn.put(Permission.getPermissionsById(resultSet.getString("ivy_user_permission__permission_id")), true);
            }

            this.permissions = forReturn;

        }catch(Exception exception){
            return null;
        }

        return this.permissions;
    }

    public String getCommunicationKey(){

        List<UserProperties> properties = this.preloadPropertiesList();
        int preloadedValues = 0;

        UserProperties[] userProperties = {
                UserProperties.USERNAME, UserProperties.PASSWORDHASH, UserProperties.EMAILADDRESS,
        };

        for(UserProperties property : userProperties){
            if(properties.contains(property)){
                preloadedValues++;
            }
        }

        User user;

        if(preloadedValues > 1){
            user = this;
        }else{
            user = new User(this.getUserId(), userProperties);
        }

        StringBuilder forReturn = new StringBuilder();

        forReturn.append(ISP.getMd5(user.getUsername()));
        forReturn.append(ISP.getMd5(user.getPasswordHash()));
        forReturn.append(ISP.getMd5(user.getEmailAddress()));
        forReturn.append(ISP.getMd5(String.valueOf(user.getUserId())));

        return ISP.getMd5(forReturn.toString());
    }

}
