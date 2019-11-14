package organice.model.util;

import static organice.model.person.Nric.calculateChecksumLetter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;

import organice.model.AddressBook;
import organice.model.ReadOnlyAddressBook;
import organice.model.person.Age;
import organice.model.person.BloodType;
import organice.model.person.Doctor;
import organice.model.person.DoctorInCharge;
import organice.model.person.Donor;
import organice.model.person.Name;
import organice.model.person.Nric;
import organice.model.person.Organ;
import organice.model.person.OrganExpiryDate;
import organice.model.person.Patient;
import organice.model.person.Person;
import organice.model.person.Phone;
import organice.model.person.Priority;
import organice.model.person.Status;
import organice.model.person.TissueType;
import organice.model.person.Type;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */

public class SampleDataUtil {
    private static final Name[] NAMES_FIRST = Arrays.stream(
            new String[] {"Alice", "Antiope", "Benson", "Betty", "Carol", "Casey", "Duncan", "Dexter", "Elliot", "Elle",
                "Darion", "Delilah", "Elle", "Eden", "Frank", "Felicia", "Gary", "George", "Helen", "Halimah",
                "Issac", "Irene", "James", "Janet", "Kelly", "Karen", "Lionel", "Leonard", "Mary", "Marisha", "Neo",
                "Navin", "Oscar", "Olivia", "Penelope", "Pauline"})
            .map(Name::new).toArray(Name[]::new);
    private static final Name[] NAMES_LAST = Arrays.stream(
            new String[]{"Smith", "Lim", "Tan", "Lee", "Chua", "Yacob", "Chow", "Doe", "Richards", "Walker", "Ross",
                "Martinez", "Brady", "Weiss", "Belladonna", "Rose", "Tennant", "Perry", "Davidson"})
            .map(Name::new).toArray(Name[]::new);
    private static final char[] NRIC_LETTERS = "STFG".toCharArray();
    private static final BloodType[] BLOODTYPES = BloodType.BLOOD_TYPES.stream()
            .map(BloodType::new).toArray(BloodType[]::new);
    private static final Priority[] PRIORITIES = Arrays.stream(
            new String[] {Priority.PRIORITY_LOW, Priority.PRIORITY_MEDIUM, Priority.PRIORITY_HIGH})
            .map(Priority::new).toArray(Priority[]::new);
    private static final String[] TISSUETYPE_VALUES =
            new String[] {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"};

    private static Person[] getSamplePersons() {
        int numDoctors = 10;
        int numPatients = 20;
        int numDonors = 20;
        int numPersons = numDoctors + numDonors + numPatients;
        int nricMin = 1000000;
        int nricMax = 9999999;
        int nricIncrementMax = (nricMax - nricMin) / numPersons;
        char dummyChecksum = 'A';

        // To cycle back, % array.length
        int iterator = 0;
        int nricBody = nricMin;

        ArrayList<Doctor> doctorList = new ArrayList<>();
        // Create Doctors
        for (; iterator < numDoctors; iterator++, nricBody += Math.random() * (nricIncrementMax + 1)) {
            String nricLessChecksum = NRIC_LETTERS[iterator % NRIC_LETTERS.length] + String.valueOf(nricBody);
            Doctor newDoctor = new Doctor(
                    new Type("doctor"),
                    new Nric(nricLessChecksum + calculateChecksumLetter(nricLessChecksum + dummyChecksum)),
                    new Name(NAMES_FIRST[iterator % NAMES_FIRST.length]
                            + " " + NAMES_LAST[iterator % NAMES_LAST.length]),
                    new Phone((String.valueOf((int) (Math.random() * ((99999999 - 80000000) + 1) + 80000000))))
            );
            doctorList.add(newDoctor);
        }

        // Create Patients
        ArrayList<Patient> patientList = new ArrayList<>();
        for (; iterator < numDoctors + numPatients;
             iterator++, nricBody += Math.random() * (nricIncrementMax + 1)) {
            String nricLessChecksum = NRIC_LETTERS[iterator % NRIC_LETTERS.length] + String.valueOf(nricBody);
            // NOTE TissueType not random.
            Patient newPatient = new Patient(
                    new Type("patient"),
                    new Nric(nricLessChecksum + calculateChecksumLetter(nricLessChecksum + dummyChecksum)),
                    new Name(NAMES_FIRST[iterator % NAMES_FIRST.length]
                            + " " + NAMES_LAST[iterator % NAMES_LAST.length]),
                    new Phone((String.valueOf((int) (Math.random() * ((99999999 - 80000000) + 1) + 80000000)))),
                    new Age(String.valueOf(
                            (int) ((Math.random() * (((Age.AGE_MAX - 2) - (Age.AGE_MIN + 1)) + 1)) + Age.AGE_MIN))),
                    new Priority(PRIORITIES[iterator % PRIORITIES.length].toString()),
                    new BloodType(BLOODTYPES[iterator % BLOODTYPES.length].toString()),
                    new TissueType(
                            TISSUETYPE_VALUES[iterator % TISSUETYPE_VALUES.length] + ","
                            + TISSUETYPE_VALUES[(iterator + 7) % TISSUETYPE_VALUES.length] + ","
                            + TISSUETYPE_VALUES[(iterator + 4) % TISSUETYPE_VALUES.length] + ","
                            + TISSUETYPE_VALUES[(iterator + 2) % TISSUETYPE_VALUES.length] + ","
                            + TISSUETYPE_VALUES[(iterator + 11) % TISSUETYPE_VALUES.length] + ","
                            + TISSUETYPE_VALUES[(iterator + 5) % TISSUETYPE_VALUES.length]
                    ),
                    new Organ("kidney"),
                    new DoctorInCharge(doctorList.get(iterator % doctorList.size()).getNric().toString()),
                    new Status(Status.STATUS_NOT_PROCESSING)
            );
            patientList.add(newPatient);
        }

        // Create Donors
        ArrayList<Donor> donorList = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        int minDateIncrement = 30;
        int maxDateIncrement = 150;
        for (; iterator < numPersons; iterator++, nricBody += Math.random() * (nricIncrementMax + 1)) {
            // Random date within +[30, 150) days
            calendar.add(Calendar.DATE,
                    (int) (Math.random() * ((maxDateIncrement - minDateIncrement) + 1) + minDateIncrement));
            String nricLessChecksum = NRIC_LETTERS[iterator % NRIC_LETTERS.length] + String.valueOf(nricBody);
            Donor newDonor = new Donor(
                    new Type("donor"),
                    new Nric(nricLessChecksum + calculateChecksumLetter(nricLessChecksum + dummyChecksum)),
                    new Name(NAMES_FIRST[iterator % NAMES_FIRST.length]
                            + " " + NAMES_LAST[iterator % NAMES_LAST.length]),
                    new Phone((String.valueOf((int) (Math.random() * ((99999999 - 80000000) + 1) + 80000000)))),
                    new Age(String.valueOf(
                            (int) ((Math.random() * (((Age.AGE_MAX - 2) - (Age.AGE_MIN + 1)) + 1)) + Age.AGE_MIN))),
                    new BloodType(BLOODTYPES[iterator % BLOODTYPES.length].toString()),
                    new TissueType(
                            TISSUETYPE_VALUES[iterator % TISSUETYPE_VALUES.length] + ","
                                    + TISSUETYPE_VALUES[(iterator + 7) % TISSUETYPE_VALUES.length] + ","
                                    + TISSUETYPE_VALUES[(iterator + 4) % TISSUETYPE_VALUES.length] + ","
                                    + TISSUETYPE_VALUES[(iterator + 2) % TISSUETYPE_VALUES.length] + ","
                                    + TISSUETYPE_VALUES[(iterator + 11) % TISSUETYPE_VALUES.length] + ","
                                    + TISSUETYPE_VALUES[(iterator + 5) % TISSUETYPE_VALUES.length]
                    ),
                    new Organ("kidney"),
                    new OrganExpiryDate(OrganExpiryDate.DATE_FORMAT.format(calendar.getTime())),
                    new Status(Status.STATUS_NOT_PROCESSING)
            );
            donorList.add(newDonor);
        }

        ArrayList<Person> personList = new ArrayList<>();
        personList.addAll(doctorList);
        personList.addAll(patientList);
        personList.addAll(donorList);
        // Random sort
        Collections.shuffle(personList);
        return personList.toArray(Person[]::new);
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        AddressBook sampleAb = new AddressBook();
        for (Person samplePerson : getSamplePersons()) {
            sampleAb.addPerson(samplePerson);
        }
        return sampleAb;
    }
}
