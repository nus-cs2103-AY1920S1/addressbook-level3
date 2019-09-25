package seedu.address.model.person;

import java.util.ArrayList;

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
    public boolean addPerson(PersonDescriptor personDescriptor) {
        if (findPerson(personDescriptor.getName()) == null) {
            this.persons.add(new Person(personDescriptor));
            return true;
        }
        return false;
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
     * Finds a person with Name and returns the person.
     * @param name of the person to find
     * @return person found
     */
    public Person findPerson(Name name) {
        int i;
        for (i = 0; i < persons.size(); i++) {
            if (persons.get(i).getName().equals(name)) {
                return persons.get(i);
            }
        }
        return null;
    }

    /**
     * Finds a person with personId and returns the person.
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
     * Delete a person with personId.
     * @param personId of the person to delete
     * @return true when successfully deleted
     */
    public boolean deletePerson(PersonId personId) {
        int i;
        for (i = 0; i < persons.size(); i++) {
            if (persons.get(i).getPersonId().equals(personId)) {
                persons.remove(i);
                return true;
            }
        }
        return false;
    }

    public Person editPerson(Name name, PersonDescriptor personDescriptor){
        Person toEdit = findPerson(name);

        if(personDescriptor.getName() != null){
            toEdit.setName(personDescriptor.getName());
        }
        if(personDescriptor.getPhone() != null){
            toEdit.setPhone(personDescriptor.getPhone());
        }
        if(personDescriptor.getEmail() != null){
            toEdit.setEmail(personDescriptor.getEmail());
        }
        if(personDescriptor.getAddress() != null){
            toEdit.setAddress(personDescriptor.getAddress());
        }
        if(personDescriptor.getRemark() != null){
            toEdit.setRemark(personDescriptor.getRemark());
        }
        if(personDescriptor.getTags() != null){
            toEdit.addTags(personDescriptor.getTags());
        }

        return toEdit;
    }

}
