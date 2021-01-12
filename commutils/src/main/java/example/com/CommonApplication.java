package example.com;

import android.app.Application;
import android.content.Context;
import android.text.TextUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import example.com.commutils.BuildConfig;

public class CommonApplication {
    private static CommonApplication INSTANCE;

    private Context applicationContext;
    private Config config;

    /**
     * should be called in method onCreate() of class App
     */
    public static CommonApplication createInstance(Application applicationContext, Config config) {
        INSTANCE = new CommonApplication(applicationContext, config);
        return INSTANCE;
    }

    public static CommonApplication getInstance() {
        return INSTANCE;
    }

    private CommonApplication(Application applicationContext, Config config) {
        this.applicationContext = applicationContext;
        this.config = config;
    }


    private static String getProcessName(int pid) {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader("/proc/" + pid + "/cmdline"));
            String processName = reader.readLine();
            if (!TextUtils.isEmpty(processName)) {
                processName = processName.trim();
            }
            return processName;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }
        return null;
    }


    public Context getApplicationContext() {
        return applicationContext;
    }

    public Config getConfig() {
        return config;
    }

    public static class Config {
        private boolean age;//随便的一些配置都行
        private String name;

        public boolean isAge() {
            return age;
        }

        public void setAge(boolean age) {
            this.age = age;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
