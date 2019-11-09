package dukecooks.logic.parser.diary;

import org.junit.jupiter.api.Test;

import dukecooks.commons.core.Messages;
import dukecooks.logic.commands.CommandTestUtil;
import dukecooks.logic.commands.diary.CreatePageCommand;
import dukecooks.logic.parser.CommandParserTestUtil;
import dukecooks.model.diary.components.Diary;
import dukecooks.model.diary.components.DiaryName;
import dukecooks.model.diary.components.Page;
import dukecooks.model.diary.components.Title;
import dukecooks.testutil.diary.DiaryBuilder;
import dukecooks.testutil.diary.PageBuilder;

public class CreatePageCommandParserTest {
    private CreatePageCommandParser parser = new CreatePageCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Page expectedPage = new PageBuilder().build();
        Diary expectedDiary = new DiaryBuilder().build();

        // whitespace only preamble
        CommandParserTestUtil.assertParseSuccess(parser, CommandTestUtil.PREAMBLE_WHITESPACE
                        + CommandTestUtil.DIARY_NAME_INPUT
                        + " t/ " + PageBuilder.DEFAULT_TITLE
                        + " tp/ " + PageBuilder.DEFAULT_PAGE_TYPE
                        + " desc/ " + PageBuilder.DEFAULT_DESCRIPTION
                        + " i/ " + PageBuilder.DEFAULT_IMAGE,
                new CreatePageCommand(expectedPage, expectedDiary.getDiaryName()));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT,
                CreatePageCommand.MESSAGE_USAGE);

        // missing name prefix
        CommandParserTestUtil.assertParseFailure(parser, CommandTestUtil.VALID_DIARY_NAME,
                expectedMessage);

        // missing title prefix
        CommandParserTestUtil.assertParseFailure(parser, CommandTestUtil.VALID_PHO_TITLE,
                expectedMessage);

        // missing type prefix
        CommandParserTestUtil.assertParseFailure(parser, CommandTestUtil.VALID_PHO_TYPE,
                expectedMessage);

        // missing description prefix
        CommandParserTestUtil.assertParseFailure(parser, CommandTestUtil.VALID_PHO_DESCRIPTION,
                expectedMessage);

        // missing image prefix
        CommandParserTestUtil.assertParseFailure(parser, CommandTestUtil.VALID_PHO_IMAGE,
                expectedMessage);

        // all prefixes missing
        CommandParserTestUtil.assertParseFailure(parser, CommandTestUtil.VALID_NAME_BURGER,
                expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {

        // invalid diary name
        CommandParserTestUtil.assertParseFailure(parser, CommandTestUtil.INVALID_DIARY_NAME
                + CommandTestUtil.TITLE_SUSHI
                + CommandTestUtil.PAGE_TYPE_SUSHI + CommandTestUtil.PAGE_DESCRIPTION_SUSHI
                + CommandTestUtil.PAGE_IMAGE_SUSHI, DiaryName.MESSAGE_CONSTRAINTS);

        // invalid title
        CommandParserTestUtil.assertParseFailure(parser, CommandTestUtil.DIARY_NAME_INPUT
                + CommandTestUtil.INVALID_PAGE_TITLE
                + CommandTestUtil.PAGE_TYPE_SUSHI + CommandTestUtil.PAGE_DESCRIPTION_SUSHI
                + CommandTestUtil.PAGE_IMAGE_SUSHI, Title.MESSAGE_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        CommandParserTestUtil.assertParseFailure(parser, CommandTestUtil.INVALID_DIARY_NAME
                + CommandTestUtil.INVALID_PAGE_TITLE
                + CommandTestUtil.PAGE_TYPE_SUSHI + CommandTestUtil.PAGE_DESCRIPTION_SUSHI
                + CommandTestUtil.PAGE_IMAGE_SUSHI, DiaryName.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        CommandParserTestUtil.assertParseFailure(parser, CommandTestUtil.PREAMBLE_NON_EMPTY
                + CommandTestUtil.DIARY_NAME_INPUT + CommandTestUtil.TITLE_SUSHI
                + CommandTestUtil.PAGE_TYPE_SUSHI + CommandTestUtil.PAGE_DESCRIPTION_SUSHI
                + CommandTestUtil.PAGE_IMAGE_SUSHI,
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, CreatePageCommand.MESSAGE_USAGE));
    }
}
