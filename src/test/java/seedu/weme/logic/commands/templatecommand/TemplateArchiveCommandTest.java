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
 * {@code TemplateArchiveCommand}.
 */
public class TemplateArchiveCommandTest extends ApplicationTest {

    private Model model;

    @BeforeEach
    public void setup() {
        model = new ModelManager(getTypicalWeme(), new UserPrefs());
    }

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Template templateToArchive = model.getFilteredTemplateList().get(INDEX_FIRST.getZeroBased());
        TemplateArchiveCommand templateArchiveCommand = new TemplateArchiveCommand(INDEX_FIRST);
        Template archivedTemplate = new TemplateBuilder(templateToArchive).withIsArchived(true).build();

        String expectedMessage = String.format(TemplateArchiveCommand.MESSAGE_ARCHIVE_TEMPLATE_SUCCESS,
                templateToArchive);

        ModelManager expectedModel = new ModelManager(model.getWeme(), new UserPrefs());
        expectedModel.setTemplate(templateToArchive, archivedTemplate);
        expectedModel.commitWeme(expectedMessage);

        assertCommandSuccess(templateArchiveCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredTemplateList().size() + 1);
        TemplateArchiveCommand templateArchiveCommand = new TemplateArchiveCommand(outOfBoundIndex);

        assertCommandFailure(templateArchiveCommand, model, Messages.MESSAGE_INVALID_TEMPLATE_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showTemplateAtIndex(model, INDEX_FIRST);

        Template templateToArchive = model.getFilteredTemplateList().get(INDEX_FIRST.getZeroBased());
        TemplateArchiveCommand templateArchiveCommand = new TemplateArchiveCommand(INDEX_FIRST);
        Template archivedTemplate = new TemplateBuilder(templateToArchive).withIsArchived(true).build();

        String expectedMessage = String.format(TemplateArchiveCommand.MESSAGE_ARCHIVE_TEMPLATE_SUCCESS,
                templateToArchive);

        Model expectedModel = new ModelManager(model.getWeme(), new UserPrefs());
        expectedModel.setTemplate(templateToArchive, archivedTemplate);
        expectedModel.commitWeme(expectedMessage);

        assertCommandSuccess(templateArchiveCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showTemplateAtIndex(model, INDEX_FIRST);

        Index outOfBoundIndex = INDEX_SECOND;
        // ensures that outOfBoundIndex is still in bounds of template list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getWeme().getTemplateList().size());

        TemplateArchiveCommand templateArchiveCommand = new TemplateArchiveCommand(outOfBoundIndex);

        assertCommandFailure(templateArchiveCommand, model, Messages.MESSAGE_INVALID_TEMPLATE_DISPLAYED_INDEX);
    }

    @Test
    public void execute_archivedTemplate_throwsCommandException() {
        model.updateFilteredTemplateList(Model.PREDICATE_SHOW_ALL_ARCHIVED_TEMPLATES);
        TemplateArchiveCommand templateArchiveCommand = new TemplateArchiveCommand(INDEX_FIRST);

        assertCommandFailure(templateArchiveCommand, model, TemplateArchiveCommand.MESSAGE_ALREADY_ARCHIVED);
    }

    @Test
    public void equals() {
        TemplateArchiveCommand archiveFirstCommand = new TemplateArchiveCommand(INDEX_FIRST);
        TemplateArchiveCommand archiveSecondCommand = new TemplateArchiveCommand(INDEX_SECOND);

        // same object -> returns true
        assertTrue(archiveFirstCommand.equals(archiveFirstCommand));

        // same values -> returns true
        TemplateArchiveCommand archiveFirstCommandCopy = new TemplateArchiveCommand(INDEX_FIRST);
        assertTrue(archiveFirstCommand.equals(archiveFirstCommandCopy));

        // different types -> returns false
        assertFalse(archiveFirstCommand.equals(1));

        // null -> returns false
        assertFalse(archiveFirstCommand.equals(null));

        // different template -> returns false
        assertFalse(archiveFirstCommand.equals(archiveSecondCommand));
    }

}
