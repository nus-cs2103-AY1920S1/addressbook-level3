package dukecooks.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import dukecooks.model.profile.ReadOnlyUserProfile;
import dukecooks.model.profile.UserProfile;
import dukecooks.model.profile.medical.MedicalHistory;
import dukecooks.model.profile.person.BloodType;
import dukecooks.model.profile.person.DoB;
import dukecooks.model.profile.person.Gender;
import dukecooks.model.profile.person.Height;
import dukecooks.model.profile.person.Name;
import dukecooks.model.profile.person.Person;
import dukecooks.model.profile.person.Weight;

/**
 * Contains utility methods for populating {@code RecipeBook} with sample data.
 */
public class SamplePersonDataUtil {
    public static Person[] getSamplePersons() {
        return new Person[]{
            new Person(new Name("Alex Yeoh"),
                new DoB("25/03/1997"),
                new Gender("male"),
                new BloodType("A+"),
                new Weight("70"),
                new Height("180"),
                getMedicalHistorySet("Diabetes", "High Blood Pressure", "Stroke"))
        };
    }

    public static ReadOnlyUserProfile getSampleUserProfile() {
        UserProfile sampleDc = new UserProfile();
        for (Person samplePerson : getSamplePersons()) {
            sampleDc.addPerson(samplePerson);
        }
        return sampleDc;
    }

    public static Set<MedicalHistory> getMedicalHistorySet (String...strings) {
        return Arrays.stream(strings)
                .map(MedicalHistory::new)
                .collect(Collectors.toSet());
    }

}
