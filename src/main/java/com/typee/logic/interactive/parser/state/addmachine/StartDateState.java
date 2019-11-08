package com.typee.logic.interactive.parser.state.addmachine;

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
 * Handles input of the start date.
 */
public class StartDateState extends State {

    private static final String MESSAGE_CONSTRAINTS = "Please enter a start date and time prefixed by \"s/\"."
            + " The start time must conform to the dd/mm/yyyy/hhmm format.";
    private static final String MESSAGE_MISSING_KEYWORD = "Please enter a valid date and time after the prefix"
            + " \"s/\". Please conform to the dd/mm/yyyy/hhmm format.";

    protected StartDateState(ArgumentMultimap soFar) {
        super(soFar);
    }

    @Override
    public State transition(ArgumentMultimap newArgs) throws StateTransitionException {
        requireNonNull(newArgs);

        Optional<String> startDate = newArgs.getValue(PREFIX_START_TIME);
        performGuardChecks(newArgs, startDate);
        collateArguments(this, newArgs, PREFIX_START_TIME);

        return new EndDateState(soFar);
    }

    private void performGuardChecks(ArgumentMultimap newArgs, Optional<String> startDate)
            throws StateTransitionException {
        disallowDuplicatePrefix(newArgs);
        requireKeywordPresence(startDate, MESSAGE_MISSING_KEYWORD);
        enforceValidity(startDate);
    }

    private void enforceValidity(Optional<String> startDate) throws StateTransitionException {
        if (!isValid(startDate.get())) {
            throw new StateTransitionException(MESSAGE_CONSTRAINTS);
        }
    }

    private boolean isValid(String startTime) {
        return InteractiveParserUtil.isValidDateTime(startTime);
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
        return PREFIX_START_TIME;
    }
}
