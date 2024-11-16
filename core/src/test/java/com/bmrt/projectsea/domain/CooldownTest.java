package com.bmrt.projectsea.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class CooldownTest {

    @Nested
    class isReady {

        @Test
        void caseReady() {
            Cooldown cooldown = new Cooldown();
            Assertions.assertTrue(cooldown.isReady());
        }

        @Test
        void caseNotReady() {
            Cooldown cooldown = new Cooldown();
            cooldown.trigger();
            Assertions.assertFalse(cooldown.isReady());
        }
    }
}
