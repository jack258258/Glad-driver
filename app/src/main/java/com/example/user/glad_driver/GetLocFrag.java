package com.example.user.glad_driver;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.user.glad_driver.AmountMoney;

/**
 * Created by USER on 2017/10/13.
 */

public class GetLocFrag extends Fragment {
    // 前往目的地...Fragment
    public static final String TAG = GetLocFrag.class.getSimpleName();


    public GetLocFrag() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View rootView = inflater.inflate(R.layout.fragment_get_loca, container, false);

        Button button = (Button) rootView.findViewById(R.id.btngetloca);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AmountMoney.class);
                startActivity(intent);
            }
        });

        return rootView;
    }


}

