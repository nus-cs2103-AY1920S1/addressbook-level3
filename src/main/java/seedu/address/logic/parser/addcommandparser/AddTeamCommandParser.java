package seedu.address.logic.parser.addcommandparser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LOCATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PROJECT_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SUBJECT_NAME;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.addcommand.AddTeamCommand;
import seedu.address.logic.parser.AlfredParserUtil;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.entity.Id;
import seedu.address.model.entity.Location;
import seedu.address.model.entity.Mentor;
import seedu.address.model.entity.Name;
import seedu.address.model.entity.Participant;
import seedu.address.model.entity.Score;
import seedu.address.model.entity.SubjectName;
import seedu.address.model.entity.Team;
import seedu.address.model.entitylist.TeamList;

/**
 * Parses input arguments and creates a new {@link AddTeamCommand} object.
 */
public class AddTeamCommandParser implements Parser<AddTeamCommand> {

    private final Logger logger = new LogsCenter().getLogger(AddTeamCommandParser.class);

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddTeamCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_SUBJECT_NAME,
                        PREFIX_PROJECT_NAME, PREFIX_LOCATION);

        if (!AlfredParserUtil.arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_PROJECT_NAME,
                PREFIX_LOCATION, PREFIX_SUBJECT_NAME) || !argMultimap.getPreamble().isEmpty()) {
            logger.severe("Parse exception is thrown");
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    AddTeamCommand.MESSAGE_USAGE));
        }

        Name name = AlfredParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
        SubjectName subject = AlfredParserUtil.parseSubject(argMultimap.getValue(PREFIX_SUBJECT_NAME).get());
        Name projectName = AlfredParserUtil.parseName(argMultimap.getValue(PREFIX_PROJECT_NAME).get());
        Location location = AlfredParserUtil.parseLocation(argMultimap.getValue(PREFIX_LOCATION).get());
        Id id = TeamList.generateId();
        List<Participant> participants = new LinkedList<>();
        Score score = new Score(0);
        Optional<Mentor> mentor = Optional.empty();

        Team team = new Team(id, name, participants, mentor, subject, score, projectName, location);

        logger.info("New team added with the following information: " + team.toString());
        return new AddTeamCommand(team);
    }
}
