package seedu.address.testutil;

import static seedu.address.testutil.TypicalAthletickDates.FIRST_DATE;
import static seedu.address.testutil.TypicalAthletickDates.FOURTH_DATE;
import static seedu.address.testutil.TypicalAthletickDates.SECOND_DATE;
import static seedu.address.testutil.TypicalAthletickDates.THIRD_DATE;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.CARL;
import static seedu.address.testutil.TypicalPersons.DANIEL;
import static seedu.address.testutil.TypicalPersons.ELLE;
import static seedu.address.testutil.TypicalPersons.FIONA;
import static seedu.address.testutil.TypicalPersons.GEORGE;
import static seedu.address.testutil.TypicalPersons.HOON;
import static seedu.address.testutil.TypicalPersons.IDA;

import java.util.HashMap;

import seedu.address.model.person.Person;
import seedu.address.model.training.Training;

/**
 * A sample class of a Hashmap consisting of players and their attendance.
 */
public class TypicalTrainings {

    private static final HashMap<Person, Boolean> firstAttendance;
    static {
        HashMap<Person, Boolean> hashMap = new HashMap<>();
        hashMap.put(ALICE, true);
        hashMap.put(BENSON, true);
        hashMap.put(CARL, false);
        hashMap.put(DANIEL, false);
        hashMap.put(ELLE, true);
        hashMap.put(FIONA, false);
        hashMap.put(GEORGE, false);
        firstAttendance = hashMap;
    }

    // Hoon did not attend
    private static final HashMap<Person, Boolean> secondAttendance;
    static {
        HashMap<Person, Boolean> hashMap = new HashMap<>();
        hashMap.put(ALICE, true);
        hashMap.put(BENSON, true);
        hashMap.put(CARL, true);
        hashMap.put(DANIEL, true);
        hashMap.put(ELLE, true);
        hashMap.put(FIONA, false);
        hashMap.put(GEORGE, true);
        hashMap.put(HOON, false);
        secondAttendance = hashMap;
    }

    // Ida did not attend
    private static final HashMap<Person, Boolean> thirdAttendance;
    static {
        HashMap<Person, Boolean> hashMap = new HashMap<>();
        hashMap.put(ALICE, true);
        hashMap.put(BENSON, true);
        hashMap.put(CARL, true);
        hashMap.put(DANIEL, true);
        hashMap.put(ELLE, true);
        hashMap.put(FIONA, false);
        hashMap.put(GEORGE, true);
        hashMap.put(HOON, true);
        hashMap.put(IDA, false);
        thirdAttendance = hashMap;
    }
    // All attend
    private static final HashMap<Person, Boolean> fourthAttendance;
    static {
        HashMap<Person, Boolean> hashMap = new HashMap<>();
        hashMap.put(ALICE, true);
        hashMap.put(BENSON, true);
        hashMap.put(CARL, true);
        hashMap.put(DANIEL, true);
        hashMap.put(ELLE, true);
        hashMap.put(FIONA, false);
        hashMap.put(GEORGE, true);
        hashMap.put(HOON, true);
        hashMap.put(IDA, true);
        fourthAttendance = hashMap;
    }

    public static final Training FIRST_TRAINING =
            new TrainingBuilder().withDate(FIRST_DATE).withAttendance(firstAttendance).build();
    public static final Training SECOND_TRAINING =
            new TrainingBuilder().withDate(SECOND_DATE).withAttendance(secondAttendance).build();
    public static final Training THIRD_TRAINING =
            new TrainingBuilder().withDate(THIRD_DATE).withAttendance(thirdAttendance).build();
    public static final Training FOURTH_TRAINING =
            new TrainingBuilder().withDate(FOURTH_DATE).withAttendance(fourthAttendance).build();


    private TypicalTrainings() {}

}
