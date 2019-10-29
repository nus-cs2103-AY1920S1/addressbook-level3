package seedu.address.achievements.model;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.IntegerBinding;
import javafx.collections.ObservableList;
import seedu.address.address.model.person.Person;

public class StatisticsModelManager implements StatisticsModel {

    private final ObservableList<Person> filteredPersonList;

    public StatisticsModelManager(ObservableList<Person> filteredPersonList) {
        this.filteredPersonList = filteredPersonList;
        IntegerBinding sizeProperty = Bindings.size(filteredPersonList);
    }

    @Override
    public int getTotalPersons() {
        return filteredPersonList.size();
    }
}
