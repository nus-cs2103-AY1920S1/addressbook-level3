package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.personutil.TypicalPersonDescriptor.ALICE;
import static seedu.address.testutil.personutil.TypicalPersonDescriptor.ZACK;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.ModelManager;
import seedu.address.model.group.exceptions.DuplicateGroupException;
import seedu.address.model.mapping.exceptions.DuplicateMappingException;
import seedu.address.model.module.NusModsShareLink;
import seedu.address.model.person.Name;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.util.ModuleEventMappingUtil;
import seedu.address.testutil.modelutil.TypicalModel;
import seedu.address.testutil.moduleutil.NusModsShareLinkStrings;

class AddNusModsCommandTest {

    private static ModelManager model;

    @BeforeAll
    static void setUp() throws DuplicateMappingException, DuplicatePersonException, DuplicateGroupException {
        model = TypicalModel.generateModelWithNusModsData();
    }

    @Test
    void execute_success_commandResultSuccess() throws CommandException, ParseException {
        Name name = ALICE.getName();
        NusModsShareLink validLink = NusModsShareLink.parseLink(NusModsShareLink.VALID_EXAMPLE_STRING);
        CommandResult actualCommandResult = new AddNusModsCommand(name, validLink).execute(model);

        CommandResult expectedCommandResult = new CommandResult(AddNusModsCommand.MESSAGE_SUCCESS);

        assertTrue(actualCommandResult.equals(expectedCommandResult));
    }

    @Test
    void execute_personDoesNotExist_commandResultFailure() throws CommandException, ParseException {
        Name name = ZACK.getName();
        NusModsShareLink validLink = NusModsShareLink.parseLink(NusModsShareLinkStrings.VALID_LINK_1);

        CommandResult actualCommandResult = new AddNusModsCommand(name, validLink).execute(model);
        CommandResult expectedCommandResult = new CommandResult(
                String.format(AddNusModsCommand.MESSAGE_FAILURE, AddNusModsCommand.MESSAGE_PERSON_NOT_FOUND));

        assertTrue(actualCommandResult.equals(expectedCommandResult));
    }

    @Test
    void execute_invalidModuleCode_commandResultFailure() throws CommandException, ParseException {
        Name name = ALICE.getName();
        NusModsShareLink validLink = NusModsShareLink.parseLink(NusModsShareLinkStrings.VALID_LINK_INVALID_MODULE_CODE);

        CommandResult actualCommandResult = new AddNusModsCommand(name, validLink).execute(model);
        CommandResult expectedCommandResult = new CommandResult(
                String.format(AddNusModsCommand.MESSAGE_FAILURE,
                        String.format(AddNusModsCommand.MESSAGE_MODULE_NOT_FOUND, "INVALIDMODULE")));

        assertTrue(actualCommandResult.equals(expectedCommandResult));
    }

    @Test
    void execute_invalidClassNumber_commandResultFailure() throws CommandException, ParseException {
        Name name = ALICE.getName();
        NusModsShareLink validLink = NusModsShareLink.parseLink(
                NusModsShareLinkStrings.VALID_LINK_INVALID_CLASS_NUMBER);

        CommandResult actualCommandResult = new AddNusModsCommand(name, validLink).execute(model);
        CommandResult expectedCommandResult = new CommandResult(
                String.format(AddNusModsCommand.MESSAGE_FAILURE, ModuleEventMappingUtil.MESSAGE_INVALID_LESSONS));

        assertTrue(actualCommandResult.equals(expectedCommandResult));
    }

    @Test
    void execute_clashingEvents_commandResultFailure() throws CommandException, ParseException {
        Name name = ALICE.getName();
        NusModsShareLink validLink = NusModsShareLink.parseLink(
                NusModsShareLinkStrings.VALID_LINK_1);

        //execute twice with same link
        new AddNusModsCommand(name, validLink).execute(model);
        CommandResult actualCommandResult = new AddNusModsCommand(name, validLink).execute(model);
        CommandResult expectedCommandResult = new CommandResult(
                String.format(AddNusModsCommand.MESSAGE_FAILURE, AddNusModsCommand.MESSAGE_EVENTS_CLASH));

        assertTrue(actualCommandResult.equals(expectedCommandResult));
    }

    @Test
    void equals() throws ParseException {
        Name name = ALICE.getName();
        NusModsShareLink validLink1 = NusModsShareLink.parseLink(
                NusModsShareLinkStrings.VALID_LINK_1);
        NusModsShareLink validLink2 = NusModsShareLink.parseLink(
                NusModsShareLinkStrings.VALID_LINK_2);

        AddNusModsCommand command = new AddNusModsCommand(name, validLink1);
        assertTrue(command.equals(command));
        assertTrue(command.equals(new AddNusModsCommand(name, validLink1)));
        assertFalse(command.equals(new AddNusModsCommand(name, validLink2)));
    }
}
