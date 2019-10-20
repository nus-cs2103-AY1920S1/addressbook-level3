package budgetbuddy.storage.loans;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import budgetbuddy.commons.exceptions.IllegalValueException;
import budgetbuddy.model.LoansManager;
import budgetbuddy.model.person.Person;

/**
 * An immutable LoansManager that is serializable to JSON format.
 */
@JsonRootName(value = "loansmanager")
public class JsonSerializableLoansManager {

    public static final String MESSAGE_DUPLICATE_PERSON = "Persons list contains duplicate person(s).";

    private final List<JsonAdaptedPerson> persons = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableLoansManager} with the given persons.
     */
    @JsonCreator
    public JsonSerializableLoansManager(@JsonProperty("persons") List<JsonAdaptedPerson> persons) {
        this.persons.addAll(persons);
    }

    /**
     * Converts a given {@code LoansManager} into this class for Jackson use.
     * @param source Future changes to the source will not affect the created {@code JsonSerializableLoansManager}.
     */
    public JsonSerializableLoansManager(LoansManager source) {
        persons.addAll(source.getPersonsList().stream().map(JsonAdaptedPerson::new).collect(Collectors.toList()));
    }

    /**
     * Converts this loans manager into the model's {@code LoansManager} object.
     * @throws IllegalValueException If any data constraints are violated.
     */
    public LoansManager toModelType() throws IllegalValueException {
        List<Person> personsList = new ArrayList<Person>();
        for (JsonAdaptedPerson jsonAdaptedPerson : persons) {
            Person person = jsonAdaptedPerson.toModelType();
            if (personsList.contains(person)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_PERSON);
            }
            personsList.add(person);
        }
        return new LoansManager(personsList);
    }
}
