package organice.testutil;

import static organice.logic.commands.CommandTestUtil.VALID_AGE_DONOR_JOHN;
import static organice.logic.commands.CommandTestUtil.VALID_AGE_PATIENT_IRENE;
import static organice.logic.commands.CommandTestUtil.VALID_BLOOD_TYPE_DONOR_JOHN;
import static organice.logic.commands.CommandTestUtil.VALID_BLOOD_TYPE_PATIENT_BOB;
import static organice.logic.commands.CommandTestUtil.VALID_BLOOD_TYPE_PATIENT_IRENE;
import static organice.logic.commands.CommandTestUtil.VALID_DOCTOR_IN_CHARGE_PATIENT_BOB;
import static organice.logic.commands.CommandTestUtil.VALID_DOCTOR_IN_CHARGE_PATIENT_IRENE;
import static organice.logic.commands.CommandTestUtil.VALID_NAME_DOCTOR_AMY;
import static organice.logic.commands.CommandTestUtil.VALID_NAME_DONOR_JOHN;
import static organice.logic.commands.CommandTestUtil.VALID_NAME_PATIENT_BOB;
import static organice.logic.commands.CommandTestUtil.VALID_NAME_PATIENT_IRENE;
import static organice.logic.commands.CommandTestUtil.VALID_NRIC_DOCTOR_AMY;
import static organice.logic.commands.CommandTestUtil.VALID_NRIC_DONOR_JOHN;
import static organice.logic.commands.CommandTestUtil.VALID_NRIC_PATIENT_BOB;
import static organice.logic.commands.CommandTestUtil.VALID_NRIC_PATIENT_IRENE;
import static organice.logic.commands.CommandTestUtil.VALID_ORGAN_DONOR_JOHN;
import static organice.logic.commands.CommandTestUtil.VALID_ORGAN_EXPIRY_DATE_DONOR_JOHN;
import static organice.logic.commands.CommandTestUtil.VALID_ORGAN_PATIENT_BOB;
import static organice.logic.commands.CommandTestUtil.VALID_ORGAN_PATIENT_IRENE;
import static organice.logic.commands.CommandTestUtil.VALID_PHONE_DONOR_JOHN;
import static organice.logic.commands.CommandTestUtil.VALID_PHONE_PATIENT_BOB;
import static organice.logic.commands.CommandTestUtil.VALID_PHONE_PATIENT_IRENE;
import static organice.logic.commands.CommandTestUtil.VALID_TISSUE_TYPE_DONOR_JOHN;
import static organice.logic.commands.CommandTestUtil.VALID_TISSUE_TYPE_PATIENT_BOB;
import static organice.logic.commands.CommandTestUtil.VALID_TISSUE_TYPE_PATIENT_IRENE;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import organice.logic.commands.CommandTestUtil;
import organice.model.AddressBook;
import organice.model.person.Doctor;
import organice.model.person.Donor;
import organice.model.person.Patient;
import organice.model.person.Person;

/**
 * A utility class containing a list of {@code Person} objects to be used in tests.
 */
public class TypicalPersons {

