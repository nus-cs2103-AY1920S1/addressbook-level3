package com.typee.logic.interactive.parser.state.sortmachine;

import static com.typee.logic.interactive.parser.CliSyntax.PREFIX_ORDER;
import static com.typee.logic.interactive.parser.CliSyntax.PREFIX_PROPERTY;

import com.typee.logic.commands.Command;
import com.typee.logic.commands.SortCommand;
import com.typee.logic.commands.exceptions.CommandException;
import com.typee.logic.interactive.parser.ArgumentMultimap;
import com.typee.logic.interactive.parser.InteractiveParserUtil;
import com.typee.logic.interactive.parser.Prefix;
import com.typee.logic.interactive.parser.state.EndState;
import com.typee.logic.interactive.parser.state.State;
import com.typee.logic.interactive.parser.state.exceptions.StateTransitionException;
import com.typee.logic.parser.exceptions.ParseException;
import com.typee.model.util.EngagementComparator;

/**
 * Represents the end state of the state machine that builds the {@code SortCommand}.
 */
public class SortEndState extends EndState {

    private static final String CONSTANT_COMBINER = "_";
    private static final String MESSAGE_CONSTRAINTS = "Sorting engagements.";
    private static final String KEYWORD_START_ASCENDING = "START_TIME";
    private static final String KEYWORD_START_DESCENDING = "START_TIME_REVERSE";
    private static final String KEYWORD_END_ASCENDING = "END_TIME";
    private static final String KEYWORD_END_DESCENDING = "END_TIME_REVERSE";
    private static final String KEYWORD_DESCRIPTION_ASCENDING = "ALPHABETICAL";
    private static final String KEYWORD_DESCRIPTION_DESCENDING = "ALPHABETICAL_REVERSE";
    private static final String KEYWORD_PRIORITY_ASCENDING = "PRIORITY";
    private static final String KEYWORD_PRIORITY_DESCENDING = "PRIORITY_REVERSE";
    private static final String MESSAGE_INVALID_INPUT = "Invalid sort order!";

    protected SortEndState(ArgumentMultimap soFar) {
        super(soFar);
    }

    @Override
    public Command buildCommand() throws CommandException {
        EngagementComparator engagementComparator = makeEngagementComparator(soFar);
        SortCommand sortCommand = new SortCommand(engagementComparator);
        return sortCommand;
    }

    /**
     * Builds an {@code EngagementComparator} from the entered arguments.
     *
     * @param soFar Processed arguments.
     * @return {@code EngagementComparator}.
     * @throws CommandException If the arguments are invalid.
     */
    private EngagementComparator makeEngagementComparator(ArgumentMultimap soFar) throws CommandException {
        String property = soFar.getValue(PREFIX_PROPERTY).get();
        String order = soFar.getValue(PREFIX_ORDER).get();
        EngagementComparator engagementComparator = getComparator(property, order);
        return engagementComparator;
    }

    private EngagementComparator getComparator(String property, String order) throws CommandException {
        try {
            String sortOrder = combineAndFormat(property, order);
            String normalizedSortOrder = normalize(sortOrder);
            EngagementComparator comparator = InteractiveParserUtil.parseComparator(normalizedSortOrder);
            return comparator;
        } catch (ParseException pe) {
            throw new CommandException(MESSAGE_INVALID_INPUT);
        }
    }

    /**
     * Combines and formats the entered property and order into a valid sort order.
     *
     * @param property Property.
     * @param order Order.
     * @return formatted string.
     */
    private String combineAndFormat(String property, String order) {
        return property.toUpperCase() + CONSTANT_COMBINER + order.toUpperCase();
    }

    /**
     * Returns a {@code String} representation of a sort order made from the entered, formatted input.
     *
     * @param sortOrder Formatted {@code String}.
     * @return {@code String} representing sort order.
     */
    private String normalize(String sortOrder) {
        Order order = Order.valueOf(sortOrder);
        switch (order) {

        case START_ASCENDING:
            return KEYWORD_START_ASCENDING;

        case START_DESCENDING:
            return KEYWORD_START_DESCENDING;

        case END_ASCENDING:
            return KEYWORD_END_ASCENDING;

        case END_DESCENDING:
            return KEYWORD_END_DESCENDING;

        case DESCRIPTION_ASCENDING:
            return KEYWORD_DESCRIPTION_ASCENDING;

        case DESCRIPTION_DESCENDING:
            return KEYWORD_DESCRIPTION_DESCENDING;

        case PRIORITY_ASCENDING:
            return KEYWORD_PRIORITY_ASCENDING;

        case PRIORITY_DESCENDING:
            return KEYWORD_PRIORITY_DESCENDING;

        default:
            throw new IllegalArgumentException();
        }
    }

    @Override
    public State transition(ArgumentMultimap newArgs) throws StateTransitionException {
        throw new StateTransitionException(MESSAGE_END_STATE);
    }

    @Override
    public String getStateConstraints() {
        return MESSAGE_CONSTRAINTS;
    }

    @Override
    public boolean isEndState() {
        return true;
    }

    @Override
    public Prefix getPrefix() {
        return DUMMY_PREFIX;
    }

    /**
     * Represents a collection of the allowed sort orders.
     */
    private enum Order {
        START_ASCENDING, START_DESCENDING, END_ASCENDING, END_DESCENDING, DESCRIPTION_ASCENDING, DESCRIPTION_DESCENDING,
        PRIORITY_ASCENDING, PRIORITY_DESCENDING
    }
}
