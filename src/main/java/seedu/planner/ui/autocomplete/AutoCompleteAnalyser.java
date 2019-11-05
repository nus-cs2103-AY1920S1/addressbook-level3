package seedu.planner.ui.autocomplete;

import seedu.planner.logic.commands.AddActivityCommand;
import seedu.planner.logic.commands.util.CommandInformation;
import seedu.planner.logic.parser.Prefix;
import seedu.planner.ui.autocomplete.exceptions.CommandNotFoundException;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class AutoCompleteAnalyser {
    private CommandInformation[] allCommandInformation = new CommandInformation[] {
            AddActivityCommand.COMMAND_INFORMATION
    };

    private List<String> allCommandWords;

    public List<String> getPossibilities(String command, boolean preamblePresent, List<Prefix> prefixesPresent) {
        if (command == null) {
            return getAllCommandWord();
        } else {
            List<String> possibilities = new ArrayList<>();
            CommandInformation commandInformation;
            try {
                commandInformation = findMatchingCommandInformation(command);
            } catch (CommandNotFoundException e) {
                return new ArrayList<>();
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
        String stringPrefix = prefix.toString();
        if (stringPrefix.length() >= stringToTest.length()) {
            return false;
        } else if (stringToTest.substring(0, stringPrefix.length()).equals(stringPrefix)) {
            return true;
        } else {
            return false;
        }
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

    private List<String> getAllCommandWord() {
        if (!(allCommandWords == null)) {
            return allCommandWords;
        } else {
            List<String> possibilities = new ArrayList<>();
            for (CommandInformation commandInformation : allCommandInformation) {
                possibilities.add(commandInformation.getCommand());
            }
            allCommandWords = possibilities;
            return allCommandWords;
        }
    }
}
