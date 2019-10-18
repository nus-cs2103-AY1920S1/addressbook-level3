package seedu.address.model.util;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.AddressBook;
import seedu.address.model.EventBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyEventBook;
import seedu.address.model.employee.Employee;
import seedu.address.model.employee.EmployeeAddress;
import seedu.address.model.employee.EmployeeEmail;
import seedu.address.model.employee.EmployeeGender;
import seedu.address.model.employee.EmployeeId;
import seedu.address.model.employee.EmployeeJoinDate;
import seedu.address.model.employee.EmployeeName;
import seedu.address.model.employee.EmployeePhone;
import seedu.address.model.employee.EmployeePosition;
import seedu.address.model.event.Event;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    public static Employee[] getSampleEmployees() {
        return new Employee[] {
            new Employee(new EmployeeId("000"), new EmployeeName("Alex Yeoh"), new EmployeeGender("Male"),
                    new EmployeePosition("Manager"), new EmployeePhone("87438807"),
                    new EmployeeEmail("alexyeoh@example.com"),
                    new EmployeeAddress("Blk 30 Geylang Street 29, #06-40"),
                    new EmployeeJoinDate(LocalDate.now()), getTagSet("friends")),

        };
    }

    public static Event[] getSampleEvents() {
        return new Event[] {
            new Event()
        };
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        AddressBook sampleAb = new AddressBook();
        for (Employee sampleEmployee : getSampleEmployees()) {
            sampleAb.addEmployee(sampleEmployee);
        }
        return sampleAb;
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        return Arrays.stream(strings)
                .map(Tag::new)
                .collect(Collectors.toSet());
    }

    public static ReadOnlyEventBook getSampleEventBook() {
        EventBook sampleAb = new EventBook();
        for (Event sampleEmployee : getSampleEvents()) {
            sampleAb.addEvent(sampleEmployee);
        }
        return (ReadOnlyEventBook) sampleAb;
    }
}
