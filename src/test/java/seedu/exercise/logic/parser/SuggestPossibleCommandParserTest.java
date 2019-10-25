package seedu.exercise.logic.parser;

import static seedu.exercise.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.exercise.logic.commands.SuggestPossibleCommand;
import seedu.exercise.model.property.Muscle;

public class SuggestPossibleCommandParserTest {
    private SuggestCommandParser parser = new SuggestCommandParser();

    @Test
    public void parse_optionalFieldsMissing_success() {
        Set<Muscle> targetMuscles = new HashSet<>();
        Map<String, String> targetCustomPropertiesMap = new HashMap<>();

        //no muscles and no custom properties
        assertParseSuccess(parser, " s/possible",
            new SuggestPossibleCommand(targetMuscles, targetCustomPropertiesMap));

        //no custom properties
        assertParseSuccess(parser, " s/possible m/Chest m/Triceps",
            new SuggestPossibleCommand(targetMuscles, targetCustomPropertiesMap));
    }

}
