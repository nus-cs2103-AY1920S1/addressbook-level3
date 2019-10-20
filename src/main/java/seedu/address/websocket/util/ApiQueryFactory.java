package seedu.address.websocket.util;

public class ApiQueryFactory {
    private String url;
    private String cachePath;
    public ApiQueryFactory(String url, String cachePath) {
        this.url = url;
        this.cachePath = cachePath;
    }

    /**
     * This method is used to return queryResult if present else return queryResult from API
     * call and save the response to the cache.
     * @return
     */
    public QueryResult execute() {
        QueryResult queryResult;
        queryResult = new CacheApiQuery(url, cachePath).execute();
        if (queryResult.getResponseCode() != 200) {
            queryResult = new ApiQuery(url, cachePath).execute();
        }

        return queryResult;
    }
}
