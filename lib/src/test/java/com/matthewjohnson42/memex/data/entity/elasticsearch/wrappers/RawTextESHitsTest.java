package com.matthewjohnson42.memex.data.entity.elasticsearch.wrappers;

import com.matthewjohnson42.memex.data.entity.elasticsearch.RawTextES;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.assertj.core.api.Assertions.*;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

/**
 * Unit tests for RawTextESHits class
 */
class RawTextESHitsTest {

    private RawTextESHits hits;

    @BeforeEach
    void setUp() {
        hits = new RawTextESHits();
    }

    @Test
    void testDefaultConstructor() {
        RawTextESHits newHits = new RawTextESHits();
        
        assertThat(newHits.getTotal()).isNull();
        assertThat(newHits.getHits()).isNull();
    }

    @Test
    void testSetAndGetTotal() {
        RawTextESTotal total = new RawTextESTotal();
        total.setValue(42);
        
        hits.setTotal(total);
        
        assertThat(hits.getTotal()).isSameAs(total);
        assertThat(hits.getTotal().getValue()).isEqualTo(42);
    }

    @Test
    void testSetAndGetHits() {
        // Create test hits
        RawTextESHit hit1 = new RawTextESHit();
        RawTextES source1 = new RawTextES();
        source1.setId("hit1-id");
        source1.setTextContent("hit1 content");
        hit1.set_source(source1);
        
        RawTextESHit hit2 = new RawTextESHit();
        RawTextES source2 = new RawTextES();
        source2.setId("hit2-id");
        source2.setTextContent("hit2 content");
        hit2.set_source(source2);
        
        List<RawTextESHit> hitsList = Arrays.asList(hit1, hit2);
        
        hits.setHits(hitsList);
        
        assertThat(hits.getHits()).isSameAs(hitsList);
        assertThat(hits.getHits()).hasSize(2);
        assertThat(hits.getHits().get(0)).isSameAs(hit1);
        assertThat(hits.getHits().get(1)).isSameAs(hit2);
    }

    @Test
    void testSetNullTotal() {
        RawTextESTotal initialTotal = new RawTextESTotal();
        hits.setTotal(initialTotal);
        hits.setTotal(null);
        
        assertThat(hits.getTotal()).isNull();
    }

    @Test
    void testSetNullHits() {
        List<RawTextESHit> initialHits = Arrays.asList(new RawTextESHit());
        hits.setHits(initialHits);
        hits.setHits(null);
        
        assertThat(hits.getHits()).isNull();
    }

    @Test
    void testSetEmptyHits() {
        List<RawTextESHit> emptyHits = new ArrayList<>();
        
        hits.setHits(emptyHits);
        
        assertThat(hits.getHits()).isSameAs(emptyHits);
        assertThat(hits.getHits()).isEmpty();
    }

    @Test
    void testCompleteHitsSetup() {
        // Setup total
        RawTextESTotal total = new RawTextESTotal();
        total.setValue(2);
        
        // Setup hits with highlights
        RawTextESHit hit1 = new RawTextESHit();
        RawTextES source1 = new RawTextES();
        source1.setId("complete-hit1-id");
        source1.setTextContent("complete hit1 content");
        hit1.set_source(source1);
        
        RawTextESHighlight highlight1 = new RawTextESHighlight();
        highlight1.setTextContent(Arrays.asList("complete", "highlight1"));
        hit1.setHighlight(highlight1);
        
        RawTextESHit hit2 = new RawTextESHit();
        RawTextES source2 = new RawTextES();
        source2.setId("complete-hit2-id");
        source2.setTextContent("complete hit2 content");
        hit2.set_source(source2);
        
        RawTextESHighlight highlight2 = new RawTextESHighlight();
        highlight2.setTextContent(Arrays.asList("complete", "highlight2"));
        hit2.setHighlight(highlight2);
        
        List<RawTextESHit> hitsList = Arrays.asList(hit1, hit2);
        
        // Set everything
        hits.setTotal(total);
        hits.setHits(hitsList);
        
        // Verify everything is set correctly
        assertThat(hits.getTotal().getValue()).isEqualTo(2);
        assertThat(hits.getHits()).hasSize(2);
        assertThat(hits.getHits().get(0).get_source().getId()).isEqualTo("complete-hit1-id");
        assertThat(hits.getHits().get(1).get_source().getId()).isEqualTo("complete-hit2-id");
        assertThat(hits.getHits().get(0).getHighlight().getTextContent()).containsExactly("complete", "highlight1");
        assertThat(hits.getHits().get(1).getHighlight().getTextContent()).containsExactly("complete", "highlight2");
    }

    @Test
    void testLargeHitsList() {
        List<RawTextESHit> largeHitsList = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            RawTextESHit hit = new RawTextESHit();
            RawTextES source = new RawTextES();
            source.setId("hit_" + i + "_id");
            source.setTextContent("hit " + i + " content");
            hit.set_source(source);
            largeHitsList.add(hit);
        }
        
        hits.setHits(largeHitsList);
        
        assertThat(hits.getHits()).hasSize(100);
        assertThat(hits.getHits().get(0).get_source().getId()).isEqualTo("hit_0_id");
        assertThat(hits.getHits().get(99).get_source().getId()).isEqualTo("hit_99_id");
    }

    @Test
    void testSingleHit() {
        RawTextESTotal total = new RawTextESTotal();
        total.setValue(1);
        
        RawTextESHit singleHit = new RawTextESHit();
        RawTextES source = new RawTextES();
        source.setId("single-hit-id");
        source.setTextContent("single hit content");
        singleHit.set_source(source);
        
        List<RawTextESHit> singleHitList = Arrays.asList(singleHit);
        
        hits.setTotal(total);
        hits.setHits(singleHitList);
        
        assertThat(hits.getTotal().getValue()).isEqualTo(1);
        assertThat(hits.getHits()).hasSize(1);
        assertThat(hits.getHits().get(0).get_source().getId()).isEqualTo("single-hit-id");
    }

    @Test
    void testZeroTotal() {
        RawTextESTotal zeroTotal = new RawTextESTotal();
        zeroTotal.setValue(0);
        
        hits.setTotal(zeroTotal);
        hits.setHits(new ArrayList<>());
        
        assertThat(hits.getTotal().getValue()).isEqualTo(0);
        assertThat(hits.getHits()).isEmpty();
    }
}