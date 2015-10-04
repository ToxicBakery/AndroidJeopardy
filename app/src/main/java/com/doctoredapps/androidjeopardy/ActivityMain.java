package com.doctoredapps.androidjeopardy;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.doctoredapps.androidjeopardy.connection.NearbyConnection;
import com.doctoredapps.androidjeopardy.fragment.FragmentTeamConfiguration;

public class ActivityMain extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, new FragmentTeamConfiguration(), FragmentTeamConfiguration.TAG)
                    .commit();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        NearbyConnection.getInstance(this)
                .start(false);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        if (getSupportFragmentManager().getBackStackEntryCount() == 0) {
            NearbyConnection.getInstance(this)
                    .stop();
        }
    }
}
