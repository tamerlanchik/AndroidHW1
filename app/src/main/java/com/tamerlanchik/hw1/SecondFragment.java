package com.tamerlanchik.hw1;


import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class SecondFragment extends Fragment {
    private static final String KEY_TEXT = "value";
    private static final String KEY_COLOR = "color";
    public static SecondFragment newInstance(String value, int color) {
        SecondFragment fragment = new SecondFragment();
        Bundle args = new Bundle();
        args.putString(KEY_TEXT, value);
        args.putInt(KEY_COLOR, color);
        fragment.setArguments(args);
        return fragment;
    }

    private TextView mTextView;
    private String mValue;
    private int mColor;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mValue = getArguments().getString(KEY_TEXT);
        mColor = getArguments().getInt(KEY_COLOR);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d("HW1", "onCreateView()");
        View v = inflater.inflate(R.layout.second_fragment, container, false);
        mTextView = v.findViewById(R.id.second_fragment_textview);
        mTextView.setTextColor(mColor);
        mTextView.setText(mValue);

        return v;
    }
}
