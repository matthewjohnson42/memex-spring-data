package com.matthewjohnson42.memex.data.service;

import com.matthewjohnson42.memex.data.converter.DtoEntityConverter;
import com.matthewjohnson42.memex.data.dto.DtoForEntity;
import com.matthewjohnson42.memex.data.entity.Entity;
import com.matthewjohnson42.memex.data.repository.Repository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.lenient;
import java.time.LocalDateTime;
import java.util.Optional;

/**
 * Unit tests for DataService abstract class - testing through concrete implementation
 */
@ExtendWith(MockitoExtension.class)
class DataServiceTest {

    @Mock
    private DtoEntityConverter<String, TestDto, TestEntity> mockConverter;
    
    @Mock
    private Repository<TestEntity, String> mockRepository;
    
    private TestDataService dataService;
    private TestDto testDto;
    @Mock
    private TestEntity mockEntity;
    private LocalDateTime testTime;

    // Test implementations for testing the abstract class
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

    private static class TestDataService extends DataService<String, TestDto, TestEntity> {
        public TestDataService(DtoEntityConverter<String, TestDto, TestEntity> converter, 
                              Repository<TestEntity, String> repository) {
            super(converter, repository);
        }
    }

    @BeforeEach
    void setUp() {
        dataService = new TestDataService(mockConverter, mockRepository);
        testDto = new TestDto();
        testTime = LocalDateTime.of(2023, 1, 15, 10, 30, 45, 123000000);
        
        testDto.setId("test-dto-id");
        lenient().when(mockEntity.getId()).thenReturn("test-entity-id");
    }

    @Test
    void testGetByIdSuccess() {
        // Setup mocks
        when(mockRepository.findById("test-id")).thenReturn(Optional.of(mockEntity));
        when(mockConverter.convertEntity(mockEntity)).thenReturn(testDto);

        // Execute
        TestDto result = dataService.getById("test-id");

        // Verify
        assertThat(result).isSameAs(testDto);
        verify(mockRepository).findById("test-id");
        verify(mockConverter).convertEntity(mockEntity);
    }

    @Test
    void testGetByIdNotFound() {
        // Setup mocks
        when(mockRepository.findById("non-existent-id")).thenReturn(Optional.empty());

        // Execute and verify exception
        assertThatThrownBy(() -> dataService.getById("non-existent-id"))
            .isInstanceOf(ResponseStatusException.class)
            .hasMessageContaining("No entity found for id non-existent-id")
            .extracting("status").isEqualTo(HttpStatus.NOT_FOUND);

        verify(mockRepository).findById("non-existent-id");
        verify(mockConverter, never()).convertEntity(any());
    }

    @Test
    void testCreateWithDateTime() {
        // Setup mocks
        when(mockConverter.convertDto(testDto)).thenReturn(mockEntity);
        when(mockRepository.save(mockEntity)).thenReturn(mockEntity);
        when(mockConverter.convertEntity(mockEntity)).thenReturn(testDto);

        // Execute
        TestDto result = dataService.create(testDto, testTime);

        // Verify
        assertThat(result).isSameAs(testDto);
        verify(mockConverter).convertDto(testDto);
        verify(mockRepository).save(mockEntity);
        verify(mockConverter).convertEntity(mockEntity);
        
        // Verify timestamps were set
        verify(mockEntity).setCreateDateTime(testTime);
        verify(mockEntity).setUpdateDateTime(testTime);
    }

    @Test
    void testCreateWithoutDateTime() {
        // Setup mocks
        when(mockConverter.convertDto(testDto)).thenReturn(mockEntity);
        when(mockRepository.save(mockEntity)).thenReturn(mockEntity);
        when(mockConverter.convertEntity(mockEntity)).thenReturn(testDto);

        // Execute
        TestDto result = dataService.create(testDto);

        // Verify
        assertThat(result).isSameAs(testDto);
        verify(mockConverter).convertDto(testDto);
        verify(mockRepository).save(mockEntity);
        verify(mockConverter).convertEntity(mockEntity);
        
        // Verify timestamps were set (can't verify exact time, but verify methods were called)
        verify(mockEntity).setCreateDateTime(any(LocalDateTime.class));
        verify(mockEntity).setUpdateDateTime(any(LocalDateTime.class));
    }

    @Test
    void testCreateWithNullId() {
        // Setup entity with null ID
        when(mockEntity.getId()).thenReturn(null);
        when(mockConverter.convertDto(testDto)).thenReturn(mockEntity);

        // Execute and verify exception
        assertThatThrownBy(() -> dataService.create(testDto, testTime))
            .isInstanceOf(ResponseStatusException.class)
            .hasMessageContaining("No id found for entity")
            .extracting("status").isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);

