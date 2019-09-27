package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
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
            .withAddress("123, Jurong West Ave 6, #08-111").withEmail("alice@example.com")
            .withAnswer("Entire packet must arrive ar a router before it can be transmitted on the next link")
            .withTags("friends").build();
    public static final Person DELAY = new PersonBuilder().withQuestion("End-to-end delay")
            .withAddress("311, Clementi Ave 2, #02-25")
            .withEmail("johnd@example.com").withAnswer("2L/R (assuming no other delay)")
            .withTags("owesMoney", "friends").build();
    public static final Person SOURCE_DELAY = new PersonBuilder().withQuestion("Sources of Packet Delay?").withAnswer("Nodal processing, queuing, transmission, propagation")
            .withEmail("heinz@example.com").withAddress("wall street").build();
    public static final Person PROP_DALAY = new PersonBuilder().withQuestion("Formula for propagation delay").withAnswer("d, length of physical link (m) / s, progagation speed in medium (~2x10^8 m/sec) ")
            .withEmail("cornelia@example.com").withAddress("10th street").withTags("friends").build();
    public static final Person TRANS_DELAY = new PersonBuilder().withQuestion("Formula for transmission delay").withAnswer("L, packet length (bits) / R, link bandwidth (bps)")
            .withEmail("werner@example.com").withAddress("michegan ave").build();
    public static final Person THROUGHPUT = new PersonBuilder().withQuestion("Throughput definition").withAnswer("How many bits can be transmitted per unit time")
            .withEmail("lydia@example.com").withAddress("little tokyo").build();
    public static final Person PROTOCOL = new PersonBuilder().withQuestion("What is a Protocol").withAnswer("Protocols define format and order of messages exchanged and the action taken after messages are sent or received")
            .withEmail("anna@example.com").withAddress("4th street").build();

    // Manually added
    public static final Person GREETING = new PersonBuilder().withQuestion("How are you?").withAnswer("I am fine, thank you and you?")
            .withEmail("stefan@example.com").withAddress("little india").build();
    public static final Person SUM = new PersonBuilder().withQuestion("1 + 1 = ").withAnswer("2")
            .withEmail("hans@example.com").withAddress("chicago ave").build();

    // Manually added - Person's details found in {@code CommandTestUtil}
    public static final Person WWII = new PersonBuilder().withQuestion(VALID_QUESTION_1).withAnswer(VALID_ANSWER_1)
            .withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY).withTags(VALID_TAG_FRIEND).build();
    public static final Person NUS = new PersonBuilder().withQuestion(VALID_QUESTION_2).withAnswer(VALID_ANSWER_2)
            .withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND)
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
        return new ArrayList<>(Arrays.asList(STORE_AND_FORWARD, DELAY, SOURCE_DELAY, PROP_DALAY, TRANS_DELAY, THROUGHPUT, PROTOCOL));
    }
}
