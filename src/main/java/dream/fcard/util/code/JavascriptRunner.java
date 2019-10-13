package dream.fcard.util.code;

import java.io.BufferedReader;
import java.io.IOException;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.tools.shell.Global;

/**
 * Evaluates Javascript code either from the console or from a .js file.
 * Returns output as a string.
 */
public class JavascriptRunner {
    /**
     * Evaluates JS code from a given filepath. Uses FileImporter to read in the file.
     * @param filepath
     * @return the output from Rhino as a String.
     */
    public static String evaluateFromFile(String filepath) throws IOException {

        BufferedReader reader = FileImporter.readJsFile(filepath);
        if (reader == null) {
            return "Error: File/filepath invalid, please try again.";
        }
        StringBuilder sb = new StringBuilder();
        String lines;
        while ((lines = reader.readLine()) != null) {
            sb.append(lines);
        }

        return evaluateString(sb.toString());
    }

    /**
     * Evaluates a piece of JavaScript code.
     * @param code the JS code.
     * @return any output from running the code.
     */
    public static String evaluateString(String code) {
        Context cx = Context.enter();
        try {
            Global g = new Global(cx);
            Object result = cx.evaluateString(g, code, "<cmd>", 1, null);
            return Context.toString(result);
        } finally {
            Context.exit();
        }
    }
}
