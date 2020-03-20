package com.tamerlanchik.hw1;

import android.content.Context;
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
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class FirstFragment extends Fragment {
    private static final String KEY_LAST_VALUE = "last_value";
    public interface FragmentSwitchView {
        void openDetails(String text, int color);
    }

    private RecyclerView mRecycleView;
    private FloatingActionButton mAddElement;
    private GridAdapter mAdapter;
    private FragmentSwitchView mCheckoutListener;



    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.first_fragment, container, false);

        mRecycleView = v.findViewById(R.id.recycler_view);
        int width = getResources().getInteger(R.integer.recycler_view__span_count);
        mRecycleView.setLayoutManager(new GridLayoutManager(getActivity(), width));

        mAddElement = v.findViewById(R.id.fab);
        mAddElement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Storage storage = Storage.get(getActivity());
                int len = storage.getData().size();
                int val = Integer.valueOf(storage.getData().get(len-1)) + 1;
                storage.getData().add(Integer.toString(val));
                mAdapter.notifyItemChanged(len);
            }
        });

        mAdapter = new GridAdapter(Storage.get(getActivity()).getData());
        mRecycleView.setAdapter(mAdapter);

        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(savedInstanceState == null) {
            savedInstanceState = new Bundle();
        }
        if(Storage.get(getActivity()).getData() == null) {
            int from = getResources().getInteger(R.integer.list_from);
            int to = savedInstanceState.getInt(KEY_LAST_VALUE, getResources().getInteger(R.integer.list_to));
            int step = getResources().getInteger(R.integer.list_step);
            Storage.get(getActivity())
                    .setData(generateList(from, to, step));
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mAddElement = null;     //  prevents the only memory leak
    }

    private List<String> generateList(int from, int to, int step) {
        List<String> list= new ArrayList<>();
        for(int i = from; i <= to; i+=step) {
            list.add(Integer.toString(i));
        }
        return list;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof FragmentSwitchView) {
            mCheckoutListener = (FragmentSwitchView) context;
        } else {
            throw new ClassCastException(context.toString()
                    + " must implement FirstFragment.FragmentSwitchView");
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(KEY_LAST_VALUE, Integer.parseInt(Storage.get(getActivity()).getLast()));
    }

    private class GridHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView mTextView;
        public GridHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.gridelement_layout, parent, false));
            mTextView = itemView.findViewById(R.id.grid_element_textview);
            itemView.setOnClickListener(this);
        }

        public void bind(String value) {
            mTextView.setText(value);
            mTextView.setTextColor(
                    Integer.valueOf(value) % 2 == 0
                            ? getResources().getColor(R.color.number_even)
                            : getResources().getColor(R.color.number_uneven)
            );
        }

        @Override
        public void onClick(View v) {
            String text = mTextView.getText().toString();
            int color = mTextView.getCurrentTextColor();
            mCheckoutListener.openDetails(text, color);
        }
    }

    private class GridAdapter extends RecyclerView.Adapter<GridHolder> {
        private List<String> mNumbers;

        public GridAdapter(List<String> elements) {
            mNumbers = elements;
        }

        @NonNull
        @Override
        public GridHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            return new GridHolder(inflater, parent);
        }

        @Override
        public void onBindViewHolder(@NonNull GridHolder holder, int position) {
            holder.bind(mNumbers.get(position));
        }

        @Override
        public int getItemCount() {
            return mNumbers.size();
        }
    }
}
