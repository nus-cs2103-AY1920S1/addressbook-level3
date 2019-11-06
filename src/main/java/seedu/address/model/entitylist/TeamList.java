package seedu.address.model.entitylist;

import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import seedu.address.commons.core.LogsCenter;

import seedu.address.commons.exceptions.AlfredModelException;
import seedu.address.commons.exceptions.MissingEntityException;
import seedu.address.commons.exceptions.ModelValidationException;
import seedu.address.model.entity.Entity;
import seedu.address.model.entity.Id;
import seedu.address.model.entity.PrefixType;
import seedu.address.model.entity.SubjectName;
import seedu.address.model.entity.Team;

/**
 * This interface serves as the new API for the model.
 * {@code TeamList} should behave as a singleton.
 */
public class TeamList extends EntityList {
    public static final String SIMILAR_TEAM_MSG = "A similar Team already exists.";
    private static int lastUsedId = 0;
    private final Logger logger = LogsCenter.getLogger(TeamList.class);
    private final ObservableList<Team> teams = FXCollections.observableArrayList();
    private final ObservableList<Team> unmodifiableTeams =
            FXCollections.unmodifiableObservableList(teams);

    /**
     * Gets team by their ID.
     *
     * @param id
     * @return Team
     * @throws MissingEntityException if the team to get does not exist.
     */
    public Team get(Id id) throws MissingEntityException {
        for (Team t : this.teams) {
            if (t.getId().equals(id)) {
                return t;
            }
        }
        throw new MissingEntityException("Team to get does not exist!");
    }

    /**
     * Returns the size of ObservableList of Teams.
     * Used to set the lastUsedId during the intialization of model in ModelManager#intialize.
     * During the intialization, TeamList is set the the TeamList gotten from storage.
     *
     * @return size Number of Teams in TeamList
     */
    public int getSize() {
        return this.teams.size();
    }

    /**
     * Updates team by ID.
     *
     * @param id
     * @param updatedTeam
     * @throws MissingEntityException   if the team to update does not exist.
     * @throws ModelValidationException if a similar participant already exists.
     */
    public void update(Id id, Team updatedTeam)
            throws MissingEntityException, ModelValidationException {
        // First check if the updated team already exists
        for (Team t : this.teams) {
            if (t.isSameTeam(updatedTeam) && !t.getId().equals(updatedTeam.getId())) {
                throw new ModelValidationException(SIMILAR_TEAM_MSG);
            }
        }

        for (int i = 0; i < this.teams.size(); i++) {
            if (this.teams.get(i).getId().equals(id)) {
                this.teams.set(i, updatedTeam);
                return;
            }
        }
        throw new MissingEntityException("Team to update does not exist");
    }

    /**
     * Adds the team into the list.
     *
     * @param team
     * @throws AlfredModelException
     */
    public void add(Team team) throws AlfredModelException {
        for (Team t : this.teams) {
            if (t.isSameTeam(team)) {
                logger.severe("The same team already exist in TeamList of Model." + this.teams);
                throw new AlfredModelException("Team to add already exists.");
            }
        }
        this.teams.add(team);
        if (team.getId().getNumber() > lastUsedId) {
            lastUsedId = team.getId().getNumber();
        }
    }

    /**
     * Deletes team by id.
     *
     * @param id
     * @throws MissingEntityException if team to delete cannot be found.
     */
    public Team delete(Id id) throws MissingEntityException {
        for (Team t : this.teams) {
            if (t.getId().equals(id)) {
                this.teams.remove(t);
                return t;
            }
        }
        throw new MissingEntityException("Team to delete cannot be found.");
    }

    /**
     * Returns a list but with element type Team.
     *
     * @return List of Teams.
     */
    public ObservableList<Team> getSpecificTypedList() {
        return this.unmodifiableTeams;
    }

    /**
     * List the teams.
     *
     * @return List of Teams.
     */
    public ObservableList<? extends Entity> list() {
        return this.teams;
    }

    /**
     * List the unmodifiable team of mentors.
     *
     * @return {@code ObservableList<? extends Entity>}
     */
    @Override
    public ObservableList<? extends Entity> getUnmodifiableList() {
        return this.unmodifiableTeams;
    }

    /**
     * Checks if a given ID exists.
     *
     * @param id
     * @return boolean
     */
    @Override
    public boolean contains(Id id) {
        for (Team p : this.teams) {
            if (p.getId().equals(id)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if given {@code Entity} exists.
     */
    @Override
    public boolean contains(Entity entity) {
        if (!(entity instanceof Team)) {
            return false;
        }
        Team team = (Team) entity;
        for (Team t : this.teams) {
            if (t.isSameTeam(team) || t.getId().equals(team.getId())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if this {@code TeamList} is empty.
     */
    @Override
    public boolean isEmpty() {
        return this.teams.isEmpty();
    }

    /**
     * Generates the ID for the object.
     *
     * @return ID
     */
    public static Id generateId() {
        lastUsedId++;
        return new Id(PrefixType.T, lastUsedId);
    }

    /**
     * Gets the lastUsedId class attribute.
     * @return lastUsedId
     */
    public static int getLastUsedId() {
        return lastUsedId;
    }

    /**
     * Sets the lastUsedId class attribute.
     *
     * @param number
     */
    public static void setLastUsedId(int number) {
        lastUsedId = number;
    }

    /**
     * Provides a deep copy of the TeamList
     * @return Deep copy of TeamList
     */
    public TeamList copy() throws AlfredModelException {
        TeamList newTList = new TeamList();
        for (Team t: this.teams) {
            newTList.add(t.copy());
        }
        return newTList;
    }

    public long getEduTeam() {
        return teams.stream().filter(m -> m.getSubject().equals(SubjectName.EDUCATION)).count();
    }

    public long getEnvTeam() {
        return teams.stream().filter(m -> m.getSubject().equals(SubjectName.ENVIRONMENTAL)).count();
    }

    public long getSocialTeam() {
        return teams.stream().filter(m -> m.getSubject().equals(SubjectName.SOCIAL)).count();
    }

    public long getHealthTeam() {
        return teams.stream().filter(m -> m.getSubject().equals(SubjectName.HEALTH)).count();
    }

    @Override
    public PrefixType getPrefix() {
        return PrefixType.T;
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof TeamList)) {
            return false;
        }
        if (this == other) {
            return true;
        }
        TeamList teamList = (TeamList) other;
        return this.getSpecificTypedList().stream().allMatch(teamList::contains)
                && teamList.getSpecificTypedList().stream().allMatch(this::contains);
    }
}
