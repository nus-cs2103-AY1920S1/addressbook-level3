package seedu.address.storage;

import static seedu.address.logic.commands.EditIncomeCommand.MESSAGE_DUPLICATE_INCOME;

import java.util.ArrayList;
import java.util.List;

import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.FinSec;
import seedu.address.model.ReadOnlyFinSec;
import seedu.address.model.autocorrectsuggestion.AutocorrectSuggestion;
import seedu.address.model.claim.Claim;
import seedu.address.model.commanditem.CommandItem;
import seedu.address.model.contact.Contact;
import seedu.address.model.income.Income;

/**
 * An Immutable FinSec that is serializable to JSON format.
 */
@JsonRootName(value = "finsec")

class JsonSerializableFinSec {

    public static final String MESSAGE_DUPLICATE_CLAIM = "Claims list contains duplicate claim(s).";
    public static final String MESSAGE_DUPLICATE_PERSON = "Persons list contains duplicate contact(s).";
    public static final String MESSAGE_DUPLICATE_COMMAND = "Commands list contains duplicate command(s).";

    private final List<JsonAdaptedIncome> incomes = new ArrayList<>();
    private final List<JsonAdaptedContact> contacts = new ArrayList<>();
    private final List<JsonAdaptedClaim> claims = new ArrayList<>();
    private final List<JsonAdaptedAutocorrectSuggestion> suggestions = new ArrayList<>();
    private final List<JsonAdaptedCommandItem> commands = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableFinSec} with the given views.
     */
    @JsonCreator
    public JsonSerializableFinSec(@JsonProperty("contacts") List<JsonAdaptedContact> contacts,
                                  @JsonProperty("incomes") List<JsonAdaptedIncome> incomes,
                                  @JsonProperty("claims") List<JsonAdaptedClaim> claims,
                                  @JsonProperty("suggestions") List<JsonAdaptedAutocorrectSuggestion> suggestions,
                                  @JsonProperty("commands") List<JsonAdaptedCommandItem> commands) {
        this.contacts.addAll(contacts);
        this.claims.addAll(claims);
        this.incomes.addAll(incomes);
        this.suggestions.addAll(suggestions);
        this.commands.addAll(commands);
    }

    /**
     * Converts a given {@code ReadOnlyFinSec} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableFinSec}.
     */

    public JsonSerializableFinSec(ReadOnlyFinSec source) {
        contacts.addAll(source.getContactList().stream().map(JsonAdaptedContact::new).collect(Collectors.toList()));
        claims.addAll(source.getClaimList().stream().map(JsonAdaptedClaim::new).collect(Collectors.toList()));
        incomes.addAll(source.getIncomeList().stream().map(JsonAdaptedIncome::new).collect(Collectors.toList()));
        suggestions.addAll(source.getAutocorrectSuggestionList().stream()
                                        .map(JsonAdaptedAutocorrectSuggestion::new).collect(Collectors.toList()));
        commands.addAll(source.getCommandsList().stream()
                                        .map(JsonAdaptedCommandItem::new).collect(Collectors.toList()));
    }

    /**
     * Converts this finSec into the model's {@code FinSec} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public FinSec toModelType() throws IllegalValueException {
        FinSec finSec = new FinSec();
        for (JsonAdaptedContact jsonAdaptedContact : contacts) {
            Contact contact = jsonAdaptedContact.toModelType();
            if (finSec.hasContact(contact)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_PERSON);
            }
            finSec.addContact(contact);
        }

        for (JsonAdaptedIncome jsonAdaptedIncome : incomes) {
            Income income = jsonAdaptedIncome.toModelType();
            if (finSec.hasIncome(income)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_INCOME);
            }
            finSec.addIncome(income);
        }

        for (JsonAdaptedClaim jsonAdaptedClaim : claims) {
            Claim claim = jsonAdaptedClaim.toModelType();
            if (finSec.hasClaim(claim)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_CLAIM);
            }
            finSec.addClaim(claim);
        }
        for (JsonAdaptedAutocorrectSuggestion jsonAdaptedAutocorrectSuggestion : suggestions) {
            AutocorrectSuggestion suggestion = jsonAdaptedAutocorrectSuggestion.toModelType();

            finSec.addAutocorrectSuggestion(suggestion);
        }
        for (JsonAdaptedCommandItem jsonAdaptedCommandItem : commands) {
            CommandItem command = jsonAdaptedCommandItem.toModelType();
            if (finSec.hasCommand(command)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_COMMAND);
            }
            finSec.addCommand(command);
        }
        return finSec;
    }
}
