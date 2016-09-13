package com.anthony.app.common.data.retrofit;


import com.anthony.app.common.base.Constants;

import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Url;
import rx.Observable;

/**
 * Created by Anthony on 2016/7/8.
 * Class Note:
 * {@link retrofit2.Retrofit} 使用的RemoteApi，
 */
public interface RemoteApi {
    String end_point = Constants.Remote_BASE_END_POINT;

    @GET("{url}")
    Observable<ResponseBody> loadString(@Path(value = "url", encoded = true) String url);

    @POST
    @FormUrlEncoded
    Observable<ResponseBody> postString(@Url String url, @FieldMap Map<String, String> params);
}