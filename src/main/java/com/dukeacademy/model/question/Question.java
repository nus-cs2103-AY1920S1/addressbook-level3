package com.dukeacademy.model.question;

import static com.dukeacademy.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import com.dukeacademy.model.question.entities.Difficulty;
import com.dukeacademy.model.question.entities.Status;
import com.dukeacademy.model.question.entities.TestCase;
import com.dukeacademy.model.question.entities.Topic;

/**
 * Represents a Question in the question bank. Each newly created question is tagged with a UUID. This UUID is not
 * saved to storage and is not exposed to external classes. However, it is used to determine the equality of questions.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Question {
    private static final String TITLE_VALIDATION_REGEX = "[^\\s].*";
    private static int questionCount = 1;

    private final int id;
    private final String title;
    private final Status status;
    private final Difficulty difficulty;
    private final Set<Topic> topics = new HashSet<>();
    private final List<TestCase> testCases = new ArrayList<>();
    private final UserProgram userProgram;
    private final String description;
    private final boolean isBookmarked;


    /**
     * Every field must be present and not null.
     *
     * @param id         the id
     * @param title        the title
     * @param status       the status
     * @param difficulty   the difficulty
     * @param topics       the topics
     * @param testCases    the test cases
     * @param userProgram  the user program
     * @param isBookmarked the bookmark flag
     * @param description  the description
     */
    private Question(int id, String title, Status status, Difficulty difficulty, Set<Topic> topics,
                    List<TestCase> testCases, UserProgram userProgram,
                    boolean isBookmarked, String description) {
        requireAllNonNull(title, status, difficulty, topics, testCases, userProgram);
        if (!Question.checkValidTitle(title)) {
            throw new IllegalArgumentException();
        }

        this.id = id;
        this.title = title;
        this.status = status;
        this.difficulty = difficulty;
        this.topics.addAll(topics);
        this.testCases.addAll(testCases);
        this.description = description;
        this.userProgram = new UserProgram(userProgram.getCanonicalName(), userProgram.getSourceCode());
        this.isBookmarked = isBookmarked;
    }


    /**
     * Every field must be present and not null.
     *
     * @param title        the title
     * @param status       the status
     * @param difficulty   the difficulty
     * @param topics       the topics
     * @param testCases    the test cases
     * @param userProgram  the user program
     * @param isBookmarked the is bookmarked
     * @param description  the description
     */
    public Question(String title, Status status, Difficulty difficulty, Set<Topic> topics,
                    List<TestCase> testCases, UserProgram userProgram,
                    boolean isBookmarked, String description) {
        requireAllNonNull(title, status, difficulty, topics, testCases,
            userProgram, description);
        if (!Question.checkValidTitle(title)) {
            throw new IllegalArgumentException();
        }

        this.id = questionCount++;
        this.title = title;
        this.status = status;
        this.difficulty = difficulty;
        this.topics.addAll(topics);
        this.testCases.addAll(testCases);
        this.description = description;
        this.userProgram = new UserProgram(userProgram.getCanonicalName(), userProgram.getSourceCode());
        this.isBookmarked = isBookmarked;
    }

    public int getId() {
        return this.id;
    }

    /**
     * Gets title.
     *
     * @return the title
     */
    public String getTitle() {
        return this.title;
    }

    /**
     * Gets status.
     *
     * @return the status
     */
    public Status getStatus() {
        return status;
    }

    /**
     * Gets difficulty.
     *
     * @return the difficulty
     */
    public Difficulty getDifficulty() {
        return this.difficulty;
    }

    /**
     * Gets topic.
     *
     * @return the topic
     */
    public Set<Topic> getTopics() {
        return Collections.unmodifiableSet(this.topics);
    }

    /**
     * Gets question description.
     *
     * @return the description
     */
    public String getDescription() {
        return description;
    }


    /**
     * Returns the file path which stores the user program currently attempted
     * by the user.
     * If not attempted, the file path is an empty string.
     *
     * @return the userProgram
     */
    public UserProgram getUserProgram() {
        return new UserProgram(this.userProgram.getCanonicalName(), this.userProgram.getSourceCode());
    }

    /**
     * Returns the test cases of the question.
     *
     * @return the testcases
     */
    public List<TestCase> getTestCases() {
        return new ArrayList<>(this.testCases);
    }

    /**
     * Is bookmarked boolean.
     *
     * @return the boolean
     */
    public boolean isBookmarked() {
        return isBookmarked;
    }

    /**
     * Creates a new instance of the same question with a new isBookmarked attribute.
     * This new instance has the same uuid as the previous instance.
     * @param isBookmarked the isBookmarked to be updated to.
     * @return a new instance of the question.
     */
    public Question withNewIsBookmarked(boolean isBookmarked) {
        return new Question(this.id, this.title, this.status, this.difficulty, this.topics,
                this.testCases, this.userProgram, isBookmarked, this.description);
    }

    /**
     * Creates a new instance of the same question with a new status. This new instance has the same uuid as the
     * previous instance.
     *
     * @param status the status to be updated to.
     * @return a new instance of the question.
     */
    public Question withNewStatus(Status status) {
        return new Question(this.id, this.title, status, this.difficulty, this.topics,
                this.testCases, this.userProgram, this.isBookmarked, this.description);
    }

    /**
     * Creates a new instance of the same question with a new user program. This new instance has the same uuid as the
     * previous instance.
     *
     * @param userProgram the user program to be updated to.
     * @return a new instance of the question.
     */
    public Question withNewUserProgram(UserProgram userProgram) {
        return new Question(this.id, this.title, this.status, this.difficulty, this.topics,
                this.testCases, userProgram, this.isBookmarked, this.description);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getTitle())
                .append(" Status: ")
                .append(getStatus())
                .append(" Difficulty: ")
                .append(getDifficulty())
                .append(" Topics: ")
                .append(isBookmarked());
        this.getTopics().forEach(builder::append);
        return builder.toString();
    }

    /**
     * Checks if the given string is a valid title for a question. Titles must be alphanumeric.
     *
     * @param title the string to be checked.
     * @return true if the string is a valid title.
     */
    public static boolean checkValidTitle(String title) {
        return title.matches(TITLE_VALIDATION_REGEX);
    }

    /**
     * Checks if the contents of the questions are equal. The UUID of each question is disregarded.
     *
     * @param other the other question to be checked against.
     * @return true if the contents are equal.
     */
    public boolean checkContentsEqual(Question other) {
        return other.getTitle().equals(this.title)
                && other.getStatus().equals(this.status)
                && other.getDifficulty().equals(this.difficulty)
                && other.getTopics().equals(this.topics)
                && other.getTestCases().equals(this.testCases)
                && other.getUserProgram().equals(this.userProgram)
                && other.isBookmarked() == this.isBookmarked();
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Question) {
            return this.id == ((Question) o).id;
        }

        return false;
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(title, topics, status, difficulty, description);
    }

}
