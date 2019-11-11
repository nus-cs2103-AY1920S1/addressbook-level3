package seedu.address.testutil;

import java.util.HashSet;
import java.util.Set;

import seedu.address.model.card.Card;
import seedu.address.model.card.CardNumber;
import seedu.address.model.card.Cvc;
import seedu.address.model.card.Description;
import seedu.address.model.card.ExpiryDate;
import seedu.address.model.tag.Tag;
import seedu.address.model.util.SampleDataCardUtil;

/**
 * A utility class to help with building Card objects.
 */
public class CardBuilder {
    public static final String DEFAULT_DESCRIPTION = "VisaPOSB";
    public static final String DEFAULT_CARD_NUMBER = "4928371857283956";
    public static final String DEFAULT_CVC = "123";
    public static final String DEFAULT_EXPIRY = "12/25";

    private Description description;
    private CardNumber cardNumber;
    private Cvc cvc;
    private ExpiryDate expiry;
    private Set<Tag> tags;

    public CardBuilder() {
        description = new Description(DEFAULT_DESCRIPTION);
        cardNumber = new CardNumber(DEFAULT_CARD_NUMBER);
        cvc = new Cvc(DEFAULT_CVC);
        expiry = new ExpiryDate(DEFAULT_EXPIRY);
        tags = new HashSet<>();
    }

    /**
     * Initializes the CardBuilder with the data of {@code cardToCopy}.
     */
    public CardBuilder(Card cardToCopy) {
        description = cardToCopy.getDescription();
        cardNumber = cardToCopy.getCardNumber();
        cvc = cardToCopy.getCvc();
        expiry = cardToCopy.getExpiryDate();
        tags = new HashSet<>(cardToCopy.getTags());
    }

    /**
     * Sets the {@code Description} of the {@code Card} that we are building.
     */
    public CardBuilder withDescription(String name) {
        this.description = new Description(name);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Card}
     * that we are building.
     */
    public CardBuilder withTags(String ... tags) {
        this.tags = SampleDataCardUtil.getTagSet(tags);
        return this;
    }

    /**
     * Sets the {@code CardNumber} of the {@code Card} that we are building.
     */
    public CardBuilder withCardNumber(String cardNumber) {
        this.cardNumber = new CardNumber(cardNumber);
        return this;
    }

    /**
     * Sets the {@code Cvc} of the {@code Card} that we are building.
     */
    public CardBuilder withCvc(String cvc) {
        this.cvc = new Cvc(cvc);
        return this;
    }

    /**
     * Sets the {@code ExpiryDate} of the {@code Card} that we are building.
     */
    public CardBuilder withExpiryDate(String expiry) {
        this.expiry = new ExpiryDate(expiry);
        return this;
    }

    /**
     * Builds a new Card with inputted values
     * @return Card
     */
    public Card build() {
        return new Card(description,
                cardNumber, cvc, expiry, tags);
    }

}
