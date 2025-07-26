package com.matthewjohnson42.memex.data.converter;

import com.matthewjohnson42.memex.data.dto.DtoForEntity;
import com.matthewjohnson42.memex.data.entity.Entity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.assertj.core.api.Assertions.*;
import java.time.LocalDateTime;

/**
 * Unit tests for DtoEntityConverter interface - focusing on default methods
 */
class DtoEntityConverterTest {

    private TestDtoEntityConverter converter;
    private TestDto testDto;
    private TestEntity testEntity;
    private LocalDateTime testTime;

    // Test implementations for testing the interface
    private static class TestDto extends DtoForEntity<String> {
        private String id;

        @Override
        public String getId() {
            return id;
        }

        @Override
        public DtoForEntity<String> setId(String id) {
            this.id = id;
            return this;
        }
    }

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

    private static class TestDtoEntityConverter implements DtoEntityConverter<String, TestDto, TestEntity> {
        
        @Override
        public TestEntity convertDto(TestDto d) {
            TestEntity entity = new TestEntity();
            if (d != null) {
                entity.setId(d.getId());
            }
            return entity;
        }

        @Override
        public TestDto convertEntity(TestEntity e) {
            TestDto dto = new TestDto();
            if (e != null) {
                dto.setId(e.getId());
                dto.setCreateDateTime(e.getCreateDateTime());
                dto.setUpdateDateTime(e.getUpdateDateTime());
            }
            return dto;
        }
    }

    @BeforeEach
    void setUp() {
        converter = new TestDtoEntityConverter();
        testDto = new TestDto();
        testEntity = new TestEntity();
        testTime = LocalDateTime.of(2023, 1, 15, 10, 30, 45, 123000000);
    }

    @Test
    void testConvertDto() {
        testDto.setId("test-dto-id");
        
        TestEntity result = converter.convertDto(testDto);
        
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo("test-dto-id");
    }

    @Test
    void testConvertDtoWithNull() {
        TestEntity result = converter.convertDto(null);
        
        assertThat(result).isNotNull();
        assertThat(result.getId()).isNull();
    }

    @Test
    void testConvertEntity() {
        testEntity.setId("test-entity-id");
        testEntity.setCreateDateTime(testTime);
        testEntity.setUpdateDateTime(testTime.plusHours(1));
        
        TestDto result = converter.convertEntity(testEntity);
        
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo("test-entity-id");
        assertThat(result.getCreateDateTime()).isEqualTo(testTime);
        assertThat(result.getUpdateDateTime()).isEqualTo(testTime.plusHours(1));
    }

    @Test
    void testConvertEntityWithNull() {
        TestDto result = converter.convertEntity(null);
        
        assertThat(result).isNotNull();
        assertThat(result.getId()).isNull();
        assertThat(result.getCreateDateTime()).isNull();
        assertThat(result.getUpdateDateTime()).isNull();
    }

    @Test
    void testUpdateFromDtoWithNonNullId() {
        // Setup entity with initial state
        testEntity.setId("original-entity-id");
        
        // Setup DTO with different ID
        testDto.setId("updated-dto-id");
        
        TestEntity result = converter.updateFromDto(testEntity, testDto);
        
        assertThat(result).isSameAs(testEntity); // Should return same entity instance
        assertThat(result.getId()).isEqualTo("updated-dto-id"); // ID should be updated
    }

    @Test
    void testUpdateFromDtoWithNullId() {
        // Setup entity with initial state
        testEntity.setId("original-entity-id");
        
        // Setup DTO with null ID
        testDto.setId(null);
        
        TestEntity result = converter.updateFromDto(testEntity, testDto);
        
        assertThat(result).isSameAs(testEntity);
        assertThat(result.getId()).isEqualTo("original-entity-id"); // ID should remain unchanged
    }

