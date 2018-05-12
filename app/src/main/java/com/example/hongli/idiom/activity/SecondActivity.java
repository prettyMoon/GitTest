package com.example.hongli.idiom.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hongli.idiom.R;
import com.example.hongli.idiom.adapter.GridAdapter;
import com.example.hongli.idiom.tools.Game;
import com.example.hongli.idiom.tools.GridItemEntity;
import com.example.hongli.idiom.tools.SelfDialog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by hongli on 2018/2/24.
 */

public class SecondActivity extends Activity implements View.OnClickListener {
    private GridAdapter gridAdapter;
    private GridView gridView;
    private ImageView imgLeft;
    private TextView btnRight;
    private TextView btnMiddle;
    private List<GridItemEntity> idoimList;
    private SelfDialog selfDialog;
    private Game game;
    private List<Map<String, Object>> dataList;
    private TextView tvInfo;
    private int indexSelected = -1;
    private EditText editText;
    private Button btn_sure;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        initView();
        initContent();
        initListener();
    }

    public void initListener() {
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                indexSelected = position;
                if (game == null) {
                    Toast.makeText(SecondActivity.this, "Game is null!", Toast.LENGTH_SHORT).show();
                } else {
                    Log.e("zhl", "****" + game.getMeaning(position / 10, position % 10));
                    tvInfo.setText(game.getMeaning(position / 10, position % 10));
                    //黄色
                    showSelected(position);
                }
            }
        });
    }

    public void showSelected(final int position) {
        for (int i = 0; i < idoimList.size(); i++) {
            idoimList.get(i).setNeedInfo(false);
        }
        int[] pos = game.getInfoPosition(position / 10, position % 10);
        if (pos[0] != -1 && pos[1] != -1) {
            int x = pos[0];
            int y = pos[1];
            idoimList.get((x * 10 + y)).setNeedInfo(true);
            idoimList.get((x * 10 + y + 1)).setNeedInfo(true);
            idoimList.get((x * 10 + y + 2)).setNeedInfo(true);
            idoimList.get((x * 10 + y + 3)).setNeedInfo(true);
        }
        if (pos[2] != -1 && pos[3] != -1) {
            int x = pos[2];
            int y = pos[3];
            idoimList.get((x * 10 + y)).setNeedInfo(true);
            idoimList.get((x + 1) * 10 + y).setNeedInfo(true);
            idoimList.get((x + 2) * 10 + y).setNeedInfo(true);
            idoimList.get((x + 3) * 10 + y).setNeedInfo(true);
        }
        gridAdapter.notifyDataSetChanged();

    }

    public void showInfo(int position) {
        if (position <= 0) {
            return;
        }
        int[] pos = game.getInfoPosition(position / 10, position % 10);
        if (pos[0] != -1 && pos[1] != -1) {
            int x = pos[0];
            int y = pos[1];
            idoimList.get((x * 10 + y)).setUserText(idoimList.get((x * 10 + y)).getText());
            idoimList.get((x * 10 + y + 1)).setUserText(idoimList.get((x * 10 + y + 1)).getText());
            idoimList.get((x * 10 + y + 2)).setUserText(idoimList.get((x * 10 + y + 2)).getText());
            idoimList.get((x * 10 + y + 3)).setUserText(idoimList.get((x * 10 + y + 3)).getText());
        }
        if (pos[2] != -1 && pos[3] != -1) {
            int x = pos[2];
            int y = pos[3];
            idoimList.get((x * 10 + y)).setUserText(idoimList.get((x * 10 + y)).getText());
            idoimList.get((x + 1) * 10 + y).setUserText(idoimList.get((x + 1) * 10 + y).getText());
            idoimList.get((x + 2) * 10 + y).setUserText(idoimList.get((x + 2) * 10 + y).getText());
            idoimList.get((x + 3) * 10 + y).setUserText(idoimList.get((x + 3) * 10 + y).getText());
        }
        gridAdapter.notifyDataSetChanged();
    }

    private void initView() {
        btnMiddle = this.findViewById(R.id.title_middle);
        btnRight = this.findViewById(R.id.title_right);
        imgLeft = this.findViewById(R.id.title_left);
        btnRight.setOnClickListener(this);
        imgLeft.setOnClickListener(this);
        //
        gridView = this.findViewById(R.id.grid_view);
        tvInfo = this.findViewById(R.id.tv_info);
        editText = this.findViewById(R.id.editText);
        btn_sure = this.findViewById(R.id.btn_sure);
        btn_sure.setOnClickListener(this);
        btnRight.setText("帮填");
    }

    public void initDialog() {
        selfDialog = new SelfDialog(SecondActivity.this, R.style.selfDialog);
        selfDialog.setListener(new SelfDialog.SelfDialogListener() {
            @Override
            public void callback() {
                selfDialog.dismiss();
                finish();
            }
        });
        selfDialog.show();
    }

    private void initContent() {
        initData();
        gridAdapter = new GridAdapter(SecondActivity.this, idoimList);
        gridView.setAdapter(gridAdapter);
    }

    private void initData() {
        if (idoimList == null) {
            idoimList = new ArrayList<GridItemEntity>();
        }
        game = new Game(10, SecondActivity.this);
        game.makeCross();
        Map<String, Object> temp = new HashMap<String, Object>();
        for (int i = 0; i < game.cross.length; i++) {
            for (int j = 0; j < game.cross[i].length; j++) {
                GridItemEntity entity = new GridItemEntity(game.cross[i][j], false);
                entity.setUserText("");
                idoimList.add(entity);
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.title_right:
                showInfo(indexSelected);
                if (isPass()) {
                    initDialog();
                }
                break;
            case R.id.title_left:
                finish();
                break;
            case R.id.btn_sure:
                String text = editText.getText().toString();
                idoimList.get(indexSelected).setUserText(text);
                gridAdapter.notifyDataSetChanged();
                if (isPass()) {
                    initDialog();
                }
                break;
        }
    }

    public boolean isPass() {
        Log.e("zhl", "isPass");
        for (GridItemEntity entity : idoimList) {
            if ((!entity.getText().equals("*")) && (!entity.getText().equals(entity.getUserText()))) {
                return false;
            }
        }
        return true;
    }
}
