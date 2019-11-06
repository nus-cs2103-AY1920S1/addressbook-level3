package seedu.planner.logic.autocomplete;

import static seedu.planner.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.regex.Pattern;

import seedu.planner.logic.autocomplete.exceptions.CommandNotFoundException;
import seedu.planner.logic.commands.AddAccommodationCommand;
import seedu.planner.logic.commands.AddActivityCommand;
import seedu.planner.logic.commands.AddContactCommand;
import seedu.planner.logic.commands.AddDayCommand;
import seedu.planner.logic.commands.AutoScheduleCommand;
import seedu.planner.logic.commands.ClearCommand;
import seedu.planner.logic.commands.DeleteAccommodationCommand;
import seedu.planner.logic.commands.DeleteActivityCommand;
import seedu.planner.logic.commands.DeleteContactCommand;
import seedu.planner.logic.commands.DeleteDayCommand;
import seedu.planner.logic.commands.EditAccommodationCommand;
import seedu.planner.logic.commands.EditActivityCommand;
import seedu.planner.logic.commands.EditContactCommand;
import seedu.planner.logic.commands.ExitCommand;
import seedu.planner.logic.commands.HelpCommand;
import seedu.planner.logic.commands.InitCommand;
import seedu.planner.logic.commands.ListAccommodationCommand;
import seedu.planner.logic.commands.ListActivityCommand;
import seedu.planner.logic.commands.ListContactCommand;
import seedu.planner.logic.commands.OptimiseCommand;
import seedu.planner.logic.commands.RedoCommand;
import seedu.planner.logic.commands.UndoCommand;
import seedu.planner.logic.commands.ViewAccommodationCommand;
import seedu.planner.logic.commands.ViewActivityCommand;
import seedu.planner.logic.commands.ViewContactCommand;
import seedu.planner.logic.commands.ViewHelpCommand;
import seedu.planner.logic.commands.ViewInfoCommand;
import seedu.planner.logic.commands.ViewItineraryCommand;
import seedu.planner.logic.parser.Prefix;

/**
 * Makes suggestions based on the text in the command box.
 */
public class AutoCompleteSuggester {
    private CommandInformation[] allCommandInformation = new CommandInformation[] {
        AddActivityCommand.COMMAND_INFORMATION,
        AddAccommodationCommand.COMMAND_INFORMATION,
        AddContactCommand.COMMAND_INFORMATION,
        AddDayCommand.COMMAND_INFORMATION,
        EditAccommodationCommand.COMMAND_INFORMATION,
        EditActivityCommand.COMMAND_INFORMATION,
        EditContactCommand.COMMAND_INFORMATION,
        DeleteAccommodationCommand.COMMAND_INFORMATION,
        DeleteActivityCommand.COMMAND_INFORMATION,
        DeleteContactCommand.COMMAND_INFORMATION,
        DeleteDayCommand.COMMAND_INFORMATION,
        ClearCommand.COMMAND_INFORMATION,
        HelpCommand.COMMAND_INFORMATION,
        InitCommand.COMMAND_INFORMATION,
        ExitCommand.COMMAND_INFORMATION,
        AutoScheduleCommand.COMMAND_INFORMATION,
        OptimiseCommand.COMMAND_INFORMATION,
        UndoCommand.COMMAND_INFORMATION,
        RedoCommand.COMMAND_INFORMATION,
        ListAccommodationCommand.COMMAND_INFORMATION,
        ListActivityCommand.COMMAND_INFORMATION,
        ListContactCommand.COMMAND_INFORMATION,
        ViewAccommodationCommand.COMMAND_INFORMATION,
        ViewActivityCommand.COMMAND_INFORMATION,
        ViewContactCommand.COMMAND_INFORMATION,
        ViewHelpCommand.COMMAND_INFORMATION,
        ViewInfoCommand.COMMAND_INFORMATION,
        ViewItineraryCommand.COMMAND_INFORMATION
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

    /**
     * Removes the prefix from the list.
     */
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

    /**
     * Finds command information based on the command typed in the command box.
     */
    private CommandInformation findMatchingCommandInformation(String command) throws CommandNotFoundException {
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
