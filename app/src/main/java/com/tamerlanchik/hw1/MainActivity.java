package com.tamerlanchik.hw1;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

public class MainActivity extends AppCompatActivity
        implements FirstFragment.FragmentSwitchView {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);

        Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.fragment_container);
        if(currentFragment == null) {
            currentFragment = new FirstFragment();
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.fragment_container, currentFragment)
                    .commit();
        }
    }

    @Override
    public void openDetails(String text, int color) {
        Log.d("HW1", "openDetails()");
        Fragment fragment = SecondFragment.newInstance(text, color);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit();
    }
}