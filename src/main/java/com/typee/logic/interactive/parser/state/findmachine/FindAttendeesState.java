package com.typee.logic.interactive.parser.state.findmachine;

import static com.typee.logic.interactive.parser.CliSyntax.PREFIX_ATTENDEES;
import static java.util.Objects.requireNonNull;

import java.util.Optional;

import com.typee.logic.interactive.parser.ArgumentMultimap;
import com.typee.logic.interactive.parser.Prefix;
import com.typee.logic.interactive.parser.state.State;
import com.typee.logic.interactive.parser.state.StateTransitionException;
import com.typee.model.engagement.AttendeeList;

public class FindAttendeesState extends State {

    private static final String MESSAGE_CONSTRAINTS = "Please enter the attendees to search for prefixed by \"a/\"."
            + " The presence of ANY attendee will be considered a match. Vertical lines should separate attendees.";

    protected FindAttendeesState(ArgumentMultimap soFar) {
        super(soFar);
    }

    @Override
    public State transition(ArgumentMultimap newArgs) throws StateTransitionException {
        requireNonNull(newArgs);

        Optional<String> attendees = newArgs.getValue(PREFIX_ATTENDEES);
        performGuardChecks(newArgs, attendees);
        if (attendees.isPresent()) {
            collateArguments(this, newArgs, PREFIX_ATTENDEES);
        }

        return new FindPriorityState(soFar);
    }

    private void performGuardChecks(ArgumentMultimap newArgs, Optional<String> attendees)
            throws StateTransitionException {
        disallowDuplicatePrefix(newArgs);
        if (attendees.isPresent()) {
            enforceValidity(attendees.get());
        }
    }

    private void enforceValidity(String attendees) throws StateTransitionException {
        if (!AttendeeList.areValidNames(attendees)) {
            throw new StateTransitionException(AttendeeList.MESSAGE_CONSTRAINTS);
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
