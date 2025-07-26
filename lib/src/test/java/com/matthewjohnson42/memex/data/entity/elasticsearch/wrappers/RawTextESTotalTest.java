package com.matthewjohnson42.memex.data.entity.elasticsearch.wrappers;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.assertj.core.api.Assertions.*;

/**
 * Unit tests for RawTextESTotal class
 */
class RawTextESTotalTest {

    private RawTextESTotal total;

    @BeforeEach
    void setUp() {
        total = new RawTextESTotal();
    }

    @Test
    void testDefaultConstructor() {
        RawTextESTotal newTotal = new RawTextESTotal();
        
        assertThat(newTotal.getValue()).isEqualTo(0); // int default value
    }

    @Test
    void testSetAndGetValue() {
        int testValue = 42;
        
        total.setValue(testValue);
        
        assertThat(total.getValue()).isEqualTo(testValue);
    }

    @Test
    void testSetZeroValue() {
        total.setValue(100);
        total.setValue(0);
        
        assertThat(total.getValue()).isEqualTo(0);
    }

    @Test
    void testSetNegativeValue() {
        int negativeValue = -10;
        
        total.setValue(negativeValue);
        
        assertThat(total.getValue()).isEqualTo(negativeValue);
    }

    @Test
    void testSetLargeValue() {
        int largeValue = Integer.MAX_VALUE;
        
        total.setValue(largeValue);
        
        assertThat(total.getValue()).isEqualTo(largeValue);
    }

    @Test
    void testSetMinValue() {
        int minValue = Integer.MIN_VALUE;
        
        total.setValue(minValue);
        
        assertThat(total.getValue()).isEqualTo(minValue);
    }

    @Test
    void testMultipleValueChanges() {
        total.setValue(10);
        assertThat(total.getValue()).isEqualTo(10);
        
        total.setValue(20);
        assertThat(total.getValue()).isEqualTo(20);
        
        total.setValue(0);
        assertThat(total.getValue()).isEqualTo(0);
        
        total.setValue(-5);
        assertThat(total.getValue()).isEqualTo(-5);
    }
}