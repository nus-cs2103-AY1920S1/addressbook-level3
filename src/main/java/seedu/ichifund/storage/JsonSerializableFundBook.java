package seedu.ichifund.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.ichifund.commons.exceptions.IllegalValueException;
import seedu.ichifund.model.FundBook;
import seedu.ichifund.model.ReadOnlyFundBook;
import seedu.ichifund.model.person.Person;

/**
 * An Immutable FundBook that is serializable to JSON format.
 */
@JsonRootName(value = "fundbook")
class JsonSerializableFundBook {

    public static final String MESSAGE_DUPLICATE_PERSON = "Persons list contains duplicate person(s).";

    private final List<JsonAdaptedPerson> persons = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableFundBook} with the given persons.
     */
    @JsonCreator
    public JsonSerializableFundBook(@JsonProperty("persons") List<JsonAdaptedPerson> persons) {
        this.persons.addAll(persons);
    }

    /**
     * Converts a given {@code ReadOnlyFundBook} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableFundBook}.
     */
    public JsonSerializableFundBook(ReadOnlyFundBook source) {
        persons.addAll(source.getPersonList().stream().map(JsonAdaptedPerson::new).collect(Collectors.toList()));
    }

    /**
     * Converts this fund book into the model's {@code FundBook} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public FundBook toModelType() throws IllegalValueException {
        FundBook fundBook = new FundBook();
        for (JsonAdaptedPerson jsonAdaptedPerson : persons) {
            Person person = jsonAdaptedPerson.toModelType();
            if (fundBook.hasPerson(person)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_PERSON);
            }
            fundBook.addPerson(person);
        }
        return fundBook;
    }

}
