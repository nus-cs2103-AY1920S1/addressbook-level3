package seedu.address.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.HealthRecords;
import seedu.address.model.ReadOnlyHealthRecords;
import seedu.address.model.ReadOnlyUserProfile;
import seedu.address.model.UserProfile;
import seedu.address.model.common.Name;
import seedu.address.model.medical.MedicalHistory;
import seedu.address.model.person.BloodType;
import seedu.address.model.person.DoB;
import seedu.address.model.person.Gender;
import seedu.address.model.person.Height;
import seedu.address.model.person.Person;
import seedu.address.model.person.Weight;
import seedu.address.model.records.Record;
import seedu.address.model.records.Timestamp;
import seedu.address.model.records.Type;
import seedu.address.model.records.Value;

/**
 * Contains utility methods for populating {@code UserProfile} with sample data.
 */
public class SampleDataUtil {

    //=========== Sample Person ==================================================================================

    public static Person[] getSamplePersons() {
        return new Person[] {
            new Person(new Name("Alex Yeoh"),
                new DoB("25/03/1997"),
                new Gender("male"),
                new BloodType("A+"),
                new Weight("70", "13/10/2019 1230"),
                new Height("180", "13/10/2019 1230"),
                getMedicalHistorySet("friends"))
        };
    }

    public static ReadOnlyUserProfile getSampleDukeCooks() {
        UserProfile sampleDc = new UserProfile();
        for (Person samplePerson : getSamplePersons()) {
            sampleDc.addPerson(samplePerson);
        }
        return sampleDc;
    }

    /**
     * Returns a Medical History set containing the list of strings given.
     */
    public static Set<MedicalHistory> getMedicalHistorySet(String... strings) {
        return Arrays.stream(strings)
                .map(MedicalHistory::new)
                .collect(Collectors.toSet());
    }

    //=========== Sample Record ==================================================================================

    public static Record[] getSampleRecords() {
        return new Record[] {
            new Record(
                new Type("bloodpressure"),
                new Value("90"),
                new Timestamp("14/10/2019 01:10"))
        };
    }

    public static ReadOnlyHealthRecords getSampleHealthRecords() {
        HealthRecords sampleDc = new HealthRecords();
        for (Record sampleRecord : getSampleRecords()) {
            sampleDc.addRecord(sampleRecord);
        }
        return sampleDc;
    }

}
