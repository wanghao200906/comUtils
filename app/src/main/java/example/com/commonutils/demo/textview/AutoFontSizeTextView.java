package example.com.commonutils.demo.textview;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import example.com.commonutils.R;


/**
 * Created by wanghao on 2017/2/23.
 */
public class AutoFontSizeTextView extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.autofontsizetextview);

        final example.com.commonutils.widgets.AutoFontSizeTextView autoFontSizeTextView = (example.com.commonutils.widgets.AutoFontSizeTextView) findViewById(R.id.autoFontSizeTextView2);
        final String textContext = (String) autoFontSizeTextView.getText();
        findViewById(R.id.btn_add_text).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                autoFontSizeTextView.setText(textContext + textContext);
            }
        });
    }
}
