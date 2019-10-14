package seedu.address.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.commons.exceptions.AlfredException;
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
    public static final Mentor A = new Mentor(new Name("Mentor A"),
                                              new Id(PrefixType.M, 3),
                                              new Phone("91111111"),
                                              new Email("mentorA@gmail.com"),
                                              new Name("Organization A"),
                                              SubjectName.SOCIAL);

    public static final Mentor A_UPDATED = new Mentor(new Name("Mentor A"),
            new Id(PrefixType.M, 3),
            new Phone("91111111"),
            new Email("mentorA@gmail.com"),
            new Name("Organization Updated"),
            SubjectName.SOCIAL);

    public static final Mentor B = new Mentor(new Name("Mentor B"),
                                              new Id(PrefixType.M, 31),
                                              new Phone("92222222"),
                                              new Email("mentorB@gmail.com"),
                                              new Name("Organization B"),
                                              SubjectName.EDUCATION);

    public static final Mentor C = new Mentor(new Name("Mentor C"),
                                              new Id(PrefixType.M, 33),
                                              new Phone("93333333"),
                                              new Email("mentorC@gmail.com"),
                                              new Name("Organization C"),
                                              SubjectName.ENVIRONMENTAL);

    public static List<Mentor> getTypicalMentors() {
        return new ArrayList<>(Arrays.asList(A, B, C));
    }

    public static MentorList getTypicalMentorList() throws AlfredException {
        MentorList mList = new MentorList();
        for (Mentor m: getTypicalMentors()) {
            mList.add(m);
        }
        return mList;
    }
}
