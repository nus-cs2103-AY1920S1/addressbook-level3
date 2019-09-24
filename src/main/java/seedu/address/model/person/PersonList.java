package seedu.address.model.person;

import java.util.ArrayList;

public class PersonList {
    private ArrayList<Person> persons;

    public PersonList() {
        this.persons = new ArrayList<Person>();
    }

    public boolean addPerson(Person person) {

        if(findPerson(person.getName()) == null){
            this.persons.add(person);
            return true;
        }
        return false;
    }

    public String toString() {
        int i;
        String output = "";
        for(i = 0; i < persons.size(); i++){
            output += persons.get(i).toString();
        }
        return output;
    }

    public Person findPerson(Name name){
        int i;
        for(i = 0; i < persons.size(); i++){
            if(persons.get(i).getName().equals(name)){
                return persons.get(i);
            }
        }
        return null;
    }

    public boolean deletePerson(PersonID personID){
        int i;
        for(i = 0; i < persons.size(); i++){
            if(persons.get(i).getPersonID().equals(personID)){
                persons.remove(i);
                return true;
            }
        }
        return false;
    }

}
