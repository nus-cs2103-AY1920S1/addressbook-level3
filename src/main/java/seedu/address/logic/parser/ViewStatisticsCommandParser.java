//package seedu.address.logic.parser;
//
//import static seedu.address.commons.core.Messages.MESSAGE_INVALID_ARGUMENT_FORMAT;
//import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
//import static seedu.address.logic.parser.CliSyntax.*;
//
//import java.util.stream.Stream;
//
//import javax.swing.text.View;
//import seedu.address.logic.commands.SortCommand;
//import seedu.address.logic.commands.ViewStatisticsCommand;
//import seedu.address.logic.parser.exceptions.ParseException;
//import seedu.address.model.person.SortSequence;
//import seedu.address.model.person.SortType;
//
//public class ViewStatisticsCommandParser implements Parser<ViewStatisticsCommand> {
//
//    /**
//     * Parses the given {@code String} of arguments in the context of the SortCommand
//     * and returns a SortCommand object for execution.
//     * @throws ParseException if the user input does not conform the expected format
//     */
//    public ViewStatisticsCommand parse(String args) throws ParseException, IllegalArgumentException {
//        ArgumentMultimap argMultimap =
//                ArgumentTokenizer.tokenize(args, PREFIX_TYPE, PREFIX_PERIOD);
//
//        if (!arePrefixesPresent(argMultimap, PREFIX_TYPE, PREFIX_PERIOD)
//                || !argMultimap.getPreamble().isEmpty()) {
//            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewStatisticsCommand.MESSAGE_USAGE));
//        }
//        SortType type;
//        type = ParserUtil.(argMultimap.getValue(PREFIX_TYPE).get().toLowerCase());
//        SortSequence seq = ParserUtil.parseSortSequence(argMultimap.getValue(PREFIX_SEQUENCE).get().toLowerCase());
//        return new SortCommand(type, seq);
//    }
//
//
//    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
//        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
//    }
//}
