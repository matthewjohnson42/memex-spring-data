package com.matthewjohnson42.memex.data.entity.elasticsearch;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.assertj.core.api.Assertions.*;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

/**
 * Unit tests for RawTextESComposite entity
 */
class RawTextESCompositeTest {

    private RawTextESComposite entity;
    private LocalDateTime testTime;

    @BeforeEach
    void setUp() {
        entity = new RawTextESComposite();
        testTime = LocalDateTime.of(2023, 1, 15, 10, 30, 45, 123000000);
    }

    @Test
    void testDefaultConstructor() {
        RawTextESComposite newEntity = new RawTextESComposite();
        
        assertThat(newEntity.getId()).isNull();
        assertThat(newEntity.getTextContent()).isNull();
        assertThat(newEntity.getHighlights()).isNull();
        assertThat(newEntity.getCreateDateTime()).isNull();
        assertThat(newEntity.getUpdateDateTime()).isNull();
    }

    @Test
    void testCopyConstructorFromComposite() {
        // Setup original composite entity
        List<String> highlights = Arrays.asList("highlight1", "highlight2", "highlight3");
        entity.setId("test-composite-id");
        entity.setTextContent("test composite content");
        entity.setHighlights(highlights);
        entity.setCreateDateTime(testTime);
        entity.setUpdateDateTime(testTime.plusHours(1));

        // Create copy using composite copy constructor
        RawTextESComposite copiedEntity = new RawTextESComposite(entity);

        assertThat(copiedEntity.getId()).isEqualTo("test-composite-id");
        assertThat(copiedEntity.getTextContent()).isEqualTo("test composite content");
        assertThat(copiedEntity.getHighlights()).isEqualTo(highlights);
        assertThat(copiedEntity.getCreateDateTime()).isEqualTo(testTime);
        assertThat(copiedEntity.getUpdateDateTime()).isEqualTo(testTime.plusHours(1));
        
        // Verify they are different objects
        assertThat(copiedEntity).isNotSameAs(entity);
        // Verify highlights reference is the same (shallow copy)
        assertThat(copiedEntity.getHighlights()).isSameAs(entity.getHighlights());
    }

    @Test
    void testCopyConstructorFromRawTextES() {
        // Setup base RawTextES entity
        RawTextES baseEntity = new RawTextES();
        baseEntity.setId("base-es-id");
        baseEntity.setTextContent("base elasticsearch content");
        baseEntity.setCreateDateTime(testTime);
        baseEntity.setUpdateDateTime(testTime.plusMinutes(30));

        // Create composite from base entity
        RawTextESComposite compositeEntity = new RawTextESComposite(baseEntity);

        assertThat(compositeEntity.getId()).isEqualTo("base-es-id");
        assertThat(compositeEntity.getTextContent()).isEqualTo("base elasticsearch content");
        assertThat(compositeEntity.getCreateDateTime()).isEqualTo(testTime);
        assertThat(compositeEntity.getUpdateDateTime()).isEqualTo(testTime.plusMinutes(30));
        assertThat(compositeEntity.getHighlights()).isNull(); // Should be null for base conversion
        
        // Verify they are different objects
        assertThat(compositeEntity).isNotSameAs(baseEntity);
    }

    @Test
    void testSetAndGetHighlights() {
        List<String> highlights = Arrays.asList("first highlight", "second highlight");
        
        RawTextESComposite result = entity.setHighlights(highlights);
        
        assertThat(result).isSameAs(entity); // Should return this for fluent interface
        assertThat(entity.getHighlights()).isEqualTo(highlights);
    }

    @Test
    void testSetEmptyHighlights() {
        List<String> emptyHighlights = new ArrayList<>();
        
        entity.setHighlights(emptyHighlights);
        
        assertThat(entity.getHighlights()).isEqualTo(emptyHighlights);
        assertThat(entity.getHighlights()).isEmpty();
    }

    @Test
    void testSetNullHighlights() {
        entity.setHighlights(Arrays.asList("initial", "highlights"));
        entity.setHighlights(null);
        assertThat(entity.getHighlights()).isNull();
    }

