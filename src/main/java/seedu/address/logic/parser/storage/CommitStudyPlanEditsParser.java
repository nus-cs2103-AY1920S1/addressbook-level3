//package seedu.address.logic.parser.storage;
//
//import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
//import seedu.address.logic.commands.CommitStudyPlanEditCommand;
//import seedu.address.logic.parser.Parser;
//import seedu.address.logic.parser.exceptions.ParseException;
//import seedu.address.model.studyplan.StudyPlan;
//import seedu.address.model.studyplan.Title;
//
///**
// * Parses input arguments and creates a new CommitStudyPlanEditCommand object
// */
//public class CommitStudyPlanEditsParser implements Parser<CommitStudyPlanEditCommand> {
//    /**
//     * Parses the given {@code String} of arguments in the context of the
//     * CommitStudyPlanEditCommand and returns an CommitStudyPlanEditCommand object for
//     * execution.
//     *
//     * @throws ParseException if the user input does not conform the expected format
//     */
//    public CommitStudyPlanEditCommand parse(String args) throws ParseException {
//        String commitName = args.trim();
//        if (commitName.isEmpty()) {
//            throw new ParseException(
//                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, CommitStudyPlanEditCommand.MESSAGE_USAGE));
//        }
//        return new CommitStudyPlanEditCommand(new StudyPlan(new Title(commitName)));
//    }
//
//}
