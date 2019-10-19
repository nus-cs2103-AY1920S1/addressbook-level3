package seedu.address.model.person;

import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.EventClashException;
import seedu.address.model.person.exceptions.NoPersonFieldsEditedException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.person.schedule.Event;

/**
 * List of Persons.
 */
public class PersonList {
    private ArrayList<Person> persons;

    public PersonList() {
        this.persons = new ArrayList<Person>();
    }

    /**
     * Adds a person into the list of persons.
     *
     * @param personDescriptor to be added
     * @return return true when successfully added
     */
    public Person addPerson(PersonDescriptor personDescriptor) throws DuplicatePersonException{
        try{
            findPerson(personDescriptor.getName());
        } catch (PersonNotFoundException e) {
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
     * @return true when successfully deleted
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

    public void deletePerson(Name name) throws PersonNotFoundException {
        Person person = findPerson(name);
        deletePerson(person.getPersonId());
    }

    /**
     * Edits a person with given Name based on the given PersonDescriptor.
     *
     * @param name             of person to be edited
     * @param personDescriptor how the person should be edited
     * @return person
     */
    public Person editPerson(Name name, PersonDescriptor personDescriptor) throws PersonNotFoundException, NoPersonFieldsEditedException, DuplicatePersonException {
        Person toEdit = findPerson(name);

        if(!personDescriptor.isAnyFieldEdited()){
            throw new NoPersonFieldsEditedException();
        }

        if (!personDescriptor.getName().equals(Name.emptyName())) {
            Name otherName = personDescriptor.getName();
            try{
                findPerson(otherName);
            } catch (PersonNotFoundException e) {
                throw new DuplicatePersonException();
            }
            toEdit.setName(personDescriptor.getName());
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
            toEdit.addTags(personDescriptor.getTags());
        }
        return toEdit;
    }

    public void addEvent(Name name, Event event) throws PersonNotFoundException, EventClashException {
        Person person = findPerson(name);
        person.addEvent(event);
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
     * Converts to String.
     *
     * @return String
     */
    public String toString() {
        int i;
        String output = "";
        for (i = 0; i < persons.size(); i++) {
            output += persons.get(i).toString();
            output += "\n";
        }
        return output;
    }

    /**
     * Returns an unmodifiable observable list of Persons.
     * @return ObservableList
     */
    public ObservableList<Person> asUnmodifiableObservableList() {
        ObservableList<Person> observableList = FXCollections.observableArrayList(persons);
        return FXCollections.unmodifiableObservableList(observableList);
    }

    public ArrayList<Person> getPersons() {
        return this.persons;
    }
}
