package seedu.jarvis.commons.util.andor;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Iterator;

public class AndOrTree {
    private String name;

    public AndOrTree(String name, String jsonTree) throws IOException {
        this.name = name;

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode node = objectMapper.readTree(jsonTree);
        iterateThroughTree(node);
    }

    private void iterateThroughTree(JsonNode node) {
        if (node.isArray()) {
            Iterator<JsonNode> it = node.elements();
            int index = 1;
            while (it.hasNext()) {
                JsonNode a = it.next();
                System.out.println(a);
                iterateThroughTree(a);
                index++;
            }
        } else if (node.isObject()) {
            node.fields().forEachRemaining(entry -> {
                System.out.println(entry.getValue());
                iterateThroughTree(entry.getValue());
            });
        }
    }
}
