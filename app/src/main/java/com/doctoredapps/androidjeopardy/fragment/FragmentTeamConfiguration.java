package com.doctoredapps.androidjeopardy.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.doctoredapps.androidjeopardy.R;

public class FragmentTeamConfiguration extends Fragment implements View.OnClickListener {

    public static final String TAG = "FragmentTeamConfiguration";

    private TextView teamNameView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_team_configuration, container, false);

        view.findViewById(R.id.button_host)
                .setOnClickListener(this);
        view.findViewById(R.id.button_join)
                .setOnClickListener(this);

        teamNameView = (TextView) view.findViewById(R.id.team_name);

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_host:
                break;
            case R.id.button_join:
                break;
        }
    }

}
