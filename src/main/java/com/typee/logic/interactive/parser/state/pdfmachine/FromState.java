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

    private static final String MESSAGE_CONSTRAINTS = "Who is the sender of the document? Please enter the name "
            + "prefixed by " + PREFIX_FROM.getPrefix() + ". Example - [f/Damith]";
    private static final String MESSAGE_INVALID_INPUT = "Invalid input! Please enter a valid name after "
            + PREFIX_FROM.getPrefix() + ". Names must be in English and contain only alphanumeric characters.";

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
        requireKeywordPresence(from, MESSAGE_INVALID_INPUT);
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
        return PREFIX_FROM;
    }
}
