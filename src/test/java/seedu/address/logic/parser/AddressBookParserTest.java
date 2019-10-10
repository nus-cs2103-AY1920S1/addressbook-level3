package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FLAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_IDENTIFICATION_NUMBER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SEX;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIdentificationNumbers.FIRST_BODY_ID_NUM;
import static seedu.address.testutil.TypicalIdentificationNumbers.FIRST_WORKER_ID_NUM;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.ListBodyCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.ListWorkerCommand;
import seedu.address.logic.commands.UpdateCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.entity.NameContainsKeywordsPredicate;
import seedu.address.logic.parser.utility.UpdateBodyDescriptor;
import seedu.address.model.entity.Sex;
import seedu.address.model.entity.body.Body;
import seedu.address.model.person.Person;
import seedu.address.testutil.BodyBuilder;
import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.PersonUtil;

public class AddressBookParserTest {

    private static final String PREFIX_FLAG = "-";
    private static final String BODY_FLAG = "b";
    private static final String WORKER_FLAG = "w";

    private final AddressBookParser parser = new AddressBookParser();

    @Test
    public void parseCommand_add() throws Exception {
        Person person = new PersonBuilder().build();
        AddCommand command = (AddCommand) parser.parseCommand(PersonUtil.getAddCommand(person));
        assertEquals(new AddCommand(person), command);
    }

    @Test
    public void parseCommand_clear() throws Exception {
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD) instanceof ClearCommand);
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD + " 3") instanceof ClearCommand);
    }

    @Test
    public void parseCommand_delete() throws Exception {
        DeleteCommand bodyCommand = (DeleteCommand) parser.parseCommand(
                DeleteCommand.COMMAND_WORD + " " + FIRST_BODY_ID_NUM.toString());
        assertEquals(new DeleteCommand(FIRST_BODY_ID_NUM), bodyCommand);

        DeleteCommand workerCommand = (DeleteCommand) parser.parseCommand(
                DeleteCommand.COMMAND_WORD + " " + FIRST_WORKER_ID_NUM.toString());
        assertEquals(new DeleteCommand(FIRST_WORKER_ID_NUM), workerCommand);

        // todo add parser test for fridge class
    }
    /*
    @Test
    public void parseCommand_edit() throws Exception {
        Person person = new PersonBuilder().build();
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder(person).build();
        UpdateCommand command = (UpdateCommand) parser.parseCommand(UpdateCommand.COMMAND_WORD + " "
                + INDEX_FIRST_PERSON.getOneBased() + " " + PersonUtil.getEditPersonDescriptorDetails(descriptor));
        assertEquals(new UpdateCommand(INDEX_FIRST_PERSON, descriptor), command);
    }

    */
    //@@author ambervoong
    @Test
    public void parseCommand_update() throws Exception {
        Body body = new BodyBuilder().build();
        UpdateBodyDescriptor descriptor = new UpdateBodyDescriptor();
        descriptor.setSex(Sex.MALE);
        UpdateCommand command = (UpdateCommand) parser.parseCommand(UpdateCommand.COMMAND_WORD + " "
                + PREFIX_FLAG + "b "
                + PREFIX_IDENTIFICATION_NUMBER + " 1 "
                + PREFIX_SEX + " male");
        assertEquals(new UpdateCommand(body.getBodyIdNum(), descriptor), command);
    }
    //@@author

    @Test
    public void parseCommand_exit() throws Exception {
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD) instanceof ExitCommand);
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD + " 3") instanceof ExitCommand);
    }

    @Test
    public void parseCommand_find() throws Exception {
        List<String> keywords = Arrays.asList("foo", "bar", "baz");
        FindCommand command1 = (FindCommand) parser.parseCommand(
                FindCommand.COMMAND_WORD + " " + PREFIX_FLAG + BODY_FLAG + " "
                        + keywords.stream().collect(Collectors.joining(" ")));
        assertEquals(new FindCommand(new NameContainsKeywordsPredicate(keywords), BODY_FLAG), command1);

        FindCommand command2 = (FindCommand) parser.parseCommand(
                FindCommand.COMMAND_WORD + " " + PREFIX_FLAG + WORKER_FLAG + " "
                        + keywords.stream().collect(Collectors.joining(" ")));
        assertEquals(new FindCommand(new NameContainsKeywordsPredicate(keywords), WORKER_FLAG), command2);
    }

    @Test
    public void parseCommand_help() throws Exception {
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD) instanceof HelpCommand);
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD + " 3") instanceof HelpCommand);
    }

    @Test
    public void parseCommand_list() throws Exception {
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD + " -b") instanceof ListBodyCommand);
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD + " -w") instanceof ListWorkerCommand);
        //assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD + " -f") instanceof ListFridgeCommand);
    }

    @Test
    public void parseCommand_unrecognisedInput_throwsParseException() {
        assertThrows(ParseException.class, String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE), ()
            -> parser.parseCommand(""));
    }

    @Test
    public void parseCommand_unknownCommand_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_UNKNOWN_COMMAND, () -> parser.parseCommand("unknownCommand"));
    }
}
