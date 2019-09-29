package seedu.jarvis.commons.util.andor;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import seedu.jarvis.commons.util.CourseUtil;
import seedu.jarvis.model.course.Course;

import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AndOrTreeTest {
    @Test
    public void fulfillsCondition_insufficientRequirements_returnsFalse() {
        try {
            AndOrTree tree = AndOrTree.buildTree("CS3244");
            System.out.println(tree.toString());
            Collection<Course> col = List.of(
                    CourseUtil.getCourse("CS2040"),
                    CourseUtil.getCourse("ST2334"),
                    CourseUtil.getCourse("MA1505"),
                    CourseUtil.getCourse("MA1101R")
            );
            System.out.println("fulfill: " + tree.fulfillsCondition(col));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
