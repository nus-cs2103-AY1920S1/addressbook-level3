package seedu.planner.testutil.contact;

import static seedu.planner.logic.commands.CommandTestUtil.VALID_ADDRESS_AMY;
import static seedu.planner.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.planner.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.planner.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.planner.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.planner.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.planner.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.planner.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.planner.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.planner.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.planner.model.ContactManager;
import seedu.planner.model.contact.Contact;

/**
 * A utility class containing a list of {@code Contact} objects to be used in tests.
 */
public class TypicalContacts {

    public static final Contact ALICE = new ContactBuilder().withName("Alice Pauline")
            .withAddress("123, Jurong West Ave 6, #08-111").withEmail("alice@example.com")
            .withPhone("94351253")
            .withTags("friends").build();

    public static final Contact BENSON = new ContactBuilder().withName("Benson Meier")
            .withAddress("311, Clementi Ave 2, #02-25")
            .withEmail("johnd@example.com").withPhone("98765432")
            .withTags("owesMoney", "friends").build();

    public static final Contact CARL = new ContactBuilder().withName("Carl Kurz").withPhone("95352563")
            .withEmail("heinz@example.com").withAddress("wall street").build();

    public static final Contact DANIEL = new ContactBuilder().withName("Daniel Meier").withPhone("87652533")
            .withEmail("cornelia@example.com").withAddress("10th street").withTags("friends").build();

    public static final Contact ELLE = new ContactBuilder().withName("Elle Meyer").withPhone("9482224")
            .withEmail("werner@example.com").withAddress("michegan ave").build();

    public static final Contact FIONA = new ContactBuilder().withName("Fiona Kunz").withPhone("9482427")
            .withEmail("lydia@example.com").withAddress("little tokyo").build();

    public static final Contact GEORGE = new ContactBuilder().withName("George Best").withPhone("9482442")
            .withEmail("anna@example.com").withAddress("4th street").build();

    public static final Contact SAM = new ContactBuilder().withName("Sam Smith").withPhone("92007122")
            .withEmail("sam1997@gmail.com").withAddress("Nantan, Kyoto 601-0776, Japan").withTags("tourguide").build();

    public static final Contact MATSAFUSHI = new ContactBuilder().withName("Matsafushi").withPhone("82337121")
            .withEmail("matsafushi@gmail.com")
            .withAddress("150-2345 Tokyo-to, Shibuya-ku, Hommachi 2 choume, 4-7, Sunny Mansion 203")
            .withTags("tourguide").build();

    public static final Contact YUI = new ContactBuilder().withName("Yui").withPhone("93619823")
            .withEmail("Yui@hotmail.com").withAddress("Akishima, Tokyo 196-0022").build();

    public static final Contact HIMARI = new ContactBuilder().withName("Himari").withPhone("94523656")
            .withEmail("Himari@hotmail.com").withAddress("5 Chome Josai, Nishi Ward, Nagoya, Aichi 451-0031")
            .withTags("tourguide").build();

    public static final Contact KOSUKE = new ContactBuilder().withName("kosuke").withPhone("95255523")
            .withEmail("kosuke@hotmail.com").withAddress("5 Chome Josai, Nishi Ward, Nagoya, Aichi 451-0031").build();

    public static final Contact KAKASHI = new ContactBuilder().withName("Kakashi").withPhone("95131415")
            .withEmail("kakashi@yahoo.com").withAddress("Aioi Inuyama, Aichi 484-0081")
            .withTags("tourguide", "experienced").build();

    public static final Contact MAYLIN = new ContactBuilder().withName("Maylin").withPhone("95123444")
            .withEmail("Maylin@yahoo.com").withAddress("Kita Ward, Kyoto, 603-8477").withTags("Japanfriend").build();

    // Manually added
    public static final Contact HOON = new ContactBuilder().withName("Hoon Meier").withPhone("8482424")
            .withEmail("stefan@example.com").withAddress("little india").build();

    public static final Contact IDA = new ContactBuilder().withName("Ida Mueller").withPhone("8482131")
            .withEmail("hans@example.com").withAddress("chicago ave").build();

    // Manually added - Contact's details found in {@code CommandTestUtil}
    public static final Contact AMY = new ContactBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY)
            .withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY).withTags(VALID_TAG_FRIEND).build();
    public static final Contact BOB = new ContactBuilder().withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
            .withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND)
            .build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalContacts() {} // prevents instantiation

    /**
     * Returns an {@code Planner} with all the typical persons.
     */
    public static ContactManager getTypicalContactManager() {
        ContactManager cm = new ContactManager();
        for (Contact contact : getTypicalContacts()) {
            cm.addContact(contact);
        }
        return cm;
    }

    public static List<Contact> getTypicalContacts() {
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE, SAM, MATSAFUSHI, YUI,
                HIMARI, KOSUKE, KAKASHI, MAYLIN));
    }
}
