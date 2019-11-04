package seedu.address.logic.commands.suggestions;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.address.logic.parser.ArgumentList;
import seedu.address.logic.parser.CliSyntax;
import seedu.address.logic.parser.CommandArgument;
import seedu.address.testutil.grouputil.TypicalGroups;
import seedu.address.testutil.personutil.TypicalPersonDescriptor;

class AddToGroupCommandSuggesterTest extends SuggesterImplTester {

    protected AddToGroupCommandSuggesterTest() throws ReflectiveOperationException {
        super(AddToGroupCommandSuggester.class);
    }

    @Test
    void getSuggestion_exactGroupBlankPersonName_allPersonNames() {
        final ArgumentList argumentList = argumentListOf(
                new CommandArgument(CliSyntax.PREFIX_GROUPNAME, 0, TypicalGroups.GROUPNAME1.toString()),
                new CommandArgument(CliSyntax.PREFIX_NAME, 1, EMPTY_STRING)
        );
        final List<String> expectedNames = allPersonNames().collect(Collectors.toUnmodifiableList());

        assertSuggestionsEquals(argumentList, argumentList.get(1), expectedNames);
    }

    @Test
    void getSuggestion_exactGroupPartialPersonName_onePersonName() {
        final ArgumentList argumentList = singularArgumentListOfCommandArgument(
                CliSyntax.PREFIX_GROUPNAME, 0, TypicalGroups.GROUPNAME1.toString()
        );
        final List<String> expectedNames = allPersonNames().limit(1).collect(Collectors.toUnmodifiableList());
        final String expectedName = expectedNames.get(0);
        final String searchName = expectedName.substring(0, expectedName.length() - 1);
        argumentList.add(new CommandArgument(CliSyntax.PREFIX_NAME, 1, searchName));

        assertSuggestionsEquals(argumentList, argumentList.get(1), expectedNames);
    }

    @Test
    void getSuggestion_exactPersonBlankGroupName_allGroupNames() {
        final ArgumentList argumentList = argumentListOf(
                new CommandArgument(CliSyntax.PREFIX_NAME, 0, TypicalPersonDescriptor.ALICE.getName().toString()),
                new CommandArgument(CliSyntax.PREFIX_GROUPNAME, 1, EMPTY_STRING)
        );

        final List<String> expectedGroupNames = allGroupNames().collect(Collectors.toUnmodifiableList());
        assertSuggestionsEquals(argumentList, argumentList.get(1), expectedGroupNames);
    }

    @Test
    void getSuggestion_exactPersonPartialGroupName_onePersonName() {
        final ArgumentList argumentList = singularArgumentListOfCommandArgument(
                CliSyntax.PREFIX_NAME, 0, TypicalPersonDescriptor.ALICE.getName().toString()
        );
        final String expectedName = allGroupNames().findFirst().orElseThrow();
        final String searchName = expectedName.substring(0, expectedName.length() - 1);
        final List<String> expectedGroupNames = allGroupNames().filter(groupName -> {
            return groupName.startsWith(searchName);
        }).collect(Collectors.toUnmodifiableList());

        argumentList.add(new CommandArgument(CliSyntax.PREFIX_GROUPNAME, 1, searchName));

        assertSuggestionsEquals(argumentList, argumentList.get(1), expectedGroupNames);
    }
}
