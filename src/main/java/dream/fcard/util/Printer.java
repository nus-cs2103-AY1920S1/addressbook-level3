package dream.fcard.util;

/**
 * Collection of string utils.
 */
public class Printer {

    /**
     * Default indent text of 4 spaces.
     */
    private static final String INDENT = repeatChar(4, ' ');

    /**
     * Surround a string with double quotes.
     *
     * @param str string to be formatted
     * @return formatted string
     */
    public static String surroundQuotes(String str) {
        return "\"" + str + "\"";
    }

    /**
     * Indent each line with INDENT string.
     *
     * @param str string to be indented
     * @return formatted string
     */
    public static String indentString(String str) {
        return indentString(str, INDENT);
    }

    /**
     * Given a specific indent string, append it before each line.
     *
     * @param str string to be indented
     * @param ind indent string
     * @return formatted string
     */
    public static String indentString(String str, String ind) {
        StringBuilder formattedString = new StringBuilder();
        for (String s : str.split("\n")) {
            formattedString.append(ind);
            formattedString.append(s);
            formattedString.append("\n");
        }
        return formattedString.toString();
    }

    /**
     * Generates a string of repeated characters.
     *
     * @param length number of repetitions
     * @param c      character to be repeated
     * @return resulting repeated character string
     */
    public static String repeatChar(int length, char c) {
        return String.valueOf(c).repeat(Math.max(0, length));
    }
}
