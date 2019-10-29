package seedu.address.model.person;

import java.util.HashSet;
import java.util.Set;

import seedu.address.model.tag.Tag;

/**
 * A placeholder stub that represents the user.
 */
public class UserStub extends Person {

    public UserStub() {
        super(new Name("ME"), null, null, null, null, new HashSet<Tag>());
        super.setSchedule(new ScheduleStub().getSchedule());
    }

    public UserStub(Phone phone, Email email, Address address, Remark remark, Set<Tag> tags) {
        super(new Name("ME"), phone, email, address, remark, tags);
    }

    public UserStub(PersonDescriptor personDescriptor) {
        super(personDescriptor);
    }
}
