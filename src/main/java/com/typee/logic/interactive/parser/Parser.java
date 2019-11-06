package com.typee.logic.interactive.parser;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
import com.typee.logic.interactive.parser.state.EndStateException;
import com.typee.logic.interactive.parser.state.State;
import com.typee.logic.interactive.parser.state.StateTransitionException;
import com.typee.logic.interactive.parser.state.addmachine.TypeState;
import com.typee.logic.interactive.parser.state.calendarstate.CalendarState;
import com.typee.logic.interactive.parser.state.clearmachine.ClearState;
import com.typee.logic.interactive.parser.state.currentmachine.CurrentState;
import com.typee.logic.interactive.parser.state.deletemachine.IndexState;
import com.typee.logic.interactive.parser.state.exitmachine.ExitState;
import com.typee.logic.interactive.parser.state.findmachine.FindDescriptionState;
import com.typee.logic.interactive.parser.state.helpmachine.HelpState;
import com.typee.logic.interactive.parser.state.listmachine.ListState;
import com.typee.logic.interactive.parser.state.pdfmachine.PdfIndexState;
import com.typee.logic.interactive.parser.state.redomachine.RedoState;
import com.typee.logic.interactive.parser.state.sortmachine.PropertyState;
import com.typee.logic.interactive.parser.state.tabmachine.TabState;
import com.typee.logic.interactive.parser.state.undomachine.UndoState;
import com.typee.logic.parser.exceptions.ParseException;

public class Parser implements InteractiveParser {

    private static final String BUFFER_TEXT = " ";
    private static final String MESSAGE_CLEAR_ARGUMENTS = "// clear";
    private static final String MESSAGE_CURRENT = "// current";
    private static final String MESSAGE_MISSING_PREFIX = "Please input only the prefix %s and its argument."
            + " You may enter additional prefixes and arguments as long as they follow the specified ordering.";
    private static final String MESSAGE_RESET = "The arguments of the previously entered command have been flushed."
            + " Please enter another command to get started!";
    private static final String MESSAGE_IDLE_STATE = "No command is being executed currently.";
    public static final String REGEX_PATTERN_COMMAND_WORD = "[a-zA-Z]+";
    private static final String REGEX_PATTERN_PREFIX = "[a-z]/";
    private static final String MESSAGE_BLANK = "The command entered cannot be blank!";


    private State currentState;
    private State temporaryState;

    public Parser() {
        this.currentState = null;
        this.temporaryState = null;
    }

    private boolean isActive() {
        return currentState != null;
    }

    @Override
    public void parseInput(String commandText) throws ParseException {

        /*
        if (commandText.isBlank()) {
            throw new ParseException(MESSAGE_BLANK);
        }
         */

        if (isClearArgumentsCommand(commandText)) {
            resetParser();
            return;
        }

        if (isExitCommand(commandText)) {
            initializeExit();
            return;
        }

        if (isHelpCommand(commandText)) {
            initializeHelp();
            return;
        }

        if (isCurrentCommand(commandText)) {
            initializeCurrent();
            return;
        }

        if (isTabCommand(commandText)) {
            initializeTab();
            //return;
        }

        Prefix[] arrayOfPrefixes = extractPrefixes(commandText);
        initializeAndParse(commandText, arrayOfPrefixes);
    }

    private void initializeTab() {
        temporaryState = currentState;
        currentState = null;
    }

    private boolean isTabCommand(String commandText) {
        if (commandText.isBlank()) {
            return false;
        }
        String[] tokens = commandText.split("\\s+");
        boolean startsWithTab = tokens[0].equalsIgnoreCase(TabCommand.COMMAND_WORD);
        if (tokens.length == 2) {
            boolean endsWithArgument = tokens[1].matches("b/[a-z]+");
            return startsWithTab && endsWithArgument;
        } else if (tokens.length > 2){
            return false;
        }
        return startsWithTab;
    }

    private void initializeCurrent() {
        temporaryState = currentState;
        if (currentState == null) {
            currentState = new CurrentState(MESSAGE_IDLE_STATE);
        }
        currentState = new CurrentState(currentState.getStateConstraints());
    }

    private boolean isCurrentCommand(String commandText) {
        return commandText.equalsIgnoreCase(MESSAGE_CURRENT);
    }

    private boolean isClearArgumentsCommand(String commandText) {
        return commandText.equalsIgnoreCase(MESSAGE_CLEAR_ARGUMENTS);
    }

    private void initializeAndParse(String commandText, Prefix... prefixes) throws ParseException {
        boolean wasActivatedNow = activateIfInactive(commandText);
        parse(commandText, wasActivatedNow, prefixes);
    }

