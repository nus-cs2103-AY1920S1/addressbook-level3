package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_ANSWER_1;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ANSWER_2;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CATEGORY_HISTORY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CATEGORY_LOCATION;
import static seedu.address.logic.commands.CommandTestUtil.VALID_QUESTION_1;
import static seedu.address.logic.commands.CommandTestUtil.VALID_QUESTION_2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import seedu.address.model.KeyboardFlashCards;
import seedu.address.model.deadline.Deadline;
import seedu.address.model.flashcard.FlashCard;

/**
 * A utility class containing a list of {@code FlashCard} objects to be used in tests.
 */
public class TypicalFlashCards {

    public static final FlashCard STORE_AND_FORWARD = new FlashCardBuilder().withQuestion("Store-and-forward")
            .withAnswer("Entire packet must arrive at a router before it can be transmitted on the next link")
            .withCatgeories("CS2105").build();
    public static final FlashCard DELAY = new FlashCardBuilder().withQuestion("End-to-end delay")
            .withAnswer("2L/R (assuming no other delay)")
            .withCatgeories("computerNetworking", "CS2105").build();
    public static final FlashCard SOURCE_DELAY = new FlashCardBuilder().withQuestion("Sources of Packet Delay?")
            .withAnswer("Nodal processing, queuing, transmission, propagation")
            .build();
    public static final FlashCard PROP_DELAY = new FlashCardBuilder().withQuestion("Formula for propagation delay")
            .withAnswer("d, length of physical link (m) / s, propagation speed in medium (~2x10^8 m/sec) ")
            .withCatgeories("Delay").build();
    public static final FlashCard TRANS_DELAY =
            new FlashCardBuilder().withQuestion("Formula for transmission delay")
                    .withAnswer("L, packet length (bits) / R, link bandwidth (bps)")
                    .withCatgeories("c")
                    .build();
    public static final FlashCard THROUGHPUT =
            new FlashCardBuilder().withQuestion("Throughput definition")
                    .withAnswer("How many bits can be transmitted per unit time")
                    .build();
    public static final FlashCard PROTOCOL =
            new FlashCardBuilder().withQuestion("What is a Protocol")
                    .withAnswer("Protocols define format and order "
                            + "of messages exchanged and the action taken after "
                            + "messages are sent or received")
                    .build();

    // Manually added
    public static final FlashCard GREETING = new
            FlashCardBuilder().withQuestion("How are you?")
            .withAnswer("I am fine, thank you and you?")
            .build();
    public static final FlashCard SUM =
            new FlashCardBuilder()
                    .withQuestion("1 + 1 = ")
                    .withAnswer("2")
                    .build();

    // Manually added - FlashCard's details found in {@code CommandTestUtil}
    public static final FlashCard WWII =
            new FlashCardBuilder().withQuestion(VALID_QUESTION_1)
                    .withAnswer(VALID_ANSWER_1)
                    .withCatgeories(VALID_CATEGORY_LOCATION)
                    .build();
    public static final FlashCard NUS =
            new FlashCardBuilder().withQuestion(VALID_QUESTION_2)
                    .withAnswer(VALID_ANSWER_2)
                    .withCatgeories(VALID_CATEGORY_HISTORY, VALID_CATEGORY_LOCATION)
                    .build();
    public static final FlashCard NORATING =
            new FlashCardBuilder().withQuestion(VALID_QUESTION_1)
                    .withAnswer(VALID_ANSWER_1)
                    .withCatgeories(VALID_CATEGORY_LOCATION)
                    .build();
    public static final String KEYWORD_MATCHING_SORT = "sort"; // A keyword that matches MEIER

    private TypicalFlashCards() {} // prevents instantiation

    /**
     * Returns an {@code KeyboardFlashCards} with all the typical flashcards.
     */
    public static KeyboardFlashCards getTypicalAddressBook() {
        KeyboardFlashCards ab = new KeyboardFlashCards();
        for (FlashCard flashCard : getTypicalFlashCards()) {
            ab.addFlashcard(flashCard);
        }
        return ab;
    }

    public static List<FlashCard> getTypicalFlashCards() {
        return new ArrayList<>(
                Arrays.asList(STORE_AND_FORWARD, DELAY, SOURCE_DELAY,
                        PROP_DELAY, TRANS_DELAY, THROUGHPUT, PROTOCOL));
    }

    public static List<FlashCard> getEmptyFlashCardList() {
        return Collections.emptyList();
    }

    public static List<FlashCard> getSingletonFlashCardList() {
        return Collections.singletonList(
                GREETING
        );
    }

    public static List<Deadline> getTypicalDeadlines() {
        return new ArrayList<>(
                Arrays.asList());
    }
}
