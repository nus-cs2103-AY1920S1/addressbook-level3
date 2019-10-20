package seedu.address.transaction.logic;

import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.transaction.commands.CommandTestUtil.DESC_NAME_ALICE;
import static seedu.address.transaction.commands.CommandTestUtil.DESC_NAME_AMY;
import static seedu.address.transaction.logic.CommandParserTestUtil.assertDeleteCommandParseFailure;
import static seedu.address.transaction.logic.CommandParserTestUtil.assertDeleteCommandParseSuccess;

import seedu.address.person.model.Model;
import seedu.address.person.model.ModelManager;
import seedu.address.person.model.UserPrefs;
import seedu.address.testutil.TypicalPersons;
import seedu.address.transaction.commands.DeleteIndexCommand;
import seedu.address.transaction.commands.DeleteNameCommand;
import seedu.address.transaction.ui.TransactionMessages;

import org.junit.jupiter.api.Test;

class DeleteCommandParserTest {
    private DeleteCommandParser parser = new DeleteCommandParser();
    private Model personModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void parse_validArgsWithinBounds_returnsDeleteIndexCommand() {
        DeleteIndexCommand deleteIndexCommand = new DeleteIndexCommand(1);
        assertDeleteCommandParseSuccess(parser, " 1", deleteIndexCommand, personModel);
    }

    @Test
    public void parse_invalidArgs_NotANumberAndNoPersonPrefix() {
        assertDeleteCommandParseFailure(parser, " a",
                TransactionMessages.MESSAGE_INVALID_DELETE_COMMAND_FORMAT, personModel);
    }

    @Test
    public void parse_validArgs_returnsDeleteNameCommand() {
        DeleteNameCommand deleteNameCommand = new DeleteNameCommand(TypicalPersons.ALICE);
        assertDeleteCommandParseSuccess(parser, DESC_NAME_ALICE, deleteNameCommand, personModel);
    }

    @Test
    public void parse_invalidArgs_NoSuchPersonInAB() {
        assertDeleteCommandParseFailure(parser, DESC_NAME_AMY,
                TransactionMessages.MESSAGE_NO_SUCH_PERSON, personModel);
    }
}
