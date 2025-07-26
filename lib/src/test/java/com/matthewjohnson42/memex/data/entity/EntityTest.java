package com.matthewjohnson42.memex.data.entity;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.assertj.core.api.Assertions.*;
import java.time.LocalDateTime;

/**
 * Unit tests for Entity abstract class
 */
class EntityTest {

    private TestEntity entity;
    private LocalDateTime testTime;

    // Concrete implementation for testing with copy constructor
    private static class TestEntity extends Entity<String> {
        private String id;

        public TestEntity() {
            super();
        }

        public TestEntity(TestEntity other) {
            super(other);
            this.id = other.id;
        }

        @Override
        public String getId() {
            return id;
        }

        @Override
        public Entity<String> setId(String id) {
            this.id = id;
            return this;
        }
    }

    @BeforeEach
    void setUp() {
        entity = new TestEntity();
        testTime = LocalDateTime.of(2023, 1, 15, 10, 30, 45, 123000000);
    }

    @Test
    void testDefaultConstructor() {
        TestEntity newEntity = new TestEntity();
        
        assertThat(newEntity.getId()).isNull();
        assertThat(newEntity.getCreateDateTime()).isNull();
        assertThat(newEntity.getUpdateDateTime()).isNull();
    }

    @Test
    void testCopyConstructor() {
        // Setup original entity
        entity.setId("test-id");
        entity.setCreateDateTime(testTime);
        entity.setUpdateDateTime(testTime.plusHours(1));

        // Create copy using copy constructor
        TestEntity copiedEntity = new TestEntity(entity);

        assertThat(copiedEntity.getId()).isEqualTo("test-id");
        assertThat(copiedEntity.getCreateDateTime()).isEqualTo(testTime);
        assertThat(copiedEntity.getUpdateDateTime()).isEqualTo(testTime.plusHours(1));
        assertThat(copiedEntity).isNotSameAs(entity);
    }

    @Test
    void testSetAndGetId() {
        String testId = "test-id-123";
        
        Entity<String> result = entity.setId(testId);
        
        assertThat(result).isSameAs(entity); // Should return this for fluent interface
        assertThat(entity.getId()).isEqualTo(testId);
    }

    @Test
    void testSetAndGetCreateDateTime() {
        Entity result = entity.setCreateDateTime(testTime);
        
        assertThat(result).isSameAs(entity); // Should return this for fluent interface
        assertThat(entity.getCreateDateTime()).isEqualTo(testTime);
    }

    @Test
    void testSetAndGetUpdateDateTime() {
        Entity result = entity.setUpdateDateTime(testTime);
        
        assertThat(result).isSameAs(entity); // Should return this for fluent interface
        assertThat(entity.getUpdateDateTime()).isEqualTo(testTime);
    }

    @Test
    void testNullCreateDateTime() {
        entity.setCreateDateTime(null);
        assertThat(entity.getCreateDateTime()).isNull();
    }

    @Test
    void testNullUpdateDateTime() {
        entity.setUpdateDateTime(null);
        assertThat(entity.getUpdateDateTime()).isNull();
    }

    @Test
    void testFluentInterface() {
        String testId = "fluent-test";
        LocalDateTime createTime = LocalDateTime.now();
        LocalDateTime updateTime = LocalDateTime.now().plusMinutes(5);

        Entity<String> result = entity
            .setId(testId)
            .setCreateDateTime(createTime)
            .setUpdateDateTime(updateTime);

        assertThat(result).isSameAs(entity);
        assertThat(entity.getId()).isEqualTo(testId);
        assertThat(entity.getCreateDateTime()).isEqualTo(createTime);
        assertThat(entity.getUpdateDateTime()).isEqualTo(updateTime);
    }
}