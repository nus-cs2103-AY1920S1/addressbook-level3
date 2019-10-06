package seedu.address.model.entitylist;

import java.util.ArrayList;
import java.util.List;

import seedu.address.AlfredException;
import seedu.address.AlfredRuntimeException;
import seedu.address.model.entity.Entity;
import seedu.address.model.entity.Id;
import seedu.address.model.entity.PrefixType;
import seedu.address.model.entity.Team;

/**
 * This interface serves as the new API for the model.
 * {@code TeamList} should behave as a singleton.
 */
public class TeamList extends EntityList {
    private List<Team> teams;
    private int lastUsedId;

    /**
     * Constructor.
     */
    public TeamList() {
        this.teams = new ArrayList<>();
        this.lastUsedId = 0;
    }

    /**
     * Gets team by their ID.
     *
     * @param id
     * @return Team
     * @throws AlfredException
     */
    public Team get(Id id) throws AlfredException {
        for (Team t: this.teams) {
            if (t.getId() == id) {
                return t;
            }
        }
        throw new AlfredRuntimeException("Team to get does not exist!");
    }

    /**
     * Updates team by ID.
     *
     * @param id
     * @param updatedTeam
     * @return boolean
     */
    public boolean update(Id id, Team updatedTeam) {
        for (int i = 0; i < this.teams.size(); i++) {
            if (this.teams.get(i).getId() == id) {
                this.teams.set(i, updatedTeam);
                return true;
            }
        }
        return false;
    }

    /**
     * Adds the team into the list.
     *
     * @param team
     * @throws AlfredException
     */
    public void add(Team team) throws AlfredException {
        for (Team t: this.teams) {
            if (t.getId() == team.getId()) {
                throw new AlfredRuntimeException("Team to add already exists.");
            }
        }
        this.teams.add(team);
    }

    /**
     * Deletes team by id.
     *
     * @param id
     * @throws AlfredException
     */
    public Team delete(Id id) throws AlfredException {
        for (Team t: this.teams) {
            if (t.getId() == id) {
                this.teams.remove(t);
                return t;
            }
        }
        throw new AlfredRuntimeException("Team to delete cannot be found.");
    }

    /**
     * Returns a list but with element type Team.
     *
     * @return List of Teams.
     */
    public List<Team> getSpecificTypedList() {
        return this.teams;
    }

    /**
     * List the teams.
     *
     * @return List of Teams.
     */
    public List<? extends Entity> list() {
        return this.teams;
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
            if (p.getId() == id) {
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
    @Override
    public Id generateId() {
        this.lastUsedId++;
        return new Id(PrefixType.T, this.lastUsedId);
    }
}
