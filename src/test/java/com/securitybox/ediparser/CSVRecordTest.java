package com.securitybox.ediparser;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class CSVRecordTest {

    String record = "0000001339::20100615::682249465:Box 2326 ::40315:GEBORG:031-581600:031-581600::837483438:1932098535::42651:1060530195:012-3456789:::";
    CSVRecord csvRecord = new CSVRecord(":","",record);


    @Test
    public void getField() {
    }

    @Test
    public void setFiled() {
    }

    @Test
    public void getCount() {
        Assert.assertEquals(1,csvRecord.getCount());
    }

    @Test
    public void getRecord() {
    }
}