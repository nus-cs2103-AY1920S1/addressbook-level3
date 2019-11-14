package organice.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static organice.storage.JsonAdaptedPerson.MISSING_FIELD_MESSAGE_FORMAT;
import static organice.testutil.Assert.assertThrows;
import static organice.testutil.TypicalPersons.DOCTOR_ALICE;
import static organice.testutil.TypicalPersons.DONOR_JOHN;
import static organice.testutil.TypicalPersons.PATIENT_IRENE;

import org.junit.jupiter.api.Test;

import organice.commons.exceptions.IllegalValueException;
import organice.model.person.Age;
import organice.model.person.BloodType;
import organice.model.person.DoctorInCharge;
import organice.model.person.Name;
import organice.model.person.Nric;
import organice.model.person.Organ;
import organice.model.person.OrganExpiryDate;
import organice.model.person.Phone;
import organice.model.person.Priority;
import organice.model.person.Status;
import organice.model.person.TissueType;
import organice.model.person.Type;

public class JsonAdaptedPersonTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_PHONE = "+651234";
    private static final String INVALID_NRIC = "G123A";
    private static final String INVALID_TYPE = "student";
    private static final String INVALID_AGE = "-28";
    private static final String INVALID_PRIORITY = "hgh";
    private static final String INVALID_BLOOD_TYPE = "AB";
    private static final String INVALID_TISSUE_TYPE = "1,1,2,3,4,5";
    private static final String INVALID_ORGAN = "heart";
    private static final String INVALID_DOCTOR_IN_CHARGE = "S123A";
    private static final String INVALID_ORGAN_EXPIRY_DATE = "22.01.2020";
    private static final String INVALID_STATUS = "procesing";

    private static final String PLACEHOLDER_DOCTOR_IN_CHARGE = "";
    private static final String PLACEHOLDER_ORGAN_EXPIRY_DATE = "";
    private static final String PLACEHOLDER_PROCESSINGLIST = "";
    private static final String PLACEHOLDER_PATIENTSMATCHEDBEFORELIST = "";

    private static final String VALID_NAME = DOCTOR_ALICE.getName().toString();
    private static final String VALID_PHONE = DOCTOR_ALICE.getPhone().toString();
    private static final String VALID_NRIC = DOCTOR_ALICE.getNric().toString();
    private static final String VALID_TYPE = PATIENT_IRENE.getType().toString();
    private static final String VALID_AGE = PATIENT_IRENE.getAge().toString();
    private static final String VALID_PRIORITY = PATIENT_IRENE.getPriority().toString();
    private static final String VALID_BLOOD_TYPE = PATIENT_IRENE.getBloodType().toString();
    private static final String VALID_TISSUE_TYPE = PATIENT_IRENE.getTissueType().toString();
    private static final String VALID_ORGAN = PATIENT_IRENE.getOrgan().toString();
    private static final String VALID_DOCTOR_IN_CHARGE = PATIENT_IRENE.getDoctorInCharge().toString();
    private static final String VALID_STATUS = PATIENT_IRENE.getStatus().toString();

    @Test
    public void toModelType_validDoctorDetails_returnsDoctor() throws Exception {
        JsonAdaptedPerson person = new JsonAdaptedPerson(DOCTOR_ALICE);
        assertEquals(DOCTOR_ALICE, person.toModelType());
    }

    @Test
    public void toModelType_validDonorDetails_returnsDonor() throws Exception {
        JsonAdaptedPerson person = new JsonAdaptedPerson(DONOR_JOHN);
        assertEquals(DONOR_JOHN, person.toModelType());
    }

    @Test
    public void toModelType_validPatientDetails_returnsPatient() throws Exception {
        JsonAdaptedPerson person = new JsonAdaptedPerson(PATIENT_IRENE);
        assertEquals(PATIENT_IRENE, person.toModelType());
    }

    @Test
    public void toModelType_invalidType_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(INVALID_TYPE, VALID_NRIC, VALID_NAME,
                VALID_PHONE, VALID_AGE, VALID_PRIORITY, VALID_BLOOD_TYPE, VALID_TISSUE_TYPE,
                        VALID_ORGAN, VALID_DOCTOR_IN_CHARGE, PLACEHOLDER_ORGAN_EXPIRY_DATE, VALID_STATUS,
                                PLACEHOLDER_PROCESSINGLIST, PLACEHOLDER_PATIENTSMATCHEDBEFORELIST);
        String expectedMessage = Type.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullType_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(null, VALID_NRIC, VALID_NAME, VALID_PHONE,
                VALID_AGE, VALID_PRIORITY, VALID_BLOOD_TYPE, VALID_TISSUE_TYPE,
                        VALID_ORGAN, VALID_DOCTOR_IN_CHARGE, PLACEHOLDER_ORGAN_EXPIRY_DATE, VALID_STATUS,
                                PLACEHOLDER_PROCESSINGLIST, PLACEHOLDER_PATIENTSMATCHEDBEFORELIST);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Type.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidNric_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_TYPE, INVALID_NRIC, VALID_NAME, VALID_PHONE,
                VALID_AGE, VALID_PRIORITY, VALID_BLOOD_TYPE, VALID_TISSUE_TYPE,
                        VALID_ORGAN, VALID_DOCTOR_IN_CHARGE, PLACEHOLDER_ORGAN_EXPIRY_DATE, VALID_STATUS,
                                PLACEHOLDER_PROCESSINGLIST, PLACEHOLDER_PATIENTSMATCHEDBEFORELIST);
        String expectedMessage = Nric.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullNric_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_TYPE, null, VALID_NAME, VALID_PHONE,
                VALID_AGE, VALID_PRIORITY, VALID_BLOOD_TYPE, VALID_TISSUE_TYPE,
                        VALID_ORGAN, VALID_DOCTOR_IN_CHARGE, PLACEHOLDER_ORGAN_EXPIRY_DATE, VALID_STATUS,
                                PLACEHOLDER_PROCESSINGLIST, PLACEHOLDER_PATIENTSMATCHEDBEFORELIST);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Nric.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_TYPE, VALID_NRIC, INVALID_NAME,
                VALID_PHONE, VALID_AGE, VALID_PRIORITY, VALID_BLOOD_TYPE,
                        VALID_TISSUE_TYPE, VALID_ORGAN, VALID_DOCTOR_IN_CHARGE, PLACEHOLDER_ORGAN_EXPIRY_DATE,
                                VALID_STATUS, PLACEHOLDER_PROCESSINGLIST, PLACEHOLDER_PATIENTSMATCHEDBEFORELIST);
        String expectedMessage = Name.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_TYPE, VALID_NRIC, null,
                VALID_PHONE, VALID_AGE, VALID_PRIORITY, VALID_BLOOD_TYPE,
                        VALID_TISSUE_TYPE, VALID_ORGAN, VALID_DOCTOR_IN_CHARGE, PLACEHOLDER_ORGAN_EXPIRY_DATE,
                                VALID_STATUS, PLACEHOLDER_PROCESSINGLIST, PLACEHOLDER_PATIENTSMATCHEDBEFORELIST);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidPhone_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_TYPE, VALID_NRIC, VALID_NAME,
                INVALID_PHONE, VALID_AGE, VALID_PRIORITY, VALID_BLOOD_TYPE,
                        VALID_TISSUE_TYPE, VALID_ORGAN, VALID_DOCTOR_IN_CHARGE, PLACEHOLDER_ORGAN_EXPIRY_DATE,
                                VALID_STATUS, PLACEHOLDER_PROCESSINGLIST, PLACEHOLDER_PATIENTSMATCHEDBEFORELIST);
        String expectedMessage = Phone.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullPhone_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_TYPE, VALID_NRIC, VALID_NAME,
                null, VALID_AGE, VALID_PRIORITY, VALID_BLOOD_TYPE,
                        VALID_TISSUE_TYPE, VALID_ORGAN, VALID_DOCTOR_IN_CHARGE, PLACEHOLDER_ORGAN_EXPIRY_DATE,
                                VALID_STATUS, PLACEHOLDER_PROCESSINGLIST, PLACEHOLDER_PATIENTSMATCHEDBEFORELIST);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidAge_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_TYPE, VALID_NRIC, VALID_NAME,
                VALID_PHONE, INVALID_AGE, VALID_PRIORITY, VALID_BLOOD_TYPE,
                        VALID_TISSUE_TYPE, VALID_ORGAN, VALID_DOCTOR_IN_CHARGE, PLACEHOLDER_ORGAN_EXPIRY_DATE,
                                VALID_STATUS, PLACEHOLDER_PROCESSINGLIST, PLACEHOLDER_PATIENTSMATCHEDBEFORELIST);
        String expectedMessage = Age.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullAge_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_TYPE, VALID_NRIC, VALID_NAME,
                VALID_PHONE, null, VALID_PRIORITY, VALID_BLOOD_TYPE,
                        VALID_TISSUE_TYPE, VALID_ORGAN, VALID_DOCTOR_IN_CHARGE, PLACEHOLDER_ORGAN_EXPIRY_DATE,
                                VALID_STATUS, PLACEHOLDER_PROCESSINGLIST, PLACEHOLDER_PATIENTSMATCHEDBEFORELIST);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Age.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidPriority_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_TYPE, VALID_NRIC, VALID_NAME,
                VALID_PHONE, VALID_AGE, INVALID_PRIORITY, VALID_BLOOD_TYPE,
                        VALID_TISSUE_TYPE, VALID_ORGAN, VALID_DOCTOR_IN_CHARGE, PLACEHOLDER_ORGAN_EXPIRY_DATE,
                                VALID_STATUS, PLACEHOLDER_PROCESSINGLIST, PLACEHOLDER_PATIENTSMATCHEDBEFORELIST);
        String expectedMessage = Priority.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullPriority_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_TYPE, VALID_NRIC, VALID_NAME,
                VALID_PHONE, VALID_AGE, null, VALID_BLOOD_TYPE,
                        VALID_TISSUE_TYPE, VALID_ORGAN, VALID_DOCTOR_IN_CHARGE, PLACEHOLDER_ORGAN_EXPIRY_DATE,
                                VALID_STATUS, PLACEHOLDER_PROCESSINGLIST, PLACEHOLDER_PATIENTSMATCHEDBEFORELIST);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Priority.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidBloodType_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_TYPE, VALID_NRIC, VALID_NAME,
                VALID_PHONE, VALID_AGE, VALID_PRIORITY, INVALID_BLOOD_TYPE,
                        VALID_TISSUE_TYPE, VALID_ORGAN, VALID_DOCTOR_IN_CHARGE, PLACEHOLDER_ORGAN_EXPIRY_DATE,
                                VALID_STATUS, PLACEHOLDER_PROCESSINGLIST, PLACEHOLDER_PATIENTSMATCHEDBEFORELIST);
        String expectedMessage = BloodType.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullBloodType_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_TYPE, VALID_NRIC, VALID_NAME,
                VALID_PHONE, VALID_AGE, VALID_PRIORITY, null,
                        VALID_TISSUE_TYPE, VALID_ORGAN, VALID_DOCTOR_IN_CHARGE, PLACEHOLDER_ORGAN_EXPIRY_DATE,
                                VALID_STATUS, PLACEHOLDER_PROCESSINGLIST, PLACEHOLDER_PATIENTSMATCHEDBEFORELIST);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, BloodType.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidTissueType_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_TYPE, VALID_NRIC, VALID_NAME,
                VALID_PHONE, VALID_AGE, VALID_PRIORITY, VALID_BLOOD_TYPE,
                        INVALID_TISSUE_TYPE, VALID_ORGAN, VALID_DOCTOR_IN_CHARGE, PLACEHOLDER_ORGAN_EXPIRY_DATE,
                                VALID_STATUS, PLACEHOLDER_PROCESSINGLIST, PLACEHOLDER_PATIENTSMATCHEDBEFORELIST);
        String expectedMessage = TissueType.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullTissueType_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_TYPE, VALID_NRIC, VALID_NAME,
                VALID_PHONE, VALID_AGE, VALID_PRIORITY, VALID_BLOOD_TYPE,
                        null, VALID_ORGAN, VALID_DOCTOR_IN_CHARGE, PLACEHOLDER_ORGAN_EXPIRY_DATE,
                                VALID_STATUS, PLACEHOLDER_PROCESSINGLIST, PLACEHOLDER_PATIENTSMATCHEDBEFORELIST);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, TissueType.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidOrgan_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_TYPE, VALID_NRIC, VALID_NAME,
                VALID_PHONE, VALID_AGE, VALID_PRIORITY, VALID_BLOOD_TYPE,
                        VALID_TISSUE_TYPE, INVALID_ORGAN, VALID_DOCTOR_IN_CHARGE, PLACEHOLDER_ORGAN_EXPIRY_DATE,
                                VALID_STATUS, PLACEHOLDER_PROCESSINGLIST, PLACEHOLDER_PATIENTSMATCHEDBEFORELIST);
        String expectedMessage = Organ.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullOrgan_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_TYPE, VALID_NRIC, VALID_NAME,
                VALID_PHONE, VALID_AGE, VALID_PRIORITY, VALID_BLOOD_TYPE,
                        VALID_TISSUE_TYPE, null, VALID_DOCTOR_IN_CHARGE, PLACEHOLDER_ORGAN_EXPIRY_DATE,
                                VALID_STATUS, PLACEHOLDER_PROCESSINGLIST, PLACEHOLDER_PATIENTSMATCHEDBEFORELIST);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Organ.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidDoctorInCharge_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_TYPE, VALID_NRIC, VALID_NAME,
                VALID_PHONE, VALID_AGE, VALID_PRIORITY, VALID_BLOOD_TYPE,
                        VALID_TISSUE_TYPE, VALID_ORGAN, INVALID_DOCTOR_IN_CHARGE, PLACEHOLDER_ORGAN_EXPIRY_DATE,
                                VALID_STATUS, PLACEHOLDER_PROCESSINGLIST, PLACEHOLDER_PATIENTSMATCHEDBEFORELIST);
        String expectedMessage = DoctorInCharge.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullDoctorInCharge_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_TYPE, VALID_NRIC, VALID_NAME,
                VALID_PHONE, VALID_AGE, VALID_PRIORITY, VALID_BLOOD_TYPE,
                        VALID_TISSUE_TYPE, VALID_ORGAN, null, PLACEHOLDER_ORGAN_EXPIRY_DATE,
                                VALID_STATUS, PLACEHOLDER_PROCESSINGLIST, PLACEHOLDER_PATIENTSMATCHEDBEFORELIST);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, DoctorInCharge.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidOrganExpiryDate_throwsIllegalValueException() {
        String donorType = "donor";
        JsonAdaptedPerson person = new JsonAdaptedPerson(donorType, VALID_NRIC, VALID_NAME,
                VALID_PHONE, VALID_AGE, VALID_PRIORITY, VALID_BLOOD_TYPE,
                        VALID_TISSUE_TYPE, VALID_ORGAN, PLACEHOLDER_DOCTOR_IN_CHARGE, INVALID_ORGAN_EXPIRY_DATE,
                                VALID_STATUS, PLACEHOLDER_PROCESSINGLIST, PLACEHOLDER_PATIENTSMATCHEDBEFORELIST);
        String expectedMessage = OrganExpiryDate.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullOrganExpiryDate_throwsIllegalValueException() {
        String donorType = "donor";
        JsonAdaptedPerson person = new JsonAdaptedPerson(donorType, VALID_NRIC, VALID_NAME,
                VALID_PHONE, VALID_AGE, VALID_PRIORITY, VALID_BLOOD_TYPE,
                        VALID_TISSUE_TYPE, VALID_ORGAN, PLACEHOLDER_DOCTOR_IN_CHARGE, null,
                                VALID_STATUS, PLACEHOLDER_PROCESSINGLIST, PLACEHOLDER_PATIENTSMATCHEDBEFORELIST);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, OrganExpiryDate.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidStatus_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_TYPE, VALID_NRIC, VALID_NAME,
                VALID_PHONE, VALID_AGE, VALID_PRIORITY, VALID_BLOOD_TYPE,
                        VALID_TISSUE_TYPE, VALID_ORGAN, VALID_DOCTOR_IN_CHARGE, PLACEHOLDER_ORGAN_EXPIRY_DATE,
                                INVALID_STATUS, PLACEHOLDER_PROCESSINGLIST, PLACEHOLDER_PATIENTSMATCHEDBEFORELIST);
        String expectedMessage = Status.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }
}
