package dream.fcard.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;

/**
 * Collection of file read write and path manipulation functions.
 */
public class FileReadWrite {

    /**
     * Resolve relative path from origin path.
     * @param origin        origin path
     * @param relativePath  reltive path
     * @return              absolute path relative from origin
     */
    public static String resolve(String origin, String relativePath) {
        return normalizePath(Paths.get(normalizePath(origin)).resolve(relativePath).toString());
    }

    /**
     * Normalize relative path symbols and home if any into absolute path.
     * @param path  path to normalize
     * @return      normalized path
     */
    public static String normalizePath(String path) {
        if (path.startsWith("~" + File.separator)) {
            path = System.getProperty("user.home") + path.substring(1);
        }
        return Paths.get(path).normalize().toString();
    }

    /**
     * Given a path and content, makes directories if don't exist then writes the file.
     *
     * @param path    path to file
     * @param content content to write
     */
    public static void write(String path, String content) {
        File file = new File(normalizePath(path));
        file.getParentFile().mkdirs();

        try {
            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(content);
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }

    /**
     * Given a path, attempts to read contents of file and return it as string.
     * @param path  path to file
     * @return      file content string
     * @throws FileNotFoundException    file does not exist
     */
    public static String read(String path) throws FileNotFoundException {
        File file = new File(normalizePath(path));
        if (file.exists()) {
            FileReader fr = new FileReader(file.getAbsoluteFile());
            BufferedReader br = new BufferedReader(fr);

            try {
                StringBuilder sb = new StringBuilder();
                String line;
                boolean empty = true;
                while ((line = br.readLine()) != null) {
                    if (!empty) {
                        sb.append("\n");
                    }
                    sb.append(line);
                    empty = false;
                }
                br.close();
                return sb.toString();
            } catch (IOException e) {
                e.printStackTrace();
                System.exit(-1);
            }

        }
        return "";
    }

    /**
     * Delete the file of the given path. Can also delete empty directory.
     * @param path  path to file
     * @return      true successful delete
     */
    public static Boolean delete(String path) {
        File file = new File(normalizePath(path));
        return file.delete();
    }

    /**
     * Determine if file exists.
     * @param path  path to file
     * @return      true if file exists
     */
    public static Boolean fileExists(String path) {
        File file = new File(normalizePath(path));
        return file.exists();
    }
}
