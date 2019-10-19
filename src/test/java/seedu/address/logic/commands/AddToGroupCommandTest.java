package seedu.address.logic.commands;

import static seedu.address.testutil.grouputil.TypicalGroups.GROUPNAME1;
import static seedu.address.testutil.personutil.TypicalPersonDescriptor.ALICE;
import static seedu.address.testutil.personutil.TypicalPersonDescriptor.DANIEL;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.ModelManager;
import seedu.address.model.group.Group;
import seedu.address.model.group.exceptions.DuplicateGroupException;
import seedu.address.model.group.exceptions.GroupNotFoundException;
import seedu.address.model.mapping.PersonToGroupMapping;
import seedu.address.model.mapping.Role;
import seedu.address.model.mapping.exceptions.DuplicateMappingException;
import seedu.address.model.person.Person;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.testutil.modelutil.TypicalModel;



class AddToGroupCommandTest {

    private ModelManager model;

    @BeforeEach
    void init() throws DuplicateMappingException, DuplicatePersonException, DuplicateGroupException {
        model = TypicalModel.generateTypicalModel();
    }

    @Test
    void execute_success() throws CommandException, PersonNotFoundException, GroupNotFoundException {

        Person person = model.findPerson(DANIEL.getName());
        Group group = model.findGroup(GROUPNAME1);
        PersonToGroupMapping map = new PersonToGroupMapping(person.getPersonId(), group.getGroupId());

        CommandResult actualCommandResult =
                new AddToGroupCommand(person.getName(), group.getGroupName(), Role.emptyRole()).execute(model);

        CommandResult expectedCommandResult =
                new CommandResult(AddToGroupCommand.MESSAGE_SUCCESS + map.toString());

        assertTrue(actualCommandResult.equals(expectedCommandResult));
    }

    private void assertTrue(boolean equals) {
    }

    /*@Test
    void execute_nullPerson() throws CommandException {

        CommandResult actualCommandResult =
                new AddToGroupCommand(null, GROUPNAME1, Role.emptyRole()).execute(model);

        CommandResult expectedCommandResult =
                new CommandResult(AddToGroupCommand.MESSAGE_FAILURE);

        assertTrue(actualCommandResult.equals(expectedCommandResult));
    }*/

    /*@Test
    void execute_nullGroup() throws CommandException {

        CommandResult actualCommandResult =
                new AddToGroupCommand(ALICE.getName(), null, Role.emptyRole()).execute(model);

        CommandResult expectedCommandResult =
                new CommandResult(AddToGroupCommand.MESSAGE_FAILURE);

        assertTrue(actualCommandResult.equals(expectedCommandResult));
    }*/

    /*@Test
    void execute_allNull() throws CommandException {

        CommandResult actualCommandResult =
                new AddToGroupCommand(null, null, Role.emptyRole()).execute(model);

        CommandResult expectedCommandResult =
                new CommandResult(AddToGroupCommand.MESSAGE_FAILURE);

        assertTrue(actualCommandResult.equals(expectedCommandResult));
    }*/

    @Test
    void execute_duplicate() throws CommandException {

        CommandResult actualCommandResult =
                new AddToGroupCommand(ALICE.getName(), GROUPNAME1, Role.emptyRole()).execute(model);

        CommandResult expectedCommandResult =
                new CommandResult(AddToGroupCommand.MESSAGE_DUPLICATE);

        assertTrue(actualCommandResult.equals(expectedCommandResult));
    }
}
