package seedu.sugarmummy.logic.commands;

import static seedu.sugarmummy.logic.commands.CommandTestUtil.assertCommandSuccess;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.sugarmummy.model.Model;
import seedu.sugarmummy.model.ModelManager;
import seedu.sugarmummy.model.UserPrefs;
import seedu.sugarmummy.model.bio.UserList;
import seedu.sugarmummy.model.calendar.Calendar;
import seedu.sugarmummy.model.record.UniqueRecordList;
import seedu.sugarmummy.recmfood.model.UniqueFoodList;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ListCommand.
 */
public class ListCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(new UserPrefs(), new UserList(), new UniqueFoodList(),
                new UniqueRecordList(), new Calendar());
        expectedModel = new ModelManager(new UserPrefs(), new UserList(), new UniqueFoodList(),
                new UniqueRecordList(), new Calendar());
    }

    @Test
    public void execute_listIsNotFiltered_showsSameList() {
        assertCommandSuccess(new ListCommand(), model, ListCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_listIsFiltered_showsEverything() {
        //        showPersonAtIndex(sugarmummy.recmfood.model, INDEX_FIRST_PERSON);
        assertCommandSuccess(new ListCommand(), model, ListCommand.MESSAGE_SUCCESS, expectedModel);
    }
}
