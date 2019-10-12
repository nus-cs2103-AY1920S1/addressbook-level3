package dream.fcard.util.json;

/**
 * ...
 */
public class JsonParser {

    /**
     * Replace instances of " and n as escape characters for formatting as json string.
     * @param str   string to format
     * @return      formatted string
     */
    public static String formatStringForJson(String str) {
        return "\"" + str.replaceAll("\"", "\\\\\"").replaceAll("\n", "\\\\\n") + "\"";
    }
}
