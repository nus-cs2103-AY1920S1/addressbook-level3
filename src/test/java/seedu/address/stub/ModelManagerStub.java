package seedu.address.stub;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javafx.collections.transformation.FilteredList;
import seedu.address.commons.exceptions.AlfredException;
import seedu.address.commons.exceptions.AlfredModelException;
import seedu.address.commons.exceptions.MissingEntityException;
import seedu.address.commons.exceptions.ModelValidationException;
import seedu.address.logic.commands.Command;
import seedu.address.model.ModelManager;
import seedu.address.model.entity.Id;
import seedu.address.model.entity.Mentor;
import seedu.address.model.entity.Participant;
import seedu.address.model.entity.Team;

/**
 * Stub for ModelManager in order to avoid saving of Entities created during tests.
 */
public class ModelManagerStub extends ModelManager {


    public ModelManagerStub() {
        super();
        this.filteredParticipantList =
                new FilteredList<>(this.participantList.getSpecificTypedList());
        this.filteredMentorList =
                new FilteredList<>(this.mentorList.getSpecificTypedList());
        this.filteredTeamList =
                new FilteredList<>(this.teamList.getSpecificTypedList());
    }

    //========== Entity Methods =============================

    /* Participant Methods */

    /**
     * Adds the participant into the list.
     */
    @Override
    public void addParticipant(Participant participant) throws AlfredException {
        this.participantList.add(participant);
    }

    /**
     * Updates the participant in the list, if any.
     */
    @Override
    public void updateParticipant(Id id, Participant participant) throws AlfredException {
        List<Team> targetTeams;
        try {
            targetTeams = this.getTeamByParticipantId(id);
        } catch (MissingEntityException e) {
            this.participantList.update(id, participant);
            return;
        }
        this.participantList.update(id, participant);

        boolean isSuccessful = targetTeams.stream()
                .map(team -> team.updateParticipant(participant))
                .allMatch(result -> result == true);

        if (!isSuccessful) {
            throw new ModelValidationException("Participant is not in the team provided");
        }
    }

    /**
     * Deletes the participant by id.
     */
    @Override
    public Participant deleteParticipant(Id id) throws AlfredException {
        Participant participantToDelete = this.participantList.delete(id); // May throw MissingEntityException here
        List<Team> targetTeams;
        try {
            targetTeams = this.getTeamByParticipantId(id);
        } catch (MissingEntityException e) {
            return participantToDelete;
        }

        boolean isSuccessful = targetTeams.stream()
                .map(team -> team.updateParticipant(participantToDelete))
                .allMatch(result -> result == true);

        if (!isSuccessful) {
            throw new ModelValidationException("Participant does not exist");
        }

        return participantToDelete;
    }

    /* Team Methods*/

    /**
     * Updates the team with the given teamID.
     */
    @Override
    public void updateTeam(Id teamId, Team updatedTeam) throws AlfredException {
        this.teamList.update(teamId, updatedTeam);
    }

    /**
     * Adds the team.
     */
    @Override
    public void addTeam(Team team) throws AlfredException {
        this.teamList.add(team);
    }

    /**
     * Gets the team by participant id.
     */
    public List<Team> getTeamByParticipantId(Id participantId) throws MissingEntityException {
        List<Team> teams = this.teamList.getSpecificTypedList();
        List<Team> results = new ArrayList<>();
        for (Team t : teams) {
            for (Participant p : t.getParticipants()) {
                if (p.getId().equals(participantId)) {
                    results.add(t);
                }
            }
        }
        if (results.size() == 0) {
            throw new MissingEntityException("Team with said participant cannot be found.");
        }
        return results;
    }

    /**
     * Gets the team by mentor id.
     */
    public List<Team> getTeamByMentorId(Id mentorId) throws MissingEntityException {
        List<Team> teams = this.teamList.getSpecificTypedList();
        List<Team> results = new ArrayList<>();
        for (Team t : teams) {
            Optional<Mentor> mentor = t.getMentor();
            if (mentor.isPresent()) {
                if (mentor.get().getId().equals(mentorId)) {
                    results.add(t);
                }
            }
        }
        if (results.size() == 0) {
            throw new MissingEntityException("Team with said participant cannot be found.");
        }
        return results;
    }

    /**
     * Adds the participant to the given team.
     */
    @Override
    public void addParticipantToTeam(Id teamId, Participant participant) throws AlfredException {
        if (!this.participantList.contains(participant.getId())) {
            throw new ModelValidationException("Participant does not exist in participantList");
        }
        Team targetTeam;
        try {
            targetTeam = this.getTeam(teamId);
        } catch (MissingEntityException e) {
            throw e;
        }
        try {
            boolean isSuccessful = targetTeam.addParticipant(participant);
            if (!isSuccessful) {
                throw new AlfredModelException("Participant is already present in team");
            }
        } catch (Exception e) {
            throw new AlfredModelException(e.getMessage());
        }
    }

