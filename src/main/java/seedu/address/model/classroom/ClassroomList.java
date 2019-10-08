package seedu.address.model.classroom;

import java.util.ArrayList;

/*
Aggregates Classroom
 */
public class ClassroomList {
    private ArrayList<Classroom> classes;
    public ClassroomList() {
        classes = new ArrayList<>();
    }
    public Classroom getClass(int index) {
        return classes.get(index);
    }
}
