package seedu.revision.logic.parser.main;

import static java.util.Objects.requireNonNull;
import static seedu.revision.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.revision.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static seedu.revision.logic.parser.CliSyntax.PREFIX_DIFFICULTY;
import static seedu.revision.logic.parser.CliSyntax.PREFIX_MODE;
import static seedu.revision.logic.parser.CliSyntax.PREFIX_TIMER;

import seedu.revision.logic.commands.main.StartQuizCommand;
import seedu.revision.logic.parser.ArgumentMultimap;
import seedu.revision.logic.parser.ArgumentTokenizer;
import seedu.revision.logic.parser.Parser;
import seedu.revision.logic.parser.ParserUtil;
import seedu.revision.logic.parser.exceptions.ParseException;
import seedu.revision.model.answerable.Difficulty;
import seedu.revision.model.category.Category;
import seedu.revision.model.quiz.Mode;

/**
 * Parses input arguments and creates a new StartQuizCommand object
 */
public class StartQuizCommandParser implements Parser<StartQuizCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the StartQuizCommand
     * and returns a StartQuizCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public StartQuizCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_MODE, PREFIX_TIMER, PREFIX_DIFFICULTY, PREFIX_CATEGORY);

        if (!argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, StartQuizCommand.MESSAGE_USAGE));
        }


       boolean optionalPrefixesArePresent = argMultimap.getValue(PREFIX_TIMER).isPresent()
                || argMultimap.getValue(PREFIX_DIFFICULTY).isPresent()
                || argMultimap.getValue(PREFIX_CATEGORY).isPresent();


        Mode mode;
        Difficulty difficulty;
        Category category;
        int time;

        if (argMultimap.getValue(PREFIX_MODE).isPresent()) {
            mode = ParserUtil.parseMode(argMultimap.getValue(PREFIX_MODE).get());
        } else {
            throw new ParseException(StartQuizCommand.MESSAGE_USAGE);
        }

        switch (mode.toString().toLowerCase()) {
        case "normal":
            if (optionalPrefixesArePresent) {
                throw new ParseException(StartQuizCommand.MESSAGE_USAGE);
            } else {
                requireNonNull(mode);
                return new StartQuizCommand(mode);
            }
        case "chaos":
            break;
        case "custom":
        
        }


    }
}
