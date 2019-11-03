package seedu.address.logic.commands.suggestions;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.Test;

import seedu.address.commons.util.StringUtil;
import seedu.address.logic.parser.ArgumentList;
import seedu.address.logic.parser.CliSyntax;
import seedu.address.testutil.grouputil.TypicalGroups;
import seedu.address.testutil.personutil.TypicalPersonDescriptor;

abstract class EditCommandSuggesterTest extends SuggesterImplTester {
    final PrefixEditType editType;

    protected EditCommandSuggesterTest(final Class<? extends Suggester> suggesterType, final PrefixEditType editType)
            throws ReflectiveOperationException {
        super(suggesterType);
        this.editType = editType;
        disableAutoTestFor(CliSyntax.PREFIX_GROUPNAME, CliSyntax.PREFIX_NAME);
    }

    @Test
    void getSuggestions_prefixEditPartialPresentPersonName_oneFullPersonName() {
        Assumptions.assumeTrue(editType == PrefixEditType.PERSON);

        final int inconsequentialValue = 0;
        final String presentPersonName = TypicalPersonDescriptor.ALICE.getName().toString();
        final String searchKeyword = StringUtil.substringBefore(presentPersonName, " ");
        assert !searchKeyword.equals(presentPersonName);

        final ArgumentList argumentList = singularArgumentListOfCommandArgument(
                CliSyntax.PREFIX_EDIT, inconsequentialValue, searchKeyword);
        final List<String> actualPersonNameSuggestions = getSuggestions(argumentList, argumentList.get(0));
        assertTrue(actualPersonNameSuggestions.contains(presentPersonName));
    }

    @Test
    void getSuggestions_prefixEditAbsentPersonName_noSuggestions() {
        Assumptions.assumeTrue(editType == PrefixEditType.PERSON);

        final int inconsequentialValue = 0;
        final String absentPersonName = TypicalPersonDescriptor.ZACK.getName().toString();
        final String searchKeyword = absentPersonName.substring(0, 1);
        assert !searchKeyword.equals(absentPersonName);

        final ArgumentList argumentList = singularArgumentListOfCommandArgument(
                CliSyntax.PREFIX_NAME, inconsequentialValue, searchKeyword);
        final List<String> actualPersonNameSuggestions = getSuggestions(argumentList, argumentList.get(0));
        assertFalse(actualPersonNameSuggestions.contains(absentPersonName));
    }

    @Test
    void getSuggestions_prefixEditNamePartialPresentGroupName_fullGroupNamePresent() {
        Assumptions.assumeTrue(editType == PrefixEditType.GROUP);

        final int inconsequentialValue = 0;
        final String presentGroupName = TypicalGroups.GROUPNAME1.toString();
        final String searchKeyword = presentGroupName.substring(0, presentGroupName.length() - 1);
        assert !searchKeyword.equals(presentGroupName);

        final ArgumentList argumentList = singularArgumentListOfCommandArgument(
                CliSyntax.PREFIX_EDIT, inconsequentialValue, searchKeyword);
        final List<String> actualGroupNameSuggestions = getSuggestions(argumentList, argumentList.get(0));
        assertTrue(actualGroupNameSuggestions.contains(presentGroupName));
    }

    @Test
    void getSuggestions_prefixGroupNameAbsentGroupName_noSuggestions() {
        Assumptions.assumeTrue(editType == PrefixEditType.GROUP);

        final int inconsequentialValue = 0;
        final String absentGroupName = TypicalGroups.GROUPNAME0.toString();
        final String searchKeyword = absentGroupName.substring(0, absentGroupName.length() - 1);
        assert !searchKeyword.equals(absentGroupName);

        final ArgumentList argumentList = singularArgumentListOfCommandArgument(
                CliSyntax.PREFIX_EDIT, inconsequentialValue, searchKeyword);
        final List<String> actualGroupNameSuggestions = getSuggestions(argumentList, argumentList.get(0));
        assertFalse(actualGroupNameSuggestions.contains(absentGroupName));
    }

    /**
     * Specifies whether the PREFIX_EDIT for the Suggester under test deals with people or groups.
     */
    enum PrefixEditType {
        PERSON, GROUP
    }
}
