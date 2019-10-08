package seedu.address.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.wordbank.WordBank;
import seedu.address.model.card.Card;

/**
 * A utility class containing a list of {@code Card} objects to be used in tests.
 */
public class TypicalCards {

    public static final Card ABRA = new CardBuilder().withName("Abra")
            .withDescription("It sleeps eighteen hours a day, but employs telekinesis even while sleeping.")
            .withTags("friends").build();
    public static final Card BUTTERFREE = new CardBuilder().withName("Butterfree")
            .withDescription("Its wings are covered with poisonous dust. If you see one flapping its wings, "
                    + "be careful not to inhale any of the dust.")
            .withTags("owesMoney", "friends").build();
    public static final Card CHARIZARD = new CardBuilder().withName("Charizard")
            .withDescription("It flies around the sky in search of powerful opponents. It breathes fire of such "
                    + "great heat that it melts anything. However, it never turns its fiery breath on any opponent "
                    + "weaker than itself.")
            .build();
    public static final Card DITTO = new CardBuilder().withName("Ditto")
            .withDescription("Its transformation ability is perfect. However, if it is made to laugh, it can't "
                    + "maintain its disguise.")
            .withTags("friends").build();
    public static final Card EEVEE = new CardBuilder().withName("Eevee")
            .withDescription("Possessing an unbalanced and unstable genetic makeup, it conceals many possible "
                    + "evolutions")
            .build();
    public static final Card FLAREON = new CardBuilder().withName("Flareon")
            .withDescription("It fluffs out its fur collar to cool down its body temperature, which can reach "
                    + "1,650 degrees.")
            .build();
    public static final Card GEODUDE = new CardBuilder().withName("Geodude")
            .withDescription("At rest, it looks just like a rock. Carelessly stepping on it will make it swing "
                    + "its fists angrily.")
            .build();

    public static final String KEYWORD_MATCHING_EE = "ee"; // A keyword that matches ee

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalCards() {} // prevents instantiation

    /**
     * Returns a {@code WordBank} with all the typical cards.
     */
    public static WordBank getTypicalWordBank() {
        WordBank wb = new WordBank();
        for (Card card : getTypicalCards()) {
            wb.addCard(card);
        }
        return wb;
    }

    public static List<Card> getTypicalCards() {
        return new ArrayList<>(Arrays.asList(ABRA, BUTTERFREE, CHARIZARD, DITTO, EEVEE, FLAREON, GEODUDE));
    }
}
