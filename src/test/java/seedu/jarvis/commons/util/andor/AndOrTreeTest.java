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

            AndOrTree tree2 = AndOrTree.buildTree("MA1511");
            System.out.println(tree2.toString());

            AndOrTree tree3 = AndOrTree.buildTree("CS2102");
            System.out.println(tree3.toString());

            AndOrTree tree4 = AndOrTree.buildTree("CS2030");
            System.out.println(tree4.toString());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
