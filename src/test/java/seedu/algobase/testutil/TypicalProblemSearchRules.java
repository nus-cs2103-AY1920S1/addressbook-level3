package seedu.algobase.testutil;

import static seedu.algobase.testutil.TypicalProblems.QUICK_SORT;
import static seedu.algobase.testutil.TypicalProblems.getTypicalProblems;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import seedu.algobase.model.AlgoBase;
import seedu.algobase.model.problem.Problem;
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
        new NameContainsKeywordsPredicate(Collections.singletonList(new Keyword("Sequences"))),
        null,
        null,
        null,
        null,
        null
    );

    public static final ProblemSearchRule NAME_SEQUENCES_DUPLICATE = new ProblemSearchRule(
        new Name("Sequences"),
        new NameContainsKeywordsPredicate(Collections.singletonList(new Keyword("Sequences"))),
        null,
        null,
        null,
        null,
        null
    );

    public static final ProblemSearchRule NAME_SEQUENCES_SAME_NAME_DIFFERENT_PREDICATE = new ProblemSearchRule(
        new Name("Sequences"),
        new NameContainsKeywordsPredicate(Collections.singletonList(new Keyword("Hello"))),
        null,
        null,
        null,
        new DifficultyIsInRangePredicate(1.0, 3.0),
        null
    );

    public static final ProblemSearchRule ALL_PREDICATE = new ProblemSearchRule(
        new Name("All predicates search rule"),
        new NameContainsKeywordsPredicate(Collections.singletonList(new Keyword("hello"))),
        new AuthorMatchesKeywordPredicate(new Keyword("steven")),
        new DescriptionContainsKeywordsPredicate(Collections.singletonList(new Keyword("des"))),
        new SourceMatchesKeywordPredicate(new Keyword("src")),
        new DifficultyIsInRangePredicate(1.0, 3.0),
        new TagIncludesKeywordsPredicate(Collections.singletonList(new Keyword("tags")))
    );

    public static final ProblemSearchRule QUICK_SORT_PREDICATE = new ProblemSearchRule(
        new Name("Quick sort predicate"),
        new NameContainsKeywordsPredicate(Arrays.stream(QUICK_SORT.getName().fullName.split(" "))
            .map(Keyword::new)
            .collect(Collectors.toList())),
        null,
        null,
        null,
        null,
        null
    );

    public static final String INVALID_KEYWORD = "";
    public static final JsonAdaptedKeyword INVALID_ADAPTED_KEYWORD = new JsonAdaptedKeyword(INVALID_KEYWORD);

    /**
     * Returns an {@code AlgoBase} with all typical find rules and problems.
     */
    public static AlgoBase getTypicalAlgoBase() {
        AlgoBase algoBase = new AlgoBase();
        for (ProblemSearchRule rule : getTypicalFindRules()) {
            algoBase.addFindRule(rule);
        }
        for (Problem problem : getTypicalProblems()) {
            algoBase.addProblem(problem);
        }
        return algoBase;
    }

    public static List<ProblemSearchRule> getTypicalFindRules() {
        // Please always keep QUICK_SORT_PREDICATE as the first predicate.
        return new ArrayList<>(Arrays.asList(QUICK_SORT_PREDICATE, MEDIUM_DIFFICULTY, NAME_SEQUENCES, ALL_PREDICATE));
    }
}
