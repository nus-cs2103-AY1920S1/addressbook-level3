package cs.f10.t1.nursetraverse.logic.parser;

import static cs.f10.t1.nursetraverse.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static cs.f10.t1.nursetraverse.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import cs.f10.t1.nursetraverse.logic.commands.AddCommand;
import cs.f10.t1.nursetraverse.logic.commands.ClearCommand;
import cs.f10.t1.nursetraverse.logic.commands.DeleteCommand;
import cs.f10.t1.nursetraverse.logic.commands.EditCommand;
import cs.f10.t1.nursetraverse.logic.commands.ExitCommand;
import cs.f10.t1.nursetraverse.logic.commands.FindCommand;
import cs.f10.t1.nursetraverse.logic.commands.HelpCommand;
import cs.f10.t1.nursetraverse.logic.commands.ListCommand;
import cs.f10.t1.nursetraverse.logic.commands.visit.BeginVisitCommand;
import cs.f10.t1.nursetraverse.logic.commands.visit.CancelOngoingVisitCommand;
import cs.f10.t1.nursetraverse.logic.commands.visit.FinishOngoingVisitCommand;
import cs.f10.t1.nursetraverse.logic.parser.exceptions.ParseException;
import cs.f10.t1.nursetraverse.model.ModelState;
import cs.f10.t1.nursetraverse.model.patient.NameContainsKeywordsPredicate;
import cs.f10.t1.nursetraverse.model.patient.Patient;
import cs.f10.t1.nursetraverse.testutil.Assert;
import cs.f10.t1.nursetraverse.testutil.EditPatientDescriptorBuilder;
import cs.f10.t1.nursetraverse.testutil.PatientBuilder;
import cs.f10.t1.nursetraverse.testutil.PatientUtil;
import cs.f10.t1.nursetraverse.testutil.TypicalIndexes;

public class PatientBookParserTest {

    private final PatientBookParser parser = new PatientBookParser();

    @Test
    public void parseCommand_add() throws Exception {
        Patient patient = new PatientBuilder().build();
        AddCommand command = (AddCommand) parser.parseCommand(PatientUtil.getAddCommand(patient), ModelState.NORMAL);
        assertEquals(new AddCommand(patient), command);
    }

    @Test
    public void parseCommand_clear() throws Exception {
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD, ModelState.NORMAL) instanceof ClearCommand);
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD + " 3", ModelState.NORMAL)
                instanceof ClearCommand);
    }

    @Test
    public void parseCommand_delete() throws Exception {
        DeleteCommand command = (DeleteCommand) parser.parseCommand(
                DeleteCommand.COMMAND_WORD + " " + TypicalIndexes.INDEX_FIRST_PATIENT.getOneBased(),
                ModelState.NORMAL);
        assertEquals(new DeleteCommand(TypicalIndexes.INDEX_FIRST_PATIENT), command);
    }

    @Test
    public void parseCommand_edit() throws Exception {
        Patient patient = new PatientBuilder().build();
        EditCommand.EditPatientDescriptor descriptor = new EditPatientDescriptorBuilder(patient).build();
        EditCommand command = (EditCommand) parser.parseCommand(EditCommand.COMMAND_WORD + " "
                + TypicalIndexes.INDEX_FIRST_PATIENT.getOneBased() + " "
                + PatientUtil.getEditPatientDescriptorDetails(descriptor), ModelState.NORMAL);
        assertEquals(new EditCommand(TypicalIndexes.INDEX_FIRST_PATIENT, descriptor), command);
    }

    @Test
    public void parseCommand_exit() throws Exception {
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD, ModelState.NORMAL) instanceof ExitCommand);
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD + " 3", ModelState.NORMAL)
                instanceof ExitCommand);
    }

    @Test
    public void parseCommand_find() throws Exception {
        List<String> keywords = Arrays.asList("foo", "bar", "baz");
        FindCommand command = (FindCommand) parser.parseCommand(
                FindCommand.COMMAND_WORD + " " + keywords.stream()
                        .collect(Collectors.joining(" ")), ModelState.NORMAL);
        assertEquals(new FindCommand(new NameContainsKeywordsPredicate(keywords)), command);
    }

    @Test
    public void parseCommand_help() throws Exception {
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD, ModelState.NORMAL) instanceof HelpCommand);
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD + " 3", ModelState.NORMAL)
                instanceof HelpCommand);
    }

    @Test
    public void parseCommand_list() throws Exception {
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD, ModelState.NORMAL)
                instanceof ListCommand);
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD + " 3", ModelState.NORMAL)
                instanceof ListCommand);
    }

    @Test
    public void parseCommand_beginVisit() throws Exception {
        BeginVisitCommand command = (BeginVisitCommand) parser.parseCommand(
                BeginVisitCommand.COMMAND_WORD + " " + TypicalIndexes.INDEX_FIRST_PATIENT.getOneBased(),
                ModelState.NORMAL);
        assertEquals(new BeginVisitCommand(TypicalIndexes.INDEX_FIRST_PATIENT), command);
    }

    @Test
    public void parseCommand_cancelOngoingVisit() throws Exception {
        assertTrue(parser.parseCommand(CancelOngoingVisitCommand.COMMAND_WORD, ModelState.VISIT_ONGOING)
                instanceof CancelOngoingVisitCommand);
        assertTrue(parser.parseCommand(CancelOngoingVisitCommand.COMMAND_WORD + " 3",
                ModelState.VISIT_ONGOING) instanceof CancelOngoingVisitCommand);
    }

    @Test
    public void parseCommand_finishOngoingVisit() throws Exception {
        assertTrue(parser.parseCommand(FinishOngoingVisitCommand.COMMAND_WORD, ModelState.VISIT_ONGOING)
                instanceof FinishOngoingVisitCommand);
        assertTrue(parser.parseCommand(FinishOngoingVisitCommand.COMMAND_WORD + " 3",
                ModelState.VISIT_ONGOING) instanceof FinishOngoingVisitCommand);
    }

    @Test
    public void parseCommand_unrecognisedInput_throwsParseException() {
        Assert.assertThrows(ParseException.class, String
                .format(MESSAGE_INVALID_COMMAND_FORMAT,
                        HelpCommand.MESSAGE_USAGE), () -> parser.parseCommand("", ModelState.NORMAL));
    }

    @Test
    public void parseCommand_unknownCommand_throwsParseException() {
        Assert.assertThrows(ParseException.class, MESSAGE_UNKNOWN_COMMAND, () -> parser
                .parseCommand("unknownCommand", ModelState.NORMAL));
    }
}
