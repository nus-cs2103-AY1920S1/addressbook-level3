package com.typee.logic.interactive.parser;

import com.typee.commons.core.Messages;
import com.typee.logic.commands.AddCommand;
import com.typee.logic.commands.CalendarCommand;
import com.typee.logic.commands.ClearCommand;
import com.typee.logic.commands.Command;
import com.typee.logic.commands.CommandResult;
import com.typee.logic.commands.CurrentCommand;
import com.typee.logic.commands.DeleteCommand;
import com.typee.logic.commands.ExitCommand;
import com.typee.logic.commands.FindCommand;
import com.typee.logic.commands.HelpCommand;
import com.typee.logic.commands.ListCommand;
import com.typee.logic.commands.PdfCommand;
import com.typee.logic.commands.RedoCommand;
import com.typee.logic.commands.SortCommand;
import com.typee.logic.commands.TabCommand;
import com.typee.logic.commands.UndoCommand;
import com.typee.logic.commands.exceptions.CommandException;
import com.typee.logic.interactive.parser.state.EndState;
import com.typee.logic.interactive.parser.state.OptionalState;
import com.typee.logic.interactive.parser.state.State;
import com.typee.logic.interactive.parser.state.addmachine.TypeState;
import com.typee.logic.interactive.parser.state.calendarstate.CalendarState;
import com.typee.logic.interactive.parser.state.clearmachine.ClearState;
import com.typee.logic.interactive.parser.state.currentmachine.CurrentState;
import com.typee.logic.interactive.parser.state.deletemachine.IndexState;
import com.typee.logic.interactive.parser.state.exceptions.PenultimateStateTransitionException;
import com.typee.logic.interactive.parser.state.exceptions.StateTransitionException;
import com.typee.logic.interactive.parser.state.exitmachine.ExitState;
import com.typee.logic.interactive.parser.state.findmachine.FindBufferState;
import com.typee.logic.interactive.parser.state.helpmachine.HelpState;
import com.typee.logic.interactive.parser.state.listmachine.ListState;
import com.typee.logic.interactive.parser.state.pdfmachine.PdfIndexState;
import com.typee.logic.interactive.parser.state.redomachine.RedoState;
import com.typee.logic.interactive.parser.state.sortmachine.PropertyState;
import com.typee.logic.interactive.parser.state.tabmachine.TabState;
import com.typee.logic.interactive.parser.state.undomachine.UndoState;
import com.typee.logic.parser.exceptions.ParseException;

/**
 * Represents an implementation of {@code InteractiveParser} that keeps track of the state machine currently in
 * execution.
 *
 * The commands that are parsable are further partitioned into dynamic and static commands. Dynamic commands can
 * be executed at any point of parsing, while static commands must be parsed to fruition before another static
 * command is initiated.
 *
 * This partition, however, is only a {@code Parser} level implementation to handle the execution of two state
 * machines concurrently.
 */
public class Parser implements InteractiveParser {

    private static final String BUFFER_TEXT = " ";
    private static final String MESSAGE_CLEAR_ARGUMENTS = "// clear";
    private static final String MESSAGE_CURRENT = "// current";
    private static final String MESSAGE_MISSING_PREFIX = "Please input only the prefix %s and its argument."
            + " You may enter additional prefixes and arguments as long as they follow the specified ordering.";
    private static final String MESSAGE_RESET = "The arguments of the previously entered command have been flushed."
            + " Please enter another command to get started!";
    private static final String MESSAGE_IDLE_STATE = "No command is being executed currently.";
    private static final String MESSAGE_BLANK = "The command entered cannot be blank!";


    /** The state of the finite state machine currently being parsed. */
    private State currentState;
    /** Backup state to handle parsing of dynamic commands. */
    private State temporaryState;
    private final InputProcessor inputProcessor;

    /**
     * Constructs an interactive parser.
     */
    public Parser() {
        this.currentState = null;
        this.temporaryState = null;
        this.inputProcessor = new InputProcessor();
    }

    /**
     * Returns true if a state machine is actively being parsed.
     *
     * @return true if a command is being parsed.
     */
    private boolean isActive() {
        return currentState != null;
    }

    //=========== Core Parser Methods ======================================================================

