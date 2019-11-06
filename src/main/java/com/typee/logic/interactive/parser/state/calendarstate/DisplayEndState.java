package com.typee.logic.interactive.parser.state.calendarstate;

import static com.typee.logic.interactive.parser.CliSyntax.PREFIX_DATE;

import java.time.DateTimeException;
import java.time.LocalDate;

import com.typee.logic.commands.CalendarDateDisplayEngagementsCommand;
import com.typee.logic.commands.Command;
import com.typee.logic.commands.exceptions.CommandException;
import com.typee.logic.interactive.parser.ArgumentMultimap;
import com.typee.logic.interactive.parser.InteractiveParser;
import com.typee.logic.interactive.parser.InteractiveParserUtil;
import com.typee.logic.interactive.parser.Prefix;
import com.typee.logic.interactive.parser.state.EndState;
import com.typee.logic.interactive.parser.state.State;
import com.typee.logic.interactive.parser.state.StateTransitionException;

public class DisplayEndState extends EndState {

    private static final String MESSAGE_CONSTRAINTS = "Displayed engagements on the entered date.";
    private static final String DATE_PATTERN = "dd/MM/uuuu";
    private static final String MESSAGE_INVALID_INPUT = "Invalid command! Please enter a valid date.";

    protected DisplayEndState(ArgumentMultimap soFar) {
        super(soFar);
    }

    @Override
    public Command buildCommand() throws CommandException {
        String dateString = soFar.getValue(PREFIX_DATE).get();
        LocalDate date = fetchDate(dateString);
        return new CalendarDateDisplayEngagementsCommand(date);
    }

    private LocalDate fetchDate(String dateString) throws CommandException {
        try {
            LocalDate date = InteractiveParserUtil.parseLocalDate(dateString, DATE_PATTERN);
            return date;
        } catch (DateTimeException e) {
            throw new CommandException(MESSAGE_INVALID_INPUT);
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
