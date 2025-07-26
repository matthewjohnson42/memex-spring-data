package com.matthewjohnson42.memex.data.converter;

import com.matthewjohnson42.memex.data.dto.RawTextDto;
import com.matthewjohnson42.memex.data.entity.mongo.RawTextMongo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.assertj.core.api.Assertions.*;
import java.time.LocalDateTime;

/**
 * Unit tests for RawTextMongoConverter class
 */
class RawTextMongoConverterTest {

    private RawTextMongoConverter converter;
    private RawTextDto dto;
    private RawTextMongo entity;
    private LocalDateTime testTime;

    @BeforeEach
    void setUp() {
        converter = new RawTextMongoConverter();
        dto = new RawTextDto();
        entity = new RawTextMongo();
        testTime = LocalDateTime.of(2023, 1, 15, 10, 30, 45, 123000000);
    }

    @Test
    void testConvertEntity() {
        // Setup entity
        entity.setId("mongo-entity-id");
        entity.setTextContent("mongo entity content");
        entity.setCreateDateTime(testTime);
        entity.setUpdateDateTime(testTime.plusHours(1));

        RawTextDto result = converter.convertEntity(entity);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo("mongo-entity-id");
        assertThat(result.getTextContent()).isEqualTo("mongo entity content");
        assertThat(result.getCreateDateTime()).isEqualTo(testTime);
        assertThat(result.getUpdateDateTime()).isEqualTo(testTime.plusHours(1));
    }

    @Test
    void testConvertEntityWithNullTextContent() {
        entity.setId("entity-with-null-content");
        entity.setTextContent(null);
        entity.setCreateDateTime(testTime);

        RawTextDto result = converter.convertEntity(entity);

        assertThat(result.getId()).isEqualTo("entity-with-null-content");
        assertThat(result.getTextContent()).isNull();
        assertThat(result.getCreateDateTime()).isEqualTo(testTime);
    }

    @Test
    void testConvertEntityWithAllNullFields() {
        RawTextMongo nullEntity = new RawTextMongo();
        // All fields remain null

        RawTextDto result = converter.convertEntity(nullEntity);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isNull();
        assertThat(result.getTextContent()).isNull();
        assertThat(result.getCreateDateTime()).isNull();
        assertThat(result.getUpdateDateTime()).isNull();
    }

    @Test
    void testConvertDto() {
        // Setup DTO
        dto.setId("dto-id");
        dto.setTextContent("dto content");
        dto.setCreateDateTime(testTime);
        dto.setUpdateDateTime(testTime.plusMinutes(30));

        RawTextMongo result = converter.convertDto(dto);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo("dto-id");
        assertThat(result.getTextContent()).isEqualTo("dto content");
        // Note: convertDto may not set timestamps depending on implementation
    }

    @Test
    void testConvertDtoWithNullTextContent() {
        dto.setId("dto-with-null-content");
        dto.setTextContent(null);

        RawTextMongo result = converter.convertDto(dto);

        assertThat(result.getId()).isEqualTo("dto-with-null-content");
        assertThat(result.getTextContent()).isNull();
    }

    @Test
    void testConvertDtoWithAllNullFields() {
        RawTextDto nullDto = new RawTextDto();
        // All fields remain null

        RawTextMongo result = converter.convertDto(nullDto);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isNull();
        assertThat(result.getTextContent()).isNull();
    }

    @Test
    void testUpdateFromEntity() {
        // Setup initial DTO
        dto.setId("original-dto-id");
        dto.setTextContent("original dto content");
        dto.setCreateDateTime(testTime.minusDays(1));

        // Setup entity with updates
        entity.setId("updated-entity-id");
        entity.setTextContent("updated entity content");
        entity.setCreateDateTime(testTime);
        entity.setUpdateDateTime(testTime.plusHours(2));

        RawTextDto result = converter.updateFromEntity(dto, entity);

        assertThat(result).isSameAs(dto); // Should return same DTO instance
        assertThat(result.getId()).isEqualTo("updated-entity-id");
        assertThat(result.getTextContent()).isEqualTo("updated entity content");
        assertThat(result.getCreateDateTime()).isEqualTo(testTime);
        assertThat(result.getUpdateDateTime()).isEqualTo(testTime.plusHours(2));
    }

