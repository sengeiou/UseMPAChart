package com.altman29.usempac.widget;

/**
 * Copyright©  2021
 * 正岸健康
 * author: csy
 * created on: 6/22/21 2:21 PM
 * description:
 */

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CompoundButton;

import androidx.appcompat.widget.SwitchCompat;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.altman29.usempac.R;


public class MySwitchTabLayout extends ConstraintLayout {
    private SwitchCompat mySwitch;
    private OnItemCheckedChangeListener mListener;

    public MySwitchTabLayout(Context context) {
        super(context);
        init(context);
    }

    public MySwitchTabLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public MySwitchTabLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        View inflate = inflate(context, R.layout.my_switch, this);
        mySwitch = inflate.findViewById(R.id.times_switch);
        mySwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mListener.onItemCheckedChange(isChecked);
            }
        });
    }

    public interface OnItemCheckedChangeListener {
        void onItemCheckedChange(boolean isChecked);
    }

    public void setOnItemCheckedChangeListener(OnItemCheckedChangeListener listener) {
        this.mListener = listener;
    }
}