package seedu.weme.testutil;

import java.util.HashSet;
import java.util.Set;

import seedu.weme.model.meme.Description;
import seedu.weme.model.meme.ImagePath;
import seedu.weme.model.meme.Meme;
import seedu.weme.model.tag.Tag;
import seedu.weme.model.util.SampleDataUtil;

/**
 * A utility class to help with building Meme objects.
 */
public class MemeBuilder {

    public static final String DEFAULT_DESCRIPTION = "Meme created for testing.";
    public static final String DEFAULT_FILEPATH = "src/test/data/memes/test_meme.jpg";

    private Description description;
    private ImagePath filePath;
    private Set<Tag> tags;

    public MemeBuilder() {
        filePath = new ImagePath(DEFAULT_FILEPATH);
        description = new Description(DEFAULT_DESCRIPTION);
        tags = new HashSet<>();
    }

    /**
     * Initializes the MemeBuilder with the data of {@code memeToCopy}.
     */
    public MemeBuilder(Meme memeToCopy) {
        description = memeToCopy.getDescription();
        filePath = memeToCopy.getFilePath();
        tags = new HashSet<>(memeToCopy.getTags());
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

    /**
     * Sets the {@code ImageUrl} of the {@code Meme} that we are building.
     * @param filePath
     */
    public MemeBuilder withFilePath(String filePath) {
        this.filePath = new ImagePath(filePath);
        return this;
    }

    public Meme build() {
        return new Meme(filePath, description, tags);
    }

}
