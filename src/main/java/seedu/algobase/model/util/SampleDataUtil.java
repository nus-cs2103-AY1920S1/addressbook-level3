package seedu.algobase.model.util;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.algobase.model.AlgoBase;
import seedu.algobase.model.Id;
import seedu.algobase.model.ModelType;
import seedu.algobase.model.ReadOnlyAlgoBase;
import seedu.algobase.model.gui.TabData;
import seedu.algobase.model.plan.Plan;
import seedu.algobase.model.plan.PlanDescription;
import seedu.algobase.model.plan.PlanName;
import seedu.algobase.model.problem.Author;
import seedu.algobase.model.problem.Description;
import seedu.algobase.model.problem.Difficulty;
import seedu.algobase.model.problem.Name;
import seedu.algobase.model.problem.Problem;
import seedu.algobase.model.problem.Remark;
import seedu.algobase.model.problem.Source;
import seedu.algobase.model.problem.WebLink;
import seedu.algobase.model.searchrule.problemsearchrule.AuthorMatchesKeywordPredicate;
import seedu.algobase.model.searchrule.problemsearchrule.DescriptionContainsKeywordsPredicate;
import seedu.algobase.model.searchrule.problemsearchrule.DifficultyIsInRangePredicate;
import seedu.algobase.model.searchrule.problemsearchrule.Keyword;
import seedu.algobase.model.searchrule.problemsearchrule.NameContainsKeywordsPredicate;
import seedu.algobase.model.searchrule.problemsearchrule.ProblemSearchRule;
import seedu.algobase.model.searchrule.problemsearchrule.SourceMatchesKeywordPredicate;
import seedu.algobase.model.searchrule.problemsearchrule.TagIncludesKeywordsPredicate;
import seedu.algobase.model.tag.Tag;
import seedu.algobase.model.task.Task;

/**
 * Contains utility methods for populating {@code AlgoBase} with sample data.
 */
public class SampleDataUtil {
    public static Problem[] getSampleProblems() {
        return new Problem[] {
            new Problem(Id.generateId(),
                new Name("Sequences"), new Author("Tung Kam Chuen"),
                new WebLink("https://open.kattis.com/problems/sequences"),
                new Description("Find the sum of the number of inversions of the 2k sequences, "
                    + "modulo 1000000007(109+7)."),
                getTagSet(),
                Difficulty.DEFAULT_DIFFICULTY,
                Remark.DEFAULT_REMARK,
                new Source("Kattis")),
            new Problem(Id.generateId(),
                new Name("Two Sum"), Author.DEFAULT_AUTHOR,
                new WebLink("https://leetcode.com/problems/two-sum/"),
                new Description("Given an array of integers, "
                    + "return indices of the two numbers such that they add up to a specific target."),
                getTagSet("array", "hash-table", "algorithm"),
                new Difficulty("1.0"),
                new Remark("You may assume that each input would have exactly one solution, \n"
                    + "and you may not use the same element twice."),
                new Source("LeetCode")),
            new Problem(Id.generateId(),
                new Name("Second Highest Salary"), new Author("LeetCode"),
                new WebLink("https://leetcode.com/problems/second-highest-salary/"),
                new Description("Write a SQL query to get the second highest salary from the Employee table."),
                getTagSet("MySQL", "database"),
                new Difficulty("2.0"),
                Remark.DEFAULT_REMARK,
                new Source("LeetCode")),
            new Problem(Id.generateId(),
                new Name("Sudoku Solver"), Author.DEFAULT_AUTHOR,
                new WebLink("https://leetcode.com/problems/sudoku-solver/"),
                Description.DEFAULT_DESCRIPTION,
                getTagSet("hash-table", "backtracking", "algorithm"),
                new Difficulty("5.0"),
                new Remark("You may assume that the given Sudoku puzzle will have a single unique solution."),
                new Source("LeetCode")),
            new Problem(Id.generateId(),
                new Name("A Dawid and Bags of Candies"), Author.DEFAULT_AUTHOR,
                new WebLink("https://codeforces.com/problemset/problem/1230/A"),
                Description.DEFAULT_DESCRIPTION,
                getTagSet("brute-force"),
                new Difficulty("4.0"),
                new Remark("time limit per test1 second" + "memory limit per test256 megabytes"),
                new Source("CodeForce")),
            new Problem(Id.generateId(),
                new Name("Factorial"), new Author("Wee Han"), WebLink.DEFAULT_WEBLINK,
                new Description("define a function factorial that takes in a number n "
                    + "and returns the factorial of the number."),
                getTagSet("recursion"),
                Difficulty.DEFAULT_DIFFICULTY,
                Remark.DEFAULT_REMARK,
                Source.DEFAULT_SOURCE)
        };
    }

