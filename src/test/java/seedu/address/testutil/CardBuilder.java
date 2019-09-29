package seedu.address.testutil;

import java.util.HashSet;
import java.util.Set;

import seedu.address.model.card.Description;
import seedu.address.model.card.Name;
import seedu.address.model.card.Card;
import seedu.address.model.tag.Tag;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Card objects.
 */
public class CardBuilder {

    public static final String DEFAULT_NAME = "Pikachu";
    public static final String DEFAULT_DESCRIPTION = "This forest-dwelling Pokémon stores electricity in its cheeks, " +
            "so you'll feel a tingly shock if you touch it.";

    private Name name;
    private Description description;
    private Set<Tag> tags;

    public CardBuilder() {
        name = new Name(DEFAULT_NAME);
        description = new Description(DEFAULT_DESCRIPTION);
        tags = new HashSet<>();
    }

    /**
     * Initializes the CardBuilder with the data of {@code cardToCopy}.
     */
    public CardBuilder(Card cardToCopy) {
        name = cardToCopy.getName();
        description = cardToCopy.getDescription();
        tags = new HashSet<>(cardToCopy.getTags());
    }

    /**
     * Sets the {@code Name} of the {@code Card} that we are building.
     */
    public CardBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Card} that we are building.
     */
    public CardBuilder withTags(String ... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    /**
     * Sets the {@code Description} of the {@code Card} that we are building.
     */
    public CardBuilder withDescription(String description) {
        this.description = new Description(description);
        return this;
    }

    public Card build() {
        return new Card(name, description, tags);
    }

}
