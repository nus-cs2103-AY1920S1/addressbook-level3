package seedu.address.model.entity;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * Team is the main entity of this system.
 */
public class Team extends Entity {
    private List<Participant> participants;
    private Optional<Mentor> mentor;
    private SubjectName subject;
    private Score score;
    private Name projectName;
    private ProjectType projectType;
    private Location location;

    /**
     * Constructor with mentor.
     *
     * @param teamId
     * @param teamName
     * @param participants
     * @param subject
     * @param score
     * @param projectName
     * @param projectType
     * @param location
     */
    public Team(
            Id teamId,
            Name teamName,
            List<Participant> participants,
            Optional<Mentor> mentor,
            SubjectName subject,
            Score score,
            Name projectName,
            ProjectType projectType,
            Location location
    ) {
        super(teamId, teamName);
        this.participants = participants;
        this.mentor = mentor;
        this.subject = subject;
        this.score = score;
        this.projectName = projectName;
        this.projectType = projectType;
        this.location = location;
    }

    // Getters

    public List<Participant> getParticipants() {
        return this.participants;
    }

    public Optional<Mentor> getMentor() {
        return this.mentor;
    }

    public SubjectName getSubject() {
        return this.subject;
    }

    public Score getScore() {
        return this.score;
    }

    public Name getProjectName() {
        return this.projectName;
    }

    public ProjectType getProjectType() {
        return this.projectType;
    }

    public Location getLocation() {
        return this.location;
    }

    // Setters

    public void setParticipants(List<Participant> participants) {
        this.participants = participants;
    }

    public void setMentor(Optional<Mentor> mentor) {
        this.mentor = mentor;
    }

    public void setSubject(SubjectName subject) {
        this.subject = subject;
    }

    public void setScore(Score score) {
        this.score = score;
    }

    public void setProjectName(Name projectName) {
        this.projectName = projectName;
    }

    public void setProjectType(ProjectType projectType) {
        this.projectType = projectType;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    /*
    @Override
    public HashMap<String, String> viewMinimal() {
        HashMap<String, String> result = new HashMap<>();
        Stream<String> participantStream = participants.stream()
                .map(participant -> participant.toString());
        String participantsString = Arrays.toString(participantStream.toArray());
        result.put("name", getName().toString());
        result.put("id", getId().toString());
        result.put("participants", participantsString);
        return result;
    }

     */

    @Override
    public HashMap<String, String> viewDetailed() {
        HashMap<String, String> result = new HashMap<>();
        Stream<String> participantStream = participants.stream()
                .map(participant -> participant.toString());
        String participantsString = Arrays.toString(participantStream.toArray());
        Mentor mentor = getMentor().orElse(null);

        result.put("name", getName().toString());
        result.put("id", getId().toString());
        result.put("participants", participantsString);
        result.put("subject", getSubject().toString());
        result.put("location", getLocation().toString());
        result.put("mentor", mentor.toString());
        result.put("score", score.toString());
        result.put("projectName", projectName.toString());
        result.put("projectType", projectType.toString());
        result.put("participants", participantsString);
        return result;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, participants, mentor, subject, score,
                projectName, projectType, location);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Team)) {
            return false;
        }

        Team otherTeam = ((Team) other);
        return otherTeam.getName() == this.getName()
               && otherTeam.getId() == this.getId()
               && otherTeam.getParticipants() == this.getParticipants()
               && otherTeam.getSubject() == this.getSubject()
               && otherTeam.getLocation() == this.getLocation()
               && otherTeam.getMentor() == this.getMentor()
               && otherTeam.getScore() == this.getScore()
               && otherTeam.getProjectName() == this.getProjectName()
               && otherTeam.getProjectType() == this.getProjectType();
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        Stream<String> participantStream = participants.stream()
                .map(participant -> participant.toString());
        String participantsString = Arrays.toString(participantStream.toArray());

        builder.append(" Name: ")
                .append(getName())
                .append(" ID: ")
                .append(getId())
                .append(" Subject: ")
                .append(getSubject())
                .append(" Location: ")
                .append(getLocation())
                .append(" Mentor: ")
                .append(getMentor().orElse(null))
                .append(" Score: ")
                .append(getScore())
                .append(" Project Name: ")
                .append(getProjectName())
                .append(" Project Type: ")
                .append(getProjectType())
                .append(" Participants: ")
                .append(participantsString);

        return builder.toString();
    }

}
