package OfflineQuotes.services;

import OfflineQuotes.model.BranchIssuanceAnalysis;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.Iterator;

public class insurerPriceComparisons {
    public static BranchIssuanceAnalysis compareInsurerPricesFromPremiumResult(BranchIssuanceAnalysis data) throws ParseException {
        JSONParser parser = new JSONParser();
        Object premiumResultResponseObj = data.getPremiumResultResponse();
        JSONObject premiumResultResponse = (JSONObject) premiumResultResponseObj;
        Object premiumResultDataObj = premiumResultResponse.get("data");
        String premiumResultDataStr = new Gson().toJson(premiumResultDataObj);
        JSONObject premiumResultData = (JSONObject) parser.parse(premiumResultDataStr);
        JSONArray premiumResults = (JSONArray) premiumResultData.get("premiumResults");
        Iterator<JsonElement> it = premiumResults.iterator();
        Object record;
        JSONObject jsonRecord;
        String status;
        String insurer;
        int finalPremium = 0;
        JSONObject priceComparision = new JSONObject();
        while(it.hasNext()){
            JSONObject insurerPriceComparision = new JSONObject();
            record = it.next();
            jsonRecord = (JSONObject) parser.parse(record.toString());
            status = (String) jsonRecord.get("status");
            insurer = (String) jsonRecord.get("insurer");
            insurerPriceComparision.put("status", status);
            if(status.equals("SUCCESS")){
                finalPremium = Integer.parseInt((String) jsonRecord.get("finalPremium"));
                insurerPriceComparision.put("finalPremium", finalPremium);
            }else{
                JSONObject motorPremiumResult = (JSONObject) jsonRecord.get("motorPremiumResult");
                System.out.println(motorPremiumResult);
                insurerPriceComparision.put("errorMessage", motorPremiumResult.get("errorMsg"));
            }
            priceComparision.put(insurer, insurerPriceComparision);
        }
        data.setOnlineQuotes(priceComparision);
        data.setRecordStatus(BranchIssuanceAnalysis.RecordStatus.PRICE_COMPARED);
        return data;
    }
}
