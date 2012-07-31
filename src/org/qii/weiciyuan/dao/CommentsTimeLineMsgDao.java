package org.qii.weiciyuan.dao;

import android.text.TextUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.qii.weiciyuan.support.http.HttpMethod;
import org.qii.weiciyuan.support.http.HttpUtility;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: qii
 * Date: 12-7-29
 * Time: 下午1:17
 * To change this template use File | Settings | File Templates.
 */
public class CommentsTimeLineMsgDao {

    private String getMsgs() {
        String msg = "";

        String url = URLHelper.getMyCommentsTimeLine();

        Map<String, String> map = new HashMap<String, String>();

        msg = HttpUtility.getInstance().execute(HttpMethod.Get, url, map);

        return msg;
    }


    public List<Map<String, String>> getMsgList() {
        List<Map<String, String>> list = new ArrayList<Map<String, String>>();
        String msg = getMsgs();

        try {
            JSONObject jsonObject = new JSONObject(msg);
            JSONArray statuses = jsonObject.getJSONArray("statuses");
            int length = statuses.length();
            for (int i = 0; i < length; i++) {
                JSONObject object = statuses.getJSONObject(i);
                Map<String, String> map = new HashMap<String, String>();
                map.put("id", object.optString("id"));
                map.put("text", object.optString("text"));
                Iterator iterator = object.keys();
                String key;
                while (iterator.hasNext()) {
                    key = (String) iterator.next();
                    Object value = object.opt(key);
                    if (value instanceof String) {
                        map.put(key, value.toString());
                    } else if (value instanceof JSONObject) {
                        map.put("screen_name", ((JSONObject) value).optString("screen_name"));
                    }
                }
                list.add(map);
            }

        } catch (JSONException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        return list;
    }

    private String access_token;


    public CommentsTimeLineMsgDao(String access_token) {
        if (TextUtils.isEmpty(access_token))
            throw new IllegalArgumentException();
        this.access_token = access_token;
    }

}
