package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_ATTENDANCE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ATTENDANCE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CLASSID_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CLASSID_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PARTICIPATION_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PARTICIPATION_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PICTURE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PICTURE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_RESULT_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_RESULT_BOB;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.AddressBook;
import seedu.address.model.person.Person;

/**
 * A utility class containing a list of {@code Person} objects to be used in tests.
 */
public class TypicalPersons {

    public static final Person ALICE = new PersonBuilder().withName("Alice Pauline")
            .withPicture("alice.jpg").withClassId("Tutorial 7")
            .withAttendance("9").withResult("12").withParticipation("100").build();
    public static final Person BENSON = new PersonBuilder().withName("Benson Meier")
            .withPicture("benson.jpg").withClassId("Tutorial 99")
            .withAttendance("19").withResult("13").withParticipation("10").build();

    // Manually added - Person's details found in {@code CommandTestUtil}
    public static final Person AMY = new PersonBuilder().withName(VALID_NAME_AMY)
            .withPicture(VALID_PICTURE_AMY)
            .withClassId(VALID_CLASSID_AMY)
            .withResult(VALID_RESULT_AMY)
            .withParticipation(VALID_PARTICIPATION_AMY)
            .withAttendance(VALID_ATTENDANCE_AMY)
            .build();
    public static final Person BOB = new PersonBuilder().withName(VALID_NAME_BOB)
            .withPicture(VALID_PICTURE_BOB)
            .withClassId(VALID_CLASSID_BOB)
            .withResult(VALID_RESULT_BOB)
            .withParticipation(VALID_PARTICIPATION_BOB)
            .withAttendance(VALID_ATTENDANCE_BOB)
            .build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

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
        return new ArrayList<>(Arrays.asList(ALICE, BENSON));
    }
}
