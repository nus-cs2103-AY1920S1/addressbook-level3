package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ORGANIZATION_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ORGANIZATION_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SUBJECT_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SUBJECT_BOB;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.commons.exceptions.AlfredModelException;
import seedu.address.model.entity.Email;
import seedu.address.model.entity.Id;
import seedu.address.model.entity.Mentor;
import seedu.address.model.entity.Name;
import seedu.address.model.entity.Phone;
import seedu.address.model.entity.PrefixType;
import seedu.address.model.entity.SubjectName;
import seedu.address.model.entitylist.MentorList;

/**
 * A utility class containing a list of {@code Mentor} objects to be used in tests.
 */
public class TypicalMentors {

    // Manually created mentors

    public static final Mentor A = new Mentor(new Name("Mentor A"),
                                              new Id(PrefixType.M, 3),
                                              new Phone("+6591111111"),
                                              new Email("mentorA@gmail.com"),
                                              new Name("Organization A"),
                                              SubjectName.SOCIAL);

    public static final Mentor A_UPDATED = new Mentor(new Name("Mentor A"),
            new Id(PrefixType.M, 3),
            new Phone("+6591111111"),
            new Email("mentorA@gmail.com"),
            new Name("Organization Updated"),
            SubjectName.SOCIAL);

    public static final Mentor A_SIMILAR = new Mentor(new Name("Mentor A"),
            new Id(PrefixType.M, 31),
            new Phone("+6591111111"),
            new Email("mentorA@gmail.com"),
            new Name("Organization B"),
            SubjectName.EDUCATION);

    public static final Mentor B = new Mentor(new Name("Mentor B"),
                                              new Id(PrefixType.M, 31),
                                              new Phone("+6592222222"),
                                              new Email("mentorB@gmail.com"),
                                              new Name("Organization B"),
                                              SubjectName.EDUCATION);

    public static final Mentor C = new Mentor(new Name("Mentor C"),
                                              new Id(PrefixType.M, 33),
                                              new Phone("+6593333333"),
                                              new Email("mentorC@gmail.com"),
                                              new Name("Organization C"),
                                              SubjectName.ENVIRONMENTAL);

    // Manually created mentors - Mentor details found in {@code CommandTestUtil}
    public static final Mentor AMY = new MentorBuilder().withName(VALID_NAME_AMY).withEmail(VALID_EMAIL_AMY)
            .withOrganization(VALID_ORGANIZATION_AMY).withPhone(VALID_PHONE_AMY).withSubject(VALID_SUBJECT_AMY).build();

    public static final Mentor BOB = new MentorBuilder().withName(VALID_NAME_BOB).withEmail(VALID_EMAIL_BOB)
            .withOrganization(VALID_ORGANIZATION_BOB).withPhone(VALID_PHONE_BOB).withSubject(VALID_SUBJECT_BOB).build();


    /**
     * Gets the Typical Mentors in the form of a List.
     * @return List of Typical Mentors
     */
    public static List<Mentor> getTypicalMentors() {
        return new ArrayList<>(Arrays.asList(A, B, C));
    }

    /**
     * Gets the Typical Mentors in the form of a MentorList.
     * @return MentorList containing Typical Mentors
     * @throws AlfredModelException
     */
    public static MentorList getTypicalMentorList() throws AlfredModelException {
        MentorList mList = new MentorList();
        for (Mentor m: getTypicalMentors()) {
            mList.add(m);
        }
        return mList;
    }
}