    @Override
    public void parseInput(String commandText) throws ParseException {

        if (isDynamicStatelessCommand(commandText)) {
            parseDynamicStatelessCommand(commandText);
            return;
        }

        if (isDynamicStatefulCommand(commandText)) {
            parseDynamicStatefulCommand(commandText);
        }

        Prefix[] arrayOfPrefixes = inputProcessor.extractPrefixes(commandText);
        boolean wasActivatedNow = activateStateMachineIfInactive(commandText);
        parseStaticCommand(commandText, wasActivatedNow, arrayOfPrefixes);
    }

    /**
     * Returns true if the command text is a dynamic command with no parameters, like "Exit" and "Help".
     *
     * @param commandText User entered text.
     * @return true if the text corresponds to a dynamic stateless command.
     */
    private boolean isDynamicStatelessCommand(String commandText) {
        return isClearArgumentsCommand(commandText)
                || isExitCommand(commandText)
                || isHelpCommand(commandText)
                || isCurrentCommand(commandText);
    }

    /**
     * Parses a dynamic, stateless command, i.e. a command with no parameters.
     * Initializes the state machine to that of the command.
     *
     * @param commandText User entered command text.
     * @throws ParseException If the command isn't a dynamic, stateless command.
     */
    private void parseDynamicStatelessCommand(String commandText) throws ParseException {
        String trimmedNormalizedText = commandText.trim().toLowerCase();
        switch (trimmedNormalizedText) {

        case MESSAGE_CLEAR_ARGUMENTS:
            resetParser();
            break;

        case MESSAGE_CURRENT:
            initializeCurrent();
            break;

        case ExitCommand.COMMAND_WORD:
            initializeExit();
            break;

        case HelpCommand.COMMAND_WORD:
            initializeHelp();
            break;

        default:
            throw new ParseException(Messages.MESSAGE_UNKNOWN_COMMAND);
        }
    }

    /**
     * Returns true if the command text is a dynamic, stateful command, i.e. a dynamic command with parameters.
     *
     * @param commandText User entered command text.
     * @return true if the text corresponds to a dynamic, stateful command.
     */
    private boolean isDynamicStatefulCommand(String commandText) {
        return isTabCommand(commandText);
        // Further extensible.
    }

    /**
     * Parses a dynamic, stateful command.
     * Initializes the corresponding state machine.
     *
     * @param commandText User entered text that represents a dynamic, stateful command.
     */
    private void parseDynamicStatefulCommand(String commandText) {
        if (isTabCommand(commandText)) {
            initializeTab();
        }
        // Room for further extensions.
    }

    /**
     * Activates the state machine to that of the command text.
     * Returns true if no state machine was in execution previously.
     *
     * @param commandText User entered command text.
     * @return true if a new state machine's parsing is initiated.
     * @throws ParseException If the command text doesn't match any command.
     */
    private boolean activateStateMachineIfInactive(String commandText) throws ParseException {
        boolean activatedNow = false;
        if (!isActive()) {
            instantiateStateMachine(commandText);
            activatedNow = true;
        }
        return activatedNow;
    }

    /**
     * Instantiates a new state machine corresponding to the user entered command text.
     * This method should be called only when no other state machine is in execution.
     *
     * @param commandText User entered input.
     * @throws ParseException If the user input doesn't match any command.
     */
    private void instantiateStateMachine(String commandText) throws ParseException {
        String commandWord = inputProcessor.getCommandWord(commandText);
        switch (commandWord) {

        case AddCommand.COMMAND_WORD:
            currentState = new TypeState(new ArgumentMultimap());
            break;

        case DeleteCommand.COMMAND_WORD:
            currentState = new IndexState(new ArgumentMultimap());
            break;

        case UndoCommand.COMMAND_WORD:
            currentState = new UndoState(new ArgumentMultimap());
            break;

        case RedoCommand.COMMAND_WORD:
            currentState = new RedoState(new ArgumentMultimap());
            break;

        case TabCommand.COMMAND_WORD:
            currentState = new TabState(new ArgumentMultimap());
            break;

        case SortCommand.COMMAND_WORD:
            currentState = new PropertyState(new ArgumentMultimap());
            break;

        case ClearCommand.COMMAND_WORD:
            currentState = new ClearState(new ArgumentMultimap());
            break;

        case PdfCommand.COMMAND_WORD:
            currentState = new PdfIndexState(new ArgumentMultimap());
            break;

        case CalendarCommand.COMMAND_WORD:
            currentState = new CalendarState(new ArgumentMultimap());
            break;

        case ListCommand.COMMAND_WORD:
            currentState = new ListState(new ArgumentMultimap());
            break;

        case FindCommand.COMMAND_WORD:
            currentState = new FindBufferState(new ArgumentMultimap());
            break;

        default:
            throw new ParseException(Messages.MESSAGE_UNKNOWN_COMMAND);
        }
    }

