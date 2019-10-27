package com.dukeacademy.model.prefs;

import java.nio.file.Path;
import java.util.logging.Logger;

import com.dukeacademy.MainApp;
import com.dukeacademy.commons.core.LogsCenter;


/**
 * Unmodifiable view of user prefs.
 */
public abstract class ReadOnlyUserPrefs {
    private static final Logger logger = LogsCenter.getLogger(MainApp.class);

    /**
     * Gets app directory path.
     *
     * @return the app directory path
     */
    protected abstract Path getAppDirectoryPath();

    /**
     * Gets test executor output path.
     *
     * @return the test executor output path
     */
    public Path getTestExecutorOutputPath() {
        return this.getAppDirectoryPath().resolve("tests");
    }

    /**
     * Gets question bank file path.
     *
     * @return the question bank file path
     */
    public Path getQuestionBankFilePath() {
        logger.info("question bank file path: " + getAppDirectoryPath()
            + "/questionBank.json");
        return this.getAppDirectoryPath().resolve("questionBank.json");
    }

}
