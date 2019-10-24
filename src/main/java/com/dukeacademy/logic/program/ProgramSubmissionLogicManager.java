package com.dukeacademy.logic.program;

import java.io.File;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import com.dukeacademy.commons.core.LogsCenter;
import com.dukeacademy.logic.program.exceptions.LogicCreationException;
import com.dukeacademy.logic.program.exceptions.NoQuestionSetException;
import com.dukeacademy.logic.program.exceptions.SubmissionChannelNotSetException;
import com.dukeacademy.logic.program.exceptions.SubmissionLogicManagerClosedException;
import com.dukeacademy.model.program.TestResult;
import com.dukeacademy.model.question.Question;
import com.dukeacademy.model.question.UserProgram;
import com.dukeacademy.model.question.entities.TestCase;
import com.dukeacademy.observable.Observable;
import com.dukeacademy.observable.StandardObservable;
import com.dukeacademy.testexecutor.TestExecutor;
import com.dukeacademy.testexecutor.compiler.StandardCompiler;
import com.dukeacademy.testexecutor.environment.CompilerEnvironment;
import com.dukeacademy.testexecutor.environment.StandardCompilerEnvironment;
import com.dukeacademy.testexecutor.exceptions.CompilerEnvironmentException;
import com.dukeacademy.testexecutor.exceptions.EmptyUserProgramException;
import com.dukeacademy.testexecutor.exceptions.IncorrectClassNameException;
import com.dukeacademy.testexecutor.exceptions.TestExecutorException;
import com.dukeacademy.testexecutor.executor.StandardProgramExecutor;

/**
 * Standard implementation of the submission logic interface that runs tests locally.
 */
public class ProgramSubmissionLogicManager implements ProgramSubmissionLogic {
    private static final String messageCreationError = "Failed to create ProgramSubmissionLogic instance.";
    private static final String messageInvalidDirectoryError = "Could not locate give directory";
    private static final String messageCouldNotClearEnvironmentWarning =
            "Warning: Could not clear compiler environment, some test files may persist.";
    private static final Logger logger = LogsCenter.getLogger(ProgramSubmissionLogicManager.class);

    private StandardObservable<TestResult> resultObservable;
    private StandardObservable<Question> currentQuestionObservable;
    private CompilerEnvironment compilerEnvironment;
    private TestExecutor testExecutor;
    private boolean isClosed;
    private UserProgramChannel submissionChannel;

    /**
     * Constructor.
     * @param outputDirectoryPath The path to the directory in which all generated Java and Class files are to be saved.
     * @throws LogicCreationException if the directory is invalid or the components of the Logic instance fails to be
     * created.
     */
    public ProgramSubmissionLogicManager(String outputDirectoryPath) throws LogicCreationException {
        if (!new File(outputDirectoryPath).isDirectory()) {
            throw new LogicCreationException(messageInvalidDirectoryError);
        }

        try {
            String compilerEnvironmentPath = Paths.get(outputDirectoryPath).resolve("temp").toString();
            this.compilerEnvironment = new StandardCompilerEnvironment(compilerEnvironmentPath);
            this.testExecutor = new TestExecutor(this.compilerEnvironment, new StandardCompiler(),
                    new StandardProgramExecutor());

            this.resultObservable = new StandardObservable<>();
            this.currentQuestionObservable = new StandardObservable<>();

            this.isClosed = false;
        } catch (CompilerEnvironmentException e) {
            throw new LogicCreationException(messageCreationError, e);
        }
    }

    /**
     * Clears any files created by the Logic instance in the output directory.
     */
    public void closeProgramSubmissionLogicManager() {
        this.verifyNotClosed();

        try {
            this.compilerEnvironment.close();
        } catch (CompilerEnvironmentException e) {
            logger.info(messageCouldNotClearEnvironmentWarning);
        } finally {
            this.resultObservable.clearListeners();
            this.isClosed = true;
        }
    }

    @Override
    public Observable<TestResult> getTestResultObservable() {
        this.verifyNotClosed();
        return this.resultObservable;
    }

    @Override
    public Observable<Question> getCurrentQuestionObservable() {
        this.verifyNotClosed();
        return this.currentQuestionObservable;
    }

    @Override
    public Optional<Question> getCurrentQuestion() {
        return this.currentQuestionObservable.getValue();
    }

    @Override
    public void setCurrentQuestion(Question question) {
        this.verifyNotClosed();
        this.currentQuestionObservable.setValue(question);
        this.resultObservable.setValue(null);
    }

    @Override
    public Optional<TestResult> submitUserProgram(UserProgram userProgram) throws IncorrectClassNameException,
            EmptyUserProgramException {
        this.verifyNotClosed();

        if (userProgram.getSourceCode().equals("")) {
            throw new EmptyUserProgramException();
        }

        try {
            List<TestCase> testCases = this.currentQuestionObservable.getValue()
                    .map(Question::getTestCases)
                    .orElseThrow(NoQuestionSetException::new);
            TestResult results = this.testExecutor.runTestCases(testCases, userProgram);
            this.resultObservable.setValue(results);
            return Optional.of(results);
        } catch (TestExecutorException e) {
            return Optional.empty();
        }

    }

    @Override
    public void setUserProgramSubmissionChannel(UserProgramChannel channel) {
        this.submissionChannel = channel;
    }

    @Override
    public Optional<TestResult> submitUserProgramFromSubmissionChannel() throws IncorrectClassNameException,
            EmptyUserProgramException {
        if (this.submissionChannel == null) {
            throw new SubmissionChannelNotSetException();
        }

        UserProgram program = this.submissionChannel.getProgram();
        return this.submitUserProgram(program);
    }

    @Override
    public UserProgram getUserProgramFromSubmissionChannel() {
        if (this.submissionChannel == null) {
            throw new SubmissionChannelNotSetException();
        }

        return this.submissionChannel.getProgram();
    }

    private void verifyNotClosed() {
        if (this.isClosed) {
            throw new SubmissionLogicManagerClosedException();
        }
    }
}
