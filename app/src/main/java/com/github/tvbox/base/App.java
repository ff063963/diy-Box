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
   Hawk.put(HawkConfig.HOME_REC, 2);       // Home Rec 0=豆瓣, 1=推荐, 2=历史
        Hawk.put(HawkConfig.PLAY_TYPE, 2);      // Player   0=系统, 1=IJK, 2=Exo
        Hawk.put(HawkConfig.IJK_CODEC, "硬解码");// IJK Render 软解码, 硬解码
//        Hawk.put(HawkConfig.HOME_NUM, 2);       // History Number
//          Hawk.put(HawkConfig.DOH_URL, 2);        // DNS
//          Hawk.put(HawkConfig.SEARCH_VIEW, 1);    // Text or Picture  
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
