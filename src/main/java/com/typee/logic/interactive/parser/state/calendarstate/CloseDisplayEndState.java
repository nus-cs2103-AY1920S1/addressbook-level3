package com.typee.logic.interactive.parser.state.calendarstate;

import static com.typee.logic.interactive.parser.CliSyntax.PREFIX_DATE;

import java.time.DateTimeException;
import java.time.LocalDate;

import com.typee.logic.commands.CalendarCloseDisplayCommand;
import com.typee.logic.commands.Command;
import com.typee.logic.commands.exceptions.CommandException;
import com.typee.logic.interactive.parser.ArgumentMultimap;
import com.typee.logic.interactive.parser.InteractiveParserUtil;
import com.typee.logic.interactive.parser.Prefix;
import com.typee.logic.interactive.parser.state.EndState;
import com.typee.logic.interactive.parser.state.State;
import com.typee.logic.interactive.parser.state.exceptions.StateTransitionException;

/**
 * Represents the end state (accepting state) of the state machine that builds the close display command.
 */
public class CloseDisplayEndState extends EndState {

    private static final String MESSAGE_CONSTRAINTS = "Displayed engagements on the entered date.";
    private static final String DATE_PATTERN = "dd/MM/uuuu";
    private static final String MESSAGE_INVALID_INPUT = "Invalid command! Please enter a valid date.";

    protected CloseDisplayEndState(ArgumentMultimap soFar) {
        super(soFar);
    }

    @Override
    public Command buildCommand() throws CommandException {
        String dateString = soFar.getValue(PREFIX_DATE).get();
        LocalDate date = fetchDate(dateString);
        return new CalendarCloseDisplayCommand(date);
    }

    /**
     * Makes and returns a date from the entered {@code String}.
     *
     * @param dateString String that represents a date.
     * @return {@code LocalDate}.
     * @throws CommandException If the date is invalid.
     */
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
        return null;
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
