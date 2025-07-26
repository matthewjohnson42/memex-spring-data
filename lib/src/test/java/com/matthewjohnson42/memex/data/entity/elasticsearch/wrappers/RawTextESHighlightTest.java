package com.matthewjohnson42.memex.data.entity.elasticsearch.wrappers;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.assertj.core.api.Assertions.*;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

/**
 * Unit tests for RawTextESHighlight class
 */
class RawTextESHighlightTest {

    private RawTextESHighlight highlight;

    @BeforeEach
    void setUp() {
        highlight = new RawTextESHighlight();
    }

    @Test
    void testDefaultConstructor() {
        RawTextESHighlight newHighlight = new RawTextESHighlight();
        
        assertThat(newHighlight.getTextContent()).isNull();
    }

    @Test
    void testSetAndGetTextContent() {
        List<String> textContent = Arrays.asList("highlight text 1", "highlight text 2", "highlight text 3");
        
        RawTextESHighlight result = highlight.setTextContent(textContent);
        
        assertThat(result).isSameAs(highlight); // Should return this for fluent interface
        assertThat(highlight.getTextContent()).isEqualTo(textContent);
        assertThat(highlight.getTextContent()).hasSize(3);
    }

    @Test
    void testSetEmptyTextContent() {
        List<String> emptyContent = new ArrayList<>();
        
        highlight.setTextContent(emptyContent);
        
        assertThat(highlight.getTextContent()).isEqualTo(emptyContent);
        assertThat(highlight.getTextContent()).isEmpty();
    }

    @Test
    void testSetNullTextContent() {
        highlight.setTextContent(Arrays.asList("initial", "content"));
        highlight.setTextContent(null);
        
        assertThat(highlight.getTextContent()).isNull();
    }

    @Test
    void testFluentInterface() {
        List<String> textContent = Arrays.asList("fluent", "interface", "test");
        
        RawTextESHighlight result = highlight.setTextContent(textContent);
        
        assertThat(result).isSameAs(highlight);
        assertThat(highlight.getTextContent()).isEqualTo(textContent);
    }

    @Test
    void testTextContentWithSpecialCharacters() {
        List<String> specialContent = Arrays.asList(
            "Text with <em>HTML</em> tags",
            "Text with \"quotes\" and 'apostrophes'",
            "Text with\nnewlines\tand\ttabs",
            "Text with unicode: 中文 🚀"
        );
        
        highlight.setTextContent(specialContent);
        
        assertThat(highlight.getTextContent()).isEqualTo(specialContent);
        assertThat(highlight.getTextContent()).hasSize(4);
    }

    @Test
    void testLargeTextContentList() {
        List<String> largeContent = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            largeContent.add("highlight_" + i);
        }
        
        highlight.setTextContent(largeContent);
        
        assertThat(highlight.getTextContent()).hasSize(1000);
        assertThat(highlight.getTextContent().get(0)).isEqualTo("highlight_0");
        assertThat(highlight.getTextContent().get(999)).isEqualTo("highlight_999");
    }

    @Test
    void testSingleTextContentItem() {
        List<String> singleContent = Arrays.asList("single highlight item");
        
        highlight.setTextContent(singleContent);
        
        assertThat(highlight.getTextContent()).hasSize(1);
        assertThat(highlight.getTextContent().get(0)).isEqualTo("single highlight item");
    }
}