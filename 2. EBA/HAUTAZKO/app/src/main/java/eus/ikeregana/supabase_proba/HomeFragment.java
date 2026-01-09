package eus.ikeregana.supabase_proba;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class HomeFragment extends Fragment {

    private static final String ARG_USUARIO = "usuario";

    public static HomeFragment newInstance(String usuario) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_USUARIO, usuario);
        fragment.setArguments(args);
        return fragment;
    }

    public HomeFragment() {}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        TextView tvMsg = view.findViewById(R.id.tvMsg);

        String usuario = "";
        if (getArguments() != null) {
            usuario = getArguments().getString(ARG_USUARIO, "");
        }

        tvMsg.setText("Login correcto.\nUsuario: " + usuario);

        return view;
    }
}
