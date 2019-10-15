package seedu.ezwatchlist.testutil;

import static seedu.ezwatchlist.logic.commands.CommandTestUtil.VALID_ADDRESS_AMY;
import static seedu.ezwatchlist.logic.commands.CommandTestUtil.VALID_DESCRIPTION_BOB;
import static seedu.ezwatchlist.logic.commands.CommandTestUtil.VALID_WATCHED_AMY;
import static seedu.ezwatchlist.logic.commands.CommandTestUtil.VALID_WATCHED_BOB;
import static seedu.ezwatchlist.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.ezwatchlist.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.ezwatchlist.logic.commands.CommandTestUtil.VALID_DATE_AMY;
import static seedu.ezwatchlist.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.ezwatchlist.logic.commands.CommandTestUtil.VALID_TAG_KID_FRIENDLY;
import static seedu.ezwatchlist.logic.commands.CommandTestUtil.VALID_TAG_HORROR;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.ezwatchlist.model.AddressBook;
import seedu.ezwatchlist.model.person.Person;

/**
 * A utility class containing a list of {@code Person} objects to be used in tests.
 */
public class TypicalShows {

    public static final Person ALICE = new PersonBuilder().withName("Alice Pauline")
            .withAddress("123, Jurong West Ave 6, #08-111").withEmail("alice@example.com")
            .withPhone("94351253")
            .withTags("friends").build();
    public static final Person BENSON = new PersonBuilder().withName("Benson Meier")
            .withAddress("311, Clementi Ave 2, #02-25")
            .withEmail("johnd@example.com").withPhone("98765432")
            .withTags("owesMoney", "friends").build();
    public static final Person CARL = new PersonBuilder().withName("Carl Kurz").withPhone("95352563")
            .withEmail("heinz@example.com").withAddress("wall street").build();
    public static final Person DANIEL = new PersonBuilder().withName("Daniel Meier").withPhone("87652533")
            .withEmail("cornelia@example.com").withAddress("10th street").withTags("friends").build();
    public static final Person ELLE = new PersonBuilder().withName("Elle Meyer").withPhone("9482224")
            .withEmail("werner@example.com").withAddress("michegan ave").build();
    public static final Person FIONA = new PersonBuilder().withName("Fiona Kunz").withPhone("9482427")
            .withEmail("lydia@example.com").withAddress("little tokyo").build();
    public static final Person GEORGE = new PersonBuilder().withName("George Best").withPhone("9482442")
            .withEmail("anna@example.com").withAddress("4th street").build();

    // Manually added
    public static final Person HOON = new PersonBuilder().withName("Hoon Meier").withPhone("8482424")
            .withEmail("stefan@example.com").withAddress("little india").build();
    public static final Person IDA = new PersonBuilder().withName("Ida Mueller").withPhone("8482131")
            .withEmail("hans@example.com").withAddress("chicago ave").build();

    // Manually added - Person's details found in {@code CommandTestUtil}
    public static final Person AMY = new PersonBuilder().withName(VALID_NAME_AMY).withPhone(VALID_DATE_AMY)
            .withEmail(VALID_WATCHED_AMY).withAddress(VALID_ADDRESS_AMY).withTags(VALID_TAG_KID_FRIENDLY).build();
    public static final Person BOB = new PersonBuilder().withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
            .withEmail(VALID_WATCHED_BOB).withAddress(VALID_DESCRIPTION_BOB).withTags(VALID_TAG_HORROR, VALID_TAG_KID_FRIENDLY)
            .build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalShows() {} // prevents instantiation

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
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));
    }
}
