package com.typee.logic.interactive.parser.state.addmachine;

import static com.typee.logic.interactive.parser.CliSyntax.PREFIX_END_TIME;
import static com.typee.logic.interactive.parser.CliSyntax.PREFIX_START_TIME;
import static java.util.Objects.requireNonNull;

import java.util.Optional;

import com.typee.logic.interactive.parser.ArgumentMultimap;
import com.typee.logic.interactive.parser.InteractiveParserUtil;
import com.typee.logic.interactive.parser.Prefix;
import com.typee.logic.interactive.parser.state.State;
import com.typee.logic.interactive.parser.state.exceptions.StateTransitionException;

/**
 * Represents the state of the finite state machine that builds the {@code AddCommand}.
 * Handles input of the end date.
 */
public class EndDateState extends State {

    private static final String MESSAGE_CONSTRAINTS = "When should the engagement end? Please enter the end"
            + " date-time prefixed by " + PREFIX_END_TIME.getPrefix() + ". Example - [e/15/11/2019/1600]";
    private static final String MESSAGE_INVALID_INPUT = "Invalid input! Please enter a valid date-time after the"
            + " prefix " + PREFIX_END_TIME.getPrefix() + ". The date-time must conform to the dd/MM/yyyy/HHmm format"
            + " and occur after the start date-time.";

    protected EndDateState(ArgumentMultimap soFar) {
        super(soFar);
    }

    @Override
    public State transition(ArgumentMultimap newArgs) throws StateTransitionException {
        requireNonNull(newArgs);

        Optional<String> endDate = newArgs.getValue(PREFIX_END_TIME);
        performGuardChecks(newArgs, endDate);
        collateArguments(this, newArgs, PREFIX_END_TIME);

        return new LocationState(soFar);
    }

    private void performGuardChecks(ArgumentMultimap newArgs, Optional<String> endDate)
            throws StateTransitionException {
        disallowDuplicatePrefix(newArgs);
        requireKeywordPresence(endDate, MESSAGE_INVALID_INPUT);
        enforceValidity(endDate);
    }

    private void enforceValidity(Optional<String> endDate) throws StateTransitionException {
        if (!isValid(endDate.get())) {
            throw new StateTransitionException(MESSAGE_INVALID_INPUT);
        }
    }

    private boolean isValid(String endDate) {
        return InteractiveParserUtil.isValidDateTime(endDate)
                && InteractiveParserUtil.isValidTimeSlot(soFar.getValue(PREFIX_START_TIME).get(), endDate);
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
        return PREFIX_END_TIME;
    }
}
