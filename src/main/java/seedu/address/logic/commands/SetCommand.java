package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FINE_INCREMENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LOAN_PERIOD;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MAX_RENEWS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_RENEW_PERIOD;

import java.util.Optional;

import seedu.address.commons.core.UserSettings;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.usersettings.FineIncrement;
import seedu.address.model.usersettings.LoanPeriod;
import seedu.address.model.usersettings.MaxRenews;
import seedu.address.model.usersettings.RenewPeriod;

/**
 * Sets the user configuration of the application.
 */
public class SetCommand extends ReversibleCommand {

    public static final String COMMAND_WORD = "set";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Sets custom user settings of the application. \n"
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: "
            + "[" + PREFIX_LOAN_PERIOD + "LOAN_PERIOD] "
            + "[" + PREFIX_RENEW_PERIOD + "RENEW_PERIOD] "
            + "[" + PREFIX_FINE_INCREMENT + "FINE_INCREMENT] "
            + "[" + PREFIX_MAX_RENEWS + "MAX_RENEWS]\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_LOAN_PERIOD + "14 "
            + PREFIX_RENEW_PERIOD + "14 "
            + PREFIX_FINE_INCREMENT + "5 "
            + PREFIX_MAX_RENEWS + "2 ";

    public static final String MESSAGE_SET_USER_SETTINGS_SUCCESS = "Set User Settings:\n%1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";

    private final SetUserSettingsDescriptor setUserSettingsDescriptor;

    /**
     * Creates a SetCommand to set the {@code LoanPeriod}, {@code RenewPeriod} and {@code FineIncrement}.
     *
     * @param setUserSettingsDescriptor details of the new user settings.
     */
    public SetCommand(SetUserSettingsDescriptor setUserSettingsDescriptor) {
        requireNonNull(setUserSettingsDescriptor);

        this.setUserSettingsDescriptor = new SetUserSettingsDescriptor(setUserSettingsDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        UserSettings userSettingsToEdit = model.getUserSettings();
        UserSettings editedUserSettings = createEditedUserSettings(userSettingsToEdit, setUserSettingsDescriptor);

        model.setUserSettings(editedUserSettings);

        undoCommand = new SetCommand(getSettingsDescriptor(userSettingsToEdit));
        redoCommand = this;
        commandResult = new CommandResult(String.format(MESSAGE_SET_USER_SETTINGS_SUCCESS, model.getUserSettings()));

        return commandResult;

    }

    /**
     * Returns a {@code SetUserSettingsDescriptor} from {@code UserSettings}.
     *
     */
    private SetUserSettingsDescriptor getSettingsDescriptor(UserSettings userSettings) {
        SetUserSettingsDescriptor settingsDescriptor = new SetUserSettingsDescriptor();
        settingsDescriptor.setLoanPeriod(new LoanPeriod(userSettings.getLoanPeriod()));
        settingsDescriptor.setRenewPeriod(new RenewPeriod(userSettings.getRenewPeriod()));
        settingsDescriptor.setFineIncrement(new FineIncrement(userSettings.getFineIncrement()));
        settingsDescriptor.setMaxRenews(new MaxRenews(userSettings.getMaxRenews()));

        return settingsDescriptor;
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

        MaxRenews maxRenews = setUserSettingsDescriptor.getMaxRenews()
                .orElse(new MaxRenews(userSettingsToEdit.getMaxRenews()));

        return new UserSettings(loanPeriod.getLoanPeriod(), renewPeriod.getRenewPeriod(),
                fineIncrement.getFineIncrement(), maxRenews.getMaxRenews());
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
        return setUserSettingsDescriptor.equals(e.setUserSettingsDescriptor);
    }

    /**
     * Stores the user settings to change. Each non-empty field value will replace the
     * corresponding field value of the book.
     */
    public static class SetUserSettingsDescriptor {
        private LoanPeriod loanPeriod;
        private RenewPeriod renewPeriod;
        private FineIncrement fineIncrement;
        private MaxRenews maxRenews;

        public SetUserSettingsDescriptor() {}

        /**
         * Copy constructor.
         */
        public SetUserSettingsDescriptor(SetUserSettingsDescriptor toCopy) {
            setLoanPeriod(toCopy.loanPeriod);
            setRenewPeriod(toCopy.renewPeriod);
            setFineIncrement(toCopy.fineIncrement);
            setMaxRenews(toCopy.maxRenews);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(loanPeriod, renewPeriod, fineIncrement, maxRenews);
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

        public void setMaxRenews(MaxRenews maxRenews) {
            this.maxRenews = maxRenews;
        }

        public Optional<MaxRenews> getMaxRenews() {
            return Optional.ofNullable(maxRenews);
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
                    && getFineIncrement().equals(e.getFineIncrement())
                    && getMaxRenews().equals(e.getMaxRenews());
        }
    }
}
