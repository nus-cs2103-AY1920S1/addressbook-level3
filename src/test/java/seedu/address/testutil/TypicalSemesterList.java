package seedu.address.testutil;

import static seedu.address.testutil.TypicalSemester.EMPTY_SEMESTER_1;
import static seedu.address.testutil.TypicalSemester.EMPTY_SEMESTER_2;
import static seedu.address.testutil.TypicalSemester.EMPTY_SEMESTER_3;
import static seedu.address.testutil.TypicalSemester.EMPTY_SEMESTER_4;
import static seedu.address.testutil.TypicalSemester.EMPTY_SEMESTER_5;
import static seedu.address.testutil.TypicalSemester.EMPTY_SEMESTER_6;
import static seedu.address.testutil.TypicalSemester.EMPTY_SEMESTER_7;
import static seedu.address.testutil.TypicalSemester.EMPTY_SEMESTER_8;
import static seedu.address.testutil.TypicalSemester.FULL_UNBLOCKED_SEMESTER_1;
import static seedu.address.testutil.TypicalSemester.FULL_UNBLOCKED_SEMESTER_1_WITH_CS1101S;
import static seedu.address.testutil.TypicalSemester.FULL_UNBLOCKED_SEMESTER_2;

import seedu.address.model.semester.UniqueSemesterList;

/**
 * A utility class containing a list of {@code UniqueSemesterList} objects to be used in tests.
 */
public class TypicalSemesterList {
    public static final UniqueSemesterList TYPICAL_SEMESTER_LIST = getTypicalUniqueSemesterList1();
    public static final UniqueSemesterList TYPICAL_SEMESTER_LIST_WITH_CS1101S =
            getTypicalUniqueSemesterListWithCs1101s();
    public static final UniqueSemesterList EMPTY_SEMESTER_LIST = getEmptySemesters();

    private static UniqueSemesterList getEmptySemesters() {
        UniqueSemesterList uniqueSemesterList = new UniqueSemesterList();
        uniqueSemesterList.add(EMPTY_SEMESTER_1);
        uniqueSemesterList.add(EMPTY_SEMESTER_2);
        uniqueSemesterList.add(EMPTY_SEMESTER_3);
        uniqueSemesterList.add(EMPTY_SEMESTER_4);
        uniqueSemesterList.add(EMPTY_SEMESTER_5);
        uniqueSemesterList.add(EMPTY_SEMESTER_6);
        uniqueSemesterList.add(EMPTY_SEMESTER_7);
        uniqueSemesterList.add(EMPTY_SEMESTER_8);
        return uniqueSemesterList;
    }

    private static UniqueSemesterList getTypicalUniqueSemesterList1() {
        UniqueSemesterList uniqueSemesterList = new UniqueSemesterList();
        uniqueSemesterList.add(FULL_UNBLOCKED_SEMESTER_1);
        uniqueSemesterList.add(FULL_UNBLOCKED_SEMESTER_2);
        uniqueSemesterList.add(EMPTY_SEMESTER_3);
        uniqueSemesterList.add(EMPTY_SEMESTER_4);
        uniqueSemesterList.add(EMPTY_SEMESTER_5);
        uniqueSemesterList.add(EMPTY_SEMESTER_6);
        uniqueSemesterList.add(EMPTY_SEMESTER_7);
        uniqueSemesterList.add(EMPTY_SEMESTER_8);
        return uniqueSemesterList;
    }

    private static UniqueSemesterList getTypicalUniqueSemesterListWithCs1101s() {
        UniqueSemesterList uniqueSemesterList = new UniqueSemesterList();
        uniqueSemesterList.add(FULL_UNBLOCKED_SEMESTER_1_WITH_CS1101S);
        uniqueSemesterList.add(FULL_UNBLOCKED_SEMESTER_2);
        uniqueSemesterList.add(EMPTY_SEMESTER_3);
        uniqueSemesterList.add(EMPTY_SEMESTER_4);
        uniqueSemesterList.add(EMPTY_SEMESTER_5);
        uniqueSemesterList.add(EMPTY_SEMESTER_6);
        uniqueSemesterList.add(EMPTY_SEMESTER_7);
        uniqueSemesterList.add(EMPTY_SEMESTER_8);
        return uniqueSemesterList;
    }
}
