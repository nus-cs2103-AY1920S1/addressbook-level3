package seedu.address.logic.commands.suggestions;

import org.junit.jupiter.api.Test;

import seedu.address.logic.parser.ArgumentList;
import seedu.address.logic.parser.CliSyntax;

class AddGroupCommandSuggesterTest extends SuggesterImplTester {
    protected AddGroupCommandSuggesterTest() throws ReflectiveOperationException {
        super(AddGroupCommandSuggester.class);
        disableAutoTestFor(CliSyntax.PREFIX_GROUPNAME);
    }

    @Test
    void getSuggestions_prefixGroupName_noSuggestions() {
        final ArgumentList argumentList = singularArgumentListOfCommandArgument(
                CliSyntax.PREFIX_GROUPNAME, 0, "new group name");

        assertNoSuggestions(argumentList, argumentList.get(0));
    }
}
