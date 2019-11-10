package seedu.algobase.logic.parser.problem;

import static java.util.Objects.requireNonNull;
import static seedu.algobase.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.algobase.logic.parser.CliSyntax.PREFIX_AUTHOR;
import static seedu.algobase.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.algobase.logic.parser.CliSyntax.PREFIX_DIFFICULTY;
import static seedu.algobase.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.algobase.logic.parser.CliSyntax.PREFIX_SOURCE;
import static seedu.algobase.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.algobase.logic.parser.ParserUtil.parseAuthorPredicate;
import static seedu.algobase.logic.parser.ParserUtil.parseDescriptionPredicate;
import static seedu.algobase.logic.parser.ParserUtil.parseDifficultyPredicate;
import static seedu.algobase.logic.parser.ParserUtil.parseNamePredicate;
import static seedu.algobase.logic.parser.ParserUtil.parseSourcePredicate;
import static seedu.algobase.logic.parser.ParserUtil.parseTagPredicate;

import java.util.logging.Logger;

import seedu.algobase.commons.core.LogsCenter;
import seedu.algobase.logic.commands.problem.FindCommand;
import seedu.algobase.logic.parser.ArgumentMultimap;
import seedu.algobase.logic.parser.ArgumentTokenizer;
import seedu.algobase.logic.parser.Parser;
import seedu.algobase.logic.parser.exceptions.ParseException;
import seedu.algobase.model.searchrule.problemsearchrule.FindProblemDescriptor;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FindCommandParser implements Parser<FindCommand> {

    private static final Logger logger = LogsCenter.getLogger(FindCommandParser.class);

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns a FindCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public FindCommand parse(String args) throws ParseException {
        requireNonNull(args);
        logger.info("Parsing find command of input: " + args);

        ArgumentMultimap argumentMultimap =
            ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_AUTHOR, PREFIX_DESCRIPTION, PREFIX_SOURCE,
                PREFIX_DIFFICULTY, PREFIX_TAG);

        // According to the command format, no preamble should be present.
        if (!argumentMultimap.getPreamble().isBlank()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        FindProblemDescriptor findProblemDescriptor = new FindProblemDescriptor();

        if (argumentMultimap.getValue(PREFIX_NAME).isPresent()) {
            findProblemDescriptor.setNamePredicate(parseNamePredicate(argumentMultimap.getValue(PREFIX_NAME).get()
            ));
        }

        if (argumentMultimap.getValue(PREFIX_AUTHOR).isPresent()) {
            findProblemDescriptor.setAuthorPredicate(parseAuthorPredicate(
                argumentMultimap.getValue(PREFIX_AUTHOR).get()
            ));
        }

        if (argumentMultimap.getValue(PREFIX_DESCRIPTION).isPresent()) {
            findProblemDescriptor.setDescriptionPredicate(parseDescriptionPredicate(
                argumentMultimap.getValue(PREFIX_DESCRIPTION).get()
            ));
        }

        if (argumentMultimap.getValue(PREFIX_SOURCE).isPresent()) {
            findProblemDescriptor.setSourcePredicate(
                parseSourcePredicate(argumentMultimap.getValue(PREFIX_SOURCE).get()
                ));
        }

        if (argumentMultimap.getValue(PREFIX_DIFFICULTY).isPresent()) {
            findProblemDescriptor.setDifficultyPredicate(parseDifficultyPredicate(
                argumentMultimap.getValue(PREFIX_DIFFICULTY).get(),
                FindCommand.MESSAGE_USAGE));
        }

        if (argumentMultimap.getValue(PREFIX_TAG).isPresent()) {
            findProblemDescriptor.setTagPredicate(parseTagPredicate(argumentMultimap.getValue(PREFIX_TAG).get()
            ));
        }

        if (!findProblemDescriptor.isAnyFieldProvided()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_NO_CONSTRAINTS));
        }

        return new FindCommand(findProblemDescriptor);
    }
}
