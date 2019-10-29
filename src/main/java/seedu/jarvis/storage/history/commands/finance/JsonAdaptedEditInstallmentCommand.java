package seedu.jarvis.storage.history.commands.finance;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.jarvis.commons.exceptions.IllegalValueException;
import seedu.jarvis.logic.commands.Command;
import seedu.jarvis.logic.commands.finance.EditInstallmentCommand;
import seedu.jarvis.storage.JsonAdapter;
import seedu.jarvis.storage.commons.core.JsonAdaptedIndex;
import seedu.jarvis.storage.finance.JsonAdaptedEditInstallmentDescriptor;
import seedu.jarvis.storage.finance.JsonAdaptedInstallment;
import seedu.jarvis.storage.history.commands.JsonAdaptedCommand;

/**
 * Jackson-friendly version of {@link EditInstallmentCommand}.
 */
public class JsonAdaptedEditInstallmentCommand extends JsonAdaptedCommand implements JsonAdapter<Command> {
    private final JsonAdaptedIndex index;
    private final JsonAdaptedEditInstallmentDescriptor editInstallmentDescriptor;
    private final JsonAdaptedInstallment originalInstallment;
    private final JsonAdaptedInstallment editedInstallment;

    /**
     * Constructs a {@code JsonAdaptedEditInstallmentCommand} with the given {@code Index} of the installment to edit,
     * the {@code EditInstallmentDescriptor} to apply the changes to, the {@code Installment} that was edited to, and
     * the original {@code Installment}.
     *
     * @param index {@code Index} of the installment to be edited.
     * @param editInstallmentDescriptor {@code EditInstallmentDescriptor} that contains the changes.
     * @param originalInstallment Original {@code Installment}, which may be null.
     * @param editedInstallemnt Edited {@code Installment}, which may be null.
     */
    @JsonCreator
    public JsonAdaptedEditInstallmentCommand(@JsonProperty("index") JsonAdaptedIndex index,
                                             @JsonProperty("editInstallmentDescriptor")
                                                     JsonAdaptedEditInstallmentDescriptor editInstallmentDescriptor,
                                             @JsonProperty("originalInstallment")
                                                         JsonAdaptedInstallment originalInstallment,
                                             @JsonProperty("editInstallment")
                                                         JsonAdaptedInstallment editedInstallemnt) {
        this.index = index;
        this.editInstallmentDescriptor = editInstallmentDescriptor;
        this.originalInstallment = originalInstallment;
        this.editedInstallment = editedInstallemnt;
    }

    /**
     * Converts a given {@code EditInstallmentCommand} into this class for Jackson use.
     *
     * @param editInstallmentCommand {@code EditInstallmentCommand} to be used to construct the
     * {@code JsonAdaptedEditInstallmentCommand}.
     */
    public JsonAdaptedEditInstallmentCommand(EditInstallmentCommand editInstallmentCommand) {
        index = new JsonAdaptedIndex(editInstallmentCommand.getIndex());
        editInstallmentDescriptor = new JsonAdaptedEditInstallmentDescriptor(
                editInstallmentCommand.getEditInstallmentDescriptor());
        originalInstallment = editInstallmentCommand.getOriginalInstallment()
                .map(JsonAdaptedInstallment::new)
                .orElse(null);
        editedInstallment = editInstallmentCommand.getEditedInstallment()
                .map(JsonAdaptedInstallment::new)
                .orElse(null);
    }

    /**
     * Converts this Jackson-friendly adapted command into the model's {@code Command} object.
     *
     * @return {@code Command} of the Jackson-friendly adapted command.
     * @throws IllegalValueException if there were any data constraints violated in the adapted
     * {@code JsonAdaptedEditInstallmentCommand}.
     */
    @Override
    public Command toModelType() throws IllegalValueException {
        return new EditInstallmentCommand(
                index.toModelType(),
                editInstallmentDescriptor.toModelType(),
                originalInstallment != null ? originalInstallment.toModelType() : null,
                editedInstallment != null ? editedInstallment.toModelType() : null);
    }
}
