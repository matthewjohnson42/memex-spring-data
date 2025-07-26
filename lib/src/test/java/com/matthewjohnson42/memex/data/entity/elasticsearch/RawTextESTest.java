package com.matthewjohnson42.memex.data.entity.elasticsearch;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.assertj.core.api.Assertions.*;
import java.time.LocalDateTime;

/**
 * Unit tests for RawTextES entity
 */
class RawTextESTest {

    private RawTextES entity;
    private LocalDateTime testTime;

    @BeforeEach
    void setUp() {
        entity = new RawTextES();
        testTime = LocalDateTime.of(2023, 1, 15, 10, 30, 45, 123000000);
    }

    @Test
    void testDefaultConstructor() {
        RawTextES newEntity = new RawTextES();
        
        assertThat(newEntity.getId()).isNull();
        assertThat(newEntity.getTextContent()).isNull();
        assertThat(newEntity.getCreateDateTime()).isNull();
        assertThat(newEntity.getUpdateDateTime()).isNull();
    }

    @Test
    void testCopyConstructor() {
        // Setup original entity
        entity.setId("test-es-id");
        entity.setTextContent("test elasticsearch content");
        entity.setCreateDateTime(testTime);
        entity.setUpdateDateTime(testTime.plusHours(2));

        // Create copy
        RawTextES copiedEntity = new RawTextES(entity);

        assertThat(copiedEntity.getId()).isEqualTo("test-es-id");
        assertThat(copiedEntity.getTextContent()).isEqualTo("test elasticsearch content");
        assertThat(copiedEntity.getCreateDateTime()).isEqualTo(testTime);
        assertThat(copiedEntity.getUpdateDateTime()).isEqualTo(testTime.plusHours(2));
        
        // Verify they are different objects
        assertThat(copiedEntity).isNotSameAs(entity);
    }

    @Test
    void testSetAndGetId() {
        String testId = "test-elasticsearch-id-456";
        
        RawTextES result = entity.setId(testId);
        
        assertThat(result).isSameAs(entity); // Should return this for fluent interface
        assertThat(entity.getId()).isEqualTo(testId);
    }

    @Test
    void testSetAndGetTextContent() {
        String testContent = "This is test content for Elasticsearch storage with special chars: @#$%";
        
        RawTextES result = entity.setTextContent(testContent);
        
        assertThat(result).isSameAs(entity); // Should return this for fluent interface
        assertThat(entity.getTextContent()).isEqualTo(testContent);
    }

    @Test
    void testSetNullId() {
        entity.setId("initial-es-id");
        entity.setId(null);
        assertThat(entity.getId()).isNull();
    }

    @Test
    void testSetNullTextContent() {
        entity.setTextContent("initial content");
        entity.setTextContent(null);
        assertThat(entity.getTextContent()).isNull();
    }

    @Test
    void testSetEmptyTextContent() {
        entity.setTextContent("");
        assertThat(entity.getTextContent()).isEqualTo("");
    }

    @Test
    void testFluentInterface() {
        String testId = "fluent-es-test-id";
        String testContent = "fluent elasticsearch test content";
        LocalDateTime createTime = LocalDateTime.now();
        LocalDateTime updateTime = LocalDateTime.now().plusMinutes(10);

        RawTextES result = entity
            .setId(testId)
            .setTextContent(testContent);
        
        // Test inherited fluent methods
        result.setCreateDateTime(createTime)
              .setUpdateDateTime(updateTime);

        assertThat(result).isSameAs(entity);
        assertThat(entity.getId()).isEqualTo(testId);
        assertThat(entity.getTextContent()).isEqualTo(testContent);
        assertThat(entity.getCreateDateTime()).isEqualTo(createTime);
        assertThat(entity.getUpdateDateTime()).isEqualTo(updateTime);
    }

    @Test
    void testInheritanceFromEntity() {
        // Test that RawTextES properly inherits from Entity
        assertThat(entity).isInstanceOf(com.matthewjohnson42.memex.data.entity.Entity.class);
        
        // Test inherited methods work correctly
        entity.setCreateDateTime(testTime);
        entity.setUpdateDateTime(testTime.plusDays(1));
        
        assertThat(entity.getCreateDateTime()).isEqualTo(testTime);
        assertThat(entity.getUpdateDateTime()).isEqualTo(testTime.plusDays(1));
    }

    @Test
    void testLongTextContent() {
        String longContent = "This is a very long elasticsearch text content ".repeat(200);
        
        entity.setTextContent(longContent);
        
        assertThat(entity.getTextContent()).isEqualTo(longContent);
        assertThat(entity.getTextContent().length()).isEqualTo(47 * 200); // "This is a very long elasticsearch text content " = 47 chars
    }

    @Test 
    void testCopyConstructorWithNullValues() {
        // Test copy constructor when source has null values
        RawTextES sourceEntity = new RawTextES();
        // Leave all fields as null
        
        RawTextES copiedEntity = new RawTextES(sourceEntity);
        
        assertThat(copiedEntity.getId()).isNull();
        assertThat(copiedEntity.getTextContent()).isNull();
        assertThat(copiedEntity.getCreateDateTime()).isNull();
        assertThat(copiedEntity.getUpdateDateTime()).isNull();
    }

    @Test
    void testCopyConstructorWithPartialValues() {
        // Test copy constructor when source has some null values
        entity.setId("partial-es-id");
        entity.setTextContent(null); // null content
        entity.setCreateDateTime(testTime);
        // updateDateTime remains null
        
        RawTextES copiedEntity = new RawTextES(entity);
        
        assertThat(copiedEntity.getId()).isEqualTo("partial-es-id");
        assertThat(copiedEntity.getTextContent()).isNull();
        assertThat(copiedEntity.getCreateDateTime()).isEqualTo(testTime);
        assertThat(copiedEntity.getUpdateDateTime()).isNull();
    }

    @Test
    void testUnicodeTextContent() {
        String unicodeContent = "Test with unicode: 中文 日本語 한국어 🚀 🎉";
        
        entity.setTextContent(unicodeContent);
        
        assertThat(entity.getTextContent()).isEqualTo(unicodeContent);
    }
}