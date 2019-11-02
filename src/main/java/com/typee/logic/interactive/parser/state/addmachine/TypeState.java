package com.typee.logic.interactive.parser.state.addmachine;

import static com.typee.logic.interactive.parser.CliSyntax.PREFIX_ENGAGEMENT_TYPE;
import static java.util.Objects.requireNonNull;

import java.util.Optional;

import com.typee.logic.interactive.parser.ArgumentMultimap;
import com.typee.logic.interactive.parser.state.State;
import com.typee.logic.interactive.parser.state.StateTransitionException;

public class TypeState implements State {

    private static final String MESSAGE_CONSTRAINTS = "The engagement should be an appointment,"
            + " meeting or interview.";
    private static final String MESSAGE_MISSING_KEYWORD = "Please enter a valid engagement type following"
            + " \"t/\".";
    private static final String MESSAGE_DUPLICATE_ARGUMENTS = "The parameter corresponding to the entered argument"
            + " has already been processed.";

    private final ArgumentMultimap soFar;

    public TypeState(ArgumentMultimap soFar) {
        this.soFar = soFar;
    }

    @Override
    public State transition(ArgumentMultimap newArgs) throws StateTransitionException {
        requireNonNull(soFar);
        requireNonNull(newArgs);

        Optional<String> typeValue = newArgs.getValue(PREFIX_ENGAGEMENT_TYPE);

        performGuardChecks(newArgs, typeValue);

        collateArguments(newArgs);

        return new DateState(soFar);
    }

    private void performGuardChecks(ArgumentMultimap newArgs, Optional<String> typeValue)
            throws StateTransitionException {
        if (!soFar.isDisjointWith(newArgs)) {
            throw new StateTransitionException(MESSAGE_DUPLICATE_ARGUMENTS);
        }

        if (typeValue.isEmpty()) {
            throw new StateTransitionException(MESSAGE_MISSING_KEYWORD);
        }

        if (!isValid(typeValue.get())) {
            throw new StateTransitionException(MESSAGE_CONSTRAINTS);
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

    private boolean isValid(String commandText) {
        return commandText.equalsIgnoreCase("Meeting")
                || commandText.equalsIgnoreCase("Interview")
                || commandText.equalsIgnoreCase("Appointment");
    }

    private void collateArguments(ArgumentMultimap newArgs) {
        String engagementType = newArgs.getValue(PREFIX_ENGAGEMENT_TYPE).get();
        soFar.put(PREFIX_ENGAGEMENT_TYPE, engagementType);
        newArgs.clearValues(PREFIX_ENGAGEMENT_TYPE);
    }
}
