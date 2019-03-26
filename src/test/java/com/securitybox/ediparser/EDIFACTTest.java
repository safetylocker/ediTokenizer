package com.securitybox.ediparser;

import com.securitybox.constants.Constants;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class EDIFACTTest {
    EDIFACT edifact = new EDIFACT();
    JSONArray objectToTokenized= new JSONArray();
    JSONObject jsonObject = new JSONObject();

    public EDIFACTTest() throws NoSuchAlgorithmException {
    }

    @Test
    public void docuemntHandler() throws NoSuchAlgorithmException, JSONException {
        jsonObject.put(Constants.EDIFACT_SEGMENT_NUMBER,16);
        jsonObject.put(Constants.EDIFACT_DATA_ELEMENT_NUMBER,5);
        jsonObject.put(Constants.EDIFACT_DATA_ELEMENT_POSITION,1);
        jsonObject.put(Constants.EDIFACT_DATA_ELEMENT_LENGTH,30);
        objectToTokenized.put(jsonObject);

        JSONObject jsonObject1 = new JSONObject();
        jsonObject1.put(Constants.EDIFACT_SEGMENT_NUMBER,21);
        jsonObject1.put(Constants.EDIFACT_DATA_ELEMENT_NUMBER,5);
        jsonObject1.put(Constants.EDIFACT_DATA_ELEMENT_POSITION,1);
        jsonObject1.put(Constants.EDIFACT_DATA_ELEMENT_LENGTH,30);
        objectToTokenized.put(jsonObject1);

        JSONObject jsonObject2 = new JSONObject();
        jsonObject2.put(Constants.EDIFACT_SEGMENT_NUMBER,18);
        jsonObject2.put(Constants.EDIFACT_DATA_ELEMENT_NUMBER,2);
        jsonObject2.put(Constants.EDIFACT_DATA_ELEMENT_POSITION,2);
        jsonObject2.put(Constants.EDIFACT_DATA_ELEMENT_LENGTH,30);

        JSONObject jsonObject3 = new JSONObject();
        jsonObject3.put(Constants.EDIFACT_SEGMENT_NUMBER,1);
        jsonObject3.put(Constants.EDIFACT_DATA_ELEMENT_NUMBER,5);
        jsonObject3.put(Constants.EDIFACT_DATA_ELEMENT_POSITION,1);
        jsonObject3.put(Constants.EDIFACT_DATA_ELEMENT_LENGTH,30);
        objectToTokenized.put(jsonObject3);

        JSONObject jsonObject4 = new JSONObject();
        jsonObject4.put(Constants.EDIFACT_SEGMENT_NUMBER,38);
        jsonObject4.put(Constants.EDIFACT_DATA_ELEMENT_NUMBER,3);
        jsonObject4.put(Constants.EDIFACT_DATA_ELEMENT_POSITION,1);
        jsonObject4.put(Constants.EDIFACT_DATA_ELEMENT_LENGTH,30);

        JSONObject jsonObject5 = new JSONObject();
        jsonObject5.put(Constants.EDIFACT_SEGMENT_NUMBER,2);
        jsonObject5.put(Constants.EDIFACT_DATA_ELEMENT_NUMBER,5);
        jsonObject5.put(Constants.EDIFACT_DATA_ELEMENT_POSITION,2);
        jsonObject5.put(Constants.EDIFACT_DATA_ELEMENT_LENGTH,30);;
        objectToTokenized.put(jsonObject);


        String completeEDIFACT = "UNA:+.? 'UNB+UNOC:3+Patner1+Patner2+100615:0100+1006150100000++Patner1'UNH+1+IFTMIN:S:93A:UN'BGM+340+0000001339+9'DTM+137:20100615:102'TSR+PCO'FTX+DIN+++Delivery information'CNT+7:7.000:KGM'CNT+26:0.500:MTQ'CNT+ZLM:0.40:MTR'CNT+Z12:3:PCE'CNT+11:3:PCE'RFF+AAO:Receivers reference'RFF+CU:Shipment reference'TDT+20'NAD+CZ+123456++Patner1 AB+Box 2326 +GEBORG++40315+SE'CTA+IC+:John Doe'COM+031-581600:TE'COM+031-7581605:FX'COM+info@Patner1.com:EM'NAD+CN+++Testmottagaren AB+Sdervsvn 12 +VTRA FRUNDA++42651+SE'CTA+IC+:Bjrn Svensson'COM+012-3456789:TE'COM+012-9876543:FX'COM+test@test.com:EM'COM+07353289222:ZMS'GID+1+1:CT'FTX+AAA+++Electronics'FTX+PAC+++9'MEA+WT++KGM:5.000'MEA+VOL++MTQ:0.300'PCI+18+373925550000420599'GID+2+2:CT'FTX+AAA+++Electronics'FTX+PAC+++9'MEA+WT++KGM:2.000'MEA+VOL++MTQ:0.200'PCI+18+373925550000420605:373925550000420612'EQD+EFP'EQN+1'UNT+39+1'UNZ+1+1006150100000'";
        String oneLineSegment = "UNA:+.? 'UNB+UNOC:3+Patner1+Patner2+100615:0100+1006150100000++Patner1'NAD+CN+++Testmottagaren AB+Sdervsvn 12 +VTRA FRUNDA++42651+SE'";

        ArrayList<String> sender = new ArrayList<String>();
        sender.add("clientA");
        ArrayList<String> receiver = new ArrayList<String>();
        receiver.add("clientB");
        String completeEdiTokenized_0="",completeEdiDeTokenized_0 = "",completeEdiTokenized_1="",completeEdiDeTokenized_1 = "";

        completeEdiTokenized_0 = edifact.docuemntHandler(Constants.TOKENIZER_METHOD_TOKENIZE,objectToTokenized,completeEDIFACT,sender,receiver);
        completeEdiDeTokenized_0= edifact.docuemntHandler(Constants.TOKENIZER_METHOD_DETOKENIZE,objectToTokenized,completeEdiTokenized_0,sender,receiver);
        assertEquals(completeEDIFACT,completeEdiDeTokenized_0);

        completeEdiTokenized_1 = edifact.docuemntHandler(Constants.TOKENIZER_METHOD_TOKENIZE,objectToTokenized,oneLineSegment,sender,receiver);
        completeEdiDeTokenized_1= edifact.docuemntHandler(Constants.TOKENIZER_METHOD_DETOKENIZE,objectToTokenized,completeEdiTokenized_1,sender,receiver);
        assertEquals(oneLineSegment,completeEdiDeTokenized_1);
    }
}