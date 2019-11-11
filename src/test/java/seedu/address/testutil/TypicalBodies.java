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

import seedu.address.model.entity.body.Body;

//@@author ambervoong
/**
 * A utility class containing {@code Body} objects to be used in tests.
 */
public class TypicalBodies {

    public static final Body ALICE = new BodyBuilder()
            .withDateOfAdmission("20/10/2019")
            .withName("Alice Pauline")
            .withSex("FEMALE")
            .withNric("S1234567A")
            .withReligion("ISLAM")
            .withCauseOfDeath("Stroke")
            .withOrgansForDonation("")
            .withStatus("PENDING CLAIM")
            .withFridgeId("1")
            .withDateOfBirth("03/09/1982")
            .withDateOfDeath("01/01/1991")
            .withNextOfKin("Ben Joseph")
            .withRelationship("Husband")
            .withKinPhoneNumber("87120909")
            .withDetails("polly")
            .build();

    public static final Body BOB = new BodyBuilder()
            .withDateOfAdmission("19/10/2019")
            .withName("Bob Chachki")
            .withSex("MALE")
            .withNric("S1224567A")
            .withReligion("CHRISTIANITY")
            .withCauseOfDeath("NECROSIS")
            .withOrgansForDonation("")
            .withStatus("ARRIVED")
            .withFridgeId("2")
            .withDateOfBirth("02/09/1982")
            .withDateOfDeath("01/06/2019")
            .withNextOfKin("Ben Chachki")
            .withRelationship("Father")
            .withKinPhoneNumber("87120919")
            .withDetails("")
            .build();

    public static final Body LAUII = new BodyBuilder()
            .withDateOfAdmission("22/10/2019")
            .withName("Lauii Echeveria")
            .withSex("MALE")
            .withNric("S1224567A")
            .withReligion("CHRISTIANITY")
            .withCauseOfDeath("NECROSIS")
            .withOrgansForDonation("")
            .withStatus("ARRIVED")
            .withFridgeId("2")
            .withDateOfBirth("02/09/1982")
            .withDateOfDeath("01/06/2019")
            .withNextOfKin("Ben Chachki")
            .withRelationship("Father")
            .withKinPhoneNumber("87120919")
            .build(1);

    public static final Body MOSSY = new BodyBuilder()
            .withDateOfAdmission("27/10/2019")
            .withName("Mossy Humilis")
            .withSex("FEMALE")
            .withStatus("ARRIVED")
            .withDateOfDeath("23/01/2018")
            .withStatus("ARRIVED")
            .build(2);

    public static final Body CHARLES = new BodyBuilder()
            .withDateOfAdmission("01/01/1991")
            .withName("Charles Chachki")
            .withNextOfKin("John Chachki")
            .withRelationship("Father")
            .withKinPhoneNumber("87120919")
            .build();

    // Manually added - Bodies's details found in {@code CommandTestUtil}
    public static final Body JOHN = new BodyBuilder().withName(VALID_NAME_JOHN).withSex(VALID_SEX_JOHN)
            .withDateOfAdmission(VALID_DATE_OF_ADMISSION_JOHN).withDateOfDeath(VALID_DATE_OF_DEATH_JOHN)
            .withDetails("").build();
    public static final Body JANE = new BodyBuilder().withName(VALID_NAME_JANE).withSex(VALID_SEX_JANE)
            .withDateOfAdmission(VALID_DATE_OF_ADMISSION_JANE).withDateOfDeath(VALID_DATE_OF_DEATH_JANE)
            .withDetails("").build();

    private TypicalBodies() {
    } // prevents instantiation



    public static List<Body> getTypicalStoredBodies() {
        return new ArrayList<>(Arrays.asList(LAUII, MOSSY));
    }

    public static List<Body> getTypicalBodies() {
        return new ArrayList<>(Arrays.asList(ALICE, BOB, CHARLES));
    }
}
