package seedu.algobase.logic.parser;

import static seedu.algobase.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.algobase.logic.parser.CliSyntax.PREFIX_AUTHOR;
import static seedu.algobase.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.algobase.logic.parser.CliSyntax.PREFIX_DIFFICULTY;
import static seedu.algobase.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.algobase.logic.parser.CliSyntax.PREFIX_SOURCE;
import static seedu.algobase.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Arrays;

import seedu.algobase.logic.commands.FindCommand;
import seedu.algobase.logic.parser.exceptions.ParseException;
import seedu.algobase.model.problem.AuthorMatchesKeywordPredicate;
import seedu.algobase.model.problem.DescriptionContainsKeywordsPredicate;
import seedu.algobase.model.problem.DifficultyIsInRangePredicate;
import seedu.algobase.model.problem.NameContainsKeywordsPredicate;
import seedu.algobase.model.problem.SourceMatchesKeywordPredicate;
import seedu.algobase.model.problem.TagIncludesKeywordsPredicate;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FindCommandParser implements Parser<FindCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns a FindCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindCommand parse(String args) throws ParseException {
        ArgumentMultimap argumentMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_AUTHOR, PREFIX_DESCRIPTION, PREFIX_SOURCE,
                        PREFIX_DIFFICULTY, PREFIX_TAG);

        // According to the command format, no preamble should be present.
        if (!argumentMultimap.getPreamble().isBlank()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        FindCommand.FindProblemDescriptor findProblemDescriptor = new FindCommand.FindProblemDescriptor();

        if (argumentMultimap.getValue(PREFIX_NAME).isPresent()) {
            String trimmedNameArg = argumentMultimap.getValue(PREFIX_NAME).get().trim();
            String[] nameKeywords = trimmedNameArg.split("\\s+");
            findProblemDescriptor.setNamePredicate(new NameContainsKeywordsPredicate(Arrays.asList(nameKeywords)));
        }

        if (argumentMultimap.getValue(PREFIX_AUTHOR).isPresent()) {
            String authorKeyword = argumentMultimap.getValue(PREFIX_AUTHOR).get();
            findProblemDescriptor.setAuthorPredicate(new AuthorMatchesKeywordPredicate(authorKeyword));
        }

        if (argumentMultimap.getValue(PREFIX_DESCRIPTION).isPresent()) {
            String trimmedDescriptionArg = argumentMultimap.getValue(PREFIX_DESCRIPTION).get();
            String[] descriptionKeywords = trimmedDescriptionArg.split("\\s+");
            findProblemDescriptor.setDescriptionPredicate(
                new DescriptionContainsKeywordsPredicate(Arrays.asList(descriptionKeywords)));
        }

        if (argumentMultimap.getValue(PREFIX_SOURCE).isPresent()) {
            String sourceKeyword = argumentMultimap.getValue(PREFIX_SOURCE).get();
            findProblemDescriptor.setSourcePredicate(new SourceMatchesKeywordPredicate(sourceKeyword));
        }

        if (argumentMultimap.getValue(PREFIX_DIFFICULTY).isPresent()) {
            String[] difficultyBounds = argumentMultimap.getValue(PREFIX_DIFFICULTY).get().split("-");
            if (difficultyBounds.length != 2) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
            }
            try {
                double lowerBound = Double.parseDouble(difficultyBounds[0]);
                double upperBound = Double.parseDouble(difficultyBounds[1]);
                findProblemDescriptor.setDifficultyPredicate(new DifficultyIsInRangePredicate(lowerBound, upperBound));
            } catch (NumberFormatException nfe) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE), nfe);
            } catch (NullPointerException npe) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE), npe);
            }
        }

        if (argumentMultimap.getValue(PREFIX_TAG).isPresent()) {
            String trimmedTagArg = argumentMultimap.getValue(PREFIX_TAG).get().trim();
            String[] tagKeywords = trimmedTagArg.split("\\s+");
            findProblemDescriptor.setTagPredicate(new TagIncludesKeywordsPredicate(Arrays.asList(tagKeywords)));
        }

        if (!findProblemDescriptor.isAnyFieldProvided()) {
            throw new ParseException(FindCommand.MESSAGE_NO_CONSTRAINTS);
        }

        return new FindCommand(findProblemDescriptor);
    }
}
