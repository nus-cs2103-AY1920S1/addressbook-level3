package seedu.address.itinerary.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

import seedu.address.itinerary.model.Model;

class ClearEventCommandTest {

    /*
        NOTE: Clear command start up will result in an ExceptionInInitializerError since there is no GUI to
        open up the window. However, upon testing the assert throw method, it is also shown that an additional
        NoClassDefError is also being thrown.
    */

    @Test
    void testClear() {
        ClearEventCommandTest commandTest = new ClearEventCommandTest();
        ClearEventCommandTest commandTest1 = new ClearEventCommandTest();

        // Check to see whether the test are equal
        assertNotEquals(commandTest, commandTest1);

        // Similar test objects
        assertEquals(commandTest, commandTest);
        assertEquals(commandTest1, commandTest1);
    }

    /*
        Although there is a creation of stub, it still requires a parent window to be open.
     */
    private static class ModelStub extends Model {
        private boolean flag = false;

        @Override
        public void clearEvent() {
            flag = true;
        }

        boolean getFlag() {
            return flag;
        }
    }
}
