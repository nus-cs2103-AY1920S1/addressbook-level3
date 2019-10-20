package seedu.address.transaction.logic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.transaction.commands.CommandTestUtil.DESC_BUILDER_AMOUNT;
import static seedu.address.transaction.commands.CommandTestUtil.DESC_BUILDER_CATEGORY;
import static seedu.address.transaction.commands.CommandTestUtil.DESC_BUILDER_DATE;
import static seedu.address.transaction.commands.CommandTestUtil.DESC_BUILDER_DESC;
import static seedu.address.transaction.commands.CommandTestUtil.DESC_NAME_ALICE;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import seedu.address.person.model.Model;
import seedu.address.person.model.ModelManager;
import seedu.address.person.model.UserPrefs;
import seedu.address.person.model.person.Person;
import seedu.address.testutil.EditTransactionDescriptorBuilder;
import seedu.address.testutil.TransactionBuilder;
import seedu.address.testutil.TypicalPersons;
import seedu.address.transaction.commands.AddCommand;
import seedu.address.transaction.commands.BackCommand;
import seedu.address.transaction.commands.DeleteCommand;
import seedu.address.transaction.commands.DeleteIndexCommand;
import seedu.address.transaction.commands.DeleteNameCommand;
import seedu.address.transaction.commands.EditCommand;
import seedu.address.transaction.commands.ExitCommand;
import seedu.address.transaction.commands.FindCommand;
import seedu.address.transaction.commands.SortCommand;
import seedu.address.transaction.commands.SortNameCommand;
import seedu.address.transaction.model.Transaction;
import seedu.address.transaction.model.TransactionContainsKeywordsPredicate;
import seedu.address.transaction.ui.TransactionMessages;

import org.junit.jupiter.api.Test;

class TransactionTabParserTest {
    private final TransactionTabParser parser = new TransactionTabParser();
    private Model personModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void parseCommand_add() throws Exception {
        Person person = TypicalPersons.ALICE;
        Transaction transaction = new TransactionBuilder(person).build();
        AddCommand command = (AddCommand) parser.parseCommand(AddCommand.COMMAND_WORD
                        + DESC_NAME_ALICE + DESC_BUILDER_DATE + DESC_BUILDER_DESC
                        + DESC_BUILDER_CATEGORY + DESC_BUILDER_AMOUNT,
                personModel.getFilteredPersonList().size(), personModel);
        assertEquals(new AddCommand(transaction), command);
    }


    @Test
    public void parseCommand_delete() throws Exception {
        DeleteIndexCommand command = (DeleteIndexCommand) parser.parseCommand(
                DeleteCommand.COMMAND_WORD + " " + 1, personModel.getFilteredPersonList().size(), personModel);
        assertEquals(new DeleteIndexCommand(1), command);
    }

    @Test
    public void parseCommand_deletePerson() throws Exception {
        DeleteNameCommand command = (DeleteNameCommand) parser.parseCommand(
                DeleteCommand.COMMAND_WORD + DESC_NAME_ALICE, personModel.getFilteredPersonList().size(), personModel);
        assertEquals(new DeleteNameCommand(TypicalPersons.ALICE), command);
    }

    @Test
    public void parseCommand_edit() throws Exception {
        EditCommand.EditTransactionDescriptor descriptor = new EditTransactionDescriptorBuilder()
                .withAmount(12.0).build();
        EditCommand command = (EditCommand) parser.parseCommand(EditCommand.COMMAND_WORD + " "
                + " 1 a/12", personModel.getFilteredPersonList().size(), personModel);
        assertEquals(new EditCommand(1, descriptor), command);
    }

    @Test
    public void parseCommand_exit() throws Exception {
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD, personModel.getFilteredPersonList().size(),
                personModel) instanceof ExitCommand);
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD + " 3",
                personModel.getFilteredPersonList().size(), personModel) instanceof ExitCommand);
    }

    @Test
    public void parseCommand_find() throws Exception {
        List<String> keywords = Arrays.asList("foo", "bar", "baz");
        FindCommand command = (FindCommand) parser.parseCommand(
                FindCommand.COMMAND_WORD + " " + keywords.stream().collect(Collectors.joining(" ")),
                personModel.getFilteredPersonList().size(), personModel);
        assertEquals(new FindCommand(new TransactionContainsKeywordsPredicate(keywords)), command);
    }

    @Test
    public void parseCommand_back() throws Exception {
        assertTrue(parser.parseCommand(BackCommand.COMMAND_WORD,
                personModel.getFilteredPersonList().size(), personModel) instanceof BackCommand);
        assertTrue(parser.parseCommand(BackCommand.COMMAND_WORD + " 3",
                personModel.getFilteredPersonList().size(), personModel) instanceof BackCommand);
    }

   @Test
    public void parseCommand_sort() throws Exception {
       SortNameCommand command = (SortNameCommand) parser.parseCommand(
               SortCommand.COMMAND_WORD + " name", personModel.getFilteredPersonList().size(), personModel);
       assertEquals(new SortNameCommand(), command);
    }

    @Test
    public void parseCommand_unrecognisedInput_throwsParseException() {
        assertThrows(Exception.class, TransactionMessages.MESSAGE_INVALID_ADD_COMMAND_FORMAT, ()
                -> parser.parseCommand("", personModel.getFilteredPersonList().size(), personModel));
    }

    @Test
    public void parseCommand_unknownCommand_throwsParseException() {
        assertThrows(Exception.class, TransactionMessages.MESSAGE_NO_SUCH_COMMAND,
                () -> parser.parseCommand("unknownCommand", personModel.getFilteredPersonList().size(),
                        personModel));
    }
}
