package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_OF_ADMISSION_JANE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_OF_ADMISSION_JOHN;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_OF_DEATH_JANE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_OF_DEATH_JOHN;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_JANE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_JOHN;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SEX_JANE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SEX_JOHN;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.AddressBook;
import seedu.address.model.entity.body.Body;
import seedu.address.model.entity.worker.Worker;

//@@author ambervoong
/**
 * A utility class containing {@code Body} objects to be used in tests.
 */
public class TypicalBodies {

    public static final Body ALICE = new BodyBuilder()
            .withDateOfAdmission("01/01/1991")
            .withName("Alice Pauline")
            .withSex("FEMALE")
            .withNric("S1234567A")
            .withReligion("ISLAM")
            .withCauseOfDeath("Stroke")
            .withOrgansForDonation("")
            .withStatus("PENDING CLAIM")
            .withFridgeId("F01")
            .withDateOfBirth("03/09/1982")
            .withDateOfDeath("01/01/1991")
            .withNextOfKin("Ben Joseph")
            .withRelationship("Husband")
            .withKinPhoneNumber("87120909")
            .build();

    public static final Body BOB = new BodyBuilder()
            .withDateOfAdmission("01/01/1991")
            .withName("Bob Chachki")
            .withSex("MALE")
            .withNric("S1224567A")
            .withReligion("CHRISTIANITY")
            .withCauseOfDeath("NECROSIS")
            .withOrgansForDonation("")
            .withStatus("ARRIVED")
            .withFridgeId("F01")
            .withDateOfBirth("02/09/1982")
            .withDateOfDeath("01/06/1971")
            .withNextOfKin("Ben Chachki")
            .withRelationship("Father")
            .withKinPhoneNumber("87120919")
            .build();

    // Manually added - Workers's details found in {@code CommandTestUtil}
    public static final Body JOHN = new BodyBuilder().withName(VALID_NAME_JOHN).withSex(VALID_SEX_JOHN)
        .withDateOfAdmission(VALID_DATE_OF_ADMISSION_JOHN).withDateOfDeath(VALID_DATE_OF_DEATH_JOHN).build();
    public static final Body JANE = new BodyBuilder().withName(VALID_NAME_JANE).withSex(VALID_SEX_JANE)
        .withDateOfAdmission(VALID_DATE_OF_ADMISSION_JANE).withDateOfDeath(VALID_DATE_OF_DEATH_JANE).build();

    private TypicalBodies() {
    } // prevents instantiation

    /**
    * Returns an {@code AddressBook} with all the typical persons.
    */
    public static AddressBook getTypicalAddressBook() {
        AddressBook ab = new AddressBook();

        for (Body body : getTypicalBodies()) {
            ab.addEntity(body);
        }

        for (Worker worker : TypicalWorkers.getTypicalWorkers()) {
            ab.addEntity(worker);
        }

        return ab;
    }

    public static List<Body> getTypicalBodies() {
        return new ArrayList<>(Arrays.asList(ALICE, BOB));
    }
}
