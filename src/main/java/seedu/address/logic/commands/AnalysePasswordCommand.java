package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STRONG;

import java.util.ArrayList;
import java.util.List;

import seedu.address.commons.core.Dictionary;
//import seedu.address.commons.exceptions.DictionaryException;
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

/**
 * Analyses passwords in the password book.
 */
public class AnalysePasswordCommand extends Command {

    public static final String COMMAND_WORD = "analyse";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + "Analyses the entire list of passwords currently stored in the existing password book in general detail."
            + "Optional Parameters: \n"
            + PREFIX_STRONG + "INDEX (must be a positive integer) \n"
            + "(Analyses the password identified by the index in greater detail.)";
    public static final String MESSAGE_INIT =
            "  ____                           ___ _____  \n"
            + " / ___|  ___  ___ _   _ _ __ ___|_ _|_   _| \n"
            + " \\___ \\ / _ \\/ __| | | | '__/ _ \\| |  | |   \n"
            + "  ___) |  __/ (__| |_| | | |  __/| |  | |   \n"
            + " |____/ \\___|\\___|\\__,_|_|  \\___|___| |_|   \n"
            + "                                            \n"
            + "---- Password analysis ----\n"
            + "\n";
    public static final String DICTIONARY_PASSWORD = "passwords.txt";

    public AnalysePasswordCommand() {
    }

    @Override
    public CommandResult execute(Model model) throws CommandException, DictionaryException {
        requireNonNull(model);
        List<Password> passwordList = model.getFilteredPasswordList();
        List<Analyser> analyserList = getRequiredAnalysers();
        StringBuilder reportBuilder = new StringBuilder();
        reportBuilder.append(MESSAGE_INIT);
        for (Analyser analyser : analyserList) {
            analyser.analyse(passwordList);
            String report = analyser.outputSummaryReport();
            reportBuilder.append(report);
        }
        System.out.println(reportBuilder.toString());
        return new CommandResult("Details are shown in CLI!");
    }

    private List<Analyser> getRequiredAnalysers() throws DictionaryException {
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
