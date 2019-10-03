package seedu.address.testutil;

import java.util.ArrayList;

import seedu.address.model.entity.Sex;
import seedu.address.model.entity.body.Body;
import seedu.address.model.entity.body.Religion;
import seedu.address.model.entity.body.Status;

//@@author ambervoong
/**
 * A utility class containing {@code Body} objects to be used in tests.
 */
public class TypicalBodies {

    public static final Body ALICE = new BodyBuilder()
            .withDateOfAdmission("01/01/1991")
            .withName("Alice Pauline")
            .withSex(Sex.FEMALE)
            .withNric("S1234567A")
            .withReligion(Religion.ISLAM)
            .withCauseOfDeath("Stroke")
            .withOrgansForDonation(new ArrayList<>())
            .withStatus(Status.PENDING_CLAIM)
            .withFridgeId(1)
            .withDateOfBirth("03/09/1982")
            .withDateOfDeath("01/01/1991")
            .withNextOfKin("Ben Joseph")
            .withRelationship("Husband")
            .withKinPhoneNumber("87120909")
            .build();

    public static final Body BOB = new BodyBuilder()
            .withDateOfAdmission("01/01/1991")
            .withName("Bob Chachki")
            .withSex(Sex.MALE)
            .withNric("S1224567A")
            .withReligion(Religion.CHRISTIANITY)
            .withCauseOfDeath("NECROSIS")
            .withOrgansForDonation(new ArrayList<>())
            .withStatus(Status.ARRIVED)
            .withFridgeId(1)
            .withDateOfBirth("02/09/1982")
            .withDateOfDeath("01/06/1971")
            .withNextOfKin("Ben Chachki")
            .withRelationship("Father")
            .withKinPhoneNumber("87120919")
            .build();

    private TypicalBodies() {
    } // prevents instantiation
}
