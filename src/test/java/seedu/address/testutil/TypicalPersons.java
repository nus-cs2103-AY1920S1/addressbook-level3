package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_RATING_1;
import static seedu.address.logic.commands.CommandTestUtil.VALID_RATING_2;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_QUESTION_1;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ANSWER_2;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ANSWER_1;
import static seedu.address.logic.commands.CommandTestUtil.VALID_QUESTION_2;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.AddressBook;
import seedu.address.model.person.Person;

/**
 * A utility class containing a list of {@code Person} objects to be used in tests.
 */
public class TypicalPersons {

    public static final Person STORE_AND_FORWARD = new PersonBuilder().withQuestion("Store-and-forward")
            .withRating("good").withEmail("alice@example.com")
            .withAnswer("Entire packet must arrive ar a router before it can be transmitted on the next link")
            .withTags("friends").build();
    public static final Person DELAY = new PersonBuilder().withQuestion("End-to-end delay")
            .withRating("good")
            .withEmail("johnd@example.com").withAnswer("2L/R (assuming no other delay)")
            .withTags("owesMoney", "friends").build();
    public static final Person SOURCE_DELAY = new PersonBuilder().withQuestion("Sources of Packet Delay?").withAnswer("Nodal processing, queuing, transmission, propagation")
            .withEmail("heinz@example.com").withRating("good").build();
    public static final Person PROP_DELAY = new PersonBuilder().withQuestion("Formula for propagation delay").withAnswer("d, length of physical link (m) / s, progagation speed in medium (~2x10^8 m/sec) ")
            .withEmail("cornelia@example.com").withRating("good").withTags("friends").build();
    public static final Person TRANS_DELAY = new PersonBuilder().withQuestion("Formula for transmission delay").withAnswer("L, packet length (bits) / R, link bandwidth (bps)")
            .withEmail("werner@example.com").withRating("good").build();
    public static final Person THROUGHPUT = new PersonBuilder().withQuestion("Throughput definition").withAnswer("How many bits can be transmitted per unit time")
            .withEmail("lydia@example.com").withRating("easy").build();
    public static final Person PROTOCOL = new PersonBuilder().withQuestion("What is a Protocol").withAnswer("Protocols define format and order of messages exchanged and the action taken after messages are sent or received")
            .withEmail("anna@example.com").withRating("easy").build();

    // Manually added
    public static final Person GREETING = new PersonBuilder().withQuestion("How are you?").withAnswer("I am fine, thank you and you?")
            .withEmail("stefan@example.com").withRating("easy").build();
    public static final Person SUM = new PersonBuilder().withQuestion("1 + 1 = ").withAnswer("2")
            .withEmail("hans@example.com").withRating("good").build();

    // Manually added - Person's details found in {@code CommandTestUtil}
    public static final Person WWII = new PersonBuilder().withQuestion(VALID_QUESTION_1).withAnswer(VALID_ANSWER_1)
            .withEmail(VALID_EMAIL_AMY).withRating(VALID_RATING_1).withTags(VALID_TAG_FRIEND).build();
    public static final Person NUS = new PersonBuilder().withQuestion(VALID_QUESTION_2).withAnswer(VALID_ANSWER_2)
            .withEmail(VALID_EMAIL_BOB).withRating(VALID_RATING_2).withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND)
            .build();

    public static final String KEYWORD_MATCHING_SORT = "sort"; // A keyword that matches MEIER

    private TypicalPersons() {} // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical persons.
     */
    public static AddressBook getTypicalAddressBook() {
        AddressBook ab = new AddressBook();
        for (Person person : getTypicalPersons()) {
            ab.addPerson(person);
        }
        return ab;
    }

    public static List<Person> getTypicalPersons() {
        return new ArrayList<>(Arrays.asList(STORE_AND_FORWARD, DELAY, SOURCE_DELAY, PROP_DELAY, TRANS_DELAY, THROUGHPUT, PROTOCOL));
    }
}
