package seedu.address.logic.commands.suggestions;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import seedu.address.logic.parser.ArgumentList;
import seedu.address.logic.parser.CliSyntax;
import seedu.address.logic.parser.CommandArgument;
import seedu.address.model.group.Group;
import seedu.address.model.group.GroupId;
import seedu.address.model.group.exceptions.GroupNotFoundException;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.PersonId;

class DeleteFromGroupCommandSuggesterTest extends SuggesterImplTester {
    protected DeleteFromGroupCommandSuggesterTest() throws ReflectiveOperationException {
        super(DeleteFromGroupCommandSuggester.class);
        disableAutoTestFor(CliSyntax.PREFIX_NAME);
    }

    @Test
    void getSuggestions_exactGroupBlankPersonName_allPersonNamesWithinGroup() {
        final GroupId groupIdWithMultiplePeople = new GroupId(1);
        final Group groupWithMultiplePeople;
        try {
            groupWithMultiplePeople = model.findGroup(groupIdWithMultiplePeople);
        } catch (GroupNotFoundException e) {
            Assertions.fail("Hard-coded group ID does not exist", e);
            return;
        }
        final List<PersonId> personsInGroup = model.findPersonsOfGroup(groupIdWithMultiplePeople);
        if (personsInGroup.size() < 2) {
            Assertions.fail("Hard-coded group ID does not already have more than 1 person in the group");
        }

        final String groupName = groupWithMultiplePeople.getGroupName().toString();
        final ArgumentList argumentList = argumentListOf(
                new CommandArgument(CliSyntax.PREFIX_GROUPNAME, 0, groupName),
                new CommandArgument(CliSyntax.PREFIX_NAME, 1, EMPTY_STRING)
        );

        final List<String> expectedNames = personsInGroup.stream()
                .map(model::findPerson)
                .map(Person::getName)
                .map(Name::toString)
                .collect(Collectors.toUnmodifiableList());

        assertSuggestionsEquals(argumentList, argumentList.get(1), expectedNames);
    }

    @Test
    void getSuggestions_nonExistentGroupBlankPersonName_noSuggestions() {
        final ArgumentList argumentList = argumentListOf(
                new CommandArgument(CliSyntax.PREFIX_GROUPNAME, 0, "non-existent group name"),
                new CommandArgument(CliSyntax.PREFIX_NAME, 1, EMPTY_STRING)
        );

        assertNoSuggestions(argumentList, argumentList.get(1));
    }

    @Test
    void getSuggestions_missingGroupPrefixBlankPersonName_noSuggestions() {
        final ArgumentList argumentList = argumentListOf(
                new CommandArgument(CliSyntax.PREFIX_NAME, 0, EMPTY_STRING)
        );

        assertNoSuggestions(argumentList, argumentList.get(0));
    }

    @Test
    void getSuggestions_blankGroupPrefixBlankPersonName_noSuggestions() {
        final ArgumentList argumentList = argumentListOf(
                new CommandArgument(CliSyntax.PREFIX_GROUPNAME, 0, EMPTY_STRING),
                new CommandArgument(CliSyntax.PREFIX_NAME, 0, EMPTY_STRING)
        );

        assertNoSuggestions(argumentList, argumentList.get(1));
    }

    @Test
    void getSuggestions_whitespaceGroupPrefixBlankPersonName_noSuggestions() {
        final ArgumentList argumentList = argumentListOf(
                new CommandArgument(CliSyntax.PREFIX_GROUPNAME, 0, " "),
                new CommandArgument(CliSyntax.PREFIX_NAME, 0, EMPTY_STRING)
        );

        assertNoSuggestions(argumentList, argumentList.get(1));
    }
}
