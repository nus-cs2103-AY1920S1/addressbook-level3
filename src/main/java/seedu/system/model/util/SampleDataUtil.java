package seedu.system.model.util;

import seedu.system.model.Data;
import seedu.system.model.ReadOnlyData;
import seedu.system.model.competition.Competition;
import seedu.system.model.participation.Participation;
import seedu.system.model.person.CustomDate;
import seedu.system.model.person.Gender;
import seedu.system.model.person.Name;
import seedu.system.model.person.Person;

/**
 * Contains utility methods for populating {@code Data} with sample data.
 */
public class SampleDataUtil {
    public static Person[] getSamplePersons() {
        return new Person[] {
            new Person(new Name("Alex Yeoh"), new CustomDate("02/02/1995"), Gender.MALE),
            new Person(new Name("Bernice Yu"), new CustomDate("03/02/1997"), Gender.FEMALE),
            new Person(new Name("Charlotte Oliveiro"), new CustomDate("02/03/1996"), Gender.FEMALE)
        };
    }

    public static ReadOnlyData<Person> getSamplePersonData() {
        Data<Person> persons = new Data<>();
        for (Person samplePerson : getSamplePersons()) {
            persons.addUniqueElement(samplePerson);
        }
        return persons;
    }

    public static Competition[] getSampleCompetitions() {
        return new Competition[]{
            new Competition(
                new Name("NUS Powerlifting Open 2019"),
                new CustomDate("08/05/2019"),
                new CustomDate("08/09/2019"))
        };
    }

    public static ReadOnlyData<Competition> getSampleCompetitionData() {
        Data<Competition> competitions = new Data<>();
        for (Competition sampleCompetition : getSampleCompetitions()) {
            competitions.addUniqueElement(sampleCompetition);
        }
        return competitions;
    }

    /**
     * Creates sample participations by make every person  a participant to every competition
     */
    public static ReadOnlyData<Participation> getSampleParticipationData(
        ReadOnlyData<Person> persons,
        ReadOnlyData<Competition> competitions
    ) {
        Data<Participation> participations = new Data<>();
        for (Person person : persons.getListOfElements()) {
            for (Competition competition : competitions.getListOfElements()) {
                participations.addUniqueElement(new Participation(person, competition));
            }
        }
        return participations;
    }

}
