package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ACAD_YEAR;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LESSON_TYPE_AND_NUM;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODULE_CODE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SEMESTER;

import java.util.Map;

import seedu.address.logic.commands.AddNusModCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.module.LessonNo;
import seedu.address.model.module.LessonType;
import seedu.address.model.module.ModuleCode;
import seedu.address.model.person.Name;

/**
 * Parses input arguments and creates a new AddNusModsCommand object.
 */
public class AddNusModCommandParser implements Parser<AddNusModCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the AddNusModsCommand
     * and returns an AddNusModsCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public AddNusModCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_MODULE_CODE, PREFIX_LESSON_TYPE_AND_NUM,
                        PREFIX_ACAD_YEAR, PREFIX_SEMESTER);

        if (!Parser.arePrefixesPresent(argMultimap, PREFIX_MODULE_CODE, PREFIX_LESSON_TYPE_AND_NUM)
                || Parser.areMultiplePrefixesPresent(argMultimap, PREFIX_MODULE_CODE, PREFIX_LESSON_TYPE_AND_NUM)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddNusModCommand.MESSAGE_USAGE));
        }

        Name name;
        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
        } else {
            name = null;
        }

        ModuleCode moduleCode = ParserUtil.parseModuleCode(argMultimap.getValue(PREFIX_MODULE_CODE).get());
        Map<LessonType, LessonNo> lessonTypeNumMap = ParserUtil.parseLessonTypeNumMap(
                argMultimap.getValue(PREFIX_LESSON_TYPE_AND_NUM).get());

        return new AddNusModCommand(name, moduleCode, lessonTypeNumMap);
    }
}
