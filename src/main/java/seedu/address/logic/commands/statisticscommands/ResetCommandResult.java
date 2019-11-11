package seedu.address.logic.commands.statisticscommands;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Path;

import seedu.address.model.wordbankstats.WordBankStatistics;
import seedu.address.storage.Storage;

/**
 * Represents the command result returned by {@code ResetCommand}.
 * This class is needed to pass some info to the {@code LogicManager} to update storage.
 */
public class ResetCommandResult extends WordBankStatisticsCommandResult {

    public static final String MESSAGE_RESET_SUCCESS = "Word bank statistics has been reset!";

    private final WordBankStatistics resetWbStats;

    public ResetCommandResult(WordBankStatistics wbStats) {
        super(MESSAGE_RESET_SUCCESS);
        requireNonNull(wbStats);
        this.resetWbStats = wbStats;
    }

    @Override
    public void updateStorage(Storage storage) {
        try {
            storage.saveWordBankStatistics(resetWbStats, Path.of(storage.getWordBankStatisticsListFilePath().toString(),
                    resetWbStats.getWordBankName() + ".json"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
