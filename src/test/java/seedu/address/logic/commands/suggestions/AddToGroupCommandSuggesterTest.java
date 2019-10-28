package seedu.address.logic.commands.suggestions;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
        final ArgumentList argumentList = singularArgumentListOfCommandArgument(
                CliSyntax.PREFIX_GROUPNAME, 0, TypicalGroups.GROUPNAME1.toString()
        );
        argumentList.add(new CommandArgument(CliSyntax.PREFIX_NAME, 1, ""));

        final List<String> expectedNames = allPersonNames().collect(Collectors.toUnmodifiableList());
        final List<String> actualNames = getSuggestions(argumentList, argumentList.get(1));
        assertEquals(expectedNames, actualNames);
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

        final List<String> actualNames = getSuggestions(argumentList, argumentList.get(1));
        assertEquals(expectedNames, actualNames);
    }

    @Test
    void getSuggestion_exactPersonBlankGroupName_allGroupNames() {
        final ArgumentList argumentList = singularArgumentListOfCommandArgument(
                CliSyntax.PREFIX_NAME, 0, TypicalPersonDescriptor.ALICE.getName().toString()
        );
        argumentList.add(new CommandArgument(CliSyntax.PREFIX_GROUPNAME, 1, ""));

        final List<String> expectedGroupNames = allGroupNames().collect(Collectors.toUnmodifiableList());
        final List<String> actualGroupNames = getSuggestions(argumentList, argumentList.get(1));
        assertEquals(expectedGroupNames, actualGroupNames);
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

        final List<String> actualGroupNames = getSuggestions(argumentList, argumentList.get(1));
        assertEquals(expectedGroupNames, actualGroupNames);
    }
}
