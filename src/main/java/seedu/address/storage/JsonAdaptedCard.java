package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.card.Card;
import seedu.address.model.card.CardNumber;
import seedu.address.model.card.Cvc;
import seedu.address.model.card.Description;
import seedu.address.model.tag.Tag;

/**
 * Jackson-friendly version of {@link Card}.
 */
class JsonAdaptedCard {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Card's %s field is missing!";

    private final String description;
    private final String cardNumber;
    private final String cvc;
    private final List<JsonAdaptedTag> tagged = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedCard} with the given card details.
     */
    @JsonCreator
    public JsonAdaptedCard(@JsonProperty("description") String description,
                           @JsonProperty("cardNumber") String cardNumber,
                           @JsonProperty("cvc") String cvc,
                           @JsonProperty("tagged") List<JsonAdaptedTag> tagged) {
        this.description = description;
        this.cardNumber = cardNumber;
        this.cvc = cvc;
        if (tagged != null) {
            this.tagged.addAll(tagged);
        }
    }

    /**
     * Converts a given {@code Card} into this class for Jackson use.
     */
    public JsonAdaptedCard(Card source) {
        description = source.getDescription().value;
        cardNumber = source.getCardNumber().value;
        cvc = source.getCvc().value;
        tagged.addAll(source.getTags().stream()
                .map(JsonAdaptedTag::new)
                .collect(Collectors.toList()));
    }

    /**
     * Converts this Jackson-friendly adapted card object into the model's {@code Card} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted card.
     */
    public Card toModelType() throws IllegalValueException {
        final List<Tag> cardTags = new ArrayList<>();
        for (JsonAdaptedTag tag : tagged) {
            cardTags.add(tag.toModelType());
        }

        if (description == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Description.class.getSimpleName()));
        }
        if (!Description.isValidDescription(description)) {
            throw new IllegalValueException(Description.MESSAGE_CONSTRAINTS);
        }
        final Description modelDescription = new Description(description);

        if (cardNumber == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    CardNumber.class.getSimpleName()));
        }
        if (!CardNumber.isValidCardNumber(cardNumber)) {
            throw new IllegalValueException(CardNumber.MESSAGE_CONSTRAINTS);
        }
        final CardNumber modelCardNumber = new CardNumber(cardNumber);

        if (cvc == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Cvc.class.getSimpleName()));
        }
        if (!Cvc.isValidCvc(cvc)) {
            throw new IllegalValueException(Cvc.MESSAGE_CONSTRAINTS);
        }
        final Cvc modelCvc = new Cvc(cvc);

        final Set<Tag> modelTags = new HashSet<>(cardTags);
        return new Card(modelDescription, modelCardNumber, modelCvc, modelTags);
    }

}
