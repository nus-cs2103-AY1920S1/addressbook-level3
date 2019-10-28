package seedu.address.logic.commands.suggestions;

import org.junit.jupiter.api.Test;

import seedu.address.logic.parser.ArgumentList;
import seedu.address.logic.parser.CliSyntax;

class AddPersonCommandSuggesterTest extends SuggesterImplTester {

    protected AddPersonCommandSuggesterTest() throws ReflectiveOperationException {
        super(AddPersonCommandSuggester.class);
        disableAutoTestFor(CliSyntax.PREFIX_NAME);
    }

    @Test
    void getSuggestions_prefixName_noSuggestions() {
        final ArgumentList argumentList = singularArgumentListOfCommandArgument(
                CliSyntax.PREFIX_NAME, 0, "new person name");

        assertNoSuggestions(argumentList, argumentList.get(0));
    }
}
