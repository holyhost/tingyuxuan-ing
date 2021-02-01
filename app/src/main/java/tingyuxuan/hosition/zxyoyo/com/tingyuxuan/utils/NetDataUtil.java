package tingyuxuan.hosition.zxyoyo.com.tingyuxuan.utils;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by Administrator on 2018/4/5.
 */

public class NetDataUtil {


    public  static int loginToServer(String name,String pwd){
        OkHttpClient okHttpClient = new OkHttpClient().newBuilder().connectTimeout(10, TimeUnit.SECONDS).build();
        RequestBody body= new FormBody.Builder()
                .add("name",name)
                .add("pwd",pwd)
                .build();
        Request request = new Request.Builder()
                .url(Constants.URL_LOGIN)
                .post(body)
                .build();
        Call call = okHttpClient.newCall(request);
        try{
            call.execute();
        }catch (IOException e){
            e.printStackTrace();
            LogUtil.e("登录失败");
        }
        return 0;
    }
}
