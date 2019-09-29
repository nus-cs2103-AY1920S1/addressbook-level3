package seedu.address.model.entity;

import java.util.Date;
import java.util.HashMap;
import java.util.Objects;
import java.util.Optional;

public class Issue extends Entity {
    private String description;
    private IssueType type;
    private boolean isCompleted;
    private Optional<Date> time;

    /**
     * Constructor with date.
     *
     * @param name
     * @param id
     * @param description
     * @param type
     * @param time
     * @param isCompleted
     */
    public Issue(
            Name name,
            Id id,
            String description,
            IssueType type,
            Optional<Date> time,
            boolean isCompleted
    ) {
        super(id, name);
        this.description = description;
        this.type = type;
        this.isCompleted = isCompleted;
        this.time = time;
    }

    // Getters

    public String getDescription() {
        return description;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public IssueType getType() {
        return type;
    }

    public Optional<Date> getTime() {
        return time;
    }

    // Setters

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }

    public void setType(IssueType type) {
        this.type = type;
    }

    public void setTime(Optional<Date> time) {
        this.time = time;
    }

    /**
     * Returns the minimal view of this entity.
     *
     * @return HashMap<String, String>
     */
    @Override
    public HashMap<String, String> viewMinimal() {
        HashMap<String, String> result = new HashMap<>();
        result.put("name", this.name.toString());
        result.put("id", this.id.toString());
        result.put("type", this.type.toString());
        result.put("isCompleted", Boolean.toString(this.isCompleted));
        return result;
    }

    /**
     * Returns the detailed view of this entity.
     *
     * @return HashMap<String, String>
     */
    @Override
    public HashMap<String, String> viewDetailed() {
        HashMap<String, String> result = new HashMap<>();
        result.put("name", this.name.toString());
        result.put("id", this.id.toString());
        result.put("type", this.type.toString());
        result.put("description", this.description);
        result.put("time", this.time.toString());
        result.put("isCompleted", Boolean.toString(this.isCompleted));
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(" Name: ")
                .append(getName())
                .append(" Id: ")
                .append(getId())
                .append(" Type: ")
                .append(getType())
                .append(" Description: ")
                .append(getDescription())
                .append(" Time: ")
                .append(getTime())
                .append("isCompleted")
                .append(isCompleted());
        return builder.toString();
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, id, type, description, time, isCompleted);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Issue)) {
            return false;
        }

        Issue otherIssue = ((Issue) other);
        return this.getName() == otherIssue.getName()
                && this.getId() == otherIssue.getId()
                && this.getType() == otherIssue.getType()
                && this.getDescription() == otherIssue.getDescription()
                && this.getTime() == otherIssue.getTime()
                && this.isCompleted() == otherIssue.isCompleted();
    }
}
