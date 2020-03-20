package com.tamerlanchik.hw1;

import android.content.Context;

import java.util.List;

public class Storage {
    private static Storage storage;
    private List<String> data;

    public static Storage get(Context context) {
        if(storage == null) {
            storage = new Storage(context);
        }
        return storage;
    }
    private Storage(Context context) {

    }

    public List<String> getData() {
        return data;
    }

    public void setData(List<String> data) {
        this.data = data;
    }

    public String getLast() {
        return this.data.get(this.data.size() - 1);
    }
}
