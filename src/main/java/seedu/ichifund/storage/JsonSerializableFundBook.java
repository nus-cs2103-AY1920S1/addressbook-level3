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
import seedu.ichifund.model.budget.Budget;
import seedu.ichifund.model.person.Person;
import seedu.ichifund.model.repeater.Repeater;
import seedu.ichifund.model.transaction.Transaction;

/**
 * An Immutable FundBook that is serializable to JSON format.
 */
@JsonRootName(value = "fundbook")
class JsonSerializableFundBook {

    public static final String MESSAGE_DUPLICATE_PERSON = "Persons list contains duplicate person(s).";
    public static final String MESSAGE_DUPLICATE_REPEATER = "Repeaters list contains duplicate repeater(s).";
    public static final String MESSAGE_DUPLICATE_BUDGET = "Budgets list contains duplicate budget(s).";

    private final List<JsonAdaptedPerson> persons = new ArrayList<>();
    private final List<JsonAdaptedTransaction> transactions = new ArrayList<>();
    private final List<JsonAdaptedRepeater> repeaters = new ArrayList<>();
    private final List<JsonAdaptedBudget> budgets = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableFundBook} with the given person and budget.
     */
    @JsonCreator
    public JsonSerializableFundBook(@JsonProperty("persons") List<JsonAdaptedPerson> persons,
                                    @JsonProperty("transactions") List<JsonAdaptedTransaction> transactions,
                                    @JsonProperty("repeaters") List<JsonAdaptedRepeater> repeaters,
                                    @JsonProperty("budgets") List<JsonAdaptedBudget> budgets) {
        this.persons.addAll(persons);
        this.transactions.addAll(transactions);
        this.repeaters.addAll(repeaters);
        this.budgets.addAll(budgets);
    }

    /**
     * Converts a given {@code ReadOnlyFundBook} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableFundBook}.
     */
    public JsonSerializableFundBook(ReadOnlyFundBook source) {
        persons.addAll(source.getPersonList().stream().map(JsonAdaptedPerson::new).collect(Collectors.toList()));
        transactions.addAll(source.getTransactionList().stream().map(JsonAdaptedTransaction::new)
                .collect(Collectors.toList()));
        repeaters.addAll(source.getRepeaterList().stream().map(JsonAdaptedRepeater::new).collect(Collectors.toList()));
        budgets.addAll(source.getBudgetList().stream().map(JsonAdaptedBudget::new).collect(Collectors.toList()));
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

        for (JsonAdaptedTransaction jsonAdaptedTransaction : transactions) {
            Transaction transaction = jsonAdaptedTransaction.toModelType();
            fundBook.addTransaction(transaction);
        }

        for (JsonAdaptedRepeater jsonAdaptedRepeater : repeaters) {
            Repeater repeater = jsonAdaptedRepeater.toModelType();
            if (fundBook.hasRepeater(repeater)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_REPEATER);
            }
            fundBook.addRepeater(repeater);
        }

        for (JsonAdaptedBudget jsonAdaptedBudget : budgets) {
            Budget budget = jsonAdaptedBudget.toModelType();
            if (fundBook.hasBudget(budget)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_BUDGET);
            }
            fundBook.addBudget(budget);
        }

        return fundBook;
    }

}
