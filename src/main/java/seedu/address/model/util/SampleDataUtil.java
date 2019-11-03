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
import seedu.address.model.employee.EmployeePay;
import seedu.address.model.employee.EmployeePhone;
import seedu.address.model.event.Event;
import seedu.address.model.event.EventDate;
import seedu.address.model.event.EventManpowerNeeded;
import seedu.address.model.event.EventName;
import seedu.address.model.event.EventVenue;
import seedu.address.model.tag.Tag;



/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    public static Employee[] getSampleEmployees() {
        return new Employee[] {
            new Employee(new EmployeeId(), new EmployeeName("Alex Yeoh"), new EmployeeGender("male"),
                    new EmployeePay("9"),
                    new EmployeePhone("87438807"),
                    new EmployeeEmail("alexyeoh@example.com"),
                    new EmployeeAddress("Blk 30 Geylang Street 29, #06-40"),
                    new EmployeeJoinDate(LocalDate.now()), getTagSet("friends")),
            new Employee(new EmployeeId(), new EmployeeName("Bernice Yu"), new EmployeeGender("female"),
                    new EmployeePay("9"),
                    new EmployeePhone("87438807"),
                    new EmployeeEmail("bbb@example.com"),
                    new EmployeeAddress("Blk 30 BBB Street 29, #06-40"),
                    new EmployeeJoinDate(LocalDate.now()), getTagSet("friends")),
            new Employee(new EmployeeId(), new EmployeeName("Charlotte Oliveiro"), new EmployeeGender("female"),
                    new EmployeePay("9"),
                    new EmployeePhone("89123407"),
                    new EmployeeEmail("ccc@example.com"),
                    new EmployeeAddress("Blk 30 CCC Street 29, #06-40"),
                    new EmployeeJoinDate(LocalDate.now()), getTagSet("friends")),
            new Employee(new EmployeeId(), new EmployeeName("David Li"), new EmployeeGender("male"),
                    new EmployeePay("9"),
                    new EmployeePhone("87499807"),
                    new EmployeeEmail("ddd@example.com"),
                    new EmployeeAddress("Blk 30 DDD Street 29, #06-40"),
                    new EmployeeJoinDate(LocalDate.now()), getTagSet("friends")),
            new Employee(new EmployeeId(), new EmployeeName("Irfan Ibrahim"), new EmployeeGender("male"),
                    new EmployeePay("9"), new EmployeePhone("87438807"),
                    new EmployeeEmail("eee@example.com"),
                    new EmployeeAddress("Blk 30 EEE Street 29, #06-40"),
                    new EmployeeJoinDate(LocalDate.now()), getTagSet("friends")),
            new Employee(new EmployeeId(), new EmployeeName("Roy Balakrishnan"), new EmployeeGender("male"),
                    new EmployeePay("9"),
                    new EmployeePhone("87438807"),
                    new EmployeeEmail("fff@example.com"),
                    new EmployeeAddress("Blk 30 FFF Street 29, #06-40"),
                    new EmployeeJoinDate(LocalDate.now()), getTagSet("friends")),
            new Employee(new EmployeeId(), new EmployeeName("George Michael"), new EmployeeGender("male"),
                    new EmployeePay("9"),
                    new EmployeePhone("87438807"),
                    new EmployeeEmail("ggg@example.com"),
                    new EmployeeAddress("Blk 30 GGG Street 99, #06-40"),
                    new EmployeeJoinDate(LocalDate.now()), getTagSet("friends")),

        };
    }

    public static Event[] getSampleEvents() {
        return new Event[] {
            new Event(new EventName("Musical"), new EventVenue("NUS"), new EventManpowerNeeded("5"),
                    new EventDate(LocalDate.of(2019, 10, 20)),
                    new EventDate(LocalDate.of(2019, 10, 20)),
                            getTagSet("music")),
            new Event(new EventName("Party"), new EventVenue("ABC Hotel"), new EventManpowerNeeded("20"),
                    new EventDate(LocalDate.of(2019, 10, 20)),
                    new EventDate(LocalDate.of(2019, 10, 20)),
                    getTagSet("informal", "above21")),
            new Event(new EventName("Talk by DEF Company"), new EventVenue("QWER Building, level 99"),
                    new EventManpowerNeeded("3"),
                    new EventDate(LocalDate.of(2019, 11, 20)),
                    new EventDate(LocalDate.of(2019, 11, 21)),
                    getTagSet("formal")),
            new Event(new EventName("Birthday party"), new EventVenue("George's House"),
                    new EventManpowerNeeded("3"),
                    new EventDate(LocalDate.of(2019, 11, 23)),
                    new EventDate(LocalDate.of(2019, 11, 24)),
                    getTagSet("fun")),
            new Event(new EventName("Concert"), new EventVenue("NUS Utown"),
                    new EventManpowerNeeded("5"),
                    new EventDate(LocalDate.of(2019, 11, 23)),
                    new EventDate(LocalDate.of(2019, 11, 24)),
                    getTagSet("fun", "music"))
        };
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        AddressBook sampleAb = new AddressBook();
        for (Employee sampleEmployee : getSampleEmployees()) {
            sampleAb.addEmployee(sampleEmployee);
        }
        return sampleAb;
    }


    public static ReadOnlyEventBook getSampleEventBook() {
        EventBook sampleEb = new EventBook();
        for (Event sampleEmployee : getSampleEvents()) {
            sampleEb.addEvent(sampleEmployee);
        }
        return (ReadOnlyEventBook) sampleEb;
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        return Arrays.stream(strings)
                .map(Tag::new)
                .collect(Collectors.toSet());
    }
}
