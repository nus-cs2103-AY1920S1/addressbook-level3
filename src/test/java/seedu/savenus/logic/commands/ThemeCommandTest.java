package seedu.savenus.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import seedu.savenus.logic.parser.ThemeCommandParser;
import seedu.savenus.model.Model;
import seedu.savenus.model.ModelManager;

//@@author robytanama
/**
 * Contains tests for {@code ThemeCommand}.
 */
public class ThemeCommandTest {

    private static Model model = new ModelManager();
    private static Model expectedModel = new ModelManager();
    private static ThemeCommandParser themeCommandParser = new ThemeCommandParser();

    private static final String VALID_THEME_DARK = "dark";
    private static final String VALID_THEME_LIGHT = "light";
    private static final String VALID_THEME_DARK_CAPITAL = "DARK";
}
