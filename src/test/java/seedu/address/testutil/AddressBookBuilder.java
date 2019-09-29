package seedu.address.testutil;

import seedu.address.model.WordBank;
import seedu.address.model.card.Card;

/**
 * A utility class to help with building Addressbook objects.
 * Example usage: <br>
 *     {@code AddressBook ab = new AddressBookBuilder().withPerson("John", "Doe").build();}
 */
public class AddressBookBuilder {

    private WordBank wordBank;

    public AddressBookBuilder() {
        wordBank = new WordBank();
    }

    public AddressBookBuilder(WordBank wordBank) {
        this.wordBank = wordBank;
    }

    /**
     * Adds a new {@code Person} to the {@code AddressBook} that we are building.
     */
    public AddressBookBuilder withPerson(Card person) {
        wordBank.addCard(person);
        return this;
    }

    public WordBank build() {
        return wordBank;
    }
}
