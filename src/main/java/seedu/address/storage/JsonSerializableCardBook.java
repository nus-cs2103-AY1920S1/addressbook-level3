package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.CardBook;
import seedu.address.model.card.Card;

/**
 * An Immutable CardBook that is serializable to JSON format.
 */
@JsonRootName(value = "cardbook")
class JsonSerializableCardBook {

    public static final String MESSAGE_DUPLICATE_CARD = "Cards list contains duplicate card(s).";

    private final List<JsonAdaptedCard> cards = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableCardBook} with the given cards.
     */
    @JsonCreator
    public JsonSerializableCardBook(@JsonProperty("cards") List<JsonAdaptedCard> cards) {
        this.cards.addAll(cards);
    }

    /**
     * Converts a given {@code CardBook} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableCardBook}.
     */
    public JsonSerializableCardBook(CardBook source) {
        cards.addAll(source.getCardList().stream().map(JsonAdaptedCard::new).collect(Collectors.toList()));
    }

    /**
     * Converts this card book into the model's {@code CardBook} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public CardBook toModelType() throws IllegalValueException {
        CardBook cardBook = new CardBook();
        for (JsonAdaptedCard jsonAdaptedCard : cards) {
            Card card = jsonAdaptedCard.toModelType();
            if (cardBook.hasCard(card)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_CARD);
            }
            cardBook.addCard(card);
        }
        return cardBook;
    }

}
