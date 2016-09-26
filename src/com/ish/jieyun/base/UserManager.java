package com.ish.jieyun.base;

import android.content.Context;
import android.text.TextUtils;


import java.util.Map;

import com.ish.jieyun.bean.UserInfo;
import com.ish.jieyun.utils.CommonUtils;

/**
 * @author weiyanjie
 * @Version 1.0
 * @Created time 2016/4/13 20:11.
 * @类说明:用户信息管理类
 */
public class UserManager {

    private static UserManager userManager = null;
    private static UserInfo userInfo = null;
    private Context mContext;
    
    /**
     * 内部获得用户信息类
     */
    private UserManager(Context context) {
        this.mContext = context;
        userInfo = readLocal();
    }
    
    /**
     * 获取实例
     *
     * @return
     */
    public static UserManager getInstance() {
        if (userManager == null) {
        	synchronized (UserManager.class) {
        		if (userManager == null) {
        			userManager = new UserManager(BaseApplication.getContext());
				}
			}
        }
        return userManager;
    }

    /**
     * 获取用户信息
     *
     * @return
     */
    public UserInfo getUserinfo() {
        userInfo = readLocal();
        return userInfo;
    }

    /**
     * 保存用户信息
     *
     * @param map
     */
    public void saveUserInfo(Map<String, String> map) {
        SharePreStore.saveUserInfo(mContext, CommonUtils.map2Str(map));
        userInfo = readLocal();
    }

    /**
     * 清空用户数据
     */
    public void clearUserInfo() {
        SharePreStore.clearUserInfo(mContext);
        userInfo = null;
    }


    /**
     * 用户是否登录
     *
     * @return
     */
    public boolean isLogin() {
        if (userInfo == null) {
            return false;
        }
        return true;
    }

    /**
     * 从本地获取用户对象
     *
     * @return
     */
    private UserInfo readLocal() {
        String tmp = SharePreStore.readUserInfo(mContext);
        if (TextUtils.isEmpty(tmp)) {
            return null;
        }
        return (UserInfo) CommonUtils.map2Bean(CommonUtils.str2Map(tmp), UserInfo.class);
    }


}
