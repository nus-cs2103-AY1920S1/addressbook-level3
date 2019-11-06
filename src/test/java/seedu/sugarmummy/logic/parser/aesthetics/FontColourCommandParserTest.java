package seedu.sugarmummy.logic.parser.aesthetics;

import static seedu.sugarmummy.commons.core.Messages.MESSAGE_USE_ONLY_ONE_BACKGROUND_PREFIX;
import static seedu.sugarmummy.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.sugarmummy.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.sugarmummy.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.sugarmummy.logic.commands.aesthetics.BackgroundCommand;
import seedu.sugarmummy.logic.commands.aesthetics.FontColourCommand;
import seedu.sugarmummy.model.aesthetics.Background;
import seedu.sugarmummy.model.aesthetics.Colour;

class FontColourCommandParserTest {

    private FontColourCommandParser parser = new FontColourCommandParser();

    @Test
    public void parse_nullArguments() {
        assertThrows(RuntimeException.class, (new NullPointerException()).getMessage(), () -> parser.parse(null));
    }

    @Test
    public void parse_noArguments() {
        assertParseSuccess(parser, "", new FontColourCommand());
    }

    @Test
    public void parse_multipleBackgroundArgs() {
        assertParseFailure(parser, "yellow bg/black bg/darkblue",
                MESSAGE_USE_ONLY_ONE_BACKGROUND_PREFIX);
    }

    @Test
    public void parseSuccess_withoutBackgroundArgs() {
        assertParseSuccess(parser, "yellow", new FontColourCommand(new Colour("yellow")));
    }

    @Test
    public void parseSuccess_fontColourArgs() {
        Background background = new Background("black");
        background.setBgSize("");
        background.setBgRepeat("");

        assertParseSuccess(parser, "yellow bg/black", new FontColourCommand(new Colour("yellow"),
                new BackgroundCommand(background)));
    }
}
