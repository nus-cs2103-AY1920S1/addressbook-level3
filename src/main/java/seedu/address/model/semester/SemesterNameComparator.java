package seedu.address.model.semester;

import java.util.Comparator;

public class SemesterNameComparator implements Comparator<SemesterName> {

    @Override
    public int compare(SemesterName name1, SemesterName name2) {
        return name1.getIndex() - name2.getIndex();
    }

}
