package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.EditLessonCommand.EditLessonDescriptor;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DAY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ENDTIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LESSONNAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STARTTIME;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditLessonCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new EditLessonCommand object
 */
public class EditLessonCommandParser implements Parser<EditLessonCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the EditLessonCommand
     * and returns an EditLessonCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditLessonCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_DAY, PREFIX_LESSONNAME, PREFIX_STARTTIME, PREFIX_ENDTIME);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    EditLessonCommand.MESSAGE_USAGE), pe);
        }

        Index day = ParserUtil.parseIndex(argMultimap.getValue(PREFIX_DAY).get());

        EditLessonDescriptor editLessonDescriptor = new EditLessonDescriptor();
        if (argMultimap.getValue(PREFIX_LESSONNAME).isPresent()) {
            editLessonDescriptor.setName(ParserUtil.parseClassName(argMultimap.getValue(PREFIX_LESSONNAME).get()));
        }
        if (argMultimap.getValue(PREFIX_STARTTIME).isPresent()) {
            editLessonDescriptor.setStartTime(ParserUtil.parseTime(argMultimap.getValue(PREFIX_STARTTIME).get()));
        }
        if (argMultimap.getValue(PREFIX_ENDTIME).isPresent()) {
            editLessonDescriptor.setEndTime(ParserUtil.parseTime(argMultimap.getValue(PREFIX_ENDTIME).get()));
        }
        if (!editLessonDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditLessonCommand.MESSAGE_NOT_EDITED);
        }

        return new EditLessonCommand(index, editLessonDescriptor, day);
    }

}
