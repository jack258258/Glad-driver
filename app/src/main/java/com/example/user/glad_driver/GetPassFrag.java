package com.example.user.glad_driver;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by USER on 2017/10/13.
 */

public class GetPassFrag extends Fragment {
    // 已載客...Fragment
    public static final String TAG = GetPassFrag.class.getSimpleName();


    public GetPassFrag() {

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_get_pass, container, false);
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Button btnChangeFragment = (Button) getView().findViewById(R.id.takepassenger);
        btnChangeFragment.setOnClickListener(btnChangeFragment_Listener);
    }

    Button.OnClickListener btnChangeFragment_Listener = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            ((MapsActivity)getActivity()).gotoScene(MapsActivity.SCENE_NORMAL, MapsActivity.FRAG_02);
        }
    };

}
