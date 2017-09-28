package ir.scorize.tpo.net;


import ir.scorize.tpo.net.serializers.LoginCredentials;
import ir.scorize.tpo.net.serializers.LoginResponse;
import okhttp3.*;

import java.io.IOException;

/**
 * Created by mjafar on 8/4/17.
 */
public class NetworkManager {
    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");

    private final OkHttpClient mClient = new OkHttpClient();
    private final String mBaseUrl = "https://scorize.com/api/v1/";
    private final String mLoginUrl = mBaseUrl + "login/";

    public String login(String username, String password) {
        return login(new LoginCredentials(username, password));
    }

    public String login(LoginCredentials data) {
        Request request = new Request.Builder()
                .url(mLoginUrl)
                .post(RequestBody.create(JSON, LoginCredentials.getAdapter().toJson(data)))
                .build();

        Response response = null;
        try {
            response = mClient.newCall(request).execute();
            return LoginResponse.getAdapter().fromJson(response.body().string()).getToken();
        } catch (IOException | NullPointerException e) {
            e.printStackTrace();
            return null;
        }
    }
}
