package seedu.address.logic.commands.suggestions;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.logic.parser.ArgumentList;
import seedu.address.logic.parser.CliSyntax;
import seedu.address.logic.parser.CommandArgument;
import seedu.address.model.GmapsModelManager;

class AddEventCommandSuggesterTest extends SuggesterImplTester {

    protected AddEventCommandSuggesterTest() throws ReflectiveOperationException {
        super(AddEventCommandSuggester.class);
    }

    @Test
    void getValidLocationSuggestion_userEnterBlank_thirdHyphenPresent() {
        final String prefix = "01012019-1200-1400-";
        final ArgumentList arguments = argumentListOf(
                new CommandArgument(CliSyntax.PREFIX_TIMING, 0, "01012019-1200-1400-")
        );
        List<String> expectedValidLocations = new GmapsModelManager().validLocationSuggester("");
        List<String> expectedSuggestionWPrefix = new ArrayList<>();
        for (int i = 0; i < expectedValidLocations.size(); i++) {
            expectedSuggestionWPrefix.add(prefix + expectedValidLocations.get(i));
        }
        assertSuggestionsEquals(arguments, arguments.get(0), expectedSuggestionWPrefix);
    }

    @Test
    void userEnterBlank_thirdHyphenAbsent() {
        final ArgumentList arguments = argumentListOf(
                new CommandArgument(CliSyntax.PREFIX_TIMING, 0, "01012019-1200-1400")
        );
        assertSuggestionsEquals(arguments, arguments.get(0), new ArrayList<String>());
    }
}
