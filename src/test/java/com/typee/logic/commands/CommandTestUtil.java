package com.typee.logic.commands;

import static com.typee.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import com.typee.commons.core.index.Index;
import com.typee.logic.commands.exceptions.CommandException;
import com.typee.model.EngagementList;
import com.typee.model.Model;
import com.typee.model.engagement.Engagement;
import com.typee.model.engagement.EngagementPredicate;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {

    public static final String VALID_DESCRIPTION_LUNCH_APPOINTMENT = "Lunch";
    public static final String VALID_DESCRIPTION_GOOGLE_INTERVIEW = "Googs";
    public static final String VALID_DESCRIPTION_TEAM_MEETING = "Team Project Meeting";

    /*
    public static final String VALID_NAME_AMY = "Amy Bee";
    public static final String VALID_NAME_BOB = "Bob Choo";
    public static final String VALID_PHONE_AMY = "11111111";
    public static final String VALID_PHONE_BOB = "22222222";
    public static final String VALID_EMAIL_AMY = "amy@example.com";
    public static final String VALID_EMAIL_BOB = "bob@example.com";
    public static final String VALID_ADDRESS_AMY = "Block 312, Amy Street 1";
    public static final String VALID_ADDRESS_BOB = "Block 123, Bobby Street 3";
    public static final String VALID_REMARK_AMY = "Like skiing.";
    public static final String VALID_REMARK_BOB = "Favourite pastime: Eating";
    public static final String VALID_TAG_HUSBAND = "husband";
    public static final String VALID_TAG_FRIEND = "friend";

    public static final String NAME_DESC_AMY = " " + PREFIX_NAME + VALID_NAME_AMY;
    public static final String NAME_DESC_BOB = " " + PREFIX_NAME + VALID_NAME_BOB;

    public static final String INVALID_NAME_DESC = " " + PREFIX_NAME + "James&"; // '&' not allowed in names

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    public static final EditCommand.EditEngagementDescriptor DESC_AMY;
    public static final EditCommand.EditEngagementDescriptor DESC_BOB;

    static {
        DESC_AMY = new EditPersonDescriptorBuilder().withName(VALID_NAME_AMY).build();
        DESC_BOB = new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB).build();
    }
    */

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
     * Convenience wrapper to
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
        EngagementList expectedEngagementList = new EngagementList(actualModel.getEngagementList());
        List<Engagement> expectedFilteredList = new ArrayList<>(actualModel.getFilteredEngagementList());

        assertThrows(CommandException.class, expectedMessage, () -> command.execute(actualModel));
        assertEquals(expectedEngagementList, actualModel.getEngagementList());
        assertEquals(expectedFilteredList, actualModel.getFilteredEngagementList());
    }

    /**
     * Updates {@code model}'s filtered list to show only the engagement at the given {@code targetIndex} in the
     * {@code model}'s engagement list based on the engagement description of the first engagement.
     */
    public static void showEngagementAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredEngagementList().size());

        Engagement engagement = model.getFilteredEngagementList().get(targetIndex.getZeroBased());
        final String[] splitDescription = engagement.getDescription().split("\\s+");
        EngagementPredicate predicate = new EngagementPredicate();
        predicate.setDescription(splitDescription[0]);
        model.updateFilteredEngagementList(predicate);
        assertEquals(1, model.getFilteredEngagementList().size());
    }

}
