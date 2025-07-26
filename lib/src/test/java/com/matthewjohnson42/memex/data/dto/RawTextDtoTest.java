package com.matthewjohnson42.memex.data.dto;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.assertj.core.api.Assertions.*;
import java.time.LocalDateTime;

/**
 * Unit tests for RawTextDto class
 */
class RawTextDtoTest {

    private RawTextDto dto;
    private LocalDateTime testTime;

    @BeforeEach
    void setUp() {
        dto = new RawTextDto();
        testTime = LocalDateTime.of(2023, 1, 15, 10, 30, 45, 123000000);
    }

    @Test
    void testDefaultConstructor() {
        RawTextDto newDto = new RawTextDto();
        
        assertThat(newDto.getId()).isNull();
        assertThat(newDto.getTextContent()).isNull();
        assertThat(newDto.getCreateDateTime()).isNull();
        assertThat(newDto.getUpdateDateTime()).isNull();
    }

    @Test
    void testJsonCreatorConstructor() {
        String testContent = "Test content from JSON";
        
        RawTextDto newDto = new RawTextDto(testContent);
        
        assertThat(newDto.getTextContent()).isEqualTo(testContent);
        assertThat(newDto.getId()).isNull(); // ID should not be set by this constructor
        assertThat(newDto.getCreateDateTime()).isNull();
        assertThat(newDto.getUpdateDateTime()).isNull();
    }

    @Test
    void testCopyConstructor() {
        // Setup original DTO
        dto.setId("test-raw-text-id");
        dto.setTextContent("test raw text content");
        dto.setCreateDateTime(testTime);
        dto.setUpdateDateTime(testTime.plusHours(1));

        // Create copy
        RawTextDto copiedDto = new RawTextDto(dto);

        assertThat(copiedDto.getId()).isEqualTo("test-raw-text-id");
        assertThat(copiedDto.getTextContent()).isEqualTo("test raw text content");
        assertThat(copiedDto.getCreateDateTime()).isEqualTo(testTime);
        assertThat(copiedDto.getUpdateDateTime()).isEqualTo(testTime.plusHours(1));
        
        // Verify they are different objects
        assertThat(copiedDto).isNotSameAs(dto);
    }

    @Test
    void testSetAndGetId() {
        String testId = "test-raw-text-dto-id-456";
        
        RawTextDto result = dto.setId(testId);
        
        assertThat(result).isSameAs(dto); // Should return this for fluent interface
        assertThat(dto.getId()).isEqualTo(testId);
    }

    @Test
    void testSetAndGetTextContent() {
        String testContent = "This is test content for RawTextDto with special chars: @#$%^&*()";
        
        RawTextDto result = dto.setTextContent(testContent);
        
        assertThat(result).isSameAs(dto); // Should return this for fluent interface
        assertThat(dto.getTextContent()).isEqualTo(testContent);
    }

    @Test
    void testSetNullId() {
        dto.setId("initial-id");
        dto.setId(null);
        assertThat(dto.getId()).isNull();
    }

    @Test
    void testSetNullTextContent() {
        dto.setTextContent("initial content");
        dto.setTextContent(null);
        assertThat(dto.getTextContent()).isNull();
    }

    @Test
    void testSetEmptyTextContent() {
        dto.setTextContent("");
        assertThat(dto.getTextContent()).isEqualTo("");
    }

    @Test
    void testFluentInterface() {
        String testId = "fluent-raw-text-id";
        String testContent = "fluent raw text content";
        LocalDateTime createTime = LocalDateTime.now();
        LocalDateTime updateTime = LocalDateTime.now().plusMinutes(10);

        RawTextDto result = dto
            .setId(testId)
            .setTextContent(testContent);
        
        // Test inherited fluent methods
        result.setCreateDateTime(createTime)
              .setUpdateDateTime(updateTime);

        assertThat(result).isSameAs(dto);
        assertThat(dto.getId()).isEqualTo(testId);
        assertThat(dto.getTextContent()).isEqualTo(testContent);
        assertThat(dto.getCreateDateTime()).isEqualTo(createTime);
        assertThat(dto.getUpdateDateTime()).isEqualTo(updateTime);
    }

    @Test
    void testInheritanceFromDtoForEntity() {
        // Test that RawTextDto properly inherits from DtoForEntity
        assertThat(dto).isInstanceOf(DtoForEntity.class);
        assertThat(dto).isInstanceOf(java.io.Serializable.class);
        
        // Test inherited methods work correctly
        dto.setCreateDateTime(testTime);
        dto.setUpdateDateTime(testTime.plusDays(1));
        
        assertThat(dto.getCreateDateTime()).isEqualTo(testTime);
        assertThat(dto.getUpdateDateTime()).isEqualTo(testTime.plusDays(1));
    }

    @Test
    void testLongTextContent() {
        String longContent = "This is a very long raw text content ".repeat(500);
        
        dto.setTextContent(longContent);
        
        assertThat(dto.getTextContent()).isEqualTo(longContent);
        assertThat(dto.getTextContent().length()).isEqualTo(37 * 500); // "This is a very long raw text content " = 37 chars
    }

    @Test 
    void testCopyConstructorWithNullValues() {
        // Test copy constructor when source has null values
        RawTextDto sourceDto = new RawTextDto();
        // Leave all fields as null
        
        RawTextDto copiedDto = new RawTextDto(sourceDto);
        
        assertThat(copiedDto.getId()).isNull();
        assertThat(copiedDto.getTextContent()).isNull();
        assertThat(copiedDto.getCreateDateTime()).isNull();
        assertThat(copiedDto.getUpdateDateTime()).isNull();
    }

    @Test
    void testCopyConstructorWithPartialValues() {
        // Test copy constructor when source has some null values
        dto.setId("partial-raw-text-id");
        dto.setTextContent(null); // null content
        dto.setCreateDateTime(testTime);
        // updateDateTime remains null
        
        RawTextDto copiedDto = new RawTextDto(dto);
        
        assertThat(copiedDto.getId()).isEqualTo("partial-raw-text-id");
        assertThat(copiedDto.getTextContent()).isNull();
        assertThat(copiedDto.getCreateDateTime()).isEqualTo(testTime);
        assertThat(copiedDto.getUpdateDateTime()).isNull();
    }

    @Test
    void testUnicodeTextContent() {
        String unicodeContent = "Raw text with unicode: 中文 日本語 한국어 🚀 🎉 emoji test";
        
        dto.setTextContent(unicodeContent);
        
        assertThat(dto.getTextContent()).isEqualTo(unicodeContent);
    }

    @Test
    void testJsonCreatorConstructorWithNull() {
        // Test JsonCreator constructor with null content
        RawTextDto newDto = new RawTextDto((String) null);
        
        assertThat(newDto.getTextContent()).isNull();
        assertThat(newDto.getId()).isNull();
    }

    @Test
    void testJsonCreatorConstructorWithEmptyString() {
        // Test JsonCreator constructor with empty string
        RawTextDto newDto = new RawTextDto("");
        
        assertThat(newDto.getTextContent()).isEqualTo("");
        assertThat(newDto.getId()).isNull();
    }

    @Test
    void testMultilineTextContent() {
        String multilineContent = "Line 1\nLine 2\nLine 3\nLine with\ttabs\nLine with\r\nWindows line endings";
        
        dto.setTextContent(multilineContent);
        
        assertThat(dto.getTextContent()).isEqualTo(multilineContent);
        assertThat(dto.getTextContent()).contains("\n");
        assertThat(dto.getTextContent()).contains("\t");
        assertThat(dto.getTextContent()).contains("\r\n");
    }
}