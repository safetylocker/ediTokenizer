package com.securitybox.ediparser;

import com.securitybox.models.CSVRecord;
import org.junit.Assert;
import org.junit.Test;

public class CSVRecordTest {

    String record = "0000001339::20100615";
    CSVRecord csvRecord = new CSVRecord(":","",record);


    @Test
    public void getField() {
    }

    @Test
    public void setFiled() {
    }

    @Test
    public void getCount() {
        Assert.assertEquals(4,csvRecord.getCount());
    }

    @Test
    public void getRecord() {
    }
}