package seedu.address.model.entitylist;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.exceptions.AlfredException;
import seedu.address.commons.exceptions.AlfredModelException;
import seedu.address.commons.exceptions.MissingEntityException;
import seedu.address.commons.exceptions.ModelValidationException;
import seedu.address.model.entity.Entity;
import seedu.address.model.entity.Id;
import seedu.address.model.entity.PrefixType;
import seedu.address.model.entity.Team;

/**
 * This interface serves as the new API for the model.
 * {@code TeamList} should behave as a singleton.
 */
public class TeamList extends EntityList {
    private static int lastUsedId = 0;
    private static final String SIMILAR_TEAM_MSG = "A team with the same name or project name "
            + "already exists.";

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
        for (Team t: this.teams) {
            if (t.getId().equals(id)) {
                return t;
            }
        }
        throw new MissingEntityException("Team to get does not exist!");
    }

    /**
     * Updates team by ID.
     *
     * @param id
     * @param updatedTeam
     * @throws MissingEntityException if the team to update does not exist.
     * @throws ModelValidationException if a similar participant already exists.
     */
    public void update(Id id, Team updatedTeam)
            throws MissingEntityException, ModelValidationException {
        // First check if the updated team already exists
        for (Team t: this.teams) {
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
     * @throws AlfredException
     */
    public void add(Team team) throws AlfredModelException {
        for (Team t: this.teams) {
            if (t.isSameTeam(team) || t.getId().equals(team.getId())) {
                throw new AlfredModelException("Team to add already exists.");
            }
        }
        this.teams.add(team);
    }

    /**
     * Deletes team by id.
     *
     * @param id
     * @throws MissingEntityException if team to delete cannot be found.
     */
    public Team delete(Id id) throws MissingEntityException {
        for (Team t: this.teams) {
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
        return this.teams;
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
        for (Team p: this.teams) {
            if (p.getId().equals(id)) {
                return true;
            }
        }
        return false;
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
     * Sets the lastUsedId class attribute.
     *
     * @param number
     */
    public static void setLastUsedId(int number) {
        lastUsedId = number;
    }
}
