package io.xpire.logic.parser;

import static java.util.Objects.requireNonNull;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import io.xpire.commons.core.Messages;
import io.xpire.commons.util.StringUtil;
import io.xpire.logic.commands.SearchCommand;
import io.xpire.logic.parser.exceptions.ParseException;
import io.xpire.model.ListType;
import io.xpire.model.item.ContainsKeywordsPredicate;

//@@author JermyTan
/**
 * Parses input arguments and creates a new SearchCommand object
 */
public class SearchCommandParser implements Parser<SearchCommand> {
    /** The current list type */
    private final ListType listType;

    /**
     * Public constructor for class.
     *
     * @param listType Current list type.
     */
    SearchCommandParser(ListType listType) {
        this.listType = listType;
    }

    /**
     * Parses the given {@code String} of arguments in the context of the SearchCommand
     * and returns a SearchCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public SearchCommand parse(String args) throws ParseException {
        requireNonNull(args);

        String trimmedArgs = args.trim();
        List<String> keywords = Arrays.stream(trimmedArgs.split("\\|"))
                .map(String::trim)
                .filter(keyword -> !keyword.isEmpty())
                .map(keyword -> keyword.startsWith("#")
                ? "#" + StringUtil.convertToSentenceCase(keyword.substring(1))
                : keyword.toLowerCase())
                .collect(Collectors.toList());

        if (keywords.isEmpty()) {
            throw new ParseException(
                    String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, SearchCommand.MESSAGE_USAGE));
        }
        return new SearchCommand(this.listType, new ContainsKeywordsPredicate(keywords));
    }
}
