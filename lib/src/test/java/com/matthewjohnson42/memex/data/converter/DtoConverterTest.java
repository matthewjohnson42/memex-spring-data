package com.matthewjohnson42.memex.data.converter;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;

/**
 * Unit tests for DtoConverter interface
 */
class DtoConverterTest {

    // Test implementation for testing the interface
    private static class TestDtoConverter implements DtoConverter<String, Integer> {
        
        @Override
        public Integer convertDtoType1(String s) {
            if (s == null) return null;
            try {
                return Integer.parseInt(s);
            } catch (NumberFormatException e) {
                return 0;
            }
        }

        @Override
        public String convertDtoType2(Integer i) {
            if (i == null) return null;
            return i.toString();
        }
    }

    @Test
    void testConvertDtoType1WithValidString() {
        TestDtoConverter converter = new TestDtoConverter();
        
        Integer result = converter.convertDtoType1("42");
        
        assertThat(result).isEqualTo(42);
    }

    @Test
    void testConvertDtoType1WithNullString() {
        TestDtoConverter converter = new TestDtoConverter();
        
        Integer result = converter.convertDtoType1(null);
        
        assertThat(result).isNull();
    }

    @Test
    void testConvertDtoType1WithInvalidString() {
        TestDtoConverter converter = new TestDtoConverter();
        
        Integer result = converter.convertDtoType1("invalid");
        
        assertThat(result).isEqualTo(0);
    }

    @Test
    void testConvertDtoType1WithEmptyString() {
        TestDtoConverter converter = new TestDtoConverter();
        
        Integer result = converter.convertDtoType1("");
        
        assertThat(result).isEqualTo(0);
    }

    @Test
    void testConvertDtoType1WithNegativeNumber() {
        TestDtoConverter converter = new TestDtoConverter();
        
        Integer result = converter.convertDtoType1("-15");
        
        assertThat(result).isEqualTo(-15);
    }

    @Test
    void testConvertDtoType2WithValidInteger() {
        TestDtoConverter converter = new TestDtoConverter();
        
        String result = converter.convertDtoType2(123);
        
        assertThat(result).isEqualTo("123");
    }

    @Test
    void testConvertDtoType2WithNullInteger() {
        TestDtoConverter converter = new TestDtoConverter();
        
        String result = converter.convertDtoType2(null);
        
        assertThat(result).isNull();
    }

    @Test
    void testConvertDtoType2WithZero() {
        TestDtoConverter converter = new TestDtoConverter();
        
        String result = converter.convertDtoType2(0);
        
        assertThat(result).isEqualTo("0");
    }

    @Test
    void testConvertDtoType2WithNegativeInteger() {
        TestDtoConverter converter = new TestDtoConverter();
        
        String result = converter.convertDtoType2(-99);
        
        assertThat(result).isEqualTo("-99");
    }

    @Test
    void testBidirectionalConversion() {
        TestDtoConverter converter = new TestDtoConverter();
        
        // Test round-trip conversion
        String original = "456";
        Integer converted = converter.convertDtoType1(original);
        String backConverted = converter.convertDtoType2(converted);
        
        assertThat(backConverted).isEqualTo(original);
    }

    @Test
    void testBidirectionalConversionWithInteger() {
        TestDtoConverter converter = new TestDtoConverter();
        
        // Test round-trip conversion starting with Integer
        Integer original = 789;
        String converted = converter.convertDtoType2(original);
        Integer backConverted = converter.convertDtoType1(converted);
        
        assertThat(backConverted).isEqualTo(original);
    }

    @Test
    void testConverterWithLargeNumbers() {
        TestDtoConverter converter = new TestDtoConverter();
        
        Integer result1 = converter.convertDtoType1(String.valueOf(Integer.MAX_VALUE));
        assertThat(result1).isEqualTo(Integer.MAX_VALUE);
        
        Integer result2 = converter.convertDtoType1(String.valueOf(Integer.MIN_VALUE));
        assertThat(result2).isEqualTo(Integer.MIN_VALUE);
        
        String result3 = converter.convertDtoType2(Integer.MAX_VALUE);
        assertThat(result3).isEqualTo(String.valueOf(Integer.MAX_VALUE));
        
        String result4 = converter.convertDtoType2(Integer.MIN_VALUE);
        assertThat(result4).isEqualTo(String.valueOf(Integer.MIN_VALUE));
    }
}