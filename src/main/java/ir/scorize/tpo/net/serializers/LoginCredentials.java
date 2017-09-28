package ir.scorize.tpo.net.serializers;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;

/**
 * Created by mjafar on 8/4/17.
 */
public final class LoginCredentials {
    private String username;
    private String password;

    public LoginCredentials() {
    }

    public LoginCredentials(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public static JsonAdapter<LoginCredentials> getAdapter() {
        Moshi moshi = new Moshi.Builder().build();
        return moshi.adapter(LoginCredentials.class);
    }
}
