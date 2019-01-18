package easycommand.mbds.unice.fr.eaasycommand.fragment;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import easycommand.mbds.unice.fr.eaasycommand.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class AcceuilFragment extends Fragment {


    public AcceuilFragment() {}


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_acceuil, container, false);
        TextView restao = view.findViewById(R.id.restoName);
        Bundle bundle = getArguments();
        String restoName = bundle.getString("restoName");
        restao.setText(restoName);
        return view;

    }

}
