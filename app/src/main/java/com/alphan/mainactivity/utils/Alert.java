package com.alphan.mainactivity.utils;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import com.alphan.mainactivity.R;

public class Alert {

    private Dialog mDialog;
    private AlertBtnListener listener;
    private Context mContext;
    private String mMainText;
    private String mTitleInfo;
    private String mFirstBtnTitle;
    private String mSecondBtnTitle;
    private boolean mIsCancelable;

    private Button mFirstBtn = null, mSecondBtn = null;
    private TextView mainInfoTv, titleTv;

    public Alert(Builder builder) {
        this.mContext = builder.mContext;
        this.mMainText = builder.mMainText;
        this.mTitleInfo = builder.mTitle;
        this.listener = builder.mListener;
        this.mFirstBtnTitle = builder.mFirstBtnTitle;
        this.mSecondBtnTitle = builder.mSecondBtnTitle;
        this.mIsCancelable = builder.mIsCancelable;
    }

    public static Builder makeBuilder(Context context, String mainText) {
        return new Builder(context, mainText);
    }

    private Dialog makeDialog() {
        if (mContext != null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(mContext, R.style.customDialog);
            View customView = LayoutInflater.from(mContext).inflate(R.layout.dialog_custom, null);

            mFirstBtn = customView.findViewById(R.id.firstBtn);
            mSecondBtn = customView.findViewById(R.id.secondBtn);
            mainInfoTv = customView.findViewById(R.id.bodyTv);
            titleTv = customView.findViewById(R.id.titleTv);

            mainInfoTv.setText(mMainText);

            if (mTitleInfo != null) {
                titleTv.setVisibility(View.VISIBLE);
                titleTv.setText(mTitleInfo);
            }
            if (mFirstBtnTitle != null) {
                mFirstBtn.setVisibility(View.VISIBLE);
                mFirstBtn.setText(mFirstBtnTitle);
            }

            if (mSecondBtnTitle != null) {
                mSecondBtn.setVisibility(View.VISIBLE);
                mSecondBtn.setText(mSecondBtnTitle);
            }

            if (listener != null) {
                mFirstBtn.setOnClickListener(v -> listener.onFirstBtnClicked(mDialog));
                mSecondBtn.setOnClickListener(v -> listener.onSecondBtnClicked(mDialog));
            }
            builder.setView(customView);
            mDialog = builder.create();
            mDialog.setCancelable(mIsCancelable);

            mDialog.show();
            return mDialog;
        }
        return null;
    }

    public interface AlertBtnListener {

        void onFirstBtnClicked(Dialog dialog);

        default void onSecondBtnClicked(Dialog dialog) {
        }
    }

    public static class Builder {
        //required
        private Context mContext;
        private String mMainText;

        //optional
        private AlertBtnListener mListener = null;
        private String mTitle = null;
        private String mFirstBtnTitle = null;
        private String mSecondBtnTitle = null;
        private boolean mIsCancelable = false;

        public Builder(Context context, String mainText) {
            this.mContext = context;
            this.mMainText = mainText;
        }

        public Builder enableTitle(String title) {
            this.mTitle = title;
            return this;
        }

        public Builder setListener(AlertBtnListener alertBtnListener) {
            this.mListener = alertBtnListener;
            return this;
        }

        public Builder enableFirstBtn(String btnTitle) {
            this.mFirstBtnTitle = btnTitle;
            return this;
        }

        public Builder enableSecondBtn(String btnTitle) {
            this.mSecondBtnTitle = btnTitle;
            return this;
        }

        public Builder setCancelable(boolean status) {
            this.mIsCancelable = status;
            return this;
        }

        public Dialog show() {
            return new Alert(this).makeDialog();
        }
    }
}
