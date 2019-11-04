package com.typee.logic.interactive.parser;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.typee.logic.commands.AddCommand;
import com.typee.logic.commands.Command;
import com.typee.logic.commands.CommandResult;
import com.typee.logic.commands.ExitCommand;
import com.typee.logic.commands.HelpCommand;
import com.typee.logic.commands.exceptions.CommandException;
import com.typee.logic.interactive.parser.state.EndState;
import com.typee.logic.interactive.parser.state.EndStateException;
import com.typee.logic.interactive.parser.state.State;
import com.typee.logic.interactive.parser.state.StateTransitionException;
import com.typee.logic.interactive.parser.state.addmachine.TypeState;
import com.typee.logic.interactive.parser.state.exitmachine.ExitState;
import com.typee.logic.interactive.parser.state.helpmachine.HelpState;
import com.typee.logic.parser.exceptions.ParseException;

public class Parser implements InteractiveParser {

    private static final String BUFFER_TEXT = " ";
    private static final String MESSAGE_CLEAR_ALL = "// clear";
    private static final String MESSAGE_INVALID_COMMAND = "No such command exists!";
    private static final String MESSAGE_MISSING_PREFIX = "Please input only the prefix %s and its argument."
            + " You may enter additional prefixes and arguments as long as they follow the specified ordering.";
    private static final String MESSAGE_RESET = "The arguments of the previously entered command have been flushed."
            + " Please enter another command to get started!";

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
        if (isClearCommand(commandText)) {
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

        Prefix[] arrayOfPrefixes = extractPrefixes(commandText);
        initializeAndParse(commandText, arrayOfPrefixes);
    }

    private boolean isClearCommand(String commandText) {
        return commandText.equalsIgnoreCase(MESSAGE_CLEAR_ALL);
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

        default:
            throw new ParseException(MESSAGE_INVALID_COMMAND);
        }
    }

    private String getCommandWord(String commandText) throws ParseException {
        String trimmedCommandText = commandText.trim();
        List<String> commandWords = getAllCommandWords(trimmedCommandText);

        // If there is no unique command word, throw an exception.
        if (commandWords.size() != 1) {
            throw new ParseException(MESSAGE_INVALID_COMMAND);
        }

        return commandWords.get(0);
    }

    private List<String> getAllCommandWords(String trimmedCommandText) {
        Pattern pattern = Pattern.compile("^[a-zA-z]+");
        Matcher matcher = pattern.matcher(trimmedCommandText);
        List<String> commandWords = new ArrayList<>();
        while (matcher.find()) {
            commandWords.add(matcher.group());
        }
        return commandWords;
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
