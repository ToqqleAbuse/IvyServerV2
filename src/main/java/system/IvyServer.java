package system;

import org.reflections.Reflections;

import system.database.DatabaseSystem;
import system.io.logs.LogSystem;
import system.io.logs.LogTypes;
import system.startup.StartupHandler;
import system.tools.skills.Console;

import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class IvyServer{

    public static void test() throws Exception{
        System.out.println(DatabaseSystem.generateUniqueId());
        System.out.println(DatabaseSystem.generateUniqueId());
    }

    public static void main(String[] args) throws Exception{

        LogSystem logSystem = new LogSystem(LogTypes.SYSTEM);
        Console.print(Console.GREEN_BOLD.getColorCode() + "Server is starting...");
        logSystem.log("server is starting...");

        if(args.length > 0){
            Console.print("Take the arguments into account..");
        }

        Reflections reflections = new Reflections(IvyServer.class.getPackage().getName());
        Set<Class<? extends StartupHandler>> classes = reflections.getSubTypesOf(StartupHandler.class);

        classes.forEach(singleClass -> {
            synchronized(singleClass){
                try{
                    StartupHandler startupHandler = singleClass.newInstance();
                    try{

                        Console.print(Console.GREEN_ITAL.getColorCode() + "Preparing " + startupHandler.systemName() + "...");
                        logSystem.log("preparing " + startupHandler.systemName() + "...");
                        if(startupHandler.async()){

                            ExecutorService executorService = Executors.newSingleThreadExecutor();
                            Callable<String> forReturn = () -> {
                                try{
                                    startupHandler.__init__();
                                }catch(Exception exception){
                                    return exception.getMessage();
                                }
                                return null;
                            };

                            Future<String> futureResult = executorService.submit(forReturn);
                            String result = futureResult.get();
                            if(result != null){
                                throw new Exception(result);
                            }

                        }else{

                            try{
                                startupHandler.__init__();
                            }catch(Exception exception){
                                throw new Exception(exception.getMessage());
                            }

                        }

                    }catch(Exception exception){
                        if(startupHandler.systemRequirement()){
                            Console.print("Server cannot be started! [Error in " + startupHandler.systemName() + "] Reason:");
                            Console.print("> " + exception.getMessage());
                            return;
                        }else{
                            Console.print(startupHandler.systemName() + " has an error! Reason:");
                            Console.print("> " + exception.getMessage());
                        }

                    }


                }catch(IllegalAccessException e){
                    e.printStackTrace();
                }catch(InstantiationException e){
                    e.printStackTrace();
                }
            }
        });

        test();
    }

}
