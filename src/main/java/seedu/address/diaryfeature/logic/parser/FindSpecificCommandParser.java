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
 * Parses input arguments and creates a new FindSpecificCommand object
 */
public class FindSpecificCommandParser {
    private static final String FIND_SPECIFIC_USAGE = "In particular, input your findSpecific command like this: \n" +
            "findSpecific PREFIX/TARGET | EG: findSpecific t/birthday. " +
            "\n Note that the input cant be empty, and has " +
            "to be at least 1 character \n" +
            "The parameters that you can use for PREFIX are t for title, d for date, p for place and m for memory";


    /**
     * Parses the given {@code String} of arguments in the context of the FindSpecificCommand
     * and returns an FindSpecificCommand object for execution.
     *
     * @throws EmptyArgumentException if the user input does not conform the expected format
     */
    public Command parse(String args) throws EmptyArgumentException {
        try {
            ArgumentMultimap multimap = ArgumentTokenizer.tokenize(args, PREFIX_TITLE, PREFIX_DATE, PREFIX_PLACE, PREFIX_MEMORY);
            return new FindSpecificCommand(new FindSpecificPredicate(getPresentValue(multimap)));
        } catch (EmptyArgumentException error) {
            throw new EmptyArgumentException(FindSpecificCommand.COMMAND_WORD, FIND_SPECIFIC_USAGE);
        }
    }

    /**
     * Get the value (from the prefixes) which is present
     *
     * @return a String[] of the component and the corresponding value
     */
    private String[] getPresentValue(ArgumentMultimap multimap) throws EmptyArgumentException {
        Optional<String> title = multimap.getValue(PREFIX_TITLE);
        Optional<String> date = multimap.getValue(PREFIX_DATE);
        Optional<String> place = multimap.getValue(PREFIX_PLACE);
        Optional<String> memory = multimap.getValue(PREFIX_MEMORY);
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
        } else {
            throw new EmptyArgumentException(FindSpecificCommand.COMMAND_WORD, FIND_SPECIFIC_USAGE);
        }
        return myAnswer;
    }

    /**
     * Set the corresponding value of the component
     *
     * @param input   String[] to add values
     * @param myValue Optional value to check
     */
    private void setValues(String[] input, Optional<String> myValue) throws EmptyArgumentException {
        input[1] = ParserUtil.parseStringArgs(myValue.get(), FindSpecificCommand.COMMAND_WORD);

    }
}


















