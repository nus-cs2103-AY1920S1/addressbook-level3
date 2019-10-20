package budgetbuddy.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import budgetbuddy.model.LoansManager;

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
}
