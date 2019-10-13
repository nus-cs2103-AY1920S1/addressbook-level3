package seedu.address.logic.parser.preferences;

import seedu.address.logic.parser.Prefix;

/**
 * Contains Command Line Interface (CLI) syntax definitions common to preferences page commands
 */
public class PrefsCliSyntax {

    /* Prefix definitions */
    public static final Prefix PREFIX_WINDOW_HEIGHT = new Prefix("wh/");
    public static final Prefix PREFIX_WINDOW_WIDTH = new Prefix("ww/");
    public static final Prefix PREFIX_WINDOW_XPOS = new Prefix("wx/");
    public static final Prefix PREFIX_WINDOW_YPOS = new Prefix("wy/");
    public static final Prefix PREFIX_GUI_LOCK = new Prefix("lg/");
}
