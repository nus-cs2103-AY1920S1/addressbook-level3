package com.typee.logic.interactive.parser.state.pdfmachine;

import static com.typee.logic.interactive.parser.CliSyntax.PREFIX_FROM;
import static java.util.Objects.requireNonNull;

import java.util.Optional;

import com.typee.logic.interactive.parser.ArgumentMultimap;
import com.typee.logic.interactive.parser.Prefix;
import com.typee.logic.interactive.parser.state.State;
import com.typee.logic.interactive.parser.state.exceptions.StateTransitionException;
import com.typee.model.person.Name;

/**
 * Represents a state in the state machine that builds the {@code PdfCommand}.
 * Accepts the sender.
 */
public class FromState extends State {

    private static final String MESSAGE_CONSTRAINTS = "Index stored. Please enter the name of the sender,"
            + " i.e. the person who the report is from, prefixed by \"f/\".";
    private static final String MESSAGE_MISSING_KEYWORD = "Invalid input! Please enter a valid name after \"f/\".";

    protected FromState(ArgumentMultimap soFar) {
        super(soFar);
    }

    @Override
    public State transition(ArgumentMultimap newArgs) throws StateTransitionException {
        requireNonNull(newArgs);

        Optional<String> from = newArgs.getValue(PREFIX_FROM);
        performGuardChecks(newArgs, from);
        collateArguments(this, newArgs, PREFIX_FROM);

        return new ToState(soFar);
    }

    private void performGuardChecks(ArgumentMultimap newArgs, Optional<String> from)
            throws StateTransitionException {
        disallowDuplicatePrefix(newArgs);
        requireKeywordPresence(from, MESSAGE_MISSING_KEYWORD);
        enforceValidity(from);
    }

    /**
     * Ensures that the sender is valid.
     *
     * @param from Sender.
     * @throws StateTransitionException If the sender is invalid.
     */
    private void enforceValidity(Optional<String> from) throws StateTransitionException {
        String name = from.get();
        if (!Name.isValidName(name)) {
            throw new StateTransitionException(Name.MESSAGE_CONSTRAINTS);
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
        return PREFIX_FROM;
    }
}
