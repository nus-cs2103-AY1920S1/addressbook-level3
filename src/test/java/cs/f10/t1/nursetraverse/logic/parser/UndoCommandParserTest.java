package cs.f10.t1.nursetraverse.logic.parser;

import org.junit.jupiter.api.Test;

import cs.f10.t1.nursetraverse.commons.core.index.Index;
import cs.f10.t1.nursetraverse.logic.commands.UndoCommand;

public class UndoCommandParserTest {

    private UndoCommandParser parser = new UndoCommandParser();

    @Test
    public void parse_emptyArg_returnsUndoCommand() {
        UndoCommand expectedUndoCommand = new UndoCommand();
        CommandParserTestUtil.assertParseSuccess(parser, "", expectedUndoCommand);
    }

    @Test
    public void parse_withValidArgs_returnsUndoCommand() {
        UndoCommand expectedUndoCommand = new UndoCommand(Index.fromOneBased(1));
        CommandParserTestUtil.assertParseSuccess(parser, "1", expectedUndoCommand);
    }
}
