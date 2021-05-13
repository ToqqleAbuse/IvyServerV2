package system.handling.server;

import org.json.JSONObject;
import system.handling.server.evaluation.Authenticator;
import system.startup.StartupHandler;
import system.tools.skills.Console;
import system.tools.skills.ISP;
import system.tools.utils.JsonUtils;
import system.tools.utils.StringUtils;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.Arrays;
import java.util.UUID;

public class SocketServer implements StartupHandler{

    @Override
    public void __init__() throws Exception{


        Thread socketServer = new Thread(() -> {

            int BUFFER = 1024;
            byte[] buffer = new byte[BUFFER];

            DatagramSocket socket = null;
            try{
                socket = new DatagramSocket(12312);
            }catch(Exception exception){
                Console.print(Console.RED.getColorCode() + "socketserver for product  cannot be started");
            }

            while(true){
                try{

                    if(socket == null){
                        continue;
                    }

                    Arrays.fill(buffer, (byte) 0);
                    DatagramPacket datagramPacket = new DatagramPacket(buffer, buffer.length);
                    socket.receive(datagramPacket);
                    String received = new String(buffer, buffer.length);
                    JSONObject errorMessage = new JSONObject();

                    if(!JsonUtils.validJson(received)){
                        errorMessage.put("error", "wrong incoming format");
                        socket.send(sendResponse(datagramPacket, errorMessage.toString()));
                        continue;
                    }

                    if(!checkData(datagramPacket, received)){
                        errorMessage.put("error", "firewall blocked");
                        socket.send(sendResponse(datagramPacket, errorMessage.toString()));
                        continue;
                    }

                    JSONObject incomingData = new JSONObject(received);
                    String salt = incomingData.getString("x");
                    String four = incomingData.getString("y");
                    String value = incomingData.getString("z");
                    String sessionId = incomingData.getString("a");
                    String sessionCheck = incomingData.getString("b");

                    Session session;
                    if(Session.sessions.containsKey(sessionId)){
                        session = Session.sessions.get(sessionId);
                    }else{
                        session = new Session(sessionId);
                    }

                    String sessionToken = session.getSessionToken();
                    if(sessionToken == null || !sessionCheck.equalsIgnoreCase(ISP.getMd5(sessionToken))){
                        errorMessage.put("error", "wrong session token");
                        socket.send(sendResponse(datagramPacket, errorMessage.toString()));
                        continue;
                    }

                    String decodedValue = ISP.aesDecrypt(value, session.getSessionToken(), salt, four);

                    if(!JsonUtils.validJson(decodedValue)){
                        errorMessage.put("error", "wrong incoming format");
                        socket.send(sendResponse(datagramPacket, errorMessage.toString()));
                        continue;
                    }


                    JSONObject decodedJson = new JSONObject(decodedValue);
                    JSONObject returnCreator = Authenticator.execute(decodedJson, session.getSessionUserId());

                    String returnSalt = StringUtils.removeChar(UUID.randomUUID().toString(), '-');
                    String returnFour = StringUtils.removeChar(UUID.randomUUID().toString(), '-');
                    String returnValue = ISP.aesEncrypt(returnCreator.toString(), session.getSessionToken(), returnSalt, returnFour);

                    JSONObject forReturn = new JSONObject();
                    forReturn.put("x", returnSalt);
                    forReturn.put("y", returnFour);
                    forReturn.put("z", returnValue);

                    socket.send(this.sendResponse(datagramPacket, forReturn.toString()));

                }catch(Exception exception){
                    exception.printStackTrace();
                }
            }

        });

        socketServer.start();


    }

    @Override
    public boolean async(){
        return true;
    }

    @Override
    public boolean systemRequirement(){
        return true;
    }

    @Override
    public String systemName(){
        return "Socket-Server";
    }

    public DatagramPacket sendResponse(final DatagramPacket packet, final String message){
        byte[] buffer = message.getBytes();
        return new DatagramPacket(buffer, buffer.length, packet.getAddress(), packet.getPort());
    }

    public boolean checkData(final DatagramPacket packet, final String message){
        return true;
    }

}
