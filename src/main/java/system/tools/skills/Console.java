package system.tools.skills;

import system.io.logs.LogSystem;
import system.io.logs.LogTypes;

import java.io.PrintStream;

public enum Console{

    BLACK_BG((short) 0, "\033[40m"),
    RED_BG((short) 1, "\033[41m"),
    GREEN_BG((short) 2, "\033[42m"),
    YELLOW_BG((short) 3, "\033[43m\""),
    BLUE_BG((short) 4, "\033[44m"),
    PURPLE_BG((short) 5, "\033[45m"),
    CYAN_BG((short) 6, "\033[46m"),
    WHITE_BG((short) 7, "\033[47m"),
    BLACK_UL((short) 8, "\033[4;30m"),
    RED_UL((short) 9, "\033[4;31m"),
    GREEN_UL((short) 10, "\033[4;32m"),
    YELLOW_UL((short) 11, "\033[4;33m"),
    BLUE_UL((short) 12, "\033[4;34m"),
    PURPLE_UL((short) 13, "\033[4;35m"),
    CYAN_UL((short) 14, "\033[4;36m"),
    WHITE_UL((short) 15, "\033[4;37m"),
    BLACK_BOLD((short) 16, "\033[1;30m"),
    RED_BOLD((short) 17, "\033[1;31m"),
    GREEN_BOLD((short) 18, "\033[1;32m"),
    YELLOW_BOLD((short) 19, "\033[1;33m"),
    BLUE_BOLD((short) 20, "\033[1;34m"),
    PURPLE_BOLD((short) 21, "\033[1;35m"),
    CYAN_BOLD((short) 22, "\033[1;36m"),
    WHITE_BOLD((short) 23, "\033[1;37m"),
    BLACK_ITAL((short) 24, "\033[3;30m"),
    RED_ITAL((short) 25, "\033[3;31m"),
    GREEN_ITAL((short) 26, "\033[3;32m"),
    YELLOW_ITAL((short) 27, "\033[3;33m"),
    BLUE_ITAL((short) 28, "\033[3;34m"),
    PURPLE_ITAL((short) 29, "\033[3;35m"),
    CYAN_ITAL((short) 30, "\033[3;36m"),
    WHITE_ITAL((short) 31, "\033[3;37m"),
    RESET((short) 32, "\u001B[0m"),
    BLACK((short) 33, "\033[0;30m"),
    RED((short) 34, "\033[0;31m"),
    YELLOW((short) 35, "\033[0;33m"),
    GREEN((short) 36, "\033[0;32m"),
    BLUE((short) 37, "\033[0;34m"),
    WHITE((short) 38, "\033[0;37m"),
    CYAN((short) 39, "\033[0;36m"),
    PURPLE((short) 40, "\033[0;35m");

    public short id;
    private String color_code;

    Console(final short id, final String colorCode){
        this.id = id;
        this.color_code = colorCode;
    }

    public String getColorCode(){
        return this.color_code;
    }

    public int getId(){
        return this.id;
    }

    public static void print(final Object message){

        LogSystem logSystem = new LogSystem(LogTypes.CONSOLE);
        PrintStream stream = System.out;

        TimeManager timeManager = new TimeManager(System.currentTimeMillis());
        String messageTime = BLACK.getColorCode() + "[" + timeManager.getDateFormat("YYYY.MM.dd hh:mm:ss") + "] " + Console.RESET.getColorCode();

        stream.println(messageTime + message + RESET.getColorCode());
        logSystem.log(message.toString());

    }

}
