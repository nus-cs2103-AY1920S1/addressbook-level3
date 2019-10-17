package dream.fcard.util.code;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
//solution adapted from from https://www.journaldev.com/937/compile-run-java-program-another-java-program
//Credits: Pankaj

/**
 * Type your Java code inside Solution.java in the /data directory. Then run the main method in this class to see
 * your code being compiled and run during runtime.
 */
public class JavaRunner {

    /**
     * Exists for testing {@code compileAndRun} without GUI.
     * @param args not needed.
     * @throws IOException thrown when reading in the Java file.
     */
    public static void main(String[] args) throws IOException {
        String result = compileAndRun("C:\\Users\\User\\Documents\\GitHub\\main\\src\\main\\java"
                + "\\dream\\fcard\\util\\code\\data\\Solution.java");
        System.out.print(result);
    }

    /**
     * Will look for a .java file in the given filepath and compile and run the code, returning any output
     * as a String.
     * @param filepath where the file resides.
     * @return the output from Java
     * @throws IOException if the file is not a Java classfile, or the file could not be read from.
     */
    public static String compileAndRun(String filepath) throws IOException {
        if (!filepath.endsWith(".java")) {
            throw new IOException("Your file is not a Java file");
        }
        runProcess("javac " + filepath);
        return runProcess("java " + filepath);
    }

    /**
     * Gets the {@code InputStream} from the generated {@code Process} and collects its output.
     * @param ins the {@code InputStream}.
     * @return the output from the Process's {@code InputStream}.
     * @throws IOException if reading from the {@code InputStream} fails.
     */
    private static String collectOutput(InputStream ins) throws IOException {
        String line;
        StringBuilder sb = new StringBuilder();
        BufferedReader in = new BufferedReader(
                new InputStreamReader(ins));
        while ((line = in.readLine()) != null) {
            sb.append(line).append("\n");
        }
        return sb.toString();
    }

    /**
     * Runs the given command on a {@code Process}
     * @param command the command to run
     * @return the output from the computer.
     * @throws IOException if the command could not be run.
     */
    private static String runProcess(String command) throws IOException {
        Process pro = Runtime.getRuntime().exec(command);
        StringBuilder sb = new StringBuilder();
        sb.append(collectOutput(pro.getInputStream()));
        sb.append(collectOutput(pro.getErrorStream()));
        pro.destroy();
        return sb.toString();
    }

}


