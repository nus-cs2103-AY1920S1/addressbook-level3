package seedu.algobase.logic.parser;

import static seedu.algobase.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.algobase.logic.parser.CliSyntax.PREFIX_AUTHOR;
import static seedu.algobase.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.algobase.logic.parser.CliSyntax.PREFIX_DIFFICULTY;
import static seedu.algobase.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.algobase.logic.parser.CliSyntax.PREFIX_REMARK;
import static seedu.algobase.logic.parser.CliSyntax.PREFIX_SOURCE;
import static seedu.algobase.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.algobase.logic.parser.CliSyntax.PREFIX_WEBLINK;
import static seedu.algobase.logic.parser.ParserUtil.arePrefixesPresent;

import java.util.HashSet;
import java.util.Set;

import seedu.algobase.logic.commands.AddCommand;
import seedu.algobase.logic.parser.exceptions.ParseException;
import seedu.algobase.model.problem.Author;
import seedu.algobase.model.problem.Description;
import seedu.algobase.model.problem.Difficulty;
import seedu.algobase.model.problem.Name;
import seedu.algobase.model.problem.Problem;
import seedu.algobase.model.problem.Remark;
import seedu.algobase.model.problem.Source;
import seedu.algobase.model.problem.WebLink;
import seedu.algobase.model.tag.Tag;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddCommandParser implements Parser<AddCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_AUTHOR, PREFIX_WEBLINK, PREFIX_DESCRIPTION,
                        PREFIX_TAG, PREFIX_DIFFICULTY, PREFIX_REMARK, PREFIX_SOURCE);

        if (!arePrefixesPresent(argMultimap, PREFIX_NAME)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }
        Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());

        Author author;
        if (arePrefixesPresent(argMultimap, PREFIX_AUTHOR)) {
            author = ParserUtil.parseAuthor(argMultimap.getValue(PREFIX_AUTHOR).get());
        } else {
            author = Author.DEFAULT_AUTHOR;
        }

        WebLink webLink;
        if (arePrefixesPresent(argMultimap, PREFIX_WEBLINK)) {
            webLink = ParserUtil.parseWeblink(argMultimap.getValue(PREFIX_WEBLINK).get());
        } else {
            webLink = WebLink.DEFAULT_WEBLINK;
        }

        Description description;
        if (arePrefixesPresent(argMultimap, PREFIX_DESCRIPTION)) {
            description = ParserUtil.parseDescription(argMultimap.getValue(PREFIX_DESCRIPTION).get());
        } else {
            description = Description.DEFAULT_DESCRIPTION;
        }

        Set<Tag> tagList;
        if (arePrefixesPresent(argMultimap, PREFIX_TAG)) {
            tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));
        } else {
            tagList = new HashSet<>();
        }

        Difficulty difficulty;
        if (arePrefixesPresent(argMultimap, PREFIX_DIFFICULTY)) {
            difficulty = ParserUtil.parseDifficulty(argMultimap.getValue(PREFIX_DIFFICULTY).get());
        } else {
            difficulty = Difficulty.DEFAULT_DIFFICULTY;
        }

        Remark remark;
        if (arePrefixesPresent(argMultimap, PREFIX_REMARK)) {
            remark = ParserUtil.parseRemark(argMultimap.getValue(PREFIX_REMARK).get());
        } else {
            remark = Remark.DEFAULT_REMARK;
        }

        Source source;
        if (arePrefixesPresent(argMultimap, PREFIX_SOURCE)) {
            source = ParserUtil.parseSource(argMultimap.getValue(PREFIX_SOURCE).get());
        } else {
            source = Source.DEFAULT_SOURCE;
        }

        Problem problem = new Problem(name, author, webLink, description, tagList, difficulty, remark, source);

        return new AddCommand(problem);
    }
}
