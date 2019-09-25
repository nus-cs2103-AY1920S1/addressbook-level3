package seedu.jarvis.commons.util.andor;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class AndOrTree {
    private String name;
    private AndOrNode root;

    public AndOrTree(String name, Path file) throws IOException {
        this.name = name;
        this.root = null;
    }
}
