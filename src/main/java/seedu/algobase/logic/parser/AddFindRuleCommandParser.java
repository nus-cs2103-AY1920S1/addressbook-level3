package seedu.algobase.logic.parser;

import static seedu.algobase.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.algobase.commons.util.CollectionUtil.isAnyNonNull;
import static seedu.algobase.commons.util.CollectionUtil.isArrayOfLength;
import static seedu.algobase.logic.parser.CliSyntax.PREFIX_AUTHOR;
import static seedu.algobase.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.algobase.logic.parser.CliSyntax.PREFIX_DIFFICULTY;
import static seedu.algobase.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.algobase.logic.parser.CliSyntax.PREFIX_SOURCE;
import static seedu.algobase.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Arrays;
import java.util.List;

import seedu.algobase.logic.commands.AddFindRuleCommand;
import seedu.algobase.logic.parser.exceptions.ParseException;
import seedu.algobase.model.searchrule.problemsearchrule.AuthorMatchesKeywordPredicate;
import seedu.algobase.model.searchrule.problemsearchrule.DescriptionContainsKeywordsPredicate;
import seedu.algobase.model.searchrule.problemsearchrule.DifficultyIsInRangePredicate;
import seedu.algobase.model.searchrule.problemsearchrule.NameContainsKeywordsPredicate;
import seedu.algobase.model.searchrule.problemsearchrule.ProblemSearchRule;
import seedu.algobase.model.searchrule.problemsearchrule.SourceMatchesKeywordPredicate;
import seedu.algobase.model.searchrule.problemsearchrule.TagIncludesKeywordsPredicate;

/**
 * Parses input arguments and creates a new AddFindRuleCommand object
 */
public class AddFindRuleCommandParser implements Parser<AddFindRuleCommand> {

    private List<String> getArgumentValueAsList(String argValue) {
        String trimmedArg = argValue.trim();
        String[] keywords = trimmedArg.split("\\s+");
        return Arrays.asList(keywords);
    }

    /**
     * Parses the given {@code String} of arguments in the context of the AddFindRuleCommand
     * and returns a AddFindRuleCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public AddFindRuleCommand parse(String args) throws ParseException {
        ArgumentMultimap argumentMultimap =
            ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_AUTHOR, PREFIX_DESCRIPTION, PREFIX_SOURCE,
                PREFIX_DIFFICULTY, PREFIX_TAG);

        // Every find rule must have a name.
        if (argumentMultimap.getPreamble().isBlank()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddFindRuleCommand.MESSAGE_USAGE));
        }

        String name = argumentMultimap.getPreamble();

        final NameContainsKeywordsPredicate nameContainsKeywordsPredicate;
        if (argumentMultimap.getValue(PREFIX_NAME).isPresent()) {
            List<String> nameKeywords = getArgumentValueAsList(argumentMultimap.getValue(PREFIX_NAME).get());
            nameContainsKeywordsPredicate = new NameContainsKeywordsPredicate(nameKeywords);
        } else {
            nameContainsKeywordsPredicate = null;
        }

        final AuthorMatchesKeywordPredicate authorMatchesKeywordPredicate;
        if (argumentMultimap.getValue(PREFIX_AUTHOR).isPresent()) {
            String authorKeyword = argumentMultimap.getValue(PREFIX_AUTHOR).get();
            authorMatchesKeywordPredicate = new AuthorMatchesKeywordPredicate(authorKeyword);
        } else {
            authorMatchesKeywordPredicate = null;
        }

        final DescriptionContainsKeywordsPredicate descriptionContainsKeywordsPredicate;
        if (argumentMultimap.getValue(PREFIX_DESCRIPTION).isPresent()) {
            List<String> descriptionKeywords =
                getArgumentValueAsList(argumentMultimap.getValue(PREFIX_DESCRIPTION).get());
            descriptionContainsKeywordsPredicate =
                new DescriptionContainsKeywordsPredicate(descriptionKeywords);
        } else {
            descriptionContainsKeywordsPredicate = null;
        }

        final SourceMatchesKeywordPredicate sourceMatchesKeywordPredicate;
        if (argumentMultimap.getValue(PREFIX_SOURCE).isPresent()) {
            String sourceKeyword = argumentMultimap.getValue(PREFIX_SOURCE).get();
            sourceMatchesKeywordPredicate = new SourceMatchesKeywordPredicate(sourceKeyword);
        } else {
            sourceMatchesKeywordPredicate = null;
        }

        final DifficultyIsInRangePredicate difficultyIsInRangePredicate;
        if (argumentMultimap.getValue(PREFIX_DIFFICULTY).isPresent()) {
            String[] difficultyBounds = argumentMultimap.getValue(PREFIX_DIFFICULTY).get().split("-");
            if (!isArrayOfLength(difficultyBounds, 2)) {
                throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddFindRuleCommand.MESSAGE_USAGE));
            }
            try {
                double lowerBound = Double.parseDouble(difficultyBounds[0]);
                double upperBound = Double.parseDouble(difficultyBounds[1]);
                difficultyIsInRangePredicate = new DifficultyIsInRangePredicate(lowerBound, upperBound);
            } catch (NumberFormatException | NullPointerException nfe) {
                throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddFindRuleCommand.MESSAGE_USAGE), nfe);
            }
        } else {
            difficultyIsInRangePredicate = null;
        }

        final TagIncludesKeywordsPredicate tagIncludesKeywordsPredicate;
        if (argumentMultimap.getValue(PREFIX_TAG).isPresent()) {
            List<String> tagKeywords = getArgumentValueAsList(argumentMultimap.getValue(PREFIX_TAG).get());
            tagIncludesKeywordsPredicate = new TagIncludesKeywordsPredicate(tagKeywords);
        } else {
            tagIncludesKeywordsPredicate = null;
        }

        if (!isAnyNonNull(nameContainsKeywordsPredicate, authorMatchesKeywordPredicate,
            descriptionContainsKeywordsPredicate, sourceMatchesKeywordPredicate,
            difficultyIsInRangePredicate, tagIncludesKeywordsPredicate)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddFindRuleCommand.MESSAGE_USAGE));
        }

        ProblemSearchRule findRule = new ProblemSearchRule(name,
            nameContainsKeywordsPredicate,
            authorMatchesKeywordPredicate,
            descriptionContainsKeywordsPredicate,
            sourceMatchesKeywordPredicate,
            difficultyIsInRangePredicate,
            tagIncludesKeywordsPredicate);

        return new AddFindRuleCommand(findRule);
    }
}
