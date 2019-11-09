package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ID_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ID_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.person.AddressBook;
import seedu.address.model.person.Person;
import seedu.address.model.queue.QueueManager;

/**
 * A utility class containing a list of {@code Person} objects to be used in tests.
 */
public class TypicalPersons {

    public static final Person ALICE = new PersonBuilder().withPatientId("S0000001A").withName("Alice Pauline")
            .withAddress("123, Jurong West Ave 6, #08-111").withEmail("alice@example.com")
            .withPhone("94351253")
            .withTags("friends").build();
    public static final Person BENSON = new PersonBuilder().withPatientId("S0000002B").withName("Benson Meier")
            .withAddress("311, Clementi Ave 2, #02-25")
            .withEmail("johnd@example.com").withPhone("98765432")
            .withTags("owesMoney", "friends").build();
    public static final Person CARL = new PersonBuilder().withPatientId("S0000003C").withName("Carl Kurz")
            .withPhone("95352563")
            .withEmail("heinz@example.com")
            .withAddress("wall street").build();
    public static final Person DANIEL = new PersonBuilder().withPatientId("S0000004D").withName("Daniel Meier")
            .withPhone("87652533")
            .withEmail("cornelia@example.com")
            .withAddress("10th street")
            .withTags("friends").build();
    public static final Person ELLE = new PersonBuilder().withPatientId("S0000005E").withName("Elle Meyer")
            .withPhone("94822243")
            .withEmail("werner@example.com")
            .withAddress("michegan ave").build();
    public static final Person FIONA = new PersonBuilder().withPatientId("S0000006F").withName("Fiona Kunz")
            .withPhone("94824273")
            .withEmail("lydia@example.com")
            .withAddress("little tokyo").build();
    public static final Person GEORGE = new PersonBuilder().withPatientId("S0000007G").withName("George Best")
            .withPhone("94824423")
            .withEmail("anna@example.com")
            .withAddress("4th street").build();

    public static final Person STAFF_ALICE = new PersonBuilder().withStaffId("W0000001A").withName("DR Alice Pauline")
            .withAddress("123, Jurong West Ave 6, #08-111").withEmail("alice@example.com")
            .withPhone("94351253")
            .withTags("friends").build();
    public static final Person STAFF_BENSON = new PersonBuilder().withStaffId("W0000002B").withName("DR Benson Meier")
            .withAddress("311, Clementi Ave 2, #02-25")
            .withEmail("johnd@example.com").withPhone("98765432")
            .withTags("owesMoney", "friends").build();
    public static final Person STAFF_CARL = new PersonBuilder().withStaffId("W0000003C").withName("DR Carl Kurz")
            .withPhone("95352563")
            .withEmail("heinz@example.com")
            .withAddress("wall street").build();
    public static final Person STAFF_DANIEL = new PersonBuilder().withStaffId("W0000004D").withName("DR Daniel Meier")
            .withPhone("87652533")
            .withEmail("cornelia@example.com")
            .withAddress("10th street")
            .withTags("friends").build();
    public static final Person STAFF_ELLE = new PersonBuilder().withStaffId("W0000005E").withName("DR Elle Meyer")
            .withPhone("94822243")
            .withEmail("werner@example.com")
            .withAddress("michegan ave").build();
    public static final Person STAFF_FIONA = new PersonBuilder().withStaffId("W0000006F").withName("DR Fiona Kunz")
            .withPhone("94824273")
            .withEmail("lydia@example.com")
            .withAddress("little tokyo").build();
    public static final Person STAFF_GEORGE = new PersonBuilder().withStaffId("W0000007G").withName("DR George Best")
            .withPhone("94824423")
            .withEmail("anna@example.com")
            .withAddress("4th street").build();

    // Manually added
    public static final Person HOON = new PersonBuilder().withPatientId("S0000008H")
            .withName("Hoon Meier").withPhone("84824243")
            .withEmail("stefan@example.com").withAddress("little india").build();
    public static final Person IDA = new PersonBuilder().withPatientId("S0000009I")
            .withName("Ida Mueller").withPhone("84821313")
            .withEmail("hans@example.com").withAddress("chicago ave").build();

    // Manually added - Person's details found in {@code CommandTestUtil}
    public static final Person AMY = new PersonBuilder().withPatientId(VALID_ID_AMY).withName(VALID_NAME_AMY)
            .withPhone(VALID_PHONE_AMY).withEmail(VALID_EMAIL_AMY)
        .withAddress(VALID_ADDRESS_AMY).withTags(VALID_TAG_FRIEND).build();
    public static final Person BOB = new PersonBuilder().withPatientId(VALID_ID_BOB).withName(VALID_NAME_BOB)
            .withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB)
            .withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND)
            .build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalPersons() {
    } // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical patients.
     */
    public static AddressBook getTypicalPatientAddressBook() {
        AddressBook ab = new AddressBook();
        for (Person person : getTypicalPatients()) {
            ab.addPerson(person);
        }
        return ab;
    }

    /**
     * Returns an {@code AddressBook} with all the typical staff.
     */
    public static AddressBook getTypicalStaffAddressBook() {
        AddressBook ab = new AddressBook();
        for (Person person : getTypicalStaff()) {
            ab.addPerson(person);
        }
        return ab;
    }

    public static List<Person> getTypicalPatients() {
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));
    }

    public static List<Person> getTypicalStaff() {
        return new ArrayList<>(Arrays.asList(STAFF_ALICE, STAFF_BENSON, STAFF_CARL, STAFF_DANIEL,
                STAFF_ELLE, STAFF_FIONA, STAFF_GEORGE));
    }

    public static QueueManager getTypicalQueueManager() {
        QueueManager qm = new QueueManager();
        for (Person person : getTypicalPatients()) {
            qm.addPatient(person.getReferenceId());
        }
        return qm;
    }
}