        verify(mockConverter).convertDto(testDto);
        verify(mockRepository, never()).save(any());
    }

    @Test
    void testUpdateWithDateTime() {
        // Setup mocks
        when(mockRepository.findById("test-dto-id")).thenReturn(Optional.of(mockEntity));
        when(mockConverter.updateFromDto(mockEntity, testDto)).thenReturn(mockEntity);
        when(mockRepository.save(mockEntity)).thenReturn(mockEntity);
        when(mockConverter.convertEntity(mockEntity)).thenReturn(testDto);

        // Execute
        TestDto result = dataService.update(testDto, testTime);

        // Verify
        assertThat(result).isSameAs(testDto);
        verify(mockRepository).findById("test-dto-id");
        verify(mockConverter).updateFromDto(mockEntity, testDto);
        verify(mockRepository).save(mockEntity);
        verify(mockConverter).convertEntity(mockEntity);
        verify(mockEntity).setUpdateDateTime(testTime);
    }

    @Test
    void testUpdateWithoutDateTime() {
        // Setup mocks
        when(mockRepository.findById("test-dto-id")).thenReturn(Optional.of(mockEntity));
        when(mockConverter.updateFromDto(mockEntity, testDto)).thenReturn(mockEntity);
        when(mockRepository.save(mockEntity)).thenReturn(mockEntity);
        when(mockConverter.convertEntity(mockEntity)).thenReturn(testDto);

        // Execute
        TestDto result = dataService.update(testDto);

        // Verify
        assertThat(result).isSameAs(testDto);
        verify(mockRepository).findById("test-dto-id");
        verify(mockConverter).updateFromDto(mockEntity, testDto);
        verify(mockRepository).save(mockEntity);
        verify(mockConverter).convertEntity(mockEntity);
        verify(mockEntity).setUpdateDateTime(any(LocalDateTime.class));
    }

    @Test
    void testUpdateEntityNotFound() {
        // Setup mocks
        when(mockRepository.findById("test-dto-id")).thenReturn(Optional.empty());

        // Execute and verify exception
        assertThatThrownBy(() -> dataService.update(testDto, testTime))
            .isInstanceOf(ResponseStatusException.class)
            .hasMessageContaining("No entity found for id test-dto-id")
            .extracting("status").isEqualTo(HttpStatus.NOT_FOUND);

        verify(mockRepository).findById("test-dto-id");
        verify(mockConverter, never()).updateFromDto(any(), any());
        verify(mockRepository, never()).save(any());
    }

    @Test
    void testDeleteById() {
        // Setup mocks
        when(mockRepository.findById("test-id")).thenReturn(Optional.of(mockEntity));
        when(mockConverter.convertEntity(mockEntity)).thenReturn(testDto);

        // Execute
        TestDto result = dataService.deleteById("test-id");

        // Verify
        assertThat(result).isSameAs(testDto);
        verify(mockRepository).findById("test-id");
        verify(mockRepository).deleteById("test-id");
        verify(mockConverter).convertEntity(mockEntity);
    }

    @Test
    void testDeleteByIdNotFound() {
        // Setup mocks
        when(mockRepository.findById("non-existent-id")).thenReturn(Optional.empty());

        // Execute and verify exception
        assertThatThrownBy(() -> dataService.deleteById("non-existent-id"))
            .isInstanceOf(ResponseStatusException.class)
            .hasMessageContaining("No entity found for id non-existent-id")
            .extracting("status").isEqualTo(HttpStatus.NOT_FOUND);

        verify(mockRepository).findById("non-existent-id");
        verify(mockRepository, never()).deleteById(any());
    }

    @Test
    void testDeleteDto() {
        // Setup mocks
        when(mockRepository.findById("test-dto-id")).thenReturn(Optional.of(mockEntity));
        when(mockConverter.convertEntity(mockEntity)).thenReturn(testDto);

        // Execute
        TestDto result = dataService.delete(testDto);

        // Verify
        assertThat(result).isSameAs(testDto);
        verify(mockRepository).findById("test-dto-id");
        verify(mockRepository).deleteById("test-dto-id");
        verify(mockConverter).convertEntity(mockEntity);
    }

    @Test
    void testExists() {
        // Setup mocks
        when(mockRepository.findById("existing-id")).thenReturn(Optional.of(mockEntity));
        when(mockRepository.findById("non-existing-id")).thenReturn(Optional.empty());

        // Execute and verify
        assertThat(dataService.exists("existing-id")).isTrue();
        assertThat(dataService.exists("non-existing-id")).isFalse();

        verify(mockRepository).findById("existing-id");
        verify(mockRepository).findById("non-existing-id");
    }

    @Test
    void testConstructorSetsFields() throws Exception {
        // Verify constructor properly sets the fields using reflection
        java.lang.reflect.Field converterField = DataService.class.getDeclaredField("converter");
        converterField.setAccessible(true);
        assertThat(converterField.get(dataService)).isSameAs(mockConverter);
        
        java.lang.reflect.Field repositoryField = DataService.class.getDeclaredField("repository");
        repositoryField.setAccessible(true);
        assertThat(repositoryField.get(dataService)).isSameAs(mockRepository);
    }

    @Test
    void testGetIfExistsSuccess() throws Exception {
        // Test the protected method via reflection since it's used internally
        when(mockRepository.findById("test-id")).thenReturn(Optional.of(mockEntity));

        // Use getById which internally calls getIfExists
        dataService.getById("test-id");

        verify(mockRepository).findById("test-id");
    }

    @Test
    void testCheckIdWithNullEntity() {
        // Test that checkId throws exception for entity with null ID
        TestEntity nullIdEntity = new TestEntity();
        nullIdEntity.setId(null);
        when(mockConverter.convertDto(testDto)).thenReturn(nullIdEntity);

        assertThatThrownBy(() -> dataService.create(testDto, testTime))
            .isInstanceOf(ResponseStatusException.class)
            .hasMessageContaining("No id found for entity")
            .extracting("status").isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Test
    void testMockCallCounts() {
        // Test that mocks are called the expected number of times
        when(mockRepository.findById("test-id")).thenReturn(Optional.of(mockEntity));
        when(mockConverter.convertEntity(mockEntity)).thenReturn(testDto);

        // Call the method twice
        dataService.getById("test-id");
        dataService.getById("test-id");

        // Verify exact call counts
        verify(mockRepository, times(2)).findById("test-id");
        verify(mockConverter, times(2)).convertEntity(mockEntity);
    }
}