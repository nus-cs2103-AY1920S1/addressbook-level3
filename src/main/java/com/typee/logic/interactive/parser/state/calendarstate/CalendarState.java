package com.typee.logic.interactive.parser.state.calendarstate;

import static com.typee.logic.interactive.parser.CliSyntax.PREFIX_CALENDAR;
import static java.util.Objects.requireNonNull;

import java.util.Optional;

import com.typee.logic.commands.CalendarDateDisplayEngagementsCommand;
import com.typee.logic.commands.CalendarNextMonthCommand;
import com.typee.logic.commands.CalendarPreviousMonthCommand;
import com.typee.logic.interactive.parser.ArgumentMultimap;
import com.typee.logic.interactive.parser.Prefix;
import com.typee.logic.interactive.parser.state.PenultimateState;
import com.typee.logic.interactive.parser.state.State;
import com.typee.logic.interactive.parser.state.StateTransitionException;

public class CalendarState extends PenultimateState {

    private static final String MESSAGE_CONSTRAINTS = "What would you like to do with the calendar? Please enter"
            + " the command prefixed by \"c/\". Allowed actions are"
            + " \"nextmonth\", \"previousmonth\" and \"opendisplay\".";
    private static final String MESSAGE_MISSING_KEYWORD = "Invalid input! Please enter a valid action after \"c/\"."
            + " Allowed actions are \"nextmonth\", \"previousmonth\" and \"opendisplay\".";
    private static final String MESSAGE_INVALID_INPUT = "Invalid input!"
            + " Allowed actions are \"nextmonth\", \"previousmonth\" and \"opendisplay\".";

    public CalendarState(ArgumentMultimap soFar) {
        super(soFar);
    }

    @Override
    public State transition(ArgumentMultimap newArgs) throws StateTransitionException {
        requireNonNull(newArgs);

        Optional<String> operation = newArgs.getValue(PREFIX_CALENDAR);
        performGuardChecks(newArgs, operation);
        collateArguments(this, newArgs, PREFIX_CALENDAR);

        return nextState(soFar, newArgs);
    }

    private void performGuardChecks(ArgumentMultimap newArgs, Optional<String> operation)
            throws StateTransitionException {
        disallowDuplicatePrefix(newArgs);
        requireKeywordPresence(operation, MESSAGE_MISSING_KEYWORD);
        enforceValidity(operation);
    }

    private void enforceValidity(Optional<String> operation) throws StateTransitionException {
        String operationString = operation.get();
        if (!isValid(operationString)) {
            throw new StateTransitionException(MESSAGE_INVALID_INPUT);
        }
    }

    private boolean isValid(String operationString) {
        return operationString.equalsIgnoreCase(CalendarNextMonthCommand.COMMAND_WORD)
                || operationString.equalsIgnoreCase(CalendarPreviousMonthCommand.COMMAND_WORD)
                || operationString.equalsIgnoreCase(CalendarDateDisplayEngagementsCommand.COMMAND_WORD);
    }

    private State nextState(ArgumentMultimap soFar, ArgumentMultimap newArgs) throws StateTransitionException {
        String operation = soFar.getValue(PREFIX_CALENDAR).get();
        String operationLowerCase = operation.toLowerCase();

        switch (operationLowerCase) {

        case CalendarNextMonthCommand.COMMAND_WORD:
            enforceNoExcessiveArguments(newArgs);
            return new NextMonthState(soFar);

        case CalendarPreviousMonthCommand.COMMAND_WORD:
            enforceNoExcessiveArguments(newArgs);
            return new PreviousMonthState(soFar);

        case CalendarDateDisplayEngagementsCommand.COMMAND_WORD:
            return new OpenDisplayState(soFar);

        default:
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
        return PREFIX_CALENDAR;
    }
}
