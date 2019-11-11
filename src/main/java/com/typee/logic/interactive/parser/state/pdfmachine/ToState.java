package com.typee.logic.interactive.parser.state.pdfmachine;

import static com.typee.logic.interactive.parser.CliSyntax.PREFIX_TO;
import static java.util.Objects.requireNonNull;

import java.util.Optional;

import com.typee.logic.interactive.parser.ArgumentMultimap;
import com.typee.logic.interactive.parser.Prefix;
import com.typee.logic.interactive.parser.state.PenultimateState;
import com.typee.logic.interactive.parser.state.State;
import com.typee.logic.interactive.parser.state.exceptions.StateTransitionException;
import com.typee.model.person.Name;

/**
 * Represents the penultimate state in the state machine that builds the {@code PdfCommand}.
 * Accepts the receiver.
 */
public class ToState extends PenultimateState {

    private static final String MESSAGE_CONSTRAINTS = "Who is the receiver of the document? Please enter the name "
            + "prefixed by " + PREFIX_TO.getPrefix() + ". Example - [t/Damith]";
    private static final String MESSAGE_INVALID_INPUT = "Invalid input! Please enter a valid name after "
            + PREFIX_TO.getPrefix() + ". Names must be in English and contain only alphanumeric characters.";

    protected ToState(ArgumentMultimap soFar) {
        super(soFar);
    }

    @Override
    public State transition(ArgumentMultimap newArgs) throws StateTransitionException {
        requireNonNull(newArgs);

        Optional<String> from = newArgs.getValue(PREFIX_TO);
        performGuardChecks(newArgs, from);
        collateArguments(this, newArgs, PREFIX_TO);

        enforceNoExcessiveArguments(newArgs);

        return new PdfEndState(soFar);
    }

    private void performGuardChecks(ArgumentMultimap newArgs, Optional<String> from)
            throws StateTransitionException {
        disallowDuplicatePrefix(newArgs);
        requireKeywordPresence(from, MESSAGE_INVALID_INPUT);
        enforceValidity(from);
    }

    /**
     * Ensures that the receiver is valid.
     *
     * @param to Receiver.
     * @throws StateTransitionException If the receiver is invalid.
     */
    private void enforceValidity(Optional<String> to) throws StateTransitionException {
        String name = to.get();
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
        return PREFIX_TO;
    }
}
