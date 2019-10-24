package seedu.jarvis.logic.commands.finance;

import static java.util.Objects.requireNonNull;
import static seedu.jarvis.logic.parser.CliSyntax.FinanceSyntax.PREFIX_DESCRIPTION;
import static seedu.jarvis.logic.parser.CliSyntax.FinanceSyntax.PREFIX_MONEY;

import java.util.Optional;

import seedu.jarvis.commons.core.Messages;
import seedu.jarvis.commons.core.index.Index;
import seedu.jarvis.commons.util.CollectionUtil;
import seedu.jarvis.logic.commands.Command;
import seedu.jarvis.logic.commands.CommandResult;
import seedu.jarvis.logic.commands.exceptions.CommandException;
import seedu.jarvis.model.Model;
import seedu.jarvis.model.financetracker.exceptions.InstallmentNotFoundException;
import seedu.jarvis.model.financetracker.installment.Installment;
import seedu.jarvis.model.financetracker.installment.InstallmentDescription;
import seedu.jarvis.model.financetracker.installment.InstallmentMoneyPaid;

/**
 * Edits an existing installment using its displayed index in the finance tracker.
 */
public class EditInstallmentCommand extends Command {

    public static final String COMMAND_WORD = "edit-install";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the person identified "
            + "by the index number used in the displayed person list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_DESCRIPTION + "DESCRIPTION] "
            + "[" + PREFIX_MONEY + "MONEY] "
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_DESCRIPTION + "Netflix subscription "
            + PREFIX_MONEY + "13.50";

    public static final String MESSAGE_EDIT_INSTALLMENT_SUCCESS = "Edited installment: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_INSTALLMENT = "This installment already exists in your list.";

    public static final String MESSAGE_INVERSE_SUCCESS_REVERSE = "Reversed editing on installment: %1$s";
    public static final String MESSAGE_INVERSE_INSTALLMENT_NOT_FOUND = "Installment already deleted: %1$s";

    public static final boolean HAS_INVERSE = true;

    private final Index index;
    private final EditInstallmentDescriptor editInstallmentDescriptor;

    private Installment originalInstallment;
    private Installment editedInstallment;

    /**
     * Creates a {@code EditInstallmentCommand} to edit the specified {@code Installment}.
     *
     * @param index of the installment in the installment list to edit
     * @param editInstallmentDescriptor details to edit the installment with
     */
    public EditInstallmentCommand(Index index, EditInstallmentDescriptor editInstallmentDescriptor) {
        requireNonNull(index);
        requireNonNull(editInstallmentDescriptor);

        this.index = index;
        this.editInstallmentDescriptor = editInstallmentDescriptor;
    }

    @Override
    public String getCommandWord() {
        return COMMAND_WORD;
    }

    /**
     * Returns whether the command has an inverse execution.
     * If the command has no inverse execution, then calling {@code executeInverse}
     * will be guaranteed to always throw a {@code CommandException}.
     *
     * @return Whether the command has an inverse execution.
     */
    @Override
    public boolean hasInverseExecution() {
        return HAS_INVERSE;
    }

    /**
     * Edits a {@code Installment} in finance tracker with a new set of information from
     * {@code EditInstallmentDescriptor}.
     *
     * @param model {@code Model} which the command should operate on.
     * @return {@code CommandResult} of a successful edit.
     * @throws CommandException If the targetIndex is out of range of the number of installments in the finance tracker
     * in zero-based indexing, or if the new edited installment is already of an existing {@code Installment}
     * in finance tracker.
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        try {
            originalInstallment = model.getInstallment(index.getOneBased());
            Installment createdEditedInstallment = createEditedInstallment(originalInstallment,
                    editInstallmentDescriptor);
            if (originalInstallment.isSameInstallment(createdEditedInstallment)
                    && model.hasInstallment(createdEditedInstallment)) {
                throw new CommandException(MESSAGE_DUPLICATE_INSTALLMENT);
            }
            editedInstallment = createdEditedInstallment;

            model.setInstallment(originalInstallment, createdEditedInstallment);

            return new CommandResult(String.format(MESSAGE_EDIT_INSTALLMENT_SUCCESS, editedInstallment));

        } catch (InstallmentNotFoundException e) {
            throw new CommandException(Messages.MESSAGE_INVALID_INSTALLMENT_DISPLAYED_INDEX);
        }
    }

    /**
     * Creates and returns a {@code Installment} with the details of {@code installmentToEdit}
     * edited with {@code editInstallmentDescriptor}.
     */
    private static Installment createEditedInstallment(Installment installmentToEdit,
                                             EditInstallmentDescriptor editInstallmentDescriptor) {
        assert installmentToEdit != null;

        InstallmentDescription description = editInstallmentDescriptor
                .getDescription()
                .orElse(installmentToEdit.getDescription());
        InstallmentMoneyPaid moneyPaid = editInstallmentDescriptor
                .getMoneyPaid()
                .orElse(installmentToEdit.getMoneySpentOnInstallment());

        return new Installment(description, moneyPaid);
    }

    /**
     * Reverses the edits done to {@code Installment} from the finance tracker that was edited by this command's
     * execution if the installment is still in the finance tracker
     *
     * @param model {@code Model} which the command should inversely operate on.
     * @return {@code CommandResult} that installment was changed back if installment was in the finance tracker,
     * else {@code CommandResult} that the installment was already not in the finance tracker
     * @throws CommandException If installment to be edited is not found in the finance tracker
     */
    @Override
    public CommandResult executeInverse(Model model) throws CommandException {
        requireNonNull(model);

        if (!model.hasInstallment(editedInstallment)) {
            throw new CommandException(String.format(MESSAGE_INVERSE_INSTALLMENT_NOT_FOUND, editedInstallment));
        }

        model.setInstallment(editedInstallment, originalInstallment);

        return new CommandResult(String.format(MESSAGE_INVERSE_SUCCESS_REVERSE, originalInstallment));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditInstallmentCommand)) {
            return false;
        }

        // state check
        EditInstallmentCommand e = (EditInstallmentCommand) other;
        return index.equals(e.index)
                && editInstallmentDescriptor.equals(e.editInstallmentDescriptor);
    }

    /**
     * Stores the details to edit the installment with. Each non-empty field value will replace the
     * corresponding field value of the installment.
     */
    public static class EditInstallmentDescriptor {
        private InstallmentDescription description;
        private InstallmentMoneyPaid moneyPaid;

        public EditInstallmentDescriptor() {}

        /**
         * Copy constructor
         */
        public EditInstallmentDescriptor(EditInstallmentDescriptor toCopy) {
            setDescription(toCopy.description);
            setMoneyPaid(toCopy.moneyPaid);
        }

        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(description, moneyPaid);
        }

        public void setDescription(InstallmentDescription description) {
            this.description = description;
        }

        public Optional<InstallmentDescription> getDescription() {
            return Optional.ofNullable(description);
        }

        public void setMoneyPaid(InstallmentMoneyPaid moneyPaid) {
            this.moneyPaid = moneyPaid;
        }

        public Optional<InstallmentMoneyPaid> getMoneyPaid() {
            return Optional.ofNullable(moneyPaid);
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditInstallmentDescriptor)) {
                return false;
            }

            // state check
            EditInstallmentDescriptor e = (EditInstallmentDescriptor) other;

            return getDescription().equals(e.getDescription())
                    && getMoneyPaid().equals(e.getMoneyPaid());
        }
    }
}
