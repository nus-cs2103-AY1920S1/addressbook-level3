package seedu.weme.logic.commands.templatecommand;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.weme.logic.commands.CommandTestUtil.VALID_NAME_DOGE;
import static seedu.weme.logic.commands.CommandTestUtil.VALID_NAME_DRAKE;
import static seedu.weme.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.weme.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.weme.logic.commands.CommandTestUtil.showTemplateAtIndex;
import static seedu.weme.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.weme.testutil.TypicalIndexes.INDEX_SECOND;
import static seedu.weme.testutil.TypicalWeme.getTypicalWeme;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import seedu.weme.commons.core.Messages;
import seedu.weme.commons.core.index.Index;
import seedu.weme.model.Model;
import seedu.weme.model.ModelManager;
import seedu.weme.model.UserPrefs;
import seedu.weme.model.Weme;
import seedu.weme.model.template.Name;
import seedu.weme.model.template.Template;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * TemplateEditCommand.
 */
public class TemplateEditCommandTest extends ApplicationTest {

    private Model model;

    @BeforeEach
    public void setup() {
        model = new ModelManager(getTypicalWeme(), new UserPrefs());
    }

    @Test
    public void execute_nameSpecifiedUnfilteredList_success() {
        Template templateToEdit = model.getFilteredTemplateList().get(INDEX_FIRST.getZeroBased());
        Name newName = new Name(VALID_NAME_DRAKE);
        Template editedTemplate = new Template(newName, templateToEdit.getImagePath());
        TemplateEditCommand memeEditCommand = new TemplateEditCommand(INDEX_FIRST, newName);

        String expectedMessage = String.format(TemplateEditCommand.MESSAGE_EDIT_TEMPLATE_SUCCESS, editedTemplate);

        Model expectedModel = new ModelManager(new Weme(model.getWeme()), new UserPrefs());
        expectedModel.setTemplate(model.getFilteredTemplateList().get(INDEX_FIRST.getZeroBased()), editedTemplate);
        expectedModel.commitWeme(expectedMessage);

        assertCommandSuccess(memeEditCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        showTemplateAtIndex(model, INDEX_FIRST);

        Template templateToEdit = model.getFilteredTemplateList().get(INDEX_FIRST.getZeroBased());
        Name newName = new Name(VALID_NAME_DRAKE);
        Template editedTemplate = new Template(newName, templateToEdit.getImagePath());
        TemplateEditCommand memeEditCommand = new TemplateEditCommand(INDEX_FIRST, newName);

        String expectedMessage = String.format(TemplateEditCommand.MESSAGE_EDIT_TEMPLATE_SUCCESS, editedTemplate);

        Model expectedModel = new ModelManager(new Weme(model.getWeme()), new UserPrefs());
        expectedModel.setTemplate(model.getFilteredTemplateList().get(INDEX_FIRST.getZeroBased()), editedTemplate);
        expectedModel.commitWeme(expectedMessage);

        assertCommandSuccess(memeEditCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicateTemplateUnfilteredList_failure() {
        Name newName = model.getFilteredTemplateList().get(INDEX_SECOND.getZeroBased()).getName();
        TemplateEditCommand memeEditCommand = new TemplateEditCommand(INDEX_FIRST, newName);

        assertCommandFailure(memeEditCommand, model, TemplateEditCommand.MESSAGE_DUPLICATE_TEMPLATE);
    }

    @Test
    public void execute_duplicateMemeFilteredList_failure() {
        showTemplateAtIndex(model, INDEX_FIRST);

        Template templateInList = model.getWeme().getTemplateList().get(INDEX_SECOND.getZeroBased());
        Name name = templateInList.getName();
        TemplateEditCommand templateEditCommand = new TemplateEditCommand(INDEX_FIRST, name);

        assertCommandFailure(templateEditCommand, model, TemplateEditCommand.MESSAGE_DUPLICATE_TEMPLATE);
    }

    @Test
    public void execute_invalidTemplateIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredTemplateList().size() + 1);
        TemplateEditCommand templateEditCommand = new TemplateEditCommand(outOfBoundIndex, new Name(VALID_NAME_DRAKE));

        assertCommandFailure(templateEditCommand, model, Messages.MESSAGE_INVALID_TEMPLATE_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of the template list
     */
    @Test
    public void execute_invalidTemplateIndexFilteredList_failure() {
        showTemplateAtIndex(model, INDEX_FIRST);
        Index outOfBoundIndex = INDEX_SECOND;
        // ensures that outOfBoundIndex is still in bounds of template list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getWeme().getTemplateList().size());

        TemplateEditCommand templateEditCommand = new TemplateEditCommand(outOfBoundIndex, new Name(VALID_NAME_DRAKE));

        assertCommandFailure(templateEditCommand, model, Messages.MESSAGE_INVALID_TEMPLATE_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        final TemplateEditCommand standardCommand = new TemplateEditCommand(INDEX_FIRST, new Name(VALID_NAME_DRAKE));

        // same values -> returns true
        assertEquals(standardCommand, new TemplateEditCommand(INDEX_FIRST, new Name(VALID_NAME_DRAKE)));

        // same object -> returns true
        assertEquals(standardCommand, standardCommand);

        // null -> returns false
        assertNotEquals(standardCommand, null);

        // different types -> returns false
        assertNotEquals(standardCommand, new TemplateClearCommand());

        // different index -> returns false
        assertNotEquals(standardCommand, new TemplateEditCommand(INDEX_SECOND, new Name(VALID_NAME_DRAKE)));

        // different names -> returns false
        assertNotEquals(standardCommand, new TemplateEditCommand(INDEX_FIRST, new Name(VALID_NAME_DOGE)));
    }

}
