package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.logic.parser.exceptions.ParseException;

//import seedu.address.logic.commands.CreateStudyPlanCommand;
//import seedu.address.logic.commands.ClearCommand;
//import seedu.address.logic.commands.DeleteCommand;
//import seedu.address.logic.commands.FindModuleCommand;
//import seedu.address.logic.commands.HelpCommand;
//import seedu.address.testutil.StudyPlanUtil;

public class ModulePlannerParserTest {

    // TODO modify tests

    private final ModulePlannerParser parser = new ModulePlannerParser();

    /*
    @Test
    public void parseCommand_add() throws Exception {
        StudyPlan studyPlan = new StudyPlanBuilder().build();
        CreateStudyPlanCommand command =
                (CreateStudyPlanCommand) parser.parseCommand(StudyPlanUtil.getCreateStudyPlanCommand(studyPlan));
        assertEquals(new CreateStudyPlanCommand(studyPlan), command);
    }
     */

    /*
    @Test
    public void parseCommand_clear() throws Exception {
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD) instanceof ClearCommand);
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD + " 3") instanceof ClearCommand);
    }

    @Test
    public void parseCommand_delete() throws Exception {
        DeleteCommand command = (DeleteCommand) parser.parseCommand(
                DeleteCommand.COMMAND_WORD + " " + INDEX_FIRST_STUDYPLAN.getOneBased());
        assertEquals(new DeleteCommand(INDEX_FIRST_STUDYPLAN), command);
    }
    */
    /*
    @Test
    public void parseCommand_edit() throws Exception {
        StudyPlan studyPlan = new StudyPlanBuilder().build();
        //EditStudyPlanDescriptor descriptor = new EditStudyPlanDescriptorBuilder(studyPlan).build();
        //EditCommand command = (EditCommand) parser.parseCommand(EditCommand.COMMAND_WORD + " "
        //       + INDEX_FIRST_STUDYPLAN.getOneBased() + " "
        //              + StudyPlanUtil.getEditStudyPlanDescriptorDetails(descriptor));
        //assertEquals(new EditCommand(INDEX_FIRST_STUDYPLAN, descriptor), command);
    }

    @Test
    public void parseCommand_exit() throws Exception {
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD) instanceof ExitCommand);
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD + " 3") instanceof ExitCommand);
    }
    */
    /*
    @Test
    public void parseCommand_find() throws Exception {
        List<String> keywords = Arrays.asList("foo", "bar", "baz");
        FindModuleCommand command = (FindModuleCommand) parser.parseCommand(
                FindModuleCommand.COMMAND_WORD + " " + keywords.stream().collect(Collectors.joining(" ")));
        assertEquals(new FindModuleCommand(new NameContainsKeywordsPredicate(keywords)), command);
    }

    @Test
    public void parseCommand_help() throws Exception {
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD) instanceof HelpCommand);
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD + " 3") instanceof HelpCommand);
    }
    @Test
    public void parseCommand_list() throws Exception {
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD) instanceof ListCommand);
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD + " 3") instanceof ListCommand);
    }
    */
    /*
    @Test
    public void parseCommand_unrecognisedInput_throwsParseException() {
        assertThrows(ParseException.class, String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE), ()
            -> parser.parseCommand(""));
    }
    */

    @Test
    public void parseCommand_unknownCommand_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_UNKNOWN_COMMAND, () -> parser.parseCommand("unknownCommand"));
    }
}
