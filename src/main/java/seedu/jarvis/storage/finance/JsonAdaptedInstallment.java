package seedu.jarvis.storage.finance;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.jarvis.commons.exceptions.IllegalValueException;
import seedu.jarvis.model.finance.installment.Installment;
import seedu.jarvis.model.finance.installment.InstallmentDescription;
import seedu.jarvis.model.finance.installment.InstallmentMoneyPaid;
import seedu.jarvis.storage.JsonAdapter;

/**
 * Jackson-friendly version of {@link Installment}.
 */
public class JsonAdaptedInstallment implements JsonAdapter<Installment> {

    public static final String MESSAGE_INVALID_ATTRIBUTES = "Invalid installment attributes.";

    private final String description;
    private final String amount;

    /**
     * Constructs a {@code JsonAdaptedInstallment} with the given installment details.
     *
     * @param description {@code InstallmentDescription} of the installment.
     * @param amount {@code InstallmentMoneyPaid} of the installment.
     */
    @JsonCreator
    public JsonAdaptedInstallment(@JsonProperty("description") String description,
                                  @JsonProperty("amount") String amount) {
        this.description = description;
        this.amount = amount;
    }

    /**
     * Converts a given {@code Installment} into this class for Jackson use.
     *
     * @param installment {@code Installment} to be used to construct the {@code JsonAdaptedInstallment}.
     */
    public JsonAdaptedInstallment(Installment installment) {
        description = installment.getDescription().installmentDescription;
        amount = installment.getMoneySpentOnInstallment().toString();
    }

    /**
     * Converts this Jackson-friendly adapted {@code Installment} object into the model's {@code Installment} object.
     *
     * @return {@code Installment} of the Jackson-friendly adapted {@code Installment}.
     * @throws IllegalValueException If there were any data constraints violated in the adapted {@code Installment}.
     */
    @Override
    public Installment toModelType() throws IllegalValueException {
        boolean isValidDescription = description != null && InstallmentDescription.isValidDescription(description);
        boolean isValidAmount = amount != null && InstallmentMoneyPaid.isValidAmount(amount);

        if (!isValidDescription || !isValidAmount) {
            throw new IllegalValueException(MESSAGE_INVALID_ATTRIBUTES);
        }

        return new Installment(new InstallmentDescription(description), new InstallmentMoneyPaid(amount));
    }
}
