package com.typee.logic.interactive.parser.state.addmachine;

import static com.typee.logic.interactive.parser.CliSyntax.PREFIX_ATTENDEES;
import static java.util.Objects.requireNonNull;

import java.util.Optional;

import com.typee.logic.interactive.parser.ArgumentMultimap;
import com.typee.logic.interactive.parser.Prefix;
import com.typee.logic.interactive.parser.state.State;
import com.typee.logic.interactive.parser.state.exceptions.StateTransitionException;
import com.typee.model.engagement.AttendeeList;

/**
 * Represents the state of the finite state machine that builds the {@code AddCommand}.
 * Handles input of the list of attendees.
 */
public class AttendeesState extends State {

    private static final String MESSAGE_CONSTRAINTS = "Who will be present at the engagement? Please enter the list of "
            + "attendees separated by vertical lines and prefixed by " + PREFIX_ATTENDEES.getPrefix() + ". "
            + "Example - [a/Prof Damith | Jon Snow]";
    private static final String MESSAGE_INVALID_INPUT = "Invalid input! Please enter a valid list of attendees after "
            + PREFIX_ATTENDEES.getPrefix() + ". Names of the attendees must be non-empty and in English. "
            + "Only alphanumeric characters are supported in names. Vertical lines must be used to separate names.";

    protected AttendeesState(ArgumentMultimap soFar) {
        super(soFar);
    }

    @Override
    public State transition(ArgumentMultimap newArgs) throws StateTransitionException {
        requireNonNull(newArgs);

        Optional<String> attendees = newArgs.getValue(PREFIX_ATTENDEES);
        performGuardChecks(newArgs, attendees);
        collateArguments(this, newArgs, PREFIX_ATTENDEES);

        return new PriorityState(soFar);
    }

    private void performGuardChecks(ArgumentMultimap newArgs, Optional<String> attendees)
            throws StateTransitionException {
        disallowDuplicatePrefix(newArgs);
        requireKeywordPresence(attendees, MESSAGE_INVALID_INPUT);
        enforceValidity(attendees);
    }

    private void enforceValidity(Optional<String> attendees) throws StateTransitionException {
        if (!AttendeeList.areValidNames(attendees.get())) {
            throw new StateTransitionException(MESSAGE_INVALID_INPUT);
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
        return PREFIX_ATTENDEES;
    }
}
