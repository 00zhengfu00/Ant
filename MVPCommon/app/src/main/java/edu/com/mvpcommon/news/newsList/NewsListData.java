package edu.com.mvpcommon.news.newsList;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import edu.com.mvplibrary.model.Channel;
import edu.com.mvplibrary.model.config.Constants;
import edu.com.mvplibrary.model.http.HttpUtil;
import edu.com.mvplibrary.model.http.callback.StringHttpCallback;
import edu.com.mvplibrary.model.http.request.HttpRequest;
import edu.com.mvplibrary.util.JSONObjectHelper;

/**
 * Created by Anthony on 2016/5/3.
 * Class Note:
 * news channel data
 * "raw://news_channels   raw://nba_list"
 */
public class NewsListData {


    private ArrayList<Channel> mChannels;
    private Context mContext;
    private NewsListContract.onGetChannelListListener mListener;

    public NewsListData(Context mContext, NewsListContract.onGetChannelListListener mListener) {
        this.mContext = mContext;
        this.mListener = mListener;

        mChannels = new ArrayList<>();
    }

    /**
     * // TODO: 2016/5/4 根据传递的url,在此进行网络数据访问, 将数据转换为channel数据
     * @param url
     * @throws JSONException
     */
    public void parseUrl(String url) {

        HttpRequest.Builder builder = new HttpRequest.Builder();
        HttpRequest request = builder.url(url).build();
        HttpUtil.getInstance().loadString(request, new StringHttpCallback() {
            @Override
            public void onResponse(String response) {
                initListData(response);
            }

            @Override
            public void onError(String error) {
               mListener.onError();
            }
        });

//        for(int i=0;i<=2;i++){
//            Channel channel=new Channel();
//            channel.setType("2002");
//            channel.setTitle("nba"+i);
//            mChannels.add(channel);
//        }
//        if (mChannels != null) {
//            mListener.onSuccess();
//        } else {
//            mListener.onError();
//        }


    }

    public void initListData(String objStr)  {
        try {
            this.initListData(new JSONObject(objStr));
        } catch (JSONException e) {
            mListener.onError();
        }
    }

    public void initListData(JSONObject obj) throws JSONException {

        JSONObjectHelper helper = new JSONObjectHelper(obj);
        JSONArray array = helper.getJSONArray(Constants.CHANNEL_NAMES, null);
        for (int i = 0; i < array.length(); i++) {
            Channel channel = new Channel(array.getJSONObject(i));
            mChannels.add(channel);
        }
        if (mChannels != null) {
            mListener.onSuccess();
        } else {
            mListener.onError();
        }

    }

    public ArrayList<Channel> getChannels() {
        return mChannels;
    }
}
