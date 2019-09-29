package seedu.algobase.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.algobase.model.AlgoBase;
import seedu.algobase.model.ReadOnlyAlgoBase;
import seedu.algobase.model.problem.Author;
import seedu.algobase.model.problem.Description;
import seedu.algobase.model.problem.Difficulty;
import seedu.algobase.model.problem.Name;
import seedu.algobase.model.problem.Problem;
import seedu.algobase.model.problem.Remark;
import seedu.algobase.model.problem.Source;
import seedu.algobase.model.problem.WebLink;
import seedu.algobase.model.tag.Tag;

/**
 * Contains utility methods for populating {@code AlgoBase} with sample data.
 */
public class SampleDataUtil {
    public static Problem[] getSampleProblems() {
        return new Problem[] {
            new Problem(new Name("Sequences"), new Author("Tung Kam Chuen"),
                    new WebLink("https://open.kattis.com/problems/sequences"),
                    new Description("Find the sum of the number of inversions of the 2k sequences, "
                            + "modulo 1000000007(109+7)."),
                    getTagSet(),
                    Difficulty.DEFAULT_DIFFICULTY,
                    Remark.DEFAULT_REMARK,
                    new Source("Kattis")),
            new Problem(new Name("Two Sum"), Author.DEFAULT_AUTHOR,
                    new WebLink("https://leetcode.com/problems/two-sum/"),
                    new Description("Given an array of integers, "
                            + "return indices of the two numbers such that they add up to a specific target."),
                    getTagSet("array", "hash table", "algorithm"),
                    new Difficulty("1.0"),
                    new Remark("You may assume that each input would have exactly one solution, "
                            + "and you may not use the same element twice."),
                    new Source("LeetCode")),
            new Problem(new Name("Second Highest Salary"), new Author("LeetCode"),
                    new WebLink("https://leetcode.com/problems/second-highest-salary/"),
                    new Description("Write a SQL query to get the second highest salary from the Employee table."),
                    getTagSet("MySQL", "database"),
                    new Difficulty("2.0"),
                    Remark.DEFAULT_REMARK,
                    new Source("LeetCode")),
            new Problem(new Name("Sudoku Solver"), Author.DEFAULT_AUTHOR,
                    new WebLink("https://leetcode.com/problems/sudoku-solver/"),
                    Description.DEFAULT_DESCRIPTION,
                    getTagSet("hash table", "backtracking", "algorithm"),
                    new Difficulty("5.0"),
                    new Remark("You may assume that the given Sudoku puzzle will have a single unique solution."),
                    new Source("LeetCode")),
            new Problem(new Name("A. Dawid and Bags of Candies"), Author.DEFAULT_AUTHOR,
                    new WebLink("https://codeforces.com/problemset/problem/1230/A"),
                    Description.DEFAULT_DESCRIPTION,
                    getTagSet("brute force"),
                    new Difficulty("4.0"),
                    new Remark("time limit per test1 second\n" + "memory limit per test256 megabytes"),
                    new Source("CodeForce")),
            new Problem(new Name("Factorial"), new Author("Wee Han"), WebLink.DEFAULT_WEBLINK,
                    new Description("define a function factorial that takes in a number n "
                            + "and returns the factorial of the number."),
                    getTagSet("recursion"),
                    Difficulty.DEFAULT_DIFFICULTY,
                    Remark.DEFAULT_REMARK,
                    Source.DEFAULT_SOURCE)
        };
    }

    public static ReadOnlyAlgoBase getSampleAlgoBase() {
        AlgoBase sampleAb = new AlgoBase();
        for (Problem sampleProblem : getSampleProblems()) {
            sampleAb.addProblem(sampleProblem);
        }
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
