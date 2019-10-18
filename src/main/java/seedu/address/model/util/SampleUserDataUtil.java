package seedu.address.model.util;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import seedu.address.model.ReadOnlyUserList;
import seedu.address.model.bio.Address;
import seedu.address.model.bio.DateOfBirth;
import seedu.address.model.bio.Gender;
import seedu.address.model.bio.Goal;
import seedu.address.model.bio.MedicalCondition;
import seedu.address.model.bio.Name;
import seedu.address.model.bio.Nric;
import seedu.address.model.bio.OtherBioInfo;
import seedu.address.model.bio.Phone;
import seedu.address.model.bio.ProfileDesc;
import seedu.address.model.bio.User;
import seedu.address.model.bio.UserList;


/**
 * Contains utility methods for populating {@code UserList} with sample data.
 */
public class SampleUserDataUtil {
    public static User[] getSampleUsers() {
        return new User[] {
            new User(new Name("Alex Yeoh"), new ProfileDesc("Sometimes I pretend I'm a carrot."),
                    new Nric("S1234567A"), new Gender("Male"), new DateOfBirth("21/12/1900"),
                    getContactNumberList("91234567"), getEmergencyContactList("81234567"),
                    getMedicalConditionList("Type II Diabetes"),
                    new Address("Blk 123 #01-123 Example Road S(123456)"), getGoalList("test"),
                    new OtherBioInfo("dislikes potatoes"))};
    }

    public static ReadOnlyUserList getSampleUserList() {
        UserList sampleUserList = new UserList();
        for (User sampleUser : getSampleUsers()) {
            sampleUserList.addUser(sampleUser);
        }
        return sampleUserList;
    }

    /**
     * Returns a phone number set containing the list of user's contact numbers given.
     */
    public static List<Phone> getContactNumberList(String... strings) {
        return Arrays.stream(strings)
                .map(Phone::new)
                .collect(Collectors.toList());
    }

    /**
     * Returns a phone number set containing the list of user's emergency contacts given.
     */
    public static List<Phone> getEmergencyContactList(String... strings) {
        return Arrays.stream(strings)
                .map(Phone::new)
                .collect(Collectors.toList());
    }

    /**
     * Returns a medical condition set containing the list of user's medical condition given.
     */
    public static List<MedicalCondition> getMedicalConditionList(String... strings) {
        return Arrays.stream(strings)
                .map(MedicalCondition::new)
                .collect(Collectors.toList());
    }

    /**
     * Returns a goal set containing the list of user's goals given.
     */
    public static List<Goal> getGoalList(String... strings) {
        return Arrays.stream(strings)
                .map(Goal::new)
                .collect(Collectors.toList());
    }

}
