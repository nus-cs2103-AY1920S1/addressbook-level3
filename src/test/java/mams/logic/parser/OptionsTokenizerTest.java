package mams.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

public class OptionsTokenizerTest {
    public static final String NO_DASH = "edamwl"; // no preceding dash
    public static final String WHITESPACE_AFTER_DASH = "-  "; // no character after dash

    private final Option option1 = new Option("option");
    private final Option option2 = new Option("13");
    private final Option option3 = new Option("farfm");
    private final Option option4 = new Option("c");

    @Test
    public void tokenize_emptyString_emptyOptionsSet() {
        String argsString = " ";
        OptionsSet optionsSet = OptionsTokenizer.tokenize(argsString);

        assertTrue(optionsSet.isEmpty());
    }

    @Test
    public void tokenize_optionsPresent_optionsSetContainsOptions() {
        // option1 present
        String argsString = option1.toString();
        OptionsSet optionsSet = OptionsTokenizer.tokenize(argsString);
        assertTrue(optionsSet.isOptionPresent(option1));

        // option2 present
        argsString = argsString + " " + option2.toString();
        optionsSet = OptionsTokenizer.tokenize(argsString);
        assertTrue(optionsSet.isOptionPresent(option2));
        assertTrue(optionsSet.areAllTheseOptionsPresent(option1, option2));
        assertFalse(optionsSet.isOptionPresent(option4));


        // option 1, 2, 3, 4 present
        argsString = argsString + " " + option3.toString() + " " + option4.toString();
        optionsSet = OptionsTokenizer.tokenize(argsString);
        assertTrue(optionsSet.areAllTheseOptionsPresent(option1, option2, option3, option4));
    }

    @Test
    public void tokenize_nonOptionsGiven_optionsSetOnlyContainsValidOptions() {
        // no preceding dash, option not processed
        String argsString = " " + NO_DASH;
        OptionsSet optionsSet = OptionsTokenizer.tokenize(argsString);
        assertTrue(optionsSet.isEmpty());


        // no non-whitespace character after dash, option not processed
        argsString = " " + WHITESPACE_AFTER_DASH;
        optionsSet = OptionsTokenizer.tokenize(argsString);
        assertTrue(optionsSet.isEmpty());
    }

    @Test
    public void getUnrecognizedArguments() {
        // option1 and option2 will be the target arguments
        // should return option3 and NO_DASH as the unrecognized arguments
        String argsString = option1.toString() + " "
                + option2.toString() + " "
                + NO_DASH + " "
                + option3.toString();

        List<String> unrecognizedArguments = new ArrayList<String>();
        unrecognizedArguments.add(option3.toString());
        unrecognizedArguments.add(NO_DASH);

        List<String> output = OptionsTokenizer.getUnrecognizedArguments(argsString,
                option1.toString(),
                option2.toString());
        Collections.sort(output);
        Collections.sort(unrecognizedArguments);
        assertEquals(output, unrecognizedArguments);
    }
}
