package seedu.weme.testutil;

import java.util.HashSet;
import java.util.Set;

import seedu.weme.model.meme.Description;
import seedu.weme.model.meme.Meme;
import seedu.weme.model.meme.Name;
import seedu.weme.model.tag.Tag;
import seedu.weme.model.util.SampleDataUtil;

/**
 * A utility class to help with building Meme objects.
 */
public class MemeBuilder {

    public static final String DEFAULT_NAME = "Alice Kingsleigh";
    public static final String DEFAULT_DESCRIPTION = "Meme created in CS2103 Lecture";

    private Name name;
    private Description description;
    private Set<Tag> tags;

    public MemeBuilder() {
        name = new Name(DEFAULT_NAME);
        description = new Description(DEFAULT_DESCRIPTION);
        tags = new HashSet<>();
    }

    /**
     * Initializes the MemeBuilder with the data of {@code memeToCopy}.
     */
    public MemeBuilder(Meme memeToCopy) {
        name = memeToCopy.getName();
        description = memeToCopy.getDescription();
        tags = new HashSet<>(memeToCopy.getTags());
    }

    /**
     * Sets the {@code Name} of the {@code Meme} that we are building.
     */
    public MemeBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Meme} that we are building.
     */
    public MemeBuilder withTags(String ... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    /**
     * Sets the {@code Description} of the {@code Meme} that we are building.
     * @param description
     */
    public MemeBuilder withDescription(String description) {
        this.description = new Description(description);
        return this;
    }

    public Meme build() {
        return new Meme(name, description, tags);
    }

}
