import com.documenthandler.ediparser.EDIFACT;
import com.documenthandler.ediparser.EdiDocument;
import org.json.JSONException;
import org.junit.Test;


public class EdiDocumentParserTester {
    @Test
    public void name() throws JSONException {
        EDIFACT edifact = new EDIFACT();
        System.out.println(edifact.convertToJson("UNA:+.? 'UNB+UNOC:3+Patner1+Patner2+100615:0100+1006150100000++Patner1'UNH+1+IFTMIN:S:93A:UN'BGM+340+0000001339+9'DTM+137:20100615:102'TSR+PCO'FTX+DIN+++Delivery information'CNT+7:7.000:KGM'CNT+26:0.500:MTQ'CNT+ZLM:0.40:MTR'CNT+Z12:3:PCE'CNT+11:3:PCE'RFF+AAO:Receivers reference'RFF+CU:Shipment reference'TDT+20'NAD+CZ+123456++Patner1 AB+Box 2326 +GEBORG++40315+SE'CTA+IC+:John Doe'COM+031-581600:TE'COM+031-7581605:FX'COM+info@Patner1.com:EM'NAD+CN+++Testmottagaren AB+Sdervsvn 12 +VTRA FRUNDA++42651+SE'CTA+IC+:Bjrn Svensson'COM+012-3456789:TE'COM+012-9876543:FX'COM+test@test.com:EM'COM+07353289222:ZMS'GID+1+1:CT'FTX+AAA+++Electronics'FTX+PAC+++9'MEA+WT++KGM:5.000'MEA+VOL++MTQ:0.300'PCI+18+373925550000420599'GID+2+2:CT'FTX+AAA+++Electronics'FTX+PAC+++9'MEA+WT++KGM:2.000'MEA+VOL++MTQ:0.200'PCI+18+373925550000420605:373925550000420612'EQD+EFP'EQN+1'UNT+39+1'UNZ+1+1006150100000'"));

        //System.out.println(edifact.convertToJson("NAD+CN+++Testmottagaren AB+Sdervsvn 12 +VTRA FRUNDA++42651+SE'"));

    }
}
