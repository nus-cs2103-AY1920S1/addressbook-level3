package com.typee.logic.interactive.parser.state.deletemachine;

import static com.typee.logic.interactive.parser.CliSyntax.PREFIX_LIST_INDEX;
import static java.util.Objects.requireNonNull;

import java.util.Optional;

import com.typee.logic.interactive.parser.ArgumentMultimap;
import com.typee.logic.interactive.parser.InteractiveParserUtil;
import com.typee.logic.interactive.parser.Prefix;
import com.typee.logic.interactive.parser.exceptions.ParseException;
import com.typee.logic.interactive.parser.state.PenultimateState;
import com.typee.logic.interactive.parser.state.State;
import com.typee.logic.interactive.parser.state.exceptions.StateTransitionException;

/**
 * Represents the penultimate state of the state machine that builds the {@code DeleteCommand}.
 */
public class IndexState extends PenultimateState {

    private static final String MESSAGE_CONSTRAINTS = "Which engagement would you like to delete? Please enter the"
            + " index of the engagement you would like to delete. The index should be prefixed by "
            + PREFIX_LIST_INDEX.getPrefix() + ". Example - [i/5]";
    private static final String MESSAGE_INVALID_INPUT = "Invalid input! Please enter an index prefixed by "
            + PREFIX_LIST_INDEX.getPrefix() + ". Only positive indices are allowed.";

    public IndexState(ArgumentMultimap soFar) {
        super(soFar);
    }

    @Override
    public State transition(ArgumentMultimap newArgs) throws StateTransitionException {
        requireNonNull(newArgs);

        Optional<String> index = newArgs.getValue(PREFIX_LIST_INDEX);
        performGuardChecks(newArgs, index);
        collateArguments(this, newArgs, PREFIX_LIST_INDEX);

        enforceNoExcessiveArguments(newArgs);

        return new DeleteEndState(soFar);
    }

    private void performGuardChecks(ArgumentMultimap newArgs, Optional<String> index)
            throws StateTransitionException {
        disallowDuplicatePrefix(newArgs);
        requireKeywordPresence(index, MESSAGE_INVALID_INPUT);
        enforceValidity(index);
    }

    /**
     * Enforces the validity of the entered index.
     *
     * @param index Index.
     * @throws StateTransitionException If the index is invalid.
     */
    private void enforceValidity(Optional<String> index) throws StateTransitionException {
        try {
            InteractiveParserUtil.parseIndex(index.get());
        } catch (ParseException e) {
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
        return PREFIX_LIST_INDEX;
    }
}
