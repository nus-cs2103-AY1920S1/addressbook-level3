// @@author chrischenhui
package seedu.address.storage.wordbanks;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.card.Card;
import seedu.address.model.card.Meaning;
import seedu.address.model.card.Word;
import seedu.address.model.tag.Tag;

/**
 * Jackson-friendly version of {@link Card}.
 */
public class JsonAdaptedCard {
    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Card's %s field is missing!";

    private final String id;
    private final String name;
    private final String description;
    private final List<JsonAdaptedTag> tagged = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedCard} with the given card details.
     */
    @JsonCreator
    public JsonAdaptedCard(@JsonProperty("id") String id,
                           @JsonProperty("name") String name,
                           @JsonProperty("description") String description,
                           @JsonProperty("tagged") List<JsonAdaptedTag> tagged) {
        this.id = id;
        this.name = name;
        this.description = description;
        if (tagged != null) {
            this.tagged.addAll(tagged);
        }
    }

    /**
     * Converts a given {@code Card} into this class for Jackson use.
     */
    public JsonAdaptedCard(Card source) {
        this.id = source.getId();
        this.name = source.getWord().toString();
        this.description = source.getMeaning().toString();
        tagged.addAll(source.getTags().stream()
                .map(JsonAdaptedTag::new)
                .collect(Collectors.toList()));
    }

    /**
     * Converts this Jackson-friendly adapted card object into the model's {@code Card} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person.
     */
    public Card toModelType() throws IllegalValueException {
        final List<Tag> cardTags = new ArrayList<>();
        for (JsonAdaptedTag tag : tagged) {
            cardTags.add(tag.toModelType());
        }

        if (id == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, String.class.getSimpleName()));
        }

        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Word.class.getSimpleName()));
        }
        if (!Word.isValidWord(name)) {
            throw new IllegalValueException(Word.MESSAGE_CONSTRAINTS);
        }
        final Word modelWord = new Word(name);

        if (description == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Meaning.class.getSimpleName()));
        }
        if (!Meaning.isValidMeaning(description)) {
            throw new IllegalValueException(Meaning.MESSAGE_CONSTRAINTS);
        }
        final Meaning modelMeaning = new Meaning(description);

        final Set<Tag> modelTags = new HashSet<>(cardTags);

        if (id == null) {
            return Card.createNewCard(modelWord, modelMeaning, modelTags);
        } else {
            return new Card(id, modelWord, modelMeaning, modelTags);
        }
    }

}
