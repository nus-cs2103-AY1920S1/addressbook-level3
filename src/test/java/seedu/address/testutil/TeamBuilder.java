package seedu.address.testutil;

import static seedu.address.testutil.TypicalIds.ID_FIRST_TEAM;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import seedu.address.model.entity.Id;
import seedu.address.model.entity.Location;
import seedu.address.model.entity.Mentor;
import seedu.address.model.entity.Name;
import seedu.address.model.entity.Participant;
import seedu.address.model.entity.PrefixType;
import seedu.address.model.entity.ProjectType;
import seedu.address.model.entity.Score;
import seedu.address.model.entity.SubjectName;
import seedu.address.model.entity.Team;
/**
 * Builds a valid {@link Team} to facilitate testing.
 */
public class TeamBuilder {

    public static final Id DEFAULT_ID = ID_FIRST_TEAM;
    public static final String DEFAULT_NAME = "Alice Pauline";
    public static final List<Participant> DEFAULT_PARTICIPANTS = new ArrayList<>();
    public static final Optional<Mentor> DEFAULT_MENTOR = Optional.empty();
    public static final String DEFAULT_SUBJECT = "ENVIRONMENTAL";
    public static final int DEFAULT_SCORE = 100;
    public static final String DEFAULT_PROJECT_NAME = "Hackathon Manager";
    public static final String DEFAULT_PROJECT_TYPE = "PLACEHOLDER";
    public static final int DEFAULT_LOCATION = 1;

    private Id id;
    private Name name;
    private List<Participant> participants;
    private Optional<Mentor> mentor;
    private SubjectName subject;
    private Score score;
    private Name projectName;
    private ProjectType projectType;
    private Location location;

    public TeamBuilder() {
        id = DEFAULT_ID;
        name = new Name(DEFAULT_NAME);
        participants = DEFAULT_PARTICIPANTS;
        mentor = DEFAULT_MENTOR;
        subject = SubjectName.valueOf(DEFAULT_SUBJECT);
        score = new Score(DEFAULT_SCORE);
        projectName = new Name(DEFAULT_PROJECT_NAME);
        projectType = ProjectType.valueOf(DEFAULT_PROJECT_TYPE);
        location = new Location(DEFAULT_LOCATION);
    }

    /**
     * Initializes the TeamBuilder with the data of {@code teamToCopy}.
     */
    public TeamBuilder(Team teamToCopy) {
        id = teamToCopy.getId();
        name = teamToCopy.getName();
        participants = teamToCopy.getParticipants();
        mentor = teamToCopy.getMentor();
        subject = teamToCopy.getSubject();
        score = teamToCopy.getScore();
        projectName = teamToCopy.getProjectName();
        projectType = teamToCopy.getProjectType();
        location = teamToCopy.getLocation();
    }

    /**
     * Sets the {@code Id} of the {@code Team} that we are building.
     */
    public TeamBuilder withId(int id) {
        this.id = new Id(PrefixType.P, id);
        return this;
    }

    /**
     * Sets the {@code Name} of the {@code Team} that we are building.
     */
    public TeamBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Sets the {@code Participants} of the {@code Team} that we are building.
     */
    public TeamBuilder withParticipants(List<Participant> participants) {
        this.participants = participants;
        return this;
    }

    /**
     * Sets the {@code Mentor} of the {@code Team} that we are building.
     */
    public TeamBuilder withMentor(Optional<Mentor> mentor) {
        this.mentor = mentor;
        return this;
    }

    /**
     * Sets the {@code Subject} of the {@code Team} that we are building.
     */
    public TeamBuilder withSubject(String subject) {
        this.subject = SubjectName.valueOf(subject.toUpperCase());
        return this;
    }

    /**
     * Sets the {@code Score} of the {@code Team} that we are building.
     */
    public TeamBuilder withScore(int score) {
        this.score = new Score(score);
        return this;
    }

    /**
     * Sets the {@code ProjectName} of the {@code Team} that we are building.
     */
    public TeamBuilder withProjectName(String projectName) {
        this.projectName = new Name(projectName);
        return this;
    }

    /**
     * Sets the {@code ProjectType} of the {@code Team} that we are building.
     */
    public TeamBuilder withProjectType(String projectType) {
        this.projectType = ProjectType.valueOf(projectType.toUpperCase());
        return this;
    }

    /**
     * Sets the {@code Location} of the {@code Team} that we are building.
     */
    public TeamBuilder withLocation(int location) {
        this.location = new Location(location);
        return this;
    }

    /**
     * This builds a standard {@code Team} object.
     */
    public Team build() {
        return new Team(id, name, participants, mentor, subject, score, projectName, projectType, location);
    }

}
