package seedu.address.model.EntityList;

import java.util.ArrayList;
import java.util.List;
import seedu.address.model.Entity.Entity;
import seedu.address.model.Entity.Id;
import seedu.address.model.Entity.PrefixType;
import seedu.address.model.Entity.Team;

public class TeamList extends EntityList {
    private List<Team> teams;

    /**
     * Constructor.
     *
     */
    public TeamList() {
        super();
        this.teams = new ArrayList<>();
    }

    /**
     * Gets team by their ID.
     *
     * @param id
     * @return
     */
    @Override
    public Team get(Id id) {
        // TODO
        return null;
    }

    /**
     * Updates team by ID.
     *
     * @param team
     * @throws Exception if error while updating
     */
    @Override
    public void update(Entity team) throws Exception {
        // TODO
        teams.add((Team) team);
    }

    /**
     * Adds the team into the list
     *
     * @param team
     * @throws Exception
     */
    @Override
    public void add(Entity team) throws Exception {
        teams.add((Team) team);
    }

    /**
     * Deletes team by id.
     *
     * @param id
     * @throws Exception
     */
    @Override
    public void delete(Id id) throws Exception {
        for (Team t: this.teams) {
            if (t.getId() == id) {
                this.teams.remove(t);
                return;
            }
        }
    }

    /**
     * List the teams.
     *
     * @return List<Team>
     */
    @Override
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
    public boolean isContain(Id id) {
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
    public Id generateID() {
        return new Id(PrefixType.T, this.getNewIDSuffix());
    }
}
