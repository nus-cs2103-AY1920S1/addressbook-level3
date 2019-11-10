package com.typee.logic.interactive.parser.state.sortmachine;

import static com.typee.logic.interactive.parser.CliSyntax.PREFIX_PROPERTY;
import static java.util.Objects.requireNonNull;

import java.util.Optional;

import com.typee.logic.interactive.parser.ArgumentMultimap;
import com.typee.logic.interactive.parser.Prefix;
import com.typee.logic.interactive.parser.state.State;
import com.typee.logic.interactive.parser.state.exceptions.StateTransitionException;

/**
 * Initial state of the state machine that builds the {@code SortCommand}.
 */
public class PropertyState extends State {

    private static final String MESSAGE_CONSTRAINTS = "Sort command initiated. Please enter the property you would"
            + " like to sort by, prefixed by \"p/\". The sortable properties are start date, end date, description"
            + " and priority, to be specified as \"start\", \"end\", \"description\" and \"priority\" respectively.";
    private static final String MESSAGE_MISSING_KEYWORD = "Invalid input! Please enter a valid property after \"p\".";
    private static final String MESSAGE_INVALID_INPUT = "Invalid input! Accepted properties are"
            + " \"start\", \"end\", \"description\" and \"priority\".";
    private static final String KEYWORD_START_PROPERTY = "start";
    private static final String KEYWORD_END_PROPERTY = "end";
    private static final String KEYWORD_DESCRIPTION_PROPERTY = "description";
    private static final String KEYWORD_PRIORITY_PROPERTY = "priority";

    public PropertyState(ArgumentMultimap soFar) {
        super(soFar);
    }

    @Override
    public State transition(ArgumentMultimap newArgs) throws StateTransitionException {
        requireNonNull(newArgs);

        Optional<String> property = newArgs.getValue(PREFIX_PROPERTY);
        performGuardChecks(newArgs, property);
        collateArguments(this, newArgs, PREFIX_PROPERTY);

        return new OrderState(soFar);
    }

    private void performGuardChecks(ArgumentMultimap newArgs, Optional<String> property)
            throws StateTransitionException {
        disallowDuplicatePrefix(newArgs);
        requireKeywordPresence(property, MESSAGE_MISSING_KEYWORD);
        enforceValidity(property);
    }

    private void enforceValidity(Optional<String> property) throws StateTransitionException {
        if (!isValid(property.get())) {
            throw new StateTransitionException(MESSAGE_INVALID_INPUT);
        }
    }

    /**
     * Returns true if the entered property is a valid, sortable property.
     *
     * @param property Property.
     * @return true if the property is valid.
     */
    private boolean isValid(String property) {
        return property.equalsIgnoreCase(KEYWORD_START_PROPERTY)
                || property.equalsIgnoreCase(KEYWORD_END_PROPERTY)
                || property.equalsIgnoreCase(KEYWORD_DESCRIPTION_PROPERTY)
                || property.equalsIgnoreCase(KEYWORD_PRIORITY_PROPERTY);
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
        return PREFIX_PROPERTY;
    }
}
