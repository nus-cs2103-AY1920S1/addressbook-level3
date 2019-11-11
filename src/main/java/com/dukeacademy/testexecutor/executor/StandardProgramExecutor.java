package com.dukeacademy.testexecutor.executor;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.concurrent.CompletableFuture;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import com.dukeacademy.commons.core.LogsCenter;
import com.dukeacademy.testexecutor.executor.exceptions.ProgramExecutorException;
import com.dukeacademy.testexecutor.executor.exceptions.ProgramExecutorExceptionWrapper;
import com.dukeacademy.testexecutor.models.ClassFile;
import com.dukeacademy.testexecutor.models.ProgramInput;
import com.dukeacademy.testexecutor.models.ProgramOutput;

/**
 * Standard executor for Java class files used by the application. This implementation utilizes Java Standard Library's
 * Runtime class to execute Java programs on another thread. The program is executed using command line arguments.
 */
public class StandardProgramExecutor implements ProgramExecutor {
    private static final String MESSAGE_PROGRAM_EXECUTION_FAILED = "The program failed to execute : ";

    private final Runtime runtime;
    private final Logger logger = LogsCenter.getLogger(StandardProgramExecutor.class);

    public StandardProgramExecutor() {
        this.runtime = Runtime.getRuntime();
    }

    @Override
    public CompletableFuture<ProgramOutput> executeProgram(ClassFile program, ProgramInput input)
            throws ProgramExecutorException {
        Process process = this.getExecutionProcess(program);

        try {
            logger.info("Starting program execution: " + program);
            logger.info("Feeding program input: " + program);

            // Starts the program by feeding the necessary inputs
            this.feedProgramInput(process, input);

            // Converts the rest of the evaluation process into a CompletableFuture asynchronous task
            return CompletableFuture.supplyAsync(() -> {
                logger.info("Processing program output: " + program);

                try {
                    ProgramOutput output = this.getProgramOutput(process);

                    logger.info("Output received : " + program);
                    logger.info("Program successfully executed: " + program);

                    return output;
                } catch (IOException e) {
                    throw new ProgramExecutorExceptionWrapper();
                }
            }).whenCompleteAsync((programOutput, throwable) -> {
                // Closes the input and output streams and kills the evaluation process to prevent memory leaks
                this.closeStreams(process);
                process.destroy();
            });
        } catch (IOException | ProgramExecutorExceptionWrapper e) {
            throw new ProgramExecutorException(MESSAGE_PROGRAM_EXECUTION_FAILED + program);
        }
    }


    /**
     * Gets the execution process using Java's RunTime standard library.
     *
     * @param program the program to be executed as a ClassFile.
     * @return the execution process.
     * @throws ProgramExecutorException if the program fails to be executed.
     */
    private Process getExecutionProcess(ClassFile program) throws ProgramExecutorException {
        // Generate the correct command for the process
        String className = program.getCanonicalName();
        String classPath = program.getClassPath();

        try {
            return this.runtime.exec(new String[]{"java", "-cp", classPath, className});
        } catch (IOException e) {
            throw new ProgramExecutorException(MESSAGE_PROGRAM_EXECUTION_FAILED + program);
        }
    }

    /**
     * Gets the application's model of a program output from an execution process.
     *
     * @param process the executing process.
     * @return the program output of the process.
     */
    private ProgramOutput getProgramOutput(Process process) throws IOException {
        // Retrieve the error stream of the process
        InputStream err = process.getErrorStream();

        // Read any errors
        BufferedReader errorReader = new BufferedReader(new InputStreamReader(err));
        String error = errorReader.lines().collect(Collectors.joining());

        // Return errored program output if present
        if (!"".equals(error)) {
            return ProgramOutput.getErroredProgramOutput(error);
        }

        // Retrieve the output stream of the process
        InputStream stdout = process.getInputStream();

        // Read the output of the process
        BufferedReader reader = new BufferedReader(new InputStreamReader(stdout));

        char[] output = new char[500];
        int bytesRead = reader.read(output);

        // Important because bytesRead is -1 when there is no output
        if (bytesRead == -1) {
            bytesRead = 0;
        }

        String programOutput = String.copyValueOf(output, 0, bytesRead);

        return ProgramOutput.getEmptyProgramOutput().append(programOutput);
    }

    /**
     * Feeds input into a currently executing execution process.
     *
     * @param process      the process to be fed.
     * @param programInput the inputs to be fed to the program.
     * @throws ProgramExecutorException if the input fails to be fed.
     */
    private void feedProgramInput(Process process, ProgramInput programInput) throws IOException {
        // Retrieve the input stream of the process
        OutputStream stdin = process.getOutputStream();

        // Try to write the input into the process
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(stdin));
        writer.write(programInput.getInput());
        writer.flush();
        writer.close();

    }

    /***
     * Helper methods to close streams of a process.
     * @param process the process to be closed
     */
    private void closeStreams(Process process) {
        try {
            process.getOutputStream().close();
            process.getInputStream().close();
            process.getErrorStream().close();
        } catch (IOException e) {
            logger.warning("Unable to close process streams. Watch out for memory leaks.");
        }
    }
}
