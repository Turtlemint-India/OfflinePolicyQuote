package OfflineQuotes.services;

import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;

public class ninjaRecordService {

    private static JSONObject updateFilters(JSONObject requestBody) {
//        JSONArray filters = (JSONArray) requestBody.get("filters");
        JSONArray filters = null;
        filters.add("{\"type\":\"date-range\",\"code\":\"creation_date\",\"from\":\"1630434600000\",\"to\":\"1632249000000\"}");
        System.out.println(filters);
        return requestBody;
    }

    public static JSONObject getNinjaRequestBody() throws IOException, ParseException {
        JSONParser parser = new JSONParser();
        JSONObject requestBody = (JSONObject) parser.parse(new FileReader("/Users/gopikrishna/workspace/OfflinePolicyQuote/src/main/java/OfflineQuotes/configs/ninjaRequest.json"));
        requestBody = updateFilters(requestBody);
        return requestBody;
    }

    public static void getPolicyDetailRecords() throws UnirestException, IOException, ParseException {
//        Unirest.setTimeouts(0, 0);
//        HttpResponse<String> response = Unirest.post("https://ninja.turtlemint.com/api/getrecords?broker=turtlemint&tenant=turtlemint")
//                .header("authority", "ninja.turtlemint.com")
//                .header("accept", "application/json, text/plain, */*")
//                .header("authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJDIjoiYWRtaW4iLCJVSSI6IkNGSllVSEsyMzI4IiwiRSI6InNhZ2FyLm11bGNoYW5kYW5pQHR1cnRsZW1pbnQuY29tIiwiU0kiOiJQIiwiaXNzIjoidHVydGxlbWludCIsIlVBIjoiTW96aWxsYS81LjAgKE1hY2ludG9zaDsgSW50ZWwgTWFjIE9TIFggMTBfMTRfNikgQXBwbGVXZWJLaXQvNTM3LjM2IChLSFRNTCwgbGlrZSBHZWNrbykgQ2hyb21lLzg1LjAuNDE4My4xMDIgU2FmYXJpLzUzNy4zNiIsImlhdCI6MTYzMjMwOTg3MCwiUkEiOiIxLjM4LjIzNi43NyJ9.aBAKlh08m5E7lzjiRd0pQofQJdAuKEUaFLOicc4EYdw")
//                .header("user-agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_14_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/85.0.4183.102 Safari/537.36")
//                .header("content-type", "application/json")
//                .header("origin", "https://ninja.turtlemint.com")
//                .header("sec-fetch-site", "same-origin")
//                .header("sec-fetch-mode", "cors")
//                .header("sec-fetch-dest", "empty")
//                .header("referer", "https://ninja.turtlemint.com/mis-home/allrecords")
//                .header("accept-language", "en-GB,en;q=0.9")
//                .header("cookie", "emailDomain=turtlemint.com; loginType=google; PLATFORM=WEB_APP; broker=turtlemint; organisation=turtlemint; G_ENABLED_IDPS=google; G_AUTHUSER_H=0; rl_page_init_referrer=RudderEncrypt%3AU2FsdGVkX1%2BuQF1Vb1HMwWEfz6AZR%2FCnW6fnO5U%2FSXx4s6tkv42T8%2F6kKy9TPKN3; rl_page_init_referring_domain=RudderEncrypt%3AU2FsdGVkX19utTTSxVaLO8I9PJRI%2F07e2s2AMPwb3%2Fj20PzmSRsb2Mv4Rhj7nsRu; _gcl_au=1.1.998511309.1631795639; _fbp=fb.1.1631795638885.890503030; _ga=GA1.2.1127568081.1630929894; view=admin; category=admin; username=Sagar Mulchandani; userId=CFJYUHK2328; email=sagar.mulchandani@turtlemint.com; mobileNo=9619221483; displayName=Sagar Mulchandani; encEmail=HIIn4vr3qeky8WmwjSIddbg0ub37VZyKxDiku8x3aGDQIv59%2FreoPQ%3D%3D; mp_168a25ca5b14a859ada5d7c356ec053d_mixpanel=%7B%22distinct_id%22%3A%20%2217be3ca681754-096d9775ae301e-316b7005-13c680-17be3ca68187b8%22%2C%22%24device_id%22%3A%20%2217be3ca681754-096d9775ae301e-316b7005-13c680-17be3ca68187b8%22%2C%22__alias%22%3A%20%22sagar.mulchandani%40turtlemint.com%22%2C%22%24initial_referrer%22%3A%20%22https%3A%2F%2Fninja.turtlemint.com%2F%22%2C%22%24initial_referring_domain%22%3A%20%22ninja.turtlemint.com%22%2C%22%24user_id%22%3A%20%22sagar.mulchandani%40turtlemint.com%22%7D; qisReadOnly=true; dealerUserName=5ec210080adef71aba73fa03; tenant=turtlemint; requestId=MGGFN15HHQN; filedClaimLastYear=false; PLAY_SESSION=49fe43a2fd79dd8983479c48d155f787365685a9-displayName=Sagar+Mulchandani&dealerUserName=5ec210080adef71aba73fa03&email=sagar.mulchandani%40turtlemint.com&username=Sagar+Mulchandani&tenant=turtlemint&host=http%3A%2F%2Fninja.turtlemint.com&mobileNo=9619221483&misUser=userId%23CFJYUHK2328%23%23category%23admin&broker=turtlemint&category=admin&userId=CFJYUHK2328&view=admin; newQIS=false; _gid=GA1.2.839636823.1632232588; mp_somethingrandomstring_mixpanel=%7B%22distinct_id%22%3A%20%2217bee997332894-0cc83aa3f1c803-316b7005-13c680-17bee9973336a%22%2C%22%24device_id%22%3A%20%2217bee997332894-0cc83aa3f1c803-316b7005-13c680-17bee9973336a%22%2C%22%24initial_referrer%22%3A%20%22https%3A%2F%2Fninja.turtlemint.com%2F%22%2C%22%24initial_referring_domain%22%3A%20%22ninja.turtlemint.com%22%7D; mp_94b72fe8fa0b0fbf2984f556ad073226_mixpanel=%7B%22distinct_id%22%3A%20%225ec210080adef71aba73fa03%22%2C%22%24device_id%22%3A%20%2217bee99733e70b-0cedbb67d948f1-316b7005-13c680-17bee9973407f7%22%2C%22%24initial_referrer%22%3A%20%22https%3A%2F%2Fninja.turtlemint.com%2F%22%2C%22%24initial_referring_domain%22%3A%20%22ninja.turtlemint.com%22%2C%22__mps%22%3A%20%7B%7D%2C%22__mpso%22%3A%20%7B%7D%2C%22__mpus%22%3A%20%7B%7D%2C%22__mpa%22%3A%20%7B%7D%2C%22__mpu%22%3A%20%7B%7D%2C%22__mpr%22%3A%20%5B%5D%2C%22__mpap%22%3A%20%5B%5D%2C%22%24user_id%22%3A%20%225ec210080adef71aba73fa03%22%7D; ninjauser={\"email\":\"sagar.mulchandani@turtlemint.com\",\"fullName\":\"Sagar Mulchandani\",\"dp\":\"https://lh3.googleusercontent.com/a-/AOh14GggcQgVFEAZBGGuve_ArkSX78AlhLXi20RHGXIJ=s96-c\",\"turtlemint_google_id_token\":\"eyJhbGciOiJSUzI1NiIsImtpZCI6ImMzMTA0YzY4OGMxNWU2YjhlNThlNjdhMzI4NzgwOTUyYjIxNzQwMTciLCJ0eXAiOiJKV1QifQ.eyJpc3MiOiJhY2NvdW50cy5nb29nbGUuY29tIiwiYXpwIjoiNzg2NDYxNDE0MTA5LWI3ZDNrcDhxM2c1bzl0bGx2dG9tYWF0bmlucWc3dG5rLmFwcHMuZ29vZ2xldXNlcmNvbnRlbnQuY29tIiwiYXVkIjoiNzg2NDYxNDE0MTA5LWI3ZDNrcDhxM2c1bzl0bGx2dG9tYWF0bmlucWc3dG5rLmFwcHMuZ29vZ2xldXNlcmNvbnRlbnQuY29tIiwic3ViIjoiMTA5NjY1MjAwMTkxOTM5NzE3NTkyIiwiaGQiOiJ0dXJ0bGVtaW50LmNvbSIsImVtYWlsIjoic2FnYXIubXVsY2hhbmRhbmlAdHVydGxlbWludC5jb20iLCJlbWFpbF92ZXJpZmllZCI6dHJ1ZSwiYXRfaGFzaCI6Im43MmdWVEZucGJDTi1OTkRjR21HUEEiLCJuYW1lIjoiU2FnYXIgTXVsY2hhbmRhbmkiLCJwaWN0dXJlIjoiaHR0cHM6Ly9saDMuZ29vZ2xldXNlcmNvbnRlbnQuY29tL2EtL0FPaDE0R2dnY1FnVkZFQVpCR0d1dmVfQXJrU1g3OEFsaExYaTIwUkhHWElKPXM5Ni1jIiwiZ2l2ZW5fbmFtZSI6IlNhZ2FyIiwiZmFtaWx5X25hbWUiOiJNdWxjaGFuZGFuaSIsImxvY2FsZSI6ImVuIiwiaWF0IjoxNjMyMzA5OTU3LCJleHAiOjE2MzIzMTM1NTcsImp0aSI6ImNhZGEyMjhlODFiMWNiMzczYzdiZWVmNjhiMDFmNjAzNTI5ODllNDEifQ.Z9vWchKcB0FlAirQBJabV9ih05DBIs4hm0idtcfguJTyavbW97I-skDvAqoNibOUY9r_PNf2v-XOqWKLHOO1ChDSUUiHd-tGJ-buG1SfbjxIhU6W75Gwrx-Oq13Tlson3sfKGrfkdit_BHPVPq51NJYzwajGM1ZENlyMTKqi3TpHz8RO2-Tias6WdIY8LrSE2RZk9FbD5x1SFiS4aBe9r8VrrMELtJnLVqymywybdsnTC0UI3Hdi2xuydsZBz5wVOBNgYXceXJgWjGhZXgyqE9eXnksjBhLtPUoDv7fPaCofJdcnqyOT5c_MArKjVxhrHaqJ3AZ5MNbyCkFH-UJ94Q\",\"expires_at\":1632313556627,\"category\":\"admin\"}; rl_user_id=RudderEncrypt%3AU2FsdGVkX1%2Bp2eD9v6O6oQ%2BcNNsTk3RtsUnb%2FCd2IQmGKJfxmj4M3538osB%2FsAbTwOQF%2F7nZDW72sTsLTDl2BA%3D%3D; rl_anonymous_id=RudderEncrypt%3AU2FsdGVkX1%2FR7FAHymqOB0U9Gsx1baJzqv%2FvhTNGHCQ4ELlsFKLzok%2FfNBWhl3nZ0HR%2FuIHw0IaZ2pKQdOJiAw%3D%3D; rl_group_id=RudderEncrypt%3AU2FsdGVkX1%2B4LUYStJnxrH6%2BgeNKWRX9ZqCCKNTYWeU%3D; rl_trait=RudderEncrypt%3AU2FsdGVkX1%2FDFgzhZ3LWIIj6%2FdUtt8N%2BeyHbEIE7Wnw%3D; rl_group_trait=RudderEncrypt%3AU2FsdGVkX1%2BZJSkBvkV7XBLOgAfVvF%2FQXFsNEguCvXk%3D; PLAY_SESSION=49fe43a2fd79dd8983479c48d155f787365685a9-displayName=Sagar+Mulchandani&dealerUserName=5ec210080adef71aba73fa03&email=sagar.mulchandani%40turtlemint.com&username=Sagar+Mulchandani&tenant=turtlemint&host=http%3A%2F%2Fninja.turtlemint.com&mobileNo=9619221483&misUser=userId%23CFJYUHK2328%23%23category%23admin&broker=turtlemint&category=admin&userId=CFJYUHK2328&view=admin")
//                .body("{\n    \"page\":1,\n    \"limit\":500,\n    \"pageType\":\"ALLRECORDS\",\n    \"sort\":[],\n    \"filters\":[\n        {\"type\":\"multi-select\",\"code\":\"creation_source\"},\n        {\"type\":\"multi-select\",\"code\":\"channel_type\"},\n        {\"type\":\"multi-select\",\"code\":\"vertical\",\"values\":[\"CV\"]},\n        {\"type\":\"multi-select\",\"code\":\"insurer\"},\n        {\"type\":\"multi-select\",\"code\":\"policy_status\"},\n        {\"type\":\"multi-select\",\"code\":\"pre_sale_verification_status\"},\n        {\"type\":\"multi-select\",\"code\":\"source_of_sale_status\"},\n        {\"type\":\"multi-select\",\"code\":\"salesDetail.business_channel\"},\n        {\"type\":\"date-range\",\"code\":\"creation_date\",\"from\":\"1630434600000\",\"to\":\"1632249000000\"}\n    ]\n}")
//                .asString();
        getNinjaRequestBody();
    }
}