    @Test
    void testUpdateFromEntityWithNullTextContent() {
        dto.setTextContent("original content");
        entity.setTextContent(null);

        RawTextDto result = converter.updateFromEntity(dto, entity);

        assertThat(result.getTextContent()).isEqualTo("original content"); // Should remain unchanged
    }

    @Test
    void testUpdateFromEntityCallsSuperMethod() {
        // Test that the method properly calls the parent updateFromEntity
        dto.setId("original-id");
        entity.setId("new-id");
        entity.setCreateDateTime(testTime);
        entity.setTextContent("new content");

        RawTextDto result = converter.updateFromEntity(dto, entity);

        // Verify both superclass behavior (ID, timestamps) and subclass behavior (textContent)
        assertThat(result.getId()).isEqualTo("new-id");
        assertThat(result.getCreateDateTime()).isEqualTo(testTime);
        assertThat(result.getTextContent()).isEqualTo("new content");
    }

    @Test
    void testUpdateFromDto() {
        // Setup initial entity
        entity.setId("original-entity-id");
        entity.setTextContent("original entity content");

        // Setup DTO with updates
        dto.setId("updated-dto-id");
        dto.setTextContent("updated dto content");

        RawTextMongo result = converter.updateFromDto(entity, dto);

        assertThat(result).isSameAs(entity); // Should return same entity instance
        assertThat(result.getId()).isEqualTo("updated-dto-id");
        assertThat(result.getTextContent()).isEqualTo("updated dto content");
    }

    @Test
    void testUpdateFromDtoWithNullTextContent() {
        entity.setTextContent("original content");
        dto.setTextContent(null);

        RawTextMongo result = converter.updateFromDto(entity, dto);

        assertThat(result.getTextContent()).isEqualTo("original content"); // Should remain unchanged
    }

    @Test
    void testUpdateFromDtoCallsSuperMethod() {
        // Test that the method properly calls the parent updateFromDto
        entity.setId("original-id");
        dto.setId("new-id");
        dto.setTextContent("new content");

        RawTextMongo result = converter.updateFromDto(entity, dto);

        // Verify both superclass behavior (ID) and subclass behavior (textContent)
        assertThat(result.getId()).isEqualTo("new-id");
        assertThat(result.getTextContent()).isEqualTo("new content");
    }

    @Test
    void testBidirectionalConversion() {
        // Setup original entity
        entity.setId("bidirectional-test");
        entity.setTextContent("bidirectional content");
        entity.setCreateDateTime(testTime);
        entity.setUpdateDateTime(testTime.plusHours(1));

        // Entity -> DTO -> Entity
        RawTextDto convertedDto = converter.convertEntity(entity);
        RawTextMongo convertedEntity = converter.convertDto(convertedDto);

        assertThat(convertedEntity.getId()).isEqualTo("bidirectional-test");
        assertThat(convertedEntity.getTextContent()).isEqualTo("bidirectional content");
        // Note: timestamps may not round-trip through convertDto depending on implementation
    }

    @Test
    void testLongTextContent() {
        String longContent = "This is a very long text content for MongoDB ".repeat(100);
        
        entity.setTextContent(longContent);
        entity.setId("long-content-test");

        RawTextDto convertedDto = converter.convertEntity(entity);
        RawTextMongo convertedBack = converter.convertDto(convertedDto);

        assertThat(convertedDto.getTextContent()).isEqualTo(longContent);
        assertThat(convertedBack.getTextContent()).isEqualTo(longContent);
    }

    @Test
    void testSpecialCharactersInTextContent() {
        String specialContent = "Content with special chars: <>&\"'中文🚀\n\t";
        
        entity.setTextContent(specialContent);
        entity.setId("special-chars-test");

        RawTextDto convertedDto = converter.convertEntity(entity);
        RawTextMongo convertedBack = converter.convertDto(convertedDto);

        assertThat(convertedDto.getTextContent()).isEqualTo(specialContent);
        assertThat(convertedBack.getTextContent()).isEqualTo(specialContent);
    }
}