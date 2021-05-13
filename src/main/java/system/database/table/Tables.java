package system.database.table;

import system.database.DatabaseSystem;
import system.handling.human.group.Group;
import system.handling.human.permissions.Permission;
import system.handling.language.Language;
import system.handling.language.LanguageProperties;
import system.handling.language.placeholder.LanguagePlaceholder;
import system.handling.products.ProductsSystem;
import system.io.config.ConfigEntries;
import system.tools.skills.Console;
import system.tools.utils.StringUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public enum Tables{

    IVY_LANGUAGE("ivydb_ivy_language", "ivy_language__id VARCHAR(48) PRIMARY KEY", "ivy_language__name VARCHAR(128)", "ivy_language__code VARCHAR(5)", "ivy_language__created_datetime DATETIME"),
    IVY_LANGUAGE_PLACEHOLDER("ivydb_ivy_language_placeholder", "ivy_language_placeholder__id VARCHAR(48) PRIMARY KEY", "ivy_language_placeholder__name VARCHAR(512)", "ivy_language_placeholder__created_datetime DATETIME"),
    IVY_LANGUAGE_PLACEHOLDER_CONTENT("ivydb_ivy_language_placeholder_content", "ivy_language_placeholder_content__id VARCHAR(48) PRIMARY KEY", "ivy_language_placeholder_content__placeholder_id VARCHAR(48)", "ivy_language_placeholder_content__language_id VARCHAR(48)", "ivy_language_placeholder_content__value TEXT", "ivy_language_placeholder_content__created_datetime DATETIME"),

    IVY_PERMISSION("ivydb_ivy_permission", "ivy_permission__id VARCHAR(48) PRIMARY KEY", "ivy_permission__parent_id VARCHAR(48)", "ivy_permission__name VARCHAR(512)", "ivy_permission__default_value BOOLEAN", "ivy_permission__description TEXT"),

    IVY_USER("ivydb_ivy_user", "ivy_user__id VARCHAR(48) PRIMARY KEY", "ivy_user__customer_number INT(8)", "ivy_user__firstname VARCHAR(16)", "ivy_user__secondname VARCHAR(16)", "ivy_user__familyname VARCHAR(16)", "ivy_user__email_address VARCHAR(64)", "ivy_user__username VARCHAR(16)", "ivy_user__password VARCHAR(128)", "ivy_user__birthday DATE", "ivy_user__language_id VARCHAR(48)", "ivy_user__group_id VARCHAR(48)", "ivy_user__profile_picture_url VARCHAR(1024)", "ivy_user__created_datetime DATETIME"),
    IVY_USER_PERMISSION("ivydb_ivy_user_permission", "ivy_user_permission__id VARCHAR(48) PRIMARY KEY", "ivy_user_permission__permission_id VARCHAR(48)", "ivy_user_permission__user_id VARCHAR(48)", "ivy_user_permission__granted DATETIME"),
    IVY_USER_SESSION("ivydb_ivy_user_session", "ivy_user_session__id VARCHAR(48) PRIMARY KEY", "ivy_user_session__user_id VARCHAR(48)", "ivy_user_session__product_id VARCHAR(48)", "ivy_user_session__token VARCHAR(64)", "ivy_user_session__started DATETIME"),
    IVY_USER_IVYUSB("ivydb_ivy_user_ivyusb", "ivy_user_ivyusb__id VARCHAR(48) PRIMARY KEY", "ivy_user_ivyusb__user_id VARCHAR(48)", "ivy_user_ivyusb__name VARCHAR(128)", "ivy_user_ivyusb__authcode VARCHAR(512)", "ivy_user_ivyusb__active BOOLEAN", "ivy_user_ivyusb__created_datetime DATETIME"),

    IVY_PRODUCT("ivydb_ivy_product", "ivy_product__id VARCHAR(48) PRIMARY KEY", "ivy_product__name VARCHAR(64)", "ivy_product__enabled BOOLEAN", "ivy_product__port INT(5)", "ivy_product__created_datetime DATETIME"),

    IVY_CALENDAR("ivydb_ivy_calendar", "ivy_calendar__id VARCHAR(48) PRIMARY KEY", "ivy_calendar__name VARCHAR(512)", "ivy_calendar__owner_id VARCHAR(48)", "ivy_calendar__created_datetime DATETIME"),

    IVY_GROUP("ivydb_ivy_group", "ivy_group__id VARCHAR(48) PRIMARY KEY", "ivy_group__name VARCHAR(64)", "ivy_group__shortcut CHAR(1)", "ivy_group__hex_color VARCHAR(7)", "ivy_group__staff_group BOOLEAN", "ivy_group__created_datetime DATETIME");

    private final String tableName;
    private final String[] tableColumns;

    Tables(final String tableName, final String... tableColumns){
        this.tableName = tableName;
        this.tableColumns = tableColumns;
    }

    public String getTableName(){
        return this.tableName;
    }

    public String[] getTableColumns(){
        return this.tableColumns;
    }

    static TreeMap<String, Tables> singleTables = new TreeMap<>();

    static{
        Arrays.stream(Tables.values()).forEach(tables -> singleTables.put(tables.getTableName(), tables));
    }

    public static void createTable() throws Exception{

        Console.print(Console.BLUE_BOLD.getColorCode() + "Creating Database-Table...");

        ResultSet resultSet = DatabaseSystem.query("SELECT COLUMN_NAME, ORDINAL_POSITION, TABLE_NAME FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_SCHEMA='" + ConfigEntries.DB_DATABASE.getDefaultValue() + "'");

        for(Tables table : Tables.values()){
            String tableName = table.getTableName();
            Console.print(Console.BLUE_ITAL.getColorCode() + "Table " + tableName + " is created.");

            Map<Integer, String> oldColumnStructure = new HashMap<>();

            try{

                while(resultSet.next()){
                    if(resultSet.getString("TABLE_NAME").equals(tableName)){
                        int ordinalPosition = resultSet.getInt("ORDINAL_POSITION") - 1;
                        String columnName = resultSet.getString("COLUMN_NAME");
                        oldColumnStructure.put(ordinalPosition, columnName);
                    }
                }

                Map<Integer, String> newColumnStructure = new HashMap<>();

                StringBuilder composed = new StringBuilder();
                int count = 0;

                String[] tableColumns = table.getTableColumns();
                for(String column : tableColumns){
                    newColumnStructure.put(count, column.split(" ")[0]);
                    composed.append(tableColumns[count]).append(", ");
                    count++;
                }

                if(oldColumnStructure.toString().equals(newColumnStructure.toString())){
                    continue;
                }

                String composedString = new String(composed);
                String sqlStatement = "CREATE TABLE IF NOT EXISTS " + tableName + " (" + composedString.substring(0, composedString.length() - 2) + ")";

                if(oldColumnStructure.size() == 0){
                    DatabaseSystem.update(sqlStatement);
                    continue;
                }

                ResultSet oldTableData = DatabaseSystem.query("SELECT * FROM " + tableName);
                DatabaseSystem.update("DROP TABLE " + tableName);

                Thread.sleep(100);
                DatabaseSystem.update(sqlStatement);

                while(oldTableData.next()){
                    StringBuilder newComposed = new StringBuilder(), dataComposed = new StringBuilder();
                    newColumnStructure.forEach((key, value) -> {
                        try{
                            newComposed.append(value).append(", ");
                            if(oldColumnStructure.containsValue(value)){
                                String columnName = oldTableData.getString(value);
                                dataComposed.append((columnName == null ? "NULL, " : "'" + columnName + "', "));
                            }else{
                                dataComposed.append("NULL, ");
                            }
                        }catch(Exception exception){
                            exception.printStackTrace();
                        }
                    });

                    String newComposedString = new String(newComposed);
                    newComposedString = StringUtils.cutOff(newComposedString, 2);

                    String dataComposedString = new String(dataComposed);
                    dataComposedString = StringUtils.cutOff(dataComposedString, 2);

                    DatabaseSystem.update("INSERT INTO " + tableName + " (" + newComposedString + ") VALUES (" + dataComposedString + ")");
                }

            }catch(SQLException exception){
                exception.printStackTrace();
            }

        }

    }

    public static List<DefaultEntries> defaultEntries = new ArrayList<>();

    public static void createDefaultValues(){

        defaultEntries.add(new Language(null));
        defaultEntries.add(new Group(null));
        defaultEntries.add(new ProductsSystem());
        defaultEntries.add(Permission.IVY_TEAM__IVY_USB_FORCE_LOGIN);
        defaultEntries.add(LanguagePlaceholder.DefaultLanguagePlaceholder.CUSTOMER);
        defaultEntries.add(LanguagePlaceholder.DefaultLanguagePlaceholderContent.CUSTOMER_DE);

        defaultEntries.forEach(entries -> DatabaseSystem.insert(entries.getTable(), true, entries.getValues()));

    }

}