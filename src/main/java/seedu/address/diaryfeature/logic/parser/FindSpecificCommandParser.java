package seedu.address.diaryfeature.logic.parser;

import static seedu.address.diaryfeature.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.diaryfeature.logic.parser.CliSyntax.PREFIX_MEMORY;
import static seedu.address.diaryfeature.logic.parser.CliSyntax.PREFIX_PLACE;
import static seedu.address.diaryfeature.logic.parser.CliSyntax.PREFIX_TITLE;


import java.util.Optional;

import seedu.address.diaryfeature.logic.commands.FindSpecificCommand;
import seedu.address.diaryfeature.logic.parser.exceptions.EmptyArgumentException;
import seedu.address.diaryfeature.logic.predicates.FindSpecificPredicate;
import seedu.address.logic.commands.Command;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;


/**
 * Parses input arguments and creates a new AddCommand object
*/

public class FindSpecificCommandParser {
    public static final String FIND_SPECIFIC_USAGE = "In particular, input your findSpecific command like this: \n" +
            "findSpecific prefix/target Eg: findSpecific t/birthday. \n Note that the input cant be empty, and has " +
            "to be at least 1 character";
    ArgumentMultimap argMultimap;

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     *
     */

    public Command parse(String args) throws EmptyArgumentException {
        try {
            String trimmed = ParserUtil.parseStringArgs(args, FindSpecificCommand.COMMAND_WORD);
            argMultimap = ArgumentTokenizer.tokenize(trimmed, PREFIX_TITLE, PREFIX_DATE, PREFIX_PLACE, PREFIX_MEMORY);
            return new FindSpecificCommand(new FindSpecificPredicate(getPresentValue()));
        } catch (EmptyArgumentException error) {
            throw new EmptyArgumentException(FindSpecificCommand.COMMAND_WORD, FIND_SPECIFIC_USAGE);
        }
    }


    private String[] getPresentValue() {
        Optional<String> title = argMultimap.getValue(PREFIX_TITLE);
        Optional<String> date = argMultimap.getValue(PREFIX_DATE);
        Optional<String> place = argMultimap.getValue(PREFIX_PLACE);
        Optional<String> memory = argMultimap.getValue(PREFIX_MEMORY);

        String[] myAnswer = new String[2];
        if (title.isPresent()) {
            myAnswer[0] = "title";
            setValues(myAnswer, title);
        } else if (date.isPresent()) {
            myAnswer[0] = "date";
            setValues(myAnswer, date);
        } else if (place.isPresent()) {
            myAnswer[0] = "place";
            setValues(myAnswer, place);
        } else if (memory.isPresent()) {
            myAnswer[0] = "memory";
            setValues(myAnswer, memory);
        }
        return myAnswer;
    }


    private void setValues(String[] input, Optional<String> myValue) {
        input[1] = myValue.get();
    }
}


















