package seedu.jarvis.storage.finance;

import static seedu.jarvis.logic.commands.finance.EditInstallmentCommand.EditInstallmentDescriptor;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.jarvis.commons.exceptions.IllegalValueException;
import seedu.jarvis.model.finance.installment.InstallmentDescription;
import seedu.jarvis.model.finance.installment.InstallmentMoneyPaid;
import seedu.jarvis.storage.JsonAdapter;

/**
 * Jackson-friendly version of {@link EditInstallmentDescriptor}
 */
public class JsonAdaptedEditInstallmentDescriptor implements JsonAdapter<EditInstallmentDescriptor> {
    private final String description;
    private final String amount;

    /**
     * Constructs a {@code JsonAdaptedEditInstallmentDescriptor} with the given description.
     *
     * @param description Installment description, can be null.
     * @param amount Installment amount, can be null.
     */
    @JsonCreator
    public JsonAdaptedEditInstallmentDescriptor(@JsonProperty("description") String description,
                                                @JsonProperty("amount") String amount) {
        this.description = description;
        this.amount = amount;
    }

    /**
     * Converts a given {@code EditInstallmentDescriptor} into this class for Jackson use.
     *
     * @param editInstallmentDescriptor {@code EditInstallmentDescriptor} to be converted for Jackson use.
     */
    public JsonAdaptedEditInstallmentDescriptor(EditInstallmentDescriptor editInstallmentDescriptor) {
        description = editInstallmentDescriptor.getDescription()
                .map(InstallmentDescription::getInstallmentDescription)
                .orElse(null);
        amount = editInstallmentDescriptor.getMoneyPaid()
                .map(InstallmentMoneyPaid::toString)
                .orElse(null);
    }

    /**
     * Converts this Jackson-friendly adapted descriptor into the model's {@code EditInstallmentDescriptor} object.
     *
     * @return {@code EditInstallmentDescriptor} object.
     * @throws IllegalValueException If there were any data constraints violated in the adapted
     * {@code EditInstallmentDescriptor}.
     */
    @Override
    public EditInstallmentDescriptor toModelType() throws IllegalValueException {
        EditInstallmentDescriptor editInstallmentDescriptor = new EditInstallmentDescriptor();
        editInstallmentDescriptor.setDescription(new InstallmentDescription(description));
        editInstallmentDescriptor.setMoneyPaid(new InstallmentMoneyPaid(amount));
        return editInstallmentDescriptor;
    }
}
