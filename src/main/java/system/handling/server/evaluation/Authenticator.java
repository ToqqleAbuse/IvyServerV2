package system.handling.server.evaluation;

import org.json.JSONObject;
import system.tools.utils.JsonUtils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Authenticator{

    public static ConcurrentHashMap<String, SocketExecutor> events = new ConcurrentHashMap<>();

    static{
        for(Events event : Events.values()){
            events.put(event.get_name(), event.get_event_class());
        }
    }

    public static JSONObject execute(final JSONObject objects, final String userId){

        JSONObject wrong_format = new JSONObject();
        wrong_format.put("error", "wrong incoming format");

        JSONObject event_not_found = new JSONObject();
        event_not_found.put("error", "event not found");

        JSONObject missing_parameter = new JSONObject();
        missing_parameter.put("error", "missing parameter");

        JSONObject wrong_datatype = new JSONObject();
        wrong_datatype.put("error", "wrong datatype of parameter");

        JSONObject invalid_user = new JSONObject();
        invalid_user.put("error", "invalid user");

        if(!objects.has("Events")){
            return wrong_format;
        }

        String events_string = objects.get("Events").toString();

        if(!JsonUtils.validJson(events_string)){
            return wrong_format;
        }

        JSONObject events = new JSONObject(events_string);
        Iterator<String> single_events = events.keys();
        JSONObject returner = new JSONObject();

        outer:
        while(single_events.hasNext()){
            String single_event_value = single_events.next();
            String event_parameter = events.get(single_event_value).toString();

            if(!Events.event_exists(single_event_value)){
                returner.put(single_event_value, event_not_found);
                continue;
            }

            if(!JsonUtils.validJson(event_parameter)){
                returner.put(single_event_value, wrong_format);
                continue;
            }

            Events event = Events.of(single_event_value);
            JSONObject parameter = new JSONObject(event_parameter);

            for(String single_parameter : event.get_parameter()){
                String unedited_parameter = single_parameter;
                if(single_parameter.contains(">")){
                    single_parameter = single_parameter.split(">")[0];
                }
                if(!parameter.has(single_parameter) && !unedited_parameter.contains(">optional")){
                    returner.put(single_event_value, missing_parameter);
                    continue outer;
                }
            }


            HashMap<String, ICT> parameter_types = new HashMap<>();
            for(String single_parameter : event.get_parameter()){
                if(single_parameter.contains(">")){
                    String[] value = single_parameter.split(">");
                    String type = value[1];
                    if(type.length() > 0 && ICT.types.containsKey(type)){
                        parameter_types.put(value[0], ICT.of(type));
                    }else{
                        parameter_types.put(value[0], ICT.UNDEFINED);
                    }
                }else{
                    parameter_types.put(single_parameter, ICT.UNDEFINED);
                }
            }

            Iterator<String> parameter_values = parameter.keys();
            while(parameter_values.hasNext()){
                String parameter_single_value = parameter_values.next();
                ICT type = parameter_types.get(parameter_single_value);
                String parameter_value = parameter.get(parameter_single_value).toString();
                switch(type){
                    case INT:
                        try{
                            Integer.parseInt(parameter_value);
                        }catch(Exception exception){
                            returner.put(single_event_value, wrong_datatype);
                            break outer;
                        }
                        break;
                    case LONG:
                        try{
                            Long.parseLong(parameter_value);
                        }catch(Exception exception){
                            returner.put(single_event_value, wrong_datatype);
                            break outer;
                        }
                        break;
                    case JSON:
                        if(!JsonUtils.validJson(parameter_value)){
                            returner.put(single_event_value, wrong_datatype);
                            break outer;
                        }
                        break;
                    case ARRAY:
                        try{
                            String[] parameter_array = parameter_value.split(",");
                            for(String array_value : parameter_array){
                                try{
                                    if(array_value.length() < 1){
                                        throw new ArrayIndexOutOfBoundsException(new String());
                                    }
                                }catch(ArrayIndexOutOfBoundsException exception){
                                    throw new Exception(new String());
                                }
                            }
                        }catch(Exception exception){
                            exception.printStackTrace();
                            returner.put(single_event_value, wrong_datatype);
                            break outer;
                        }
                        break;
                    default:
                        continue;
                }

            }
            SocketExecutor executor = Authenticator.events.get(single_event_value);
            JSONObject event_returner = executor.exe(parameter, event, userId);
            returner.put(single_event_value, event_returner == null ? new JSONObject() : event_returner);

        }

        return returner;
    }

    enum ICT{

        UNDEFINED("undefined"),
        JSON("json"),
        LONG("long"),
        INT("int"),
        ARRAY("array"),
        HEX("hex"),
        DOUBLE("double");

        String type_name;

        ICT(String type_name){
            this.type_name = type_name;
        }

        public String get_type_name(){
            return this.type_name;
        }

        public static final Map<String, ICT> types = new HashMap<>(values().length, 1);

        static{
            for(ICT ict : values())
                types.put(ict.get_type_name(), ict);
        }

        public static ICT of(final String type_name){
            return types.get(type_name);
        }

    }

}