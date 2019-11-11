package seedu.jarvis.storage.finance;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.jarvis.commons.exceptions.IllegalValueException;
import seedu.jarvis.model.finance.FinanceTracker;
import seedu.jarvis.model.finance.MonthlyLimit;
import seedu.jarvis.model.finance.installment.Installment;
import seedu.jarvis.model.finance.purchase.Purchase;
import seedu.jarvis.storage.JsonAdapter;

/**
 * A {@code FinanceTracker} that is serializable to JSON format.
 */
@JsonRootName(value = "financetracker")
public class JsonSerializableFinanceTracker implements JsonAdapter<FinanceTracker> {

    public static final String MESSAGE_DUPLICATE_FINANCES = "Finances contains duplicate installment(s) / purchase(s)";

    private final String monthlyLimit;
    private final List<JsonAdaptedInstallment> installments = new ArrayList<>();
    private final List<JsonAdaptedPurchase> purchases = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableFinanceTracker} with the given finances.
     */
    @JsonCreator
    public JsonSerializableFinanceTracker(@JsonProperty("monthlyLimit") String monthlyLimit,
                                          @JsonProperty("installments") List<JsonAdaptedInstallment> installments,
                                          @JsonProperty("purchases") List<JsonAdaptedPurchase> purchases) {
        this.monthlyLimit = monthlyLimit;
        this.installments.addAll(installments);
        this.purchases.addAll(purchases);
    }

    /**
     * Converts a given {@code FinanceTracker} into this class for Jackson use.
     *
     * @param financeTracker Future changes to this will not affect the created {@code JsonSerializableFinanceTracker}.
     */
    public JsonSerializableFinanceTracker(FinanceTracker financeTracker) {
        monthlyLimit = financeTracker.getMonthlyLimit().map(MonthlyLimit::getLimitValue).orElse(null);
        installments.addAll(financeTracker.getInstallmentList()
                .stream()
                .map(JsonAdaptedInstallment::new)
                .collect(Collectors.toList()));
        purchases.addAll(financeTracker.getPurchaseList()
                .stream()
                .map(JsonAdaptedPurchase::new)
                .collect(Collectors.toList()));
    }

    /**
     * Converts this {@code JsonAdaptedFinanceTracker} into the model's {@code FinanceTracker} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    @Override
    public FinanceTracker toModelType() throws IllegalValueException {
        FinanceTracker financeTracker = new FinanceTracker();
        financeTracker.setMonthlyLimit(monthlyLimit != null ? new MonthlyLimit(monthlyLimit) : null);
        for (JsonAdaptedInstallment jsonAdaptedInstallment : installments) {
            Installment installment = jsonAdaptedInstallment.toModelType();
            if (financeTracker.hasInstallment(installment)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_FINANCES);
            }
            financeTracker.addInstallment(installment);
        }
        for (JsonAdaptedPurchase jsonAdaptedPurchase : purchases) {
            Purchase purchase = jsonAdaptedPurchase.toModelType();
            if (financeTracker.hasPurchase(purchase)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_FINANCES);
            }
            financeTracker.addPurchaseToBack(purchase);
        }
        return financeTracker;
    }
}
