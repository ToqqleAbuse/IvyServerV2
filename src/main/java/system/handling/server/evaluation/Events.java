package system.handling.server.evaluation;

import system.handling.server.evaluation.executors.ivyteam.calendar.IvyTeam__GetCalendarEventData;
import system.handling.server.evaluation.executors.ivyteam.startup.IvyTeam__PreloadUserData;

import java.util.*;

public enum Events{

    IVY_TEAM__PRELOAD_USER_DATA("ivy_team__preload_user_data", new IvyTeam__PreloadUserData()),
    IVY_TEAM__GET_CALENDAR_EVENT_DATA("ivy_team__get_calendar_event_data", new IvyTeam__GetCalendarEventData(), "start_time>long", "end_time>long");

    private String name;
    private String[] parameter;
    private SocketExecutor event_class;

    Events(final String name, final SocketExecutor event_class, final String... parameter){
        this.name = name;
        this.event_class = event_class;
        this.parameter = parameter;
    }

    private static final Map<String, Events> map = new HashMap<>(values().length, 1);

    static{
        for(Events c : values())
            map.put(c.name.toLowerCase(), c);
    }

    public static Events of(final String name){
        return map.get(name);
    }

    public static boolean event_exists(final String event_name){
        for(Events single_event : Events.values()){
            if(event_name.equals(single_event.get_name())){
                return true;
            }
        }
        return false;
    }

    public String get_name(){
        return this.name;
    }

    public String[] get_parameter(){
        return this.parameter;
    }

    public SocketExecutor get_event_class(){ return this.event_class; }


}