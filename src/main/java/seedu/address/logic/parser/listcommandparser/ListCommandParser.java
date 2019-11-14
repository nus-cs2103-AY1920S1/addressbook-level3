package seedu.address.logic.parser.listcommandparser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.listcommand.ListCommand;
import seedu.address.logic.commands.listcommand.ListMentorCommand;
import seedu.address.logic.commands.listcommand.ListParticipantCommand;
import seedu.address.logic.commands.listcommand.ListTeamCommand;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses the input arguments and correspondingly returns the appropriate
 * ListCommand object for the given scenario.
 */
public class ListCommandParser implements Parser<ListCommand> {

    private static final String MENTOR_CASE = "mentors";
    private static final String PARTICIPANT_CASE = "participants";
    private static final String TEAM_CASE = "teams";

    /**
     * Parses the given {@code String} of arguments in the context of a ListCommand and returns
     * the appropriate ListCommand object for the specific case for execution.
     *
     * @param args the arguments to be parsed the ListCommandParser.
     * @return returns a new ListCommand object to be executed.
     * @throws ParseException if the user input does not conform to the expect format.
     */
    public ListCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim().toLowerCase();

        switch(trimmedArgs) {

        case MENTOR_CASE:
            return new ListMentorCommand();

        case PARTICIPANT_CASE:
            return new ListParticipantCommand();

        case TEAM_CASE:
            return new ListTeamCommand();

        default:
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    ListCommand.MESSAGE_USAGE));
        }
    }

}
