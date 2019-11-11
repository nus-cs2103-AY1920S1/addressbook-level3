package seedu.address.logic.commands.suggestions;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.address.logic.parser.ArgumentList;
import seedu.address.logic.parser.CliSyntax;
import seedu.address.logic.parser.CommandArgument;

class DeleteGroupCommandSuggesterTest extends SuggesterImplTester {

    protected DeleteGroupCommandSuggesterTest() throws ReflectiveOperationException {
        super(DeleteGroupCommandSuggester.class);
    }

    @Test
    void getSuggestion_multiGroupFirstExactSecondPartial_suggestionsExcludeFirstGroupName() {
        final String firstGroupName = allGroupNames().findFirst().orElseThrow();
        final String secondGroupName = allGroupNames().skip(1).findFirst().orElseThrow();
        final String partialSecondGroupName = secondGroupName.substring(0, secondGroupName.length() - 1);

        final ArgumentList argumentList = argumentListOf(
                new CommandArgument(CliSyntax.PREFIX_GROUPNAME, 0, firstGroupName),
                new CommandArgument(CliSyntax.PREFIX_GROUPNAME, 1, partialSecondGroupName)
        );
        final List<String> expectedSuggestions = allGroupNames().skip(1).collect(Collectors.toUnmodifiableList());

        assertSuggestionsEquals(argumentList, argumentList.get(1), expectedSuggestions);
    }
}
