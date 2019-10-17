package seedu.address.logic.parser.visit;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_VISIT_REMARKS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_VISIT_TASK_FINISH;
import static seedu.address.logic.parser.CliSyntax.PREFIX_VISIT_TASK_INDEX_AND_DETAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_VISIT_TASK_UNFINISH;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.util.Pair;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.visit.UpdateOngoingVisitCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new UpdateOngoingVisitCommand object
 */
public class UpdateOngoingVisitCommandParser implements Parser<UpdateOngoingVisitCommand> {

    /*
     * Must match Index, whitespace then String.
     */
    public static final String INDEX_WHITESPACE_THEN_STRING_VALIDATION_REGEX = "^([0-9]+) (.*)";
    public static final int REGEX_MATCHER_INDEX = 1;
    public static final int REGEX_MATCHER_STRING = 2;

    /**
     * Parses the given {@code String} of arguments in the context of the UpdateOngoingVisitCommand
     * and returns an UpdateOngoingVisitCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public UpdateOngoingVisitCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_VISIT_REMARKS, PREFIX_VISIT_TASK_FINISH,
                        PREFIX_VISIT_TASK_UNFINISH, PREFIX_VISIT_TASK_INDEX_AND_DETAIL);

        UpdateOngoingVisitCommand.UpdateOngoingVisitDescriptor updateOngoingVisitDescriptor =
                new UpdateOngoingVisitCommand.UpdateOngoingVisitDescriptor();
        if (argMultimap.getValue(PREFIX_VISIT_REMARKS).isPresent()) {
            updateOngoingVisitDescriptor.setRemark(
                    ParserUtil.parseRemark(argMultimap.getValue(PREFIX_VISIT_REMARKS).get()));
        }
        updateOngoingVisitDescriptor.setFinishedVisitTaskIndexes(
                parseIndexes(argMultimap.getAllValues(PREFIX_VISIT_TASK_FINISH)));
        updateOngoingVisitDescriptor.setUnfinishedVisitTaskIndexes(
                parseIndexes(argMultimap.getAllValues(PREFIX_VISIT_TASK_UNFINISH)));
        updateOngoingVisitDescriptor.setUpdatedVisitTaskDetails(
                parseIndexAndDetailPairs(argMultimap.getAllValues(PREFIX_VISIT_TASK_INDEX_AND_DETAIL)));

        if (!updateOngoingVisitDescriptor.isAnyFieldEdited()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    UpdateOngoingVisitCommand.MESSAGE_USAGE));
        }

        return new UpdateOngoingVisitCommand(updateOngoingVisitDescriptor);
    }

    /**
     * Parse a list of string (presumably obtained from argMultimap.getAllValues) into a list of Indexes.
     * The expected input of these string should be of indexes.
     * Returns said list of Indexes.
     * @throws ParseException if an index is not of the right format.
     */
    private List<Index> parseIndexes(List<String> indexesToParse) throws ParseException {
        List<Index> collector = new ArrayList<>();
        try {
            for (String value : indexesToParse) {
                collector.add(ParserUtil.parseIndex(value));
            }
            return collector;
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, UpdateOngoingVisitCommand.MESSAGE_USAGE), pe);
        }
    }

    /**
     * Parse a list of string (presumably obtained from argMultimap.getAllValues) into a list of Indexes.
     * The expected input should be index, a whitespace, then the detail associated with the index.
     * Returns said list of Pairs of Index and String.
     * @throws ParseException if an index is not of the right format.
     */
    private List<Pair<Index, String>> parseIndexAndDetailPairs(List<String> indexAndDetailPairs)
            throws ParseException {
        List<Pair<Index, String>> collector = new ArrayList<>();
        Pattern regex = Pattern.compile(INDEX_WHITESPACE_THEN_STRING_VALIDATION_REGEX);
        try {
            for (String value : indexAndDetailPairs) {
                Matcher matcher = regex.matcher(value);
                //Check if is valid
                if (matcher.find()) {
                    collector.add(new Pair<>(
                            ParserUtil.parseIndex(matcher.group(REGEX_MATCHER_INDEX)),
                            matcher.group(REGEX_MATCHER_STRING).trim()
                    ));
                } else {
                    //Throw ParseException this way because ParserUtil also throws its own ParseException
                    throw new ParseException("");
                }
            }
            return collector;
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, UpdateOngoingVisitCommand.MESSAGE_USAGE), pe);
        }
    }
}
