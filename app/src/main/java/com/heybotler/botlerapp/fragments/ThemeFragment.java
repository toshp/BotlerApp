package com.heybotler.botlerapp.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.heybotler.botlerapp.R;
import com.heybotler.botlerapp.helpers.FontChanger;

/**
 * Created by toshitpanigrahi on 3/11/17.
 */

public class ThemeFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_settings, null);
        TextView title = (TextView) v.findViewById(R.id.settings_title);
        FontChanger.changeFont(getActivity().getAssets(), title, "bold");
        title = (TextView) v.findViewById(R.id.settings_subtitle);
        FontChanger.changeFont(getActivity().getAssets(), title, "regular");
        return v;
    }
}
