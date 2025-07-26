package com.matthewjohnson42.memex.data.entity.mongo;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.assertj.core.api.Assertions.*;
import java.time.LocalDateTime;

/**
 * Unit tests for RawTextMongo entity
 */
class RawTextMongoTest {

    private RawTextMongo entity;
    private LocalDateTime testTime;

    @BeforeEach
    void setUp() {
        entity = new RawTextMongo();
        testTime = LocalDateTime.of(2023, 1, 15, 10, 30, 45, 123000000);
    }

    @Test
    void testDefaultConstructor() {
        RawTextMongo newEntity = new RawTextMongo();
        
        assertThat(newEntity.getId()).isNull();
        assertThat(newEntity.getTextContent()).isNull();
        assertThat(newEntity.getCreateDateTime()).isNull();
        assertThat(newEntity.getUpdateDateTime()).isNull();
    }

    @Test
    void testCopyConstructor() {
        // Setup original entity
        entity.setId("test-id");
        entity.setTextContent("test content");
        entity.setCreateDateTime(testTime);
        entity.setUpdateDateTime(testTime.plusHours(1));

        // Create copy
        RawTextMongo copiedEntity = new RawTextMongo(entity);

        assertThat(copiedEntity.getId()).isEqualTo("test-id");
        assertThat(copiedEntity.getTextContent()).isEqualTo("test content");
        assertThat(copiedEntity.getCreateDateTime()).isEqualTo(testTime);
        assertThat(copiedEntity.getUpdateDateTime()).isEqualTo(testTime.plusHours(1));
        
        // Verify they are different objects
        assertThat(copiedEntity).isNotSameAs(entity);
    }

    @Test
    void testSetAndGetId() {
        String testId = "test-mongo-id-123";
        
        RawTextMongo result = entity.setId(testId);
        
        assertThat(result).isSameAs(entity); // Should return this for fluent interface
        assertThat(entity.getId()).isEqualTo(testId);
    }

    @Test
    void testSetAndGetTextContent() {
        String testContent = "This is test content for MongoDB storage";
        
        RawTextMongo result = entity.setTextContent(testContent);
        
        assertThat(result).isSameAs(entity); // Should return this for fluent interface
        assertThat(entity.getTextContent()).isEqualTo(testContent);
    }

    @Test
    void testSetNullId() {
        entity.setId("initial-id");
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
        String testId = "fluent-test-id";
        String testContent = "fluent test content";
        LocalDateTime createTime = LocalDateTime.now();
        LocalDateTime updateTime = LocalDateTime.now().plusMinutes(5);

        RawTextMongo result = entity
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
        // Test that RawTextMongo properly inherits from Entity
        assertThat(entity).isInstanceOf(com.matthewjohnson42.memex.data.entity.Entity.class);
        
        // Test inherited methods work correctly
        entity.setCreateDateTime(testTime);
        entity.setUpdateDateTime(testTime.plusDays(1));
        
        assertThat(entity.getCreateDateTime()).isEqualTo(testTime);
        assertThat(entity.getUpdateDateTime()).isEqualTo(testTime.plusDays(1));
    }

    @Test
    void testLongTextContent() {
        String longContent = "This is a very long text content ".repeat(100);
        
        entity.setTextContent(longContent);
        
        assertThat(entity.getTextContent()).isEqualTo(longContent);
        assertThat(entity.getTextContent().length()).isEqualTo(33 * 100); // "This is a very long text content " = 33 chars
    }

    @Test 
    void testCopyConstructorWithNullValues() {
        // Test copy constructor when source has null values
        RawTextMongo sourceEntity = new RawTextMongo();
        // Leave all fields as null
        
        RawTextMongo copiedEntity = new RawTextMongo(sourceEntity);
        
        assertThat(copiedEntity.getId()).isNull();
        assertThat(copiedEntity.getTextContent()).isNull();
        assertThat(copiedEntity.getCreateDateTime()).isNull();
        assertThat(copiedEntity.getUpdateDateTime()).isNull();
    }

    @Test
    void testCopyConstructorWithPartialValues() {
        // Test copy constructor when source has some null values
        entity.setId("partial-id");
        entity.setTextContent(null); // null content
        entity.setCreateDateTime(testTime);
        // updateDateTime remains null
        
        RawTextMongo copiedEntity = new RawTextMongo(entity);
        
        assertThat(copiedEntity.getId()).isEqualTo("partial-id");
        assertThat(copiedEntity.getTextContent()).isNull();
        assertThat(copiedEntity.getCreateDateTime()).isEqualTo(testTime);
        assertThat(copiedEntity.getUpdateDateTime()).isNull();
    }
}