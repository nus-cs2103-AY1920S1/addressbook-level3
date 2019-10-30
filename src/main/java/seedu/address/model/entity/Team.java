package seedu.address.model.entity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.logging.Logger;
import java.util.stream.Stream;

import seedu.address.commons.core.LogsCenter;

/**
 * Team is the main entity of this system.
 */
public class Team extends Entity {
    //dummy logger, to be deleted
    private final Logger logger = LogsCenter.getLogger(Team.class);
    private List<Participant> participants;
    private Optional<Mentor> mentor;
    private SubjectName subject;
    private Score score;
    private Name projectName;
    private Location location;

    public Team(
            Id teamId,
            Name teamName,
            List<Participant> participants,
            Optional<Mentor> mentor,
            SubjectName subject,
            Score score,
            Name projectName,
            Location location
    ) {
        super(teamId, teamName);
        this.participants = participants;
        this.mentor = mentor;
        this.subject = subject;
        this.score = score;
        this.projectName = projectName;
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

    /**
     * Updates the participant in the team.
     *
     * @param updatedParticipant
     * @return boolean
     */
    public boolean updateParticipant(Participant updatedParticipant) {
        List<Participant> list = this.getParticipants();
        boolean isParticipantInTeam = false;
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getId().equals(updatedParticipant.getId())) {
                list.set(i, updatedParticipant);
                isParticipantInTeam = true;
            }
        }
        return isParticipantInTeam;
    }

    /**
     * Adds a participant into the team.
     *
     * @param participant
     * @return boolean
     */
    public boolean addParticipant(Participant participant) {
        List<Participant> list = this.getParticipants();
        if (list.contains(participant)) {
            return false;
        }
        list.add(participant);
        this.setParticipants(list);
        return true;
    }

    /**
     * Deletes the participant from the team if it exists.
     *
     * @param participant
     * @return boolean
     */
    public boolean deleteParticipant(Participant participant) {
        if (!this.participants.contains(participant)) {
            return false;
        }
        this.participants.remove(participant);
        return true;
    }

    /**
     * Updates the mentor in the team if it exists.
     *
     * @param updatedMentor
     * @return boolean
     */
    public boolean updateMentor(Mentor updatedMentor) {
        if (this.mentor.isEmpty()) {
            return false;
        }

        if (!this.mentor.get().getId().equals(updatedMentor.getId())) {
            return false;
        }

        this.mentor = Optional.of(updatedMentor);
        return true;
    }

    /**
     * Adds a mentor to the team.
     *
     * @param mentor
     * @return
     */
    public boolean addMentor(Mentor mentor) {
        Optional<Mentor> mentorOptional = this.getMentor();
        if (!mentorOptional.isEmpty()) {
            return false;
        }
        this.setMentor(Optional.of(mentor));
        return true;
    }

    /**
     * Deletes the mentor if it exists.
     *
     * @param mentor
     * @return
     */
    public boolean deleteMentor(Mentor mentor) {
        if (this.mentor.isEmpty()) {
            return false;
        }

        if (!this.mentor.get().getId().equals(mentor.getId())) {
            return false;
        }

        this.mentor = Optional.empty();
        return true;
    }

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
        result.put("participants", participantsString);
        return result;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, participants, mentor, subject, score,
                projectName, location);
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
        return otherTeam.getName().equals(this.getName())
                && otherTeam.getId().equals(this.getId())
                && otherTeam.getParticipants().equals(this.getParticipants())
                && otherTeam.getSubject().equals(this.getSubject())
                && otherTeam.getLocation().equals(this.getLocation())
                && otherTeam.getMentor().equals(this.getMentor())
                && otherTeam.getScore().equals(this.getScore())
                && otherTeam.getProjectName().equals(this.getProjectName());
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        Stream<String> participantStream = participants.stream()
                .map(participant -> participant.toString());
        String participantsString = Arrays.toString(participantStream.toArray());

        builder.append(" Name: ")
                .append(getName())
                .append(", ID: ")
                .append(getId())
                .append(", Subject: ")
                .append(getSubject())
                .append(", Location: ")
                .append(getLocation())
                .append(", Mentor: ")
                .append(getMentor().orElse(null))
                .append(", Score: ")
                .append(getScore())
                .append(", Project Name: ")
                .append(getProjectName())
                .append(", Participants: ")
                .append(participantsString);

        return builder.toString();
    }

    /**
     * This offers a looser definition of equality for Team.
     *
     * @param otherTeam
     * @return boolean
     */
    public boolean isSameTeam(Team otherTeam) {
        if (otherTeam == this) {
            return true;
        }

        if (this.name.equals(otherTeam.getName())) {
            logger.severe("same name " + this.name + " and " + otherTeam.getName());
            return true;
        }

        if (this.projectName.equals(otherTeam.getProjectName())) {
            logger.severe("same pn" + this.projectName + " and " + otherTeam.getProjectName());
            return true;
        }

        if (this.id.equals(otherTeam.getId())) {
            logger.severe("same id:" + this.id + " and " + otherTeam.getId());
            return true;
        }
        return false;


        /*return this.name.equals(otherTeam.getName())
                || this.projectName.equals(otherTeam.getProjectName())
                || this.id.equals(otherTeam.getId());
                */

    }

    /**
     * Returns a deep copy of the Team object
     * @return Deep copy of the Team object
     */
    public Team copy() {
        List<Participant> pListCopy = new ArrayList<>();
        for (Participant p: this.participants) {
            pListCopy.add(p.copy());
        }

        Optional<Mentor> copiedMentor;
        if (this.mentor.isEmpty()) {
            copiedMentor = Optional.empty();
        } else {
            copiedMentor = Optional.of(this.mentor.get().copy());
        }

        return new Team(
                this.id.copy(),
                this.name.copy(),
                pListCopy,
                copiedMentor,
                this.subject,
                this.score.copy(),
                this.projectName.copy(),
                this.location.copy()
        );
    }

    @Override
    public PrefixType getPrefix() {
        return PrefixType.T;
    }
}
