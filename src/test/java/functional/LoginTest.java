package functional;

import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static utils.TestUtils.encodeToBase64;
import static utils.TestUtils.getStringFromEntity;

public class LoginTest {

    private static final String OANA_SABOU = "oana_sabou";
    private static final String USERNAME = OANA_SABOU;
    private static final String PASSWORD = "12345678";
    private static final String LOGIN_ENDPOINT = "/login";
    private static String requestUrl = "http://localhost:8080";

    @Test
    public void contextLoads() {
        CloseableHttpClient closeableHttpClient = HttpClients.createDefault();
        String response = null;
        try {
            response = getStringFromEntity(closeableHttpClient.execute(getLoginRequest()).getEntity().getContent());
        } catch (IOException e) {
            e.printStackTrace();
        }
        Assert.assertThat(response, is(encodeToBase64(USERNAME + ":" + PASSWORD)));
    }


    private HttpPost getLoginRequest() {
        String localhost;
        try {
            localhost = Inet4Address.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        HttpPost httpPost = new HttpPost("http://localhost:8080" + LOGIN_ENDPOINT);
        Map<String, String> map = new HashMap<>();
        map.put("username", USERNAME);
        map.put("password", PASSWORD);
        JSONObject jsonObject = new JSONObject(map);
        StringEntity stringEntity = null;

        stringEntity = new StringEntity(jsonObject.toString(), ContentType.APPLICATION_JSON);
        httpPost.setEntity(stringEntity);
        return httpPost;
    }

}
