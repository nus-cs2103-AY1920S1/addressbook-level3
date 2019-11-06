package seedu.address.logic.commands;

import static seedu.address.testutil.Assert.assertThrows;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.model.date.AthletickDate;

class TrainingCommandPresentTest {

    @Test
    public void constructor_nullPerson_throwsNullPointerException() {
        List<Index> indexList = new ArrayList<>();
        AthletickDate date = new AthletickDate(20, 10, 2019, 1, "October");
        assertThrows(NullPointerException.class, () -> new TrainingCommandPresent(null, indexList));
        assertThrows(NullPointerException.class, () -> new TrainingCommandPresent(date, null));
    }
}
