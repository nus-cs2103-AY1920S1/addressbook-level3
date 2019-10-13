package seedu.address.testutil;

import seedu.address.model.classid.ClassId;
import seedu.address.model.person.Attendance;
import seedu.address.model.person.Name;
import seedu.address.model.person.Participation;
import seedu.address.model.person.Person;
import seedu.address.model.person.Picture;
import seedu.address.model.person.Result;

/**
 * A utility class to help with building Person objects.
 */
public class PersonBuilder {

    public static final String DEFAULT_NAME = "Alice Pauline";
    public static final String DEFAULT_PICTURE = "alice.jpg";
    public static final String DEFAULT_CLASSID = "Tutorial 4";
    public static final String DEFAULT_ATTENDANCE = "10";
    public static final String DEFAULT_RESULT = "12";
    public static final String DEFAULT_PARTCIPATION = "0";


    private Name name;
    private Picture picture;
    private ClassId classid;
    private Attendance attendance;
    private Result result;
    private Participation participation;

    public PersonBuilder() {
        name = new Name(DEFAULT_NAME);
        picture = new Picture(DEFAULT_PICTURE);
        classid = new ClassId(DEFAULT_CLASSID);
        attendance = new Attendance(DEFAULT_ATTENDANCE);
        result = new Result(DEFAULT_RESULT);
        participation = new Participation(DEFAULT_PARTCIPATION);
    }

    /**
     * Initializes the PersonBuilder with the data of {@code personToCopy}.
     */
    public PersonBuilder(Person personToCopy) {
        name = personToCopy.getName();
        picture = personToCopy.getPicture();
        classid = personToCopy.getClassId();
        attendance = personToCopy.getAttendance();
        result = personToCopy.getResult();
        participation = personToCopy.getParticipation();
    }

    /**
     * Sets the {@code Name} of the {@code Person} that we are building.
     */
    public PersonBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code Person} that we are building.
     */
    public PersonBuilder withPicture(String picture) {
        this.picture = new Picture(picture);
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code Person} that we are building.
     */
    public PersonBuilder withClassId(String classId) {
        this.classid = new ClassId(classId);
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code Person} that we are building.
     */
    public PersonBuilder withAttendance(String attendance) {
        this.attendance = new Attendance(attendance);
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code Person} that we are building.
     */
    public PersonBuilder withResult(String result) {
        this.result = new Result(result);
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code Person} that we are building.
     */
    public PersonBuilder withParticipation(String participation) {
        this.participation = new Participation(participation);
        return this;
    }

    public Person build() {
        return new Person(name, picture, classid, attendance, result, participation);
    }

}
