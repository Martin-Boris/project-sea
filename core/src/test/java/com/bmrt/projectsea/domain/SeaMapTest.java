package com.bmrt.projectsea.domain;

import com.bmrt.projectsea.domain.SeaMap;
import com.bmrt.projectsea.domain.Vector;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class SeaMapTest {

    @Nested
    class isOut {
        @Test
        void caseNotOut() {
            Vector position = new Vector(10, 20);
            SeaMap seaMap = new SeaMap(20, 20);
            Assertions.assertFalse(seaMap.isOut(position));
        }

        @Test
        void caseOutOnLeft() {
            Vector position = new Vector(-0.1f, 20);
            SeaMap seaMap = new SeaMap(20, 20);
            Assertions.assertTrue(seaMap.isOut(position));
        }

        @Test
        void caseOutOnBottom() {
            Vector position = new Vector(10, -0.1f);
            SeaMap seaMap = new SeaMap(20, 20);
            Assertions.assertTrue(seaMap.isOut(position));
        }

        @Test
        void caseOutOnRight() {
            Vector position = new Vector(20.1f, 20);
            SeaMap seaMap = new SeaMap(20, 20);
            Assertions.assertTrue(seaMap.isOut(position));
        }

        @Test
        void caseOutOnTop() {
            Vector position = new Vector(10, 20.1f);
            SeaMap seaMap = new SeaMap(20, 20);
            Assertions.assertTrue(seaMap.isOut(position));
        }
    }

}
