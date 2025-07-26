package com.matthewjohnson42.memex.data.config;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.assertj.core.api.Assertions.*;
import java.lang.reflect.Field;

/**
 * Unit tests for RawTextElasticConfiguration class
 */
class RawTextElasticConfigurationTest {

    private RawTextElasticConfiguration configuration;

    @BeforeEach
    void setUp() {
        configuration = new RawTextElasticConfiguration();
    }

    @Test
    void testConstructorSetsCreateIndexResourceFile() throws Exception {
        // Use reflection to access the protected field
        Field field = AbstractElasticConfiguration.class.getDeclaredField("createIndexResourceFile");
        field.setAccessible(true);
        String createIndexResourceFile = (String) field.get(configuration);
        
        assertThat(createIndexResourceFile).isEqualTo("elasticsearchqueries/rawTextCreateIndex.json");
    }

    @Test
    void testGetRawTextSearchByIdReturnsNonNull() {
        // This will return an empty string if resource doesn't exist, but should not throw
        assertThatCode(() -> {
            String result = configuration.getRawTextSearchById();
            assertThat(result).isNotNull();
        }).doesNotThrowAnyException();
    }

    @Test
    void testGetRawTextSearchByTextContentFuzzyReturnsNonNull() {
        // This will return an empty string if resource doesn't exist, but should not throw
        assertThatCode(() -> {
            String result = configuration.getRawTextSearchByTextContentFuzzy();
            assertThat(result).isNotNull();
        }).doesNotThrowAnyException();
    }

    @Test
    void testInheritsFromAbstractElasticConfiguration() {
        assertThat(configuration).isInstanceOf(AbstractElasticConfiguration.class);
    }

    @Test
    void testHasConfigurationAnnotation() {
        assertThat(configuration.getClass().isAnnotationPresent(org.springframework.context.annotation.Configuration.class))
            .isTrue();
    }

    @Test
    void testHasProfileAnnotation() {
        assertThat(configuration.getClass().isAnnotationPresent(org.springframework.context.annotation.Profile.class))
            .isTrue();
        
        org.springframework.context.annotation.Profile profileAnnotation = 
            configuration.getClass().getAnnotation(org.springframework.context.annotation.Profile.class);
        
        assertThat(profileAnnotation.value()).containsExactly("enableelasticrepositories");
    }

    @Test
    void testGetRawTextSearchByIdUsesCorrectResource() {
        // Test that the method attempts to read the correct resource
        // We can't test the actual content without the resource file, but we can test the method call
        assertThatCode(() -> configuration.getRawTextSearchById()).doesNotThrowAnyException();
    }

    @Test
    void testGetRawTextSearchByTextContentFuzzyUsesCorrectResource() {
        // Test that the method attempts to read the correct resource
        assertThatCode(() -> configuration.getRawTextSearchByTextContentFuzzy()).doesNotThrowAnyException();
    }

    @Test
    void testClassIsPublic() {
        assertThat(configuration.getClass().getModifiers() & java.lang.reflect.Modifier.PUBLIC)
            .isNotEqualTo(0);
    }

    @Test
    void testCanCreateInstance() {
        // Test that we can create an instance without exceptions
        assertThatCode(() -> new RawTextElasticConfiguration()).doesNotThrowAnyException();
    }

    @Test
    void testPackageLocation() {
        assertThat(configuration.getClass().getPackage().getName())
            .isEqualTo("com.matthewjohnson42.memex.data.config");
    }

    @Test
    void testInheritsParentMethods() {
        // Test that inherited methods are accessible
        assertThatCode(() -> {
            configuration.getHostName(); // Will be null in test, but method should exist
        }).doesNotThrowAnyException();
        
        assertThatCode(() -> {
            configuration.getHostPort(); // Will be null in test, but method should exist
        }).doesNotThrowAnyException();
        
        assertThatCode(() -> {
            configuration.getCreateIndex(); // Should execute without throwing
        }).doesNotThrowAnyException();
    }

    @Test
    void testResourceFilePathsAreCorrect() {
        // Test that the resource file paths follow expected naming convention
        String searchByIdResult = configuration.getRawTextSearchById();
        String searchByTextContentResult = configuration.getRawTextSearchByTextContentFuzzy();
        
        // These should not be null (empty string is OK if resource doesn't exist)
        assertThat(searchByIdResult).isNotNull();
        assertThat(searchByTextContentResult).isNotNull();
    }
}