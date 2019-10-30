//@@author e0031374
package tagline.testutil;

import static tagline.logic.commands.GroupCommandTestUtil.VALID_CONTACTID_CHILDREN1;
import static tagline.logic.commands.GroupCommandTestUtil.VALID_CONTACTID_CHILDREN2;
import static tagline.logic.commands.GroupCommandTestUtil.VALID_CONTACTID_HYDRA1;
import static tagline.logic.commands.GroupCommandTestUtil.VALID_CONTACTID_HYDRA2;
import static tagline.logic.commands.GroupCommandTestUtil.VALID_GROUPDESCRIPTION_CHILDREN;
import static tagline.logic.commands.GroupCommandTestUtil.VALID_GROUPDESCRIPTION_HYDRA;
import static tagline.logic.commands.GroupCommandTestUtil.VALID_GROUPNAME_CHILDREN;
import static tagline.logic.commands.GroupCommandTestUtil.VALID_GROUPNAME_HYDRA;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import tagline.model.group.Group;
import tagline.model.group.GroupBook;

/**
 * A utility class containing a list of {@code Person} objects to be used in tests.
 */
public class TypicalGroups {

    public static final Group WAKANDAN_ROYAL = new GroupBuilder().withGroupName("Wakandan-Royal-Guard")
            .withGroupDescription("Wakanda Forever")
            .withMemberIds("432", "92341", "45221", "7543").build();

    public static final Group AVENGERS = new GroupBuilder().withGroupName("The-OG-6")
            .withGroupDescription("The Earths Mightiest Heroes")
            .withMemberIds("0001", "0002", "3", "40000", "56").build();

    public static final Group GUARDIANS = new GroupBuilder().withGroupName("Guardians_of_theGalaxy")
            .withGroupDescription("").build();

    // Manually added
    public static final Group ASGARDIAN = new GroupBuilder().withGroupName("Asgards-Chosen")
            .withGroupDescription("Protectors of the Nine Realms")
            .withMemberIds("555", "123", "1", "52232").build();

    public static final Group MYSTIC_ARTS = new GroupBuilder().withGroupName("Masters-of-the-Mystic-Arts")
            .withGroupDescription("Protect the Earth from more Mystical Threats")
            .withMemberIds("667", "99999", "5", "40040", "56").build();

    // Manually added - Person's details found in {@code CommandTestUtil}
    public static final Group CHILDREN = new GroupBuilder().withGroupName(VALID_GROUPNAME_CHILDREN)
            .withGroupDescription(VALID_GROUPDESCRIPTION_CHILDREN)
            .withMemberIds(VALID_CONTACTID_CHILDREN1, VALID_CONTACTID_CHILDREN2).build();

    public static final Group HYDRA = new GroupBuilder().withGroupName(VALID_GROUPNAME_HYDRA)
            .withGroupDescription(VALID_GROUPDESCRIPTION_HYDRA)
            .withMemberIds(VALID_CONTACTID_HYDRA1, VALID_CONTACTID_HYDRA2).build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    // No Members
    public static final Group WAKANDAN_ROYAL2 = new GroupBuilder().withGroupName("Wakandan-Royal-Guard")
            .withMemberIds("1", "3")
            .withGroupDescription("Wakanda Forever").build();

    public static final Group AVENGERS2 = new GroupBuilder().withGroupName("The-OG-6")
            .withMemberIds("2", "4")
            .withGroupDescription("The Earths Mightiest Heroes").build();

    public static final Group GUARDIANS2 = new GroupBuilder().withGroupName("Guardians_of_theGalaxy")
            .withMemberIds("5", "6")
            .withGroupDescription("").build();

    // Some Non Existing Members
    public static final Group WAKANDAN_ROYAL3 = new GroupBuilder().withGroupName("Wakandan-Royal-Guard")
            .withMemberIds("1", "3", "12388")
            .withGroupDescription("Wakanda Forever").build();

    public static final Group AVENGERS3 = new GroupBuilder().withGroupName("The-OG-6")
            .withMemberIds("2", "4", "43119")
            .withGroupDescription("The Earths Mightiest Heroes").build();

    private TypicalGroups() {} // prevents instantiation

    /**
     * Returns an {@code GroupBook} with all the typical persons.
     */
    public static GroupBook getTypicalGroupBook() {
        GroupBook ab = new GroupBook();
        for (Group group : getTypicalGroups()) {
            ab.addGroup(group);
        }
        return ab;
    }

    public static List<Group> getTypicalGroups() {
        return new ArrayList<>(Arrays.asList(CHILDREN, HYDRA, WAKANDAN_ROYAL, AVENGERS, GUARDIANS));
    }

    public static GroupBook getTypicalGroupBookExistingMembers() {
        GroupBook ab = new GroupBook();
        for (Group group : getTypicalGroupsExistingMembers()) {
            ab.addGroup(group);
        }
        return ab;
    }

    public static List<Group> getTypicalGroupsExistingMembers() {
        return new ArrayList<>(Arrays.asList(WAKANDAN_ROYAL2, AVENGERS2, GUARDIANS2));
    }

    public static GroupBook getTypicalGroupBookSomeNonExistingMembers() {
        GroupBook ab = new GroupBook();
        for (Group group : getTypicalGroupsSomeNonExistingMembers()) {
            ab.addGroup(group);
        }
        return ab;
    }

    public static List<Group> getTypicalGroupsSomeNonExistingMembers() {
        return new ArrayList<>(Arrays.asList(WAKANDAN_ROYAL3, AVENGERS3, GUARDIANS2));
    }
}
