package OfflineQuotes.services;

import OfflineQuotes.dao.BranchIssuanceAnalysisDao;
import OfflineQuotes.model.BranchIssuanceAnalysis;
import com.google.gson.Gson;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.io.*;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class BranchIssuanceAnalysisService {

    @Autowired
    BranchIssuanceAnalysisDao branchIssuanceAnalysisDao;

    private int getStatusWeight(BranchIssuanceAnalysis.RecordStatus status){
        switch (status) {
            case NOT_STARTED:
                return 1;
            case DATA_VERIFIED:
                return 2;
            case PREMIUM_REQUEST_INITIATED:
                return 3;
            case PREMIUM_RESULT_INITIATED:
                return 4;
            case PRICE_COMPARED:
                return 5;
            case FINAL_RESULT_GENERATED:
                return 6;
        }
        return 0;
    }

    public BranchIssuanceAnalysis findByMisId(String misId) {
            Optional<BranchIssuanceAnalysis> branchIssuanceAnalysisOptional = branchIssuanceAnalysisDao.findOneByMisID(misId);
            if(branchIssuanceAnalysisOptional.isPresent()){
                return branchIssuanceAnalysisOptional.get();
            }
            throw new EntityNotFoundException("No record found for misId : " + misId);
    }

    public BranchIssuanceAnalysis findByRequestId(String requestId) throws EntityNotFoundException{
        Optional<BranchIssuanceAnalysis> branchIssuanceAnalysisOptional = branchIssuanceAnalysisDao.findOneByStpRequestId(requestId);
        if(branchIssuanceAnalysisOptional.isPresent()){
            return branchIssuanceAnalysisOptional.get();
        }
        throw new EntityNotFoundException("No record found for request id : " + requestId);
    }

    public BranchIssuanceAnalysis createBranchIssuanceAnalysisRecord(BranchIssuanceAnalysis data) {

        BranchIssuanceAnalysis exisitngRecord = null;

        if(data != null){
            String misID = data.getMisID();
            if(misID != null){
                try {
                    exisitngRecord = this.findByMisId(misID);
                    System.out.println("-|-|- Document already exists. Not creating-|-|-"+exisitngRecord.getEntityId());
                }catch (Exception e){
                    data.setRecordStatus(BranchIssuanceAnalysis.RecordStatus.NOT_STARTED);
                    exisitngRecord = branchIssuanceAnalysisDao.save(data);
                    System.out.println("-|-|- Creating Document with _id-|-|- :::   "+exisitngRecord.getEntityId());
                }
            }
        }
        return exisitngRecord;
    }

    public BranchIssuanceAnalysis updateBranchIssuanceAnalysisRecord(BranchIssuanceAnalysis data) {

        BranchIssuanceAnalysis exisitngRecord = null;
        if(data != null){
            String misID = data.getMisID();
            if(misID != null){
                try {
                    exisitngRecord = this.findByMisId(misID);
                    System.out.println("-|-|- Exisitng Document -|-|-"+exisitngRecord.getEntityId()+"---"+exisitngRecord.getMisID());

                    exisitngRecord.setVertical(data.getVertical());
                    exisitngRecord.setInsurer(data.getInsurer());
                    exisitngRecord.setMisID(data.getMisID());
                    exisitngRecord.setMisRequestID(data.getMisRequestID());
                    exisitngRecord.setMisPrice(data.getMisPrice());
                    exisitngRecord.setDataStatus(data.getDataStatus());
                    exisitngRecord.setMissingData(data.getMissingData());
                    exisitngRecord.setStpRequestId(data.getStpRequestId());
                    exisitngRecord.setResultStatus(data.getResultStatus());
                    exisitngRecord.setTotalResults(data.getTotalResults());
                    exisitngRecord.setPriceComparisionStatus(data.getPriceComparisionStatus());
                    exisitngRecord.setProposalStatus(data.getProposalStatus());
                    exisitngRecord.setPolicyDetailRecord(data.getPolicyDetailRecord());
                    exisitngRecord.setPremiumRequestResponse(data.getPremiumRequestResponse());
                    exisitngRecord.setPremiumResultResponse(data.getPremiumResultResponse());
                    exisitngRecord.setErrorMsg(data.getErrorMsg());

                    if(statusCheck(exisitngRecord.getRecordStatus(), data.getRecordStatus())){
                        exisitngRecord.setRecordStatus(data.getRecordStatus());
                    }
                    exisitngRecord.setTmMakeModelId(data.getTmMakeModelId());
                    exisitngRecord.setOnlineQuotes(data.getOnlineQuotes());
                    exisitngRecord = branchIssuanceAnalysisDao.save(exisitngRecord);
//                    System.out.println(exisitngRecord.getEntityId());
                }catch (Exception e){
                    System.out.println("-|-|- Document Doesn't exist-|-|-"+e);
                }
            }
        }
        return exisitngRecord;
    }

    private boolean statusCheck(BranchIssuanceAnalysis.RecordStatus recordStatus, BranchIssuanceAnalysis.RecordStatus recordStatus1) {
        int a = getStatusWeight(recordStatus);
        int b = getStatusWeight(recordStatus1);
        return a < b;
    }


    public JSONArray getNinjaRecords() throws IOException, ParseException {
        BranchIssuanceAnalysis bia = new BranchIssuanceAnalysis();
        JSONParser parser = new JSONParser();
        String fileName = "/Users/gopikrishna/workspace/OfflinePolicyQuote/src/main/java/OfflineQuotes/Data/PolicyDetailLast15days.json";
//        String fileName = "/Users/gopikrishna/workspace/OfflinePolicyQuote/src/main/java/OfflineQuotes/Data/singleJson.json";
        JSONArray data = (JSONArray) parser.parse(new FileReader(fileName));
        return data;
    }

    public List<BranchIssuanceAnalysis> processNinjaRecords(JSONArray data) {
        List<BranchIssuanceAnalysis> biaRecords = new ArrayList<>();
        System.out.println("data.size():::::::"+data.size());
        for(Object record  : data) {

            JSONObject jObj = (JSONObject) record;
            BranchIssuanceAnalysis bia = new BranchIssuanceAnalysis();
            JSONObject premiumDetails = (JSONObject) jObj.get("premiumDetails");
            System.out.println("::::::::::::::::::::::->"+(String) jObj.get("requestId"));
            bia.setMisID((String) jObj.get("requestId"));
            bia.setMisRequestID((String) jObj.get("_id"));
            bia.setVertical((String) jObj.get("vertical"));
            bia.setInsurer((String) jObj.get("insurer"));
            bia.setMisPrice(String.valueOf(premiumDetails.get("netPremium")));
            bia.setMisPolicyIssuanceDate((String) jObj.get("issuanceDate"));
//            bia.setRecordStatus(BranchIssuanceAnalysis.RecordStatus.NOT_STARTED);
            bia.setPolicyDetailRecord(jObj);
            String biaData = new Gson().toJson(bia);
            try {
//                bia = this.createBranchIssuanceAnalysisRecord(bia);
                bia = this.updateBranchIssuanceAnalysisRecord(bia);
//                System.out.println(bia.getMisID());
            }catch (Exception e){
                System.out.println(e);
            }
            biaRecords.add(bia);
        }
        return biaRecords;
    }

    public void currentStatus(BranchIssuanceAnalysis data) throws UnirestException, IOException, ParseException {
        String biaData;
        String stringData;
        switch (data.getRecordStatus()){

            case NOT_STARTED:
                try{
                    data = validationService.validateMisData(data);
                }catch (Exception e){
                    data.setRecordStatus(BranchIssuanceAnalysis.RecordStatus.EXCEPTION_DURING_DATA_VERIFICATION);
                    data.setErrorMsg("Exception in Validation Service: "+ e);
                }
                System.out.println("Eror Msg:: "+data.getErrorMsg());
                System.out.println("RecordStatus:: "+data.getRecordStatus());
                try {
                    this.updateBranchIssuanceAnalysisRecord(data);
                }catch (Exception e){
                    System.out.println("Exception while saving in db in NOT_STARTED case: "+e);
                }
//                this.currentStatus(data);
                break;

            case DATA_VERIFIED:
                JSONObject premiumResponse = premiumRequestService.insurerCall(data);
                try{
                    Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
                    try{
                        if(premiumResponse.toString().length()>0){
                            String requestID = ((JSONObject)((JSONObject)premiumResponse.get("data")).get("premiumRequest")).get("_id").toString();
                            data.setStpRequestId(requestID);
                            data.setRecordStatus(BranchIssuanceAnalysis.RecordStatus.PREMIUM_REQUEST_INITIATED);
                        }else{
                            data.setRecordStatus(BranchIssuanceAnalysis.RecordStatus.PREMIUM_REQUEST_IS_0);
                        }
                    }catch (Exception e){
                        data.setRecordStatus(BranchIssuanceAnalysis.RecordStatus.EXCEPTION_DURING_PREMIUM_REQUEST);
                        data.setErrorMsg(e.toString());
                    }
                    premiumResponse.put("timestamp", dateFormat.format(timestamp));
                    data.setPremiumRequestResponse(premiumResponse);
                }catch (Exception ignored){
                    data.setRecordStatus(BranchIssuanceAnalysis.RecordStatus.EXCEPTION_DURING_DATA_VERIFICATION);
                    data.setErrorMsg(ignored.toString());
                }
                try {
//                    this.updateBranchIssuanceAnalysisRecord(data);
                }catch (Exception e){
                    System.out.println("Exception while saving in db in DATA_VERIFIED case: "+e);
                }
//                this.currentStatus(data);
                break;

            case PREMIUM_REQUEST_INITIATED:
                System.out.println("In PREMIUM_REQUEST_INITIATED");
                String policyType = (String) data.getPolicyDetailRecord().get("planType");
                String requestID = data.getStpRequestId();
                String premiumRequestData = new Gson().toJson(data.getPremiumRequestResponse().get("data"));
                org.json.JSONObject prDataJson = new org.json.JSONObject(premiumRequestData);
                String premiumRequestString = prDataJson.get("premiumRequest").toString();
                org.json.JSONObject premumRequestJson = new org.json.JSONObject(premiumRequestString);
                String uid = (String) premumRequestJson.get("uniqueId");
                String insurers = "ICICILOMBARD,UNISOMPO,RELI,HDFC,NINA,DIGIT,UNTD";
                String premimResultResponse = premiumResultService.makePremiumResultCall(insurers, policyType, requestID, "CV", uid);
                JSONParser parser = new JSONParser();
                JSONObject premiumResultResponseJson = (JSONObject) parser.parse(premimResultResponse);
                data.setPremiumResultResponse(premiumResultResponseJson);
                System.out.println(((JSONObject)premiumResultResponseJson.get("data")).get("pendingKeyList"));
                System.out.println(((JSONObject)premiumResultResponseJson.get("data")).get("pendingKeyList").getClass());
                JSONArray pendingKeyList = (JSONArray) ((JSONObject)premiumResultResponseJson.get("data")).get("pendingKeyList");
                try {
                    this.updateBranchIssuanceAnalysisRecord(data);
                }catch (Exception e){
                    System.out.println("Exception while saving in db in PREMIUM_REQUEST_INITIATED case: "+e);
                }
                if(pendingKeyList.isEmpty()){
                    data.setRecordStatus(BranchIssuanceAnalysis.RecordStatus.PREMIUM_RESULT_INITIATED);
                    this.updateBranchIssuanceAnalysisRecord(data);
                    this.currentStatus(data);
                }
                break;

            case PREMIUM_RESULT_INITIATED:
                System.out.println("PREMIUM_RESULT_INITIATED");
                BranchIssuanceAnalysis dataAfterComparision = insurerPriceComparisons.compareInsurerPricesFromPremiumResult(data);
                try {
                    this.updateBranchIssuanceAnalysisRecord(dataAfterComparision);
                }catch (Exception e){
                    System.out.println("Exception while saving in db in PREMIUM_RESULT_INITIATED case: "+e);
                }
                this.currentStatus(dataAfterComparision);
                break;
            case IDV_REQUEST_INITIATED:
                System.out.println("IDV_REQUEST_INITIATED");
                break;
            case IDV_RESULT_INITIATED:
                System.out.println("IDV_RESULT_INITIATED");
                break;
            case PRICE_COMPARED:
                System.out.println("PRICE_COMPARED");
                break;

            case FINAL_RESULT_GENERATED:
                System.out.println("FINAL_RESULT_GENERATED");
                break;

        }
    }

    void writeToCSV(ArrayList<BranchIssuanceAnalysis> itemList)
    {
        String CSV_SEPARATOR = ",";
        try
        {
            //misID,misRequestID,misPrice,requestId,PolicyIssuanceDate,RecordStatus,errorMsg,ICICILOMBARD,UNISOMPO,RELI,HDFC,NINA,DIGIT,UNTD
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("finalOutput.csv"), "UTF-8"));
            JSONObject onlineQuotes;
            LinkedHashMap<String, Object> insurerResult;
            for (BranchIssuanceAnalysis product : itemList)
            {
//                misID,misRequestID,insurer,misPrice,requestId,PolicyIssuanceDate,ErrorMsg,RecordStatus,ICICILOMBARD,UNISOMPO,RELI,HDFC,NINA,DIGIT,UNTD

                StringBuffer oneLine = new StringBuffer();
                onlineQuotes = product.getOnlineQuotes();
//                if(onlineQuotes!=null){
                oneLine.append(product.getMisID());
                oneLine.append(CSV_SEPARATOR);
                oneLine.append(product.getMisRequestID());
                oneLine.append(CSV_SEPARATOR);
                oneLine.append(product.getInsurer());
                oneLine.append(CSV_SEPARATOR);
                oneLine.append(product.getMisPrice());
                oneLine.append(CSV_SEPARATOR);
                oneLine.append(product.getStpRequestId());
                oneLine.append(CSV_SEPARATOR);
                oneLine.append(product.getMisPolicyIssuanceDate());
                oneLine.append(CSV_SEPARATOR);
                if(product.getErrorMsg()!=null){
                oneLine.append(product.getErrorMsg().replace(",", " "));}
                oneLine.append(CSV_SEPARATOR);
                oneLine.append(product.getRecordStatus());
                oneLine.append(CSV_SEPARATOR);
//                oneLine.append(product.getPremiumResultResponse().get("timestamp"));
//                oneLine.append(CSV_SEPARATOR);
//                oneLine.append(product.getOnlineQuotes());
////                oneLine.append(CSV_SEPARATOR);



                try {
                    insurerResult = (LinkedHashMap<String, Object>) onlineQuotes.get("ICICILOMBARD");
                    if(insurerResult.get("status").equals("SUCCESS")){
                        oneLine.append(insurerResult.get("finalPremium"));
                    }else{
                        oneLine.append(insurerResult.get("status") +"_"+ insurerResult.get("errorMessage").toString().replace(",", ""));
                    }
                }catch (Exception e){
                    oneLine.append("null");
                }
                oneLine.append(CSV_SEPARATOR);
                try {
                    insurerResult = (LinkedHashMap<String, Object>) onlineQuotes.get("UNISOMPO");

                    if(insurerResult.get("status")=="SUCCESS"){
                        oneLine.append(insurerResult.get("finalPremium"));
                    }else{
                        oneLine.append(insurerResult.get("status") +"_"+ insurerResult.get("errorMessage").toString().replace(",", ""));
                    }
                }catch (Exception e){
                    oneLine.append("null");
                }
                oneLine.append(CSV_SEPARATOR);
                try {
                    insurerResult = (LinkedHashMap<String, Object>) onlineQuotes.get("RELI");
                    if(insurerResult.get("status").equals("SUCCESS")){
                        oneLine.append(insurerResult.get("finalPremium"));
                    }else{
                        oneLine.append(insurerResult.get("status") +"_"+ insurerResult.get("errorMessage").toString().replace(",", ""));
                    }
                }catch (Exception e){
                    oneLine.append("null");
                }
                oneLine.append(CSV_SEPARATOR);
                try {
                    insurerResult = (LinkedHashMap<String, Object>) onlineQuotes.get("HDFC");
                    if(insurerResult.get("status").equals("SUCCESS")){
                        oneLine.append(insurerResult.get("finalPremium"));
                    }else{
                        oneLine.append(insurerResult.get("status") +"_"+ insurerResult.get("errorMessage").toString().replace(",", ""));
                    }
                }catch (Exception e){
                    oneLine.append("null");
                }
                oneLine.append(CSV_SEPARATOR);
                try {
                    insurerResult = (LinkedHashMap<String, Object>) onlineQuotes.get("NINA");
                    if(insurerResult.get("status").equals("SUCCESS")){
                        oneLine.append(insurerResult.get("finalPremium"));
                    }else{
                        oneLine.append(insurerResult.get("status") +"_"+ insurerResult.get("errorMessage").toString().replace(",", ""));
                    }
                }catch (Exception e){
                    oneLine.append("null");
                }
                oneLine.append(CSV_SEPARATOR);
                try {
                    insurerResult = (LinkedHashMap<String, Object>) onlineQuotes.get("DIGIT");
                    if(insurerResult.get("status").equals("SUCCESS")){
                        oneLine.append(insurerResult.get("finalPremium"));
                    }else{
                        oneLine.append(insurerResult.get("status") +"_"+ insurerResult.get("errorMessage").toString().replace(",", ""));
                    }
                }catch (Exception e){
                    oneLine.append("null");
                }
                oneLine.append(CSV_SEPARATOR);
                try {
                    insurerResult = (LinkedHashMap<String, Object>) onlineQuotes.get("UNTD");
                    if(insurerResult.get("status").equals("SUCCESS")){
                        oneLine.append(insurerResult.get("finalPremium"));
                    }else{
                        oneLine.append(insurerResult.get("status") +"_"+ insurerResult.get("errorMessage").toString().replace(",", ""));
                    }
                }catch (Exception e){
                    oneLine.append("null");
                }
                oneLine.append(CSV_SEPARATOR);
                bw.write(oneLine.toString());
                bw.newLine();
            }
//        }
            bw.flush();
            bw.close();
        } catch (IOException ignored) {

        }
    }

    public void initiateService() throws IOException, ParseException, UnirestException {
//        List<BranchIssuanceAnalysis> ninjaRecords = processNinjaRecords(getNinjaRecords());
        List<BranchIssuanceAnalysis> ninjaRecords = branchIssuanceAnalysisDao.findAll();
//        writeToCSV((ArrayList<BranchIssuanceAnalysis>) ninjaRecords);
        for(BranchIssuanceAnalysis record: ninjaRecords.subList(0,45)){
//            if(record.getRecordStatus().equals(BranchIssuanceAnalysis.RecordStatus.PREMIUM_REQUEST_INITIATED)){
//            if(record.getRecordStatus().equals(BranchIssuanceAnalysis.RecordStatus.DATA_VERIFIED)){
            if(record.getErrorMsg().equals("java.lang.NullPointerException")){
                this.currentStatus(record);
//                System.out.println(record.getMisID());
            }
        }
    }

    public void premiumRequestUsingIDV() throws IOException, ParseException, UnirestException {
        idvPremiumRequestService.insurerIdvAndAddonCall();
        idvPremiumResultService.makeIdvPremiumResultCall();

    }
}