package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LESSON_TYPE_AND_NUM;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODULE_CODE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.personutil.TypicalPersonDescriptor.ALICE;
import static seedu.address.testutil.personutil.TypicalPersonDescriptor.WHITESPACE;

import java.util.Map;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.AddNusModCommand;
import seedu.address.model.module.LessonNo;
import seedu.address.model.module.LessonType;
import seedu.address.model.module.ModuleCode;
import seedu.address.model.person.Name;

class AddNusModCommandParserTest {

    private AddNusModCommandParser parser = new AddNusModCommandParser();

    @Test
    void parse_success() {
        Name name = ALICE.getName();
        ModuleCode moduleCode = new ModuleCode("CS3230");
        Map<LessonType, LessonNo> lessonTypeNumMap = Map.of(
                LessonType.findLessonType("LEC"), new LessonNo("1"),
                LessonType.findLessonType("TUT"), new LessonNo("08")
        );

        AddNusModCommand expectedCommand = new AddNusModCommand(name, moduleCode, lessonTypeNumMap);

        assertParseSuccess(parser,
                WHITESPACE + PREFIX_NAME + ALICE.getName().toString()
                        + WHITESPACE + PREFIX_MODULE_CODE + "CS3230"
                        + WHITESPACE + PREFIX_LESSON_TYPE_AND_NUM + "LEC:1,TUT:08", expectedCommand);
    }

    @Test
    void parse_failure() {
        assertParseFailure(parser,
                WHITESPACE,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddNusModCommand.MESSAGE_USAGE));

    }
}
