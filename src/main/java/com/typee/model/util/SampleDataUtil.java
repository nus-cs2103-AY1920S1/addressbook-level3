package com.typee.model.util;

import com.typee.model.AppointmentList;
import com.typee.model.ReadOnlyAppointmentList;
import com.typee.model.person.Name;
import com.typee.model.person.Person;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    public static Person[] getSamplePersons() {
        return new Person[] {
            new Person(new Name("Alex Yeoh")),
            new Person(new Name("Bernice Yu")),
            new Person(new Name("Charlotte Oliveiro")),
            new Person(new Name("David Li")),
            new Person(new Name("Irfan Ibrahim")),
            new Person(new Name("Roy Balakrishnan"))
        };
    }

    public static ReadOnlyAppointmentList getSampleAddressBook() {
        AppointmentList sampleAb = new AppointmentList();
        for (Person samplePerson : getSamplePersons()) {
            sampleAb.addPerson(samplePerson);
        }
        return sampleAb;
    }
}
