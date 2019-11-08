package seedu.address.logic.quiz.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.quiz.commands.AddCommentCommand;
import seedu.address.logic.quiz.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new AddCommentCommand object
 */
public class AddCommentCommandParser implements Parser<AddCommentCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommentCommand
     * and returns an AddCommentCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddCommentCommand parse(String args) throws ParseException {
        Index index;
        String comment;
        String trimmedArgs = args.trim();
        String[] arrParameters = trimmedArgs.split(" <val>");

        if (arrParameters.length != 2) {
            throw new ParseException(
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommentCommand.MESSAGE_USAGE));
        }

        try {
            index = ParserUtil.parseIndex(arrParameters[0]);
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                AddCommentCommand.MESSAGE_USAGE), pe);
        }

        comment = arrParameters[1];
        if (comment.contains("<val>")) {
            throw new ParseException("Comment field shouldn't contain <val> instruction keyword");
        }

        return new AddCommentCommand(index, comment);
    }
}
