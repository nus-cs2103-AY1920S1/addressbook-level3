package seedu.system.logic.commands.outofsession;

import static java.util.Objects.requireNonNull;

import static seedu.system.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.system.logic.parser.CliSyntax.PREFIX_BENCH;
import static seedu.system.logic.parser.CliSyntax.PREFIX_COMP;
import static seedu.system.logic.parser.CliSyntax.PREFIX_DEADLIFT;
import static seedu.system.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.system.logic.parser.CliSyntax.PREFIX_SQUAT;

import java.util.List;

import seedu.system.logic.commands.Command;
import seedu.system.logic.commands.CommandResult;
import seedu.system.logic.commands.CommandType;
import seedu.system.logic.commands.exceptions.InSessionCommandException;
import seedu.system.model.Model;
import seedu.system.model.competition.Competition;
import seedu.system.model.participation.Participation;
import seedu.system.model.person.Name;
import seedu.system.model.person.Person;

/**
 * Add a new Participation that is associated with a Person and a Competition,
 * and his/her 9 attempts for the competition.
 */
public class AddParticipationCommand extends Command {

    public static final String COMMAND_WORD = "addParticipation";

    public static final CommandType COMMAND_TYPE = CommandType.PARTICIPATION;

    public static final String MESSAGE_ATHLETE_NOT_FOUND = "The athlete with the given name does not exist: ";

    public static final String MESSAGE_COMPETITION_NOT_FOUND = "The competition with the given name does not exist : ";

    public static final String MESSAGE_DUPLICATE_PARTICIPATION = "This participation already exists.";

    public static final String MESSAGE_SUCCESS =
            " is added to the participation list for competition "; // athlete name MESSAGE_SUCCESS competition

    public static final String MESSAGE_USAGE = COMMAND_WORD + " "
            + PREFIX_NAME + "Athlete_Name "
            + PREFIX_COMP + "Competition_Name "
            + PREFIX_SQUAT + "S1/S2/S3 "
            + PREFIX_BENCH + "B1/B2/B3 "
            + PREFIX_DEADLIFT + "D1/D2/D3 ";

    private final Name athleteName;
    private final Name competitionName;
    private final List<Integer> nineAttempts;

    public AddParticipationCommand(Name athleteName, Name compName, List<Integer> attemptWeights) {
        requireNonNull(athleteName);
        requireNonNull(compName);
        requireAllNonNull(attemptWeights);

        this.athleteName = athleteName;
        this.competitionName = compName;
        this.nineAttempts = attemptWeights;
    }

    @Override
    public CommandResult execute(Model model) throws InSessionCommandException {
        requireNonNull(model);

        if (model.hasOngoingSession()) {
            throw new InSessionCommandException();
        }

        Person athlete = null;
        Competition competition = null;

        // check if person/athlete exists
        List<Person> personList = model.getFilteredPersonList();
        for (Person p : personList) {
            if (p.getName().equals(athleteName)) {
                athlete = p;
                break;
            }
        }
        if (athlete == null) {
            return new CommandResult(MESSAGE_ATHLETE_NOT_FOUND + athleteName);
        }

        // check if competition exits
        List<Competition> competitionList = model.getFilteredCompetitionList();
        for (Competition c : competitionList) {
            if (c.getName().equals(competitionName)) {
                competition = c;
                break;
            }
        }
        if (competition == null) {
            return new CommandResult(MESSAGE_COMPETITION_NOT_FOUND + competitionName);
        }

        // since both person and competition are present, create the associated participation object
        Participation toAdd = new Participation(athlete, competition);
        toAdd.addAttempts(nineAttempts);
        if (model.hasParticipation(toAdd)) {
            return new CommandResult(MESSAGE_DUPLICATE_PARTICIPATION);
        }
        model.addParticipation(toAdd);
        return new CommandResult(athleteName + MESSAGE_SUCCESS + competitionName, COMMAND_TYPE);
    }
}
