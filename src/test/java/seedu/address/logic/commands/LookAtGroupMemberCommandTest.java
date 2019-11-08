package seedu.address.logic.commands;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.ModelManager;
import seedu.address.model.display.schedulewindow.ScheduleWindowDisplayType;
import seedu.address.model.group.exceptions.DuplicateGroupException;
import seedu.address.model.mapping.exceptions.DuplicateMappingException;
import seedu.address.model.person.Person;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.testutil.TypicalPersons;
import seedu.address.testutil.grouputil.TypicalGroups;
import seedu.address.testutil.modelutil.TypicalModel;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;


public class LookAtGroupMemberCommandTest {
    private static final Person ALICE = TypicalPersons.ALICE;
    private static final Person BENSON = TypicalPersons.BENSON;
    private static final Person CARL = TypicalPersons.CARL;
    private static final Person DANIEL = TypicalPersons.DANIEL;
    private ModelManager model;
    @BeforeEach
    void init() throws DuplicateMappingException, DuplicatePersonException, DuplicateGroupException {
        model = TypicalModel.generateTypicalModel();
        model.updateScheduleWindowDisplay(TypicalGroups.GROUP_NAME3, LocalDateTime.now(),
                ScheduleWindowDisplayType.GROUP);
    }

    @Test
    public void commandFilteredAll() throws CommandException {
        LookAtGroupMemberCommand command = new LookAtGroupMemberCommand(List.of(ALICE.getName(), CARL.getName()));
        CommandResult commandResult = command.execute(model);
        String feedback = String.format(LookAtGroupMemberCommand.MESSAGE_SUCCESS,
                ALICE.getName().fullName + " " + CARL.getName().fullName);
        CommandResult expectedResult = new CommandResult(feedback, false, false,
                false, false , false, false, false, true);
        assertEquals(expectedResult, commandResult);
    }

    @Test
    public void commandFilterNothing() throws CommandException {
        LookAtGroupMemberCommand command = new LookAtGroupMemberCommand(new ArrayList<>());
        CommandResult commandResult = command.execute(model);
        String feedback = String.format(LookAtGroupMemberCommand.MESSAGE_SUCCESS, "");
        CommandResult expectedResult = new CommandResult(feedback, false, false,
                false, false , false, false, false, true);
        assertEquals(expectedResult, commandResult);
    }

    @Test
    public void commandFilterPersonNotInGroup() throws CommandException {
        LookAtGroupMemberCommand command = new LookAtGroupMemberCommand(List.of(ALICE.getName(), BENSON.getName(),
                CARL.getName()));
        CommandResult commandResult = command.execute(model);
        String feedback = String.format(LookAtGroupMemberCommand.MESSAGE_SUCCESS,
                ALICE.getName().fullName + " " + CARL.getName().fullName);
        feedback += "\n" + String.format(LookAtGroupMemberCommand.MESSAGE_NOT_FOUND, BENSON.getName().fullName);
        CommandResult expectedResult = new CommandResult(feedback, false, false,
                false, false , false, false, false, true);
        assertEquals(expectedResult, commandResult);
    }

    @Test
    public void equalsTest() throws CommandException {
        LookAtGroupMemberCommand command = new LookAtGroupMemberCommand(List.of(ALICE.getName(), BENSON.getName(),
                CARL.getName()));
        LookAtGroupMemberCommand duplicateCommand = new LookAtGroupMemberCommand(List.of(BENSON.getName(),
                CARL.getName(), ALICE.getName()));
        LookAtGroupMemberCommand differentCommand = new LookAtGroupMemberCommand(List.of(ALICE.getName(),
                BENSON.getName()));

        //Same object.
        assertEquals(command, command);
        //Same fields but in different order.
        assertEquals(command, duplicateCommand);
        //Different fields.
        assertNotEquals(command, differentCommand);
    }
}
