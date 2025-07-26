package com.matthewjohnson42.memex.data.dto;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.assertj.core.api.Assertions.*;
import java.time.LocalDateTime;

/**
 * Unit tests for DtoForEntity abstract class
 */
class DtoForEntityTest {

    private TestDto dto;
    private LocalDateTime testTime;

    // Concrete implementation for testing
    private static class TestDto extends DtoForEntity<String> {
        private String id;

        public TestDto() {
            super();
        }

        public TestDto(TestDto other) {
            super(other);
            this.id = other.id;
        }

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

    @BeforeEach
    void setUp() {
        dto = new TestDto();
        testTime = LocalDateTime.of(2023, 1, 15, 10, 30, 45, 123000000);
    }

    @Test
    void testDefaultConstructor() {
        TestDto newDto = new TestDto();
        
        assertThat(newDto.getId()).isNull();
        assertThat(newDto.getCreateDateTime()).isNull();
        assertThat(newDto.getUpdateDateTime()).isNull();
    }

    @Test
    void testCopyConstructor() {
        // Setup original DTO
        dto.setId("test-dto-id");
        dto.setCreateDateTime(testTime);
        dto.setUpdateDateTime(testTime.plusHours(1));

        // Create copy using copy constructor
        TestDto copiedDto = new TestDto(dto);

        assertThat(copiedDto.getId()).isEqualTo("test-dto-id");
        assertThat(copiedDto.getCreateDateTime()).isEqualTo(testTime);
        assertThat(copiedDto.getUpdateDateTime()).isEqualTo(testTime.plusHours(1));
        assertThat(copiedDto).isNotSameAs(dto);
    }

    @Test
    void testSetAndGetId() {
        String testId = "test-dto-id-123";
        
        DtoForEntity<String> result = dto.setId(testId);
        
        assertThat(result).isSameAs(dto); // Should return this for fluent interface
        assertThat(dto.getId()).isEqualTo(testId);
    }

    @Test
    void testSetAndGetCreateDateTime() {
        DtoForEntity result = dto.setCreateDateTime(testTime);
        
        assertThat(result).isSameAs(dto); // Should return this for fluent interface
        assertThat(dto.getCreateDateTime()).isEqualTo(testTime);
    }

    @Test
    void testSetAndGetUpdateDateTime() {
        DtoForEntity result = dto.setUpdateDateTime(testTime);
        
        assertThat(result).isSameAs(dto); // Should return this for fluent interface
        assertThat(dto.getUpdateDateTime()).isEqualTo(testTime);
    }

    @Test
    void testNullCreateDateTime() {
        dto.setCreateDateTime(null);
        assertThat(dto.getCreateDateTime()).isNull();
    }

    @Test
    void testNullUpdateDateTime() {
        dto.setUpdateDateTime(null);
        assertThat(dto.getUpdateDateTime()).isNull();
    }

    @Test
    void testFluentInterface() {
        String testId = "fluent-dto-test";
        LocalDateTime createTime = LocalDateTime.now();
        LocalDateTime updateTime = LocalDateTime.now().plusMinutes(5);

        DtoForEntity<String> result = dto
            .setId(testId)
            .setCreateDateTime(createTime)
            .setUpdateDateTime(updateTime);

        assertThat(result).isSameAs(dto);
        assertThat(dto.getId()).isEqualTo(testId);
        assertThat(dto.getCreateDateTime()).isEqualTo(createTime);
        assertThat(dto.getUpdateDateTime()).isEqualTo(updateTime);
    }

    @Test
    void testCopyConstructorWithNullValues() {
        // Test copy constructor when source has null values
        TestDto sourceDto = new TestDto();
        // Leave all fields as null
        
        TestDto copiedDto = new TestDto(sourceDto);
        
        assertThat(copiedDto.getId()).isNull();
        assertThat(copiedDto.getCreateDateTime()).isNull();
        assertThat(copiedDto.getUpdateDateTime()).isNull();
    }

    @Test
    void testCopyConstructorWithPartialValues() {
        // Test copy constructor when source has some null values
        dto.setId("partial-dto-id");
        dto.setCreateDateTime(testTime);
        // updateDateTime remains null
        
        TestDto copiedDto = new TestDto(dto);
        
        assertThat(copiedDto.getId()).isEqualTo("partial-dto-id");
        assertThat(copiedDto.getCreateDateTime()).isEqualTo(testTime);
        assertThat(copiedDto.getUpdateDateTime()).isNull();
    }

    @Test
    void testSerializableInterface() {
        // Test that DtoForEntity implements Serializable
        assertThat(dto).isInstanceOf(java.io.Serializable.class);
    }
}