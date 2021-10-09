package OfflineQuotes.services;

import OfflineQuotes.model.BranchIssuanceAnalysis;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class premiumRequestService {

    public static JSONObject insurerCall(BranchIssuanceAnalysis record) throws IOException, ParseException, UnirestException {
        JSONObject requestBody = generateRequestBody(record);
        JSONObject premiumResponse = null;
        JSONParser parser = new JSONParser();

        if(requestBody != null){
//            System.out.println(requestBody);
            premiumResponse = (JSONObject) parser.parse(makePremiumRequestCall(requestBody.toString()));
        }
        return premiumResponse;
    }

    private static JSONObject generateRequestBody(BranchIssuanceAnalysis record) throws IOException, ParseException, UnirestException {
        JSONParser parser = new JSONParser();
        JSONObject requestBody = (JSONObject) parser.parse(new FileReader("/Users/gopikrishna/workspace/OfflinePolicyQuote/src/main/java/OfflineQuotes/configs/requestBody.json"));
        requestBody = (JSONObject) requestBody.get("request");
        JSONObject updatedRequestBody = updateMotorPremiumRequest(requestBody, record);
        return updatedRequestBody;
    }

    private static JSONObject updateMotorPremiumRequest(JSONObject requestBody, BranchIssuanceAnalysis record) {
        try {
            JSONObject policyDetailRecord = record.getPolicyDetailRecord();
            JSONObject policyRisk = new JSONObject((Map) policyDetailRecord.get("policyRisk"));
            JSONObject motorPremiumRequest = (JSONObject) requestBody.get("motorPremiumRequest");
            String make = (String) policyRisk.get("make");
            String model = (String) policyRisk.get("model");
            String gcvPcv = (String) policyDetailRecord.get("vehicleType");
            String policyType = (String) policyDetailRecord.get("planType");
            if(policyType == null){
                policyType = "comprehensive";
            }
            String tm_make_model_id = get_tm_make_model(make, model, gcvPcv);
            String requestId = getRequestId();
            String fuel = policyRisk.get("fuel").toString();
            fuel = fuel.substring(0,1).toUpperCase()+fuel.substring(1).toLowerCase();
            String gvwStr = policyRisk.getOrDefault("gvw", 0).toString();
            int gvw = Integer.parseInt(gvwStr);
            String scStr = policyRisk.getOrDefault("seatingCapacity", 0).toString();
            int sc = Integer.parseInt(gvwStr);
            String ccStr = policyRisk.getOrDefault("cc", 0).toString();
            int cc = Integer.parseInt(gvwStr);
            if (tm_make_model_id != null) {
                requestBody.put("_id", requestId);
                motorPremiumRequest.put("requestId", requestId);
                requestBody.put("policyType", policyType);
                motorPremiumRequest.put("make", make);
                motorPremiumRequest.put("model", model);
                motorPremiumRequest.put("cvVehicleClass", gcvPcv);
                motorPremiumRequest.put("grossWeight", Integer.toString(gvw));
                motorPremiumRequest.put("cubicCapacity", Integer.toString(cc));
                motorPremiumRequest.put("carryingCapacity", sc);
                motorPremiumRequest.put("fuel", fuel);
                motorPremiumRequest.put("carrierType", policyDetailRecord.get("carrierType"));
                motorPremiumRequest.put("makeModelId", tm_make_model_id);
                return requestBody;
            } else {
                record.setErrorMsg("tm_make_model_id is null: make: "+make+"|  model: "+model+"|  vehicleSubClass: "+gcvPcv);
            }
        }catch (Exception e){
            System.out.println("Exception in updateMotorPremiumRequest() :: "+ e);
            record.setErrorMsg("Exception in updateMotorPremiumRequest() :: "+ e);
        }
        return null;
    }

    public static String get_tm_make_model(String make, String model, String vehicleclass) throws UnirestException, ParseException {
        if( make == null || model == null || vehicleclass == null )
            return null;
        Unirest.setTimeouts(0, 0);
        String searchMake = make.replace(" ", "%20");
        String searchModel = model.replace(" ", "%20");
        String url = "https://masters.mintpro.in/api/v1/get_tm_make_model/?searchKey="+searchMake+"%20"+searchModel+"&vehicleClass="+vehicleclass+"&vertical=CV";
        HttpResponse response = Unirest.get(url).asString();
        String data = (String) response.getBody();
        JSONParser parser = new JSONParser();
        List<Object> obj  = (List<Object>) parser.parse(data);
        JSONObject jobj = null;
        String elastic_make = null;
        String elastic_model = null;
        String tm_make_model_id = null;
        for (int i=0; i<obj.size(); i++){
            jobj = (JSONObject) obj.get(i);
            elastic_make = (String) jobj.get("make");
            elastic_model = (String) jobj.get("model");
            if(elastic_make.equalsIgnoreCase(make) && elastic_model.equalsIgnoreCase(model)){
                tm_make_model_id = (String) jobj.get("make_model_id");
            }
        }
        return tm_make_model_id;
    }

    static String makePremiumRequestCall(String requestBody) throws UnirestException {
        Unirest.setTimeouts(0, 0);
        String url = "https://pro.turtlemint.com/api/platform/v0/premiums/request";
        String stagingUrl = "https://pro.joker.planturtle.com/api/platform/v0/premiums/request";
        String bantaiUrl = "https://pro.turtlemint.com/api/platform/v0/premiums/request";
        HttpResponse<String> response = Unirest.post(bantaiUrl)
                .header("authority", "pro.turtlemint.com")
                .header("pragma", "no-cache")
                .header("cache-control", "no-cache")
                .header("sec-ch-ua", "\"Chromium\";v=\"92\", \" Not A;Brand\";v=\"99\", \"Google Chrome\";v=\"92\"")
                .header("accept", "application/json, text/plain, */*")
                .header("sec-ch-ua-mobile", "?0")
                .header("user-agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_14_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/92.0.4515.159 Safari/537.36")
                .header("content-type", "application/json")
                .header("origin", "https://pro.turtlemint.com")
                .header("sec-fetch-site", "same-origin")
                .header("sec-fetch-mode", "cors")
                .header("sec-fetch-dest", "empty")
                .header("referer", "https://pro.turtlemint.com/commercial-vehicle-insurance/commercial-vehicle-profile/lead")
                .header("accept-language", "en-GB,en-US;q=0.9,en;q=0.8")
                .header("cookie", "adb=0; _gcl_au=1.1.1850871477.1627982512; _fbp=fb.1.1627982512154.1097493888; _ga=GA1.3.640835248.1627982512; _ga=GA1.2.640835248.1627982512; _hjid=91a1e9c9-eac8-4785-8f60-a0c770cc008e; sharePremiumDetails=true; redirectionData=; permissions=TAB_LEAD_WRITE,LEAD_READ,TAB_EARNINGS_READ,TAB_CUSTOMER_READ,RECENT_LEADS_READ,CUSTOMER_READ,CERTIFICATION_READ,CONTENTS_READ,CREATE_QUOTE,PROFILE_READ,HELP_CENTER,TW_VERTICAL_READ,FW_VERTICAL_READ,COMMERCIAL_VERTICAL_READ,HEALTH_VERTICAL_READ,MF_VERTICAL_READ,HEALTH_ONE_PLAN,REFER_FRIENDS_CERTIFICATE,CERTIFICATION_CONTENT_VIEW,CV_QIS,HELP_CENTER_V2,EARNINGS_V2_READ,RENEWAL_REPORT,REWARDS,OFFERS,PARTNER_PUBLIC_PROFILE,DEVICE_PROTECTION_READ,TERM_VERTICAL_READ,LIFE_VERTICAL_READ,HEALTH_CONSULTING,TAB_EARNINGS_READ,EARNINGS_READ,EARNINGS_CHILD_READ,TAB_EARNINGS_WRITE,DP_PERFORMANCE_REPORTS_READ; category=partner; pospUserName=5c90df45e4b0f2135296ddd7; tenant=turtlemint; customerId=; dealerUserName=5c90df45e4b0f2135296ddd7; permission=verified; authToken=decd39ee5725b7118d65b126c2b65111e10432682f91b04ce32b6dd58167e0c4567ccbd63ff775fe34f33bcfa3991f2e; G_ENABLED_IDPS=google; leadUtm=%7B%22source%22%3A%22(direct)%22%2C%22medium%22%3A%22(none)%22%7D; isUtmParamsChange=true; mp_somethingrandomstring_mixpanel=%7B%22distinct_id%22%3A%20%2217b0b51c1bf464-08bdb037083e34-35627202-1fa400-17b0b51c1c06fc%22%2C%22%24device_id%22%3A%20%2217b0b51c1bf464-08bdb037083e34-35627202-1fa400-17b0b51c1c06fc%22%2C%22%24initial_referrer%22%3A%20%22%24direct%22%2C%22%24initial_referring_domain%22%3A%20%22%24direct%22%7D; mp_94b72fe8fa0b0fbf2984f556ad073226_mixpanel=%7B%22distinct_id%22%3A%20%225c90df45e4b0f2135296ddd7%22%2C%22%24device_id%22%3A%20%2217b0b51c1d7286-0c93b310d115ca-35627202-1fa400-17b0b51c1d8d37%22%2C%22%24initial_referrer%22%3A%20%22%24direct%22%2C%22%24initial_referring_domain%22%3A%20%22%24direct%22%2C%22__mps%22%3A%20%7B%7D%2C%22__mpso%22%3A%20%7B%7D%2C%22__mpus%22%3A%20%7B%7D%2C%22__mpa%22%3A%20%7B%7D%2C%22__mpu%22%3A%20%7B%7D%2C%22__mpr%22%3A%20%5B%5D%2C%22__mpap%22%3A%20%5B%5D%2C%22%24user_id%22%3A%20%225c90df45e4b0f2135296ddd7%22%7D; _gid=GA1.3.725381380.1629363949; _gid=GA1.2.725381380.1629363949; _hjAbsoluteSessionInProgress=0; data=; PLAY_SESSION=4e2721f36834520e9dd47ec9a759fef9d38845d0-dealerUserName=5c90df45e4b0f2135296ddd7&pospUserName=5c90df45e4b0f2135296ddd7&tenant=turtlemint&host=http%3A%2F%2Fmotor-service%3A9000&X-Forwarded-For=61.2.3.139&x-partner-id=5c90df45e4b0f2135296ddd7&broker=turtlemint; _gat=1; _gat_UA-61873031-10=1")
                .body(requestBody)
                .asString();
        String result = response.getBody();
        return result;
    }

    public static String getRequestId() throws UnirestException {
        Unirest.setTimeouts(0, 0);
        HttpResponse<String> response = Unirest.get("https://pro.turtlemint.com/api/masters/getrequestId")
                .header("Cookie", "PLAY_SESSION=7378f75cdac2e74d6b2294739dfe874ff02aabe6-host=http%3A%2F%2Fmotor-service%3A9000&X-Forwarded-For=61.2.15.66&broker=turtlemint")
                .asString();
        String requestId = response.getBody().replace("\"", "");
        return requestId;
    }
}
