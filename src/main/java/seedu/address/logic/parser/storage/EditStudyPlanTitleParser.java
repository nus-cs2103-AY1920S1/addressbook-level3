package seedu.address.logic.parser.storage;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import seedu.address.logic.commands.EditStudyPlanTitleCommand;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.studyplan.StudyPlan;
import seedu.address.model.studyplan.Title;

/**
 * Parses input arguments and creates a new EditStudyPlanTitleCommand object
 */
public class EditStudyPlanTitleParser implements Parser<EditStudyPlanTitleCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the
     * EditStudyPlanTitleCommand and returns an EditStudyPlanTitleCommand object for
     * execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditStudyPlanTitleCommand parse(String args) throws ParseException {
        String studyPlanName = args.trim();
        if (studyPlanName.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditStudyPlanTitleCommand.MESSAGE_USAGE));
        }
        return new EditStudyPlanTitleCommand(new Title(studyPlanName));
    }

}
