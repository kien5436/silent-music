package phamkien.silentMusic.Services;

public class APIService {

    private static String baseUrl = "http://192.168.1.5/silent_music/";

    public static DataService getService() {
        return APIRetrofitClient.getClient(baseUrl).create(DataService.class);
    }

    public static String getBaseUrl() {
        return baseUrl;
    }
}
