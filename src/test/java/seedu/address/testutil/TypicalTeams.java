package seedu.address.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import seedu.address.commons.exceptions.AlfredException;
import seedu.address.model.entity.Id;
import seedu.address.model.entity.Location;
import seedu.address.model.entity.Name;
import seedu.address.model.entity.Participant;
import seedu.address.model.entity.PrefixType;
import seedu.address.model.entity.ProjectType;
import seedu.address.model.entity.Score;
import seedu.address.model.entity.SubjectName;
import seedu.address.model.entity.Team;
import seedu.address.model.entitylist.TeamList;

/**
 * A utility class containing a list of {@code Team} objects to be used in tests.
 */
public class TypicalTeams {
    public static final Team A = new Team(new Id(PrefixType.T, 1),
                                          new Name("Team A"),
                                          initializeListA(),
                                          Optional.of(TypicalMentors.A),
                                          SubjectName.ENVIRONMENTAL,
                                          new Score(1),
                                          new Name("Project Alpha"),
                                          ProjectType.PLACEHOLDER,
                                          new Location(1));

    public static final Team A_UPDATED = new Team(new Id(PrefixType.T, 1),
            new Name("Team A"),
            initializeListA(),
            Optional.of(TypicalMentors.A),
            SubjectName.ENVIRONMENTAL,
            new Score(1),
            new Name("Project Alpha"),
            ProjectType.PLACEHOLDER,
            new Location(2));

    public static final Team A_SIMILAR = new Team(new Id(PrefixType.T, 2),
            new Name("Team B"),
            TypicalParticipants.getTypicalParticipants(),
            Optional.of(TypicalMentors.B),
            SubjectName.EDUCATION,
            new Score(2),
            new Name("Project Alpha"),
            ProjectType.PLACEHOLDER,
            new Location(2));

    public static final Team B = new Team(new Id(PrefixType.T, 2),
            new Name("Team B"),
            TypicalParticipants.getTypicalParticipants(),
            Optional.of(TypicalMentors.B),
                                          SubjectName.EDUCATION,
                                          new Score(2),
                                          new Name("Project Beta"),
                                          ProjectType.PLACEHOLDER,
                                         new Location(2));

    public static final Team C = new Team(new Id(PrefixType.T, 3),
                                          new Name("Team C"),
                                          TypicalParticipants.getTypicalParticipants(),
                                          Optional.of(TypicalMentors.C),
                                          SubjectName.HEALTH,
                                          new Score(3),
                                          new Name("Project Gamma"),
                                          ProjectType.PLACEHOLDER,
                                          new Location(3));

    public static List<Team> getTypicalTeams() {
        return new ArrayList<>(Arrays.asList(A, B, C));
    }

    public static TeamList getTypicalTeamList() throws AlfredException {
        TeamList tList = new TeamList();
        for (Team t: getTypicalTeams()) {
            tList.add(t);
        }
        return tList;
    }

    /**
     * Initialize the listA with the values needed.
     * @return {@code List<Participant>}
     */
    public static List<Participant> initializeListA() {
        List<Participant> listA = new ArrayList<>();
        listA.add(TypicalParticipants.A);
        return listA;
    }

    /**
     * Clears the team as this is a static variable.
     */
    public static void clearTeamA() {
        TypicalTeams.A.setParticipants(initializeListA());
    }

}
