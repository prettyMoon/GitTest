package com.example.hongli.idiom.tools;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.hongli.idiom.R;

/**
 * Created by hongli on 2018/5/11.
 */

public class SelfDialog extends Dialog {
    private Context context;
    private Button btn_know;
    public SelfDialogListener listener;

    public SelfDialog(Context context, int theme) {
        super(context, theme);
        this.context = context;
    }

    public void setListener(SelfDialogListener listener) {
        this.listener = listener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_self_dialog);
        btn_know = (Button) this.findViewById(R.id.dialog_btn_know);
        btn_know.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.callback();
                }
            }
        });
    }

    public interface SelfDialogListener {
        public void callback();
    }
}

