package seedu.address.model.module;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.model.tag.Tag;


/**
 * Represents a module for CS undergraduate students.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Module {

    // Identity fields
    private final Name name;
    private final ModuleCode moduleCode;

    // Data fields
    private final Set<Tag> tags = new HashSet<>();

    /**
     * Every field must be present and not null.
     */
    public Module(Name name, ModuleCode moduleCode, Set<Tag> tags) {
        requireAllNonNull(name, moduleCode, tags);
        this.name = name;
        this.moduleCode = moduleCode;
        this.tags.addAll(tags);
    }

    public Name getName() {
        return name;
    }

    public ModuleCode getModuleCode() {
        return moduleCode;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    /**
     * Returns true if both modules have the same identity and data fields.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof seedu.address.model.module.Module)) {
            return false;
        }

        seedu.address.model.module.Module otherModule = (seedu.address.model.module.Module) other;
        return otherModule.getName().equals(getName()) &&
                otherModule.getModuleCode().equals(getModuleCode()) &&
                otherModule.getTags().equals(getTags());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, moduleCode, tags);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append(" Module code: ")
                .append(getModuleCode())
                .append(" Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }
}
