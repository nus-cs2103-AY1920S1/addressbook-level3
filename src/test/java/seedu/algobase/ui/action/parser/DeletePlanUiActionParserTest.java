package seedu.algobase.ui.action.parser;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class DeletePlanUiActionParserTest {
    @Test
    public void parse_nullUiAction_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new DeletePlanUiActionParser().parse(null));
    }

}
