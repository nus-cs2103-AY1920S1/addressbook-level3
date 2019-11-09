package seedu.address.logic.commands.currency;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.isAllPresent;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_RATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SYMBOL;

import java.util.Optional;

import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.currency.CustomisedCurrency;
import seedu.address.model.currency.Rate;
import seedu.address.model.currency.Symbol;
import seedu.address.model.itinerary.Name;

/**
 *
 * Constructs a command that attempts to modify the current values in the currency page.
 *
 */
public class EditCurrencyFieldCommand extends Command {
    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of your form "
            + "by the index of the form field as displayed, or by the various prefixes of the fields. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_SYMBOL + "SYMBOL] "
            + "[" + PREFIX_RATE + "RATE] "
            + "Example: " + COMMAND_WORD + " 1 " + PREFIX_SYMBOL + "$" + PREFIX_RATE + "2.00";

    public static final String MESSAGE_NOT_EDITED = "Please check the format of your command!";
    public static final String MESSAGE_EDIT_SUCCESS = "Edited the current form:%1$s";

    private final EditCurrencyDescriptor editCurrencyDescriptor;

    /**
     * @param editCurrencyDescriptor details to edit the currency with
     */
    public EditCurrencyFieldCommand(EditCurrencyDescriptor editCurrencyDescriptor) {
        requireNonNull(editCurrencyDescriptor);

        this.editCurrencyDescriptor = editCurrencyDescriptor;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        EditCurrencyDescriptor currentDescriptor = model.getPageStatus().getEditCurrencyDescriptor();
        EditCurrencyDescriptor newEditCurrencyDescriptor = currentDescriptor == null
                ? new EditCurrencyDescriptor(editCurrencyDescriptor)
                : new EditCurrencyDescriptor(currentDescriptor, editCurrencyDescriptor);

        model.setPageStatus(
                model.getPageStatus().withNewEditCurrencyDescriptor(newEditCurrencyDescriptor));

        return new CommandResult(String.format(MESSAGE_EDIT_SUCCESS, editCurrencyDescriptor));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditCurrencyFieldCommand)) {
            return false;
        }

        // state check
        EditCurrencyFieldCommand e = (EditCurrencyFieldCommand) other;
        return editCurrencyDescriptor.equals(e.editCurrencyDescriptor);
    }

    /**
     * Stores the details to edit the expense with. Each non-empty field value will replace the
     * corresponding field value of the expense.
     */
    public static class EditCurrencyDescriptor {
        private Optional<Name> name;
        private Optional<Symbol> symbol;
        private Optional<Rate> rate;

        public EditCurrencyDescriptor() {
            name = Optional.empty();
            symbol = Optional.empty();
            rate = Optional.empty();
        }

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditCurrencyDescriptor(EditCurrencyDescriptor toCopy) {
            name = toCopy.getName();
            symbol = toCopy.getSymbol();
            rate = toCopy.getRate();
        }


        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditCurrencyDescriptor(CustomisedCurrency toCopy) {
            setName(toCopy.getName());
            setSymbol(toCopy.getSymbol());
            setRate(toCopy.getRate());
        }


        /**
         * Overwrite constructor.
         * Constructs a new {@code EditCurrencyDescriptor} using an {@code oldDescriptor}, overwritten with
         * values of the {@code newDescriptor} where they exist.
         *
         * @param oldDescriptor Old {@code EditCurrencyDescriptor} to use.
         * @param newDescriptor New {@code EditCurrencyDescriptor} to use.
         */
        public EditCurrencyDescriptor(EditCurrencyDescriptor oldDescriptor,
                                 EditCurrencyDescriptor newDescriptor) {
            this();
            newDescriptor.name.ifPresentOrElse(this::setName, () ->
                    oldDescriptor.name.ifPresent(this::setName));

            newDescriptor.symbol.ifPresentOrElse(this::setSymbol, () ->
                    oldDescriptor.symbol.ifPresent(this::setSymbol));

            newDescriptor.rate.ifPresentOrElse(this::setRate, () ->
                    oldDescriptor.rate.ifPresent(this::setRate));
        }


        /**
         * Builds a new {@code Currency} instance.
         * Requires name, symbol and rate to have been set minimally.
         * Uses the Optional constructor for event to accommodate missing optional fields.
         *
         * @return New {@code Currency} created.
         * @throws NullPointerException If any of the fields are empty.
         */
        public CustomisedCurrency buildCurrency() {
            if (isAllPresent(name, symbol, rate)) {
                return new CustomisedCurrency(name.get(), symbol.get(), rate.get());
            } else {
                throw new NullPointerException();
            }
        }

        /**
         * Builds an edited {@code CustomisedCurrency} instance from this {@code EditCurrencyDescriptor}.
         * Uses the original currency information first, overwriting where the values exist.
         *
         * @param currency Source {@code CustomisedCurrency} instance.
         * @param currency
         * @return Edited {@code CustomisedCurrency} instance.
         */
        public CustomisedCurrency buildCurrency(CustomisedCurrency currency) {
            Name currencyName = currency.getName();
            Symbol symbol = currency.getSymbol();
            Rate rate = currency.getRate();

            if (this.name.isPresent()) {
                currencyName = this.name.get();
            }
            if (this.symbol.isPresent()) {
                symbol = this.symbol.get();
            }
            if (this.rate.isPresent()) {
                rate = this.rate.get();
            }

            return new CustomisedCurrency(currencyName, symbol, rate);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyPresent(name, symbol, rate);
        }


        public void setName(Name name) {
            this.name = Optional.of(name);
        }

        public Optional<Name> getName() {
            return name;
        }

        public void setSymbol(Symbol symbol) {
            this.symbol = Optional.of(symbol);
        }

        public Optional<Symbol> getSymbol() {
            return symbol;
        }

        public void setRate(Rate rate) {
            this.rate = Optional.of(rate);
        }

        public Optional<Rate> getRate() {
            return rate;
        }




        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditCurrencyDescriptor)) {
                return false;
            }

            // state check
            EditCurrencyDescriptor e = (EditCurrencyDescriptor) other;

            return getName().equals(e.getName())
                    && getSymbol().equals(e.getSymbol())
                    && getRate().equals(e.getRate());
        }

        @Override
        public String toString() {
            StringBuilder builder = new StringBuilder();

            this.name.ifPresent(name -> builder.append(" Name of currency: ").append(name));
            this.symbol.ifPresent(symbol -> builder.append(" Symbol of currency: ").append(symbol));
            this.rate.ifPresent(rate -> builder.append(" Exchange rate (use SGD as base):").append(rate));

            return builder.toString();
        }
    }
}
