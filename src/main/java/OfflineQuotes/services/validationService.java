package OfflineQuotes.services;

import OfflineQuotes.model.BranchIssuanceAnalysis;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.util.ArrayList;
import java.util.Map;

public class validationService {

    public static BranchIssuanceAnalysis validateMisData(BranchIssuanceAnalysis data) throws UnirestException, ParseException {
        JSONObject policyDetailRecord = new JSONObject(data.getPolicyDetailRecord());
        JSONObject policyRisk = new JSONObject((Map) policyDetailRecord.get("policyRisk"));
        ArrayList<String> nullValues = new ArrayList<>();
        String make = (String) policyRisk.getOrDefault("make", null);
        String model = (String) policyRisk.getOrDefault("model", null);
        String gcvPcv = (String) policyDetailRecord.getOrDefault("vehicleType", null);
        String fuel = (String) policyRisk.getOrDefault("fuel", null);
        String gvwStr;
        String scStr;
        String ccStr;
        int gvw = 0;
        int sc = 0;
        int cc = 0;
        try{
            gvwStr = policyRisk.getOrDefault("gvw", 0).toString();
            gvw = Integer.parseInt(gvwStr);
        }catch (Exception e){
            System.out.println("GVW Exception::"+e);
        }
        try{
            scStr = policyRisk.getOrDefault("seatingCapacity", 0).toString();
            sc = Integer.parseInt(scStr);
        }catch (Exception e){
            System.out.println("SC Exception::"+e);
        }
        try{
            ccStr = policyRisk.getOrDefault("cc", 0).toString();
            cc = Integer.parseInt(ccStr);
        }catch (Exception e){
            System.out.println("CC Exception::"+e);
        }
        String tmMakeModelId = null;
        try {
            if (fuel != null) {
                fuel = fuel.substring(0, 1).toUpperCase() + fuel.substring(1).toLowerCase();
            }
            if (make == null) {
                nullValues.add("MAKE");
            }
            if (model == null) {
                nullValues.add("MODEL");
            }
            if (gcvPcv.equals("vehicleClass")) {
                nullValues.add("vehicleClass");
            }
            if (fuel == null) {
                nullValues.add("FUEL");
            }
            if (gcvPcv.equalsIgnoreCase("GCV")) {
                if (gvw == 0) {
                    nullValues.add("GVW");
                }
            } else if (gcvPcv.equalsIgnoreCase("PCV")) {
                if (cc == 0) {
                    nullValues.add("CC");
                }
                if (sc == 0) {
                    nullValues.add("SC");
                }
            }
        }catch (Exception ignored){
            System.out.println("  Exception ignored:::  "+ignored);
            data.setRecordStatus(BranchIssuanceAnalysis.RecordStatus.EXCEPTION_DURING_DATA_VERIFICATION);
            data.setErrorMsg(ignored.toString());
        }

        if( nullValues.isEmpty() ){
            try {
                tmMakeModelId = premiumRequestService.get_tm_make_model(make, model, gcvPcv);
            }catch (Exception e){
                data.setErrorMsg("Exception while fetching tmMakeModelId: "+e);
                data.setRecordStatus(BranchIssuanceAnalysis.RecordStatus.EXCEPTION_DURING_DATA_VERIFICATION);
            }
            if( tmMakeModelId == null ){
                data.setRecordStatus(BranchIssuanceAnalysis.RecordStatus.DATA_VERIFICATION_FAILED);
                String errorMsg = "tmMakeModelId is null :: Make - "+make+" | Model - "+model+" | vehicleClass - "+gcvPcv;
                data.setErrorMsg(errorMsg);
            }else {
                data.setRecordStatus(BranchIssuanceAnalysis.RecordStatus.DATA_VERIFIED);
                data.setErrorMsg("");
            }
        }else{
            data.setRecordStatus(BranchIssuanceAnalysis.RecordStatus.DATA_VERIFICATION_FAILED);
            String errorMsg = gcvPcv+"-Missing Values-"+ nullValues;
            data.setErrorMsg(errorMsg);
        }
        return data;
    }
}