package seedu.system.logic.commands.outofsession;

import static java.util.Objects.requireNonNull;
import static seedu.system.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.ArrayList;
import java.util.Comparator;

import seedu.system.logic.commands.Command;
import seedu.system.logic.commands.CommandResult;
import seedu.system.logic.commands.CommandType;
import seedu.system.logic.commands.exceptions.InSessionCommandException;
import seedu.system.model.Model;
import seedu.system.model.person.Person;

/**
 * Ranks all the persons by their total number of wins (i.e. first place finishes) among all the competitions.
 */
public class OverallRankCommand extends Command {
    public static final String COMMAND_WORD = "overallRank";
    public static final CommandType COMMAND_TYPE = CommandType.PERSON;
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": sorts all athletes by the number of first place wins.";
    public static final String MESSAGE_SUCCESS = "Listed all athletes ranked by their total number of wins:";

    @Override
    public CommandResult execute(Model model) throws InSessionCommandException {
        requireNonNull(model);

        if (model.hasOngoingSession()) {
            throw new InSessionCommandException();
        }

        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        ArrayList<Person> personList = new ArrayList<>();
        for (Person person : model.getFilteredPersonList()) {
            personList.add(person);
        }
        personList.sort(new OverallRankComparator(model));

        String message = MESSAGE_SUCCESS + "\n";
        for (int i = 0; i < personList.size(); i++) {
            Person person = personList.get(i);
            message += (i + 1) + ". " + person.getName() + " (Wins: " + model.getTotalWins(person) + ")\n";
        }

        return new CommandResult(message, COMMAND_TYPE);
    }

    /**
     * A comparator that imposes a total ordering on a collection of persons by a person's total wins of among
     * the competitions in the model in descending order (i.e. person with highest wins comes first).
     */
    public static class OverallRankComparator implements Comparator<Person> {

        private Model model;

        public OverallRankComparator(Model model) {
            this.model = model;
        }

        @Override
        public int compare(Person p1, Person p2) {
            return model.getTotalWins(p2) - model.getTotalWins(p1);
        }
    }
}
