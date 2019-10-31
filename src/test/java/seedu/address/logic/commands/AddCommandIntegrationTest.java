package seedu.address.logic.commands;

import org.junit.jupiter.api.BeforeEach;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.bio.UserList;
import seedu.address.model.calendar.Calendar;
import seedu.address.model.record.UniqueRecordList;
import sugarmummy.recmfood.model.UniqueFoodList;


/**
 * Contains integration tests (interaction with the Model) for {@code AddCommand}.
 */
public class AddCommandIntegrationTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(new UserPrefs(), new UserList(), new UniqueFoodList(),
                new UniqueRecordList(), new Calendar());
    }
}
