package cs.f10.t1.nursetraverse.storage;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import cs.f10.t1.nursetraverse.commons.exceptions.IllegalValueException;
import cs.f10.t1.nursetraverse.model.patient.Address;
import cs.f10.t1.nursetraverse.model.patient.Email;
import cs.f10.t1.nursetraverse.model.patient.Name;
import cs.f10.t1.nursetraverse.model.patient.Patient;
import cs.f10.t1.nursetraverse.model.patient.Phone;
import cs.f10.t1.nursetraverse.model.tag.Tag;
import cs.f10.t1.nursetraverse.model.visit.Visit;
import cs.f10.t1.nursetraverse.model.visittodo.VisitTodo;

/**
 * Jackson-friendly version of {@link Patient}.
 */
@JsonPropertyOrder({"name", "phone", "email", "address", "tagged", "visitTodos", "visits"})
public class JsonAdaptedPatient {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Patient's %s field is missing!";

    @JsonProperty("name")
    private final String name;
    @JsonProperty("phone")
    private final String phone;
    @JsonProperty("email")
    private final String email;
    @JsonProperty("address")
    private final String address;
    @JsonProperty("tagged")
    private final List<JsonAdaptedTag> tagged = new ArrayList<>();
    @JsonProperty("visitTodos")
    private final List<JsonAdaptedVisitTodo> visitTodos = new ArrayList<>();
    @JsonProperty("visits")
    private final List<JsonAdaptedVisit> visits = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedPatient} with the given patient details.
     */
    @JsonCreator
    public JsonAdaptedPatient(@JsonProperty("name") String name,
                             @JsonProperty("phone") String phone,
                             @JsonProperty("email") String email,
                             @JsonProperty("address") String address,
                             @JsonProperty("tagged") List<JsonAdaptedTag> tagged,
                             @JsonProperty("visitTodos") List<JsonAdaptedVisitTodo> visitTodos,
                             @JsonProperty("visits") List<JsonAdaptedVisit> visits) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        if (tagged != null) {
            this.tagged.addAll(tagged);
        }
        if (visitTodos != null) {
            this.visitTodos.addAll(visitTodos);
        }
        if (visits != null) {
            this.visits.addAll(visits);
        }
    }

    /**
     * Converts a given {@code Patient} into this class for Jackson use.
     */
    public JsonAdaptedPatient(Patient source) {
        name = source.getName().fullName;
        phone = source.getPhone().value;
        email = source.getEmail().value;
        address = source.getAddress().value;
        tagged.addAll(source.getTags().stream()
                .map(JsonAdaptedTag::new)
                .collect(Collectors.toList()));
        visitTodos.addAll(source.getVisitTodos().stream()
                .map(JsonAdaptedVisitTodo::new)
                .collect(Collectors.toList()));
        visits.addAll(source.getVisits().stream()
                .map(JsonAdaptedVisit::new)
                .collect(Collectors.toList()));
    }

    /**
     * Converts this Jackson-friendly adapted patient object into the model's {@code Patient} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted patient.
     */
    public Patient toModelType() throws IllegalValueException {
        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        final Name modelName = new Name(name);

        if (phone == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName()));
        }
        if (!Phone.isValidPhone(phone)) {
            throw new IllegalValueException(Phone.MESSAGE_CONSTRAINTS);
        }
        final Phone modelPhone = new Phone(phone);

        if (email == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName()));
        }
        if (!Email.isValidEmail(email)) {
            throw new IllegalValueException(Email.MESSAGE_CONSTRAINTS);
        }
        final Email modelEmail = new Email(email);

        if (address == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Address.class.getSimpleName()));
        }
        if (!Address.isValidAddress(address)) {
            throw new IllegalValueException(Address.MESSAGE_CONSTRAINTS);
        }
        final Address modelAddress = new Address(address);

        final List<Tag> patientTags = new ArrayList<>();
        for (JsonAdaptedTag tag : tagged) {
            patientTags.add(tag.toModelType());
        }
        final Set<Tag> modelTags = new HashSet<>(patientTags);

        final List<VisitTodo> visitTodoList = new ArrayList<>();
        for (JsonAdaptedVisitTodo visitTodo : visitTodos) {
            visitTodoList.add(visitTodo.toModelType());
        }
        final Collection<VisitTodo> modelVisitTodos = new LinkedHashSet<VisitTodo>(visitTodoList);

        final List<Visit> modelVisits = new ArrayList<>();
        Patient result = new Patient(modelName, modelPhone, modelEmail, modelAddress,
                modelTags, modelVisitTodos, modelVisits);
        for (JsonAdaptedVisit visit : visits) {
            result.addVisit(visit.toModelType(result));
        }

        return result;
    }

}
