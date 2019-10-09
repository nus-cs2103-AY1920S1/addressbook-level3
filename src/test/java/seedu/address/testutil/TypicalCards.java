package seedu.address.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.card.Card;
import seedu.address.model.wordbank.WordBank;

/**
 * A utility class containing a list of {@code Card} objects to be used in tests.
 */
public class TypicalCards {

    public static final Card ABRA = new CardBuilder().withName("Abra")
            .withMeaning("It sleeps eighteen hours a day, but employs telekinesis even while sleeping.")
            .withTags("psychic").build();
    public static final Card BUTTERFREE = new CardBuilder().withName("Butterfree")
            .withMeaning("Its wings are covered with poisonous dust. If you see one flapping its wings, "
                    + "be careful not to inhale any of the dust.")
            .withTags("bug", "flying").build();
    public static final Card CHARIZARD = new CardBuilder().withName("Charizard")
            .withMeaning("It flies around the sky in search of powerful opponents. It breathes fire of such "
                    + "great heat that it melts anything. However, it never turns its fiery breath on any opponent "
                    + "weaker than itself.")
            .withTags("fire", "flying")
            .build();
    public static final Card DITTO = new CardBuilder().withName("Ditto")
            .withMeaning("Its transformation ability is perfect. However, if it is made to laugh, it can't "
                    + "maintain its disguise.")
            .withTags("normal").build();
    public static final Card EEVEE = new CardBuilder().withName("Eevee")
            .withMeaning("Possessing an unbalanced and unstable genetic makeup, it conceals many possible "
                    + "evolutions")
            .withTags("normal")
            .build();
    public static final Card FLAREON = new CardBuilder().withName("Flareon")
            .withMeaning("It fluffs out its fur collar to cool down its body temperature, which can reach "
                    + "1,650 degrees.")
            .withTags("fire")
            .build();
    public static final Card GEODUDE = new CardBuilder().withName("Geodude")
            .withMeaning("At rest, it looks just like a rock. Carelessly stepping on it will make it swing "
                    + "its fists angrily.")
            .withTags("ground", "rock")
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
