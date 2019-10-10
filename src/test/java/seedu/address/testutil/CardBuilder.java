package seedu.address.testutil;

import java.util.HashSet;
import java.util.Set;

import seedu.address.model.card.Card;
import seedu.address.model.card.Meaning;
import seedu.address.model.card.Word;
import seedu.address.model.tag.Tag;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Card objects.
 */
public class CardBuilder {

    public static final String DEFAULT_WORD = "Pikachu";
    public static final String DEFAULT_MEANING = "This forest-dwelling Pokémon stores electricity in its cheeks, "
            + "so you'll feel a tingly shock if you touch it.";

    private Word word;
    private Meaning meaning;
    private Set<Tag> tags;

    public CardBuilder() {
        word = new Word(DEFAULT_WORD);
        meaning = new Meaning(DEFAULT_MEANING);
        tags = new HashSet<>();
    }

    /**
     * Initializes the CardBuilder with the data of {@code cardToCopy}.
     */
    public CardBuilder(Card cardToCopy) {
        word = cardToCopy.getWord();
        meaning = cardToCopy.getMeaning();
        tags = new HashSet<>(cardToCopy.getTags());
    }

    /**
     * Sets the {@code Word} of the {@code Card} that we are building.
     */
    public CardBuilder withWord(String word) {
        this.word = new Word(word);
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
     * Sets the {@code Meaning} of the {@code Card} that we are building.
     */
    public CardBuilder withMeaning(String meaning) {
        this.meaning = new Meaning(meaning);
        return this;
    }

    public Card build() {
        return new Card(word, meaning, tags);
    }

}
