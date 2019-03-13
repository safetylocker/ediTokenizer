package com.documenthandler.ediparser;

import com.documenthandler.constants.Constants;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.StringTokenizer;
import java.util.regex.Pattern;

@Deprecated
class EdifactCompositeElement {
    JSONObject edifactCompositeElement= new JSONObject();
    StringTokenizer edifactCompositeElementTokenizer=null;

    public String getEdifactSegmentStr(String segment) throws JSONException {
        edifactCompositeElementTokenizer = new StringTokenizer(segment, Constants.EDIFACT_DATA_ELEMENT_SEPERATOR,false);
        JSONArray edifactCompositeElementArr= new JSONArray();
        String response="";
        int compositeEleNum=0;
        while(edifactCompositeElementTokenizer.hasMoreElements()){
            edifactCompositeElementArr.put(compositeEleNum++,edifactCompositeElementTokenizer.nextElement());
        }

        for(int i = 0;i<edifactCompositeElementArr.length();i++ ){
            if(i > 0)
                response +=  Constants.EDIFACT_DATA_ELEMENT_SEPERATOR + edifactCompositeElementArr.get(i).toString();
            else
                response +=  edifactCompositeElementArr.get(i).toString();
        }

        return response;
    }

    public String getEdifactSegmentStrList(String segment) throws JSONException {

        ArrayList<String> arrayList = new ArrayList<String>(Arrays.asList(segment.split("\\"+Constants.EDIFACT_DATA_ELEMENT_SEPERATOR)));
        JSONArray edifactCompositeElementArr= new JSONArray();
        String response="";
        int compositeEleNum=0;
        while(arrayList.size() > compositeEleNum){
            edifactCompositeElementArr.put(compositeEleNum,arrayList.get(compositeEleNum));
            compositeEleNum++;
        }

        for(int i = 0;i<edifactCompositeElementArr.length();i++ ){
            System.out.println(edifactCompositeElementArr.get(i).toString());
            if(i > 0)
                response +=  Constants.EDIFACT_DATA_ELEMENT_SEPERATOR + edifactCompositeElementArr.get(i).toString();
            else
                response +=  edifactCompositeElementArr.get(i).toString();
        }

        return response;
    }
}