    /**
     * Removes the participant from the given team.
     *
     * @param teamId
     * @param participant
     * @throws AlfredException if the team does not exist.
     */
    public void removeParticipantFromTeam(Id teamId, Participant participant) throws AlfredException {
        if (!this.participantList.contains(participant.getId())) {
            throw new ModelValidationException("Participant does not exist in participantList");
        }

        Team targetTeam;
        try {
            targetTeam = this.getTeam(teamId);
        } catch (MissingEntityException e) {
            throw e;
        }
        boolean isSuccessful = targetTeam.deleteParticipant(participant);

        if (!isSuccessful) {
            throw new AlfredModelException("Team does not have this Participant");
        }
    }

    /**
     * Adds the participant to the given team.
     *
     * @param teamId
     * @param mentor
     * @throws AlfredException if the team does not exist.
     */
    @Override
    public void addMentorToTeam(Id teamId, Mentor mentor) throws AlfredException {
        if (!this.mentorList.contains(mentor.getId())) {
            throw new ModelValidationException("Mentor does not exist in mentorList.");
        }
        Team targetTeam;
        try {
            targetTeam = this.getTeam(teamId);
        } catch (MissingEntityException e) {
            throw e;
        }
        boolean isSuccessful = targetTeam.addMentor(mentor);
        if (!isSuccessful) {
            throw new AlfredModelException("Team already has a mentor");
        }
    }

    /**
     * Deletes the team.
     */
    @Override
    public Team deleteTeam(Id id) throws AlfredException {
        // First delete the Participant objects
        Team teamToDelete = this.teamList.delete(id);
        for (Participant p : teamToDelete.getParticipants()) {
            this.participantList.delete(p.getId());
        }
        return teamToDelete;
    }

    /* Mentor Methods */

    /**
     * Adds mentor into the list.
     */
    @Override
    public void addMentor(Mentor mentor) throws AlfredException {
        this.mentorList.add(mentor);
    }

    /**
     * Updates the mentor.
     */
    @Override
    public void updateMentor(Id id, Mentor updatedMentor) throws AlfredException {
        List<Team> targetTeams;
        try {
            targetTeams = this.getTeamByMentorId(id);
        } catch (MissingEntityException e) {
            this.mentorList.update(id, updatedMentor);
            return;
        }

        this.mentorList.update(id, updatedMentor);
        boolean isSuccessful = targetTeams.stream()
                .map(team -> team.updateMentor(updatedMentor))
                .allMatch(result -> result == true);
        if (!isSuccessful) {
            throw new ModelValidationException("Unable to update the mentor in team as it is not the " + "same id");
        }
    }

    /**
     * Deletes the mentor.
     */
    @Override
    public Mentor deleteMentor(Id id) throws AlfredException {
        Mentor mentorToDelete = this.mentorList.delete(id); // May throw MissingEntityException here
        List<Team> targetTeams;
        try {
            targetTeams = this.getTeamByMentorId(id);
        } catch (MissingEntityException e) {
            return mentorToDelete;
        }

        boolean isSuccessful = targetTeams.stream()
                .map(team -> team.deleteMentor(mentorToDelete))
                .allMatch(result -> result == true);
        if (!isSuccessful) {
            throw new AlfredModelException("Update to delete the mentor from the team");
        }

        return mentorToDelete;
    }

    /**
     * Removes the mentor from the given team.
     *
     * @param teamId
     * @param mentor
     * @throws AlfredException if the team does not exist.
     */
    public void removeMentorFromTeam(Id teamId, Mentor mentor) throws AlfredException {
        if (!this.mentorList.contains(mentor.getId())) {
            throw new ModelValidationException("Mentor does not exist in mentorList.");
        }

        Team targetTeam;
        try {
            targetTeam = this.getTeam(teamId);
        } catch (MissingEntityException e) {
            throw e;
        }
        boolean isSuccessful = targetTeam.deleteMentor(mentor);
        if (!isSuccessful) {
            throw new AlfredModelException("Team does not have this Mentorr");
        }

    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof ModelManagerStub)) {
            return false;
        }
        ModelManagerStub model = (ModelManagerStub) other;
        return this.participantList.equals(model.participantList)
                && this.mentorList.equals(model.mentorList)
                && this.teamList.equals(model.teamList);
    }

    /**
     * Placeholder method simulating the updating of ModelHistory
     */
    @Override
    public void updateHistory(Command c) {
    }

    /**
     * Placeholder method simulating the undoing of a command in ModelHistory
     */
    @Override
    public void undo(int numToUndo) {
    }

    /**
     * Placeholder method simulating the redoing of a command in ModelHistory
     */
    @Override
    public void redo(int numToRedo) {
    }

    /**
     * Placeholder method simulating reseting of FilteredList
     */
    @Override
    public void resetFilteredLists() {

    }

    /**
     * Placeholder method simulating the recording of a command execution in CommandHistoryManager.
     */
    @Override
    public void recordCommandExecution(String commandInputString) {

    }
}
