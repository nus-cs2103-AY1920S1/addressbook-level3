package com.typee.logic.interactive.parser.state.addmachine;

import static com.typee.logic.interactive.parser.CliSyntax.PREFIX_ATTENDEES;
import static java.util.Objects.requireNonNull;

import java.util.Optional;

import com.typee.logic.interactive.parser.ArgumentMultimap;
import com.typee.logic.interactive.parser.state.State;
import com.typee.logic.interactive.parser.state.StateTransitionException;
import com.typee.logic.interactive.parser.Prefix;
import com.typee.model.engagement.AttendeeList;

public class AttendeesState extends State {

    private static final String MESSAGE_CONSTRAINTS = "Please enter the list of attendees separated by commas and"
            + " prefixed by \"a/\". Names are supported only in English.";
    private static final String MESSAGE_MISSING_KEYWORD = "Please enter the list of attendees prefixed by \"a/\".";

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
        requireKeywordPresence(attendees, MESSAGE_MISSING_KEYWORD);
        enforceValidity(attendees);
    }

    private void enforceValidity(Optional<String> attendees) throws StateTransitionException {
        if (!AttendeeList.validateNames(attendees.get())) {
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
