package budgetbuddy.storage.loans;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import budgetbuddy.commons.exceptions.IllegalValueException;
import budgetbuddy.model.LoansManager;
import budgetbuddy.model.loan.Debtor;
import budgetbuddy.model.loan.Loan;

/**
 * An immutable LoansManager that is serializable to JSON format.
 */
@JsonRootName(value = "loansmanager")
public class JsonSerializableLoansManager {

    public static final String MESSAGE_DUPLICATE_LOANS = "Loans.json contains duplicate person(s).";

    private final List<JsonAdaptedLoan> loans = new ArrayList<>();
    private final List<JsonAdaptedDebtor> debtors = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableLoansManager} with the given loans.
     */
    @JsonCreator
    public JsonSerializableLoansManager(@JsonProperty("loans") List<JsonAdaptedLoan> loans,
                                        @JsonProperty("debtors") List<JsonAdaptedDebtor> debtors) {
        this.loans.addAll(loans);
        this.debtors.addAll(debtors);
    }

    /**
     * Converts a given {@code LoansManager} into this class for Jackson use.
     * @param source Future changes to the source will not affect the created {@code JsonSerializableLoansManager}.
     */
    public JsonSerializableLoansManager(LoansManager source) {
        loans.addAll(source.getLoans().stream().map(JsonAdaptedLoan::new).collect(Collectors.toList()));
        debtors.addAll(source.getDebtors().stream().map(JsonAdaptedDebtor::new).collect(Collectors.toList()));
    }

    /**
     * Converts this loans manager into the model's {@code LoansManager} object.
     * @throws IllegalValueException If any data constraints are violated.
     */
    public LoansManager toModelType() throws IllegalValueException {
        List<Loan> loanList = new ArrayList<>();
        for (JsonAdaptedLoan jsonAdaptedLoan : loans) {
            Loan loan = jsonAdaptedLoan.toModelType();
            if (loanList.contains(loan)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_LOANS);
            }
            loanList.add(jsonAdaptedLoan.toModelType());
        }
        List<Debtor> debtorList = new ArrayList<>();
        for (JsonAdaptedDebtor jsonAdaptedDebtor : debtors) {
            debtorList.add(jsonAdaptedDebtor.toModelType());
        }
        LoansManager loansManager = new LoansManager(loanList);
        loansManager.setDebtors(debtorList);
        return loansManager;
    }
}
