package seedu.weme.logic.commands.templatecommand;

import static seedu.weme.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.weme.testutil.TypicalWeme.getTypicalWeme;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import seedu.weme.logic.commands.CommandTestUtil;
import seedu.weme.model.Model;
import seedu.weme.model.ModelManager;
import seedu.weme.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) and unit tests for TemplateArchivesCommand.
 */
public class TemplateArchivesCommandTest extends ApplicationTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalWeme(), new UserPrefs());
        expectedModel = new ModelManager(model.getWeme(), new UserPrefs());
        expectedModel.updateFilteredTemplateList(Model.PREDICATE_SHOW_ALL_ARCHIVED_TEMPLATES);
    }

    @Test
    public void execute_listIsFilteredUnarchived_showsArchived() {
        CommandTestUtil.assertCommandSuccess(new TemplateArchivesCommand(), model,
                TemplateArchivesCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_listIsFilteredArchived_showsSameList() {
        model.updateFilteredTemplateList(Model.PREDICATE_SHOW_ALL_ARCHIVED_TEMPLATES);
        assertCommandSuccess(new TemplateArchivesCommand(), model, TemplateArchivesCommand.MESSAGE_SUCCESS,
                expectedModel);
    }
}
