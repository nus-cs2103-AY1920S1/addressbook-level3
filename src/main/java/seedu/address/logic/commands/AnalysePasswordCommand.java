package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STRONG;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PASSWORDS;

import java.util.ArrayList;
import java.util.List;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.password.Password;
import seedu.address.model.password.analyser.Analyser;
import seedu.address.model.password.analyser.DictionaryAnalyser;
import seedu.address.model.password.analyser.KeyboardAnalyser;
import seedu.address.model.password.analyser.SequenceAnalyser;
import seedu.address.model.password.analyser.SimilarityAnalyser;
import seedu.address.model.password.analyser.StrengthAnalyser;
import seedu.address.model.password.analyser.UniqueAnalyser;
import seedu.address.model.password.analyser.report.AnalysisReport;
import seedu.address.model.password.analyser.resources.Dictionary;
import seedu.address.model.password.analyser.result.Result;
import seedu.address.model.password.exceptions.DictionaryNotFoundException;


/**
 * Analyses passwords in the password book.
 */
public class AnalysePasswordCommand extends Command {
    public static final String MESSAGE_SUCCESS = "Results are shown below";
    public static final String COMMAND_WORD = "analyse";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Analyses security of the entire list of passwords.\n"
            + "OPTIONAL Parameters: \n"
            + PREFIX_STRONG + "INDEX (Analyses the password identified by the index in greater detail.)";

    public static final String DICTIONARY_PASSWORD = "passwords.txt";


    public AnalysePasswordCommand() {
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        try {
            requireNonNull(model);
            model.updateFilteredPasswordList(PREDICATE_SHOW_ALL_PASSWORDS);
            List<Password> passwords = model.getFilteredPasswordList();
            List<Analyser> analysers = getRequiredAnalysers();
            AnalysisReport analysisReport = new AnalysisReport();
            for (Analyser analyser : analysers) {
                List<Result> results = analyser.analyse(passwords);
                analysisReport.write(analyser, results);
            }
            return CommandResult.builder(MESSAGE_SUCCESS)
                    .read()
                    .setObject(analysisReport)
                    .build();
        } catch (DictionaryNotFoundException e) {
            return new CommandResult(e.getMessage());
        }
    }

    /**
     * Creates a {@code List} of all the {@code Analyser} objects.
     *
     * @return the current list of analyser objects
     * @throws DictionaryNotFoundException if the {@code Dictionary} cannot be loaded.
     */
    List<Analyser> getRequiredAnalysers() throws DictionaryNotFoundException {
        ArrayList<Analyser> analyserList = new ArrayList<>();
        analyserList.add(new UniqueAnalyser());
        analyserList.add(new StrengthAnalyser());
        analyserList.add(new SimilarityAnalyser());
        analyserList.add(new DictionaryAnalyser(Dictionary.build(DICTIONARY_PASSWORD)));
        analyserList.add(new SequenceAnalyser());
        analyserList.add(new KeyboardAnalyser());

        return analyserList;
    }

}
