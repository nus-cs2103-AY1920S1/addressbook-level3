package seedu.address.transaction.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.transaction.logic.commands.CommandTestUtil.DESC_BUILDER_AMOUNT;
import static seedu.address.transaction.logic.commands.CommandTestUtil.DESC_BUILDER_CATEGORY;
import static seedu.address.transaction.logic.commands.CommandTestUtil.DESC_BUILDER_DATE;
import static seedu.address.transaction.logic.commands.CommandTestUtil.DESC_BUILDER_DESC;
import static seedu.address.transaction.logic.commands.CommandTestUtil.DESC_NAME_ALICE;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.address.person.model.CheckAndGetPersonByNameModel;
import seedu.address.person.model.ModelManager;
import seedu.address.person.model.UserPrefs;
import seedu.address.person.model.person.Person;
import seedu.address.testutil.EditTransactionDescriptorBuilder;
import seedu.address.testutil.TransactionBuilder;
import seedu.address.testutil.TypicalPersons;
import seedu.address.transaction.logic.commands.AddCommand;
import seedu.address.transaction.logic.commands.BackCommand;
import seedu.address.transaction.logic.commands.DeleteCommand;
import seedu.address.transaction.logic.commands.DeleteIndexCommand;
import seedu.address.transaction.logic.commands.DeleteNameCommand;
import seedu.address.transaction.logic.commands.EditCommand;
import seedu.address.transaction.logic.commands.FindCommand;
import seedu.address.transaction.logic.commands.SortCommand;
import seedu.address.transaction.logic.commands.SortNameCommand;
import seedu.address.transaction.model.transaction.Transaction;
import seedu.address.transaction.model.transaction.TransactionContainsKeywordsPredicate;
import seedu.address.transaction.ui.TransactionMessages;

class TransactionTabParserTest {
    private final TransactionTabParser parser = new TransactionTabParser();
    private CheckAndGetPersonByNameModel personModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void parseCommand_add() throws Exception {
        Person person = TypicalPersons.ALICE;
        Transaction transaction = new TransactionBuilder(person).build();
        AddCommand command = (AddCommand) parser.parseCommand(AddCommand.COMMAND_WORD
                        + DESC_NAME_ALICE + DESC_BUILDER_DATE + DESC_BUILDER_DESC
                        + DESC_BUILDER_CATEGORY + DESC_BUILDER_AMOUNT, personModel);
        assertEquals(new AddCommand(transaction), command);
    }


    @Test
    public void parseCommand_delete() throws Exception {
        DeleteIndexCommand command = (DeleteIndexCommand) parser.parseCommand(
                DeleteCommand.COMMAND_WORD + " " + 1, personModel);
        assertEquals(new DeleteIndexCommand(1), command);
    }

    @Test
    public void parseCommand_deletePerson() throws Exception {
        DeleteNameCommand command = (DeleteNameCommand) parser.parseCommand(
                DeleteCommand.COMMAND_WORD + DESC_NAME_ALICE, personModel);
        assertEquals(new DeleteNameCommand(TypicalPersons.ALICE), command);
    }

    @Test
    public void parseCommand_edit() throws Exception {
        EditCommand.EditTransactionDescriptor descriptor = new EditTransactionDescriptorBuilder()
                .withAmount(12.0).build();
        EditCommand command = (EditCommand) parser.parseCommand(EditCommand.COMMAND_WORD + " "
                + " 1 a/12", personModel);
        assertEquals(new EditCommand(1, descriptor), command);
    }

    @Test
    public void parseCommand_exit() throws Exception {
        assertTrue(parser.parseCommand(BackCommand.COMMAND_WORD, personModel) instanceof BackCommand);
        assertTrue(parser.parseCommand(BackCommand.COMMAND_WORD + " 3", personModel) instanceof BackCommand);
    }

    @Test
    public void parseCommand_find() throws Exception {
        List<String> keywords = Arrays.asList("foo", "bar", "baz");
        FindCommand command = (FindCommand) parser.parseCommand(
                FindCommand.COMMAND_WORD + " " + keywords.stream().collect(Collectors.joining(" ")),
                personModel);
        assertEquals(new FindCommand(new TransactionContainsKeywordsPredicate(keywords)), command);
    }

    @Test
    public void parseCommand_back() throws Exception {
        assertTrue(parser.parseCommand(BackCommand.COMMAND_WORD, personModel) instanceof BackCommand);
        assertTrue(parser.parseCommand(BackCommand.COMMAND_WORD + " 3", personModel) instanceof BackCommand);
    }

    @Test
    public void parseCommand_sort() throws Exception {
        SortNameCommand command = (SortNameCommand) parser.parseCommand(
               SortCommand.COMMAND_WORD + " name", personModel);
        assertEquals(new SortNameCommand(), command);
    }

    @Test
    public void parseCommand_unrecognisedInput_throwsParseException() {
        assertThrows(Exception.class, TransactionMessages.MESSAGE_NO_COMMAND, () ->
                parser.parseCommand("", personModel));
    }

    @Test
    public void parseCommand_unknownCommand_throwsParseException() {
        assertThrows(Exception.class, TransactionMessages.MESSAGE_NO_COMMAND, () ->
                parser.parseCommand("unknownCommand", personModel));
    }
}
