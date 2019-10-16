package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FINE_INCREMENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LOAN_PERIOD;
import static seedu.address.logic.parser.CliSyntax.PREFIX_RENEW_PERIOD;

import java.util.Optional;

import seedu.address.commons.core.UserSettings;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.usersettings.FineIncrement;
import seedu.address.model.usersettings.LoanPeriod;
import seedu.address.model.usersettings.RenewPeriod;

/**
 * Sets the user configuration of the application.
 */
public class SetCommand extends Command {

    public static final String COMMAND_WORD = "set";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Sets custom user settings of the application."
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_LOAN_PERIOD + "LOAN_PERIOD] "
            + "[" + PREFIX_RENEW_PERIOD + "RENEW_PERIOD] "
            + "[" + PREFIX_FINE_INCREMENT + "FINE_INCREMENT]\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_LOAN_PERIOD + "14"
            + PREFIX_RENEW_PERIOD + "14";

    public static final String MESSAGE_SET_USER_SETTINGS_SUCCESS = "Set User Settings: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";

    private final SetUserSettingsDescriptor setUserSettingsDescriptor;

    /**
     * @param setUserSettingsDescriptor details of the new user settings
     */
    public SetCommand(SetUserSettingsDescriptor setUserSettingsDescriptor) {
        requireNonNull(setUserSettingsDescriptor);

        this.setUserSettingsDescriptor = new SetUserSettingsDescriptor(setUserSettingsDescriptor);
    }

    public SetUserSettingsDescriptor getSetUserSettingsDescriptor() {
        return setUserSettingsDescriptor;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        UserSettings userSettingsToEdit = model.getUserSettings();
        UserSettings editedUserSettings = createEditedUserSettings(userSettingsToEdit, setUserSettingsDescriptor);

        model.setUserSettings(editedUserSettings);

        return new CommandResult(String.format(MESSAGE_SET_USER_SETTINGS_SUCCESS, model.getUserSettings()));

    }

    /**
     * Creates and returns a {@code Person} with the details of {@code personToEdit}
     * edited with {@code editPersonDescriptor}.
     */
    private static UserSettings createEditedUserSettings(UserSettings userSettingsToEdit,
                                                      SetUserSettingsDescriptor setUserSettingsDescriptor) {

        LoanPeriod loanPeriod = setUserSettingsDescriptor.getLoanPeriod()
                .orElse(new LoanPeriod(userSettingsToEdit.getLoanPeriod()));

        RenewPeriod renewPeriod = setUserSettingsDescriptor.getRenewPeriod()
                .orElse(new RenewPeriod(userSettingsToEdit.getRenewPeriod()));

        FineIncrement fineIncrement = setUserSettingsDescriptor.getFineIncrement()
                .orElse(new FineIncrement(userSettingsToEdit.getFineIncrement()));


        return new UserSettings(loanPeriod.getLoanPeriod(), renewPeriod.getRenewPeriod(),
                fineIncrement.getFineIncrement());
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof SetCommand)) {
            return false;
        }

        // state check
        SetCommand e = (SetCommand) other;
        return setUserSettingsDescriptor.equals(e.getSetUserSettingsDescriptor());
    }

    /**
     * Stores the user settings to change. Each non-empty field value will replace the
     * corresponding field value of the book.
     */
    public static class SetUserSettingsDescriptor {
        private LoanPeriod loanPeriod;
        private RenewPeriod renewPeriod;
        private FineIncrement fineIncrement;

        public SetUserSettingsDescriptor() {}

        /**
         * Copy constructor.
         */
        public SetUserSettingsDescriptor(SetUserSettingsDescriptor toCopy) {
            setLoanPeriod(toCopy.loanPeriod);
            setRenewPeriod(toCopy.renewPeriod);
            setFineIncrement(toCopy.fineIncrement);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(loanPeriod, renewPeriod, fineIncrement);
        }

        public void setLoanPeriod(LoanPeriod loanPeriod) {
            this.loanPeriod = loanPeriod;
        }

        public Optional<LoanPeriod> getLoanPeriod() {
            return Optional.ofNullable(loanPeriod);
        }

        public void setRenewPeriod(RenewPeriod renewPeriod) {
            this.renewPeriod = renewPeriod;
        }

        public Optional<RenewPeriod> getRenewPeriod() {
            return Optional.ofNullable(renewPeriod);
        }

        public void setFineIncrement(FineIncrement fineIncrement) {
            this.fineIncrement = fineIncrement;
        }

        public Optional<FineIncrement> getFineIncrement() {
            return Optional.ofNullable(fineIncrement);
        }


        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof SetUserSettingsDescriptor)) {
                return false;
            }

            // state check
            SetUserSettingsDescriptor e = (SetUserSettingsDescriptor) other;

            return getLoanPeriod().equals(e.getLoanPeriod())
                    && getRenewPeriod().equals(e.getRenewPeriod())
                    && getFineIncrement().equals(e.getFineIncrement());
        }
    }
}