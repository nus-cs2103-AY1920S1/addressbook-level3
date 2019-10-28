package com.dukeacademy.model.util;

import com.dukeacademy.model.question.Question;
import com.dukeacademy.model.question.QuestionBank;
import com.dukeacademy.model.question.QuestionBuilder;
import com.dukeacademy.model.question.StandardQuestionBank;
import com.dukeacademy.model.question.UserProgram;
import com.dukeacademy.model.question.entities.Difficulty;
import com.dukeacademy.model.question.entities.Status;
import com.dukeacademy.model.question.entities.TestCase;
import com.dukeacademy.model.question.entities.Topic;

/**
 * Contains utility methods for populating {@code QuestionBank} with sample data.
 */
public class SampleDataUtil {
    private static final Question TWO_NUMBER_ADDER = new QuestionBuilder()
            .withTitle("Two Number Adder")
            .withStatus(Status.PASSED)
            .withDifficulty(Difficulty.EASY)
            .withIsBookmarked(false)
            .withTopics(Topic.OTHERS)
            .withTestCases(new TestCase("1 2", "3"), new TestCase("100 2", "102"))
            .withUserProgram(new UserProgram("Adder",
                    "public class Adder { public static void main(String[] args) { } }"))
            .withDescription(String.format("Given an array of integers, return "
                + "indices of the two numbers such that they add up to a specific"
                + " target.\n\nYou may assume that each input would have exactly"
                + " one solution, and you may not use the same element twice.\n\n"
                + "Example:\n\nGiven nums = [2, 7, 11, 15], target = 9,\n\n"
                + "Because nums[0] + nums[1] = 2 + 7 = 9,\nreturn [0, 1]."))
            .build();

    private static final Question VALID_SUDOKU = new QuestionBuilder()
            .withTitle("Valid Sudoku")
            .withStatus(Status.PASSED)
            .withDifficulty(Difficulty.MEDIUM)
            .withIsBookmarked(true)
            .withTopics(Topic.OTHERS, Topic.ARRAY, Topic.DYNAMIC_PROGRAMMING)
            .withTestCases(new TestCase("1 2 3 4 5 6 7 8 9", "True"),
                    new TestCase("-1 -1 -1 -1", "False"))
            .withUserProgram(new UserProgram("Sudoku",
                    "public class Sudoku { public static void main(String[] args) { } }"))
            .withDescription(String.format("Given a sorted array and a target "
                + "value, return the index if the target is found. If not, return "
                + "the index where it would be if it were inserted in order.\n\n"
                + "You may assume no duplicates in the array.\n\nExample 1:\n\n"
                + "Input: [1,3,5,6], 5\nOutput: 2\nExample 2:\n\nInput: [1,3,5,6], "
                + "2\nOutput: 1\nExample 3:\n\nInput: [1,3,5,6], 7\nOutput: 4\n"
                + "Example 4:\n\nInput: [1,3,5,6], 0\nOutput: 0"))
            .build();

    private static final Question PALINDROME_NUMBER = new QuestionBuilder()
            .withTitle("Palindrome Number")
            .withStatus(Status.PASSED)
            .withDifficulty(Difficulty.EASY)
            .withIsBookmarked(true)
            .withTopics(Topic.OTHERS, Topic.RECURSION)
            .withTestCases(new TestCase("12321", "True"),
                    new TestCase("10111", "False"))
            .withUserProgram(new UserProgram("Palindrome",
                    "public class Palindrome { public static void main(String[] args) { } }"))
            .withDescription(String.format("Given a linked list, determine if it "
                + "has a cycle in it.\n\nTo represent a cycle in the given linked "
                + "list, we use an integer pos which represents the position (0-indexed) "
                + "in the linked list where tail connects to. If pos is -1, then "
                + "there is no cycle in the linked list.\n\n \n\nExample 1:\n\nInput: "
                + "head = [3,2,0,-4], pos = 1\nOutput: true\nExplanation: There "
                + "is a cycle in the linked list, where tail connects to the second "
                + "node.\n\n\nExample 2:\n\nInput: head = [1,2], pos = 0\nOutput: "
                + "true\nExplanation: There is a cycle in the linked list, where "
                + "tail connects to the first node.\n\n\nExample 3:\n\nInput: "
                + "head = [1], pos = -1\nOutput: false\nExplanation: There is no "
                + "cycle in the linked list."))
            .build();

