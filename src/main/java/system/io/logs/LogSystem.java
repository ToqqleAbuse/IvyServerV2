package system.io.logs;

import system.io.filesystem.FileSystem;
import system.io.filesystem.SystemPaths;
import system.tools.skills.TimeManager;

import java.io.File;
import java.io.IOException;

public class LogSystem{

    private final LogTypes logType;

    public LogSystem(final LogTypes logType){
        this.logType = logType;
    }

    public LogTypes getLogType(){
        return this.logType;
    }

    public void log(final String content){

        File logFolder = SystemPaths.LOGS.getFileName();
        if(!logFolder.exists()){
            logFolder.mkdir();
        }

        TimeManager timeManager = new TimeManager(System.currentTimeMillis());
        String dayFolderName = timeManager.getDateFormat("YYYY-MM-dd");
        File dayFolder = new File(logFolder.getPath() + "/" + dayFolderName);

        if(!dayFolder.exists()){
            dayFolder.mkdir();
        }

        try{

            File logFile = new File(dayFolder.getPath() + "/" + logType.getFileName());
            if(!logFile.exists()){
                logFile.createNewFile();
            }

            String logTime = "[" + timeManager.getDateFormat("YYYY-MM-dd hh:mm:ss") + "] ";
            FileSystem.appendFileContent(logFile, logTime + content + "\n");

        }catch(IOException exception){
            exception.printStackTrace();
        }
    }

}
