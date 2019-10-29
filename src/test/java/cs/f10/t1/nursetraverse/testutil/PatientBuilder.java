package cs.f10.t1.nursetraverse.testutil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

import cs.f10.t1.nursetraverse.model.patient.Address;
import cs.f10.t1.nursetraverse.model.patient.Email;
import cs.f10.t1.nursetraverse.model.patient.Name;
import cs.f10.t1.nursetraverse.model.patient.Patient;
import cs.f10.t1.nursetraverse.model.patient.Phone;
import cs.f10.t1.nursetraverse.model.tag.Tag;
import cs.f10.t1.nursetraverse.model.util.SampleDataUtil;
import cs.f10.t1.nursetraverse.model.visit.Visit;
import cs.f10.t1.nursetraverse.model.visittodo.VisitTodo;

/**
 * A utility class to help with building Patient objects.
 */
public class PatientBuilder {

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

    //Because visits need a reference to their patient in order to be created, this allows one to call the functions
    //"withOngoingVisits()" etc before calling build
    private List<Consumer<Patient>> visitsConsumerList;

    public PatientBuilder() {
        name = new Name(DEFAULT_NAME);
        phone = new Phone(DEFAULT_PHONE);
        email = new Email(DEFAULT_EMAIL);
        address = new Address(DEFAULT_ADDRESS);
        tags = new HashSet<>();
        visitTodos = new LinkedHashSet<>();
        visits = new ArrayList<>();
        visitsConsumerList = new ArrayList<>();
    }

    /**
     * Initializes the PatientBuilder with the data of {@code patientToCopy}.
     */
    public PatientBuilder(Patient patientToCopy) {
        name = patientToCopy.getName();
        phone = patientToCopy.getPhone();
        email = patientToCopy.getEmail();
        address = patientToCopy.getAddress();
        tags = new HashSet<>(patientToCopy.getTags());
        visitTodos = new LinkedHashSet<>(patientToCopy.getVisitTodos());
        visits = new ArrayList<>(patientToCopy.getVisits());
        visitsConsumerList = new ArrayList<>();
    }

    /**
     * Sets the {@code Name} of the {@code Patient} that we are building.
     */
    public PatientBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Patient} that we are building.
     */
    public PatientBuilder withTags(String ... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code Patient} that we are building.
     */
    public PatientBuilder withAddress(String address) {
        this.address = new Address(address);
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code Patient} that we are building.
     */
    public PatientBuilder withPhone(String phone) {
        this.phone = new Phone(phone);
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code Patient} that we are building.
     */
    public PatientBuilder withEmail(String email) {
        this.email = new Email(email);
        return this;
    }

    /**
     * Parses the {@code visitTodos} into a {@code Collection<VisitTodo>}
     * and set it to the {@code Patient} that we are building.
     */
    public PatientBuilder withVisitTodos(String ... visitTodos) {
        this.visitTodos = SampleDataUtil.getVisitTodos(visitTodos);
        return this;
    }

    /**
     * Adds finished visits to the list of visits.
     */
    public PatientBuilder withPreviousVisits() {
        visitsConsumerList.add(patient -> {
            patient.addVisits(SampleDataUtil.collateVisits(SampleDataUtil.makeVisit("",
                    "10-11-2019 1500",
                    "10-11-2019 1700",
                    SampleDataUtil.collateVisitTasks(
                            SampleDataUtil.makeVisitTask("Apply Eyedrops", "", true),
                            SampleDataUtil.makeVisitTask("Top-up medicine", "", true),
                            SampleDataUtil.makeVisitTask("Check his diet",
                                    "Stopped eating donuts", true),
                            SampleDataUtil.makeVisitTask("Check his sleep cycle",
                                    "Could not sleep on Monday and Thursday", true)
                    ),
                    patient),
                    SampleDataUtil.makeVisit("Patient was very quiet.",
                            "12-11-2018 1500",
                            "12-11-2018 1700",
                            SampleDataUtil.collateVisitTasks(
                                    SampleDataUtil.makeVisitTask("Check bed for bugs", "", true),
                                    SampleDataUtil.makeVisitTask("Top-up medicine", "", true)
                            ),
                            patient
                    )
            ));
        });
        return this;
    }

    /**
     * Adds finished visits to the list of visits.
     */
    public PatientBuilder withOngoingVisit() {
        visitsConsumerList.add(patient -> {
            patient.addVisits(SampleDataUtil.collateVisits(SampleDataUtil.makeVisit("",
                    "10-11-2019 1500",
                    null,
                    SampleDataUtil.collateVisitTasks(
                            SampleDataUtil.makeVisitTask("Apply Eyedrops", "", true),
                            SampleDataUtil.makeVisitTask("Top-up medicine", "Need more Vit. D",
                                    false)
                    ),
                    patient
            )));
        });
        return this;
    }

    /**
     * Builds and returns a Patient based on the functions called on this PatientBuilder object prior.
     */
    public Patient build() {
        Patient patient = new Patient(name, phone, email, address, tags, visitTodos, visits);
        //populate with visits
        for (Consumer<Patient> consumer : visitsConsumerList) {
            consumer.accept(patient);
        }
        return patient;
    }

}
