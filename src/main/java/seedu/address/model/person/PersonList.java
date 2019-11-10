package seedu.address.model.person;

import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.EventClashException;
import seedu.address.model.person.exceptions.EventNotFoundException;
import seedu.address.model.person.exceptions.NoPersonFieldsEditedException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.person.schedule.Event;

/**
 * List of Persons.
 */
public class PersonList {

    private User user;
    private ArrayList<Person> persons;

    public PersonList() {
        this.user = new User(PersonDescriptor.getDefaultUser());
        this.persons = new ArrayList<>();
    }

    public PersonList(User user) {
        this.user = user;
        this.persons = new ArrayList<>();
    }

    /**
     * Adds a person into the list of persons.
     *
     * @param personDescriptor to be added
     * @return return true when successfully added
     */
    public Person addPerson(PersonDescriptor personDescriptor) throws DuplicatePersonException {

        try {
            findPerson(personDescriptor.getName());
        } catch (PersonNotFoundException e) {
            if (personDescriptor.getName().equals(user.getName())) {
                throw new DuplicatePersonException(personDescriptor.getName());
            }
            Person person = new Person(personDescriptor);
            this.persons.add(person);
            return person;
        }
        throw new DuplicatePersonException(personDescriptor.getName());
    }

    public void addPerson(Person person) {
        this.persons.add(person);
    }

    /**
     * Delete a person with personId.
     *
     * @param personId of the person to delete
     */
    public void deletePerson(PersonId personId) throws PersonNotFoundException {
        int i;
        for (i = 0; i < persons.size(); i++) {
            if (persons.get(i).getPersonId().equals(personId)) {
                persons.remove(i);
                return;
            }
        }
        throw new PersonNotFoundException();
    }

    /**
     * Edits the user based on the personDescriptor.
     *
     * @param personDescriptor of user to be edited
     * @return user
     * @throws NoPersonFieldsEditedException
     */
    public User editUser(PersonDescriptor personDescriptor)
            throws NoPersonFieldsEditedException, DuplicatePersonException {

        if (!personDescriptor.isAnyFieldEdited()) {
            throw new NoPersonFieldsEditedException();
        }

        if (!personDescriptor.getName().equals(Name.emptyName())) {
            try {
                findPerson(personDescriptor.getName());
                throw new DuplicatePersonException();
            } catch (PersonNotFoundException e) {
                user.setName(personDescriptor.getName());
            }
        }
        if (!personDescriptor.getPhone().equals(Phone.emptyPhone())) {
            user.setPhone(personDescriptor.getPhone());
        }
        if (!personDescriptor.getEmail().equals(Email.emptyEmail())) {
            user.setEmail(personDescriptor.getEmail());
        }
        if (!personDescriptor.getAddress().equals(Address.emptyAddress())) {
            user.setAddress(personDescriptor.getAddress());
        }
        if (!personDescriptor.getRemark().equals(Remark.emptyRemark())) {
            user.setRemark(personDescriptor.getRemark());
        }
        if (personDescriptor.getTags() != null) {
            user.setTags(personDescriptor.getTags());
        }

        return user;
    }

    /**
     * Edits a person with given Name based on the given PersonDescriptor.
     *
     * @param name             of person to be edited
     * @param personDescriptor how the person should be edited
     * @return person
     */
    public Person editPerson(Name name, PersonDescriptor personDescriptor)
            throws PersonNotFoundException, NoPersonFieldsEditedException, DuplicatePersonException {
        Person toEdit = findPerson(name);

        if (!personDescriptor.isAnyFieldEdited()) {
            throw new NoPersonFieldsEditedException();
        }

        if (!personDescriptor.getName().equals(Name.emptyName())) {
            Name otherName = personDescriptor.getName();
            try {
                findPerson(otherName);
                throw new DuplicatePersonException();
            } catch (PersonNotFoundException e) {
                if (personDescriptor.getName().equals(user.getName())) {
                    throw new DuplicatePersonException();
                }
                toEdit.setName(personDescriptor.getName());
            }
        }
        if (!personDescriptor.getPhone().equals(Phone.emptyPhone())) {
            toEdit.setPhone(personDescriptor.getPhone());
        }
        if (!personDescriptor.getEmail().equals(Email.emptyEmail())) {
            toEdit.setEmail(personDescriptor.getEmail());
        }
        if (!personDescriptor.getAddress().equals(Address.emptyAddress())) {
            toEdit.setAddress(personDescriptor.getAddress());
        }
        if (!personDescriptor.getRemark().equals(Remark.emptyRemark())) {
            toEdit.setRemark(personDescriptor.getRemark());
        }
        if (personDescriptor.getTags() != null) {
            toEdit.setTags(personDescriptor.getTags());
        }
        return toEdit;
    }

    /**
     * Adds an event into the schedule of a person.
     *
     * @param name  of the person to add the event to
     * @param event to be added
     * @throws PersonNotFoundException when person is not found
     * @throws EventClashException     when the is a clash with the existing schedule of the person
     */
    public void addEvent(Name name, Event event)
            throws PersonNotFoundException, EventClashException {
        Person person = findPerson(name);
        person.addEvent(event);
    }

    /**
     * Deletes an event from the schedule of a person.
     *
     * @param name      of the person to delete the event from
     * @param eventName to be deleted
     * @throws PersonNotFoundException
     * @throws EventNotFoundException
     */
    public void deleteEvent(Name name, String eventName)
            throws PersonNotFoundException, EventNotFoundException {
        Person person = findPerson(name);
        person.deleteEvent(eventName);
    }

    /**
     * Finds a person with Name and returns the person.
     *
     * @param name of the person to find
     * @return person found
     */
    public Person findPerson(Name name) throws PersonNotFoundException {
        int i;
        for (i = 0; i < persons.size(); i++) {
            if (persons.get(i).getName().equals(name)) {
                return persons.get(i);
            }
        }
        throw new PersonNotFoundException(name);
    }

    /**
     * Finds a person with personId and returns the person.
     *
     * @param personId of the person to find
     * @return person found
     */
    public Person findPerson(PersonId personId) {
        int i;
        for (i = 0; i < persons.size(); i++) {
            if (persons.get(i).getPersonId().equals(personId)) {
                return persons.get(i);
            }
        }
        return null;
    }

    /**
     * Returns an unmodifiable observable list of Persons.
     *
     * @return ObservableList
     */
    public ObservableList<Person> asUnmodifiableObservableList() {
        ObservableList<Person> observableList = FXCollections.observableArrayList(persons);
        return FXCollections.unmodifiableObservableList(observableList);
    }

    public ArrayList<Person> getPersons() {
        return this.persons;
    }

    public User getUser() {
        return this.user;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof PersonList // instanceof handles nulls
                && persons.equals(((PersonList) other).getPersons()))
                && user.equals(((PersonList) other).getUser());
    }
}
