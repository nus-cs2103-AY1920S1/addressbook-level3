package seedu.deliverymans.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.deliverymans.logic.parser.CliSyntax.PREFIX_LOCATION;
import static seedu.deliverymans.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.deliverymans.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.deliverymans.testutil.Assert.assertThrows;

public class CommandTestUtil {

    public static final String VALID_NAME_ONE = "Name One";
    public static final String VALID_NAME_TWO = "Name Two";
    public static final String VALID_LOCATION_ONE = "Jurong";
    public static final String VALID_LOCATION_TWO = "Changi";
    public static final String VALID_TAG_ONE = "Tag1";
    public static final String VALID_TAG_TWO = "Tag2";

    public static final String NAME_DESC_ONE = " " + PREFIX_NAME + VALID_NAME_ONE;
    public static final String NAME_DESC_TWO = " " + PREFIX_NAME + VALID_NAME_TWO;
    public static final String LOCATION_DESC_ONE = " " + PREFIX_LOCATION + VALID_LOCATION_ONE;
    public static final String LOCATION_DESC_TWO = " " + PREFIX_LOCATION + VALID_LOCATION_TWO;
    public static final String TAG_DESC_ONE = " " + PREFIX_TAG + VALID_TAG_ONE;
    public static final String TAG_DESC_TWO = " " + PREFIX_TAG + VALID_TAG_TWO;

    public static final String INVALID_NAME_DESC = " " + PREFIX_NAME + "N@me";
    public static final String INVALID_LOCATION_DESC = " " + PREFIX_LOCATION + "Malaysia";
    public static final String INVALID_TAG_DESC = " " + PREFIX_TAG + "T@g";

}
