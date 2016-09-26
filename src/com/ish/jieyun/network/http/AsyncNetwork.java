package com.ish.jieyun.network.http;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import java.lang.Thread.UncaughtExceptionHandler;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 异步请求
 */
public class AsyncNetwork {
    private List<AsyncTask> taskQueue;//任务队列
    private boolean isRunning = false;
    private static AsyncNetwork instance = null;

    /**
     * 取到异步加载的实例
     *
     * @return
     */
    public static AsyncNetwork getInstance() {
        if (instance == null) {
            synchronized (new Object()) {
                instance = new AsyncNetwork();
            }
        }
        return instance;
    }

    /**
     * 构造函数
     */
    public AsyncNetwork() {
        taskQueue = new ArrayList<AsyncTask>();
        isRunning = true;
        Thread t = new Thread(runnable);
        t.setDefaultUncaughtExceptionHandler(new UncaughtExceptionHandler() {

            @Override
            public void uncaughtException(Thread thread, Throwable ex) {
                ex.printStackTrace();
            }
        });
        t.start();
    }

    /**
     * 执行一个后台调用
     *
     * @param params
     * @param callback
     */
    public synchronized void invoke(Context context, String url, Map<String, String> params, InvokeCallback callback) {
        AsyncTask task = new AsyncTask();
        task.context = context;
        task.url = url;
        task.params = params;
        task.callback = callback;
        taskQueue.add(task);
        // 唤醒任务下载队列
        synchronized (runnable) {
            runnable.notify();
        }
    }

    /**
     * 回调接口
     */
    public interface InvokeCallback {
        //成功
        void doSuccess(Map<String, Object> result);

        //失败
        void doError(Map<String, Object> result);
    }

    private Runnable runnable = new Runnable() {

        @Override
        public void run() {
            while (isRunning) {
                while (taskQueue.size() > 0) {
                    AsyncTask task = taskQueue.remove(0);
                    try {
                        Map<String, Object> result = (Map<String, Object>)ResolveDataLayer.resolveResponeObject(RealNetworkLayer.getCommonObject(task.url, ParamsDealLayer.addDefaultParams(task.context,task.params, task.url)));
                        task.result = result;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if (handler != null) {
                        // 创建消息对象，并将完成的任务添加到消息对象中
                        Message msg = handler.obtainMessage();
                        msg.obj = task;
                        // 发送消息回主线程
                        handler.sendMessage(msg);
                    }
                }
            }
        }
    };

    /**
     * 异步handler
     */
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            // 子线程中返回的下载完成的任务
            AsyncTask task = (AsyncTask) msg.obj;
            // 调用callback对象的execute方法
            task.callback.doSuccess(task.result);
        }

    };

    /**
     * 异步任务bean
     */
    class AsyncTask {
        Context context;// 上下文对象
        String url;// 网址
        Map<String, String> params;// 任务参数
        Map<String, Object> result;// 返回结果
        InvokeCallback callback;// 回调对象
    }
}
