package com.HWHH.henry.goflower_viewing;

import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.apache.http.Header;

import java.util.ArrayList;

public class GetPlayList {

    private static AsyncHttpClient client = new AsyncHttpClient();

    public void execute(String version, final OnResponseListener onResponseListener) {

        final Response response = new Response();

        client.get("http://54.65.179.119:3333/getPlayList/" + version, new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String xmlData = new String(responseBody);
                response.add("playList", xmlData);
                onResponseListener.onResponse(response);

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.i("test", "getArticleList from server failed");

            }

        });

    }

}