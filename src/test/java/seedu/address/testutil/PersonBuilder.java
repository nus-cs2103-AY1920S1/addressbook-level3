package seedu.address.testutil;

import static seedu.address.model.util.SampleDataUtil.collateVisitTasks;
import static seedu.address.model.util.SampleDataUtil.collateVisits;
import static seedu.address.model.util.SampleDataUtil.makeVisit;
import static seedu.address.model.util.SampleDataUtil.makeVisitTask;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;
import seedu.address.model.util.SampleDataUtil;
import seedu.address.model.visit.Visit;
import seedu.address.model.visittodo.VisitTodo;

/**
 * A utility class to help with building Person objects.
 */
public class PersonBuilder {

    public static final String DEFAULT_NAME = "Alice Pauline";
    public static final String DEFAULT_PHONE = "85355255";
    public static final String DEFAULT_EMAIL = "alice@gmail.com";
    public static final String DEFAULT_ADDRESS = "123, Jurong West Ave 6, #08-111";

    private Name name;
    private Phone phone;
    private Email email;
    private Address address;
    private Set<Tag> tags;
    private Collection<VisitTodo> visitTodos;
    private List<Visit> visits;

    public PersonBuilder() {
        name = new Name(DEFAULT_NAME);
        phone = new Phone(DEFAULT_PHONE);
        email = new Email(DEFAULT_EMAIL);
        address = new Address(DEFAULT_ADDRESS);
        tags = new HashSet<>();
        visitTodos = new LinkedHashSet<>();
        visits = new ArrayList<>();
    }

    /**
     * Initializes the PersonBuilder with the data of {@code personToCopy}.
     */
    public PersonBuilder(Person personToCopy) {
        name = personToCopy.getName();
        phone = personToCopy.getPhone();
        email = personToCopy.getEmail();
        address = personToCopy.getAddress();
        tags = new HashSet<>(personToCopy.getTags());
        visitTodos = new LinkedHashSet<>(personToCopy.getVisitTodos());
        visits = new ArrayList<>(personToCopy.getVisits());
    }

    /**
     * Sets the {@code Name} of the {@code Person} that we are building.
     */
    public PersonBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Person} that we are building.
     */
    public PersonBuilder withTags(String ... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code Person} that we are building.
     */
    public PersonBuilder withAddress(String address) {
        this.address = new Address(address);
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code Person} that we are building.
     */
    public PersonBuilder withPhone(String phone) {
        this.phone = new Phone(phone);
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code Person} that we are building.
     */
    public PersonBuilder withEmail(String email) {
        this.email = new Email(email);
        return this;
    }

    /**
     * Parses the {@code visitTodos} into a {@code Collection<VisitTodo>}
     * and set it to the {@code Person} that we are building.
     */
    public PersonBuilder withVisitTodos(String ... visitTodos) {
        this.visitTodos = SampleDataUtil.getVisitTodos(visitTodos);
        return this;
    }

    /**
     * Adds finished visits to the list of visits.
     */
    public PersonBuilder withPreviousVisits() {
        this.visits.addAll(collateVisits(makeVisit("",
                "10-11-2019 1500",
                "10-11-2019 1700",
                collateVisitTasks(
                        makeVisitTask("Apply Eyedrops", "", true),
                        makeVisitTask("Top-up medicine", "", true),
                        makeVisitTask("Check his diet",
                                "Stopped eating donuts", true),
                        makeVisitTask("Check his sleep cycle",
                                "Could not sleep on Monday and Thursday", true)
                        )
                ),
                makeVisit("Patient was very quiet.",
                        "12-11-2018 1500",
                        "12-11-2018 1700",
                        collateVisitTasks(
                                makeVisitTask("Check bed for bugs", "", true),
                                makeVisitTask("Top-up medicine", "", true)
                        )
                )
        ));
        return this;
    }

    /**
     * Adds finished visits to the list of visits.
     */
    public PersonBuilder withOngoingVisit() {
        this.visits.addAll(collateVisits(makeVisit("",
                "10-11-2019 1500",
                null,
                collateVisitTasks(
                        makeVisitTask("Apply Eyedrops", "", true),
                        makeVisitTask("Top-up medicine", "Need more Vit. D",
                                false)
                ))));
        return this;
    }

    public Person build() {
        return new Person(name, phone, email, address, tags, visitTodos, visits);
    }

}
