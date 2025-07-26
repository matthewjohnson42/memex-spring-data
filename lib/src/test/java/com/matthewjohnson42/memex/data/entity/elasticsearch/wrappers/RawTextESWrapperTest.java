package com.matthewjohnson42.memex.data.entity.elasticsearch.wrappers;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.assertj.core.api.Assertions.*;

/**
 * Unit tests for RawTextESWrapper class
 */
class RawTextESWrapperTest {

    private RawTextESWrapper wrapper;

    @BeforeEach
    void setUp() {
        wrapper = new RawTextESWrapper();
    }

    @Test
    void testDefaultConstructor() {
        RawTextESWrapper newWrapper = new RawTextESWrapper();
        
        assertThat(newWrapper.getHits()).isNull();
    }

    @Test
    void testSetAndGetHits() {
        RawTextESHits hits = new RawTextESHits();
        
        // Setup the hits object
        RawTextESTotal total = new RawTextESTotal();
        total.setValue(10);
        hits.setTotal(total);
        
        wrapper.setHits(hits);
        
        assertThat(wrapper.getHits()).isSameAs(hits);
        assertThat(wrapper.getHits().getTotal().getValue()).isEqualTo(10);
    }

    @Test
    void testSetNullHits() {
        RawTextESHits initialHits = new RawTextESHits();
        wrapper.setHits(initialHits);
        wrapper.setHits(null);
        
        assertThat(wrapper.getHits()).isNull();
    }

    @Test
    void testSetEmptyHits() {
        RawTextESHits emptyHits = new RawTextESHits();
        // Don't set total or hits list, leaving them null
        
        wrapper.setHits(emptyHits);
        
        assertThat(wrapper.getHits()).isSameAs(emptyHits);
        assertThat(wrapper.getHits().getTotal()).isNull();
        assertThat(wrapper.getHits().getHits()).isNull();
    }

    @Test
    void testCompleteWrapperSetup() {
        // Create a complete wrapper structure
        RawTextESWrapper completeWrapper = new RawTextESWrapper();
        
        // Create hits
        RawTextESHits hits = new RawTextESHits();
        
        // Create total
        RawTextESTotal total = new RawTextESTotal();
        total.setValue(3);
        hits.setTotal(total);
        
        // Create hit list (empty for this test, but structure is there)
        hits.setHits(java.util.Arrays.asList());
        
        // Set hits on wrapper
        completeWrapper.setHits(hits);
        
        // Verify complete structure
        assertThat(completeWrapper.getHits()).isNotNull();
        assertThat(completeWrapper.getHits().getTotal()).isNotNull();
        assertThat(completeWrapper.getHits().getTotal().getValue()).isEqualTo(3);
        assertThat(completeWrapper.getHits().getHits()).isNotNull();
        assertThat(completeWrapper.getHits().getHits()).isEmpty();
    }

    @Test
    void testMultipleHitsAssignments() {
        RawTextESHits hits1 = new RawTextESHits();
        RawTextESTotal total1 = new RawTextESTotal();
        total1.setValue(5);
        hits1.setTotal(total1);
        
        RawTextESHits hits2 = new RawTextESHits();
        RawTextESTotal total2 = new RawTextESTotal();
        total2.setValue(10);
        hits2.setTotal(total2);
        
        // Set first hits
        wrapper.setHits(hits1);
        assertThat(wrapper.getHits().getTotal().getValue()).isEqualTo(5);
        
        // Replace with second hits
        wrapper.setHits(hits2);
        assertThat(wrapper.getHits().getTotal().getValue()).isEqualTo(10);
        
        // Verify first hits is no longer referenced
        assertThat(wrapper.getHits()).isSameAs(hits2);
        assertThat(wrapper.getHits()).isNotSameAs(hits1);
    }

    @Test
    void testWrapperAsTopLevelContainer() {
        // Test that wrapper can contain a full Elasticsearch response structure
        RawTextESWrapper topWrapper = new RawTextESWrapper();
        
        RawTextESHits mainHits = new RawTextESHits();
        
        // Set total count
        RawTextESTotal responseTotal = new RawTextESTotal();
        responseTotal.setValue(42);
        mainHits.setTotal(responseTotal);
        
        // Set the main hits on wrapper
        topWrapper.setHits(mainHits);
        
        // Verify the wrapper contains the complete structure
        assertThat(topWrapper.getHits()).isNotNull();
        assertThat(topWrapper.getHits().getTotal()).isNotNull();
        assertThat(topWrapper.getHits().getTotal().getValue()).isEqualTo(42);
    }
}