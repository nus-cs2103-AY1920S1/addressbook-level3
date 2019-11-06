//package seedu.guilttrip.logic.parser;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertTrue;
//import static seedu.guilttrip.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
//import static seedu.guilttrip.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
//import static seedu.guilttrip.testutil.Assert.assertThrows;
//import static seedu.guilttrip.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
//
//import java.util.Arrays;
//import java.util.List;
//import java.util.stream.Collectors;
//
//import org.junit.jupiter.api.Test;
//
//import seedu.guilttrip.logic.commands.addcommands.AddCommand;
//import seedu.guilttrip.logic.commands.ClearCommand;
//import seedu.guilttrip.logic.commands.deletecommands.DeleteCommand;
//import seedu.guilttrip.logic.commands.editcommands.EditCommand;
//import seedu.guilttrip.logic.commands.editcommands.EditCommand.EditPersonDescriptor;
//import seedu.guilttrip.logic.commands.ExitCommand;
//import seedu.guilttrip.logic.commands.findcommands.FindExpenseCommand;
//import seedu.guilttrip.logic.commands.HelpCommand;
//import seedu.guilttrip.logic.commands.ListCommand;
//import seedu.guilttrip.logic.parser.exceptions.ParseException;
//import seedu.guilttrip.model.entry.predicates.entries.DescriptionContainsKeywordsPredicate;
//import seedu.guilttrip.model.entry.Person;
//import seedu.guilttrip.testutil.EditEntryDescriptorBuilder;
//import seedu.guilttrip.testutil.EntryBuilder;
//import seedu.guilttrip.testutil.PersonUtil;
//
//public class AddressBookParserTest {
//
//    private final GuiltTripParser parser = new GuiltTripParser();
//
//    @Test
//    public void parseCommand_add() throws Exception {
//        Person entry = new EntryBuilder().build();
//        AddCommand command = (AddCommand) parser.parseCommand(PersonUtil.getAddCommand(entry));
//        assertEquals(new AddCommand(entry), command);
//    }
//
//    @Test
//    public void parseCommand_clear() throws Exception {
//        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD) instanceof ClearCommand);
//        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD + " 3") instanceof ClearCommand);
//    }
//
//    @Test
//    public void parseCommand_delete() throws Exception {
//        DeleteCommand command = (DeleteCommand) parser.parseCommand(
//                DeleteCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased());
//        assertEquals(new DeleteCommand(INDEX_FIRST_PERSON), command);
//    }
//
//    @Test
//    public void parseCommand_edit() throws Exception {
//        Person entry = new EntryBuilder().build();
//        EditPersonDescriptor descriptor = new EditEntryDescriptorBuilder(entry).build();
//        EditCommand command = (EditCommand) parser.parseCommand(EditCommand.COMMAND_WORD + " "
//                + INDEX_FIRST_PERSON.getOneBased() + " " + PersonUtil.getEditPersonDescriptorDetails(descriptor));
//        assertEquals(new EditCommand(INDEX_FIRST_PERSON, descriptor), command);
//    }
//
//    @Test
//    public void parseCommand_exit() throws Exception {
//        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD) instanceof ExitCommand);
//        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD + " 3") instanceof ExitCommand);
//    }
//
//    @Test
//    public void parseCommand_find() throws Exception {
//        List<String> keywords = Arrays.asList("foo", "bar", "baz");
//        FindExpenseCommand command = (FindExpenseCommand) parser.parseCommand(
//                FindExpenseCommand.COMMAND_WORD + " " + keywords.stream().collect(Collectors.joining(" ")));
//        assertEquals(new FindExpenseCommand(new DescriptionContainsKeywordsPredicate(keywords)), command);
//    }
//
//    @Test
//    public void parseCommand_help() throws Exception {
//        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD) instanceof HelpCommand);
//        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD + " 3") instanceof HelpCommand);
//    }
//
//    @Test
//    public void parseCommand_list() throws Exception {
//        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD) instanceof ListCommand);
//        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD + " 3") instanceof ListCommand);
//    }
//
//    @Test
//    public void parseCommand_unrecognisedInput_throwsParseException() {
//        assertThrows(ParseException.class, String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE),
//        () -> parser.parseCommand(""));
//    }
//
//    @Test
//    public void parseCommand_unknownCommand_throwsParseException() {
//        assertThrows(ParseException.class, MESSAGE_UNKNOWN_COMMAND, () -> parser.parseCommand("unknownCommand"));
//    }
//}
