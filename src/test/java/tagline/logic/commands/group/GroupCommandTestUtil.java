//@@author e0031374
package tagline.logic.commands.group;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static tagline.logic.parser.group.GroupCliSyntax.PREFIX_CONTACTID;
import static tagline.logic.parser.group.GroupCliSyntax.PREFIX_GROUPDESCRIPTION;
import static tagline.model.group.GroupModel.PREDICATE_SHOW_ALL_GROUPS;
import static tagline.testutil.Assert.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import tagline.commons.core.index.Index;
import tagline.logic.commands.Command;
import tagline.logic.commands.CommandResult;
import tagline.logic.commands.exceptions.CommandException;
import tagline.model.Model;
import tagline.model.contact.AddressBook;
import tagline.model.contact.Contact;
import tagline.model.group.Group;
import tagline.model.group.GroupBook;
import tagline.model.group.GroupName;
import tagline.model.group.GroupNameEqualsKeywordPredicate;
import tagline.model.note.Note;
import tagline.model.note.NoteBook;
import tagline.testutil.group.EditGroupDescriptorBuilder;

/**
 * Contains helper methods for testing commands.
 */
public class GroupCommandTestUtil {

    public static final String PREFIX_GROUPNAME = "";

    public static final String VALID_GROUPNAME_HYDRA = "HYDRA";
    public static final String VALID_GROUPNAME_SHIELD = "Shield-Hydra";
    public static final String VALID_GROUPDESCRIPTION_HYDRA = "11111111";
    public static final String VALID_GROUPDESCRIPTION_SHIELD = "22222222";
    public static final String VALID_CONTACTID_HYDRA = "77777";
    public static final String VALID_CONTACTID_WARD = "7711";
    public static final String VALID_CONTACTID_SHIELD = "11111";

    public static final String GROUPNAME_DESC_HYDRA = " " + PREFIX_GROUPNAME + VALID_GROUPNAME_HYDRA;
    public static final String GROUPNAME_DESC_SHIELD = " " + PREFIX_GROUPNAME + VALID_GROUPNAME_SHIELD;
    public static final String GROUPDESCRIPTION_DESC_HYDRA = " " + PREFIX_GROUPDESCRIPTION
        + VALID_GROUPDESCRIPTION_HYDRA;
    public static final String GROUPDESCRIPTION_DESC_SHIELD = " " + PREFIX_GROUPDESCRIPTION
        + VALID_GROUPDESCRIPTION_SHIELD;
    public static final String CONTACTID_DESC_HYDRA = " " + PREFIX_CONTACTID + VALID_CONTACTID_HYDRA;
    public static final String CONTACTID_DESC_WARD = " " + PREFIX_CONTACTID + VALID_CONTACTID_WARD;
    public static final String CONTACTID_DESC_SHIELD = " " + PREFIX_CONTACTID + VALID_CONTACTID_SHIELD;

    public static final String INVALID_GROUPNAME_DESC = " " + PREFIX_GROUPNAME + "WANNA ONE";
    // ' ' not allowed in names
    public static final String INVALID_CONTACTID_DESC = " " + PREFIX_CONTACTID + "12a4"; // 'a' not allowed in tags

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    public static final EditGroupCommand.EditGroupDescriptor DESC_HYDRA;
    public static final EditGroupCommand.EditGroupDescriptor DESC_SHIELD;

