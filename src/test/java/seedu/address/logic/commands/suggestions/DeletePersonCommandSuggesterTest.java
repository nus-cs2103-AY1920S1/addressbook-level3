package seedu.address.logic.commands.suggestions;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.stream.Collectors;

import seedu.address.logic.parser.ArgumentList;
import seedu.address.logic.parser.CliSyntax;
import seedu.address.logic.parser.CommandArgument;

class DeletePersonCommandSuggesterTest extends SuggesterImplTester {

    protected DeletePersonCommandSuggesterTest() throws ReflectiveOperationException {
        super(DeletePersonCommandSuggester.class);
    }

    void getSuggestion_multiPersonFirstExactSecondPartial_suggestionsExcludeFirstPersonName() {
        final String firstPersonName = allPersonNames().findFirst().orElseThrow();
        final String secondPersonName = allGroupNames().skip(1).findFirst().orElseThrow();
        final String partialSecondPersonName = secondPersonName.substring(0, secondPersonName.length() - 1);

        final ArgumentList argumentList = argumentListOf(
                new CommandArgument(CliSyntax.PREFIX_NAME, 0, firstPersonName),
                new CommandArgument(CliSyntax.PREFIX_NAME, 1, partialSecondPersonName)
        );
        final List<String> expectedSuggestions = allGroupNames().skip(1).collect(Collectors.toUnmodifiableList());
        final List<String> actualSuggestions = getSuggestions(argumentList, argumentList.get(1));

        assertEquals(expectedSuggestions, actualSuggestions);
    }
}
