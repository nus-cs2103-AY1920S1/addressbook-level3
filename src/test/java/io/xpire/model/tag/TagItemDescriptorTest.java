package io.xpire.model.tag;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TagItemDescriptorTest {

    private TagItemDescriptor tagItemDescriptor;
    private TagItemDescriptor copy;
    private Set<Tag> tagSet = new HashSet<>();

    @BeforeEach
    public void initialise() {
        tagItemDescriptor = new TagItemDescriptor();
        tagSet.add(new Tag("Tag1"));
        tagSet.add(new Tag("Tag2"));
        tagItemDescriptor.setTags(tagSet);
        copy = new TagItemDescriptor(tagItemDescriptor);
    }

    @Test
    public void ensuresDefensiveCloning() {
        assertNotSame(this.tagItemDescriptor, this.copy);
        //different set of tags in memory
        assertNotSame(this.tagItemDescriptor.getTags(), this.copy.getTags());
        //same set in terms of content
        assertEquals(this.tagItemDescriptor.getTags(), this.copy.getTags());
    }
}
