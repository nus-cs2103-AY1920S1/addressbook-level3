package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIds.ID_FIRST_MENTOR;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_MENTOR;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_TEAM;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_PARTICIPANT;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.addcommand.AddTeamCommand;
import seedu.address.logic.commands.deletecommand.DeleteMentorCommand;
import seedu.address.logic.commands.deletecommand.DeleteParticipantCommand;
import seedu.address.logic.commands.deletecommand.DeleteTeamCommand;
import seedu.address.logic.commands.editcommand.EditMentorCommand;
import seedu.address.logic.commands.editcommand.EditMentorCommand.EditMentorDescriptor;
import seedu.address.logic.commands.listcommand.ListMentorCommand;
import seedu.address.logic.commands.listcommand.ListParticipantCommand;
import seedu.address.logic.commands.listcommand.ListTeamCommand;
import seedu.address.logic.commands.viewcommand.ViewMentorCommand;
import seedu.address.logic.commands.viewcommand.ViewParticipantCommand;
import seedu.address.logic.commands.viewcommand.ViewTeamCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.entity.Mentor;
import seedu.address.model.entity.Team;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.testutil.EditMentorDescriptorBuilder;
import seedu.address.testutil.MentorBuilder;
import seedu.address.testutil.MentorUtil;
import seedu.address.testutil.TeamBuilder;
import seedu.address.testutil.TeamUtil;

public class AlfredParserTest {

    private static final String LIST_MENTOR_KEYWORD = "mentors";
    private static final String LIST_PARTICIPANT_KEYWORD = "participants";
    private static final String LIST_TEAM_KEYWORD = "teams";

    private final AlfredParser parser = new AlfredParser();

    @Disabled
    @Test
    public void parseCommand_add() throws Exception {
        // Testing adding a new team.
        Team team = new TeamBuilder().withScore(0).build();
        AddTeamCommand addTeamCommand = (AddTeamCommand) parser.parseCommand(TeamUtil.getAddCommand(team));
        assertEquals(new AddTeamCommand(team), addTeamCommand);
    }

    @Test
    public void parseCommand_clear() throws Exception {
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD) instanceof ClearCommand);
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD + " 3") instanceof ClearCommand);
    }

    @Test
    public void parseCommand_delete() throws Exception {
        // Checking if delete team is called appropriately.
        DeleteTeamCommand deleteTeamCommand = (DeleteTeamCommand) parser.parseCommand(
                DeleteTeamCommand.COMMAND_WORD + " team " + INDEX_FIRST_TEAM.toString());
        assertEquals(new DeleteTeamCommand(INDEX_FIRST_TEAM), deleteTeamCommand);

        // Checking if delete participant is called appropriately.
        DeleteParticipantCommand deleteParticipantCommand = (DeleteParticipantCommand) parser.parseCommand(
                DeleteParticipantCommand.COMMAND_WORD + " participant " + INDEX_THIRD_PARTICIPANT.toString());
        assertEquals(new DeleteParticipantCommand(INDEX_THIRD_PARTICIPANT), deleteParticipantCommand);

        // Checking if delete mentor is called appropriately.
        DeleteMentorCommand deleteMentorCommand = (DeleteMentorCommand) parser.parseCommand(
                DeleteMentorCommand.COMMAND_WORD + " mentor " + INDEX_FIRST_MENTOR.toString());
        assertEquals(new DeleteMentorCommand(INDEX_FIRST_MENTOR), deleteMentorCommand);
    }

    @Test
    public void parseCommand_edit() throws Exception {
        // Checking if parse mentor command is called appropriately.
        Mentor mentor = new MentorBuilder().build();
        EditMentorDescriptor mentorDescriptor = new EditMentorDescriptorBuilder(mentor).build();
        EditMentorCommand command = (EditMentorCommand) parser
                .parseCommand(EditMentorCommand.COMMAND_WORD + " mentor " + mentor.getId().toString()
                + " " + MentorUtil.getEditMentorDescriptorDetails(mentorDescriptor));

        assertEquals(new EditMentorCommand(ID_FIRST_MENTOR, mentorDescriptor), command);
    }

    @Test
    public void parseCommand_exit() throws Exception {
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD) instanceof ExitCommand);
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD + " 3") instanceof ExitCommand);
    }

    @Disabled
    @Test
    public void parseCommand_find() throws Exception {
        List<String> keywords = Arrays.asList("foo", "bar", "baz");
        FindCommand command = (FindCommand) parser.parseCommand(
                FindCommand.COMMAND_WORD + " " + keywords.stream().collect(Collectors.joining(" ")));
        assertEquals(new FindCommand(new NameContainsKeywordsPredicate(keywords)), command);
    }

    @Test
    public void parseCommand_help() throws Exception {
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD) instanceof HelpCommand);
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD + " 3") instanceof HelpCommand);
    }

    @Test
    public void parseCommand_list() throws Exception {
        assertTrue(parser.parseCommand(ListMentorCommand.COMMAND_WORD + " "
                + LIST_MENTOR_KEYWORD) instanceof ListMentorCommand);

        assertTrue(parser.parseCommand(ListTeamCommand.COMMAND_WORD + " "
                + LIST_TEAM_KEYWORD) instanceof ListTeamCommand);

        assertTrue(parser.parseCommand(ListParticipantCommand.COMMAND_WORD + " "
                + LIST_PARTICIPANT_KEYWORD) instanceof ListParticipantCommand);
    }

    @Test
    public void parseCommand_view() throws ParseException {
        ViewTeamCommand viewTeam = new ViewTeamCommand(INDEX_FIRST_TEAM);
        ViewMentorCommand viewMentor = new ViewMentorCommand(INDEX_FIRST_MENTOR);
        ViewParticipantCommand viewParticipant = new ViewParticipantCommand(INDEX_THIRD_PARTICIPANT);

        assertEquals(viewTeam, parser.parseCommand(ViewTeamCommand.COMMAND_WORD
                + " " + INDEX_FIRST_TEAM.toString()));
        assertEquals(viewMentor, parser.parseCommand(ViewMentorCommand.COMMAND_WORD
                + " " + INDEX_FIRST_MENTOR.toString()));
        assertEquals(viewParticipant, parser.parseCommand(ViewParticipantCommand.COMMAND_WORD
                + " " + INDEX_THIRD_PARTICIPANT.toString()));
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
