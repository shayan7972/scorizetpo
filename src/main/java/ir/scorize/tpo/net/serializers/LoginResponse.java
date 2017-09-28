package ir.scorize.tpo.net.serializers;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;

/**
 * Created by mjafar on 8/4/17.
 */
public class LoginResponse {
    private String token;

    public static JsonAdapter<LoginResponse> getAdapter() {
        Moshi moshi = new Moshi.Builder().build();
        return moshi.adapter(LoginResponse.class);
    }

    public String getToken() {
        return token;
    }
}
