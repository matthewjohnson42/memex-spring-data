package com.matthewjohnson42.memex.data.repository.mongo;

import com.matthewjohnson42.memex.data.entity.mongo.RawTextMongo;
import com.matthewjohnson42.memex.data.repository.Repository;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Unit tests for RawTextMongoRepo interface - testing interface structure and inheritance
 */
class RawTextMongoRepoTest {

    @Test
    void testInterfaceExtendsRepository() {
        // Verify that RawTextMongoRepo extends Repository interface
        Class<?>[] interfaces = RawTextMongoRepo.class.getInterfaces();
        
        assertThat(interfaces).hasSize(2); // Should extend both MongoRepository and Repository
        
        List<String> interfaceNames = Arrays.stream(interfaces)
            .map(Class::getSimpleName)
            .collect(Collectors.toList());
        
        assertThat(interfaceNames).contains("Repository");
        assertThat(interfaceNames).contains("MongoRepository");
    }

    @Test
    void testInheritsRepositoryMethods() {
        // Verify that RawTextMongoRepo inherits the Repository methods
        Method[] methods = RawTextMongoRepo.class.getMethods();
        
        List<String> methodNames = Arrays.stream(methods)
            .map(Method::getName)
            .collect(Collectors.toList());
        
        // Should inherit Repository methods
        assertThat(methodNames).contains("save");
        assertThat(methodNames).contains("findById");
        assertThat(methodNames).contains("deleteById");
    }

    @Test
    void testInheritsMongoRepositoryMethods() {
        // Verify that RawTextMongoRepo inherits MongoRepository methods
        Method[] methods = RawTextMongoRepo.class.getMethods();
        
        List<String> methodNames = Arrays.stream(methods)
            .map(Method::getName)
            .collect(Collectors.toList());
        
        // Should inherit MongoRepository methods (some examples)
        assertThat(methodNames).contains("findAll");
        assertThat(methodNames).contains("count");
        assertThat(methodNames).contains("existsById");
    }

    @Test
    void testInterfaceIsPublic() {
        // Verify interface accessibility
        assertThat(RawTextMongoRepo.class.getModifiers() & java.lang.reflect.Modifier.PUBLIC)
            .isNotEqualTo(0);
    }

    @Test
    void testInterfaceIsInterface() {
        // Verify this is actually an interface
        assertThat(RawTextMongoRepo.class.isInterface()).isTrue();
    }

    @Test
    void testGenericTypes() {
        // Test that the interface uses correct generic types
        java.lang.reflect.Type[] genericInterfaces = RawTextMongoRepo.class.getGenericInterfaces();
        
        assertThat(genericInterfaces).hasSizeGreaterThan(0);
        
        // Check that it uses RawTextMongo and String as generic parameters
        String typeString = Arrays.toString(genericInterfaces);
        assertThat(typeString).contains("RawTextMongo");
        assertThat(typeString).contains("String");
    }

    @Test
    void testPackageLocation() {
        // Verify the interface is in the correct package
        assertThat(RawTextMongoRepo.class.getPackage().getName())
            .isEqualTo("com.matthewjohnson42.memex.data.repository.mongo");
    }

    @Test
    void testHasNoAbstractMethods() {
        // Since it's an interface extending other interfaces, 
        // it should not declare any new abstract methods
        Method[] declaredMethods = RawTextMongoRepo.class.getDeclaredMethods();
        
        // Should have no declared methods of its own
        assertThat(declaredMethods).isEmpty();
    }

    @Test
    void testInheritanceHierarchy() {
        // Test the complete inheritance hierarchy
        assertThat(Repository.class.isAssignableFrom(RawTextMongoRepo.class)).isTrue();
        
        // Verify generic type compatibility
        Class<?>[] interfaces = RawTextMongoRepo.class.getInterfaces();
        boolean hasRepositoryInterface = Arrays.stream(interfaces)
            .anyMatch(i -> i.equals(Repository.class));
        
        assertThat(hasRepositoryInterface).isTrue();
    }

    @Test
    void testCanBeUsedAsRepository() {
        // Test that RawTextMongoRepo can be treated as a Repository
        Class<?> repoClass = RawTextMongoRepo.class;
        
        assertThat(Repository.class).isAssignableFrom(repoClass);
        
        // Can be cast to Repository
        assertThatCode(() -> {
            Class<? extends Repository> repositoryClass = repoClass.asSubclass(Repository.class);
            assertThat(repositoryClass).isNotNull();
        }).doesNotThrowAnyException();
    }

    @Test
    void testEntityAndIdTypes() {
        // Verify the interface works with correct entity and ID types
        java.lang.reflect.ParameterizedType[] parameterizedInterfaces = 
            Arrays.stream(RawTextMongoRepo.class.getGenericInterfaces())
                .filter(t -> t instanceof java.lang.reflect.ParameterizedType)
                .map(t -> (java.lang.reflect.ParameterizedType) t)
                .toArray(java.lang.reflect.ParameterizedType[]::new);
        
        assertThat(parameterizedInterfaces).isNotEmpty();
        
        // Find the Repository interface
        java.lang.reflect.ParameterizedType repositoryInterface = Arrays.stream(parameterizedInterfaces)
            .filter(pt -> pt.getRawType().equals(Repository.class))
            .findFirst()
            .orElse(null);
        
        assertThat(repositoryInterface).isNotNull();
        
        java.lang.reflect.Type[] typeArguments = repositoryInterface.getActualTypeArguments();
        assertThat(typeArguments).hasSize(2);
        
        // First type argument should be RawTextMongo
        assertThat(typeArguments[0].getTypeName()).contains("RawTextMongo");
        
        // Second type argument should be String
        assertThat(typeArguments[1].getTypeName()).contains("String");
    }
}