    @Test
    void testUpdateFromEntityWithAllNonNullFields() {
        // Setup entity with all fields
        testEntity.setId("entity-id");
        testEntity.setCreateDateTime(testTime);
        testEntity.setUpdateDateTime(testTime.plusHours(2));
        
        // Setup DTO with initial state
        testDto.setId("original-dto-id");
        
        TestDto result = converter.updateFromEntity(testDto, testEntity);
        
        assertThat(result).isSameAs(testDto); // Should return same DTO instance
        assertThat(result.getId()).isEqualTo("entity-id");
        assertThat(result.getCreateDateTime()).isEqualTo(testTime);
        assertThat(result.getUpdateDateTime()).isEqualTo(testTime.plusHours(2));
    }

    @Test
    void testUpdateFromEntityWithNullId() {
        // Setup entity with null ID but other fields set
        testEntity.setId(null);
        testEntity.setCreateDateTime(testTime);
        testEntity.setUpdateDateTime(testTime.plusHours(1));
        
        // Setup DTO with initial ID
        testDto.setId("original-dto-id");
        
        TestDto result = converter.updateFromEntity(testDto, testEntity);
        
        assertThat(result).isSameAs(testDto);
        assertThat(result.getId()).isEqualTo("original-dto-id"); // ID should remain unchanged
        assertThat(result.getCreateDateTime()).isEqualTo(testTime); // DateTime should be updated
        assertThat(result.getUpdateDateTime()).isEqualTo(testTime.plusHours(1));
    }

    @Test
    void testUpdateFromEntityWithNullCreateDateTime() {
        // Setup entity with null createDateTime
        testEntity.setId("entity-id");
        testEntity.setCreateDateTime(null);
        testEntity.setUpdateDateTime(testTime);
        
        // Setup DTO with initial createDateTime
        testDto.setCreateDateTime(testTime.minusDays(1));
        
        TestDto result = converter.updateFromEntity(testDto, testEntity);
        
        assertThat(result.getId()).isEqualTo("entity-id");
        assertThat(result.getCreateDateTime()).isEqualTo(testTime.minusDays(1)); // Should remain unchanged
        assertThat(result.getUpdateDateTime()).isEqualTo(testTime);
    }

    @Test
    void testUpdateFromEntityWithNullUpdateDateTime() {
        // Setup entity with null updateDateTime
        testEntity.setId("entity-id");
        testEntity.setCreateDateTime(testTime);
        testEntity.setUpdateDateTime(null);
        
        // Setup DTO with initial updateDateTime
        testDto.setUpdateDateTime(testTime.plusDays(1));
        
        TestDto result = converter.updateFromEntity(testDto, testEntity);
        
        assertThat(result.getId()).isEqualTo("entity-id");
        assertThat(result.getCreateDateTime()).isEqualTo(testTime);
        assertThat(result.getUpdateDateTime()).isEqualTo(testTime.plusDays(1)); // Should remain unchanged
    }

    @Test
    void testUpdateFromEntityWithAllNullFields() {
        // Setup entity with all null fields
        testEntity.setId(null);
        testEntity.setCreateDateTime(null);
        testEntity.setUpdateDateTime(null);
        
        // Setup DTO with initial values
        testDto.setId("original-dto-id");
        testDto.setCreateDateTime(testTime);
        testDto.setUpdateDateTime(testTime.plusHours(1));
        
        TestDto result = converter.updateFromEntity(testDto, testEntity);
        
        // All original values should remain unchanged
        assertThat(result.getId()).isEqualTo("original-dto-id");
        assertThat(result.getCreateDateTime()).isEqualTo(testTime);
        assertThat(result.getUpdateDateTime()).isEqualTo(testTime.plusHours(1));
    }

    @Test
    void testBidirectionalConversion() {
        // Test complete round-trip conversion
        testEntity.setId("round-trip-id");
        testEntity.setCreateDateTime(testTime);
        testEntity.setUpdateDateTime(testTime.plusMinutes(30));
        
        // Entity -> DTO -> Entity
        TestDto intermediateDto = converter.convertEntity(testEntity);
        TestEntity finalEntity = converter.convertDto(intermediateDto);
        
        assertThat(finalEntity.getId()).isEqualTo("round-trip-id");
        // Note: convertDto doesn't set timestamps, that's expected behavior
    }
}