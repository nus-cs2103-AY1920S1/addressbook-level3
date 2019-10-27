package seedu.algobase.testutil;

import java.util.Arrays;

import seedu.algobase.model.searchrule.problemsearchrule.AuthorMatchesKeywordPredicate;
import seedu.algobase.model.searchrule.problemsearchrule.DescriptionContainsKeywordsPredicate;
import seedu.algobase.model.searchrule.problemsearchrule.DifficultyIsInRangePredicate;
import seedu.algobase.model.searchrule.problemsearchrule.Keyword;
import seedu.algobase.model.searchrule.problemsearchrule.Name;
import seedu.algobase.model.searchrule.problemsearchrule.NameContainsKeywordsPredicate;
import seedu.algobase.model.searchrule.problemsearchrule.ProblemSearchRule;
import seedu.algobase.model.searchrule.problemsearchrule.SourceMatchesKeywordPredicate;
import seedu.algobase.model.searchrule.problemsearchrule.TagIncludesKeywordsPredicate;
import seedu.algobase.storage.JsonAdaptedKeyword;

/**
 * A utility class containing a list of {@code ProblemSearchRule} objects to be used in tests.
 */
public class TypicalProblemSearchRules {

    public static final ProblemSearchRule MEDIUM_DIFFICULTY = new ProblemSearchRule(
        new Name("Medium difficulty"),
        null,
        null,
        null,
        null,
        new DifficultyIsInRangePredicate(1.0, 3.0),
        null
    );

    public static final ProblemSearchRule NAME_SEQUENCES = new ProblemSearchRule(
        new Name("Sequences"),
        new NameContainsKeywordsPredicate(Arrays.asList(new Keyword("Sequences"))),
        null,
        null,
        null,
        null,
        null
    );

    public static final ProblemSearchRule ALL_PREDICATE = new ProblemSearchRule(
        new Name("All predicates search rule"),
        new NameContainsKeywordsPredicate(Arrays.asList(new Keyword("hello"))),
        new AuthorMatchesKeywordPredicate(new Keyword("steven")),
        new DescriptionContainsKeywordsPredicate(Arrays.asList(new Keyword("des"))),
        new SourceMatchesKeywordPredicate(new Keyword("src")),
        new DifficultyIsInRangePredicate(1.0, 3.0),
        new TagIncludesKeywordsPredicate(Arrays.asList(new Keyword("tags")))
    );

    public static final String INVALID_KEYWORD = "";
    public static final JsonAdaptedKeyword INVALID_ADAPTED_KEYWORD = new JsonAdaptedKeyword(INVALID_KEYWORD);
}
