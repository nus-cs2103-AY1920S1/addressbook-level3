package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STRONG;

import java.util.ArrayList;
import java.util.List;

import seedu.address.commons.core.Dictionary;
import seedu.address.commons.exceptions.DictionaryException;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.password.Password;
import seedu.address.model.password.analyser.Analyser;
import seedu.address.model.password.analyser.DictionaryAnalyser;
import seedu.address.model.password.analyser.SequenceAnalyser;
import seedu.address.model.password.analyser.SimilarityAnalyser;
import seedu.address.model.password.analyser.StrengthAnalyser;
import seedu.address.model.password.analyser.UniqueAnalyser;
import seedu.address.model.password.analyser.report.AnalysisReport;
import seedu.address.model.password.analyser.result.Result;

/**
 * Analyses passwords in the password book.
 */
public class AnalysePasswordCommand extends Command {

    public static final String COMMAND_WORD = "analyse";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Analyses security of the entire list of passwords.\n"
            + "OPTIONAL Parameters: \n"
            + PREFIX_STRONG + "INDEX (Analyses the password identified by the index in greater detail.)";

    public static final String DICTIONARY_PASSWORD = "passwords.txt";


    public AnalysePasswordCommand() {
    }

    @Override
    public CommandResult execute(Model model) throws CommandException, DictionaryException {
        requireNonNull(model);
        List<Password> passwordList = model.getFilteredPasswordList();
        List<Analyser> analyserList = getRequiredAnalysers();
        AnalysisReport analysisReport = new AnalysisReport();
        for (Analyser analyser : analyserList) {
            analysisReport.writeHeading(analyser.getHeader());
            List<Result> results = analyser.analyse(passwordList);
            analysisReport.write(results);
        }
        return new CommandResult("Results shown below", analysisReport, null);
    }

    List<Analyser> getRequiredAnalysers() throws DictionaryException {
        ArrayList<Analyser> analyserList = new ArrayList<>();
        analyserList.add(new UniqueAnalyser());
        //analyserList.add(new UserAsPassAnalyser());
        analyserList.add(new StrengthAnalyser());
        analyserList.add(new SimilarityAnalyser());
        analyserList.add(new DictionaryAnalyser(Dictionary.build(DICTIONARY_PASSWORD)));
        analyserList.add(new SequenceAnalyser());

        return analyserList;
    }

}
