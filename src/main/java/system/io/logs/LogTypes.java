package system.io.logs;

public enum LogTypes{

    DATABASELOG("database_log.log"),
    SYSTEM("system_log.log"),
    CONSOLE("console_log.log"),
    CRONLOG("cron_log.log");

    private final String fileName;

    LogTypes(final String fileName){
        this.fileName = fileName;
    }

    public String getFileName(){
        return this.fileName;
    }

}
