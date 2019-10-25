package seedu.address.model.util;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import seedu.address.model.ReadOnlyUserList;
import sugarmummy.bio.model.Address;
import sugarmummy.bio.model.DateOfBirth;
import sugarmummy.bio.model.DisplayPicPath;
import sugarmummy.bio.model.Gender;
import sugarmummy.bio.model.Goal;
import sugarmummy.bio.model.MedicalCondition;
import sugarmummy.bio.model.Name;
import sugarmummy.bio.model.Nric;
import sugarmummy.bio.model.OtherBioInfo;
import sugarmummy.bio.model.Phone;
import sugarmummy.bio.model.ProfileDesc;
import sugarmummy.bio.model.User;
import sugarmummy.bio.model.UserList;


/**
 * Contains utility methods for populating {@code UserList} with sample data.
 */
public class SampleUserDataUtil {
    public static User[] getSampleUsers() {
        return new User[]{
            new User(new Name("Alex Yeoh"), new DisplayPicPath(""),
                new ProfileDesc("Sometimes I pretend I'm a carrot."),
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
