package seedu.savenus.storage.alias;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.savenus.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.savenus.commons.exceptions.IllegalValueException;
import seedu.savenus.logic.commands.SortCommand;
import seedu.savenus.model.alias.AliasPair;

public class JsonSerializableAliasPairTest {
    @Test
    public void noCommandWord_error() throws Exception {
        JsonAdaptedAliasPair pair = new JsonAdaptedAliasPair(null, "xdxd");
        assertThrows(IllegalValueException.class, () -> pair.toModelType());
    }

    @Test
    public void noAliasWord_error() throws Exception {
        JsonAdaptedAliasPair pair = new JsonAdaptedAliasPair(SortCommand.COMMAND_WORD, null);
        assertThrows(IllegalValueException.class, () -> pair.toModelType());
    }

    @Test
    public void invalidCommandWord_error() throws Exception {
        JsonAdaptedAliasPair pair = new JsonAdaptedAliasPair("hehe", "xdxd");
        assertThrows(IllegalValueException.class, () -> pair.toModelType());
    }

    @Test
    public void validPair_success() throws Exception {
        JsonAdaptedAliasPair pair = new JsonAdaptedAliasPair(SortCommand.COMMAND_WORD, "xdxd");
        assertEquals(pair.toModelType(), new AliasPair(SortCommand.COMMAND_WORD, "xdxd"));
    }
}
