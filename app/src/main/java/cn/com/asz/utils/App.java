package cn.com.asz.utils;

import android.app.Application;

import java.util.Map;

/**
 * Created by nijian on 2015/2/28.
 * 
 * 这个类的作用是控制连接内网和外网的切换
 */

//用于设置全局变量
public class App extends Application {

    // 用于存放倒计时时间
    public static Map<String, Long> map;
    String interfaceUrl;//用于控制内网和外网的url连接

    @Override
    public void onCreate() {
        // TODO Auto-generated method stub
        super.onCreate();
        //切换为外网连接接口
        setInterfaceUrl("http://test.gyb365.com:4326/");
        //切换为内网连接接口
//        setInterfaceUrl("http://192.168.1.101:8090/");
        //切换为内网张群PC接口
//        setInterfaceUrl("http://192.168.1.121:8080/");
        //新的内网接口
//        setInterfaceUrl("http://192.168.1.66:8090/");
    }

    public String getInterfaceUrl() {
        return interfaceUrl;
    }

    public void setInterfaceUrl(String interfaceUrl) {
        this.interfaceUrl = interfaceUrl;
    }

}