    private boolean activateIfInactive(String commandText) throws ParseException {
        boolean activatedNow = false;
        if (!isActive()) {
            instantiateStateMachine(commandText);
            activatedNow = true;
        }
        return activatedNow;
    }

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
            if (command instanceof HelpCommand) {
                currentState = temporaryState;
                temporaryState = null;
            } else if (command instanceof CurrentCommand) {
                currentState = temporaryState;
                temporaryState = null;
            } else if (command instanceof TabCommand) {
                currentState = temporaryState;
                temporaryState = null;
            } else {
                resetParser();
            }
            return command;
        } catch (CommandException e) {
            throw new ParseException(e.getMessage());
        }
    }

    private void resetParser() {
        this.currentState = null;
    }

    private Prefix[] extractPrefixes(String commandText) {
        Pattern pattern = Pattern.compile("[a-zA-z]/");
        Matcher matcher = pattern.matcher(commandText);
        List<Prefix> prefixes = getMatches(matcher);

        // Convert to an array to allow the values to be processed by varargs.
        return prefixes.toArray(Prefix[]::new);
    }

    private List<Prefix> getMatches(Matcher matcher) {
        List<Prefix> prefixes = new ArrayList<>();
        while (matcher.find()) {
            prefixes.add(new Prefix(matcher.group()));
        }
        return prefixes;
    }

    private void parse(String commandText, boolean wasActivatedNow, Prefix... prefixes)
            throws ParseException {
        ArgumentMultimap argumentMultimap = ArgumentTokenizer.tokenize(addBufferTo(commandText.trim()), prefixes);
        if (wasActivatedNow) {
            argumentMultimap.clearValues(new Prefix(""));
        }
        if (!argumentMultimap.getPreamble().isBlank()) {
            throw new ParseException(String.format(MESSAGE_MISSING_PREFIX, currentState.getPrefix()));
        } else {
            argumentMultimap.clearValues(new Prefix(""));
        }
        try {
            while (!argumentMultimap.isEmpty() && !currentState.isEndState()) {
                currentState = currentState.transition(argumentMultimap);
            }
        } catch (EndStateException e) {
            // Ignore since this implies that excessive arguments were supplied.
        } catch (StateTransitionException e) {
            throw new ParseException(e.getMessage());
        }
    }

    private void instantiateStateMachine(String commandText) throws ParseException {
        String commandWord = getCommandWord(commandText);
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
            currentState = new FindDescriptionState(new ArgumentMultimap());
            break;

        default:
            throw new ParseException(Messages.MESSAGE_UNKNOWN_COMMAND);
        }
    }

    private String getCommandWord(String commandText) throws ParseException {
        String trimmedCommandText = commandText.trim();
        List<String> commandWords = getAllCommandWords(trimmedCommandText);

        // If there is no unique command word, throw an exception.
        if (commandWords.size() != 1) {
            throw new ParseException(Messages.MESSAGE_UNKNOWN_COMMAND);
        }

        return commandWords.get(0);
    }

    private List<String> getAllCommandWords(String trimmedCommandText) {
        int boundary = findBoundary(trimmedCommandText);
        Pattern pattern = Pattern.compile(REGEX_PATTERN_COMMAND_WORD);
        Matcher matcher = pattern.matcher(trimmedCommandText.substring(0, boundary));
        List<String> commandWords = new ArrayList<>();
        while (matcher.find()) {
            commandWords.add(matcher.group());
        }
        return commandWords;
    }

    private int findBoundary(String trimmedCommandText) {
        Pattern pattern = Pattern.compile(REGEX_PATTERN_PREFIX);
        Matcher matcher = pattern.matcher(trimmedCommandText);
        boolean prefixExists = matcher.find();
        if (prefixExists) {
            return matcher.start();
        }
        return trimmedCommandText.length();
    }

    private String addBufferTo(String string) {
        StringBuilder stringBuilder = new StringBuilder(BUFFER_TEXT);
        stringBuilder.append(string);
        return stringBuilder.toString();
    }

    private boolean isExitCommand(String commandText) {
        return commandText.trim().equalsIgnoreCase(ExitCommand.COMMAND_WORD);
    }

    private void initializeExit() {
        currentState = new ExitState(new ArgumentMultimap());
    }

    private boolean isHelpCommand(String commandText) {
        return commandText.trim().equalsIgnoreCase(HelpCommand.COMMAND_WORD);
    }

    private void initializeHelp() {
        temporaryState = currentState;
        currentState = new HelpState(new ArgumentMultimap());
    }

}