    public static final Doctor DOCTOR_ALICE = new DoctorBuilder().withNric("S1532142A")
            .withName("Alice Pauline").withPhone("94351253").build();
    public static final Doctor DOCTOR_BENSON = new DoctorBuilder().withNric("T5231426Q")
            .withName("Benson Meier").withPhone("98765432").build();
    public static final Patient PATIENT_CARL = new PatientBuilder().withAge("22").withNric("G5642431P")
            .withName("Carl Kurz").withPhone("95352563").withPriority("high")
            .withBloodType("A").withTissueType("1,2,3,4,5,6").withOrgan("kidney")
            .withDoctorInCharge("S1532142A").build();
    public static final Patient PATIENT_DANIEL = new PatientBuilder().withAge("34").withNric("F6423467F")
            .withName("Daniel Meier").withPhone("87652533").withPriority("low")
            .withBloodType("O+").withTissueType("10,5,3,1,6,8").withOrgan("kidney")
            .withDoctorInCharge("T5231426Q").build();
    public static final Donor DONOR_ELLE = new DonorBuilder().withAge("13").withNric("S9374923S")
            .withName("Elle Meyer").withPhone("9482224").withBloodType("O+")
            .withTissueType("10,5,3,1,6,8").withOrgan("kidney").withOrganExpiryDate("10-May-2020").build();
    public static final Donor DONOR_FIONA = new DonorBuilder().withAge("25").withNric("F9183156L")
            .withName("Fiona Kunz").withPhone("9482427").withBloodType("AB+")
            .withTissueType("7,2,3,9,6,8").withOrgan("kidney").withOrganExpiryDate("10-Jun-2020").build();
    public static final Donor DONOR_GEORGE = new DonorBuilder().withAge("44").withNric("S1234567A")
            .withName("George Best").withPhone("9482442").withBloodType("B")
            .withTissueType("9,4,1,6,8,2").withOrgan("kidney").withOrganExpiryDate("10-Dec-2030").build();

    // Manually added
    public static final Person PERSON_HOON = new PersonBuilder().withType("doctor").withNric("G1245325A")
            .withName("Hoon Meier").withPhone("8482424").build();
    public static final Person PERSON_IDA = new PersonBuilder().withType("doctor").withNric("T1125125L")
            .withName("Ida Mueller").withPhone("8482131").build();

    //Sample Patients
    public static final Patient PATIENT_IRENE = new PatientBuilder().withAge(VALID_AGE_PATIENT_IRENE)
            .withName(VALID_NAME_PATIENT_IRENE).withNric(VALID_NRIC_PATIENT_IRENE).withPhone(VALID_PHONE_PATIENT_IRENE)
            .withBloodType(VALID_BLOOD_TYPE_PATIENT_IRENE).withTissueType(VALID_TISSUE_TYPE_PATIENT_IRENE)
            .withOrgan(VALID_ORGAN_PATIENT_IRENE).withDoctorInCharge(VALID_DOCTOR_IN_CHARGE_PATIENT_IRENE).build();
    //Sample Donors
    public static final Donor DONOR_JOHN = new DonorBuilder().withAge(VALID_AGE_DONOR_JOHN)
            .withName(VALID_NAME_DONOR_JOHN).withNric(VALID_NRIC_DONOR_JOHN).withPhone(VALID_PHONE_DONOR_JOHN)
            .withBloodType(VALID_BLOOD_TYPE_DONOR_JOHN).withTissueType(VALID_TISSUE_TYPE_DONOR_JOHN)
            .withOrgan(VALID_ORGAN_DONOR_JOHN).withOrganExpiryDate(VALID_ORGAN_EXPIRY_DATE_DONOR_JOHN).build();

    // Manually added - person's details found in {@code CommandTestUtil}
    public static final Doctor DOCTOR_AMY = new DoctorBuilder().withNric(VALID_NRIC_DOCTOR_AMY)
            .withName(VALID_NAME_DOCTOR_AMY).withPhone(CommandTestUtil.VALID_PHONE_DOCTOR_AMY).build();
    public static final Patient PATIENT_BOB = new PatientBuilder().withNric(VALID_NRIC_PATIENT_BOB)
            .withName(VALID_NAME_PATIENT_BOB).withPhone(VALID_PHONE_PATIENT_BOB)
            .withBloodType(VALID_BLOOD_TYPE_PATIENT_BOB).withTissueType(VALID_TISSUE_TYPE_PATIENT_BOB)
            .withOrgan(VALID_ORGAN_PATIENT_BOB).withDoctorInCharge(VALID_DOCTOR_IN_CHARGE_PATIENT_BOB).build();

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
        return new ArrayList<>(Arrays.asList(DOCTOR_ALICE, DOCTOR_BENSON, PATIENT_CARL, PATIENT_DANIEL, DONOR_ELLE,
                DONOR_FIONA, DONOR_GEORGE));
    }
}
