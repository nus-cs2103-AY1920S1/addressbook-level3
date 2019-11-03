package dream.fcard.util;

import java.util.ArrayList;
import java.util.Arrays;

public class RegexUtil {

    /**
     * Given a string in the command format, extract the argument values
     * An argument can be repeated more than once, thus each argument has
     * an ArrayList of string values.
     * @param command   command header for the input
     * @param args      arguments to search for
     * @param input     input string
     * @return          ArrayList of each argument's ArrayList of values
     */
    public static ArrayList<ArrayList<String>> parseCommandFormat(String command, String[] args, String input) {
        ArrayList<String> inp = new ArrayList<>(Arrays.asList(
                command.length() == 0
                        ? input
                        : input.split("^"+ ignoreCase(command) + "\\s*")[1]));
        ArrayList<Integer> ain = new ArrayList<>(Arrays.asList(-1));
        ArrayList<ArrayList<String>> res = new ArrayList<>();

        for (int i = 0; i < args.length; i++) {
            res.add(new ArrayList<>());
            for (int j = 0; j < inp.size(); j++) {
                String[] sub = inp.get(j).split(args[i]);
                for(int l = 0; l < sub.length; l++) {
                    sub[l] = sub[l].replaceAll("\\s+$", "");
                }
                inp.set(j, sub[0]);
                for (int k = 1; k < sub.length; k++) {
                    j++;
                    inp.add(j, sub[k]);
                    ain.add(j, i);
                }
            }
        }

        for (int i = 1; i < inp.size(); i++) {
            res.get(ain.get(i)).add(inp.get(i));
        }

        return res;
    }

    /**
     * Generate regex to match a command format input.
     * @param command   command header
     * @param args      arguments in the command
     * @return          regex matching the command format
     */
    public static String commandFormatRegex(String command, String[] args) {
        return Arrays.stream(args).reduce("^" + ignoreCase(command), (a,v) -> a + argGroup(v), String::join) + ".*";
    }

    /**
     * Returns a regex for an argument in a command.
     * @param arg   argument text
     * @return      regex for an argument in a command
     */
    public static String argGroup(String arg) {
        return lookAhead(ignoreCase(arg));
    }

    /**
     * Returns a regex that expects a regex paired with string
     * @param arg   argument regex
     * @return      regex paired with string
     */
    public static String pairWith(String arg) {
        return pairWith(arg, false);
    }

    /**
     * Returns a regex that expects a regex paired with string or number.
     * @param arg   argument regex
     * @param num   true; pairs with number, false; any string
     * @return      regex paired with string or number
     */
    public static  String pairWith(String arg, Boolean num) {
        return arg + "\\s*" + (num ? "[0-9]+" : "\\S");
    }

    /**
     * Returns a regex that looks ahead for regex.
     * @param arg   argument regex
     * @return      lookahead regex
     */
    public static String lookAhead(String arg) {
        return "(?=.*" + arg + ")";
    }

    /**
     * Returns a regex that ignore case for argument.
     * @param arg   argument regex
     * @return      ignore case regex result
     */
    public static String ignoreCase(String arg) {
        return "((?i)" + arg + ")";
    }
}
