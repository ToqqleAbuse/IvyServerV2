package system.startup;

public interface StartupHandler {

    void __init__() throws Exception;

    boolean async();

    boolean systemRequirement();

    String systemName();
}
