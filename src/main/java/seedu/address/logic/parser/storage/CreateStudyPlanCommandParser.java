package seedu.address.logic.parser.storage;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import seedu.address.logic.commands.CreateStudyPlanCommand;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.studyplan.StudyPlan;
import seedu.address.model.studyplan.Title;

/**
 * Parses input arguments and creates a new CreateStudyPlanCommand object
 */
public class CreateStudyPlanCommandParser implements Parser<CreateStudyPlanCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the
     * CreateStudyPlanCommand and returns an CreateStudyPlanCommand object for
     * execution.
     * 
     * @throws ParseException if the user input does not conform the expected format
                */
        public CreateStudyPlanCommand parse(String args) throws ParseException {
            String studyPlanName = args.trim();
            if (studyPlanName.isEmpty()) {
                throw new ParseException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, CreateStudyPlanCommand.MESSAGE_USAGE));
            }
        return new CreateStudyPlanCommand(new StudyPlan(new Title(studyPlanName)));
    }

}
