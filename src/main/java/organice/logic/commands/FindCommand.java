package organice.logic.commands;

import static java.util.Objects.requireNonNull;
import static organice.commons.util.StringUtil.calculateLevenshteinDistance;
import static organice.logic.parser.CliSyntax.PREFIX_NAME;
import static organice.logic.parser.CliSyntax.PREFIX_NRIC;
import static organice.logic.parser.CliSyntax.PREFIX_PHONE;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.BiFunction;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.transformation.FilteredList;

import organice.commons.core.LogsCenter;
import organice.logic.parser.ArgumentMultimap;
import organice.model.Model;
import organice.model.person.Name;
import organice.model.person.Person;
import organice.model.person.PersonContainsPrefixesPredicate;

/**
 * Finds and lists all persons in address book whose prefixes match any of the argument prefix-keyword pairs.
 * Performs fuzzy searching based on Levenshtein Distance.
 * Keyword matching is case insensitive.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all persons whose prefixes are similar to any of"
            + " the specified prefix-keywords pairs (case-insensitive) and displays them as a list with index numbers."
            + "\nList of Prefixes: n/, ic/, p/, a/, t/, pr/, b/, d/, tt/, exp/, o/"
            + "\nParameters: PREFIX/KEYWORD [MORE_PREFIX-KEYWORD_PAIRS]..."
            + "\nExample: " + COMMAND_WORD + " n/alice t/doctor";

    // Maximum Levenshtein Distance tolerated for a fuzzy match
    private static final int FUZZY_THRESHOLD = 5;
    private static final Logger logger = LogsCenter.getLogger(FindCommand.class);

    private final ArgumentMultimap argMultimap;


    public FindCommand(ArgumentMultimap argMultimap) {
        this.argMultimap = argMultimap;
    }

    /**
     * Returns a copy of the {@code inputList} which is filtered according to maximum tolerable Levenshtein Distance
     * (edit distance) then sorted according to ascending level of distance.
     */
    private List<Person> fuzzyMatch(ArgumentMultimap argMultimap, List<Person> inputList) {
        // Fuzzy Match by Levenshtein Distance is not implemented for following prefixes:
        // Type, Age, Priority, BloodType, TissueType
        // Typos in these fields have a very small Levenshtein Distance (LD) as typical field length is very small

        List<String> nameKeywords = argMultimap.getAllValues(PREFIX_NAME);
        List<String> nricKeywords = argMultimap.getAllValues(PREFIX_NRIC);
        List<String> phoneKeywords = argMultimap.getAllValues(PREFIX_PHONE);

        // List containing combined Levenshtein Distance of persons in inputList
        ArrayList<Integer> distanceList = new ArrayList<>();

        for (int i = 0; i < inputList.size(); i++) {
            int combinedLevenshteinDistance = 0;
            Person currentPerson = inputList.get(i);

            // If all 3 fuzzy search parameters are empty, set all non-exact matches to be beyond threshold
            // i.e. none of them will appear
            if (nameKeywords.isEmpty() && nricKeywords.isEmpty() && phoneKeywords.isEmpty()) {
                combinedLevenshteinDistance = FUZZY_THRESHOLD + 1;
            } else {
                combinedLevenshteinDistance += nameKeywords.isEmpty() ? 0
                        : findMinLevenshteinDistance(nameKeywords, currentPerson.getName());
                combinedLevenshteinDistance += nricKeywords.isEmpty() ? 0
                        : findMinLevenshteinDistance(nricKeywords, currentPerson.getNric().toString());
                combinedLevenshteinDistance += phoneKeywords.isEmpty() ? 0
                        : findMinLevenshteinDistance(phoneKeywords, currentPerson.getPhone().toString());
            }
            distanceList.add(i, combinedLevenshteinDistance);
        }

        // Keep Persons whose Levenshtein Distance is within tolerable range
        ArrayList<Integer> distancesOfTolerablePersons = new ArrayList<>();
        ArrayList<Person> tolerablePersons = new ArrayList<>(inputList.size());
        for (int i = 0; i < inputList.size(); i++) {
            int levenshteinDistance = distanceList.get(i);
            if (levenshteinDistance <= FUZZY_THRESHOLD) {
                distancesOfTolerablePersons.add(levenshteinDistance);
                tolerablePersons.add(inputList.get(i));
            }
        }

        ArrayList<Person> sortedPersons = new ArrayList<>(tolerablePersons);
        sortedPersons.sort(Comparator.comparingInt(left ->
                distancesOfTolerablePersons.get(tolerablePersons.indexOf(left))));

        return sortedPersons;
    }

    private int findMinLevenshteinDistance(List<String> prefixKeywords, String personAttribute) {
        return prefixKeywords.stream().reduce(Integer.MAX_VALUE, (minDistance, nextKeyword) -> Integer.min(
                minDistance, calculateLevenshteinDistance(nextKeyword.toLowerCase(), personAttribute.toLowerCase())),
                Integer::min);
    }

    /**
     * Returns the minimum Levenshtein Distance of every {@code prefixKeyword} and {@code personName} pair. If
     * {@code prefixKeyword} is a single-word string, {@code personName} is split according to spaces (if possible) and
     * the minimum distance is calculated between every possible pair.
     */
    private int findMinLevenshteinDistance(List<String> prefixKeywords, Name personName) {
        // If keyword is one word long, split the personName and find minLD.
        // Else just do as we are normally doing in original finalMinLD

        BiFunction<String, String, Integer> findDistanceSplitIfMultiWord = (prefixKeyword, pName) ->
                prefixKeyword.split(" ").length == 1 ? Arrays.stream(pName.split(" "))
                        .reduce(Integer.MAX_VALUE, (minDistance, nextNameWord) -> Integer.min(minDistance,
                                calculateLevenshteinDistance(prefixKeyword.toLowerCase(), nextNameWord.toLowerCase())),
                                Integer::min)
                : calculateLevenshteinDistance(prefixKeyword.toLowerCase(), pName.toLowerCase());

        return prefixKeywords.stream().reduce(Integer.MAX_VALUE, (minDistance, nextKeyword) ->
                Integer.min(minDistance, findDistanceSplitIfMultiWord.apply(nextKeyword, personName.toString())),
                Integer::min);
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        logger.info("Executing FindCommand");
        List<Person> allPersons = Arrays.asList(model.getFullPersonList().toArray(Person[]::new));

        FilteredList<Person> exactMatches = new FilteredList<>(FXCollections.observableList(allPersons));
        exactMatches.setPredicate(new PersonContainsPrefixesPredicate(argMultimap));

        List<Person> allExceptExactMatches = new ArrayList<>(allPersons);
        allExceptExactMatches.removeAll(Arrays.asList(exactMatches.toArray(Person[]::new)));
        allExceptExactMatches = fuzzyMatch(argMultimap, allExceptExactMatches);

        ArrayList<Person> finalArrayList = new ArrayList<>(exactMatches);
        finalArrayList.addAll(allExceptExactMatches);

        model.setDisplayedPersonList(finalArrayList);
        return new CommandResult("Found " + exactMatches.size() + " exact matches and "
                + allExceptExactMatches.size() + " possible matches!");
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindCommand // instanceof handles nulls
                && argMultimap.equals(((FindCommand) other).argMultimap)); // state check
    }
}
