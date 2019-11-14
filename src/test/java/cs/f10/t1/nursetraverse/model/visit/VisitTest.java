package cs.f10.t1.nursetraverse.model.visit;

import static cs.f10.t1.nursetraverse.testutil.TypicalPatients.ALICE;
import static cs.f10.t1.nursetraverse.testutil.TypicalPatients.BENSON;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

import cs.f10.t1.nursetraverse.model.datetime.EndDateTimeTest;
import cs.f10.t1.nursetraverse.model.datetime.StartDateTimeTest;

public class VisitTest {
    @Test
    public void equals() {
        Visit visit = ALICE.getVisits().get(0);
        assertEquals(visit, visit);
        assertNotEquals(visit, ALICE);
        assertNotEquals(visit, BENSON.getVisits().get(0));
    }

    @Test
    public void getPatient() {
        Visit visit = ALICE.getVisits().get(0);
        assertEquals(visit.getPatient(), ALICE);
        assertNotEquals(visit.getPatient(), BENSON);
    }

    @Test
    public void testToString() {
        Visit visit = ALICE.getVisits().get(0);
        Visit temp = new Visit(visit.getRemark(), visit.getStartDateTime(), visit.getEndDateTime().get(),
                visit.getVisitTasks(), visit.getPatient());
        assertEquals(visit.toString(), temp.toString());
        temp = new Visit(new Remark("something completely random lmao"), visit.getStartDateTime(),
                visit.getEndDateTime().get(), visit.getVisitTasks(), visit.getPatient());
        assertNotEquals(visit.toString(), temp.toString());
        temp = new Visit(visit.getRemark(), StartDateTimeTest.VALID_START_DATE_TIME, visit.getEndDateTime().get(),
                visit.getVisitTasks(), visit.getPatient());
        assertNotEquals(visit.toString(), temp.toString());
        temp = new Visit(visit.getRemark(), visit.getStartDateTime(), EndDateTimeTest.VALID_END_DATE_TIME,
                visit.getVisitTasks(), visit.getPatient());
        assertNotEquals(visit.toString(), temp.toString());
        temp = new Visit(visit.getRemark(), visit.getStartDateTime(), visit.getEndDateTime().get(),
                BENSON.getVisits().get(0).getVisitTasks(), visit.getPatient());
        assertNotEquals(visit.toString(), temp.toString());

        //Patient is not printed out in toString, so this change won't affect
        temp = new Visit(visit.getRemark(), visit.getStartDateTime(), visit.getEndDateTime().get(),
                visit.getVisitTasks(), BENSON);
        assertEquals(visit.toString(), temp.toString());
    }
}