    /**
     * Parses a static command entered by the user.
     *
     * @param commandText User entered input.
     * @param wasActivatedNow Flag that indicates if a state machine was just instantiated.
     * @param prefixes List of prefixes contained in the user input.
     * @throws ParseException If the input is invalid.
     */
    private void parseStaticCommand(String commandText, boolean wasActivatedNow, Prefix... prefixes)
            throws ParseException {
        ArgumentMultimap argumentMultimap = ArgumentTokenizer.tokenize(addBufferTo(commandText.trim()), prefixes);
        clearCommandWordIfActivatedNow(wasActivatedNow, argumentMultimap);
        disallowCommandWordIfActivatedBefore(argumentMultimap);
        try {
            while (canTransition(argumentMultimap)) {
                currentState = currentState.transition(argumentMultimap);
            }
        } catch (PenultimateStateTransitionException e) {
            currentState = temporaryState;
            throw new ParseException(e.getMessage());
        } catch (StateTransitionException e) {
            throw new ParseException(e.getMessage());
        }
    }

    //=========== Dynamic Command Checkers =================================================================

    /**
     * Returns true if the user entered text is the current command's text.
     *
     * @param commandText User entered input.
     * @return true if the input corresponds to a {@code CurrentCommand}.
     */
    private boolean isCurrentCommand(String commandText) {
        return commandText.equalsIgnoreCase(MESSAGE_CURRENT);
    }

    /**
     * Returns true if the user entered text is the clear argument command's text.
     *
     * @param commandText User entered input.
     * @return true if the input corresponds to the clear arguments command.
     */
    private boolean isClearArgumentsCommand(String commandText) {
        return commandText.equalsIgnoreCase(MESSAGE_CLEAR_ARGUMENTS);
    }

    /**
     * Returns true if the user entered text corresponds to the {@code TabCommand}'s text.
     *
     * @param commandText User entered input.
     * @return true if the input corresponds to the {@code TabCommand}.
     */
    private boolean isTabCommand(String commandText) {
        if (commandText.isBlank()) {
            return false;
        }
        String[] tokens = commandText.split("\\s+");
        boolean startsWithTab = tokens[0].equalsIgnoreCase(TabCommand.COMMAND_WORD);
        if (tokens.length == 2) {
            boolean endsWithArgument = tokens[1].matches("b/[a-z]+");
            return startsWithTab && endsWithArgument;
        } else if (tokens.length > 2) {
            return false;
        }
        return startsWithTab;
    }

    /**
     * Returns true if the user entered command is an exit command.
     *
     * @param commandText User entered command.
     * @return true if the user input corresponds to the {@code ExitCommand}.
     */
    private boolean isExitCommand(String commandText) {
        return commandText.trim().equalsIgnoreCase(ExitCommand.COMMAND_WORD);
    }


    /**
     * Returns true if the user entered command is a {@code HelpCommand}.
     *
     * @param commandText User entered command.
     * @return true if the user input corresponds to the {@code HelpCommand}.
     */
    private boolean isHelpCommand(String commandText) {
        return commandText.trim().equalsIgnoreCase(HelpCommand.COMMAND_WORD);
    }

    //=========== Dynamic Command Handlers =================================================================

    /**
     * Initializes the {@code TabCommand}.
     */
    private void initializeTab() {
        temporaryState = currentState;
        currentState = null;
    }

    /**
     * Initializes the {@code CurrentCommand}.
     */
    private void initializeCurrent() {
        temporaryState = currentState;
        if (currentState == null) {
            currentState = new CurrentState(MESSAGE_IDLE_STATE);
        }
        currentState = new CurrentState(currentState.getStateConstraints());
    }

    /**
     * Initializes the {@code ExitCommand}.
     */
    private void initializeExit() {
        currentState = new ExitState(new ArgumentMultimap());
    }

