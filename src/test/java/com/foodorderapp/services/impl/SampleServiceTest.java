package com.foodorderapp.services.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class SampleServiceTest extends BaseServiceTest {


    @Test
    void sampleTest() {
        String s = "s";

        Assertions.assertEquals("s", s);
    }
}
