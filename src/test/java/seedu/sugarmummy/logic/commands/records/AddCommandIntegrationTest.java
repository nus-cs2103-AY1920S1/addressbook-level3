package seedu.sugarmummy.logic.commands.records;

import org.junit.jupiter.api.BeforeEach;

import seedu.sugarmummy.model.Model;
import seedu.sugarmummy.model.ModelManager;
import seedu.sugarmummy.model.UserPrefs;
import seedu.sugarmummy.model.biography.UserList;
import seedu.sugarmummy.model.calendar.Calendar;
import seedu.sugarmummy.model.recmf.UniqueFoodList;
import seedu.sugarmummy.model.records.UniqueRecordList;


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
