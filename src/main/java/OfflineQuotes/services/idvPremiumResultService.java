package OfflineQuotes.services;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

public class idvPremiumResultService {

    public static String makeIdvPremiumResultCall() throws UnirestException {
        /*
        * Need to change url and request id in referer
        * */
        Unirest.setTimeouts(0, 0);
        HttpResponse<String> response = Unirest.get("https://pro.turtlemint.com/api/platform/v0/premiums/result?keyList=ICICILOMBARD,UNISOMPO,RELI,HDFC,NINA,DIGIT,UNTD&policyType=comprehensive&requestId=MGGPAUFPRJ7&type=quote&uid=b8c81cb7-e13c-4923-a8da-8fbe4e128f1d&vertical=CV")
                .header("authority", "pro.turtlemint.com")
                .header("pragma", "no-cache")
                .header("cache-control", "no-cache")
                .header("sec-ch-ua", "\"Chromium\";v=\"94\", \"Google Chrome\";v=\"94\", \";Not A Brand\";v=\"99\"")
                .header("accept", "application/json, text/plain, */*")
                .header("sec-ch-ua-mobile", "?0")
                .header("user-agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_14_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/94.0.4606.61 Safari/537.36")
                .header("sec-ch-ua-platform", "\"macOS\"")
                .header("sec-fetch-site", "same-origin")
                .header("sec-fetch-mode", "cors")
                .header("sec-fetch-dest", "empty")
                .header("referer", "https://pro.turtlemint.com/commercial-vehicle-insurance/results/MGGPAUFPRJ7")
                .header("accept-language", "en-GB,en-US;q=0.9,en;q=0.8")
                .header("cookie", "adb=0; _gcl_au=1.1.1850871477.1627982512; _fbp=fb.1.1627982512154.1097493888; _ga=GA1.3.640835248.1627982512; _ga=GA1.2.640835248.1627982512; _hjid=91a1e9c9-eac8-4785-8f60-a0c770cc008e; G_ENABLED_IDPS=google; showActivityPoints=true; rl_page_init_referrer=RudderEncrypt%3AU2FsdGVkX1%2BgkIsnn759i0lOufPAdJ9q4A0dKZvM9sc%3D; rl_page_init_referring_domain=RudderEncrypt%3AU2FsdGVkX18wH3Y7q3qszcdLOXNZBauqAWFcpkgyAUY%3D; __zlcmid=16AkLRPZDRBnVFm; _gid=GA1.3.213860080.1632889535; _gid=GA1.2.213860080.1632889535; isUtmParamsChange=false; mp_somethingrandomstring_mixpanel=%7B%22distinct_id%22%3A%20%2217b0b51c1bf464-08bdb037083e34-35627202-1fa400-17b0b51c1c06fc%22%2C%22%24device_id%22%3A%20%2217b0b51c1bf464-08bdb037083e34-35627202-1fa400-17b0b51c1c06fc%22%2C%22%24initial_referrer%22%3A%20%22%24direct%22%2C%22%24initial_referring_domain%22%3A%20%22%24direct%22%7D; mp_94b72fe8fa0b0fbf2984f556ad073226_mixpanel=%7B%22distinct_id%22%3A%20%225c90df45e4b0f2135296ddd7%22%2C%22%24device_id%22%3A%20%2217b0b51c1d7286-0c93b310d115ca-35627202-1fa400-17b0b51c1d8d37%22%2C%22%24initial_referrer%22%3A%20%22%24direct%22%2C%22%24initial_referring_domain%22%3A%20%22%24direct%22%2C%22__mps%22%3A%20%7B%7D%2C%22__mpso%22%3A%20%7B%7D%2C%22__mpus%22%3A%20%7B%7D%2C%22__mpa%22%3A%20%7B%7D%2C%22__mpu%22%3A%20%7B%7D%2C%22__mpr%22%3A%20%5B%5D%2C%22__mpap%22%3A%20%5B%5D%2C%22%24user_id%22%3A%20%225c90df45e4b0f2135296ddd7%22%7D; rl_user_id=RudderEncrypt%3AU2FsdGVkX1%2BuK14hzm3NXiDRwPqZKF8Zr6Dt%2BSp41e8%3D; rl_anonymous_id=RudderEncrypt%3AU2FsdGVkX1%2BPKblaOg%2B0U%2F%2BKSZomCjxImfipJYyXTfWbKGJ%2BiVg5UFQeyY8KfUg8z5hlVcnD1hjJewqavZaAcA%3D%3D; rl_group_id=RudderEncrypt%3AU2FsdGVkX19g6U6BOqvxBF8lJ1Fl0UzhMwf0vPV9xsQ%3D; rl_trait=RudderEncrypt%3AU2FsdGVkX18zCHIDqEj%2FjJj4LEfLpl4zhL%2FRidAqCGI%3D; rl_group_trait=RudderEncrypt%3AU2FsdGVkX19sAF%2BJTv6L8W7MHRG1sL6Onyim%2BDKH3LU%3D; data=; PLAY_SESSION=2a9dea431b5ccdd472c9b125987c034de626ae9e-host=http%3A%2F%2Fmotor-service%3A9000&X-Forwarded-For=117.221.94.64&broker=turtlemint&x-partner-id=5c90df45e4b0f2135296ddd7; _gat=1; _gat_UA-89517885-1=1; _gat_UA-61873031-5=1")
                .asString();
        String result = response.getBody();
        return result;
    }
}
