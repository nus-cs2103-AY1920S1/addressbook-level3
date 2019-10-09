package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_ANSWER_1;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ANSWER_2;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CATEGORY_HISTORY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CATEGORY_LOCATION;
import static seedu.address.logic.commands.CommandTestUtil.VALID_QUESTION_1;
import static seedu.address.logic.commands.CommandTestUtil.VALID_QUESTION_2;
import static seedu.address.logic.commands.CommandTestUtil.VALID_RATING_1;
import static seedu.address.logic.commands.CommandTestUtil.VALID_RATING_2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.AddressBook;
import seedu.address.model.flashcard.FlashCard;

/**
 * A utility class containing a list of {@code FlashCard} objects to be used in tests.
 */
public class TypicalFlashCards {

    public static final FlashCard STORE_AND_FORWARD = new FlashCardBuilder().withQuestion("Store-and-forward")
            .withRating("good")
            .withAnswer("Entire packet must arrive at a router before it can be transmitted on the next link")
            .withCatgeories("CS2105").build();
    public static final FlashCard DELAY = new FlashCardBuilder().withQuestion("End-to-end delay")
            .withRating("good")
            .withAnswer("2L/R (assuming no other delay)")
            .withCatgeories("computerNetworking", "CS2105").build();
    public static final FlashCard SOURCE_DELAY = new FlashCardBuilder().withQuestion("Sources of Packet Delay?")
            .withAnswer("Nodal processing, queuing, transmission, propagation")
            .withRating("good").build();
    public static final FlashCard PROP_DELAY = new FlashCardBuilder().withQuestion("Formula for propagation delay")
            .withAnswer("d, length of physical link (m) / s, propagation speed in medium (~2x10^8 m/sec) ")
            .withRating("good").withCatgeories("Delay").build();
    public static final FlashCard TRANS_DELAY =
            new FlashCardBuilder().withQuestion("Formula for transmission delay")
                    .withAnswer("L, packet length (bits) / R, link bandwidth (bps)")
                    .withRating("good").withCatgeories("c")
                    .build();
    public static final FlashCard THROUGHPUT =
            new FlashCardBuilder().withQuestion("Throughput definition")
                    .withAnswer("How many bits can be transmitted per unit time")
                    .withRating("easy")
                    .build();
    public static final FlashCard PROTOCOL =
            new FlashCardBuilder().withQuestion("What is a Protocol")
                    .withAnswer("Protocols define format and order "
                            + "of messages exchanged and the action taken after "
                            + "messages are sent or received")
                    .withRating("easy")
                    .build();

    // Manually added
    public static final FlashCard GREETING = new
            FlashCardBuilder().withQuestion("How are you?")
            .withAnswer("I am fine, thank you and you?")
            .withRating("easy")
            .build();
    public static final FlashCard SUM =
            new FlashCardBuilder()
                    .withQuestion("1 + 1 = ")
                    .withAnswer("2")
                    .withRating("good")
                    .build();

    // Manually added - FlashCard's details found in {@code CommandTestUtil}
    public static final FlashCard WWII =
            new FlashCardBuilder().withQuestion(VALID_QUESTION_1)
                    .withAnswer(VALID_ANSWER_1)
                    .withRating(VALID_RATING_1)
                    .withCatgeories(VALID_CATEGORY_LOCATION)
                    .build();
    public static final FlashCard NUS =
            new FlashCardBuilder().withQuestion(VALID_QUESTION_2)
                    .withAnswer(VALID_ANSWER_2)
                    .withRating(VALID_RATING_2)
                    .withCatgeories(VALID_CATEGORY_HISTORY, VALID_CATEGORY_LOCATION)
                    .build();

    public static final String KEYWORD_MATCHING_SORT = "sort"; // A keyword that matches MEIER

    private TypicalFlashCards() {} // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical flashcards.
     */
    public static AddressBook getTypicalAddressBook() {
        AddressBook ab = new AddressBook();
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
}
