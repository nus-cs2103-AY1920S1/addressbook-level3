package seedu.module.logic.parser;

import static seedu.module.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.module.commons.core.Messages.MESSAGE_INVALID_SEARCH_FIELD;

import java.util.ArrayList;
import java.util.function.Predicate;

import seedu.module.logic.commands.FindCommand;
import seedu.module.logic.parser.exceptions.ParseException;
import seedu.module.model.module.Module;
import seedu.module.model.module.predicate.DescriptionContainsKeywordsPredicate;
import seedu.module.model.module.predicate.ModuleCodeContainsKeywordsPredicate;
import seedu.module.model.module.predicate.PreclusionContainsKeywordsPredicate;
import seedu.module.model.module.predicate.PrerequisiteContainsKeywordsPredicate;
import seedu.module.model.module.predicate.SemesterContainsKeywordsPredicate;
import seedu.module.model.module.predicate.TitleContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new FindCommand object.
 */
public class FindCommandParser implements Parser<FindCommand> {

    private final ArrayList<String> prefixes = new ArrayList<>() {
        {
            add("all\\");
            add("mod\\");
            add("desc\\");
            add("title\\");
            add("prereq\\");
            add("preclu\\");
            add("sem\\");
        }
    };

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns a FindCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format.
     */
    public FindCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        String[] nameKeywords = trimmedArgs.split("\\s+");
        ArrayList<ArrayList<String>> listOfLists = parseList(nameKeywords);
        ArrayList<Predicate<Module>> listOfPredicates = parseListOfLists(listOfLists);

        return new FindCommand(listOfPredicates);
    }

    /**
     * Parses the given {@code ArrayList<String>} of argument and returns a {@code ArrayList<ArrayList<Strings>>}
     * representing the command prefix and associating keywords.
     *
     * @throws ParseException if the user input does not conform the expected format.
     */
    ArrayList<ArrayList<String>> parseList(String[] arr) throws ParseException {
        ArrayList<ArrayList<String>> listOfLists = new ArrayList<>();
        ArrayList<String> currentList = new ArrayList<>();

        int counter = 1;
        for (String word : arr) {
            if (prefixes.contains(word)) {
                listOfLists.add(currentList);
                currentList = new ArrayList<>();
                currentList.add(word);
            } else {
                currentList.add(word);
            }
            if (counter == arr.length) {
                listOfLists.add(currentList);
            }
            counter++;
        }

        listOfLists.remove(0);

        if (listOfLists.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        for (ArrayList<String> list : listOfLists) {
            if (!list.get(0).equals("all\\") && list.size() <= 1) {
                throw new ParseException(
                        String.format(MESSAGE_INVALID_SEARCH_FIELD));
            }
        }

        return listOfLists;
    }

    /**
     * Parses the given {@code ArrayList<ArrayList<Strings>>} of argument and returns a
     * {@code ArrayList<Predicate<Module>>} representing the predicates for each filter.
     *
     * @throws ParseException if prefix is not defined in prefixes.
     */
    ArrayList<Predicate<Module>> parseListOfLists(ArrayList<ArrayList<String>> listOfLists) throws ParseException {
        assert !listOfLists.isEmpty() : "listOfList is empty.";

        ArrayList<Predicate<Module>> listOfPredicates = new ArrayList<>();

        for (ArrayList<String> list : listOfLists) {
            switch (list.get(0)) {
            case "all\\":
                Predicate<Module> showAll = unused -> true;
                listOfPredicates.clear();
                listOfPredicates.add(showAll);
                return listOfPredicates;
            case "mod\\":
                list.remove(0);
                listOfPredicates.add(new ModuleCodeContainsKeywordsPredicate(list));
                break;
            case "title\\":
                list.remove(0);
                listOfPredicates.add(new TitleContainsKeywordsPredicate(list));
                break;
            case "desc\\":
                list.remove(0);
                listOfPredicates.add(new DescriptionContainsKeywordsPredicate(list));
                break;
            case "prereq\\":
                list.remove(0);
                listOfPredicates.add(new PrerequisiteContainsKeywordsPredicate(list));
                break;
            case "preclu\\":
                list.remove(0);
                listOfPredicates.add(new PreclusionContainsKeywordsPredicate(list));
                break;
            case "sem\\":
                list.remove(0);
                ArrayList<Integer> listOfInt = new ArrayList<>();
                for (String semester : list) {
                    try {
                        int intSem = Integer.valueOf(semester);
                        if (intSem > 4 || intSem < 1) {
                            throw new ParseException("Please input valid semester numbers: 1 - 4");
                        }
                        listOfInt.add(intSem);
                    } catch (NumberFormatException e) {
                        throw new ParseException("Please input valid semester numbers: 1 - 4");
                    }
                }
                listOfPredicates.add(new SemesterContainsKeywordsPredicate(listOfInt));
                break;
            default:
                throw new ParseException("parseListOfList received unknown prefix.");
            }
        }

        return listOfPredicates;
    }

}