    private static List<Tag> getSampleTags() {
        List<Tag> tags = new ArrayList<>();
        tags.add(new Tag("algorithm"));
        tags.add(new Tag("array"));
        tags.add(new Tag("hash-table"));
        tags.add(new Tag("MySQL"));
        tags.add(new Tag("database"));
        tags.add(new Tag("backtracking"));
        tags.add(new Tag("brute-force"));
        tags.add(new Tag("recursion"));
        return tags;
    }

    private static List<ProblemSearchRule> getSampleFindRules() {
        List<ProblemSearchRule> rules = new ArrayList<>();
        rules.add(new ProblemSearchRule(
            new seedu.algobase.model.searchrule.problemsearchrule.Name("Medium Difficulty"),
            null, null, null, null,
            new DifficultyIsInRangePredicate(2.0, 4.0), null));
        rules.add(new ProblemSearchRule(
            new seedu.algobase.model.searchrule.problemsearchrule.Name("Sequence Problems"),
            new NameContainsKeywordsPredicate(Arrays.asList(new Keyword("Sequences"))),
            null, null, null, null, null));
        rules.add(new ProblemSearchRule(
            new seedu.algobase.model.searchrule.problemsearchrule.Name("Two Sum"),
            new NameContainsKeywordsPredicate(Arrays.asList(new Keyword("two"), new Keyword("SUM"))),
            null,
            new DescriptionContainsKeywordsPredicate(Arrays.asList(new Keyword("indices"))),
            new SourceMatchesKeywordPredicate(new Keyword("LeetCode")),
            new DifficultyIsInRangePredicate(0.5, 1.5),
            new TagIncludesKeywordsPredicate(Arrays.asList(new Keyword("hash-table")))
        ));
        rules.add(new ProblemSearchRule(
            new seedu.algobase.model.searchrule.problemsearchrule.Name("Problems by Wee Han"),
            null, new AuthorMatchesKeywordPredicate(new Keyword("Wee Han")),
            null, null, null, null));
        return rules;
    }

    public static ReadOnlyAlgoBase getSampleAlgoBase() {
        AlgoBase sampleAb = new AlgoBase();
        Set<Task> tasks = new HashSet<>();
        sampleAb.setTags(getSampleTags());
        for (Problem sampleProblem : getSampleProblems()) {
            sampleAb.addProblem(sampleProblem);
            tasks.add(new Task(sampleProblem, LocalDate.now().plusMonths(1), false));
        }
        for (ProblemSearchRule rule : getSampleFindRules()) {
            sampleAb.addFindRule(rule);
        }
        Plan samplePlan = new Plan(
            new PlanName("Data Structures"),
            new PlanDescription("CS2040 Data Structures"),
            LocalDate.now(),
            LocalDate.now().plusMonths(1),
            tasks
        );
        sampleAb.addPlan(samplePlan);
        Plan samplePlan2 = new Plan(
            new PlanName("Graph Algorithms"),
            new PlanDescription("CS3230 Graph Algorithms"),
            LocalDate.now(),
            LocalDate.now().plusMonths(1),
            tasks
        );
        sampleAb.addPlan(samplePlan2);

        sampleAb.getGuiState()
            .getTabManager()
            .addDetailsTabData(
                new TabData(ModelType.PROBLEM, sampleAb.getProblemList().get(0).getId())
            );

        return sampleAb;
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        return Arrays.stream(strings)
            .map(Tag::new)
            .collect(Collectors.toSet());
    }

}
