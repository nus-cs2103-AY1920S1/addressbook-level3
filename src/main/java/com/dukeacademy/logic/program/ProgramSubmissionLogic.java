package com.dukeacademy.logic.program;

import com.dukeacademy.model.program.TestResult;
import com.dukeacademy.model.question.Question;
import com.dukeacademy.model.question.UserProgram;
import com.dukeacademy.observable.Observable;

/**
 * Logic interface to handle the submission and evaluation of user program submissions.
 */
public interface ProgramSubmissionLogic {
    /**
     * Returns an observable that gets updated whenever a new submission is received by the logic instance. The
     * listeners of the observable can then choose to process the new result accordingly.
     * @return An observable of the latest results.
     */
    public Observable<TestResult> getTestResultObservable();

    /**
     * Returns an observable of the current question that the logic instance is handling. The observer is updated
     * whenever the logic instance handles another question. The listeners of the observable can then choose to
     * process the new result accordingly.
     * @return An observable of the current question.
     */
    public Observable<Question> getCurrentQuestionObservable();

    /**
     * Sets the logic instance to handle another question. Subsequent user program submissions will be tested against
     * this questions.
     * @param question The question to be handled.
     */
    public void setCurrentQuestion(Question question);

    /**
     * Submits a user program to be tested against the current question being handled by the logic instance.
     * @param userProgram The user program to be submitted.
     * @return True if the program was successfully tested.
     */
    public boolean submitUserProgram(UserProgram userProgram);

    /**
     * Sets a channel which allows the logic instance to retrieve user programs for submission.
     * @param channel The channel to be set.
     */
    public void setUserProgramSubmissionChannel(ProgramSubmissionChannel channel);

    /**
     * Retrieves a user program from the submission channel and tests it against the current question being handled
     * by the logic instance.
     * @return True if the program was successfully tested.
     */
    public boolean submitUserProgramFromSubmissionChannel();
}
