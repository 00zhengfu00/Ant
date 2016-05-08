package edu.com.mvpcommon.news.newsList;

import android.content.Context;
import android.view.View;

import java.util.ArrayList;

import edu.com.mvplibrary.model.Channel;

/**
 * Created by Anthony on 2016/5/3.
 * Class Note: Presenter in MVP
 * see {@link NewsListContract}-----------Manager role of MVP
 * &{@link NewsListPresenter}------------Presenter
 * &{@link NewsChannelFragment}-------------View
 * &{@link NewsListData}-----------------Model
 */
public class NewsListPresenter implements NewsListContract.Presenter, NewsListContract.onGetChannelListListener {
    private NewsListContract.View mView;
    private Context mContext;
    private NewsListData mData;

    public NewsListPresenter(NewsListContract.View mView, Context mContext) {
        this.mContext = mContext;
        this.mView = mView;
//        mView.setPresenter(this);//!!! bind presenter for View

        mData = new NewsListData(mContext, this);//!!!bind data listener to Model
    }

    @Override
    public void getData() {
        // TODO: 2016/5/6  传递给model层处理数据

        try {
//            mData.initListData("url");
            mData.parseUrl("url here");
//            mData.initListData("{\n" +
//                    "  \"type\": \"\",\n" +
//                    "  \"channels\": [\n" +
//                    "    {\n" +
//                    "      \"title\": \"nba\",\n" +
//                    "      \"type\": \"2002\",\n" +
//                    "      \"url\": \"raw://nba_data\"\n" +
//                    "    },\n" +
//                    "    {\n" +
//                    "      \"title\": \"cba\",\n" +
//                    "      \"type\": \"2002\",\n" +
//                    "      \"url\": \"raw://nba_data\"\n" +
//                    "    },\n" +
//                    "    {\n" +
//                    "      \"title\": \"足球\",\n" +
//                    "      \"type\": \"2002\",\n" +
//                    "      \"url\": \"raw://nba_data\"\n" +
//                    "    }\n" +
//                    "  ]\n" +
//                    "}");

//            mView.onDataReceived(mData.getChannels());
//            mView.onDataReceived(channels);
        } catch (Exception e) {
            this.onError();
            e.printStackTrace();
        }

    }


    @Override
    public void onSuccess() {
        mView.hideLoading();
        mView.onDataReceived(mData.getChannels());

    }

    @Override
    public void onError() {
        mView.showEmpty("data error", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
    }

    /**
     * todo empty operation now
     */
    @Override
    public void start() {

    }
}
