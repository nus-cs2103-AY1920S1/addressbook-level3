package seedu.algobase.model.util;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
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
                new Description("Given an Array of integers, "
                    + "return indices of the two numbers such that they add up to a specific target."),
                getTagSet("Array", "hash-table", "algorithm"),
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
                Source.DEFAULT_SOURCE),
            new Problem(Id.generateId(),
                new Name("Median of Two Sorted Arrays"), Author.DEFAULT_AUTHOR,
                new WebLink("https://leetcode.com/problems/median-of-two-sorted-Arrays/"),
                new Description("There are two sorted Arrays nums1 and nums2 of size m and n respectively."
                        + "Find the median of the two sorted Arrays. The overall run time complexity "
                        + "should be O(log (m+n))."
                        + "You may assume nums1 and nums2 cannot be both empty."),
                getTagSet("Array", "Binary-Search", "Divide-and-Conquer"),
                new Difficulty("4.2"),
                Remark.DEFAULT_REMARK,
                new Source("LeetCode")),
            new Problem(Id.generateId(),
                new Name("Search Insert Position"), Author.DEFAULT_AUTHOR,
                new WebLink("https://leetcode.com/problems/search-insert-position/"),
                new Description("Given a sorted Array and a target value, return the index if the target is found. "
                        + "If not, return the index where it would be if it were inserted in order."
                        + "You may assume no duplicates in the Array."),
                getTagSet("Array", "Binary-Search"),
                new Difficulty("1.2"),
                Remark.DEFAULT_REMARK,
                new Source("LeetCode")),

            new Problem(Id.generateId(),
                new Name("Jump Game II"), Author.DEFAULT_AUTHOR,
                new WebLink("https://leetcode.com/problems/jump-game-ii/"),
                new Description("Given an Array of non-negative integers, you are initially positioned at the first "
                        + "index of the Array."
                        + "Each element in the Array represents your maximum jump length at that position."
                        + "Your goal is to reach the last index in the minimum number of jumps."),
                getTagSet("Array", "Greedy"),
                new Difficulty("4.2"),
                Remark.DEFAULT_REMARK,
                new Source("LeetCode")),
            new Problem(Id.generateId(),
                new Name("Set Matrix Zeroes"), Author.DEFAULT_AUTHOR,
                new WebLink("https://leetcode.com/problems/set-matrix-zeroes/"),
                new Description("Given a m x n matrix, if an element is 0, set its entire row and column to 0. "
                        + "Do it in-place."),
                getTagSet("Array"),
                new Difficulty("3.2"),
                Remark.DEFAULT_REMARK,
                new Source("LeetCode")),
            new Problem(Id.generateId(),
                new Name("Sort Colors"), Author.DEFAULT_AUTHOR,
                new WebLink("https://leetcode.com/problems/sort-colors/"),
                new Description("Given an Array with n objects colored red, white or blue, sort them in-place so "
                        + "that objects of the same color are adjacent, with the colors in the order red, white and"
                        + " blue."),
                getTagSet("Array", "Two-Pointers", "Sort"),
                new Difficulty("3.2"),
                new Remark("You are not suppose to use the library's sort function for this problem."),
                new Source("LeetCode")),
            new Problem(Id.generateId(),
                new Name("Recover Binary Search Tree"), Author.DEFAULT_AUTHOR,
                new WebLink("https://leetcode.com/problems/recover-binary-search-tree/"),
                new Description("Two elements of a binary search tree (BST) are swapped by mistake."
                        + "Recover the tree without changing its structure."),
                getTagSet("Tree", "Depth-first-Search"),
                new Difficulty("4.2"),
                Remark.DEFAULT_REMARK,
                new Source("LeetCode")),
            new Problem(Id.generateId(),
                new Name("Freedom Trail"), Author.DEFAULT_AUTHOR,
                new WebLink("https://leetcode.com/problems/freedom-trail/"),
                new Description("In the video game Fallout 4, the quest \"Road to Freedom\" requires players "
                        + "to reach a metal dial called the \"Freedom Trail Ring\", and use the dial to spell a"
                        + " specific keyword in order to open the door."),
                getTagSet("Divide-and-Conquer", "Dynamic-Programming", "Depth-first-Search"),
                new Difficulty("4.8"),
                new Remark("Given a string ring, which represents the code engraved on the outer ring and another "
                        + "string key, which represents the keyword needs to be spelled. You need to find the minimum "
                        + "number of steps in order to spell all the characters in the keyword."),
                new Source("LeetCode")),
            new Problem(Id.generateId(),
                new Name("Couples Holding Hands"), Author.DEFAULT_AUTHOR,
                new WebLink("https://leetcode.com/problems/couples-holding-hands/"),
                new Description("N couples sit in 2N seats arranged in a row and want to hold hands. We want to know"
                        + " the minimum number of swaps so that every couple is sitting side by side. A swap consists"
                        + " of choosing any two people, then they stand up and switch seats."),
                getTagSet("Greedy", "Union-Find", "Graph"),
                new Difficulty("4.2"),
                new Remark("The couples' initial seating is given by row[i] being the value of the person who is "
                        + "initially sitting in the i-th seat."),
                new Source("LeetCode")),
            new Problem(Id.generateId(),
                new Name("Sort Items by Groups Respecting Dependencies"), Author.DEFAULT_AUTHOR,
                new WebLink("https://leetcode.com/problems/sort-items-by-groups-respecting-dependencies/"),
                new Description("There are n items each belonging to zero or one of m groups where group[i] is the "
                        + "group that the i-th item belongs to and it's equal to -1 if the i-th item belongs to no "
                        + "group. The items and the groups are zero indexed. A group can have no item "
                        + "belonging to it."),
                getTagSet("Depth-first-Search", "Graph", "Topological-Search"),
                new Difficulty("3.2"),
                new Remark("Return a sorted list of the items such that:"
                        + "The items that belong to the same group are next to each other in the sorted list."
                        + "There are some relations between these items where beforeItems[i] is a list containing all"
                        + " the items that should come before the i-th item in the sorted Array (to the left of "
                        + "the i-th item)."),
                new Source("LeetCode")),
            new Problem(Id.generateId(),
                new Name("Is Graph Bipartite"), Author.DEFAULT_AUTHOR,
                new WebLink("https://leetcode.com/problems/is-graph-bipartite/"),
                new Description("Given an undirected graph, return true if and only if it is bipartite."
                        + "Recall that a graph is bipartite if we can split it's set of nodes into two independent "
                        + "subsets A and B such that every edge in the graph has one node in A and another node in B."
                        + "The graph is given in the following form: graph[i] is a list of indexes j for which the "
                        + "edge between nodes i and j exists."),
                getTagSet("Depth-first-Search", "Breadth-first-search", "Graph"),
                new Difficulty("3.2"),
                new Remark("There are no self edges or parallel edges: graph[i] does not contain i, "
                        + "and it doesn't contain any element twice."),
                new Source("LeetCode")),
            new Problem(Id.generateId(),
                new Name("Reconstruct Itinerary"), Author.DEFAULT_AUTHOR,
                new WebLink("https://leetcode.com/problems/reconstruct-itinerary/"),
                new Description("Given a list of airline tickets represented by pairs of departure and arrival "
                        + "airports [from, to], reconstruct the itinerary in order. All of the tickets "
                        + "belong to a man who departs from JFK. Thus, the itinerary must begin with JFK."),
                getTagSet("Depth-first-Search", "Graph"),
                new Difficulty("3.2"),
                Remark.DEFAULT_REMARK,
                new Source("LeetCode")),
            new Problem(Id.generateId(),
                new Name("Redundant Connection II"), Author.DEFAULT_AUTHOR,
                new WebLink("https://leetcode.com/problems/redundant-connection-ii/"),
                new Description("In this problem, a rooted tree is a directed graph such that, "
                        + "there is exactly one node (the root) for which all other nodes are descendants of "
                        + "this node, plus every node has exactly one parent, except for the root node "
                        + "which has no parents."),
                getTagSet("Tree", "Depth-first-Search", "Union-Find", "Graph"),
                new Difficulty("3.2"),
                new Remark("The given input is a directed graph that started as a rooted tree "
                        + "with N nodes (with distinct values 1, 2, ..., N), with one additional "
                        + "directed edge added. The added edge has two different vertices chosen from 1 to N, "
                        + "and was not an edge that already existed."),
                new Source("LeetCode"))
        };
    }

    private static List<Tag> getSampleTags() {
        List<Tag> tags = new ArrayList<>();
        tags.add(new Tag("algorithm"));
        tags.add(new Tag("Array"));
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
            new NameContainsKeywordsPredicate(Collections.singletonList(new Keyword("Sequences"))),
            null, null, null, null, null));
        rules.add(new ProblemSearchRule(
            new seedu.algobase.model.searchrule.problemsearchrule.Name("Two Sum"),
            new NameContainsKeywordsPredicate(Arrays.asList(new Keyword("two"), new Keyword("SUM"))),
            null,
            new DescriptionContainsKeywordsPredicate(Collections.singletonList(new Keyword("indices"))),
            new SourceMatchesKeywordPredicate(new Keyword("LeetCode")),
            new DifficultyIsInRangePredicate(0.5, 1.5),
            new TagIncludesKeywordsPredicate(Collections.singletonList(new Keyword("hash-table")))
        ));
        rules.add(new ProblemSearchRule(
            new seedu.algobase.model.searchrule.problemsearchrule.Name("Problems by Wee Han"),
            null, new AuthorMatchesKeywordPredicate(new Keyword("Wee Han")),
            null, null, null, null));
        return rules;
    }

    public static ReadOnlyAlgoBase getSampleAlgoBase() {
        AlgoBase sampleAb = new AlgoBase();
        Set<Task> tasks1 = new HashSet<>();
        Set<Task> tasks2 = new HashSet<>();
        sampleAb.setTags(getSampleTags());
        for (Problem sampleProblem : getSampleProblems()) {
            boolean filter = false;
            sampleAb.addProblem(sampleProblem);
            for (Tag tag : sampleProblem.getTags()) {
                if (tag.getName().equals("Graph")) {
                    filter = true;
                    break;
                }
            }
            if (!filter) {
                tasks1.add(new Task(sampleProblem, LocalDate.now().plusMonths(1), false));
            } else {
                tasks2.add(new Task(sampleProblem, LocalDate.now().plusMonths(1), false));
            }
        }
        for (ProblemSearchRule rule : getSampleFindRules()) {
            sampleAb.addFindRule(rule);
        }
        Plan samplePlan = new Plan(
            new PlanName("Data Structures"),
            new PlanDescription("CS2040 Data Structures"),
            LocalDate.now(),
            LocalDate.now().plusMonths(1),
            tasks1
        );
        sampleAb.addPlan(samplePlan);
        Plan samplePlan2 = new Plan(
            new PlanName("Graph Algorithms"),
            new PlanDescription("CS3230 Graph Algorithms"),
            LocalDate.now(),
            LocalDate.now().plusMonths(1),
            tasks2
        );
        sampleAb.addPlan(samplePlan2);

        sampleAb.getGuiState()
            .getTabManager()
            .openDetailsTab(
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
