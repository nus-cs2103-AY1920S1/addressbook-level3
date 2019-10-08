package seedu.address.storage;

import static seedu.address.logic.commands.EditIncomeCommand.MESSAGE_DUPLICATE_INCOME;
import static seedu.address.logic.commands.EditClaimCommand.MESSAGE_DUPLICATE_CLAIM;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.AddressBook;
import seedu.address.model.claim.Claim;
import seedu.address.model.income.Income;
import seedu.address.model.person.Person;
import seedu.address.model.ReadOnlyAddressBook;

/**
 * An Immutable AddressBook that is serializable to JSON format.
 */
@JsonRootName(value = "addressbook")
class JsonSerializableAddressBook {

    public static final String MESSAGE_DUPLICATE_PERSON = "Persons list contains duplicate person(s).";


    private final List<JsonAdaptedPerson> persons = new ArrayList<>();
    private final List<JsonAdaptedIncome> incomes = new ArrayList<>();
    private final List<JsonAdaptedClaim> claims = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableAddressBook} with the given persons.
     */
    @JsonCreator
    public JsonSerializableAddressBook(@JsonProperty("persons") List<JsonAdaptedPerson> persons,
                                       @JsonProperty("incomes") List<JsonAdaptedIncome> incomes,
                                       @JsonProperty("claims") List<JsonAdaptedClaim> claims) {
        this.persons.addAll(persons);
        this.claims.addAll(claims);
        this.incomes.addAll(incomes);
    }

    /**
     * Converts a given {@code ReadOnlyAddressBook} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableAddressBook}.
     */
    public JsonSerializableAddressBook(ReadOnlyAddressBook source) {
        persons.addAll(source.getPersonList().stream().map(JsonAdaptedPerson::new).collect(Collectors.toList()));
        incomes.addAll(source.getIncomeList().stream().map(JsonAdaptedIncome::new).collect(Collectors.toList()));
        claims.addAll(source.getClaimList().stream().map(JsonAdaptedClaim::new).collect(Collectors.toList()));
    }

    /**
     * Converts this address book into the model's {@code AddressBook} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public AddressBook toModelType() throws IllegalValueException {
        AddressBook addressBook = new AddressBook();
        for (JsonAdaptedPerson jsonAdaptedPerson : persons) {
            Person person = jsonAdaptedPerson.toModelType();
            if (addressBook.hasPerson(person)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_PERSON);
            }
            addressBook.addPerson(person);
        }

        for (JsonAdaptedIncome jsonAdaptedIncome : incomes) {
            Income income = jsonAdaptedIncome.toModelType();
            if (addressBook.hasIncome(income)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_INCOME);
            }
            addressBook.addIncome(income);
        }
        for (JsonAdaptedClaim jsonAdaptedClaim : claims) {
            Claim claim = jsonAdaptedClaim.toModelType();
            if (addressBook.hasClaim(claim)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_CLAIM);
            }
            addressBook.addClaim(claim);
        }
      
        return addressBook;
    }

}
