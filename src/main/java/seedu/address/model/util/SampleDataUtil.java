package seedu.address.model.util;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;
import seedu.address.model.visit.EndDateTime;
import seedu.address.model.visit.Remark;
import seedu.address.model.visit.StartDateTime;
import seedu.address.model.visit.Visit;
import seedu.address.model.visittask.Detail;
import seedu.address.model.visittask.VisitTask;
import seedu.address.model.visittodo.VisitTodo;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    public static Person[] getSamplePersons() {
        return new Person[] {
            new Person(new Name("Alex Yeoh"),
                    new Phone("87438807"),
                    new Email("alexyeoh@example.com"),
                    new Address("Blk 30 Geylang Street 29, #06-40"),
                    getTagSet("friends"), getVisitTodos(),
                    collateVisits(makeVisit("Alex is in good health.",
                            "10-11-2019 1500",
                            "10-11-2019 1700",
                            collateVisitTasks(
                                    makeVisitTask("Apply Eyedrops", "", true),
                                    makeVisitTask("Top-up medicine", "", true),
                                    makeVisitTask("Check his diet",
                                            "Stopped eating donuts", true),
                                    makeVisitTask("Check his sleep cycle",
                                            "Could not sleep on Monday and Thursday", true)
                            )))),
            new Person(new Name("Bernice Yu"),
                    new Phone("99272758"),
                    new Email("berniceyu@example.com"),
                    new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"),
                    getTagSet("colleagues", "friends"),
                    getVisitTodos("Blood Pressure"),
                    collateVisits()),
            new Person(new Name("Charlotte Oliveiro"),
                    new Phone("93210283"),
                    new Email("charlotte@example.com"),
                    new Address("Blk 11 Ang Mo Kio Street 74, #11-04"),
                    getTagSet("neighbours"),
                    getVisitTodos("Body Temperature"),
                    collateVisits(makeVisit("Charlotte was very quiet.",
                            "12-11-2018 1500",
                            "12-11-2018 1700",
                            collateVisitTasks(
                                    makeVisitTask("Check bed for bugs", "", true),
                                    makeVisitTask("Top-up medicine", "", true)
                            )))),
            new Person(new Name("David Li"),
                    new Phone("91031282"),
                    new Email("lidavid@example.com"),
                    new Address("Blk 436 Serangoon Gardens Street 26, #16-43"),
                    getTagSet("family"),
                    getVisitTodos("Pain Level", "Check if patient has been exercising"),
                    collateVisits(makeVisit("",
                            "12-11-2018 1500",
                            null,
                            collateVisitTasks(
                                    makeVisitTask("Blood pressure", "140/90mmHg", true),
                                    makeVisitTask("Check bed for bugs", "", true),
                                    makeVisitTask("Top-up medicine", "", false),
                                    makeVisitTask("Ask spouse about David's condition",
                                            "", false)
                            )))),
            new Person(new Name("Irfan Ibrahim"),
                    new Phone("92492021"),
                    new Email("irfan@example.com"),
                    new Address("Blk 47 Tampines Street 20, #17-35"),
                    getTagSet("classmates"),
                    getVisitTodos("Check cognitive abilities"),
                    collateVisits()),
            new Person(new Name("Roy Balakrishnan"),
                    new Phone("92624417"),
                    new Email("royb@example.com"),
                    new Address("Blk 45 Aljunied Street 85, #11-31"),
                    getTagSet("colleagues"),
                    getVisitTodos("Check wounds"),
                    collateVisits())
        };
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        AddressBook sampleAb = new AddressBook();
        for (Person samplePerson : getSamplePersons()) {
            sampleAb.addPerson(samplePerson);
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

    /**
     * Returns a visitTodo collection containing the list of strings given.
     */
    public static Collection<VisitTodo> getVisitTodos(String... strings) {
        return Arrays.stream(strings)
                .map(VisitTodo::new)
                .collect(Collectors.toList());
    }

    /**
     * Returns a visit task list containing the list of visit tasks given.
     */
    public static List<VisitTask> collateVisitTasks(VisitTask... visitTasks) {
        return Arrays.stream(visitTasks).collect(Collectors.toList());
    }

    /**
     * Helper function to return a visit task.
     */
    public static VisitTask makeVisitTask(String description, String detail, boolean isDone) {
        return new VisitTask(new VisitTodo(description), new Detail(detail), isDone);
    }

    /**
     * Helper function to return a visit.
     */
    public static Visit makeVisit(String remark, String start, String end, List<VisitTask> visitTasks) {
        if (end == null) {
            return new Visit(new Remark(remark), new StartDateTime(start),
                    EndDateTime.UNFINISHED_VISIT_END_DATE_TIME, visitTasks);
        }
        return new Visit(new Remark(remark), new StartDateTime(start),
                new EndDateTime(end), visitTasks);
    }

    /**
     * Returns a visit list containing the list of visits given.
     */
    public static List<Visit> collateVisits(Visit... visits) {
        return Arrays.stream(visits).collect(Collectors.toList());
    }
}
