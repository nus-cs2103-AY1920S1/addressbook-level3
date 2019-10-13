//package seedu.address.logic.parser.datamanagement;
//
//import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
//import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
//
//import java.util.stream.Stream;
//
//import seedu.address.logic.commands.cli.DeleteTagCommand;
//import seedu.address.logic.parser.ArgumentMultimap;
//import seedu.address.logic.parser.ArgumentTokenizer;
//import seedu.address.logic.parser.Parser;
//import seedu.address.logic.parser.ParserUtil;
//import seedu.address.logic.parser.Prefix;
//import seedu.address.logic.parser.exceptions.ParseException;
//import seedu.address.model.tag.Tag;
//
///**
// * Parses input arguments and creates a new DeleteTagCommand object
// */
//public class DeleteTagCommandParser implements Parser<DeleteTagCommand> {
//
//    /**
//     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
//     * {@code ArgumentMultimap}.
//     */
//    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
//        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
//    }
//
//    /**
//     * Parses the given {@code String} of arguments in the context of the DeleteTagCommand
//     * and returns an DeleteTagCommand object for execution.
//     *
//     * @throws ParseException if the user input does not conform the expected format
//     */
//    public DeleteTagCommand parse(String args) throws ParseException {
//        ArgumentMultimap argMultimap =
//                ArgumentTokenizer.tokenize(args, PREFIX_TAG);
//
//        if (!arePrefixesPresent(argMultimap, PREFIX_TAG)
//                || !argMultimap.getPreamble().isEmpty()) {
//            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
//                    DeleteTagCommand.MESSAGE_USAGE));
//        }
//        Tag tag = ParserUtil.parseTag(argMultimap.getValue(PREFIX_TAG).get());
//        return new DeleteTagCommand(tag);
//    }
//
//}