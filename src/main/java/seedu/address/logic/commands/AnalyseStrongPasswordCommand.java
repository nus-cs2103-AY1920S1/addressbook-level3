package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PASSWORDS;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.password.Password;
import seedu.address.model.password.analyser.Analyser;
import seedu.address.model.password.analyser.report.StrongAnalysisReport;
import seedu.address.model.password.analyser.result.Result;
import seedu.address.model.password.exceptions.DictionaryNotFoundException;


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
    public CommandResult execute(Model model) throws CommandException {
        try {
            requireNonNull(model);
            model.updateFilteredPasswordList(PREDICATE_SHOW_ALL_PASSWORDS);
            List<Password> passwordList = model.getFilteredPasswordList();
            if (index.getZeroBased() >= passwordList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_PASSWORD_DISPLAYED_INDEX);
            }
            StrongAnalysisReport analysisReport = new StrongAnalysisReport(passwordList.get(index.getZeroBased()));
            List<Analyser> analysers = super.getRequiredAnalysers();
            for (Analyser analyser : analysers) {
                List<Result> results = analyser.analyse(passwordList);
                analysisReport.write(analyser, results.get(index.getZeroBased()));
            }
            return CommandResult.builder(MESSAGE_SUCCESS)
                    .read()
                    .setObject(analysisReport)
                    .build();
        } catch (DictionaryNotFoundException e) {
            return new CommandResult(e.getMessage());
        }
    }

}
