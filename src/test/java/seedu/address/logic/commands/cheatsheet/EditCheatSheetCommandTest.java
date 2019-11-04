package seedu.address.logic.commands.cheatsheet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_CS6;
import static seedu.address.logic.commands.CommandTestUtil.DESC_CS7;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CONTENT_CHINESE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CONTENT_ENGLISH;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CONTENT_MATH;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_CHEATSHEET;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FORMULA;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_IMPORTANT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TITLE_GEM;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TITLE_MATH;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.showCheatSheetAtIndex;
import static seedu.address.testutil.TypicalCheatSheets.getTypicalAddressBook;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_CHEATSHEET;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_CHEATSHEET;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.cheatsheet.EditCheatSheetCommand.EditCheatSheetDescriptor;
import seedu.address.logic.commands.global.ClearCommand;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.cheatsheet.CheatSheet;
import seedu.address.model.cheatsheet.Content;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.CheatSheetBuilder;
import seedu.address.testutil.EditCheatSheetDescriptorBuilder;

public class EditCheatSheetCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_duplicateCheatSheetUnfilteredList_failure() {
        CheatSheet firstCheatSheet = model.getFilteredCheatSheetList().get(INDEX_FIRST_CHEATSHEET.getZeroBased());
        EditCheatSheetDescriptor descriptor = new EditCheatSheetDescriptorBuilder(firstCheatSheet).build();
        EditCheatSheetCommand editCommand = new EditCheatSheetCommand(INDEX_SECOND_CHEATSHEET, descriptor);

        assertCommandFailure(editCommand, model, EditCheatSheetCommand.MESSAGE_DUPLICATE_CHEATSHEET);
    }

    @Test
    public void execute_duplicateCheatSheetFilteredList_failure() {
        showCheatSheetAtIndex(model, INDEX_FIRST_CHEATSHEET);

        // edit person in filtered list into a duplicate in address book
        CheatSheet csInList = model.getStudyBuddyPro().getCheatSheetList()
                .get(INDEX_SECOND_CHEATSHEET.getZeroBased());
        EditCheatSheetCommand editCommand = new EditCheatSheetCommand(INDEX_FIRST_CHEATSHEET,
                new EditCheatSheetDescriptorBuilder(csInList).build());

        assertCommandFailure(editCommand, model, EditCheatSheetCommand.MESSAGE_DUPLICATE_CHEATSHEET);
    }

    @Test
    public void execute_invalidCheatSheetIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredCheatSheetList().size() + 1);
        EditCheatSheetDescriptor descriptor = new EditCheatSheetDescriptorBuilder()
                .withTitle(VALID_TITLE_GEM).build();
        EditCheatSheetCommand editCommand = new EditCheatSheetCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_CHEATSHEET_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of address book
     */
    @Test
    public void execute_invalidCheatSheetIndexFilteredList_failure() {
        showCheatSheetAtIndex(model, INDEX_FIRST_CHEATSHEET);
        Index outOfBoundIndex = INDEX_SECOND_CHEATSHEET;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getStudyBuddyPro().getCheatSheetList().size());

        EditCheatSheetCommand editCommand = new EditCheatSheetCommand(outOfBoundIndex,
                new EditCheatSheetDescriptorBuilder().withTitle(VALID_TITLE_GEM).build());

        assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_CHEATSHEET_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        final EditCheatSheetCommand standardCommand = new EditCheatSheetCommand(INDEX_FIRST_CHEATSHEET, DESC_CS6);

        // same values -> returns true
        EditCheatSheetDescriptor copyDescriptor = new EditCheatSheetDescriptor(DESC_CS6);
        EditCheatSheetCommand commandWithSameValues =
                new EditCheatSheetCommand(INDEX_FIRST_CHEATSHEET, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditCheatSheetCommand(INDEX_SECOND_CHEATSHEET, DESC_CS6)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditCheatSheetCommand(INDEX_FIRST_CHEATSHEET, DESC_CS7)));
    }

    @Test
    public void method_updateContents_success() {
        CheatSheet testEdit = new CheatSheetBuilder()
                .withTitle(VALID_TITLE_MATH).withTags(VALID_TAG_CHEATSHEET)
                .withContents(VALID_CONTENT_ENGLISH, VALID_CONTENT_CHINESE, VALID_CONTENT_MATH).build();

        ArrayList<Integer> testRemove = new ArrayList<>();
        testRemove.add(1);

        Set<Content> contentList = new HashSet<>();

        // remove 1 content
        contentList.add(testEdit.getContent(2));
        contentList.add(testEdit.getContent(3));

        assertEquals(contentList, EditCheatSheetCommand.updateContents(testEdit, testRemove));

        // remove all content
        testRemove.add(2);
        testRemove.add(3);
        contentList.clear();

        assertEquals(contentList, EditCheatSheetCommand.updateContents(testEdit, testRemove));
    }

    @Test
    public void method_updateTags_success() {
        CheatSheet testEdit = new CheatSheetBuilder()
                .withTitle(VALID_TITLE_MATH).withTags(VALID_TAG_CHEATSHEET, VALID_TAG_FORMULA, VALID_TAG_IMPORTANT)
                .withContents(VALID_CONTENT_ENGLISH, VALID_CONTENT_CHINESE, VALID_CONTENT_MATH).build();

        Set<Tag> testRemove = new HashSet<>();
        testRemove.add(new Tag(VALID_TAG_CHEATSHEET));

        Set<Tag> tagList = new HashSet<>();
        tagList.add(new Tag(VALID_TAG_FORMULA));
        tagList.add(new Tag(VALID_TAG_IMPORTANT));

        // remove 1 tag
        assertEquals(tagList, EditCheatSheetCommand.updateTags(testEdit, testRemove));

        // remove all tags
        testRemove.add(new Tag(VALID_TAG_FORMULA));
        testRemove.add(new Tag(VALID_TAG_IMPORTANT));
        tagList.clear();
        assertEquals(tagList, EditCheatSheetCommand.updateTags(testEdit, testRemove));
    }

    //TO-BE-TESTED: removeIrrelevantContents()
}
