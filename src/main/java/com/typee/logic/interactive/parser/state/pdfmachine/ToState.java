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

    private static final String MESSAGE_CONSTRAINTS = "Sender name stored. Please enter the name of the receiver,"
            + " i.e. the person who the report is sent to, prefixed by \"t/\".";
    private static final String MESSAGE_MISSING_KEYWORD = "Invalid input! Please enter a valid name after \"t/\".";

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
        requireKeywordPresence(from, MESSAGE_MISSING_KEYWORD);
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
        return PREFIX_TO;
    }
}