    @Test
    void testFluentInterface() {
        String testId = "fluent-composite-id";
        String testContent = "fluent composite content";
        List<String> highlights = Arrays.asList("fluent", "highlights");
        LocalDateTime createTime = LocalDateTime.now();
        LocalDateTime updateTime = LocalDateTime.now().plusMinutes(5);

        entity.setId(testId);
        entity.setTextContent(testContent);
        RawTextESComposite result = entity.setHighlights(highlights);
        
        // Test inherited fluent methods
        result.setCreateDateTime(createTime)
              .setUpdateDateTime(updateTime);

        assertThat(result).isSameAs(entity);
        assertThat(entity.getId()).isEqualTo(testId);
        assertThat(entity.getTextContent()).isEqualTo(testContent);
        assertThat(entity.getHighlights()).isEqualTo(highlights);
        assertThat(entity.getCreateDateTime()).isEqualTo(createTime);
        assertThat(entity.getUpdateDateTime()).isEqualTo(updateTime);
    }

    @Test
    void testInheritanceFromRawTextES() {
        // Test that RawTextESComposite properly inherits from RawTextES
        assertThat(entity).isInstanceOf(RawTextES.class);
        assertThat(entity).isInstanceOf(com.matthewjohnson42.memex.data.entity.Entity.class);
        
        // Test inherited methods work correctly
        entity.setId("inheritance-test-id");
        entity.setTextContent("inheritance test content");
        entity.setCreateDateTime(testTime);
        entity.setUpdateDateTime(testTime.plusDays(1));
        
        assertThat(entity.getId()).isEqualTo("inheritance-test-id");
        assertThat(entity.getTextContent()).isEqualTo("inheritance test content");
        assertThat(entity.getCreateDateTime()).isEqualTo(testTime);
        assertThat(entity.getUpdateDateTime()).isEqualTo(testTime.plusDays(1));
    }

    @Test
    void testLargeHighlightsList() {
        List<String> largeHighlights = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            largeHighlights.add("highlight_" + i);
        }
        
        entity.setHighlights(largeHighlights);
        
        assertThat(entity.getHighlights()).hasSize(1000);
        assertThat(entity.getHighlights().get(0)).isEqualTo("highlight_0");
        assertThat(entity.getHighlights().get(999)).isEqualTo("highlight_999");
    }

    @Test 
    void testCopyConstructorWithNullHighlights() {
        // Setup entity with null highlights
        entity.setId("null-highlights-id");
        entity.setTextContent("content with null highlights");
        entity.setHighlights(null);
        entity.setCreateDateTime(testTime);
        
        RawTextESComposite copiedEntity = new RawTextESComposite(entity);
        
        assertThat(copiedEntity.getId()).isEqualTo("null-highlights-id");
        assertThat(copiedEntity.getTextContent()).isEqualTo("content with null highlights");
        assertThat(copiedEntity.getHighlights()).isNull();
        assertThat(copiedEntity.getCreateDateTime()).isEqualTo(testTime);
    }

    @Test
    void testHighlightsWithSpecialCharacters() {
        List<String> specialHighlights = Arrays.asList(
            "highlight with <em>HTML</em>",
            "highlight with \"quotes\"",
            "highlight with \n newlines \t tabs",
            "highlight with unicode: 中文"
        );
        
        entity.setHighlights(specialHighlights);
        
        assertThat(entity.getHighlights()).isEqualTo(specialHighlights);
        assertThat(entity.getHighlights()).hasSize(4);
    }

    @Test
    void testCopyConstructorFromRawTextESWithNulls() {
        // Test copying from RawTextES with null values
        RawTextES baseEntity = new RawTextES();
        // Leave all fields null
        
        RawTextESComposite compositeEntity = new RawTextESComposite(baseEntity);
        
        assertThat(compositeEntity.getId()).isNull();
        assertThat(compositeEntity.getTextContent()).isNull();
        assertThat(compositeEntity.getCreateDateTime()).isNull();
        assertThat(compositeEntity.getUpdateDateTime()).isNull();
        assertThat(compositeEntity.getHighlights()).isNull();
    }
}