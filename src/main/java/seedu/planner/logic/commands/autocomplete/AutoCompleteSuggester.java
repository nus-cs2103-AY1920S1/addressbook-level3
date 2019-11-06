package seedu.planner.logic.commands.autocomplete;

import seedu.planner.logic.commands.AddActivityCommand;
import seedu.planner.logic.parser.Prefix;
import seedu.planner.logic.commands.autocomplete.exceptions.CommandNotFoundException;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.regex.Pattern;

import static seedu.planner.commons.util.CollectionUtil.requireAllNonNull;

public class AutoCompleteSuggester {
    private CommandInformation[] allCommandInformation = new CommandInformation[] {
            AddActivityCommand.COMMAND_INFORMATION
    };

    private List<String> allCommandWords;

    public AutoCompleteSuggester() {
        this.allCommandWords = getAllCommandWord();
    }

    public List<String> getPossibilities(String command, boolean preamblePresent, List<Prefix> prefixesPresent) {
        requireAllNonNull(command, preamblePresent, preamblePresent);
        List<String> possibilities = new ArrayList<>();
        CommandInformation commandInformation;
        try {
            commandInformation = findMatchingCommandInformation(command);
        } catch (CommandNotFoundException e) {
            return closestMatchingCommand(command);
        }

        if (!preamblePresent && commandInformation.thereIsPreamble()) {
            possibilities.add(commandInformation.getPreamble().get());
            return possibilities;
        }
        possibilities.addAll(getPossibleRequiredPrefixSingle(commandInformation, prefixesPresent));
        possibilities.addAll(getPossibleRequiredPrefixMultiple(commandInformation));
        possibilities.addAll(getPossibleOptionalPrefixSingle(commandInformation, prefixesPresent));
        possibilities.addAll(getPossibleOptionalPrefixMultiple(commandInformation));
        return possibilities;
    }



    private List<String> getPossibleOptionalPrefixMultiple(CommandInformation commandInformation) {
        return commandInformation.getOptionalPrefixWithDescriptionMultipleTime();
    }

    private List<String> getPossibleOptionalPrefixSingle(CommandInformation commandInformation,
                                                         List<Prefix> prefixesPresent) {
        List<String> listOfPossiblePrefixWithDescription = commandInformation
                .getOptionalPrefixWithDescriptionSingleTime();
        // remove all possible prefix that are already inside prefixesPresent
        for (Prefix p : prefixesPresent) {
            listOfPossiblePrefixWithDescription = removeMatchingPrefixFromList(
                    listOfPossiblePrefixWithDescription, p
            );
        }
        return listOfPossiblePrefixWithDescription;
    }

    private List<String> getPossibleRequiredPrefixMultiple(CommandInformation commandInformation) {
        return commandInformation.getRequiredPrefixWithDescriptionMultipleTime();
    }

    private List<String> getPossibleRequiredPrefixSingle(CommandInformation commandInformation,
                                                         List<Prefix> prefixesPresent) {
        List<String> listOfPossiblePrefixWithDescription = commandInformation
                .getRequiredPrefixWithDescriptionSingleTime();
        // remove all possible prefix that are already inside prefixesPresent
        for (Prefix p : prefixesPresent) {
            listOfPossiblePrefixWithDescription = removeMatchingPrefixFromList(
                    listOfPossiblePrefixWithDescription, p
            );
        }
        return listOfPossiblePrefixWithDescription;
    }

    private List<String> removeMatchingPrefixFromList(List<String> ls, Prefix prefix) {
        List<String> copiedList = new ArrayList<>(ls);
        Iterator<String> copiedListIterator = copiedList.iterator();
        while (copiedListIterator.hasNext()) {
            String curr = copiedListIterator.next();
            if (checkIfPrefixMatchesString(curr, prefix)) {
                copiedListIterator.remove();
            }
        }
        return copiedList;
    }

    private boolean checkIfPrefixMatchesString(String stringToTest, Prefix prefix) {
        return Pattern.compile("\\b" + prefix.toString()).matcher(stringToTest).find();
    }

    private CommandInformation findMatchingCommandInformation(String command) {
        CommandInformation matchingCommandInformation = null;
        for (CommandInformation commandInformation : allCommandInformation) {
            if (commandInformation.getCommand().equals(command)) {
                matchingCommandInformation = commandInformation;
                break;
            }
        }
        if (matchingCommandInformation == null) {
            throw new CommandNotFoundException();
        }
        return matchingCommandInformation;
    }

    private List<String> closestMatchingCommand(String command) {
        SortedSet<String> matchingCommands = new TreeSet<>(allCommandWords)
                .subSet(command, command + Character.MAX_VALUE);
        return new ArrayList<>(matchingCommands);
    }

    private List<String> getAllCommandWord() {
        List<String> commandWords = new ArrayList<>();
        for (CommandInformation commandInformation : allCommandInformation) {
            commandWords.add(commandInformation.getCommand());
        }
        return commandWords;
    }
}
