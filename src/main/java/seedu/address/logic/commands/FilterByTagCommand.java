package seedu.address.logic.commands;

/**
 * Command to filter objects(s) with the related tag(s).
 */
public interface FilterByTagCommand {
    /**
     * Displays the tags entered in the display
     * @return string of the tags keyed in
     */
    static String displayTagKeywords(String[] tagKeywords) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < tagKeywords.length; i++) {
            if (i != tagKeywords.length - 1) {
                sb.append(tagKeywords[i] + ", ");
            } else {
                sb.append(tagKeywords[i]);
            }
        }
        return sb.toString();
    }
}
