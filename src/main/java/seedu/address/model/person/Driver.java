package seedu.address.model.person;

import seedu.address.model.tag.Tag;

import java.util.Set;

public class Driver extends Person {
    private AvailabilityManager allAssignedTimeSlots;
    private int id;
    static int IDCOUNT = 1;

    public Driver (Name name, Phone phone, Email email, Address address, Set<Tag> tags) {
        super(name, phone, email, address, tags);
        allAssignedTimeSlots = new AvailabilityManager();
        id = IDCOUNT;
        IDCOUNT++;
    }

    public boolean addTimeSlot(Duration TimeSlot) {
        if (allAssignedTimeSlots.isAvailable(TimeSlot)) {
           allAssignedTimeSlots.addTimeSlot(TimeSlot);
        }
        return allAssignedTimeSlots.isAvailable(TimeSlot);
    }

}
