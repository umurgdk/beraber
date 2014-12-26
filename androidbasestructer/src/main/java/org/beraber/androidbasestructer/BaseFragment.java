package org.beraber.androidbasestructer;

import android.app.Fragment;
import android.os.Bundle;

public class BaseFragment extends Fragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ((BaseActivity)getActivity()).inject(this);
    }
}
