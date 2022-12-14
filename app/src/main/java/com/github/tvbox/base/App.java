package com.github.tvbox.base;

import android.app.Activity;
import androidx.multidex.MultiDexApplication;

import com.github.tvbox.bean.VodInfo;
import com.github.tvbox.callback.EmptyCallback;
import com.github.tvbox.callback.LoadingCallback;
import com.github.tvbox.data.AppDataManager;
import com.github.tvbox.server.ControlManager;
import com.github.tvbox.util.AppManager;
import com.github.tvbox.util.EpgUtil;
import com.github.tvbox.util.FileUtils;
import com.github.tvbox.util.HawkConfig;
import com.github.tvbox.util.OkGoHelper;
import com.github.tvbox.util.PlayerHelper;
import com.github.tvbox.util.js.JSEngine;
import com.kingja.loadsir.core.LoadSir;
import com.orhanobut.hawk.Hawk;

import me.jessyan.autosize.AutoSizeConfig;
import me.jessyan.autosize.unit.Subunits;

/**
 * @author pj567
 * @date :2020/12/17
 * @description:
 */
public class App extends MultiDexApplication {
    private static App instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        initParams();
        // OKGo
        OkGoHelper.init(); //台标获取
        EpgUtil.init();
        // 初始化Web服务器
        ControlManager.init(this);
        //初始化数据库
        AppDataManager.init();
        LoadSir.beginBuilder()
                .addCallback(new EmptyCallback())
                .addCallback(new LoadingCallback())
                .commit();
        AutoSizeConfig.getInstance().setCustomFragment(true).getUnitsManager()
                .setSupportDP(false)
                .setSupportSP(false)
                .setSupportSubunits(Subunits.MM);
        PlayerHelper.init();
        JSEngine.getInstance().create();
        FileUtils.cleanPlayerCache();
    }

    private void initParams() {
        // Hawk
        Hawk.init(this).build();
        Hawk.put(HawkConfig.DEBUG_OPEN, false);
        if (!Hawk.contains(HawkConfig.PLAY_TYPE)) {
            Hawk.put(HawkConfig.PLAY_TYPE, 1);
        }
    }

    public static App getInstance() {
        return instance;
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        JSEngine.getInstance().destroy();
    }


    private VodInfo vodInfo;
    public void setVodInfo(VodInfo vodinfo){
        this.vodInfo = vodinfo;
    }
    public VodInfo getVodInfo(){
        return this.vodInfo;
    }

    public Activity getCurrentActivity() {
        return AppManager.getInstance().currentActivity();
    }
}