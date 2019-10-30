package seedu.address.testutil.accommodation;

import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.AccommodationManager;
import seedu.address.model.itineraryitem.accommodation.Accommodation;
import seedu.address.testutil.contact.TypicalContacts;

/**
 * A utility class containing a list of {@code Contact} objects to be used in tests.
 */
public class TypicalAccommodations {

    public static final Accommodation ALICE = new AccommodationBuilder().withName("Alice Pauline")
            .withAddress("123, Jurong West Ave 6, #08-111").withContact(TypicalContacts.ALICE)
            .withTags("Jurong", "Cool", "Cheap").build();
    public static final Accommodation BENSON = new AccommodationBuilder().withName("Benson Meier Home")
            .withAddress("311, Clementi Ave 2, #02-25")
            .withContact(TypicalContacts.BENSON).withTags("NearMrt", "Cozy").build();
    public static final Accommodation CARL = new AccommodationBuilder().withName("Carl Kurz Home")
            .withContact(TypicalContacts.CARL).withAddress("wallbstreet").build();
    public static final Accommodation DANIEL = new AccommodationBuilder().withName("Daniel Meier Home")
            .withContact(TypicalContacts.DANIEL).withAddress("10th street").withTags("friends").build();
    public static final Accommodation ELLE = new AccommodationBuilder().withName("Elle Meyer Home")
            .withContact(TypicalContacts.ELLE).withAddress("michegan ave").build();
    public static final Accommodation FIONA = new AccommodationBuilder().withName("Fiona Kunz Home")
            .withContact(TypicalContacts.FIONA).withAddress("little tokyo").build();
    public static final Accommodation GEORGE = new AccommodationBuilder().withName("George Best Home")
            .withContact(TypicalContacts.GEORGE).withAddress("4th street").build();

    // Manually added
    public static final Accommodation HOON = new AccommodationBuilder().withName("Hoon Meier")
            .withContact(TypicalContacts.HOON).withAddress("little india").build();
    public static final Accommodation IDA = new AccommodationBuilder().withName("Ida Mueller")
            .withContact(TypicalContacts.IDA).withAddress("chicago ave").build();

    // Manually added - Contact's details found in {@code CommandTestUtil}
    public static final Accommodation AMY = new AccommodationBuilder().withName(VALID_NAME_AMY)
            .withAddress(VALID_ADDRESS_AMY).withTags(VALID_TAG_FRIEND).build();
    public static final Accommodation BOB = new AccommodationBuilder().withName(VALID_NAME_BOB)
            .withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND).build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalAccommodations() {} // prevents instantiation

    /**
     * Returns an {@code Model} with all the typical accommodations.
     */
    public static AccommodationManager getTypicalAccommodationManager() {
        AccommodationManager am = new AccommodationManager();
        for (Accommodation accommodation : getTypicalAccommodations()) {
            am.addAccommodation(accommodation);
        }
        return am;
    }

    public static List<Accommodation> getTypicalAccommodations() {
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));
    }
}
