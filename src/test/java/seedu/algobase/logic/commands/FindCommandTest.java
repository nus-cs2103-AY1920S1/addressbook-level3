package seedu.algobase.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.algobase.logic.commands.CommandTestUtil.VALID_DESCRIPTION_QUICK_SORT;
import static seedu.algobase.logic.commands.CommandTestUtil.VALID_NAME_QUICK_SORT;
import static seedu.algobase.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.algobase.testutil.Assert.assertThrows;
import static seedu.algobase.testutil.TypicalAlgoBase.getTypicalAlgoBase;
import static seedu.algobase.testutil.TypicalProblems.QUICK_SORT;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.algobase.commons.core.Messages;
import seedu.algobase.logic.CommandHistory;
import seedu.algobase.logic.commands.problem.FindCommand;
import seedu.algobase.model.Model;
import seedu.algobase.model.ModelManager;
import seedu.algobase.model.UserPrefs;
import seedu.algobase.model.problem.Problem;
import seedu.algobase.model.searchrule.problemsearchrule.AuthorMatchesKeywordPredicate;
import seedu.algobase.model.searchrule.problemsearchrule.DescriptionContainsKeywordsPredicate;
import seedu.algobase.model.searchrule.problemsearchrule.DifficultyIsInRangePredicate;
import seedu.algobase.model.searchrule.problemsearchrule.FindProblemDescriptor;
import seedu.algobase.model.searchrule.problemsearchrule.Keyword;
import seedu.algobase.model.searchrule.problemsearchrule.NameContainsKeywordsPredicate;
import seedu.algobase.model.searchrule.problemsearchrule.SourceMatchesKeywordPredicate;
import seedu.algobase.model.searchrule.problemsearchrule.TagIncludesKeywordsPredicate;
import seedu.algobase.model.tag.Tag;
import seedu.algobase.testutil.FindProblemDescriptorBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code FindCommand}.
 */
class FindCommandTest {

    private static final List<String> VALID_NAME_KEYWORDS = Arrays.asList(VALID_NAME_QUICK_SORT.split(" "));
    private static final String VALID_AUTHOR_KEYWORD = QUICK_SORT.getAuthor().value;
    private static final List<String> VALID_DESCRIPTION_KEYWORDS =
        Arrays.asList(VALID_DESCRIPTION_QUICK_SORT.split(" "));
    private static final String VALID_SOURCE_KEYWORD = QUICK_SORT.getSource().value;
    private static final List<String> VALID_TAG_STRING_LIST =
        Arrays.stream(QUICK_SORT.getTags().toArray(new Tag[] {}))
            .map(tag -> tag.tagName)
            .collect(Collectors.toList());
    private Model model = new ModelManager(getTypicalAlgoBase(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAlgoBase(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    /**
     * Converts a list of strings into a list of keywords. Returns an empty list if there is any invalid string.
     * @param strings list of strings to be converted
     */
    private List<Keyword> stringListToKeywordList(List<String> strings) {
        if (strings.stream().anyMatch(str -> !Keyword.isValidKeyword(str))) {
            return new ArrayList<>();
        }
        return strings.stream()
            .map(Keyword::new)
            .collect(Collectors.toList());
    }

    /**
     * Asserts the current filtered problem list only contains {@link seedu.algobase.testutil.TypicalProblems}'s
     * {@code QUICK_SORT}.
     */
    private void assertQuickSortAsTheOnlyResult(FindProblemDescriptor descriptor,
                                                Predicate<Problem> predicate,
                                                String expectedMessage) {
        FindCommand command = new FindCommand(descriptor);
        expectedModel.updateFilteredProblemList(predicate);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Collections.singletonList(QUICK_SORT), model.getFilteredProblemList());
    }

    @Test
    public void constructor_nullDescriptor_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new FindCommand(null));
    }

    @Test
    public void execute_caseInsensitiveNameMatch_problemFound() {
        String expectedMessage = String.format(Messages.MESSAGE_PROBLEMS_LISTED_OVERVIEW, 1);
        NameContainsKeywordsPredicate predicate =
            new NameContainsKeywordsPredicate(VALID_NAME_KEYWORDS.stream()
                .map(keyword -> new Keyword(keyword.toUpperCase()))
                .collect(Collectors.toList()));
        FindProblemDescriptor descriptor = new FindProblemDescriptorBuilder().withNamePredicate(predicate).build();
        assertQuickSortAsTheOnlyResult(descriptor, predicate, expectedMessage);
    }

    @Test
    public void execute_authorExactMatch_problemFound() {
        String expectedMessage = String.format(Messages.MESSAGE_PROBLEMS_LISTED_OVERVIEW, 1);
        AuthorMatchesKeywordPredicate predicate =
            new AuthorMatchesKeywordPredicate(new Keyword(VALID_AUTHOR_KEYWORD));
        FindProblemDescriptor descriptor = new FindProblemDescriptorBuilder().withAuthorPredicate(predicate).build();
        assertQuickSortAsTheOnlyResult(descriptor, predicate, expectedMessage);
    }

