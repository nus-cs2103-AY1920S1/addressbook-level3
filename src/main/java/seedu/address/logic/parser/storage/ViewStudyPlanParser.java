//package seedu.address.logic.parser.storage;
//
//import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
//import seedu.address.logic.commands.ViewStudyPlanCommand;
//import seedu.address.logic.parser.Parser;
//import seedu.address.logic.parser.exceptions.ParseException;
//
///**
// * Parses input arguments and creates a new CreateStudyPlanCommand object
// */
//public class ViewStudyPlanParser implements Parser<ViewStudyPlanCommand> {
//    /**
//     * Parses the given {@code String} of arguments in the context of the
//     * ViewStudyPlanCommand and returns an ViewStudyPlanCommand object for
//     * execution.
//     *
//     * @throws ParseException if the user input does not conform the expected format
//     */
//    public ViewStudyPlanCommand parse(String args) throws ParseException {
//        String trimmedArgs = args.trim();
//        if (!trimmedArgs.matches("-?\\d+")) {
//            throw new ParseException(
//                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewStudyPlanCommand.MESSAGE_USAGE));
//        }
//        int studyPlanIndex = Integer.parseInt(trimmedArgs);
//        return new ViewStudyPlanCommand(studyPlanIndex);
//    }
//
//}
