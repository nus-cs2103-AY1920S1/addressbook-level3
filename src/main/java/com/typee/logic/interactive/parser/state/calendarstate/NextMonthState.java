package com.typee.logic.interactive.parser.state.calendarstate;

import com.typee.logic.commands.CalendarNextMonthCommand;
import com.typee.logic.commands.Command;
import com.typee.logic.commands.exceptions.CommandException;
import com.typee.logic.interactive.parser.ArgumentMultimap;
import com.typee.logic.interactive.parser.Prefix;
import com.typee.logic.interactive.parser.state.EndState;
import com.typee.logic.interactive.parser.state.State;
import com.typee.logic.interactive.parser.state.exceptions.StateTransitionException;

/**
 * Represents the end state(accepting state) of the state machine that builds the next month command.
 */
public class NextMonthState extends EndState {

    private static final String MESSAGE_CONSTRAINTS = "Next month displayed.";

    protected NextMonthState(ArgumentMultimap soFar) {
        super(soFar);
    }

    @Override
    public Command buildCommand() throws CommandException {
        return new CalendarNextMonthCommand();
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
