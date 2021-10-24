package com.es.phoneshop.service.impl;

import org.junit.Test;

import static java.lang.Thread.sleep;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class DefaultDosProtectionServiceTest {

    private DefaultDosProtectionService service = DefaultDosProtectionService.getInstance();

    private final int filterThreshold = 20;
    private final int overThreshold = 50;
    private final long thresholdTime = 60000L;

    @Test
    public void testIsAllowedOneIP(){
        String address = "SomeIPtest1";
        int allowed = 0;
        int forbidden = 0;
        for(int i=0;i<overThreshold;i++){
            if(service.isAllowed(address)){
                allowed++;
            } else {
                forbidden++;
            }
        }

        assertTrue(allowed == filterThreshold + 1);
        assertTrue(forbidden == overThreshold - filterThreshold - 1);
    }

    @Test
    public void testIsAllowedDifferentIP(){
        String address = "SomeIPtest2";
        int allowed = 0;
        int forbidden = 0;
        for(int i=0;i<overThreshold;i++){
            if(service.isAllowed(address + i)){
                allowed++;
            } else {
                forbidden++;
            }
        }

        assertTrue(allowed == overThreshold);
        assertTrue(forbidden == 0);
    }

    @Test
    public void testIsAllowedAfterOneMinute() throws InterruptedException {
        String address = "SomeIPtest3_";
        for(int i=0;i<filterThreshold + 1;i++){
            assertTrue(service.isAllowed(address));
        }
        assertFalse(service.isAllowed(address));
        sleep(thresholdTime + 1000L); //!?
        assertTrue(service.isAllowed(address));
    }

}
