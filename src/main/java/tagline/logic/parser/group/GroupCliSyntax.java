//@@author e0031374
package tagline.logic.parser.group;

import tagline.logic.parser.Prefix;

/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple commands
 */
public class GroupCliSyntax {

    /* Prefix definitions */
    public static final String GROUP_COMMAND_WORD = "group";
    public static final Prefix PREFIX_CONTACTID = new Prefix("--i");
    public static final Prefix PREFIX_GROUPDESCRIPTION = new Prefix("--d");

}
