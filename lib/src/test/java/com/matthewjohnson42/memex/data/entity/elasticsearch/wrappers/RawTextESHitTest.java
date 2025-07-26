package com.matthewjohnson42.memex.data.entity.elasticsearch.wrappers;

import com.matthewjohnson42.memex.data.entity.elasticsearch.RawTextES;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.assertj.core.api.Assertions.*;
import java.util.Arrays;
import java.time.LocalDateTime;

/**
 * Unit tests for RawTextESHit class
 */
class RawTextESHitTest {

    private RawTextESHit hit;

    @BeforeEach
    void setUp() {
        hit = new RawTextESHit();
    }

    @Test
    void testDefaultConstructor() {
        RawTextESHit newHit = new RawTextESHit();
        
        assertThat(newHit.get_source()).isNull();
        assertThat(newHit.getHighlight()).isNull();
    }

    @Test
    void testSetAndGet_source() {
        RawTextES source = new RawTextES();
        source.setId("test-source-id");
        source.setTextContent("test source content");
        source.setCreateDateTime(LocalDateTime.now());
        
        hit.set_source(source);
        
        assertThat(hit.get_source()).isSameAs(source);
        assertThat(hit.get_source().getId()).isEqualTo("test-source-id");
        assertThat(hit.get_source().getTextContent()).isEqualTo("test source content");
    }

    @Test
    void testSetAndGetHighlight() {
        RawTextESHighlight highlight = new RawTextESHighlight();
        highlight.setTextContent(Arrays.asList("highlight1", "highlight2"));
        
        RawTextESHit result = hit.setHighlight(highlight);
        
        assertThat(result).isSameAs(hit); // Should return this for fluent interface
        assertThat(hit.getHighlight()).isSameAs(highlight);
        assertThat(hit.getHighlight().getTextContent()).hasSize(2);
    }

    @Test
    void testSetNull_source() {
        RawTextES initialSource = new RawTextES();
        hit.set_source(initialSource);
        hit.set_source(null);
        
        assertThat(hit.get_source()).isNull();
    }

    @Test
    void testSetNullHighlight() {
        RawTextESHighlight initialHighlight = new RawTextESHighlight();
        hit.setHighlight(initialHighlight);
        hit.setHighlight(null);
        
        assertThat(hit.getHighlight()).isNull();
    }

    @Test
    void testCompleteHitSetup() {
        // Setup source
        RawTextES source = new RawTextES();
        source.setId("complete-test-id");
        source.setTextContent("complete test content");
        LocalDateTime testTime = LocalDateTime.of(2023, 1, 15, 10, 30);
        source.setCreateDateTime(testTime);
        source.setUpdateDateTime(testTime.plusHours(1));
        
        // Setup highlight
        RawTextESHighlight highlight = new RawTextESHighlight();
        highlight.setTextContent(Arrays.asList("complete", "test", "highlights"));
        
        // Set both on hit
        hit.set_source(source);
        RawTextESHit result = hit.setHighlight(highlight);
        
        // Verify everything is set correctly
        assertThat(result).isSameAs(hit);
        assertThat(hit.get_source()).isSameAs(source);
        assertThat(hit.getHighlight()).isSameAs(highlight);
        assertThat(hit.get_source().getId()).isEqualTo("complete-test-id");
        assertThat(hit.get_source().getTextContent()).isEqualTo("complete test content");
        assertThat(hit.getHighlight().getTextContent()).containsExactly("complete", "test", "highlights");
    }

    @Test
    void testFluentInterface() {
        RawTextESHighlight highlight = new RawTextESHighlight();
        highlight.setTextContent(Arrays.asList("fluent", "highlight"));
        
        RawTextESHit result = hit.setHighlight(highlight);
        
        assertThat(result).isSameAs(hit);
        assertThat(hit.getHighlight()).isSameAs(highlight);
    }

    @Test
    void testEmptyHighlight() {
        RawTextESHighlight emptyHighlight = new RawTextESHighlight();
        // Don't set textContent, leaving it null
        
        hit.setHighlight(emptyHighlight);
        
        assertThat(hit.getHighlight()).isSameAs(emptyHighlight);
        assertThat(hit.getHighlight().getTextContent()).isNull();
    }

    @Test
    void testEmptySource() {
        RawTextES emptySource = new RawTextES();
        // Don't set any fields, leaving them null/default
        
        hit.set_source(emptySource);
        
        assertThat(hit.get_source()).isSameAs(emptySource);
        assertThat(hit.get_source().getId()).isNull();
        assertThat(hit.get_source().getTextContent()).isNull();
    }
}