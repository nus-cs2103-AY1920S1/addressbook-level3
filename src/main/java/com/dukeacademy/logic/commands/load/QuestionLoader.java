package com.dukeacademy.logic.commands.load;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.dukeacademy.commons.core.LogsCenter;
import com.dukeacademy.commons.exceptions.IllegalValueException;
import com.dukeacademy.model.question.Question;
import com.dukeacademy.model.question.QuestionBuilder;
import com.dukeacademy.model.question.entities.Difficulty;
import com.dukeacademy.model.question.entities.Status;
import com.dukeacademy.model.question.entities.TestCase;
import com.dukeacademy.model.question.entities.Topic;

/**
 * The type Question loader.
 */
public class QuestionLoader {
    private static final Logger logger =
        LogsCenter.getLogger(LoadCommand.class);

    /**
     * Build from string list.
     *
     * @param questions the questions
     * @return the list
     * @throws IllegalValueException the illegal value exception
     */
    public static List<Question> buildFromString (String questions)
        throws IllegalValueException {
        if (questions.isEmpty()) {
            throw new IllegalValueException("The questions cannot be loaded");
        }
        String[] questionsString =
            questions.split("\\s*Question::\\s*");
        logger.info("the list size is: _" + questionsString[0] + "_");
        ArrayList<Question> sampleQuestions = new ArrayList<>();
        for (String questionString : questionsString) {
            if (questionString.isEmpty()) {
                logger.info("the string is empty~");
                continue;
            }
            QuestionBuilder questionBuilder = new QuestionBuilder();
            try {
                addTitle(questionString, questionBuilder);
                addDescription(questionString, questionBuilder);
                addDifficulty(questionString, questionBuilder);
                addTopics(questionString, questionBuilder);
                addTestCases(questionString, questionBuilder);
            } catch (IllegalValueException e) {
                throw e;
            }
            Question q = questionBuilder.withStatus(Status.NEW).build();
            sampleQuestions.add(q);
            logger.info("====a new qn created!====");
        }
        return sampleQuestions;
    }

    /**
     * Registers Test Cases specified by text file into question.
     * @param source
     * @param question
     * @throws IllegalValueException
     */
    private static void addTestCases(String source,
                                     QuestionBuilder question) throws IllegalValueException {
        Pattern testCasesPattern = Pattern.compile("TestCase::" + "(.|\\n)*",
            Pattern.DOTALL);
        Matcher matcher = testCasesPattern.matcher(source);
        if (!matcher.find()) {
            throw new IllegalValueException("No TestCase found for "
                + "question: " + question.getTitle() + "\n. TestCase should"
                + " begin with \"TestCase::\".");
        }
        String[] testCases = matcher.group().trim().split("TestCase::");
        logger.info("the testcase is: " + matcher.group().trim());

        for (String testCase: testCases) {
            if (testCase.isEmpty()) {
                continue;
            }
            logger.info("the testcase is: " + testCase);
            Pattern inputPattern =
                Pattern.compile("Input::" + "((.|\\n)*)" + "Output::");
            Pattern outputPattern = Pattern.compile("Output::((.|\\n)*)");
            Matcher inputMatcher = inputPattern.matcher(testCase);
            Matcher outputMatcher = outputPattern.matcher(testCase);
            if (!inputMatcher.find() || !outputMatcher.find()) {
                throw new IllegalValueException("Format wrong for a "
                    + "testCase from question: " + question.getTitle() + "\n. "
                    + "TestCase format should be \"Input::\" followed by "
                    + "\"Output::\".");
            }
            logger.info("the raw input is: " + inputMatcher.group());
            String input = inputMatcher.group(1).trim();
            logger.info("the input is: " + input);
            String output = outputMatcher.group(1).trim();
            logger.info("the raw output is: " + outputMatcher.group());
            logger.info("the output is: " + output);
            TestCase newTestCase = new TestCase(input, output);
            question.withTestCases(newTestCase);

        }

    }

    /**
     * Registers Topics specified by text file into question.
     * @param source
     * @param question
     * @throws IllegalValueException
     */
    private static void addTopics(String source,
                               QuestionBuilder question) throws IllegalValueException {
        Pattern topicsPattern = Pattern.compile("Topics::(.*?)"
            + "TestCase::", Pattern.DOTALL);
        Matcher matcher = topicsPattern.matcher(source);
        if (!matcher.find()) {
            throw new IllegalValueException("No Topics found for "
                + "question: " + question.getTitle() + "\n. Topics should"
                + " be before TestCases.");
        }
        String[] topics = matcher.group(1).split("\\s*,\\s*");
        for (String topic : topics) {
            topic = topic.trim();
            if (!Topic.contains(topic)) {
                throw new IllegalValueException(
                    "Illegal difficulty for " + "question: " + question.getTitle() + " with a Topic of:"
                        + " " + topic + ".\n Should be " + "ARRAY, LINKED_LIST, HASHTABLE, TREE, GRAPH, RECURSION,"
                        + " DIVIDE_AND_CONQUER, DYNAMIC_PROGRAMMING, SORTING, or "
                        + "OTHERS.");
            }
            question.withTopics(Topic.valueOf(topic));
        }
    }

    /**
     * Registers a Difficulty specified by text file into question.
     * @param source
     * @param question
     * @throws IllegalValueException
     */
    private static void addDifficulty(String source,
                                                 QuestionBuilder question) throws IllegalValueException {
        Pattern difficultyPattern = Pattern.compile("Difficulty::(.*?)"
            + "Topics::", Pattern.DOTALL);
        Matcher matcher = difficultyPattern.matcher(source);
        if (!matcher.find()) {
            throw new IllegalValueException("No Difficulty found for "
                + "question: " + question.getTitle() + "\n. Difficulty should"
                + " be before Topics.");
        }
        String difficulty = matcher.group(1).trim();
        if (Difficulty.contains(difficulty)) {
            question.withDifficulty(Difficulty.valueOf(difficulty));
        } else {
            throw new IllegalValueException("Illegal difficulty for "
                + "question: " + question.getTitle() + ".\n Should be "
                + "EASY, MEDIUM or HARD.");
        }
    }

    /**
     * Registers a Description specified by text file into question.
     * @param source
     * @param question
     * @throws IllegalValueException
     */
    private static void addDescription(String source,
                                       QuestionBuilder question) throws IllegalValueException {
        Pattern descriptionPattern = Pattern.compile("Description::(.*?)"
            + "Difficulty::", Pattern.DOTALL);
        Matcher matcher = descriptionPattern.matcher(source);
        if (!matcher.find()) {
            throw new IllegalValueException("No Description found for "
                + "question: " + question.getTitle() + "\n. Description should"
                + " be before Difficulty.");
        }
        String description = matcher.group(1).trim();
        question.withDescription(description);
    }

    /**
     * Registers a Title specified by text file into question.
     * @param source
     * @param question
     * @throws IllegalValueException
     */
    private static void addTitle(String source,
                                            QuestionBuilder question)
        throws IllegalValueException {
        Pattern titlePattern = Pattern.compile("Title::(.*?)"
            + "Description::", Pattern.DOTALL);
        Matcher matcher = titlePattern.matcher(source);
        if (!matcher.find()) {
            throw new IllegalValueException("No title found. Title should be "
                + "before Description.");
        }
        String title = matcher.group(1).trim();
        question.withTitle(title);
    }
}
