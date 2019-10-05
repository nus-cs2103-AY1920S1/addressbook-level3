package seedu.address.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.AddressBook;
import seedu.address.model.entity.Email;
import seedu.address.model.entity.Id;
import seedu.address.model.entity.Mentor;
import seedu.address.model.entity.Name;
import seedu.address.model.entity.Phone;
import seedu.address.model.entity.PrefixType;
import seedu.address.model.entity.SubjectName;
import seedu.address.model.person.Person;

public class TypicalMentors {
    public static final Mentor A = new Mentor(new Name("Mentor A"),
                                              new Id(PrefixType.M, 3),
                                              new Phone("91111111"),
                                              new Email("mentorA@gmail.com"),
                                              new Name("Organization A"),
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

    //public static MentorList getTypicalMentorList() {
    //
    //}
}
