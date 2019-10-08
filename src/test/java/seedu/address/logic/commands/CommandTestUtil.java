package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.ParserDateUtil.DATE_TIME_FORMATTER;
import static seedu.address.testutil.Assert.assertThrows;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.commands.trips.edit.EditTripFieldCommand;
import seedu.address.logic.parser.ParserDateUtil;
import seedu.address.model.TravelPal;
import seedu.address.model.Model;
import seedu.address.model.itinerary.Expenditure;
import seedu.address.model.itinerary.Location;
import seedu.address.model.itinerary.Name;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.Person;
import seedu.address.testutil.EditPersonDescriptorBuilder;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {

    public static final String VALID_NAME_AMY = "Amy Bee";
    public static final String VALID_NAME_BOB = "Bob Choo";
    public static final String VALID_PHONE_AMY = "11111111";
    public static final String VALID_PHONE_BOB = "22222222";
    public static final String VALID_EMAIL_AMY = "amy@example.com";
    public static final String VALID_EMAIL_BOB = "bob@example.com";
    public static final String VALID_ADDRESS_AMY = "Block 312, Amy Street 1";
    public static final String VALID_ADDRESS_BOB = "Block 123, Bobby Street 3";
    public static final String VALID_TAG_HUSBAND = "husband";
    public static final String VALID_TAG_FRIEND = "friend";

    public static final String VALID_NAME_AFRICA = "Africa Trip";
    public static final String VALID_NAME_BALI = "Bali Trip";
    public static final String VALID_STARTDATE_AFRICA_1 = "01/01/2019";
    public static final String VALID_STARTDATE_AFRICA_2 = "01/01/2019 1200";
    public static final String VALID_STARTDATE_AFRICA_3 = "1200";
    public static final String VALID_STARTDATE_BALI_1 = "02/02/2018";
    public static final String VALID_STARTDATE_BALI_2 = "02/02/2018 1300";
    public static final String VALID_STARTDATE_BALI_3 = "1300";
    public static final String VALID_ENDDATE_AFRICA_1 = "01/02/2019";
    public static final String VALID_ENDDATE_AFRICA_2 = "01/15/2019 1200";
    public static final String VALID_ENDDATE_AFRICA_3 = "1500";
    public static final String VALID_ENDDATE_BALI_1 = "05/05/2018";
    public static final String VALID_ENDDATE_BALI_2 = "07/07/2018 1300";
    public static final String VALID_ENDDATE_BALI_3 = "2100";
    public static final String VALID_DESTINATION_AFRICA = "Africa";
    public static final String VALID_DESTINATION_BALI = "Bali";
    public static final String VALID_TOTAL_BUDGET_AFRICA = "120.00";
    public static final String VALID_TOTAL_BUDGET_BALI = "3100.00";

    public static final String VALID_NAME_DAY_1 = "Arrival Day";
    public static final String VALID_NAME_DAY_2 = "Best Day";
    public static final String VALID_STARTDATE_DAY_1_1 = "01/01/2019";
    public static final String VALID_STARTDATE_DAY_1_2 = "01/01/2019 1200";
    public static final String VALID_STARTDATE_DAY_1_3 = "1200";
    public static final String VALID_STARTDATE_DAY_2_1 = "02/02/2018";
    public static final String VALID_STARTDATE_DAY_2_2 = "02/02/2018 1300";
    public static final String VALID_STARTDATE_DAY_2_3 = "1300";
    public static final String VALID_ENDDATE_DAY_1_1 = "01/02/2019";
    public static final String VALID_ENDDATE_DAY_1_2 = "01/15/2019 1200";
    public static final String VALID_ENDDATE_DAY_1_3 = "1500";
    public static final String VALID_ENDDATE_DAY_2_1 = "05/05/2018";
    public static final String VALID_ENDDATE_DAY_2_2 = "07/07/2018 1300";
    public static final String VALID_ENDDATE_DAY_2_3 = "2100";
    public static final String VALID_DESTINATION_DAY_1 = "Gambia";
    public static final String VALID_DESTINATION_DAY_2_ = "Bali";
    public static final String VALID_TOTAL_BUDGET_DAY_1 = "120.00";
    public static final String VALID_TOTAL_BUDGET_DAY_2 = "3100.00";
    public static final String VALID_DESCRIPTION_DAY_1 = "This is the day of arrival";
    public static final String VALID_DESCRIPTION_DAY_2 = "This is the second day of my trip";

    public static final String NAME_DESC_AMY = " " + PREFIX_NAME + VALID_NAME_AMY;
    public static final String NAME_DESC_BOB = " " + PREFIX_NAME + VALID_NAME_BOB;
    public static final String PHONE_DESC_AMY = " " + PREFIX_PHONE + VALID_PHONE_AMY;
    public static final String PHONE_DESC_BOB = " " + PREFIX_PHONE + VALID_PHONE_BOB;
    public static final String EMAIL_DESC_AMY = " " + PREFIX_EMAIL + VALID_EMAIL_AMY;
    public static final String EMAIL_DESC_BOB = " " + PREFIX_EMAIL + VALID_EMAIL_BOB;
    public static final String ADDRESS_DESC_AMY = " " + PREFIX_ADDRESS + VALID_ADDRESS_AMY;
    public static final String ADDRESS_DESC_BOB = " " + PREFIX_ADDRESS + VALID_ADDRESS_BOB;
    public static final String TAG_DESC_FRIEND = " " + PREFIX_TAG + VALID_TAG_FRIEND;
    public static final String TAG_DESC_HUSBAND = " " + PREFIX_TAG + VALID_TAG_HUSBAND;

    public static final String INVALID_NAME_DESC = " " + PREFIX_NAME + "James&"; // '&' not allowed in names
    public static final String INVALID_PHONE_DESC = " " + PREFIX_PHONE + "911a"; // 'a' not allowed in phones
    public static final String INVALID_EMAIL_DESC = " " + PREFIX_EMAIL + "bob!yahoo"; // missing '@' symbol
    public static final String INVALID_ADDRESS_DESC = " " + PREFIX_ADDRESS; // empty string not allowed for addresses
    public static final String INVALID_TAG_DESC = " " + PREFIX_TAG + "hubby*"; // '*' not allowed in tags

    /** The required input date format to use. */
    private static final String DATE_TIME_FORMAT = "d/M/y HHmm";
    /** The required input date format to use. */
    private static final String DATE_FORMAT = "d/M/y";
    /** The required input time format to use. */
    private static final String TIME_FORMAT = "HHmm";
    /** The output format for displaying dates and times. */
    private static final String DISPLAY_FORMAT = "d MMM y h:mma";
    /** The dateTime formatter that uses the DATE_TIME_FORMAT pattern. */
    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern(DATE_TIME_FORMAT);
    /** The dateTime formatter that uses the DATE_FORMAT pattern. */
    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern(DATE_FORMAT);
    /** The dateTime formatter that uses the TIME_FORMAT pattern. */
    public static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern(TIME_FORMAT);
    /** The dateTime formatter that uses the DISPLAY_FORMAT pattern. */
    public static final DateTimeFormatter DISPLAY_FORMATTER = DateTimeFormatter.ofPattern(DISPLAY_FORMAT);
    /** The error display message format to be shown if parsing fails. */
    public static final String MESSAGE_INVALID_FORMAT = "Invalid %1$s inputted, use %2$s.";


    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    public static final EditCommand.EditPersonDescriptor DESC_AMY;
    public static final EditCommand.EditPersonDescriptor DESC_BOB;
    public static final EditTripFieldCommand.EditTripDescriptor DESC_AFRICA;

    static {
        DESC_AMY = new EditPersonDescriptorBuilder().withName(VALID_NAME_AMY)
                .withPhone(VALID_PHONE_AMY).withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY)
                .withTags(VALID_TAG_FRIEND).build();
        DESC_BOB = new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB)
                .withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB)
                .withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND).build();

        DESC_AFRICA = new EditTripFieldCommand.EditTripDescriptor();
        DESC_AFRICA.setName(new Name(VALID_NAME_AFRICA));
        DESC_AFRICA.setBudget(new Expenditure(VALID_TOTAL_BUDGET_AFRICA));
        DESC_AFRICA.setStartDate(LocalDateTime.parse(VALID_STARTDATE_AFRICA_2, DATE_TIME_FORMATTER));
        DESC_AFRICA.setDestination(new Location(VALID_DESTINATION_AFRICA));
        DESC_AFRICA.setEndDate(LocalDateTime.parse(VALID_ENDDATE_AFRICA_2, DATE_TIME_FORMATTER));
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - the returned {@link CommandResult} matches {@code expectedCommandResult} <br>
     * - the {@code actualModel} matches {@code expectedModel}
     */
    public static void assertCommandSuccess(Command command, Model actualModel, CommandResult expectedCommandResult,
            Model expectedModel) {
        try {
            CommandResult result = command.execute(actualModel);
            assertEquals(expectedCommandResult, result);
            assertEquals(expectedModel, actualModel);
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }
    }

    /**
     * Convenience wrapper to {@link #assertCommandSuccess(Command, Model, CommandResult, Model)}
     * that takes a string {@code expectedMessage}.
     */
    public static void assertCommandSuccess(Command command, Model actualModel, String expectedMessage,
            Model expectedModel) {
        CommandResult expectedCommandResult = new CommandResult(expectedMessage);
        assertCommandSuccess(command, actualModel, expectedCommandResult, expectedModel);
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - a {@code CommandException} is thrown <br>
     * - the CommandException message matches {@code expectedMessage} <br>
     * - the address book, filtered person list and selected person in {@code actualModel} remain unchanged
     */
    public static void assertCommandFailure(Command command, Model actualModel, String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        TravelPal expectedTravelPal = new TravelPal(actualModel.getTravelPal());
        List<Person> expectedFilteredList = new ArrayList<>(actualModel.getFilteredPersonList());

        assertThrows(CommandException.class, expectedMessage, () -> command.execute(actualModel));
        assertEquals(expectedTravelPal, actualModel.getTravelPal());
        assertEquals(expectedFilteredList, actualModel.getFilteredPersonList());
    }
    /**
     * Updates {@code model}'s filtered list to show only the person at the given {@code targetIndex} in the
     * {@code model}'s address book.
     */
    public static void showPersonAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredPersonList().size());

        Person person = model.getFilteredPersonList().get(targetIndex.getZeroBased());
        final String[] splitName = person.getName().fullName.split("\\s+");
        model.updateFilteredPersonList(new NameContainsKeywordsPredicate(Arrays.asList(splitName[0])));

        assertEquals(1, model.getFilteredPersonList().size());
    }

}
