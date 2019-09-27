package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_QUESTION_1;
import static seedu.address.logic.commands.CommandTestUtil.VALID_QUESTION_2;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
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

    public static final Person NEWTON = new PersonBuilder().withQuestion("What is Newton 2nd Law of motion")
            .withAddress("123, Jurong West Ave 6, #08-111").withEmail("alice@example.com")
            .withPhone("94351253")
            .withTags("friends").build();
    public static final Person ACCELERATION = new PersonBuilder().withQuestion("What is acceleration")
            .withAddress("311, Clementi Ave 2, #02-25")
            .withEmail("johnd@example.com").withPhone("98765432")
            .withTags("owesMoney", "friends").build();
    public static final Person QUICK = new PersonBuilder().withQuestion("What is quick sort?").withPhone("95352563")
            .withEmail("heinz@example.com").withAddress("wall street").build();
    public static final Person MERGE = new PersonBuilder().withQuestion("What is merge sort").withPhone("87652533")
            .withEmail("cornelia@example.com").withAddress("10th street").withTags("friends").build();
    public static final Person SELECTION = new PersonBuilder().withQuestion("What is selection sort").withPhone("9482224")
            .withEmail("werner@example.com").withAddress("michegan ave").build();
    public static final Person BUBBLE = new PersonBuilder().withQuestion("What is bubble sort").withPhone("9482427")
            .withEmail("lydia@example.com").withAddress("little tokyo").build();
    public static final Person SSSP = new PersonBuilder().withQuestion("SSSP definition").withPhone("9482442")
            .withEmail("anna@example.com").withAddress("4th street").build();

    // Manually added
    public static final Person GREETING = new PersonBuilder().withQuestion("How are you?").withPhone("8482424")
            .withEmail("stefan@example.com").withAddress("little india").build();
    public static final Person SUM = new PersonBuilder().withQuestion("1 + 1 = ").withPhone("8482131")
            .withEmail("hans@example.com").withAddress("chicago ave").build();

    // Manually added - Person's details found in {@code CommandTestUtil}
    public static final Person WWII = new PersonBuilder().withQuestion(VALID_QUESTION_1).withPhone(VALID_PHONE_AMY)
            .withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY).withTags(VALID_TAG_FRIEND).build();
    public static final Person NUS = new PersonBuilder().withQuestion(VALID_QUESTION_2).withPhone(VALID_PHONE_BOB)
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
        return new ArrayList<>(Arrays.asList(NEWTON, ACCELERATION, QUICK, MERGE, SELECTION, BUBBLE, SSSP));
    }
}
