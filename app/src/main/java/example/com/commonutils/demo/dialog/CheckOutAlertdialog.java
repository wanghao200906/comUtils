package example.com.commonutils.demo.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Toast;

import androidx.annotation.Nullable;

import example.com.commonutils.R;


/**
 * Created by wanghao on 2017/2/23.
 * 该dialog 点击确定不消失。
 */

public class CheckOutAlertdialog extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.emptylayout);

        View view = LayoutInflater.from(this).inflate(R.layout.singlecheckbox, null, false);
        final CheckBox checkBox = (CheckBox) view.findViewById(R.id.cb_box);
        final AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setTitle("alertdialog")
                .setMessage("选中checkbox了， 确定按钮才能被点击")
                .setView(view)
                .setPositiveButton("确定", null)
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(CheckOutAlertdialog.this, "取消了", Toast.LENGTH_LONG).show();

                    }
                }).create();
        alertDialog.show();
        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkBox.isChecked()) {
                    alertDialog.dismiss();
                } else {
                    Toast.makeText(CheckOutAlertdialog.this, "请选中checkbox", Toast.LENGTH_LONG).show();

                }
            }
        });
    }
}
