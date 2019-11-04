package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.personutil.TypicalPersonDescriptor.ALICE;
import static seedu.address.testutil.personutil.TypicalPersonDescriptor.ZACK;

import java.util.Map;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.ModelManager;
import seedu.address.model.group.exceptions.DuplicateGroupException;
import seedu.address.model.mapping.exceptions.DuplicateMappingException;
import seedu.address.model.module.LessonNo;
import seedu.address.model.module.LessonType;
import seedu.address.model.module.ModuleCode;
import seedu.address.model.person.Name;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.testutil.modelutil.TypicalModel;

class AddNusModCommandTest {

    private static ModelManager model;

    @BeforeAll
    static void setUp() throws DuplicateMappingException, DuplicatePersonException, DuplicateGroupException {
        model = TypicalModel.generateModelWithNusModsData();
    }

    @Test
    void execute_success_commandResultSuccess() throws CommandException, ParseException {
        Name name = ALICE.getName();
        ModuleCode moduleCode = new ModuleCode("CS3230");
        Map<LessonType, LessonNo> lessonTypeNumMap = ParserUtil.parseLessonTypeNumMap("LEC:1,TUT:08");

        CommandResult actualCommandResult = new AddNusModCommand(name, moduleCode, lessonTypeNumMap).execute(model);
        CommandResult expectedCommandResult = new CommandResult(AddNusModCommand.MESSAGE_SUCCESS);

        assertTrue(actualCommandResult.equals(expectedCommandResult));
    }

    @Test
    void execute_personDoesNotExist_commandResultFailure() throws CommandException, ParseException {
        Name name = ZACK.getName();
        ModuleCode moduleCode = new ModuleCode("CS3230");
        Map<LessonType, LessonNo> lessonTypeNumMap = ParserUtil.parseLessonTypeNumMap("LEC:1,TUT:08");

        CommandResult actualCommandResult = new AddNusModCommand(name, moduleCode, lessonTypeNumMap).execute(model);
        CommandResult expectedCommandResult = new CommandResult(
                String.format(AddNusModCommand.MESSAGE_FAILURE, AddNusModCommand.MESSAGE_PERSON_NOT_FOUND));

        assertTrue(actualCommandResult.equals(expectedCommandResult));
    }

    @Test
    void execute_invalidModuleCode_commandResultFailure() throws CommandException, ParseException {
        Name name = ALICE.getName();
        ModuleCode moduleCode = new ModuleCode("INVALID_MODULE_CODE");
        Map<LessonType, LessonNo> lessonTypeNumMap = ParserUtil.parseLessonTypeNumMap("LEC:1,TUT:08");

        CommandResult actualCommandResult = new AddNusModCommand(name, moduleCode, lessonTypeNumMap).execute(model);
        CommandResult expectedCommandResult = new CommandResult(
                String.format(AddNusModCommand.MESSAGE_FAILURE, AddNusModCommand.MESSAGE_MODULE_NOT_FOUND));

        assertTrue(actualCommandResult.equals(expectedCommandResult));
    }

    @Test
    void execute_clashingEvents_commandResultFailure() throws CommandException, ParseException {
        Name name = ALICE.getName();
        ModuleCode moduleCode = new ModuleCode("CS3230");
        Map<LessonType, LessonNo> lessonTypeNumMap = ParserUtil.parseLessonTypeNumMap("LEC:1,TUT:08");

        //execute same type of command twice
        new AddNusModCommand(name, moduleCode, lessonTypeNumMap).execute(model);
        CommandResult actualCommandResult = new AddNusModCommand(name, moduleCode, lessonTypeNumMap).execute(model);
        CommandResult expectedCommandResult = new CommandResult(
                String.format(AddNusModCommand.MESSAGE_FAILURE, AddNusModCommand.MESSAGE_EVENTS_CLASH));

        assertTrue(actualCommandResult.equals(expectedCommandResult));
    }

    @Test
    void equals() throws ParseException {
        Name name = ALICE.getName();
        ModuleCode moduleCode = new ModuleCode("CS3230");
        Map<LessonType, LessonNo> lessonTypeNumMap = ParserUtil.parseLessonTypeNumMap("LEC:1,TUT:08");
        Map<LessonType, LessonNo> lessonTypeNumMap2 = ParserUtil.parseLessonTypeNumMap("LEC:1,TUT:09");

        AddNusModCommand command = new AddNusModCommand(name, moduleCode, lessonTypeNumMap);
        assertTrue(command.equals(command));
        assertTrue(command.equals(new AddNusModCommand(name, moduleCode, lessonTypeNumMap)));
        assertFalse(command.equals(new AddNusModCommand(name, moduleCode, lessonTypeNumMap2)));
    }
}
