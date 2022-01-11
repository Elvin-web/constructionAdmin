package az.elvin.constructionAdmin.util;

import java.util.HashMap;
import java.util.Map;

public class CommonUtil {

    public static int getPerPage(String perpage) {
        if (perpage != null && Integer.parseInt(perpage) > 9) return Integer.parseInt(perpage);
        else return 10;
    }

    public static Map<String, Object> createDataTable(Object data, long dataCount, int page, String perPage) {
        Map<String, Object> result = new HashMap<>();
        result.put("data", data);

        long pages;
        if (dataCount % 10 == 0) pages = dataCount / 10;
        else pages = dataCount / 10 + 1;

        Map<String, Object> meta = new HashMap<>();
        meta.put("page", page);
        meta.put("pages", pages);
        meta.put("perpage", perPage);
        meta.put("total", dataCount);
        meta.put("sort", "desc");
        meta.put("field", "id");
        result.put("meta", meta);
        return result;
    }
}
