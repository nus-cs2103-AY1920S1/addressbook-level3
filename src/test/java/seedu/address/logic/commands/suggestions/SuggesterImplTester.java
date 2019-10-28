package seedu.address.logic.commands.suggestions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.Constructor;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Stream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import seedu.address.logic.parser.ArgumentList;
import seedu.address.logic.parser.CliSyntax;
import seedu.address.logic.parser.CommandArgument;
import seedu.address.logic.parser.Prefix;
import seedu.address.model.ModelManager;
import seedu.address.model.group.Group;
import seedu.address.model.group.GroupName;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.testutil.grouputil.TypicalGroups;
import seedu.address.testutil.modelutil.TypicalModel;
import seedu.address.testutil.personutil.TypicalPersonDescriptor;

/**
 * Abstract class used to test classes that extend from the {@link Suggester} class.
 */
abstract class SuggesterImplTester {
    protected static final List<String> NO_SUGGESTIONS = List.of();
    protected static final Prefix UNRELATED_PREFIX = new Prefix("test/");
    protected static final CommandArgument UNRELATED_COMMAND_ARGUMENT = new CommandArgument(
            UNRELATED_PREFIX, 0, "test value");
    protected static final ArgumentList UNRELATED_ARGUMENT_LIST = argumentListOf(UNRELATED_COMMAND_ARGUMENT);
    protected final Set<Prefix> autoTestPrefixes = new HashSet<>();
    protected ModelManager model;
    protected Suggester suggester;
    private final Supplier<Suggester> createSuggester;

    protected SuggesterImplTester(final Class<? extends Suggester> suggesterType) throws ReflectiveOperationException {
        final Constructor<? extends Suggester> constructor = suggesterType.getConstructor();
        suggester = constructor.newInstance();
        autoTestPrefixes.addAll((List<Prefix>) suggesterType.getField("SUPPORTED_PREFIXES").get(null));

        createSuggester = () -> {
            try {
                return constructor.newInstance();
            } catch (ReflectiveOperationException e) {
                Assertions.fail(e);
                return null;
            }
        };
    }

    static ArgumentList argumentListOf(final CommandArgument... commandArguments) {
        final ArgumentList argumentList = new ArgumentList();
        argumentList.addAll(Arrays.asList(commandArguments));
        return argumentList;
    }

    static ArgumentList singularArgumentListOfCommandArgument(
            final Prefix prefix, final int startPosition, final String value) {
        return argumentListOf(new CommandArgument(prefix, startPosition, value));
    }

    protected boolean disableAutoTestFor(final Prefix... prefixes) {
        return autoTestPrefixes.removeAll(Arrays.asList(prefixes));
    }

    final ObservableList<String> getSuggestions(final ArgumentList arguments, final CommandArgument commandArgument) {
        return suggester.getSuggestions(model, arguments, commandArgument);
    }

    void assertNoSuggestions(final ArgumentList arguments, final CommandArgument commandArgument) {
        assertEquals(NO_SUGGESTIONS, getSuggestions(arguments, commandArgument));
    }

    /**
     * Gets a stream of all the names of the {@link Person}s from the model.
     *
     * @return A stream of all the names of the {@link Person}s from the model.
     */
    Stream<String> allPersonNames() {
        return model
                .getPersonList()
                .getPersons()
                .stream()
                .map(Person::getName)
                .map(Name::toString);
    }

    /**
     * Gets a stream of all the names of the {@link Group}s from the model.
     *
     * @return A stream of all the names of the {@link Group}s from the model.
     */
    Stream<String> allGroupNames() {
        return model
                .getGroupList()
                .getGroups()
                .stream()
                .map(Group::getGroupName)
                .map(GroupName::toString);
    }

    @BeforeEach
    void setUp() {
        model = TypicalModel.generateTypicalModel();
        suggester = createSuggester.get();
    }

    @Test
    final void getSuggestions_unrelatedPrefix_noSuggestions() {
        assertEquals(List.of(), getSuggestions(UNRELATED_ARGUMENT_LIST, UNRELATED_COMMAND_ARGUMENT));
    }

    @Test
    void getSuggestions_prefixNamePartialPresentPersonName_oneFullPersonName() {
        Assumptions.assumeTrue(autoTestPrefixes.contains(CliSyntax.PREFIX_NAME));
        final int inconsequentialValue = 0;
        final String presentPersonName = TypicalPersonDescriptor.ALICE.getName().toString();
        final String searchKeyword = presentPersonName.replaceAll(" .*$", "");
        assert !searchKeyword.equals(presentPersonName);

        final ArgumentList argumentList = singularArgumentListOfCommandArgument(
                CliSyntax.PREFIX_NAME, inconsequentialValue, searchKeyword);
        final List<String> actualPersonNameSuggestions = getSuggestions(argumentList, argumentList.get(0));
        assertTrue(actualPersonNameSuggestions.contains(presentPersonName));
    }

    @Test
    void getSuggestions_prefixNameAbsentPersonName_noSuggestions() {
        Assumptions.assumeTrue(autoTestPrefixes.contains(CliSyntax.PREFIX_NAME));
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
    void getSuggestions_prefixGroupNamePartialPresentGroupName_fullGroupNamePresent() {
        Assumptions.assumeTrue(autoTestPrefixes.contains(CliSyntax.PREFIX_GROUPNAME));

        final int inconsequentialValue = 0;
        final String presentGroupName = TypicalGroups.GROUPNAME1.toString();
        final String searchKeyword = presentGroupName.substring(0, presentGroupName.length() - 1);
        assert !searchKeyword.equals(presentGroupName);

        final ArgumentList argumentList = singularArgumentListOfCommandArgument(
                CliSyntax.PREFIX_GROUPNAME, inconsequentialValue, searchKeyword);
        final List<String> actualGroupNameSuggestions = getSuggestions(argumentList, argumentList.get(0));
        assertTrue(actualGroupNameSuggestions.contains(presentGroupName));
    }

    @Test
    void getSuggestions_prefixGroupNameAbsentGroupName_noSuggestions() {
        Assumptions.assumeTrue(autoTestPrefixes.contains(CliSyntax.PREFIX_GROUPNAME));

        final int inconsequentialValue = 0;
        final String absentGroupName = TypicalGroups.GROUPNAME0.toString();
        final String searchKeyword = absentGroupName.substring(0, absentGroupName.length() - 1);
        assert !searchKeyword.equals(absentGroupName);

        final ArgumentList argumentList = singularArgumentListOfCommandArgument(
                CliSyntax.PREFIX_GROUPNAME, inconsequentialValue, searchKeyword);
        final List<String> actualGroupNameSuggestions = getSuggestions(argumentList, argumentList.get(0));
        assertFalse(actualGroupNameSuggestions.contains(absentGroupName));
    }
}
