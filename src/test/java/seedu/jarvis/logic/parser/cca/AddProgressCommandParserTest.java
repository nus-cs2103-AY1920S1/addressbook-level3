package seedu.jarvis.logic.parser.cca;

import static seedu.jarvis.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.jarvis.logic.commands.cca.CcaCommandTestUtil.PROGRESS_DESC_TIGER;
import static seedu.jarvis.logic.commands.cca.CcaCommandTestUtil.TYPE_DESC_CANOEING;
import static seedu.jarvis.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.jarvis.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.jarvis.model.cca.ccaprogress.CcaMilestone.MESSAGE_CONSTRAINTS;
import static seedu.jarvis.testutil.TypicalIndexes.INDEX_FIRST_CCA;

import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.jarvis.logic.commands.cca.AddProgressCommand;
import seedu.jarvis.model.cca.ccaprogress.CcaMilestone;
import seedu.jarvis.model.cca.ccaprogress.CcaMilestoneList;
import seedu.jarvis.testutil.cca.TypicalCcaMilestones;

public class AddProgressCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddProgressCommand.MESSAGE_USAGE);


    private AddProgressCommandParser parser = new AddProgressCommandParser();

    @Test
    public void parse_validInputs_success() {
        CcaMilestoneList ccaMilestoneList = new CcaMilestoneList();
        ccaMilestoneList.setMilestones(List.of(TypicalCcaMilestones.TIGER));
        assertParseSuccess(parser, "1" + PROGRESS_DESC_TIGER,
                new AddProgressCommand(INDEX_FIRST_CCA, ccaMilestoneList));

        //whitespace after index
        assertParseSuccess(parser, "1 " + PROGRESS_DESC_TIGER,
                new AddProgressCommand(INDEX_FIRST_CCA, ccaMilestoneList));

        //whitespace before index
        assertParseSuccess(parser, " 1" + PROGRESS_DESC_TIGER,
                new AddProgressCommand(INDEX_FIRST_CCA, ccaMilestoneList));

    }

    @Test
    public void parse_validAdditionalTagsInputs_success() {
        CcaMilestoneList ccaMilestoneList = new CcaMilestoneList();
        CcaMilestone ccaMilestone = new CcaMilestone("Tiger t/sport");
        ccaMilestoneList.setMilestones(List.of(ccaMilestone));

        // different tags can be a valid input since there no restrictions on progress names.
        assertParseSuccess(parser, "1" + PROGRESS_DESC_TIGER + TYPE_DESC_CANOEING ,
                new AddProgressCommand(INDEX_FIRST_CCA, ccaMilestoneList));
    }

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, PROGRESS_DESC_TIGER, MESSAGE_INVALID_FORMAT);

        // no progress fields specified
        assertParseFailure(parser, "1", MESSAGE_INVALID_FORMAT);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidInputs_failure() {
        // blank progress name
        assertParseFailure(parser, "1 p/ ", MESSAGE_CONSTRAINTS);
    }
}
