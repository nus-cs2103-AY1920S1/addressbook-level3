package com.dukeacademy.testutil;

import java.util.ArrayList;
import java.util.List;

import com.dukeacademy.model.question.Question;
import com.dukeacademy.model.question.QuestionBuilder;
import com.dukeacademy.model.question.StandardQuestionBank;
import com.dukeacademy.model.question.UserProgram;
import com.dukeacademy.model.question.entities.Difficulty;
import com.dukeacademy.model.question.entities.Status;
import com.dukeacademy.model.question.entities.TestCase;
import com.dukeacademy.model.question.entities.Topic;

/**
 * A utility class containing a list of {@code Question} objects to be used in tests.
 */
public class TypicalQuestions {

    private static final Question TWO_NUMBER_ADDER = new QuestionBuilder()
            .withTitle("Two Number Adder")
            .withStatus(Status.PASSED)
            .withDifficulty(Difficulty.EASY)
            .withTopics(Topic.OTHERS)
            .withTestCases(new TestCase("1 2", "3"), new TestCase("100 2", "102"))
            .withUserProgram(new UserProgram("Adder",
                    "public class Adder { public static void main(String[] args) { } }"))
            .build();

    private static final Question VALID_SUDOKU = new QuestionBuilder()
            .withTitle("Valid Sudoku")
            .withStatus(Status.PASSED)
            .withDifficulty(Difficulty.MEDIUM)
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
            .withTopics(Topic.GRAPH, Topic.DYNAMIC_PROGRAMMING)
            .withTestCases(new TestCase("5 12 33 23", "4132"),
                    new TestCase("2 31 01", "1232"))
            .withUserProgram(new UserProgram("NodePairs", ""))
            .build();

    private static final Question MERGE_K_SORTED_LISTS = new QuestionBuilder()
            .withTitle("Merge k Sorted Lists")
            .withStatus(Status.ATTEMPTED)
            .withDifficulty(Difficulty.HARD)
            .withTopics(Topic.SORTING, Topic.LINKED_LIST)
            .withTestCases(new TestCase("3 5 3 4 1 99 0 21 34 123 3 21 3 1",
                    "0 1 3 4 21 34 99 123"), new TestCase("1 4 1 2 3 4", "1 2 3 4"))
            .withUserProgram(new UserProgram("Merge",
                    "public class Merge { public static void main(String[] args) { } }"))
            .build();

    private TypicalQuestions() {} // prevents instantiation

    /**
     * Returns an {@code QuestionBank} with all the typical persons.
     */
    public static StandardQuestionBank getTypicalQuestionBank() {
        StandardQuestionBank ab = new StandardQuestionBank();
        for (Question question : getTypicalQuestions()) {
            ab.addQuestion(question);
        }
        return ab;
    }

    public static List<Question> getTypicalQuestions() {
        List<Question> questions = new ArrayList<>();
        questions.add(TWO_NUMBER_ADDER);
        questions.add(VALID_SUDOKU);
        questions.add(PALINDROME_NUMBER);
        questions.add(SWAP_NODES_IN_PAIRS);
        questions.add(MERGE_K_SORTED_LISTS);
        return questions;
    }
}
