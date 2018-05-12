package com.example.hongli.idiom.tools;

import java.io.Serializable;

/**
 * Created by hongli on 2018/5/11.
 */

public class GridItemEntity implements Serializable {
    private String text = "";
    private boolean invisiable;
    private boolean needInfo = false;
    private String userText = "";

    public GridItemEntity() {

    }

    public GridItemEntity(String text, boolean selected) {
        this.text = text;
        this.invisiable = selected;
    }

    public String getUserText() {
        return userText;
    }

    public void setUserText(String userText) {
        this.userText = userText;
    }

    public boolean isNeedInfo() {
        return needInfo;
    }

    public void setNeedInfo(boolean needInfo) {
        this.needInfo = needInfo;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isInvisiable() {
        return invisiable;
    }

    public void setInvisiable(boolean invisiable) {
        this.invisiable = invisiable;
    }

    public boolean idRight() {
        return text.equals(userText);
    }
}
