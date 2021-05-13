package system.io.filesystem;

import system.io.config.Config;
import system.startup.StartupHandler;
import system.tools.utils.StringUtils;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class FileSystem implements StartupHandler {

    @Override
    public void __init__() throws Exception {
        for (SystemPaths singlePath : SystemPaths.values()) {
            File pathFile = singlePath.getFileName();

            if (pathFile.exists()) {
                continue;
            }

            if (!singlePath.getFileName().mkdirs()) {
                throw new IOException("cannot create system paths");
            }
        }
        Config.loadConfigEntries();
    }

    @Override
    public boolean async() {
        return false;
    }

    @Override
    public boolean systemRequirement() {
        return true;
    }

    @Override
    public String systemName() {
        return "File-System";
    }

    public static String[] getFileContent(final File file) throws IOException {

        if (!file.isFile()) {
            throw new IOException("specified file object is not a type of file");
        }

        if (!file.canRead()) {
            throw new IOException("missing permissions to read specified file");
        }

        List<String> lines = Files.readAllLines(Paths.get(file.getPath()));

        return lines.toArray(new String[0]);
    }

    public static void appendFileContent(final File file, final String content) throws IOException {


        if (!file.isFile()) {
            throw new IOException("specified file object is not a type of file");
        }

        if (!file.canRead()) {
            throw new IOException("missing permissions to read specified file");
        }

        try {
            BufferedWriter out = new BufferedWriter(new FileWriter(file.getPath(), true));
            out.write(content);
            out.close();
        } catch (IOException exception) {
            throw new IOException("bufferedwriter failed to append file content");
        }

    }

    public static void setFileContent(final File file, final String content) throws IOException {

        if (!file.isFile()) {
            throw new IOException("specified file object is not a type of file");
        }

        if (!file.canRead()) {
            throw new IOException("missing permissions to read specified file");
        }

        try {
            FileWriter writer = new FileWriter(file.getPath());
            writer.write(content);
            writer.flush();
            writer.close();
        } catch (IOException exception) {
            throw new IOException("filewriter failed to write file content");
        }
    }

    public static void removeFileContent(final File file) throws IOException {

        if (!file.isFile()) {
            throw new IOException("specified file object is not a type of file");
        }

        if (!file.canRead()) {
            throw new IOException("missing permissions to read specified file");
        }

        try {

            PrintWriter writer = new PrintWriter(file.getPath());
            writer.print(StringUtils.EMPTY);
            writer.close();
        } catch (IOException exception) {
            throw new IOException("printwriter failed to remove file content");
        }
    }

}
