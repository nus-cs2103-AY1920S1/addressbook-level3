package seedu.sugarmummy.logic.parser.aesthetics;

import static seedu.sugarmummy.commons.core.Messages.MESSAGE_BACKGROUND_COLOUR_NO_ARGS_REQUIREMENT;
import static seedu.sugarmummy.commons.core.Messages.MESSAGE_USE_ONLY_ONE_FONT_COLOUR_PREFIX;
import static seedu.sugarmummy.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.sugarmummy.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.sugarmummy.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.sugarmummy.logic.commands.aesthetics.BackgroundCommand;
import seedu.sugarmummy.logic.commands.aesthetics.FontColourCommand;
import seedu.sugarmummy.model.aesthetics.Background;
import seedu.sugarmummy.model.aesthetics.Colour;

class BackgroundCommandParserTest {

    private BackgroundCommandParser parser = new BackgroundCommandParser();

    @Test
    public void parse_nullArguments() {
        assertThrows(RuntimeException.class, (new NullPointerException()).getMessage(), () -> parser.parse(null));
    }

    @Test
    public void parse_noArguments() {
        assertParseSuccess(parser, "", new BackgroundCommand());
    }

    @Test
    public void parse_multipleFontColourArgs() {
        assertParseFailure(parser, "black fontcolour/yellow fontcolour/white",
                MESSAGE_USE_ONLY_ONE_FONT_COLOUR_PREFIX);
    }

    @Test
    public void parseSuccess_withoutFontColourArgs() {
        Background background = new Background("black");
        background.setBgSize("");
        background.setBgRepeat("");
        assertParseSuccess(parser, "black", new BackgroundCommand(background));
    }

    @Test
    public void parseSuccess_fontColourArgs() {
        Background background = new Background("black");
        background.setBgSize("");
        background.setBgRepeat("");

        assertParseSuccess(parser, "black fontcolour/yellow", new BackgroundCommand(background,
                new FontColourCommand(new Colour("yellow"))));
    }

    @Test
    public void parse_withoutFontColourArgs_colourWithImageArgs() {
        assertParseFailure(parser, "black s/cover", String
                .format(MESSAGE_BACKGROUND_COLOUR_NO_ARGS_REQUIREMENT,
                        BackgroundCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_fontColourArgs_colourWithImageArgs() {
        assertParseFailure(parser, "black fontcolour/yellow s/cover", String
                .format(MESSAGE_BACKGROUND_COLOUR_NO_ARGS_REQUIREMENT,
                        BackgroundCommand.MESSAGE_USAGE));
    }
}
