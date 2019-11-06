package com.typee.logic.interactive.parser.state.sortmachine;

import static com.typee.logic.interactive.parser.CliSyntax.PREFIX_PROPERTY;
import static java.util.Objects.requireNonNull;

import java.util.Optional;

import com.typee.logic.interactive.parser.ArgumentMultimap;
import com.typee.logic.interactive.parser.Prefix;
import com.typee.logic.interactive.parser.state.State;
import com.typee.logic.interactive.parser.state.StateTransitionException;

public class PropertyState extends State {

    private static final String MESSAGE_CONSTRAINTS = "Sort command initiated. Please enter the property you would"
            + " like to sort by, prefixed by \"py/\". The sortable properties are start date, end date, description"
            + " and priority, to be specified as \"start\", \"end\", \"description\" and \"priority\" respectively.";
    private static final String MESSAGE_MISSING_KEYWORD = "Invalid input! Please enter a valid property after \"py\".";
    private static final String MESSAGE_INVALID_INPUT = "Invalid input! Accepted properties are"
            + " \"start\", \"end\", \"description\" and \"priority\".";
    public static final String KEYWORD_START_PROPERTY = "start";
    public static final String KEYWORD_END_PROPERTY = "end";
    public static final String KEYWORD_DESCRIPTION_PROPERTY = "description";
    public static final String KEYWORD_PRIORITY_PROPERTY = "priority";

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
