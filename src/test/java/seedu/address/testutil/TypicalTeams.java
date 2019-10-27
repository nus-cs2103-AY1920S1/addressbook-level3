package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_LOCATION_ALFRED;
import static seedu.address.logic.commands.CommandTestUtil.VALID_LOCATION_BRUCE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_ALFRED;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BRUCE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PROJECT_NAME_ALFRED;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PROJECT_NAME_BRUCE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SUBJECT_ALFRED;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SUBJECT_BRUCE;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import seedu.address.commons.exceptions.AlfredModelException;
import seedu.address.model.entity.Id;
import seedu.address.model.entity.Location;
import seedu.address.model.entity.Name;
import seedu.address.model.entity.Participant;
import seedu.address.model.entity.PrefixType;
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
                                          new Location(1));

    public static final Team A_UPDATED = new Team(new Id(PrefixType.T, 1),
            new Name("Team A"),
            initializeListA(),
            Optional.of(TypicalMentors.A),
            SubjectName.ENVIRONMENTAL,
            new Score(1),
            new Name("Project Alpha"),
            new Location(2));

    public static final Team A_SIMILAR = new Team(new Id(PrefixType.T, 2),
            new Name("Team B"),
            TypicalParticipants.getTypicalParticipants(),
            Optional.of(TypicalMentors.B),
            SubjectName.EDUCATION,
            new Score(2),
            new Name("Project Alpha"),
            new Location(2));

    public static final Team B = new Team(new Id(PrefixType.T, 2),
            new Name("Team B"),
            TypicalParticipants.getTypicalParticipants(),
            Optional.of(TypicalMentors.B),
                                          SubjectName.EDUCATION,
                                          new Score(2),
                                          new Name("Project Beta"),
                                         new Location(2));

    public static final Team C = new Team(new Id(PrefixType.T, 3),
                                          new Name("Team C"),
                                          TypicalParticipants.getTypicalParticipants(),
                                          Optional.of(TypicalMentors.C),
                                          SubjectName.HEALTH,
                                          new Score(3),
                                          new Name("Project Gamma"),
                                          new Location(3));

    //With empty Optional<Mentor>
    public static final Team D = new Team(new Id(PrefixType.T, 4),
                                          new Name("Team D"),
                                          TypicalParticipants.getTypicalParticipants(),
                                          Optional.empty(),
                                          SubjectName.HEALTH,
                                          new Score(5),
                                          new Name("Project Delta"),
                                          new Location(4));

    //With empty ParticipantList
    public static final Team E = new Team(new Id(PrefixType.T, 5),
                                          new Name("Team E"),
                                          new LinkedList<Participant>(),
                                          Optional.of(TypicalMentors.C),
                                          SubjectName.HEALTH,
                                          new Score(5),
                                          new Name("Project Epsilon"),
                                          new Location(5));


    // Manually added - Person's details found in {@code CommandTestUtil}
    public static final Team ALFRED = new TeamBuilder().withName(VALID_NAME_ALFRED).withLocation(VALID_LOCATION_ALFRED)
            .withProjectName(VALID_PROJECT_NAME_ALFRED).withSubject(VALID_SUBJECT_ALFRED).build();

    public static final Team BRUCE = new TeamBuilder().withName(VALID_NAME_BRUCE).withLocation(VALID_LOCATION_BRUCE)
            .withProjectName(VALID_PROJECT_NAME_BRUCE).withSubject(VALID_SUBJECT_BRUCE).build();

    /**
     * Retrieves a List of Typical Teams.
     * @return List of Typical Teams
     */
    public static List<Team> getTypicalTeams() {
        return new ArrayList<>(Arrays.asList(A, B, C));
    }

    /**
     * Retrieves a typical TeamList.
     * @return typical TeamList
     */
    public static TeamList getTypicalTeamList() throws AlfredModelException {
        TeamList tList = new TeamList();
        for (Team t: getTypicalTeams()) {
            tList.add(t);
        }
        return tList;
    }

    /**
     * Retrieves a List of Teams with a single Team that contains an Optional Mentor.
     * @return List of Teams with a single Team that contains an Optional Mentor
     */
    public static List<Team> getTypicalTeamsWithOptionalMentor() {
        return new ArrayList<>(Arrays.asList(A, B, D)); //D contains the Optional Mentor
    }

    /**
     * Retrieves a TeamList with a single Team that contains an Optional Mentor.
     * @return TeamList with a single Team that contains an Optional Mentor
     */
    public static TeamList getTeamListWithOptionalMentor() throws AlfredModelException {
        TeamList tList = new TeamList();
        for (Team t: getTypicalTeamsWithOptionalMentor()) {
            tList.add(t);
        }
        return tList;
    }

    /**
     * Retrieves a List of Teams with a single Team that contains an empty ParticipantList.
     * @return List of Teams with a single Team that contains an empty ParticipantList
     */
    public static List<Team> getTypicalTeamsWithEmptyParticipantList() {
        return new ArrayList<>(Arrays.asList(A, B, E)); //E contains the empty ParticipantList
    }

    /**
     * Retrieves a TeamList with a single Team that contains an empty ParticipantList.
     * @return TeamList with a single Team that contains an empty ParticipantList
     */
    public static TeamList getTeamListWithEmptyParticipantList() throws AlfredModelException {
        TeamList tList = new TeamList();
        for (Team t: getTypicalTeamsWithEmptyParticipantList()) {
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
