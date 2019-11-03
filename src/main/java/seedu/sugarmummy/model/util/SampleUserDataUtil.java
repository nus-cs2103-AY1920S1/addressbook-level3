package seedu.sugarmummy.model.util;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import seedu.sugarmummy.model.ReadOnlyUserList;
import seedu.sugarmummy.model.bio.Address;
import seedu.sugarmummy.model.bio.DateOfBirth;
import seedu.sugarmummy.model.bio.DisplayPicPath;
import seedu.sugarmummy.model.bio.Gender;
import seedu.sugarmummy.model.bio.Goal;
import seedu.sugarmummy.model.bio.MedicalCondition;
import seedu.sugarmummy.model.bio.Name;
import seedu.sugarmummy.model.bio.Nric;
import seedu.sugarmummy.model.bio.OtherBioInfo;
import seedu.sugarmummy.model.bio.Phone;
import seedu.sugarmummy.model.bio.ProfileDesc;
import seedu.sugarmummy.model.bio.User;
import seedu.sugarmummy.model.bio.UserList;


/**
 * Contains utility methods for populating {@code UserList} with sample data.
 */
public class SampleUserDataUtil {
    public static User[] getSampleUsers() {
        return new User[]{
            new User(new Name("Alex Yeoh"), new DisplayPicPath(""),
                    new ProfileDesc("Sometimes I pretend I'm a carrot."),
                    new Nric("S1234567A"), new Gender("Male"), new DateOfBirth("1900-12-21"),
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
