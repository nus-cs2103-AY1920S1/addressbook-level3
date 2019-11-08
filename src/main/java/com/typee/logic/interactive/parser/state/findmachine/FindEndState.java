package com.typee.logic.interactive.parser.state.findmachine;

import static com.typee.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static com.typee.logic.parser.CliSyntax.PREFIX_ATTENDEES;
import static com.typee.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static com.typee.logic.parser.CliSyntax.PREFIX_LOCATION;
import static com.typee.logic.parser.CliSyntax.PREFIX_PRIORITY;

import com.typee.logic.commands.Command;
import com.typee.logic.commands.FindCommand;
import com.typee.logic.commands.exceptions.CommandException;
import com.typee.logic.interactive.parser.ArgumentMultimap;
import com.typee.logic.interactive.parser.InteractiveParserUtil;
import com.typee.logic.interactive.parser.Prefix;
import com.typee.logic.interactive.parser.state.EndState;
import com.typee.logic.interactive.parser.state.State;
import com.typee.logic.interactive.parser.state.exceptions.StateTransitionException;
import com.typee.model.engagement.AttendeeList;
import com.typee.model.engagement.EngagementPredicate;

/**
 * Represents the end state in the state machine that builds the {@code FindCommand}.
 */
public class FindEndState extends EndState {

    private static final String MESSAGE_EMPTY_COMMAND = "Invalid input. The find command cannot be empty!";
    private static final String MESSAGE_CONSTRAINTS = "Displaying filtered engagement list.";

    protected FindEndState(ArgumentMultimap soFar) {
        super(soFar);
    }

    @Override
    public Command buildCommand() throws CommandException {
        if (isEmptyCommand()) {
            throw new CommandException(MESSAGE_EMPTY_COMMAND);
        }
        EngagementPredicate engagementPredicate = makeEngagementPredicate();
        return new FindCommand(engagementPredicate);
    }

    private boolean isEmptyCommand() {
        return soFar.isEmpty();
    }

    /**
     * Builds the {@code EngagementPredicate} from the entered arguments.
     *
     * @return {@code EngagementPredicate}.
     * @throws CommandException If the predicate is invalid.
     */
    private EngagementPredicate makeEngagementPredicate() throws CommandException {
        // Adapted from Jun Hao's FindCommandParser.

        EngagementPredicate engagementPredicate = new EngagementPredicate();

        setPredicateLocation(engagementPredicate);
        setPredicatePriority(engagementPredicate);
        setPredicateAttendees(engagementPredicate);
        setPredicateDescription(engagementPredicate);
        checkPredicateValidity(engagementPredicate);

        return engagementPredicate;
    }

    /**
     * Ensures that the {@code EngagementPredicate} is valid.
     * @param engagementPredicate {@code EngagementPredicate}.
     * @throws CommandException If the predicate is invalid.
     */
    private void checkPredicateValidity(EngagementPredicate engagementPredicate) throws CommandException {
        if (!engagementPredicate.isValid()) {
            throw new CommandException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }
    }

    private void setPredicateDescription(EngagementPredicate engagementPredicate) {
        if (soFar.getValue(PREFIX_DESCRIPTION).isPresent()) {
            engagementPredicate.setDescription(
                    InteractiveParserUtil.parseDescription(soFar.getValue(PREFIX_DESCRIPTION).get()));
        }
    }

    private void setPredicateAttendees(EngagementPredicate engagementPredicate) {
        if (soFar.getValue(PREFIX_ATTENDEES).isPresent()) {
            AttendeeList attendeeList = InteractiveParserUtil.parseAttendees(soFar.getValue(PREFIX_ATTENDEES).get());
            String attendeeName = attendeeList.getAttendees().get(0).getName().toString();
            engagementPredicate.setAttendees(attendeeName);
        }
    }

    private void setPredicatePriority(EngagementPredicate engagementPredicate) {
        if (soFar.getValue(PREFIX_PRIORITY).isPresent()) {
            engagementPredicate.setPriority(
                    InteractiveParserUtil.parsePriority(soFar.getValue(PREFIX_PRIORITY).get()).toString());
        }
    }

    private void setPredicateLocation(EngagementPredicate engagementPredicate) {
        if (soFar.getValue(PREFIX_LOCATION).isPresent()) {
            engagementPredicate.setLocation(
                    InteractiveParserUtil.parseLocation(soFar.getValue(PREFIX_LOCATION).get()).toString());
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
}
