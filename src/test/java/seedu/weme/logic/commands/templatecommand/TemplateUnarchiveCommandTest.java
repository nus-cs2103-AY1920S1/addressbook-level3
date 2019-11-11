package seedu.weme.logic.commands.templatecommand;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
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
import seedu.weme.model.template.Template;
import seedu.weme.testutil.TemplateBuilder;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * {@code TemplateUnarchiveCommand}.
 */
public class TemplateUnarchiveCommandTest extends ApplicationTest {

    private Model model;

    @BeforeEach
    public void setup() {
        model = new ModelManager(getTypicalWeme(), new UserPrefs());
        model.updateFilteredTemplateList(Model.PREDICATE_SHOW_ALL_ARCHIVED_TEMPLATES);
    }

    @Test
    public void execute_validIndexUnfilteredList_success() {
        // prepare archived template
        ModelManager expectedModel = new ModelManager(model.getWeme(), new UserPrefs());
        expectedModel.updateFilteredTemplateList(Model.PREDICATE_SHOW_ALL_ARCHIVED_TEMPLATES);

        Template templateToUnarchive = model.getFilteredTemplateList().get(INDEX_FIRST.getZeroBased());
        TemplateUnarchiveCommand templateUnarchiveCommand = new TemplateUnarchiveCommand(INDEX_FIRST);
        Template unarchivedTemplate = new TemplateBuilder(templateToUnarchive).withIsArchived(true).build();

        String expectedMessage = String.format(TemplateUnarchiveCommand.MESSAGE_UNARCHIVE_TEMPLATE_SUCCESS,
                templateToUnarchive);

        expectedModel.setTemplate(templateToUnarchive, unarchivedTemplate);
        expectedModel.commitWeme(expectedMessage);

        assertCommandSuccess(templateUnarchiveCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredTemplateList().size() + 1);
        TemplateUnarchiveCommand templateUnarchiveCommand = new TemplateUnarchiveCommand(outOfBoundIndex);

        assertCommandFailure(templateUnarchiveCommand, model, Messages.MESSAGE_INVALID_TEMPLATE_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        ModelManager expectedModel = new ModelManager(model.getWeme(), new UserPrefs());
        expectedModel.updateFilteredTemplateList(Model.PREDICATE_SHOW_ALL_ARCHIVED_TEMPLATES);

        showTemplateAtIndex(model, INDEX_FIRST);

        Template templateToUnarchive = model.getFilteredTemplateList().get(INDEX_FIRST.getZeroBased());
        TemplateUnarchiveCommand templateUnarchiveCommand = new TemplateUnarchiveCommand(INDEX_FIRST);
        Template unarchivedTemplate = new TemplateBuilder(templateToUnarchive).withIsArchived(true).build();

        String expectedMessage = String.format(TemplateUnarchiveCommand.MESSAGE_UNARCHIVE_TEMPLATE_SUCCESS,
                templateToUnarchive);

        expectedModel.setTemplate(templateToUnarchive, unarchivedTemplate);
        expectedModel.commitWeme(expectedMessage);

        assertCommandSuccess(templateUnarchiveCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showTemplateAtIndex(model, INDEX_FIRST);

        Index outOfBoundIndex = INDEX_SECOND;
        // ensures that outOfBoundIndex is still in bounds of template list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getWeme().getTemplateList().size());

        TemplateUnarchiveCommand templateUnarchiveCommand = new TemplateUnarchiveCommand(outOfBoundIndex);

        assertCommandFailure(templateUnarchiveCommand, model, Messages.MESSAGE_INVALID_TEMPLATE_DISPLAYED_INDEX);
    }

    @Test
    public void execute_unarchivedTemplate_throwsCommandException() {
        model.updateFilteredTemplateList(Model.PREDICATE_SHOW_ALL_UNARCHIVED_TEMPLATES);
        TemplateUnarchiveCommand templateUnarchiveCommand = new TemplateUnarchiveCommand(INDEX_FIRST);

        assertCommandFailure(templateUnarchiveCommand, model, TemplateUnarchiveCommand.MESSAGE_ALREADY_UNARCHIVED);
    }

    @Test
    public void equals() {
        TemplateUnarchiveCommand unarchiveFirstCommand = new TemplateUnarchiveCommand(INDEX_FIRST);
        TemplateUnarchiveCommand unarchiveSecondCommand = new TemplateUnarchiveCommand(INDEX_SECOND);

        // same object -> returns true
        assertTrue(unarchiveFirstCommand.equals(unarchiveFirstCommand));

        // same values -> returns true
        TemplateUnarchiveCommand unarchiveFirstCommandCopy = new TemplateUnarchiveCommand(INDEX_FIRST);
        assertTrue(unarchiveFirstCommand.equals(unarchiveFirstCommandCopy));

        // different types -> returns false
        assertFalse(unarchiveFirstCommand.equals(1));

        // null -> returns false
        assertFalse(unarchiveFirstCommand.equals(null));

        // different template -> returns false
        assertFalse(unarchiveFirstCommand.equals(unarchiveSecondCommand));
    }

}
