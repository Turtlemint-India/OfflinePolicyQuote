package OfflineQuotes.services;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

public class premiumResultService {
    public static String makePremiumResultCall(String insurers, String policyType, String requestId, String vertical, String uid) throws UnirestException {
        Unirest.setTimeouts(0, 0);
//        String url = "https://pro.turtlemint.com/api/platform/v0/premiums/result?" +
//                "keyList=ICICILOMBARD,UNISOMPO,RELI,HDFC,NINA,DIGIT,UNTD" +
//                "&policyType=comprehensive" +
//                "&requestId=MGFF7XL40GQ" +
//                "&type=quote" +
//                "&uid=14481bb3-9a86-4d4f-b206-b8caaff241d5" +
//                "&vertical=CV";
        String url = "https://pro.turtlemint.com/api/platform/v0/premiums/result?" +
                "keyList="+ insurers +
                "&policyType="+ policyType +
                "&requestId="+ requestId +
                "&type=quote" +
                "&uid=" + uid +
                "&vertical="+ vertical;
        HttpResponse<String> response = Unirest.get(url)
                .header("authority", "pro.turtlemint.com")
                .header("pragma", "no-cache")
                .header("cache-control", "no-cache")
                .header("sec-ch-ua", "\"Chromium\";v=\"92\", \" Not A;Brand\";v=\"99\", \"Google Chrome\";v=\"92\"")
                .header("accept", "application/json, text/plain, */*")
                .header("sec-ch-ua-mobile", "?0")
                .header("user-agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_14_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/92.0.4515.159 Safari/537.36")
                .header("sec-fetch-site", "same-origin")
                .header("sec-fetch-mode", "cors")
                .header("sec-fetch-dest", "empty")
                .header("referer", "https://pro.turtlemint.com/commercial-vehicle-insurance/commercial-vehicle-profile/lead")
                .header("accept-language", "en-GB,en-US;q=0.9,en;q=0.8")
                .header("cookie", "adb=0; _gcl_au=1.1.1850871477.1627982512; _fbp=fb.1.1627982512154.1097493888; _ga=GA1.3.640835248.1627982512; _ga=GA1.2.640835248.1627982512; _hjid=91a1e9c9-eac8-4785-8f60-a0c770cc008e; sharePremiumDetails=true; redirectionData=; permissions=TAB_LEAD_WRITE,LEAD_READ,TAB_EARNINGS_READ,TAB_CUSTOMER_READ,RECENT_LEADS_READ,CUSTOMER_READ,CERTIFICATION_READ,CONTENTS_READ,CREATE_QUOTE,PROFILE_READ,HELP_CENTER,TW_VERTICAL_READ,FW_VERTICAL_READ,COMMERCIAL_VERTICAL_READ,HEALTH_VERTICAL_READ,MF_VERTICAL_READ,HEALTH_ONE_PLAN,REFER_FRIENDS_CERTIFICATE,CERTIFICATION_CONTENT_VIEW,CV_QIS,HELP_CENTER_V2,EARNINGS_V2_READ,RENEWAL_REPORT,REWARDS,OFFERS,PARTNER_PUBLIC_PROFILE,DEVICE_PROTECTION_READ,TERM_VERTICAL_READ,LIFE_VERTICAL_READ,HEALTH_CONSULTING,TAB_EARNINGS_READ,EARNINGS_READ,EARNINGS_CHILD_READ,TAB_EARNINGS_WRITE,DP_PERFORMANCE_REPORTS_READ; category=partner; pospUserName=5c90df45e4b0f2135296ddd7; tenant=turtlemint; customerId=; dealerUserName=5c90df45e4b0f2135296ddd7; permission=verified; authToken=decd39ee5725b7118d65b126c2b65111e10432682f91b04ce32b6dd58167e0c4567ccbd63ff775fe34f33bcfa3991f2e; G_ENABLED_IDPS=google; leadUtm=%7B%22source%22%3A%22(direct)%22%2C%22medium%22%3A%22(none)%22%7D; isUtmParamsChange=true; mp_somethingrandomstring_mixpanel=%7B%22distinct_id%22%3A%20%2217b0b51c1bf464-08bdb037083e34-35627202-1fa400-17b0b51c1c06fc%22%2C%22%24device_id%22%3A%20%2217b0b51c1bf464-08bdb037083e34-35627202-1fa400-17b0b51c1c06fc%22%2C%22%24initial_referrer%22%3A%20%22%24direct%22%2C%22%24initial_referring_domain%22%3A%20%22%24direct%22%7D; mp_94b72fe8fa0b0fbf2984f556ad073226_mixpanel=%7B%22distinct_id%22%3A%20%225c90df45e4b0f2135296ddd7%22%2C%22%24device_id%22%3A%20%2217b0b51c1d7286-0c93b310d115ca-35627202-1fa400-17b0b51c1d8d37%22%2C%22%24initial_referrer%22%3A%20%22%24direct%22%2C%22%24initial_referring_domain%22%3A%20%22%24direct%22%2C%22__mps%22%3A%20%7B%7D%2C%22__mpso%22%3A%20%7B%7D%2C%22__mpus%22%3A%20%7B%7D%2C%22__mpa%22%3A%20%7B%7D%2C%22__mpu%22%3A%20%7B%7D%2C%22__mpr%22%3A%20%5B%5D%2C%22__mpap%22%3A%20%5B%5D%2C%22%24user_id%22%3A%20%225c90df45e4b0f2135296ddd7%22%7D; _gid=GA1.3.725381380.1629363949; _gid=GA1.2.725381380.1629363949; _hjAbsoluteSessionInProgress=0; data=; PLAY_SESSION=4e2721f36834520e9dd47ec9a759fef9d38845d0-dealerUserName=5c90df45e4b0f2135296ddd7&pospUserName=5c90df45e4b0f2135296ddd7&tenant=turtlemint&host=http%3A%2F%2Fmotor-service%3A9000&X-Forwarded-For=61.2.3.139&x-partner-id=5c90df45e4b0f2135296ddd7&broker=turtlemint; _gat=1; _gat_UA-61873031-10=1")
                .asString();
        String result = response.getBody();
        return result;
    }
}
