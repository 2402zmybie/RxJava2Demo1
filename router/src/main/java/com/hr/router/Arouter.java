package com.hr.router;

import android.app.Activity;

import java.util.HashMap;
import java.util.Map;

public class Arouter {
    //存储所有Activity的类
    private Map<String, Class<? extends Activity>> map = new HashMap<>();
    //单例
    private Arouter(){};
    private static Arouter instance = new Arouter();
    public static Arouter getInstance() {
        return instance;
    }

    public void addActivity(String key,  Class<? extends Activity> clazz) {
        if(key != null && clazz != null && !map.containsKey(key)) {
            map.put(key, clazz);
        }
    }


}
