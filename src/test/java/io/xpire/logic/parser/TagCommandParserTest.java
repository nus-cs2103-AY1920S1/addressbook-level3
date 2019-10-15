package io.xpire.logic.parser;

import static io.xpire.logic.CommandParserItemUtil.VALID_TAG_FRUIT;
import static io.xpire.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static io.xpire.testutil.TypicalIndexes.INDEX_FIRST_ITEM;

import org.junit.jupiter.api.Test;

import io.xpire.logic.commands.TagCommand;

public class TagCommandParserTest {

    private TagCommandParser parser = new TagCommandParser();

    @Test
    public void parse_validArgs_returnTagCommand() {
        assertParseSuccess(parser, "1|#Fruit", new TagCommand(INDEX_FIRST_ITEM, new String[]{VALID_TAG_FRUIT}));
    }
}

