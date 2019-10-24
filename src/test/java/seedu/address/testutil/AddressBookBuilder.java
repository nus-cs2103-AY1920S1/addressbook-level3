package seedu.address.testutil;

import seedu.address.model.KeyboardFlashCards;
import seedu.address.model.flashcard.FlashCard;

/**
 * A utility class to help with building Addressbook objects.
 * Example usage: <br>
 *     {@code KeyboardFlashCards ab = new AddressBookBuilder().withFlashCard("John", "Doe").build();}
 */
public class AddressBookBuilder {

    private KeyboardFlashCards keyboardFlashCards;

    public AddressBookBuilder() {
        keyboardFlashCards = new KeyboardFlashCards();
    }

    public AddressBookBuilder(KeyboardFlashCards keyboardFlashCards) {
        this.keyboardFlashCards = keyboardFlashCards;
    }

    /**
     * Adds a new {@code FlashCard} to the {@code KeyboardFlashCards} that we are building.
     */
    public AddressBookBuilder withFlashCard(FlashCard flashCard) {
        keyboardFlashCards.addFlashcard(flashCard);
        return this;
    }

    public KeyboardFlashCards build() {
        return keyboardFlashCards;
    }
}
