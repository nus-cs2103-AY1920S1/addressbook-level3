package seedu.algobase.logic.parser.findrule;

import static java.util.Objects.requireNonNull;
import static seedu.algobase.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.algobase.commons.core.Messages.MESSAGE_INVALID_NAME_FORMAT;
import static seedu.algobase.commons.util.CollectionUtil.isAnyNonNull;
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
import seedu.algobase.logic.commands.findrule.AddFindRuleCommand;
import seedu.algobase.logic.parser.ArgumentMultimap;
import seedu.algobase.logic.parser.ArgumentTokenizer;
import seedu.algobase.logic.parser.Parser;
import seedu.algobase.logic.parser.exceptions.ParseException;
import seedu.algobase.model.searchrule.problemsearchrule.AuthorMatchesKeywordPredicate;
import seedu.algobase.model.searchrule.problemsearchrule.DescriptionContainsKeywordsPredicate;
import seedu.algobase.model.searchrule.problemsearchrule.DifficultyIsInRangePredicate;
import seedu.algobase.model.searchrule.problemsearchrule.Name;
import seedu.algobase.model.searchrule.problemsearchrule.NameContainsKeywordsPredicate;
import seedu.algobase.model.searchrule.problemsearchrule.ProblemSearchRule;
import seedu.algobase.model.searchrule.problemsearchrule.SourceMatchesKeywordPredicate;
import seedu.algobase.model.searchrule.problemsearchrule.TagIncludesKeywordsPredicate;

/**
 * Parses input arguments and creates a new AddFindRuleCommand object
 */
public class AddFindRuleCommandParser implements Parser<AddFindRuleCommand> {

    private static final Logger logger = LogsCenter.getLogger(AddFindRuleCommandParser.class);

    /**
     * Parses the given {@code String} of arguments in the context of the AddFindRuleCommand
     * and returns a AddFindRuleCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public AddFindRuleCommand parse(String args) throws ParseException {
        requireNonNull(args);

        logger.info("Parsing add find rule command with input: " + args);

        ArgumentMultimap argumentMultimap =
            ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_AUTHOR, PREFIX_DESCRIPTION, PREFIX_SOURCE,
                PREFIX_DIFFICULTY, PREFIX_TAG);

        // Every find rule must have a name.
        if (argumentMultimap.getPreamble().isBlank()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddFindRuleCommand.MESSAGE_USAGE));
        }

        if (!Name.isValidName(argumentMultimap.getPreamble())) {
            throw new ParseException(String.format(MESSAGE_INVALID_NAME_FORMAT, Name.MESSAGE_CONSTRAINTS));
        }
        final Name name = new Name(argumentMultimap.getPreamble());

        final DifficultyIsInRangePredicate difficultyIsInRangePredicate;
        if (argumentMultimap.getValue(PREFIX_DIFFICULTY).isPresent()) {
            difficultyIsInRangePredicate = parseDifficultyPredicate(argumentMultimap.getValue(PREFIX_DIFFICULTY).get(),
                AddFindRuleCommand.MESSAGE_USAGE);
        } else {
            difficultyIsInRangePredicate = null;
        }

        final NameContainsKeywordsPredicate nameContainsKeywordsPredicate;
        if (argumentMultimap.getValue(PREFIX_NAME).isPresent()) {
            nameContainsKeywordsPredicate = parseNamePredicate(argumentMultimap.getValue(PREFIX_NAME).get()
            );
        } else {
            nameContainsKeywordsPredicate = null;
        }

        final AuthorMatchesKeywordPredicate authorMatchesKeywordPredicate;
        if (argumentMultimap.getValue(PREFIX_AUTHOR).isPresent()) {
            authorMatchesKeywordPredicate = parseAuthorPredicate(argumentMultimap.getValue(PREFIX_AUTHOR).get()
            );
        } else {
            authorMatchesKeywordPredicate = null;
        }

        final DescriptionContainsKeywordsPredicate descriptionContainsKeywordsPredicate;
        if (argumentMultimap.getValue(PREFIX_DESCRIPTION).isPresent()) {
            descriptionContainsKeywordsPredicate = parseDescriptionPredicate(
                argumentMultimap.getValue(PREFIX_DESCRIPTION).get()
            );
        } else {
            descriptionContainsKeywordsPredicate = null;
        }

        final SourceMatchesKeywordPredicate sourceMatchesKeywordPredicate;
        if (argumentMultimap.getValue(PREFIX_SOURCE).isPresent()) {
            sourceMatchesKeywordPredicate = parseSourcePredicate(argumentMultimap.getValue(PREFIX_SOURCE).get()
            );
        } else {
            sourceMatchesKeywordPredicate = null;
        }

        final TagIncludesKeywordsPredicate tagIncludesKeywordsPredicate;
        if (argumentMultimap.getValue(PREFIX_TAG).isPresent()) {
            tagIncludesKeywordsPredicate = parseTagPredicate(argumentMultimap.getValue(PREFIX_TAG).get()
            );
        } else {
            tagIncludesKeywordsPredicate = null;
        }

        if (!isAnyNonNull(nameContainsKeywordsPredicate, authorMatchesKeywordPredicate,
            descriptionContainsKeywordsPredicate, sourceMatchesKeywordPredicate,
            difficultyIsInRangePredicate, tagIncludesKeywordsPredicate)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                AddFindRuleCommand.MESSAGE_NO_CONSTRAINTS));
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
