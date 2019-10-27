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
            .build();


    public static Question[] getSampleQuestions() {
        return new Question[]{ TWO_NUMBER_ADDER, VALID_SUDOKU, PALINDROME_NUMBER,
            SWAP_NODES_IN_PAIRS, MERGE_K_SORTED_LISTS };
    }

    public static QuestionBank getSampleQuestionBank() {
        StandardQuestionBank sampleQb = new StandardQuestionBank();
        for (Question sampleQuestion : getSampleQuestions()) {
            sampleQb.addQuestion(sampleQuestion);
        }
        return sampleQb;
    }
}
