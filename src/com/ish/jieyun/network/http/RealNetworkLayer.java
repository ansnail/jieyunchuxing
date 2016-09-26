package com.ish.jieyun.network.http;

import java.lang.ref.WeakReference;
import java.util.Map;

import okhttp3.Response;
import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;

import com.ish.jieyun.network.NetConfig;
import com.ish.jieyun.network.utils.ObjEarth;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.builder.PostFormBuilder;
import com.zhy.http.okhttp.request.RequestCall;


public class RealNetworkLayer extends AsyncTask<Object, Integer, String> {

    private Context context;
    private int tag = -1;
    private IBindData bindData = null;

    /**
     * 构造方法
     *
     * @param bindData
     */
    public RealNetworkLayer(IBindData bindData) {
        this.bindData = bindData;
    }


    public static void doExecute(Context context, IBindData bind, int type, String url, Map<String, String> request) {
        doexecute(new RealNetworkLayer(bind), context, bind, type, url, request);
    }

    @SuppressLint("NewApi")
    public static <T> void doexecute(final AsyncTask<T, ?, ?> task, final T... args) {
        final WeakReference<AsyncTask<T, ?, ?>> taskReference = new WeakReference<AsyncTask<T, ?, ?>>(task);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.DONUT) {
            throw new UnsupportedOperationException("This class can only be used on API 4 and newer.");
        }

        try {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
                taskReference.get().execute(args);
            } else {
                taskReference.get().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, args);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    protected String doInBackground(Object... params) {

        // 上下文环境变量
        if (params[0] instanceof Context) {
            context = (Context) params[0];
        } else {
            return null;
        }
        // 回调接口对象
        if (params[1] instanceof IBindData) {
            bindData = (IBindData) params[1];
        } else {
            return null;
        }
        // 消息唯一标识
        if (params[2] instanceof Integer) {
            tag = (Integer) params[2];
        }
        // 接口完整地址
        String url = null;
        if (params[3] instanceof String) {
            url = (String) params[3];
        }
        // 请求参数
        Map<String, String> request = null;
        if (params[4] instanceof Map<?, ?>) {
            request = (Map<String, String>) params[4];
        }
        String resultObj = getCommonObject(url, request);

        return resultObj;
    }


    @Override
    protected void onPostExecute(String result) {
        //解析数据
        try {
            ResolveDataLayer.resolveResponeData(context, result, bindData, tag);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    protected void onCancelled() {
        super.onCancelled();
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
    }


    public static String getCommonObject(String url, Map<String, String> request) {
        url = NetConfig.getServiceUrl() + url;
        PostFormBuilder pfb = OkHttpUtils.getInstance().post().params(request).url(url);
//        PostStringBuilder pfb = OkHttpUtils.postString()
//							        		.url(url)
//							        		.content("param="+new JSONObject(request).toString())
//					        				.mediaType(MediaType.parse("application/x-www-form-urlencoded; charset=UTF-8"));
        RequestCall call = pfb.build().connTimeOut(ObjEarth.CONNTIMEOUT);

        try {
            //网络请求
            Response response = call.execute();
            String content = response.body().string();
            String code = response.code() + "";
            if ("200".equals(code) && (content != null)) { // 数据是否有错
                return content;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return "";
    }

}
