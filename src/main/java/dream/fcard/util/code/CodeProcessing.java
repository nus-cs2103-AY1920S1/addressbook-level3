package dream.fcard.util.code;

import java.io.BufferedReader;
import java.io.IOException;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.tools.shell.Global;

/**
 * Evaluates Javascript code either from the console or from a .js file.
 * Returns output as a string.
 */
public class CodeProcessing {
    /**
     * Evaluates JS code from a console and returns the output as a string.
     * @return the output from Rhino as a String.
     */
    public static String evaluateFromConsole () {
        String code = ConsoleReader.readJsFromConsole();
        Context cx = Context.enter();
        Global global = new Global(cx);
        try {
            Object result = cx.evaluateString(global, code,
                    "<cmd>", 1, null);
            // Convert the result to a string and print it.
            return Context.toString(result);

        } finally {
            // Exit from the context.
            Context.exit();
        }
    }

    /**
     * Evaluates JS code from a given filepath. Uses FileImporter to read in the file.
     * @param filepath
     * @return the output from Rhino as a String.
     */
    public static String evaluateFromFile(String filepath) {

        BufferedReader reader = FileImporter.readJsFile(filepath);
        if (reader == null) {
            return "Error: File/filepath invalid, please try again.";
        }

        Context cx = Context.enter();
        try {
            Global g = new Global(cx);
            // Now evaluateFromConsole the string from a BufferedReader
            Object result = cx.evaluateReader(g, reader, "<cmd>", 1, null);

            // Convert the result to a string and print it.
            return Context.toString(result);

        } catch (IOException e) {
            e.printStackTrace();
            return "Error: I could not read your code from the console.";
        } finally {
            // Exit from the context.
            Context.exit();
        }
    }
}
