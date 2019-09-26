package seedu.jarvis.commons.util.andor;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class AndOrTreeTest {
    @Test
    public void testIteratingJson() {
        String json = "{\"and\":[{\"or\":[\"CS2010\",\"CS2020\",\"CS2040\"]},{\"or\":[\"ESP1107\","
                + "\"ESP2107\",\"ST1232\",\"ST2131\",\"ST2132\",\"ST2334\"]},{\"or\":[\"MA1102R\","
                + "\"MA1505\",{\"and\":[\"MA1511\",\"MA1512\"]},\"MA1521\"]},{\"or\":[\"MA1101R\","
                + "\"MA1311\",\"MA1506\",\"MA1508E\"]}]}";
        try {
            AndOrTree tree = AndOrTree.buildTree("CS3244", json);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
