package com.typee.logic.interactive.parser.state.calendarmachine;

import static com.typee.logic.interactive.parser.CliSyntax.PREFIX_DATE;
import static java.util.Objects.requireNonNull;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.Optional;

import com.typee.logic.interactive.parser.ArgumentMultimap;
import com.typee.logic.interactive.parser.Prefix;
import com.typee.logic.interactive.parser.state.PenultimateState;
import com.typee.logic.interactive.parser.state.State;
import com.typee.logic.interactive.parser.state.StateUtil;
import com.typee.logic.interactive.parser.state.exceptions.StateTransitionException;

/**
 * Represents the penultimate state of the state machine that builds a close display command.
 */
public class CloseDisplayState extends PenultimateState {

    private static final String MESSAGE_CONSTRAINTS = "Which day's engagements would you like to close? Please enter a "
            + "date prefixed by " + PREFIX_DATE.getPrefix() + ". Example - [d/15/11/2019]";
    private static final String DATE_PATTERN = "dd/MM/uuuu";
    private static final String MESSAGE_INVALID_INPUT = "Invalid input! Please enter a valid date after "
            + PREFIX_DATE.getPrefix() + " in the dd/mm/yyyy format.";


    protected CloseDisplayState(ArgumentMultimap soFar) {
        super(soFar);
    }

    @Override
    public State transition(ArgumentMultimap newArgs) throws StateTransitionException {
        requireNonNull(newArgs);

        Optional<String> date = newArgs.getValue(PREFIX_DATE);
        performGuardChecks(newArgs, date);
        collateArguments(this, newArgs, PREFIX_DATE);

        enforceNoExcessiveArguments(newArgs);

        return new CloseDisplayEndState(soFar);
    }

    private void performGuardChecks(ArgumentMultimap newArgs, Optional<String> date)
            throws StateTransitionException {
        disallowDuplicatePrefix(newArgs);
        requireKeywordPresence(date, MESSAGE_INVALID_INPUT);
        enforceValidity(date);
    }

    /**
     * Enforces the validity of the {@code String} input.
     *
     * @param date {@code String} that represents a date.
     * @throws StateTransitionException If the {@code String} is not a valid date.
     */
    private void enforceValidity(Optional<String> date) throws StateTransitionException {
        String dateString = date.get();
        if (!isValid(dateString)) {
            throw new StateTransitionException(MESSAGE_INVALID_INPUT);
        }
    }

    /**
     * Returns true if the {@code String} is a valid date.
     *
     * @param date {@code String} representing a date.
     * @return true if the {@code String} is a valid date.
     */
    private boolean isValid(String date) {
        try {
            LocalDate localDate = StateUtil.parseLocalDate(date, DATE_PATTERN);
            return true;
        } catch (DateTimeException e) {
            return false;
        }
    }

    @Override
    public String getStateConstraints() {
        return MESSAGE_CONSTRAINTS;
    }

    @Override
    public boolean isEndState() {
        return false;
    }

    @Override
    public Prefix getPrefix() {
        return PREFIX_DATE;
    }
}
