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
    private final int mcCount;

    // Data fields
    private final Set<Tag> tags = new HashSet<Tag>();
    private final UniqueModuleList prerequisites = new UniqueModuleList();

    /**
     * Every field must be present and not null.
     */
    public Module(Name name, ModuleCode moduleCode, int mcCount, Set<Tag> tags, UniqueModuleList prerequisites) {
        requireAllNonNull(name, moduleCode, tags);
        this.name = name;
        this.moduleCode = moduleCode;
        this.mcCount = mcCount;
        this.tags.addAll(tags);
        for (Module prerequisite : prerequisites) {
            this.prerequisites.add(prerequisite);
        }
    }

    public Name getName() {
        return name;
    }

    public ModuleCode getModuleCode() {
        return moduleCode;
    }

    public int getMcCount() {
        return mcCount;
    }

    /**
     * Adds the specified tag to the module if it is not already there.
     * @param tag Tag to be attached to the module.
     * @return True if the tag has been added and false otherwise.
     */
    public boolean addTag(Tag tag) {
        if (hasTag(tag)) {
            return false;
        }
        tags.add(tag);
        return true;
    }

    /**
     * Deletes the specified tag to the module if the module has it.
     * @param tag Tag to be deleted from the module.
     * @return True if the tag has been deleted and false otherwise.
     */
    public boolean deleteTag(Tag tag) {
        if (!hasTag(tag)) {
            return false;
        }
        tags.remove(tag);
        return true;
    }

    private boolean hasTag(Tag tag) {
        return tags.contains(tag);
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    /**
     * Returns an immutable prerequisite set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public UniqueModuleList getPrerequisites() {
        return prerequisites;
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
        return otherModule.getName().equals(getName())
                && otherModule.getModuleCode().equals(getModuleCode())
                && otherModule.getMcCount() == getMcCount()
                && otherModule.getTags().equals(getTags())
                && otherModule.getPrerequisites().equals(getPrerequisites());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, moduleCode, tags, prerequisites);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append(" Module code: ")
                .append(getModuleCode())
                .append(" MCs: ")
                .append(getMcCount())
                .append(" Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }
}
