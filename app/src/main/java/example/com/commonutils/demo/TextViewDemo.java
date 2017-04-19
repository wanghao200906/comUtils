package example.com.commonutils.demo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.Arrays;
import java.util.List;

import example.com.commonutils.R;
import example.com.commonutils.adapter.DemoAdapter;
import example.com.commonutils.interfaces.OnItemClickListener;

/**
 * Created by wanghao on 2017/2/23.
 */

public class TextViewDemo extends AppCompatActivity {

    private static final String TAG = "DailogDemo";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_fullscreen);
        String[] stringArray = getResources().getStringArray(R.array.textview_deme_name);
        final List<String> strings = Arrays.asList(stringArray);
        System.out.println("-----" + strings.size());

        for (String string : strings) {
            System.out.println("-----" + string);
        }

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rv_demo);

        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));//定义样式

        DemoAdapter demoAdapter = new DemoAdapter(strings);

        recyclerView.setAdapter(demoAdapter);

        demoAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                String s = strings.get(position);
                String className = "example.com.commonutils.demo.textview." + s;
                Intent intent = new Intent();
                intent.setClassName(TextViewDemo.this, className);
                startActivity(intent);


            }
        });
    }

}
