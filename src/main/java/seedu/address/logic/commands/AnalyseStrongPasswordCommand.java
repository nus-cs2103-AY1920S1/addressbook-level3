package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.DictionaryException;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.password.Password;
import seedu.address.model.password.analyser.Analyser;
import seedu.address.model.password.analyser.report.AnalysisReport;
import seedu.address.model.password.analyser.result.Result;

/**
 * Analyses a specific password in password book as given by the index.
 */
public class AnalyseStrongPasswordCommand extends AnalysePasswordCommand {
    private Index index;

    public AnalyseStrongPasswordCommand(Index index) {
        super();
        this.index = index;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException, DictionaryException {
        requireNonNull(model);
        List<Password> passwordList = model.getFilteredPasswordList();
        if (index.getZeroBased() >= passwordList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PASSWORD_DISPLAYED_INDEX);
        }
        AnalysisReport analysisReport = new AnalysisReport();
        analysisReport.writePassword(passwordList.get(index.getZeroBased()));
        List<Analyser> analyserList = super.getRequiredAnalysers();
        for (Analyser analyser : analyserList) {
            analysisReport.writeHeading(analyser.getHeader());
            List<Result> results = analyser.analyse(passwordList);
            analysisReport.write(results.get(index.getZeroBased()));
        }
        return CommandResult.builder(MESSAGE_SUCCESS)
                .read()
                .setObject(analysisReport)
                .build();
    }

}
