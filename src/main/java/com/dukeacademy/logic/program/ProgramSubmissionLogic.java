package com.dukeacademy.logic.program;

import java.util.Optional;

import com.dukeacademy.model.program.TestResult;
import com.dukeacademy.model.question.Question;
import com.dukeacademy.model.question.UserProgram;
import com.dukeacademy.observable.Observable;
import com.dukeacademy.testexecutor.exceptions.EmptyUserProgramException;
import com.dukeacademy.testexecutor.exceptions.IncorrectCanonicalNameException;

/**
 * Logic interface to handle the submission and evaluation of user program submissions.
 */
public interface ProgramSubmissionLogic {
    /**
     * Returns an observable that gets updated whenever a new submission is received by the logic instance. The
     * listeners of the observable can then choose to process the new result accordingly.
     *
     * @return An observable of the latest results
     */
    Observable<TestResult> getTestResultObservable();

    /**
     * Returns an observable of the current question that the logic instance is handling. The observer is updated
     * whenever the logic instance handles another question. The listeners of the observable can then choose to
     * process the new result accordingly.
     *
     * @return An observable of the current question
     */
    Observable<Question> getCurrentQuestionObservable();

    /**
     * Returns the current question instance that the logic is handling.
     *
     * @return the current question being attempted
     */
    Optional<Question> getCurrentQuestion();

    /**
     * Sets the logic instance to handle another question. Subsequent user program submissions will be tested against
     * this questions.
     *
     * @param question The question to be handled
     */
    void setCurrentQuestion(Question question);

    /**
     * Submits a user program to be tested against the current question being handled by the logic instance.
     *
     * @param userProgram The user program to be submitted.
     * @return a test result if the program was successfully tested
     * @throws IncorrectCanonicalNameException the incorrect canonical name exception
     * @throws EmptyUserProgramException       the empty user program exception
     */
    Optional<TestResult> submitUserProgram(UserProgram userProgram) throws IncorrectCanonicalNameException,
            EmptyUserProgramException;

    /**
     * Sets a channel which allows the logic instance to retrieve user programs for submission.
     *
     * @param channel The channel to be set
     */
    void setUserProgramSubmissionChannel(UserProgramChannel channel);

    /**
     * Retrieves the user program from the submission channel and tests it against the current question being handled
     * by the logic instance.
     *
     * @return a test result if the program was successfully tested
     * @throws IncorrectCanonicalNameException the incorrect canonical name exception
     * @throws EmptyUserProgramException       the empty user program exception
     */
    Optional<TestResult> submitUserProgramFromSubmissionChannel() throws IncorrectCanonicalNameException,
            EmptyUserProgramException;

    /**
     * Returns the user program retrieved from the submission channel.
     *
     * @return the user program from the submission channel
     */
    UserProgram getUserProgramFromSubmissionChannel();

    /**
     * Close program submission logic manager.
     */
    void closeProgramSubmissionLogicManager();
}