    @Test
    public void execute_descriptionCaseInsensitiveMatch_problemFound() {
        String expectedMessage = String.format(Messages.MESSAGE_PROBLEMS_LISTED_OVERVIEW, 1);
        List<String> allCapitalKeywords =
            VALID_DESCRIPTION_KEYWORDS.stream()
                .map(String::toUpperCase)
                .collect(Collectors.toList());
        DescriptionContainsKeywordsPredicate predicate =
            new DescriptionContainsKeywordsPredicate(stringListToKeywordList(allCapitalKeywords));
        FindProblemDescriptor descriptor = new FindProblemDescriptorBuilder()
            .withDescriptionPredicate(predicate).build();
        assertQuickSortAsTheOnlyResult(descriptor, predicate, expectedMessage);
    }

    @Test
    public void execute_descriptionExactMatch_problemFound() {
        String expectedMessage = String.format(Messages.MESSAGE_PROBLEMS_LISTED_OVERVIEW, 1);
        DescriptionContainsKeywordsPredicate predicate =
            new DescriptionContainsKeywordsPredicate(stringListToKeywordList(VALID_DESCRIPTION_KEYWORDS));
        FindProblemDescriptor descriptor = new FindProblemDescriptorBuilder()
            .withDescriptionPredicate(predicate).build();
        assertQuickSortAsTheOnlyResult(descriptor, predicate, expectedMessage);
    }

    @Test
    public void execute_sourceExactMatch_problemFound() {
        String expectedMessage = String.format(Messages.MESSAGE_PROBLEMS_LISTED_OVERVIEW, 1);
        SourceMatchesKeywordPredicate predicate =
            new SourceMatchesKeywordPredicate(new Keyword(VALID_SOURCE_KEYWORD));
        FindProblemDescriptor descriptor = new FindProblemDescriptorBuilder().withSourcePredicate(predicate).build();
        assertQuickSortAsTheOnlyResult(descriptor, predicate, expectedMessage);
    }

    @Test
    public void execute_tagExactMatch_problemFound() {
        String expectedMessage = String.format(Messages.MESSAGE_PROBLEMS_LISTED_OVERVIEW, 1);
        TagIncludesKeywordsPredicate predicate =
            new TagIncludesKeywordsPredicate(stringListToKeywordList(VALID_TAG_STRING_LIST));
        FindProblemDescriptor descriptor = new FindProblemDescriptorBuilder().withTagPredicate(predicate).build();
        assertQuickSortAsTheOnlyResult(descriptor, predicate, expectedMessage);
    }

    @Test
    public void execute_difficultyExactMatch_problemFound() {
        String expectedMessage = String.format(Messages.MESSAGE_PROBLEMS_LISTED_OVERVIEW, 1);
        DifficultyIsInRangePredicate predicate =
            new DifficultyIsInRangePredicate(QUICK_SORT.getDifficulty().value, QUICK_SORT.getDifficulty().value);
        FindProblemDescriptor descriptor = new FindProblemDescriptorBuilder()
            .withDifficultyPredicate(predicate).build();
        assertQuickSortAsTheOnlyResult(descriptor, predicate, expectedMessage);
    }

    @Test
    public void equals() {
        DifficultyIsInRangePredicate firstPredicate =
            new DifficultyIsInRangePredicate(QUICK_SORT.getDifficulty().value, QUICK_SORT.getDifficulty().value);
        FindProblemDescriptor firstDescriptor = new FindProblemDescriptorBuilder()
            .withDifficultyPredicate(firstPredicate).build();
        TagIncludesKeywordsPredicate secondPredicate =
            new TagIncludesKeywordsPredicate(stringListToKeywordList(VALID_TAG_STRING_LIST));
        FindProblemDescriptor secondDescriptor =
            new FindProblemDescriptorBuilder().withTagPredicate(secondPredicate).build();

        FindCommand findFirstCommand = new FindCommand(firstDescriptor);
        FindCommand findSecondCommand = new FindCommand(secondDescriptor);

        // same object -> returns true
        assertEquals(findFirstCommand, findFirstCommand);

        // same values -> returns true
        FindCommand findFirstCommandCopy = new FindCommand(firstDescriptor);
        assertEquals(findFirstCommand, findFirstCommandCopy);

        // different types -> returns false
        assertNotEquals(1, findFirstCommand);

        // null -> returns false
        assertNotEquals(null, findFirstCommand);

        // different person -> returns false
        assertNotEquals(findFirstCommand, findSecondCommand);
    }

}
