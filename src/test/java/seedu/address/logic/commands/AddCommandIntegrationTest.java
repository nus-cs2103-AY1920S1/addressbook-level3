package seedu.address.logic.commands;

import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) for {@code AddCommand}.
 */
@Disabled
public class AddCommandIntegrationTest {

    private Model model;

    @Disabled
    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }
}
