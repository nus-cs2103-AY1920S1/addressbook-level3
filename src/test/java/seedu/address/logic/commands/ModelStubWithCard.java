package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.model.card.Card;

/**
 * A Model stub that contains a single card.
 */
public class ModelStubWithCard extends ModelStub {
    private final Card card;

    ModelStubWithCard(Card card) {
        requireNonNull(card);
        this.card = card;
    }

    @Override
    public boolean hasCard(Card c) {
        requireNonNull(c);
        return this.card.isSameCard(c);
    }
}
