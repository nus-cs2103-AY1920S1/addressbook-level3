package seedu.address.model.EntityList;

import java.util.ArrayList;
import java.util.List;
import seedu.address.model.Entity.Entity;
import seedu.address.model.Entity.Id;
import seedu.address.model.Entity.Issue;
import seedu.address.model.Entity.PrefixType;

public class IssueList extends EntityList {
    private List<Issue> issues;

    /**
     * Constructor.
     *
     */
    public IssueList() {
        super();
        this.issues = new ArrayList<>();
    }

    /**
     * Gets Issue by ID.
     *
     * @param id
     * @return
     */
    @Override
    public Issue get(Id id) {
        // TODO
        return null;
    }

    /**
     * Updates Issue by ID.
     *
     * @param issue
     * @throws Exception if error while updating
     */
    @Override
    public void update(Entity issue) throws Exception {
        // TODO
        issues.add((Issue) issue);
    }

    /**
     * Adds the issue into the list
     *
     * @param issue
     * @throws Exception
     */
    @Override
    public void add(Entity issue) throws Exception {
        issues.add((Issue) issue);
    }

    /**
     * Deletes team by id.
     *
     * @param id
     * @throws Exception
     */
    @Override
    public void delete(Id id) throws Exception {
        for (Issue i: this.issues) {
            if (i.getId() == id) {
                this.issues.remove(i);
                return;
            }
        }
    }

    /**
     * List the issues.
     *
     * @return List<Issue>
     */
    @Override
    public List<? extends Entity> list() {
        return this.issues;
    }

    /**
     * Checks if a given ID exists.
     *
     * @param id
     * @return boolean
     */
    @Override
    public boolean isContain(Id id) {
        for (Issue i: this.issues) {
            if (i.getId() == id) {
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
        return new Id(PrefixType.I, this.getNewIDSuffix());
    }
}
