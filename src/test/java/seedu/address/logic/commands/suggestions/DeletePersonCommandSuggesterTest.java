package seedu.address.logic.commands.suggestions;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.address.logic.parser.ArgumentList;
import seedu.address.logic.parser.CliSyntax;
import seedu.address.logic.parser.CommandArgument;

class DeletePersonCommandSuggesterTest extends SuggesterImplTester {

    protected DeletePersonCommandSuggesterTest() throws ReflectiveOperationException {
        super(DeletePersonCommandSuggester.class);
    }

    @Test
    void getSuggestion_multiPersonFirstExactSecondBlank_suggestionsExcludeFirstPersonName() {
        final String firstPersonName = allPersonNames().findFirst().orElseThrow();
        final String secondPersonName = EMPTY_STRING;

        final ArgumentList argumentList = argumentListOf(
                new CommandArgument(CliSyntax.PREFIX_NAME, 0, firstPersonName),
                new CommandArgument(CliSyntax.PREFIX_NAME, 1, secondPersonName)
        );
        final List<String> expectedSuggestions = allPersonNames().skip(1).collect(Collectors.toUnmodifiableList());

        assertSuggestionsEquals(argumentList, argumentList.get(1), expectedSuggestions);
    }
}
