package com.typee.logic.interactive.parser.state.sortmachine;

import static com.typee.logic.interactive.parser.CliSyntax.PREFIX_ORDER;
import static java.util.Objects.requireNonNull;

import java.util.Optional;

import com.typee.logic.interactive.parser.ArgumentMultimap;
import com.typee.logic.interactive.parser.Prefix;
import com.typee.logic.interactive.parser.state.PenultimateState;
import com.typee.logic.interactive.parser.state.State;
import com.typee.logic.interactive.parser.state.exceptions.StateTransitionException;

/**
 * Represents the penultimate state of the state machine that builds a {@code SortCommand}.
 */
public class OrderState extends PenultimateState {

    private static final String MESSAGE_CONSTRAINTS = "Which ordering would you like to sort by? Please enter the"
            + " ordering prefixed by " + PREFIX_ORDER.getPrefix()
            + ". Example - [o/ascending]";
    private static final String MESSAGE_INVALID_INPUT = "Invalid input! Please enter a valid sort order "
            + "after " + PREFIX_ORDER.getPrefix() + ". The allowed orderings are \"ascending\" and \"descending\".";

    private static final String KEYWORD_ASCENDING_ORDER = "ascending";
    private static final String KEYWORD_DESCENDING_ORDER = "descending";


    protected OrderState(ArgumentMultimap soFar) {
        super(soFar);
    }

    @Override
    public State transition(ArgumentMultimap newArgs) throws StateTransitionException {
        requireNonNull(newArgs);

        Optional<String> order = newArgs.getValue(PREFIX_ORDER);
        performGuardChecks(newArgs, order);
        collateArguments(this, newArgs, PREFIX_ORDER);

        enforceNoExcessiveArguments(newArgs);

        return new SortEndState(soFar);
    }

    private void performGuardChecks(ArgumentMultimap newArgs, Optional<String> order)
            throws StateTransitionException {
        disallowDuplicatePrefix(newArgs);
        requireKeywordPresence(order, MESSAGE_INVALID_INPUT);
        enforceValidity(order);
    }

    /**
     * Ensures that the entered order is valid.
     *
     * @param order Order.
     * @throws StateTransitionException If the order is invalid.
     */
    private void enforceValidity(Optional<String> order) throws StateTransitionException {
        String sortOrder = order.get();
        if (!isValid(sortOrder)) {
            throw new StateTransitionException(MESSAGE_INVALID_INPUT);
        }
    }

    private boolean isValid(String sortOrder) {
        return sortOrder.equalsIgnoreCase(KEYWORD_ASCENDING_ORDER)
                || sortOrder.equalsIgnoreCase(KEYWORD_DESCENDING_ORDER);
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
        return PREFIX_ORDER;
    }
}
