package seedu.address.cashier.logic;

import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.cashier.logic.parser.AddCommandParser;
import seedu.address.person.model.Model;
import seedu.address.person.model.ModelManager;
import seedu.address.person.model.UserPrefs;

public class AddCommandParserTest {
    private AddCommandParser parser = new AddCommandParser();
    private Model personModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void parse_allFieldsPresent_success() {

    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {

    }
}
