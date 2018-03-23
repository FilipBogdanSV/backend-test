package functional;

import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;
import utils.database.DatabaseOperations;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import static org.hamcrest.core.Is.is;

public class RegisterTest {
    private static final String REGISTER_ENDPOINT = "/register";
    private static String USERNAME = UUID.randomUUID().toString().substring(1, 6);
    private static String PASSWORD = UUID.randomUUID().toString().substring(1, 6);
    private static final String MAIL = UUID.randomUUID().toString().substring(1, 6);
    //testing purpose

    public List<String> getUsersFromDatabase() throws SQLException {
        List<String> userNames = new ArrayList<>();
        DatabaseOperations databaseOperations = DatabaseOperations.getDatabaseService();
        ResultSet resultSet = databaseOperations.queryUsers("Users");
        while (resultSet.next()) {
            userNames.add(resultSet.getString("username"));
        }
        return userNames;
    }

    @Test
    public void testRegisterSistem() {
        CloseableHttpClient closeableHttpClient = HttpClients.createDefault();
        List<String> usernames = null;
        try {
            closeableHttpClient.execute(getRegisterRequest());
            usernames = getUsersFromDatabase();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        final StringBuilder foundValue = new StringBuilder();
        if (usernames != null) {
            usernames.stream().filter(listElement -> listElement.equals(USERNAME)).forEach(foundValue::append);
        }
        Assert.assertThat(foundValue.toString(), is(USERNAME));
    }

    private HttpPost getRegisterRequest() {
        HttpPost httpPost = new HttpPost("http://localhost:8080" + REGISTER_ENDPOINT);
        Map<String, String> map = new HashMap<>();
        map.put("username", USERNAME);
        map.put("password", PASSWORD);
        map.put("email", MAIL);

        JSONObject jsonObject = new JSONObject(map);
        StringEntity stringEntity = null;

        stringEntity = new StringEntity(jsonObject.toString(), ContentType.APPLICATION_JSON);
        httpPost.setEntity(stringEntity);
        return httpPost;
    }
}
