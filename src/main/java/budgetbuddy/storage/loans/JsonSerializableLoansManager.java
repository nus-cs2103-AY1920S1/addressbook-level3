package budgetbuddy.storage.loans;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import budgetbuddy.commons.exceptions.IllegalValueException;
import budgetbuddy.model.LoansManager;
import budgetbuddy.model.loan.Loan;

/**
 * An immutable LoansManager that is serializable to JSON format.
 */
@JsonRootName(value = "loansmanager")
public class JsonSerializableLoansManager {

    private final List<JsonAdaptedLoan> loans = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableLoansManager} with the given loans.
     */
    @JsonCreator
    public JsonSerializableLoansManager(@JsonProperty("loans") List<JsonAdaptedLoan> persons) {
        this.loans.addAll(persons);
    }

    /**
     * Converts a given {@code LoansManager} into this class for Jackson use.
     * @param source Future changes to the source will not affect the created {@code JsonSerializableLoansManager}.
     */
    public JsonSerializableLoansManager(LoansManager source) {
        loans.addAll(source.getLoans().stream().map(JsonAdaptedLoan::new).collect(Collectors.toList()));
    }

    /**
     * Converts this loans manager into the model's {@code LoansManager} object.
     * @throws IllegalValueException If any data constraints are violated.
     */
    public LoansManager toModelType() throws IllegalValueException {
        List<Loan> loanList = new ArrayList<>();
        for (JsonAdaptedLoan jsonAdaptedLoan : loans) {
            loanList.add(jsonAdaptedLoan.toModelType());
        }
        return new LoansManager(loanList);
    }
}
