package seedu.address.logic.parser.verification;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.verification.ValidModsCommand;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.semester.SemesterName;

/**
 * Parses input arguments and creates a new ValidModsCommand object
 */
public class ValidModsCommandParser implements Parser<ValidModsCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the
     * CheckCommand and returns a CheckCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public ValidModsCommand parse(String args) throws ParseException {
        if ("".equals(args.strip())) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ValidModsCommand.MESSAGE_USAGE));
        } else {
            SemesterName semName = ParserUtil.parseSemester(args.strip());
            return new ValidModsCommand(semName);
        }
    }
}
