package seedu.planner.ui.autocomplete;

import seedu.planner.logic.commands.AddActivityCommand;
import seedu.planner.logic.commands.util.CommandInformation;
import seedu.planner.logic.parser.Prefix;
import seedu.planner.ui.autocomplete.exceptions.CommandNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
            possibilities.addAll(getPossibleOptionalPrefixSingle(commandInformation));
            possibilities.addAll(getPossibleOptionalPrefixMultiple(commandInformation));
            return possibilities;
        }
    }

    private List<String> getPossibleRequiredPrefixSingle(CommandInformation commandInformation,
                                                         List<Prefix> prefixesPresent) {
        List<String> possibilities = new ArrayList<>();
        List<String> listOfPossiblePrefixWithDescription = commandInformation
                .getRequiredPrefixWithDescriptionSingleTime();
        for (Prefix p : prefixesPresent) {

        }

    }

    private Optional<Integer> findMatchingPrefix(List<String> listOfPrefixWithDescription, Prefix p) {
        for (int i = 0; i < listOfPrefixWithDescription.size(); i++) {
            
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
