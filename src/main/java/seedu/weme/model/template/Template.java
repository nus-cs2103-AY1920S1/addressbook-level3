package seedu.weme.model.template;

import static seedu.weme.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

import seedu.weme.model.imagePath.ImagePath;

/**
 * Represents a meme template in weme.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Template {

    // Identity fields
    private final Name name;

    // Data fields
    private final ImagePath filePath;

    /**
     * Every field must be present and not null.
     */
    public Template(Name name, ImagePath filePath) {
        requireAllNonNull(filePath, name);
        this.name = name;
        this.filePath = filePath;
    }

    public Name getName() {
        return name;
    }

    public ImagePath getFilePath() {
        return filePath;
    }

    /**
     * Returns true if both templates have either the same name or the same image path.
     * This defines a stronger notion of equality between two templates.
     */
    public boolean isSameTemplate(Template otherTemplate) {
        if (otherTemplate == this) {
            return true;
        }

        return otherTemplate != null
            && (otherTemplate.getName().equals(getName())
            || otherTemplate.getFilePath().equals(getFilePath()));
    }

    /**
     * Returns true if both memes have the same identity and data fields.
     * This defines a stronger notion of equality between two memes.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Template)) {
            return false;
        }

        Template otherTemplate = (Template) other;
        return otherTemplate.getName().equals(getName())
            && otherTemplate.getFilePath().equals(getFilePath());
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, filePath);
    }

    @Override
    public String toString() {
        return " Name: " + getName();
    }

}
