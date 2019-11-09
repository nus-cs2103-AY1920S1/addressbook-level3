package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EDIT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENTNAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GROUPNAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMARK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ROLE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TIMING;
import static seedu.address.logic.parser.CliSyntax.PREFIX_WEEK;
import static seedu.address.testutil.grouputil.TypicalGroups.GROUP0;
import static seedu.address.testutil.grouputil.TypicalGroups.GROUP1;
import static seedu.address.testutil.grouputil.TypicalGroups.GROUP_DESCRIPTION0;
import static seedu.address.testutil.grouputil.TypicalGroups.GROUP_DESCRIPTION1;
import static seedu.address.testutil.grouputil.TypicalGroups.GROUP_NAME0;
import static seedu.address.testutil.grouputil.TypicalGroups.GROUP_NAME1;
import static seedu.address.testutil.grouputil.TypicalGroups.GROUP_ROLE0;
import static seedu.address.testutil.grouputil.TypicalGroups.GROUP_ROLE1;
import static seedu.address.testutil.personutil.TypicalPersonDescriptor.ALICE;
import static seedu.address.testutil.personutil.TypicalPersonDescriptor.WHITESPACE;
import static seedu.address.testutil.personutil.TypicalPersonDescriptor.ZACK;
import static seedu.address.testutil.scheduleutil.TypicalEvents.EVENT_NAME1;
import static seedu.address.testutil.scheduleutil.TypicalTimeslots.TIMING1;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.AddEventCommand;
import seedu.address.logic.commands.AddGroupCommand;
import seedu.address.logic.commands.AddPersonCommand;
import seedu.address.logic.commands.AddToGroupCommand;
import seedu.address.logic.commands.DeleteEventCommand;
import seedu.address.logic.commands.DeleteFromGroupCommand;
import seedu.address.logic.commands.DeleteGroupCommand;
import seedu.address.logic.commands.DeletePersonCommand;
import seedu.address.logic.commands.EditGroupCommand;
import seedu.address.logic.commands.EditPersonCommand;
import seedu.address.logic.commands.EditUserCommand;
import seedu.address.logic.commands.PopupCommand;
import seedu.address.logic.commands.ScheduleCommand;
import seedu.address.logic.commands.SelectCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.testutil.scheduleutil.TypicalEvents;

class TimeBookParserTest {

    private final TimeBookParser parser = new TimeBookParser();

    @Test
    void parseCommand_addPerson() throws ParseException {
        AddPersonCommand command = (AddPersonCommand) parser.parseCommand(
                AddPersonCommand.COMMAND_WORD
                        + WHITESPACE + PREFIX_NAME + ALICE.getName().toString()
                        + WHITESPACE + PREFIX_PHONE + ALICE.getPhone().toString()
                        + WHITESPACE + PREFIX_ADDRESS + ALICE.getAddress().toString()
                        + WHITESPACE + PREFIX_EMAIL + ALICE.getEmail().toString()
                        + WHITESPACE + PREFIX_REMARK + ALICE.getRemark().toString()
                        + WHITESPACE + PREFIX_TAG + "friends"
        );
        assertTrue(command.equals(new AddPersonCommand(ALICE)));
    }

    @Test
    void parseCommand_editPerson() throws ParseException {
        EditPersonCommand command = (EditPersonCommand) parser.parseCommand(
                EditPersonCommand.COMMAND_WORD
                        + WHITESPACE + PREFIX_EDIT + ALICE.getName().toString() + WHITESPACE
                        + PREFIX_NAME + ZACK.getName().toString() + WHITESPACE
                        + PREFIX_PHONE + ZACK.getPhone().toString() + WHITESPACE
                        + PREFIX_EMAIL + ZACK.getEmail().toString() + WHITESPACE
                        + PREFIX_ADDRESS + ZACK.getAddress().toString() + WHITESPACE
                        + PREFIX_REMARK + ZACK.getRemark().toString() + WHITESPACE
                        + PREFIX_TAG + WHITESPACE
        );
        assertTrue(command.equals(new EditPersonCommand(ALICE.getName(), ZACK)));
    }

    @Test
    void parseCommand_addEvent() throws ParseException {
        AddEventCommand command = (AddEventCommand) parser.parseCommand(
                AddEventCommand.COMMAND_WORD
                        + WHITESPACE + PREFIX_NAME + ALICE.getName().toString() + WHITESPACE
                        + PREFIX_EVENTNAME + EVENT_NAME1 + WHITESPACE
                        + PREFIX_TIMING + TIMING1
        );
        assertTrue(command.equals(new AddEventCommand(ALICE.getName(), TypicalEvents.generateTypicalEvent1())));
    }

    @Test
    void parseCommand_addGroup() throws ParseException {
        AddGroupCommand command = (AddGroupCommand) parser.parseCommand(
                AddGroupCommand.COMMAND_WORD
                        + WHITESPACE + PREFIX_GROUPNAME + GROUP_NAME1.toString()
                        + WHITESPACE + PREFIX_DESCRIPTION + GROUP_DESCRIPTION1.toString()
                        + WHITESPACE + PREFIX_ROLE + GROUP_ROLE1
        );
        assertTrue(command.equals(new AddGroupCommand(GROUP1)));
    }

