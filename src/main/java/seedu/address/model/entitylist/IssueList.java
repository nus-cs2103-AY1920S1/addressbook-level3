package seedu.address.model.entitylist;

import java.util.ArrayList;
import java.util.List;
import seedu.address.AlfredException;
import seedu.address.AlfredRuntimeException;
import seedu.address.model.entity.Entity;
import seedu.address.model.entity.Id;
import seedu.address.model.entity.Issue;
import seedu.address.model.entity.PrefixType;

public class IssueList extends EntityList {
    private List<Issue> issues;
    private int lastUsedId;

    /**
     * Constructor.
     *
     */
    public IssueList() {
        this.issues = new ArrayList<>();
        this.lastUsedId = 0;
    }

    /**
     * Gets Issue by ID.
     *
     * @param id
     * @return Issue
     * @throws AlfredException
     */
    public Issue get(Id id) throws AlfredException {
        for (Issue i: this.issues) {
            if (i.getId() == id) {
                return i;
            }
        }
        throw new AlfredRuntimeException("Issue to get cannot be found.");
    }

    /**
     * Updates Issue by ID.
     *
     * @param id
     * @param updatedIssue
     * @return boolean;
     */
    public boolean update(Id id, Issue updatedIssue) {
        for (int i = 0; i < this.issues.size(); i++) {
            if (this.issues.get(i).getId() == id) {
                this.issues.set(i, updatedIssue);
                return true;
            }
        }
        return false;
    }

    /**
     * Adds the issue into the list
     *
     * @param issue
     * @throws AlfredException
     */
    public void add(Issue issue) throws AlfredException {
        for (Issue i: this.issues) {
            if (i.getId() == issue.getId()) {
                throw new AlfredRuntimeException("Issue to add cannot be found.");
            }
        }
        this.issues.add(issue);
    }

    /**
     * Deletes team by id.
     *
     * @param id
     * @throws AlfredException
     */
    public Issue delete(Id id) throws AlfredException {
        for (Issue i: this.issues) {
            if (i.getId() == id) {
                this.issues.remove(i);
                return i;
            }
        }
        throw new AlfredRuntimeException("Issue to delete cannot be found.");
    }

    /**
     * Returns a list but with element type Issue.
     *
     * @return List<Issue>
     */
    public List<Issue> getSpecificTypedList() {
        return this.issues;
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
    public boolean contains(Id id) {
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
        this.lastUsedId++;
        return new Id(PrefixType.I, this.lastUsedId);
    }
}
