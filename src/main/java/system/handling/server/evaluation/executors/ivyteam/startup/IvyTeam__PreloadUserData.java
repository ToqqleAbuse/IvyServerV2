package system.handling.server.evaluation.executors.ivyteam.startup;

import org.json.JSONObject;
import system.handling.human.user.User;
import system.handling.human.user.UserProperties;
import system.handling.server.evaluation.Events;
import system.handling.server.evaluation.SocketExecutor;

public class IvyTeam__PreloadUserData implements SocketExecutor{

    @Override
    public JSONObject exe(JSONObject parameter, Events events, String userId){

        UserProperties[] properties = {
                UserProperties.FIRSTNAME,
        };

        System.out.println(userId);

        User user = new User(userId, properties);
        JSONObject forReturn = new JSONObject();

        if(!user.isStaff()){
            forReturn.put("error", "no valid user account (staff required)");
            return forReturn;
        }

        JSONObject permissions = new JSONObject();
        user.getPermissions().forEach((permission, value) -> permissions.put(permission.getPermissionId(), (value ? "true" : "false")));
        forReturn.put("permissions", permissions.toString());

        String userFirstname = user.getFirstname();
        forReturn.put("firstname", userFirstname);

        return forReturn;
    }
}
