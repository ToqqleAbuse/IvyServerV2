package system.database;

import system.io.config.ConfigEntries;

import java.sql.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DatabaseConnection {


    public static ExecutorService executor = Executors.newCachedThreadPool();
    public static Connection con;

    public static void connect() throws SQLException {

        final String host = ConfigEntries.DB_HOST.getDefaultValue();
        final String port = ConfigEntries.DB_PORT.getDefaultValue();
        final String database = ConfigEntries.DB_DATABASE.getDefaultValue();
        final String user = ConfigEntries.DB_USERNAME.getDefaultValue();
        final String password = ConfigEntries.DB_PASSWORD.getDefaultValue();

        if (!is_connected()) {
            con = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + database + "?autoRecconect=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", user, password);
        }

    }

    public static boolean is_connected() {
        return con != null;
    }

}