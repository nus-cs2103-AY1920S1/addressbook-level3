package seedu.address.websocket.util;

import seedu.address.websocket.Cache;

/**
 * This class is used to establish connection if the API was not called before but load from cache
 * if the exact same response is available
 */
public class CacheApiQuery {
    private String cachePath;
    private String url;

    public CacheApiQuery(String url, String cachePath) {
        this.url = UrlUtil.sanitizeApiKey(url);
        this.cachePath = cachePath;
    }

    /**
     * This method is used to get the API response from cache
     * @return
     */
    public QueryResult execute() {
        int responseCode;
        String result = null;
        try {
            result = Cache.loadFromJson(url, cachePath);
            responseCode = 200;
        } catch (NullPointerException e) {
            responseCode = 400;
        }
        return new QueryResult(responseCode, result);
    }
}
