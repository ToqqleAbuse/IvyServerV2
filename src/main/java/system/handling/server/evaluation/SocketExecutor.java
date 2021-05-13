package system.handling.server.evaluation;

import org.json.JSONObject;
import system.handling.human.user.User;

public interface SocketExecutor{

    JSONObject exe(final JSONObject parameter, final Events events, final String userId);

}