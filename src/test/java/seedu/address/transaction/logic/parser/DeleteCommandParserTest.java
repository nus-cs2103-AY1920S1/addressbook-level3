package seedu.address.transaction.logic.parser;

import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.transaction.logic.commands.CommandTestUtil.DESC_NAME_ALICE;
import static seedu.address.transaction.logic.commands.CommandTestUtil.DESC_NAME_AMY;
import static seedu.address.transaction.logic.parser.CommandParserTestUtil.assertCommandParseWithPersonModelFailure;
import static seedu.address.transaction.logic.parser.CommandParserTestUtil.assertCommandParseWithPersonModelSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.person.model.CheckAndGetPersonByNameModel;
import seedu.address.person.model.ModelManager;
import seedu.address.person.model.UserPrefs;
import seedu.address.testutil.TypicalPersons;
import seedu.address.transaction.logic.commands.DeleteIndexCommand;
import seedu.address.transaction.logic.commands.DeleteNameCommand;
import seedu.address.transaction.ui.TransactionMessages;

class DeleteCommandParserTest {
    private DeleteCommandParser parser = new DeleteCommandParser();
    private CheckAndGetPersonByNameModel personModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void parse_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new DeleteCommandParser()
                .parse("dummy" , null));
    }

    @Test
    public void parse_validArgsWithinBounds_returnsDeleteIndexCommand() {
        DeleteIndexCommand deleteIndexCommand = new DeleteIndexCommand(1);
        assertCommandParseWithPersonModelSuccess(parser, " 1", deleteIndexCommand, personModel);
    }

    @Test
    public void parse_invalidArgs_notANumberAndNoPersonPrefix() {
        assertCommandParseWithPersonModelFailure(parser, " a",
                TransactionMessages.MESSAGE_INVALID_DELETE_COMMAND_FORMAT, personModel);
    }

    @Test
    public void parse_validArgs_returnsDeleteNameCommand() {
        DeleteNameCommand deleteNameCommand = new DeleteNameCommand(TypicalPersons.ALICE);
        assertCommandParseWithPersonModelSuccess(parser, DESC_NAME_ALICE, deleteNameCommand, personModel);
    }

    @Test
    public void parse_invalidArgs_noSuchPersonInMembersTab() {
        assertCommandParseWithPersonModelFailure(parser, DESC_NAME_AMY,
                TransactionMessages.MESSAGE_NO_SUCH_PERSON, personModel);
    }
}
