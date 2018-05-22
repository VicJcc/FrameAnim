package jcc.example.com.frameanim;

import android.app.Application;

import com.github.moduth.blockcanary.BlockCanary;

/**
 * Created by jincancan on 2018/4/25.
 * Description:
 */
public class JApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        BlockCanary.install(this, new AppBlockCanaryContext()).start();
    }
}
