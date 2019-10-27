package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LOCATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ORGANISATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PROJECT_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SUBJECT_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.Arrays;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.editcommand.EditMentorCommand.EditMentorDescriptor;
import seedu.address.logic.commands.editcommand.EditParticipantCommand.EditParticipantDescriptor;
import seedu.address.logic.commands.editcommand.EditTeamCommand.EditTeamDescriptor;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.Person;
import seedu.address.testutil.EditMentorDescriptorBuilder;
import seedu.address.testutil.EditParticipantDescriptorBuilder;
import seedu.address.testutil.EditTeamDescriptorBuilder;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {

    // Amy Bee used as a Participant and Mentor
    public static final String VALID_NAME_AMY = "Amy Bee";
    public static final String VALID_PHONE_AMY = "+611111111";
    public static final String VALID_EMAIL_AMY = "amy@example.com";
    public static final String VALID_ORGANIZATION_AMY = "Amy Org";
    public static final String VALID_SUBJECT_AMY = "Environmental";

    // Bob Choo used as a Participant and Mentor
    public static final String VALID_NAME_BOB = "Bob Choo";
    public static final String VALID_PHONE_BOB = "+622222222";
    public static final String VALID_EMAIL_BOB = "bob@example.com";
    public static final String VALID_ORGANIZATION_BOB = "Bob Org";
    public static final String VALID_SUBJECT_BOB = "Social";

    // Alfred used as a Team
    public static final String VALID_NAME_ALFRED = "Alfred";
    public static final String VALID_SUBJECT_ALFRED = "Health";
    public static final int VALID_SCORE_ALFRED = 100;
    public static final String VALID_PROJECT_NAME_ALFRED = "Hackathon Butler";
    public static final int VALID_LOCATION_ALFRED = 1;

    //Bruce used as a Team
    public static final String VALID_NAME_BRUCE = "Bruce";
    public static final String VALID_SUBJECT_BRUCE = "Education";
    public static final int VALID_SCORE_BRUCE = 99;
    public static final String VALID_PROJECT_NAME_BRUCE = "Hackathon Batman";
    public static final int VALID_LOCATION_BRUCE = 2;

    public static final EditMentorDescriptor MENTOR_DESC_AMY;
    public static final EditMentorDescriptor MENTOR_DESC_BOB;
    public static final EditParticipantDescriptor PARTICIPANT_DESC_AMY;
    public static final EditParticipantDescriptor PARTICIPANT_DESC_BOB;
    public static final EditTeamDescriptor TEAM_DESC_ALFRED;
    public static final EditTeamDescriptor TEAM_DESC_BRUCE;

    static {
        MENTOR_DESC_AMY = new EditMentorDescriptorBuilder().withName(VALID_NAME_AMY)
                .withPhone(VALID_PHONE_AMY).withEmail(VALID_EMAIL_AMY)
                .withOrganization(VALID_ORGANIZATION_AMY).withSubject(VALID_SUBJECT_AMY).build();
        MENTOR_DESC_BOB = new EditMentorDescriptorBuilder().withName(VALID_NAME_BOB)
                .withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB)
                .withOrganization(VALID_ORGANIZATION_BOB).withSubject(VALID_SUBJECT_BOB).build();
        PARTICIPANT_DESC_AMY = new EditParticipantDescriptorBuilder().withName(VALID_NAME_AMY)
                .withPhone(VALID_PHONE_AMY).withEmail(VALID_EMAIL_AMY).build();
        PARTICIPANT_DESC_BOB = new EditParticipantDescriptorBuilder().withName(VALID_NAME_BOB)
                .withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB).build();
        TEAM_DESC_ALFRED = new EditTeamDescriptorBuilder().withName(VALID_NAME_ALFRED)
                .withSubject(VALID_SUBJECT_ALFRED).withScore(VALID_SCORE_ALFRED)
                .withProjectName(VALID_PROJECT_NAME_ALFRED).withLocation(VALID_LOCATION_ALFRED).build();
        TEAM_DESC_BRUCE = new EditTeamDescriptorBuilder().withName(VALID_NAME_BRUCE)
                .withSubject(VALID_SUBJECT_BRUCE).withScore(VALID_SCORE_BRUCE)
                .withProjectName(VALID_PROJECT_NAME_BRUCE).withLocation(VALID_LOCATION_BRUCE).build();
    }

    // Redundant and can be deleted
    public static final String VALID_ADDRESS_AMY = "Block 312, Amy Street 1";
    public static final String VALID_ADDRESS_BOB = "Block 123, Bobby Street 3";
    public static final String VALID_TAG_HUSBAND = "husband";
    public static final String VALID_TAG_FRIEND = "friend";

    // Participant and Mentor formatted data
    public static final String NAME_DESC_AMY = " " + PREFIX_NAME + VALID_NAME_AMY;
    public static final String NAME_DESC_BOB = " " + PREFIX_NAME + VALID_NAME_BOB;
    public static final String PHONE_DESC_AMY = " " + PREFIX_PHONE + VALID_PHONE_AMY;
    public static final String PHONE_DESC_BOB = " " + PREFIX_PHONE + VALID_PHONE_BOB;
    public static final String EMAIL_DESC_AMY = " " + PREFIX_EMAIL + VALID_EMAIL_AMY;
    public static final String EMAIL_DESC_BOB = " " + PREFIX_EMAIL + VALID_EMAIL_BOB;
    public static final String ORGANIZATION_DESC_AMY = " " + PREFIX_ORGANISATION + VALID_ORGANIZATION_AMY;
    public static final String ORGANIZATION_DESC_BOB = " " + PREFIX_ORGANISATION + VALID_ORGANIZATION_BOB;
    public static final String SUBJECT_DESC_AMY = " " + PREFIX_SUBJECT_NAME + VALID_SUBJECT_AMY;
    public static final String SUBJECT_DESC_BOB = " " + PREFIX_SUBJECT_NAME + VALID_SUBJECT_BOB;

    // Team formatted data
    public static final String NAME_DESC_ALFRED = " " + PREFIX_NAME + VALID_NAME_ALFRED;
    public static final String NAME_DESC_BRUCE = " " + PREFIX_NAME + VALID_NAME_BRUCE;
    public static final String LOCATION_DESC_ALFRED = " " + PREFIX_LOCATION + VALID_LOCATION_ALFRED;
    public static final String LOCATION_DESC_BRUCE = " " + PREFIX_LOCATION + VALID_LOCATION_BRUCE;
    public static final String PROJECT_NAME_DESC_ALFRED = " " + PREFIX_PROJECT_NAME + VALID_PROJECT_NAME_ALFRED;
    public static final String PROJECT_NAME_DESC_BRUCE = " " + PREFIX_PROJECT_NAME + VALID_PROJECT_NAME_BRUCE;
    public static final String SUBJECT_DESC_ALFRED = " " + PREFIX_SUBJECT_NAME + VALID_SUBJECT_ALFRED;
    public static final String SUBJECT_DESC_BRUCE = " " + PREFIX_SUBJECT_NAME + VALID_SUBJECT_BRUCE;

    // Redundant and can be deleted
    public static final String ADDRESS_DESC_AMY = " " + PREFIX_ADDRESS + VALID_ADDRESS_AMY;
    public static final String ADDRESS_DESC_BOB = " " + PREFIX_ADDRESS + VALID_ADDRESS_BOB;
    public static final String TAG_DESC_FRIEND = " " + PREFIX_TAG + VALID_TAG_FRIEND;
    public static final String TAG_DESC_HUSBAND = " " + PREFIX_TAG + VALID_TAG_HUSBAND;

    // Invalid data for tests
    public static final String INVALID_NAME_DESC = " " + PREFIX_NAME + "James&"; // '&' not allowed in names
    public static final String INVALID_PROJECT_NAME_DESC = " " + PREFIX_PROJECT_NAME
            + "James&"; // '&' not allowed in names
    public static final String INVALID_PHONE_DESC = " " + PREFIX_PHONE + "911a"; // 'a' not allowed in phones
    public static final String INVALID_EMAIL_DESC = " " + PREFIX_EMAIL + "bob!yahoo"; // missing '@' symbol
    public static final String INVALID_ORGANISATION_DESC = " " + PREFIX_ORGANISATION + "Y@hoo"; // invalid '@' symbol
    public static final String INVALID_SUBJECT_DESC = " " + PREFIX_SUBJECT_NAME + "Dance"; // invalid Subject Name
    public static final String INVALID_LOCATION_DESC = " " + PREFIX_LOCATION + "room5"; // Should be a number

    // Redundant and can be deleted
    public static final String INVALID_ADDRESS_DESC = " " + PREFIX_ADDRESS; // empty string not allowed for addresses
    public static final String INVALID_TAG_DESC = " " + PREFIX_TAG + "hubby*"; // '*' not allowed in tags

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

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

        // TODO: uncomment this later when everything is deprecated
        // List<Participant> expectedFilteredParticipantList
        //     = new ArrayList<>(actualModel.getFilteredParticipantList());
        // List<Team> expectedFilteredTeamList = new ArrayList<>(actualModel.getFilteredTeamList());
        // List<Mentor> expectedFilteredMentorList = new ArrayList<>(actualModel.getFilteredMentorList());

        assertThrows(CommandException.class, expectedMessage, () -> command.execute(actualModel));
        // assertEquals(expectedFilteredParticipantList, actualModel.getFilteredParticipantList());
        // assertEquals(expectedFilteredTeamList, actualModel.getFilteredTeamList());
        // assertEquals(expectedFilteredMentorList, actualModel.getFilteredMentorList());
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
