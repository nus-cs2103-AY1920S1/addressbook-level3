package seedu.savenus.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.savenus.logic.commands.DislikeCommand;
import seedu.savenus.logic.commands.LikeCommand;
import seedu.savenus.logic.commands.PreferenceCommand;
import seedu.savenus.logic.parser.exceptions.ParseException;
import seedu.savenus.model.food.Category;
import seedu.savenus.model.food.Location;
import seedu.savenus.model.tag.Tag;

public class PreferenceCommandParserTest {
    private PreferenceCommandParser parser;
    private Set<Category> testCategory;
    private Set<Tag> testTag;
    private Set<Location> testLocation;

    @BeforeEach
    public void set_up() {
        parser = new PreferenceCommandParser();
        testCategory = new HashSet<>();
        testTag = new HashSet<>();
        testLocation = new HashSet<>();
    }

    @Test
    public void execute_normalParse_error() {
        AssertionError err = new AssertionError("This method should not be called.");;
        assertThrows(AssertionError.class, () -> parser.parse("Test String"));
    }

    @Test
    public void execute_missingPrefixParse_error() {
        String wrongString = "No Prefixes";
        String emptyString = "               ";
        assertThrows(ParseException.class, () -> parser.parse(wrongString, true));
        assertThrows(ParseException.class, () -> parser.parse(wrongString, false));
        assertThrows(ParseException.class, () -> parser.parse(emptyString, false));
    }

    @Test
    public void create_proper_commands() {
        String userInput = " c/Chinese";
        testCategory.add(new Category("Chinese"));
        PreferenceCommand expectedLikeCommand = new LikeCommand(testCategory, testTag, testLocation);
        PreferenceCommand expectedDislikeCommand = new DislikeCommand(testCategory, testTag, testLocation);
        try {
            PreferenceCommand command = parser.parse(userInput, true);
            assertEquals(expectedLikeCommand, command);
        } catch (Exception pe) {
            System.out.println(pe);
            throw new IllegalArgumentException("Invalid userInput.", pe);
        }

        try {
            PreferenceCommand command = parser.parse(userInput, false);
            assertEquals(expectedDislikeCommand, command);
        } catch (Exception pe) {
            System.out.println(pe);
            throw new IllegalArgumentException("Invalid userInput.", pe);
        }
    }
}
