package com.matthewjohnson42.memex.data.repository;

import com.matthewjohnson42.memex.data.entity.Entity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.assertj.core.api.Assertions.*;
import java.util.Optional;
import java.time.LocalDateTime;

/**
 * Unit tests for Repository interface - testing interface contract through implementation
 */
class RepositoryTest {

    private TestRepository repository;
    private TestEntity testEntity;

    // Test implementations for testing the interface
    private static class TestEntity extends Entity<String> {
        private String id;

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

    // Simple in-memory implementation for testing
    private static class TestRepository implements Repository<TestEntity, String> {
        private TestEntity storedEntity;

        @Override
        public TestEntity save(TestEntity e) {
            this.storedEntity = e;
            return e;
        }

        @Override
        public Optional<TestEntity> findById(String id) {
            if (storedEntity != null && id != null && id.equals(storedEntity.getId())) {
                return Optional.of(storedEntity);
            }
            return Optional.empty();
        }

        @Override
        public void deleteById(String id) {
            if (storedEntity != null && id != null && id.equals(storedEntity.getId())) {
                storedEntity = null;
            }
        }
    }

    @BeforeEach
    void setUp() {
        repository = new TestRepository();
        testEntity = new TestEntity();
        testEntity.setId("test-entity-id");
        testEntity.setCreateDateTime(LocalDateTime.now());
    }

    @Test
    void testSaveEntity() {
        TestEntity result = repository.save(testEntity);
        
        assertThat(result).isNotNull();
        assertThat(result).isSameAs(testEntity);
        assertThat(result.getId()).isEqualTo("test-entity-id");
    }

    @Test
    void testSaveNullEntity() {
        TestEntity result = repository.save(null);
        
        assertThat(result).isNull();
    }

    @Test
    void testFindByIdExistingEntity() {
        // First save the entity
        repository.save(testEntity);
        
        // Then find it
        Optional<TestEntity> result = repository.findById("test-entity-id");
        
        assertThat(result).isPresent();
        assertThat(result.get().getId()).isEqualTo("test-entity-id");
        assertThat(result.get()).isSameAs(testEntity);
    }

    @Test
    void testFindByIdNonExistentEntity() {
        Optional<TestEntity> result = repository.findById("non-existent-id");
        
        assertThat(result).isEmpty();
    }

    @Test
    void testFindByIdWithNullId() {
        repository.save(testEntity);
        
        Optional<TestEntity> result = repository.findById(null);
        
        assertThat(result).isEmpty();
    }

    @Test
    void testDeleteByIdExistingEntity() {
        // First save the entity
        repository.save(testEntity);
        
        // Verify it exists
        assertThat(repository.findById("test-entity-id")).isPresent();
        
        // Delete it
        repository.deleteById("test-entity-id");
        
        // Verify it's deleted
        assertThat(repository.findById("test-entity-id")).isEmpty();
    }

    @Test
    void testDeleteByIdNonExistentEntity() {
        // Should not throw exception when deleting non-existent entity
        assertThatCode(() -> repository.deleteById("non-existent-id"))
            .doesNotThrowAnyException();
    }

    @Test
    void testDeleteByIdWithNullId() {
        repository.save(testEntity);
        
        // Should not throw exception when deleting with null ID
        assertThatCode(() -> repository.deleteById(null))
            .doesNotThrowAnyException();
        
        // Entity should still exist
        assertThat(repository.findById("test-entity-id")).isPresent();
    }

    @Test
    void testRepositoryWorkflow() {
        // Test complete workflow: save -> find -> delete -> find
        
        // Save
        TestEntity saved = repository.save(testEntity);
        assertThat(saved).isNotNull();
        
        // Find
        Optional<TestEntity> found = repository.findById("test-entity-id");
        assertThat(found).isPresent();
        assertThat(found.get()).isSameAs(testEntity);
        
        // Delete
        repository.deleteById("test-entity-id");
        
        // Find again - should be empty
        Optional<TestEntity> foundAfterDelete = repository.findById("test-entity-id");
        assertThat(foundAfterDelete).isEmpty();
    }

    @Test
    void testSaveUpdateExistingEntity() {
        // Save initial entity
        repository.save(testEntity);
        
        // Update the entity
        testEntity.setUpdateDateTime(LocalDateTime.now().plusHours(1));
        
        // Save updated entity
        TestEntity updated = repository.save(testEntity);
        
        assertThat(updated).isSameAs(testEntity);
        assertThat(repository.findById("test-entity-id")).isPresent();
        assertThat(repository.findById("test-entity-id").get().getUpdateDateTime())
            .isEqualTo(testEntity.getUpdateDateTime());
    }

    @Test
    void testMultipleSaveOperations() {
        TestEntity entity1 = new TestEntity();
        entity1.setId("entity-1");
        
        TestEntity entity2 = new TestEntity();
        entity2.setId("entity-2");
        
        // Save first entity
        repository.save(entity1);
        assertThat(repository.findById("entity-1")).isPresent();
        
        // Save second entity (will replace first in this simple implementation)
        repository.save(entity2);
        assertThat(repository.findById("entity-2")).isPresent();
        
        // In this simple implementation, only the last saved entity exists
        assertThat(repository.findById("entity-1")).isEmpty();
    }

    @Test
    void testRepositoryInterfaceImplementation() {
        // Verify that TestRepository properly implements Repository interface
        assertThat(repository).isInstanceOf(Repository.class);
        
        // Verify interface methods are accessible by checking they exist
        assertThatCode(() -> repository.save(testEntity)).doesNotThrowAnyException();
        assertThatCode(() -> repository.findById("test-id")).doesNotThrowAnyException();
        assertThatCode(() -> repository.deleteById("test-id")).doesNotThrowAnyException();
    }
}