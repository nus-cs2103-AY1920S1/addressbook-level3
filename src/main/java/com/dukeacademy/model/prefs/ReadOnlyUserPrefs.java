package com.dukeacademy.model.prefs;

import java.nio.file.Path;

/**
 * Unmodifiable view of user prefs.
 */
public abstract class ReadOnlyUserPrefs {
    protected abstract Path getAppDirectoryPath();

    public Path getTestExecutorOutputPath() {
        return this.getAppDirectoryPath().resolve("tests");
    }

    public Path getQuestionBankFilePath() {
        return this.getAppDirectoryPath().resolve("questionBank.json");
    };

}
