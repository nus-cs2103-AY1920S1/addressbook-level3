package cs.f10.t1.nursetraverse.logic.parser.visit;

import static cs.f10.t1.nursetraverse.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static cs.f10.t1.nursetraverse.logic.parser.CliSyntax.PREFIX_VISIT_REMARKS;
import static cs.f10.t1.nursetraverse.logic.parser.CliSyntax.PREFIX_VISIT_TASK_FINISH;
import static cs.f10.t1.nursetraverse.logic.parser.CliSyntax.PREFIX_VISIT_TASK_INDEX_AND_DETAIL;
import static cs.f10.t1.nursetraverse.logic.parser.CliSyntax.PREFIX_VISIT_TASK_UNFINISH;
import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cs.f10.t1.nursetraverse.commons.core.index.Index;
import cs.f10.t1.nursetraverse.logic.commands.visit.UpdateOngoingVisitCommand;
import cs.f10.t1.nursetraverse.logic.commands.visit.UpdateOngoingVisitDescriptor;
import cs.f10.t1.nursetraverse.logic.parser.ArgumentMultimap;
import cs.f10.t1.nursetraverse.logic.parser.ArgumentTokenizer;
import cs.f10.t1.nursetraverse.logic.parser.Parser;
import cs.f10.t1.nursetraverse.logic.parser.ParserUtil;
import cs.f10.t1.nursetraverse.logic.parser.exceptions.ParseException;
import javafx.util.Pair;

/**
 * Parses input arguments and creates a new UpdateOngoingVisitCommand object
 */
public class UpdateOngoingVisitCommandParser implements Parser<UpdateOngoingVisitCommand> {

    /*
     * Must match Index, whitespace then String.
     */
    public static final String INDEX_WHITESPACE_THEN_STRING_VALIDATION_REGEX = "^([0-9]+) (.*)";
    public static final String INDEX_ONLY_VALIDATION_REGEX = "^([0-9]+)()";
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

        UpdateOngoingVisitDescriptor updateOngoingVisitDescriptor =
                new UpdateOngoingVisitDescriptor();
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

        if (!updateOngoingVisitDescriptor.isAnyFieldUpdated()) {
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
        requireNonNull(indexesToParse);
        List<Index> collector = new ArrayList<>();
        try {
            for (String value : indexesToParse) {
                collector.add(ParserUtil.parseIndex(value));
            }
            return collector;
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                            UpdateOngoingVisitCommand.MESSAGE_USAGE), pe);
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
        requireNonNull(indexAndDetailPairs);
        List<Pair<Index, String>> collector = new ArrayList<>();
        Pattern regexIndexAndString = Pattern.compile(INDEX_WHITESPACE_THEN_STRING_VALIDATION_REGEX);
        Pattern regexIndexOnly = Pattern.compile(INDEX_ONLY_VALIDATION_REGEX);
        try {
            for (String value : indexAndDetailPairs) {
                Pair<Index, String> pairResult = regexMatch(regexIndexAndString, value);
                //If failed to find match i.e. possibly index only
                if (pairResult == null) {
                    pairResult = regexMatch(regexIndexOnly, value);
                }
                //If still fail then is parse error
                if (pairResult == null) {
                    //Throw ParseException this way because ParserUtil also throws its own ParseException
                    throw new ParseException("");
                } else {
                    collector.add(pairResult);
                }
            }
            return collector;
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                            UpdateOngoingVisitCommand.MESSAGE_USAGE), pe);
        }
    }

    /**
     * Helper method to check if value matches regex string.
     * Returns a Pair combining Index to String if matcher finds something, else returns null
     */
    private Pair<Index, String> regexMatch(Pattern regex, String value) throws ParseException {
        Matcher matcher = regex.matcher(value);
        if (matcher.find()) {
            return new Pair<>(
                    ParserUtil.parseIndex(matcher.group(REGEX_MATCHER_INDEX)),
                    matcher.group(REGEX_MATCHER_STRING).trim()
            );
        }
        return null;
    }
}
