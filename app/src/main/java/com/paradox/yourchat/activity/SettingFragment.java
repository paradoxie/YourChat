package com.paradox.yourchat.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import com.paradox.yourchat.R;
import com.paradox.yourchat.util.SPUtil;

public class SettingFragment extends PreferenceFragmentCompat {
    private Preference key;
    private final String A_KEY = "key";


    @Override
    public void onCreatePreferences(@Nullable Bundle savedInstanceState, @Nullable String rootKey) {
        //用于取值的SharedPreferences
        initView();
    }

    private void initView() {
        addPreferencesFromResource(R.xml.pref_setting);
        String token = SPUtil.get("key", "").toString();


        key = findPreference(A_KEY);
        key.setSummary(token);
        key.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(@NonNull Preference preference) {

                View view = getLayoutInflater().inflate(R.layout.half_dialog_view, null);
                final EditText editText = view.findViewById(R.id.dialog_edit);
                AlertDialog dialog = new AlertDialog.Builder(getActivity())
                        .setIcon(R.mipmap.ic_key)//设置标题的图片
                        .setTitle("OpenAi Key")//设置对话框的标题
                        .setView(view)
                        .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String content = editText.getText().toString();
                                SPUtil.put("key", content);
                                key.setSummary(content);
                                dialog.dismiss();
                            }
                        }).create();
                dialog.show();
                return false;
            }
        });

    }

}
