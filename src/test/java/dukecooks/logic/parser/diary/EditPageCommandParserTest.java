package dukecooks.logic.parser.diary;

import org.junit.jupiter.api.Test;

import dukecooks.commons.core.Messages;
import dukecooks.commons.core.index.Index;
import dukecooks.logic.commands.CommandTestUtil;
import dukecooks.logic.commands.diary.EditPageCommand;
import dukecooks.logic.commands.diary.EditPageCommand.EditPageDescriptor;
import dukecooks.logic.parser.CommandParserTestUtil;
import dukecooks.model.diary.components.Diary;
import dukecooks.model.diary.components.DiaryName;
import dukecooks.testutil.diary.EditPageDescriptorBuilder;

public class EditPageCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, EditPageCommand.MESSAGE_USAGE);

    private EditPageCommandParser parser = new EditPageCommandParser();

    @Test
    public void parse_missingParts_failure() {

        // no diary name specified
        CommandParserTestUtil.assertParseFailure(parser, CommandTestUtil.VALID_NAME_AMY, MESSAGE_INVALID_FORMAT);

        // no field specified
        CommandParserTestUtil.assertParseFailure(parser, "1", MESSAGE_INVALID_FORMAT);

        // no index and no field specified
        CommandParserTestUtil.assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {

        // negative index
        CommandParserTestUtil.assertParseFailure(parser, "-5" + CommandTestUtil.NAME_DESC_AMY,
                MESSAGE_INVALID_FORMAT);

        // zero index
        CommandParserTestUtil.assertParseFailure(parser, "0" + CommandTestUtil.NAME_DESC_AMY,
                MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        CommandParserTestUtil.assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        CommandParserTestUtil.assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        CommandParserTestUtil.assertParseFailure(parser, "1" + CommandTestUtil.INVALID_NAME_DESC,
                DiaryName.MESSAGE_CONSTRAINTS); // invalid diary name
    }

    @Test
    public void parse_noFieldEdited_failure() {
        String userInput = "1 n/ Asian Food";
        CommandParserTestUtil.assertParseFailure(parser, userInput, EditPageCommand.MESSAGE_NOT_EDITED);

    }

    @Test
    public void parse_allFieldsSpecified_success() {

        Diary validDiary = new Diary(new DiaryName("Asian Food"));

        Index targetIndex = Index.fromOneBased(1);
        String userInput = targetIndex.getOneBased() + " n/ Asian Food" + CommandTestUtil.TITLE_PHO;

        EditPageDescriptor descriptor = new EditPageDescriptorBuilder()
                .withTitle(CommandTestUtil.VALID_PHO_TITLE).build();
        EditPageCommand expectedCommand = new EditPageCommand(targetIndex, validDiary.getDiaryName(), descriptor);

        CommandParserTestUtil.assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_oneFieldSpecified_success() {

        Diary validDiary = new Diary(new DiaryName("Asian Food"));

        // Title
        Index targetIndex = Index.fromOneBased(1);
        String userInput = targetIndex.getOneBased() + " n/ Asian Food" + CommandTestUtil.TITLE_PHO;
        EditPageDescriptor descriptor = new EditPageDescriptorBuilder()
                .withTitle(CommandTestUtil.VALID_PHO_TITLE).build();
        EditPageCommand expectedCommand = new EditPageCommand(targetIndex, validDiary.getDiaryName(), descriptor);
        CommandParserTestUtil.assertParseSuccess(parser, userInput, expectedCommand);

        // PageType
        Index secondIndex = Index.fromOneBased(1);
        String secondUserInput = targetIndex.getOneBased() + " n/ Asian Food" + CommandTestUtil.PAGE_TYPE_SUSHI;
        EditPageDescriptor secondDescriptor = new EditPageDescriptorBuilder()
                .withPageType(CommandTestUtil.VALID_SUSHI_TYPE).build();
        EditPageCommand secondExpectedCommand = new EditPageCommand(secondIndex,
                validDiary.getDiaryName(), secondDescriptor);
        CommandParserTestUtil.assertParseSuccess(parser, secondUserInput, secondExpectedCommand);

        // PageDescription
        Index thirdIndex = Index.fromOneBased(1);
        String thirdUserInput = targetIndex.getOneBased() + " n/ Asian Food" + CommandTestUtil.PAGE_DESCRIPTION_SUSHI;
        EditPageDescriptor thirdDescriptor = new EditPageDescriptorBuilder()
                .withPageDescription(CommandTestUtil.VALID_SUSHI_DESCRIPTION).build();
        EditPageCommand thirdExpectedCommand = new EditPageCommand(thirdIndex,
                validDiary.getDiaryName(), thirdDescriptor);
        CommandParserTestUtil.assertParseSuccess(parser, thirdUserInput, thirdExpectedCommand);

        //  Image
        Index fourthIndex = Index.fromOneBased(1);
        String fourthUserInput = targetIndex.getOneBased() + " n/ Asian Food" + CommandTestUtil.PAGE_IMAGE_SUSHI;
        EditPageDescriptor fourthDescriptor = new EditPageDescriptorBuilder()
                .withImage(CommandTestUtil.VALID_SUSHI_IMAGE).build();
        EditPageCommand fourthExpectedCommand = new EditPageCommand(fourthIndex,
                validDiary.getDiaryName(), fourthDescriptor);
        CommandParserTestUtil.assertParseSuccess(parser, fourthUserInput, fourthExpectedCommand);
    }
}
