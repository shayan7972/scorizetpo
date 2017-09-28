package ir.scorize.tpo;

/**
 * Created by mjafar on 8/4/17.
 */
public class DataManager {
    private String mToken;
//    public static final String kDataFolder = "testData";
    public static final String kDataFolder = "/home/mjafar/Documents/ScorizeTPO/testData";

    public DataManager() {
        // Load data
        // TODO: for debug, remove it.
        mToken = "";
    }

    public boolean hasToken() {
        return mToken != null;
    }

    public String getToken() {
        return mToken;
    }

    public void setToken(String token) {
        mToken = token;
    }

    public void save() {
        // TODO
    }
}
