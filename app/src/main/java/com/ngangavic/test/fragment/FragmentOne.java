package com.ngangavic.test.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.ngangavic.test.R;

public class FragmentOne extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    // SendMessage SM;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FragmentOne() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentOne.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentOne newInstance(String param1, String param2) {
        FragmentOne fragment = new FragmentOne();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_one, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Button buttonScanner = view.findViewById(R.id.buttonScanner);
        Button btnPassData = (Button) view.findViewById(R.id.button2);

        btnPassData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                SM.sendData("AM victor");
                loadFragment(new FragmentTwo("Am victor"));
            }
        });

        buttonScanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openScanner();
            }
        });

    }

    private void loadFragment(Fragment fragment) {
// create a FragmentManager
        FragmentManager fm = getFragmentManager();
// create a FragmentTransaction to begin the transaction and replace the Fragment
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
// replace the FrameLayout with new Fragment
        fragmentTransaction.replace(R.id.fragment, fragment);
        fragmentTransaction.commit(); // save the changes
    }

    private void openScanner() {
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        Fragment fragmentPrev = getFragmentManager().findFragmentById(R.id.scanner_layout);
        if (fragmentPrev != null) {
            fragmentTransaction.remove(fragmentPrev);
        }
        ScannerDialog scannerDialog = new ScannerDialog();
        scannerDialog.show(fragmentTransaction, "scanner");

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
//        try {
//            SM = (SendMessage) getActivity();
//        } catch (ClassCastException e) {
//            throw new ClassCastException("Error in retrieving data. Please try again");
//        }

    }

//    interface SendMessage {
//        void sendData(String message);
//    }
}