    @Test
    void parseCommand_addToGroup() throws ParseException {
        AddToGroupCommand command = (AddToGroupCommand) parser.parseCommand(
                AddToGroupCommand.COMMAND_WORD
                        + WHITESPACE + PREFIX_NAME + ALICE.getName().toString()
                        + WHITESPACE + PREFIX_GROUPNAME + GROUP_NAME1.toString()
                        + WHITESPACE + PREFIX_ROLE + GROUP_ROLE1
        );
        assertTrue(command.equals(new AddToGroupCommand(ALICE.getName(), GROUP_NAME1, GROUP_ROLE1)));
    }

    @Test
    void parseCommand_deleteEvent() throws ParseException {
        DeleteEventCommand command = (DeleteEventCommand) parser.parseCommand(
                DeleteEventCommand.COMMAND_WORD
                        + WHITESPACE + PREFIX_NAME + ALICE.getName().toString()
                        + WHITESPACE + PREFIX_EVENTNAME + EVENT_NAME1
        );
        assertTrue(command.equals(new DeleteEventCommand(ALICE.getName(), EVENT_NAME1)));
    }

    @Test
    void parseCommand_deleteFromGroup() throws ParseException {
        DeleteFromGroupCommand command = (DeleteFromGroupCommand) parser.parseCommand(
                DeleteFromGroupCommand.COMMAND_WORD
                        + WHITESPACE + PREFIX_NAME + ALICE.getName().toString()
                        + WHITESPACE + PREFIX_GROUPNAME + GROUP_NAME1
        );
        assertTrue(command.equals(new DeleteFromGroupCommand(ALICE.getName(), GROUP_NAME1)));
    }

    @Test
    void parseCommand_deleteGroup() throws ParseException {
        DeleteGroupCommand command = (DeleteGroupCommand) parser.parseCommand(
                DeleteGroupCommand.COMMAND_WORD
                        + WHITESPACE + PREFIX_GROUPNAME + GROUP_NAME1.toString()
        );
        assertTrue(command.equals(new DeleteGroupCommand(GROUP_NAME1)));
    }

    @Test
    void parseCommand_deletePerson() throws ParseException {
        DeletePersonCommand command = (DeletePersonCommand) parser.parseCommand(
                DeletePersonCommand.COMMAND_WORD
                        + WHITESPACE + PREFIX_NAME + WHITESPACE + ALICE.getName().toString()
        );

        assertTrue(command.equals(new DeletePersonCommand(ALICE.getName())));
    }

    @Test
    void parseCommand_editGroup() throws ParseException {
        EditGroupCommand command = (EditGroupCommand) parser.parseCommand(
                EditGroupCommand.COMMAND_WORD
                        + WHITESPACE + PREFIX_EDIT + GROUP_NAME1.toString() + WHITESPACE
                        + PREFIX_GROUPNAME + GROUP_NAME0 + WHITESPACE
                        + PREFIX_DESCRIPTION + GROUP_DESCRIPTION0 + WHITESPACE
                        + PREFIX_ROLE + GROUP_ROLE0
        );

        assertTrue(command.equals(new EditGroupCommand(GROUP_NAME1, GROUP0)));
    }

    @Test
    void parseCommand_editUser() throws ParseException {
        EditUserCommand command = (EditUserCommand) parser.parseCommand(
                EditUserCommand.COMMAND_WORD
                        + WHITESPACE + PREFIX_NAME + ZACK.getName().toString() + WHITESPACE
                        + PREFIX_PHONE + ZACK.getPhone().toString() + WHITESPACE
                        + PREFIX_EMAIL + ZACK.getEmail().toString() + WHITESPACE
                        + PREFIX_ADDRESS + ZACK.getAddress().toString() + WHITESPACE
                        + PREFIX_REMARK + ZACK.getRemark().toString() + WHITESPACE
                        + PREFIX_TAG + WHITESPACE
        );

        assertTrue(command.equals(new EditUserCommand(ZACK)));
    }

    @Test
    void parseCommand_popup() throws ParseException {
        PopupCommand command = (PopupCommand) parser.parseCommand(
                PopupCommand.COMMAND_WORD
                        + WHITESPACE + PREFIX_ID + 1
        );

        assertTrue(command.equals(new PopupCommand(0, 1)));
    }

    @Test
    void parseCommand_schedule() throws ParseException {
        ScheduleCommand command = (ScheduleCommand) parser.parseCommand(
                ScheduleCommand.COMMAND_WORD
                        + WHITESPACE + PREFIX_NAME + ALICE.getName().toString()
        );

        assertTrue(command.equals(new ScheduleCommand(new ArrayList<>(List.of(ALICE.getName())))));
    }

    @Test
    void parseCommand_select() throws ParseException {
        SelectCommand command = (SelectCommand) parser.parseCommand(
                SelectCommand.COMMAND_WORD
                        + WHITESPACE + PREFIX_NAME + ALICE.getName().toString()
                        + WHITESPACE + PREFIX_WEEK + "1"
                        + WHITESPACE + PREFIX_ID + "1"
        );

        assertTrue(command.equals(new SelectCommand(0, ALICE.getName(), 1)));
    }

    @Test
    void parseCommand_failure() {
        assertThrows(ParseException.class, () ->
                parser.parseCommand(WHITESPACE));
    }
}
