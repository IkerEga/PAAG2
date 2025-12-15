package eus.ikeregana.invesnews_multimedia.ui.news;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import eus.ikeregana.invesnews_multimedia.R;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

public class NewsListFragment extends Fragment {

    public NewsListFragment() {
        super(R.layout.fragment_news_list);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button btn = view.findViewById(R.id.btnGoDetail);
        btn.setOnClickListener(v ->
                Navigation.findNavController(view)
                        .navigate(R.id.action_newsListFragment_to_newsDetailFragment)
        );
    }
}
