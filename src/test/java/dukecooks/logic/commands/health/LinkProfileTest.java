package dukecooks.logic.commands.health;

import static dukecooks.testutil.health.TypicalRecords.HEIGHT_FIRST_HEIGHT;
import static dukecooks.testutil.health.TypicalRecords.WEIGHT_FIRST_WEIGHT;
import static dukecooks.testutil.health.TypicalRecords.getTypicalHealthRecords;
import static dukecooks.testutil.profile.TypicalProfiles.getTypicalProfiles;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.function.Predicate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import dukecooks.logic.commands.CommandTestUtil;
import dukecooks.model.Model;
import dukecooks.model.ModelManager;
import dukecooks.model.UserPrefs;
import dukecooks.model.health.components.Record;
import dukecooks.model.health.components.Type;
import dukecooks.model.profile.person.Height;
import dukecooks.model.profile.person.Person;
import dukecooks.model.profile.person.Weight;

class LinkProfileTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalHealthRecords(), new UserPrefs());
        model.setUserProfile(getTypicalProfiles());
        expectedModel = new ModelManager(getTypicalHealthRecords(), new UserPrefs());
        expectedModel.setUserProfile(getTypicalProfiles());
    }

    @Test
    void updateProfileWeight() {
        Predicate<Record> predicate = x -> x.getType().equals(Type.valueOf(CommandTestUtil.VALID_TYPE_WEIGHT));
        model.updateFilteredRecordList(predicate);

        LinkProfile.updateProfile(model, Type.valueOf(CommandTestUtil.VALID_TYPE_WEIGHT));

        // expected model
        Person person = expectedModel.getFilteredPersonList().get(0);
        Person expectedPerson = new Person(person.getName(), person.getDateOfBirth(), person.getGender(),
                person.getBloodType(), new Weight(WEIGHT_FIRST_WEIGHT), person.getHeight(),
                person.getMedicalHistories());

        assertEquals(expectedPerson, model.getFilteredPersonList().get(0));
    }

    @Test
    void updateProfileHeight() {
        Predicate<Record> predicate = x -> x.getType().equals(Type.valueOf(CommandTestUtil.VALID_TYPE_HEIGHT));
        model.updateFilteredRecordList(predicate);

        LinkProfile.updateProfile(model, Type.valueOf(CommandTestUtil.VALID_TYPE_WEIGHT));

        // expected model
        Person person = expectedModel.getFilteredPersonList().get(0);
        Person expectedPerson = new Person(person.getName(), person.getDateOfBirth(), person.getGender(),
                person.getBloodType(), person.getWeight(), new Height(HEIGHT_FIRST_HEIGHT),
                person.getMedicalHistories());

        assertEquals(expectedPerson, model.getFilteredPersonList().get(0));

    }
}
