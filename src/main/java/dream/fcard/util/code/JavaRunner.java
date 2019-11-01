package dream.fcard.util.code;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


/**
 * Type your Java code inside Main.java in the /data directory. Then run the main method in this class to see
 * your code being compiled and run during runtime.
 */
public class JavaRunner {
    /**
     * Runs a java file with input.
     * @param classFilePath The
     * @param inputFilePath The location of the file with input
     * @return any output or errors
     */
    public static String runJavaWithRedirection(String classFilePath, String inputFilePath) {
        try {
            ProcessBuilder pb = new ProcessBuilder("java", classFilePath);
            pb.redirectInput(new File(inputFilePath));
            pb.redirectErrorStream(true);
            Process pro = pb.start();
            return getTextFromStream(pro.getInputStream()) + getTextFromStream(pro.getErrorStream());
        } catch (IOException e) {
            return e.toString();
        }
    }

    /**
     * Run Java code without needing system inputs. Used in Java Playground
     * @param classFilePath the location of the file
     * @return any output from the console.
     */
    public static String runJava(String classFilePath) {
        try {
            ProcessBuilder pb = new ProcessBuilder("java", classFilePath);
            pb.redirectErrorStream(true);
            Process pro = pb.start();
            return getTextFromStream(pro.getInputStream())
                    + getTextFromStream(pro.getErrorStream());
        } catch (IOException e) {
            return e.toString();
        }
    }

    /**
     * A method to compile Java files. Returns any console output arising from errors.
     * @param javaFilePath The location of the Java class
     * @return the console output if any
     * @throws IOException thrown if the Process that this compilation is on fails.
     */
    public static String compile(String javaFilePath) throws IOException {
        ProcessBuilder pb = new ProcessBuilder("javac", javaFilePath);
        pb.redirectErrorStream(true);
        Process pro = pb.start();
        return getTextFromStream(pro.getInputStream())
                + getTextFromStream(pro.getErrorStream());
    }


    private static String getTextFromStream(InputStream ins) throws IOException {
        StringBuilder sb = new StringBuilder();
        String line;
        BufferedReader in = new BufferedReader(
                new InputStreamReader(ins));
        while ((line = in.readLine()) != null) {
            sb.append(line).append("\n");
        }
        return sb.toString();
    }
}
