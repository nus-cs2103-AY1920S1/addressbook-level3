package seedu.jarvis.logic.commands.cca;

import static seedu.jarvis.logic.parser.CliSyntax.CcaTrackerCliSyntax.PREFIX_CCA_NAME;
import static seedu.jarvis.logic.parser.CliSyntax.CcaTrackerCliSyntax.PREFIX_CCA_TYPE;
import static seedu.jarvis.logic.parser.CliSyntax.CcaTrackerCliSyntax.PREFIX_EQUIPMENT_NAME;

/**
 * Contains helper methods for testing commands.
 */
public class CcaCommandTestUtil {

    public static final String VALID_NAME_CANOEING = "Canoeing";
    public static final String VALID_NAME_GUITAR_ENSEMBLE = "Guitar Ensemble";

    public static final String VALID_NAME_TYPE_SPORT = "sport";
    public static final String VALID_NAME_TYPE_PERFORMING_ART = "performingArt";
    public static final String VALID_NAME_TYPE_UNIFORMED_GROUP = "uniformedGroup";
    public static final String VALID_NAME_TYPE_CLUB = "club";

    public static final String VALID_EQUIPMENT_PADDLE = "paddle";
    public static final String VALID_EQUIPMENT_BOAT = "boat";
    public static final String VALID_EQUIPMENT_GUITAR = "guitar";
    public static final String VALID_EQUIPMENT_MUSICAL_SCORE = "musical score";

    public static final String NAME_DESC_CANOEING = " " + PREFIX_CCA_NAME + VALID_NAME_CANOEING;
    public static final String NAME_DESC_GUITAR_ENSEMBLE = " " + PREFIX_CCA_NAME + VALID_NAME_GUITAR_ENSEMBLE;
    public static final String TYPE_DESC_CANOEING = " " + PREFIX_CCA_TYPE + VALID_NAME_TYPE_SPORT;
    public static final String TYPE_DESC_GUITAR_ENSEMBLE = " " + PREFIX_CCA_TYPE + VALID_NAME_TYPE_PERFORMING_ART;
    public static final String EQUIPMENT_DESC_PADDLE = " " + PREFIX_EQUIPMENT_NAME + VALID_EQUIPMENT_PADDLE;
    public static final String EQUIPMENT_DESC_BOAT = " " + PREFIX_EQUIPMENT_NAME + VALID_EQUIPMENT_BOAT;
    public static final String EQUIPMENT_DESC_GUITAR = " " + PREFIX_EQUIPMENT_NAME + VALID_EQUIPMENT_GUITAR;

    public static final String INVALID_NAME_DESC = " " + PREFIX_CCA_NAME + "bla&"; //& not allowed.
    public static final String INVALID_TYPE_DESC = " " + PREFIX_CCA_TYPE + "societies"; //not a valid enum type.
    public static final String INVALID_EQUIPMENT_DESC = " " + PREFIX_EQUIPMENT_NAME; //empty string not allowed.

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

}
