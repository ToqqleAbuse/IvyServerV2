package system.io.filesystem;

import java.io.File;

public enum SystemPaths {

    BACKUPS("backups"),
    CACHE("cache"),
    LOGS("logs"),
    PRODUCTS("products"),
    SCRIPTS("scripts"),
    MAIL("mail"),
    SESSIONS("sessions"),
    CONFIG("config");

    public static File mainPath = new File("/home/elias/IVY");
    final String fileName;

    SystemPaths(final String fileName) {
        this.fileName = fileName;
    }

    public File getFileName() {
        File file = new File(mainPath.getPath() + "/" + this.fileName);
        return file;
    }
}