package system.handling.server.evaluation.executors.ivyteam.calendar;

import org.json.JSONObject;
import system.handling.human.user.Staff;
import system.handling.server.evaluation.Events;
import system.handling.server.evaluation.SocketExecutor;

public class IvyTeam__GetCalendarEventData implements SocketExecutor{


    @Override
    public JSONObject exe(JSONObject parameter, Events events, String userId){

        long startTime = parameter.getLong("start_time");
        long endTime = parameter.getLong("end_time");

        Staff staff = new Staff(userId);
        JSONObject returner = new JSONObject();
        return null;

    }

}