    private static final Question SWAP_NODES_IN_PAIRS = new QuestionBuilder()
            .withTitle("Swap nodes in pairs")
            .withStatus(Status.NEW)
            .withDifficulty(Difficulty.MEDIUM)
            .withIsBookmarked(false)
            .withTopics(Topic.GRAPH, Topic.DYNAMIC_PROGRAMMING)
            .withTestCases(new TestCase("5 12 33 23", "4132"),
                    new TestCase("2 31 01", "1232"))
            .withUserProgram(new UserProgram("NodePairs", ""))
            .withDescription(String.format("Write an algorithm to determine if a "
                + "number is \"happy\".\n\nA happy number is a number defined by "
                + "the following process: Starting with any positive integer, "
                + "replace the number by the sum of the squares of its digits, "
                + "and repeat the process until the number equals 1 (where it will "
                + "stay), or it loops endlessly in a cycle which does not include "
                + "1. Those numbers for which this process ends in 1 are happy "
                + "numbers.\n\nExample: \n\nInput: 19\nOutput: true\nExplanation: "
                + "\n12 + 92 = 82\n82 + 22 = 68\n62 + 82 = 100\n12 + 02 + 02 = 1"))
            .build();

    private static final Question MERGE_K_SORTED_LISTS = new QuestionBuilder()
            .withTitle("Merge k Sorted Lists")
            .withStatus(Status.ATTEMPTED)
            .withDifficulty(Difficulty.HARD)
            .withIsBookmarked(false)
            .withTopics(Topic.SORTING, Topic.LINKED_LIST)
            .withTestCases(new TestCase("3 5 3 4 1 99 0 21 34 123 3 21 3 1", "0 1 3 4 21 34 99 123"),
                    new TestCase("1 4 1 2 3 4", "1 2 3 4"))
            .withUserProgram(new UserProgram("Merge",
                    "public class Merge { public static void main(String[] args) { } }"))
            .withDescription(String.format("Given an array A of positive lengths, "
                + "return the largest perimeter of a triangle with non-zero area, "
                + "formed from 3 of these lengths.\n\nIf it is impossible to form "
                + "any triangle of non-zero area, return 0.\n\n \n\nExample 1:\n\n"
                + "Input: [2,1,2]\nOutput: 5\nExample 2:\n\nInput: [1,2,1]\nOutput: "
                + "0\nExample 3:\n\nInput: [3,2,3,4]\nOutput: 10\nExample 4:\n\nInput: "
                + "[3,6,2,3]\nOutput: 8\n \n\nNote:\n\n3 <= A.length <= 10000\n1 <= "
                + "A[i] <= 10^6"))
            .build();

    /**
     * Get sample questions question [ ].
     *
     * @return the question [ ]
     */
    private static Question[] getSampleQuestions() {
        return new Question[]{ TWO_NUMBER_ADDER, VALID_SUDOKU, PALINDROME_NUMBER,
            SWAP_NODES_IN_PAIRS, MERGE_K_SORTED_LISTS };
    }

    /**
     * Gets sample question bank.
     *
     * @return the sample question bank
     */
    public static QuestionBank getSampleQuestionBank() {
        StandardQuestionBank sampleQb = new StandardQuestionBank();
        for (Question sampleQuestion : getSampleQuestions()) {
            sampleQb.addQuestion(sampleQuestion);
        }
        return sampleQb;
    }
}
