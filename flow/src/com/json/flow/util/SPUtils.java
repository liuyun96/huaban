package com.json.flow.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import java.util.List;

/**
 * SharedPreferences 轻量级存储工具
 */
public class SPUtils {
    private static SharedPreferences m_wsp = null;
    private static Editor m_weditor;
    public static final String SPLITTAG = "&&";

    /**
     * 实例化SharedPreferences对象
     *
     * @param context : 上下文对象
     * @param spName  : SharedPreferences存储名称
     * @param spMode  : SharedPreferences存储模式
     * @return SharedPreferences对象
     */
    public static SharedPreferences getInstance(Context context, String spName, int spMode) {
        if (m_wsp == null) {
            m_wsp = context.getSharedPreferences(spName, spMode);
        }
        return m_wsp;
    }

    /**
     * 向SharedPreferences对象存储
     *
     * @param spfs         : SharedPreferences对象
     * @param keyValueList : 存储的键值对,格式如:"key:value"
     */
    public static void setSharedPreferences(SharedPreferences spfs, List<String> keyValueList) {
        m_weditor = spfs.edit();
        for (String str : keyValueList) {
            String[] temp = str.split(SPLITTAG);
            m_weditor.putString(temp[0], temp[1]);
        }
        m_weditor.commit();
    }

    /**
     * 向SharedPreferences对象存储
     *
     * @param spfs     : SharedPreferences对象
     * @param keyValue : 存储的键值对
     */
    public static void setSharedPreferences(SharedPreferences spfs, String keyValue) {
        m_weditor = spfs.edit();
        String[] temp = keyValue.split(SPLITTAG);
        m_weditor.putString(temp[0], temp[1]);
        m_weditor.commit();
    }

    /**
     * 获取SharedPreferences对象中的值
     *
     * @param spfs         : SharedPreferences对象
     * @param key          : 存储键值对的键
     * @param defaultValue : 默认值
     * @return 存储键值对的值
     */
    public static String getSharedPreferences(SharedPreferences spfs, String key, String defaultValue) {
        return spfs.getString(key, defaultValue);
    }

    /**
     * 移除SharedPreferences对象中的值
     *
     * @param spfs     : SharedPreferences对象
     * @param keysList : 存储键值对的键集合
     */
    public static void removeSharedPreferences(SharedPreferences spfs, List<String> keysList) {
        m_weditor = spfs.edit();
        for (String key : keysList) {
            m_weditor.remove(key);
        }
        m_weditor.commit();
    }
}

