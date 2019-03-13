package com.documenthandler.ediparser;

import com.documenthandler.constants.Constants;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.StringTokenizer;
@Deprecated
class EdifactSegment {
    JSONObject edifactSegment=new JSONObject();

    StringTokenizer edifactSegmentTokenizer=null;

    public String getEdifactSegmentStr(String segmnet) throws JSONException {
        JSONArray edifactSegment=new JSONArray();
        edifactSegmentTokenizer = new StringTokenizer(segmnet, Constants.EDIFACT_COMPONENT_DATA_ELEMENT_SEPERATOR,false);

        int pos=0;
        String edifactCompositElement = "";
        String response="";
        while (edifactSegmentTokenizer.hasMoreElements()){

            edifactCompositElement = new EdifactCompositeElement().getEdifactSegmentStrList(edifactSegmentTokenizer.nextToken());
            edifactSegment.put(pos++,edifactCompositElement);
        }

        for(int i = 0;i<edifactSegment.length();i++ ){
            if(i > 0)
                response +=  edifactSegment.get(i).toString() + Constants.EDIFACT_SEGMENT_TERMINATOR;
            else
                response +=  edifactSegment.get(i).toString();
        }
        return response;
    }
}
