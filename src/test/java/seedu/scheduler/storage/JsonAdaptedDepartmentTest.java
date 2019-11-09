package seedu.scheduler.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.scheduler.logic.commands.CommandTestUtil.VALID_DEPARTMENT_AMY;
import static seedu.scheduler.logic.commands.CommandTestUtil.VALID_DEPARTMENT_BOB;
import static seedu.scheduler.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.scheduler.commons.exceptions.IllegalValueException;
import seedu.scheduler.model.person.Department;

class JsonAdaptedDepartmentTest {

    @Test
    public void getDepartment_validDepartmentString_success() {
        JsonAdaptedDepartment departmentAmy = new JsonAdaptedDepartment(VALID_DEPARTMENT_AMY);
        JsonAdaptedDepartment departmentBob = new JsonAdaptedDepartment(VALID_DEPARTMENT_BOB);
        assertEquals(VALID_DEPARTMENT_AMY, departmentAmy.getDepartment());
        assertEquals(VALID_DEPARTMENT_BOB, departmentBob.getDepartment());
    }

    @Test
    public void getDepartment_validDepartmentObject_success() {
        Department departmentAmy = new Department(VALID_DEPARTMENT_AMY);
        Department departmentBob = new Department(VALID_DEPARTMENT_BOB);
        JsonAdaptedDepartment jsonDepartmentAmy = new JsonAdaptedDepartment(departmentAmy);
        JsonAdaptedDepartment jsonDepartmentBob = new JsonAdaptedDepartment(departmentBob);
        assertEquals(VALID_DEPARTMENT_AMY, jsonDepartmentAmy.getDepartment());
        assertEquals(VALID_DEPARTMENT_BOB, jsonDepartmentBob.getDepartment());
    }

    @Test
    public void toModelType_validDepartment_success() throws Exception {
        JsonAdaptedDepartment departmentAmy = new JsonAdaptedDepartment(VALID_DEPARTMENT_AMY);
        JsonAdaptedDepartment departmentBob = new JsonAdaptedDepartment(VALID_DEPARTMENT_BOB);
        assertEquals(new Department(VALID_DEPARTMENT_AMY), departmentAmy.toModelType());
        assertEquals(new Department(VALID_DEPARTMENT_BOB), departmentBob.toModelType());
    }

    @Test
    public void toModelType_invalidDepartment_throwsIllegalValueException() {
        JsonAdaptedDepartment department = new JsonAdaptedDepartment("    ");
        assertThrows(IllegalValueException.class, Department.MESSAGE_CONSTRAINTS, department::toModelType);
    }
}