    /**
     * Initializes the {@code HelpCommand}.
     */
    private void initializeHelp() {
        temporaryState = currentState;
        currentState = new HelpState(new ArgumentMultimap());
    }

    //=========== Miscellaneous Parser Methods =============================================================

    /**
     * Reverts the parser's state to the previous command's last parsed state.
     */
    private void revertToPreviousCommand() {
        currentState = temporaryState;
        temporaryState = null;
    }

    /**
     * Resets the current state machine.
     */
    private void resetParser() {
        this.currentState = null;
    }

    /**
     * Returns true if the current state of the finite state machine in the parser
     * can transition to the next state.
     *
     * @param argumentMultimap The arguments supplied along with their prefixes.
     * @return true if a transition is possible.
     */
    private boolean canTransition(ArgumentMultimap argumentMultimap) {
        return (!argumentMultimap.isEmpty() && !currentState.isEndState())
                || isOptionalState(argumentMultimap);
    }

    /**
     * Disallows a user entering a command word if the parser is parsing an active state machine.
     *
     * @param argumentMultimap Arguments and their prefixes.
     * @throws ParseException If the user inputs a static command midway through another static command.
     */
    private void disallowCommandWordIfActivatedBefore(ArgumentMultimap argumentMultimap) throws ParseException {
        if (!argumentMultimap.getPreamble().isBlank()) {
            throw new ParseException(String.format(MESSAGE_MISSING_PREFIX, currentState.getPrefix()));
        } else {
            argumentMultimap.clearValues(new Prefix(""));
        }
    }

    /**
     * Removes the command word from the {@code ArgumentMultimap} to allow the finite state machine to handle
     * subsequent inputs.
     *
     * @param wasActivatedNow Boolean flag to indicate if the state machine was just activated.
     * @param argumentMultimap Arguments and their prefixes.
     */
    private void clearCommandWordIfActivatedNow(boolean wasActivatedNow, ArgumentMultimap argumentMultimap) {
        if (wasActivatedNow) {
            argumentMultimap.clearValues(new Prefix(""));
        }
    }

    /**
     * Returns true if the current state of the finite state machine allows for optional inputs.
     *
     * @param argumentMultimap Arguments and their prefixes.
     * @return true if the current input is optional.
     */
    private boolean isOptionalState(ArgumentMultimap argumentMultimap) {
        if (currentState instanceof OptionalState) {
            OptionalState optionalState = (OptionalState) currentState;
            if (optionalState.canBeSkipped(argumentMultimap)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Adds a white space as prefix to the input {@code String}.
     *
     * @param string {@code String} to add a buffer to.
     * @return {@code String} with buffer added.
     */
    private String addBufferTo(String string) {
        StringBuilder stringBuilder = new StringBuilder(BUFFER_TEXT);
        stringBuilder.append(string);
        return stringBuilder.toString();
    }

    //=========== Command Handling Methods ================================================================

    @Override
    public CommandResult fetchResult() {
        if (currentState == null) {
            // This block should only be accessed when a clear command is entered.
            return new CommandResult(MESSAGE_RESET);
        }
        return new CommandResult(currentState.getStateConstraints());
    }

    @Override
    public boolean hasParsedCommand() {
        if (currentState == null) {
            return false;
        }

        return currentState.isEndState();
    }

    @Override
    public Command makeCommand() throws ParseException {
        assert currentState instanceof EndState : "Cannot build a command from a non-end state!";
        EndState endState = (EndState) currentState;
        try {
            Command command = endState.buildCommand();
            handleStateChange(command);
            return command;
        } catch (CommandException e) {
            throw new ParseException(e.getMessage());
        }
    }

    /**
     * Handles the state machine changes associated with building and executing a dynamic command.
     *
     * @param command Command to be executed.
     */
    private void handleStateChange(Command command) {
        if (isDynamicCommand(command)) {
            revertToPreviousCommand();
        } else {
            resetParser();
        }
    }

    /**
     * Returns true if the {@code Command} is a dynamic {@code Command}.
     *
     * @param command Command to check.
     * @return true if the {@code Command} is dynamic.
     */
    private boolean isDynamicCommand(Command command) {
        return command instanceof HelpCommand
                || command instanceof TabCommand
                || command instanceof CurrentCommand
                || command instanceof ExitCommand;
    }

}
