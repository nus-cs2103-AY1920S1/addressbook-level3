package seedu.revision.logic.parser.quiz;

import static seedu.revision.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.revision.logic.commands.quiz.TfInputCommand;
import seedu.revision.logic.parser.QuizParser;
import seedu.revision.logic.parser.exceptions.ParseException;
import seedu.revision.model.answerable.Answerable;

/** TfInputCommandParser used to parse user inputs for True and False {@code Answerables}.**/
public class TfInputCommandParser implements QuizParser<TfInputCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public TfInputCommand parse(String args, Answerable currentAnswerable) throws ParseException {
        if (args.matches("\\b(?i)(true|false|t|f)\\b")) {
            return new TfInputCommand(args, currentAnswerable);
        } else {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, TfInputCommand.MESSAGE_USAGE));
        }
    }
}