    static {
        DESC_HYDRA = new EditGroupDescriptorBuilder().withGroupName(VALID_GROUPNAME_HYDRA)
                .withGroupDescription(VALID_GROUPDESCRIPTION_HYDRA)
                .withMemberIds(VALID_CONTACTID_WARD, VALID_CONTACTID_HYDRA).build();
        DESC_SHIELD = new EditGroupDescriptorBuilder().withGroupName(VALID_GROUPNAME_SHIELD)
                .withGroupDescription(VALID_GROUPDESCRIPTION_SHIELD)
                .withMemberIds(VALID_CONTACTID_SHIELD, VALID_CONTACTID_WARD).build();
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - the returned {@link CommandResult} matches {@code expectedCommandResult} <br>
     * - the {@code actualModel} matches {@code expectedModel}
     */
    public static void assertCommandSuccess(Command command, Model actualModel, Model expectedModel) {
        try {
            CommandResult result = command.execute(actualModel);
            //assertEquals(expectedCommandResult, result);
            assertEquals(expectedModel, actualModel);
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - the {@code actualModel} matches {@code expectedModel}
     * - weaker check used when the commandresult is too unwieldy due to view not being implemented yet
     */
    public static void assertCommandSuccess(Command command, Model actualModel, CommandResult expectedCommandResult,
                                            Model expectedModel) {
        try {
            CommandResult result = command.execute(actualModel);
            assertEquals(expectedCommandResult, result);
            assertEquals(expectedModel.getAddressBook(), actualModel.getAddressBook());
            assertEquals(expectedModel.getFilteredContactList(), actualModel.getFilteredContactList());
            assertEquals(expectedModel.getNoteBook(), actualModel.getNoteBook());
            assertEquals(expectedModel.getFilteredNoteList(), actualModel.getFilteredNoteList());
            assertEquals(expectedModel.getGroupBook(), actualModel.getGroupBook());
            assertEquals(expectedModel.getFilteredGroupList(), actualModel.getFilteredGroupList());
            assertEquals(expectedModel.getUserPrefs(), actualModel.getUserPrefs());
            assertEquals(expectedModel, actualModel);
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }
    }

    /**
     * Convenience wrapper to {@link #assertCommandSuccess(Command, Model, CommandResult, Model)}
     * that takes a string {@code expectedMessage} and a {@code ViewType} {@code expectedViewType}.
     */
    public static void assertCommandSuccess(Command command, Model actualModel, String expectedMessage,
                                            CommandResult.ViewType expectedViewType, Model expectedModel) {
        CommandResult expectedCommandResult = new CommandResult(expectedMessage, expectedViewType);
        assertCommandSuccess(command, actualModel, expectedCommandResult, expectedModel);
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
     * - the address book, filtered group list and selected group in {@code actualModel} remain unchanged
     */
    public static void assertCommandFailure(Command command, Model actualModel, String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        AddressBook expectedAddressBook = new AddressBook(actualModel.getAddressBook());
        NoteBook expectedNoteBook = new NoteBook(actualModel.getNoteBook());
        GroupBook expectedGroupBook = new GroupBook(actualModel.getGroupBook());
        List<Contact> expectedFilteredContactList = new ArrayList<>(actualModel.getFilteredContactList());
        List<Note> expectedFilteredNoteList = new ArrayList<>(actualModel.getFilteredNoteList());
        List<Group> expectedFilteredGroupList = new ArrayList<>(actualModel.getFilteredGroupList());

        assertThrows(CommandException.class, expectedMessage, () -> command.execute(actualModel));

        assertEquals(expectedAddressBook, actualModel.getAddressBook());
        assertEquals(expectedNoteBook, actualModel.getNoteBook());
        assertEquals(expectedGroupBook, actualModel.getGroupBook());
        assertEquals(expectedFilteredContactList, actualModel.getFilteredContactList());
        assertEquals(expectedFilteredNoteList, actualModel.getFilteredNoteList());
        assertEquals(expectedFilteredGroupList, actualModel.getFilteredGroupList());
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - the returned {@link CommandResult} matches {@code expectedCommandResult} <br>
     * - the {@code actualModel} matches {@code expectedModel}
     */
    public static void assertCommandFailure(Command command, Model actualModel, String expectedMessage,
                                            Model expectedModel) {

        assertThrows(CommandException.class, expectedMessage, () -> command.execute(actualModel));
        assertEquals(expectedModel, actualModel);
    }

    /**
     * Updates {@code model}'s filtered list to show only the group at the given {@code targetIndex} in the
     * {@code model}'s address book.
     */
    public static void showGroupAtIndex(Model model, String targetIndex) {
        // tried to adapt this as faithfully to the spirit of in AB3 may have errors tho
        model.updateFilteredGroupList(PREDICATE_SHOW_ALL_GROUPS);

        assert GroupName.isValidGroupName(targetIndex);

        GroupName[] groupNames = { new GroupName(targetIndex) };
        model.updateFilteredGroupList(new GroupNameEqualsKeywordPredicate(Arrays.asList(groupNames[0])));

        assertEquals(1, model.getFilteredGroupList().size());
    }

    /**
     * Updates {@code model}'s filtered list to show only the contact at the given {@code targetIndex} in the
     * {@code model}'s address book.
     */
    public static void showGroupAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredGroupList().size());

        Group group = model.getFilteredGroupList().get(targetIndex.getZeroBased());
        GroupName[] groupNames = { group.getGroupName() };
        model.updateFilteredGroupList(new GroupNameEqualsKeywordPredicate(Arrays.asList(groupNames[0])));

        assertEquals(1, model.getFilteredGroupList().size());
    }



}
