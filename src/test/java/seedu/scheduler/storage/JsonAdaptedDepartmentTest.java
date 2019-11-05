package seedu.scheduler.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.scheduler.logic.commands.CommandTestUtil.VALID_DEPARTMENT_AMY;

import org.junit.jupiter.api.Test;

import seedu.scheduler.model.person.Department;

class JsonAdaptedDepartmentTest {

    @Test
    public void toModelType_validSlotDetails_success() throws Exception {
        JsonAdaptedDepartment department = new JsonAdaptedDepartment(VALID_DEPARTMENT_AMY);
        assertEquals(new Department(VALID_DEPARTMENT_AMY), department.toModelType());
    }
}
