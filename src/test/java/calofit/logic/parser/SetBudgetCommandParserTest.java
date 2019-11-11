package calofit.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import calofit.logic.commands.SetBudgetCommand;
import calofit.logic.parser.exceptions.ParseException;
import calofit.model.dish.Calorie;

public class SetBudgetCommandParserTest {

    private static final int VALID_CALORIE = 724;
    private static final int INVALID_CALORIE = -360;
    private static final String NONNUMERIC_INPUT = "ABC";
    private final SetBudgetCommandParser parser = new SetBudgetCommandParser();

    @Test
    public void testSuccessfulParse() throws ParseException {
        SetBudgetCommand expected = new SetBudgetCommand(new Calorie(VALID_CALORIE));

        SetBudgetCommand parseResult = parser.parse(Integer.toString(VALID_CALORIE));
        assertEquals(expected, parseResult);
    }

    @Test
    public void testNegativeCalories() {
        assertThrows(ParseException.class, () -> parser.parse(Integer.toString(INVALID_CALORIE)),
            SetBudgetCommandParser.MISSING_CALORIE_FIELD);
    }

    @Test
    public void testNonnumericInput() {
        assertThrows(ParseException.class, () -> parser.parse(NONNUMERIC_INPUT),
            Calorie.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void testMissingInput() {
        assertThrows(ParseException.class, () -> parser.parse(""),
            SetBudgetCommandParser.MISSING_CALORIE_FIELD);
    }
}
