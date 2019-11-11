package seedu.address.logic.commands.suggestions;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.commons.util.StringUtil;
import seedu.address.logic.parser.CliSyntax;
import seedu.address.logic.parser.CommandArgument;
import seedu.address.model.ModelManager;
import seedu.address.model.group.exceptions.DuplicateGroupException;
import seedu.address.model.mapping.exceptions.DuplicateMappingException;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.testutil.grouputil.TypicalGroups;
import seedu.address.testutil.modelutil.TypicalModel;
import seedu.address.testutil.personutil.TypicalPersonDescriptor;

class SuggesterTest {
    private ModelManager model;

    @BeforeEach
    void init() throws DuplicateMappingException, DuplicatePersonException, DuplicateGroupException {
        model = TypicalModel.generateTypicalModel();
    }

    @Test
    void getPersonNameSuggestions_partialPresentPersonName_oneFullPersonName() {
        final int inconsequentialValue = 0;
        final String presentPersonName = TypicalPersonDescriptor.ALICE.getName().toString();
        final String searchKeyword = StringUtil.substringBefore(presentPersonName, " ");
        assert !searchKeyword.equals(presentPersonName);

        final CommandArgument commandArgument = new CommandArgument(
                CliSyntax.PREFIX_NAME, inconsequentialValue, searchKeyword);
        final List<String> actualPersonNameSuggestions = Suggester.getPersonNameSuggestions(model, commandArgument);
        assertTrue(actualPersonNameSuggestions.contains(presentPersonName));
    }

    @Test
    void getPersonNameSuggestions_absentPersonName_noSuggestions() {
        final int inconsequentialValue = 0;
        final String absentPersonName = TypicalPersonDescriptor.ZACK.getName().toString();
        final String searchKeyword = absentPersonName.substring(0, 1);
        assert !searchKeyword.equals(absentPersonName);

        final CommandArgument commandArgument = new CommandArgument(
                CliSyntax.PREFIX_NAME, inconsequentialValue, searchKeyword);
        final List<String> actualPersonNameSuggestions = Suggester.getPersonNameSuggestions(model, commandArgument);
        assertFalse(actualPersonNameSuggestions.contains(absentPersonName));
    }

    @Test
    void getGroupNameSuggestions_partialPresentGroupName_fullGroupNamePresent() {
        final int inconsequentialValue = 0;
        final String presentGroupName = TypicalGroups.GROUP_NAME1.toString();
        final String searchKeyword = presentGroupName.substring(0, presentGroupName.length() - 1);
        assert !searchKeyword.equals(presentGroupName);

        final CommandArgument commandArgument = new CommandArgument(
                CliSyntax.PREFIX_GROUPNAME, inconsequentialValue, searchKeyword);
        final List<String> actualGroupNameSuggestions = Suggester.getGroupNameSuggestions(model, commandArgument);
        assertTrue(actualGroupNameSuggestions.contains(presentGroupName));
    }

    @Test
    void getGroupNameSuggestions_absentGroupName_noSuggestions() {
        final int inconsequentialValue = 0;
        final String absentGroupName = TypicalGroups.GROUP_NAME0.toString();
        final String searchKeyword = absentGroupName.substring(0, absentGroupName.length() - 1);
        assert !searchKeyword.equals(absentGroupName);

        final CommandArgument commandArgument = new CommandArgument(
                CliSyntax.PREFIX_GROUPNAME, inconsequentialValue, searchKeyword);
        final List<String> actualGroupNameSuggestions = Suggester.getGroupNameSuggestions(model, commandArgument);
        assertFalse(actualGroupNameSuggestions.contains(absentGroupName));
    }

    @Test
    void getValidLocationSuggestions_partialValidLocationName() {
        final int inconsequentialValue = 0;
        final String validLocation = "LT17";
        final String searchKeyword = "27092019-1500-1600-L";
        assert !searchKeyword.equals(validLocation);

        final CommandArgument commandArgument = new CommandArgument(
                CliSyntax.PREFIX_TIMING, inconsequentialValue, searchKeyword);
        final List<String> actualValidLocationSuggestions = Suggester
                .getValidLocationSuggestions(model, commandArgument);
        assertFalse(actualValidLocationSuggestions.contains(validLocation));
    }

    @Test
    void getValidLocationSuggestions_invalidValidLocationName() {
        final int inconsequentialValue = 0;
        final String searchKeyword = "27092019-1500-1600-FOOBARFOO";

        final CommandArgument commandArgument = new CommandArgument(
                CliSyntax.PREFIX_TIMING, inconsequentialValue, searchKeyword);
        final List<String> actualValidLocationSuggestions = Suggester
                .getValidLocationSuggestions(model, commandArgument);
        assertTrue(actualValidLocationSuggestions.isEmpty());
    }
}
