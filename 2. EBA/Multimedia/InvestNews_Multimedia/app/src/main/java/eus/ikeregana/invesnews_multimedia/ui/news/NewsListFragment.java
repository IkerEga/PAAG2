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

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.lifecycle.ViewModelProvider;
import eus.ikeregana.invesnews_multimedia.viewmodel.NewsViewModel;

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



        RecyclerView recyclerView = view.findViewById(R.id.rvNews);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        NewsAdapter adapter = new NewsAdapter();
        recyclerView.setAdapter(adapter);

        NewsViewModel viewModel = new ViewModelProvider(this).get(NewsViewModel.class);

        // Observa la lista de noticias y actualiza el RecyclerView
        viewModel.getNews().observe(getViewLifecycleOwner(), articles -> {
            if (articles != null) {
                adapter.setArticles(articles);
            }
        });

        viewModel.fetchNews(
                "g9cgr5hNeJG51DUkti4a0aJ6jvVH6CoNEId1liiT",
                "TSLA,BTC,ETH",
                null,
                "en",
                1
        );


        Button btn = view.findViewById(R.id.btnGoDetail);
        btn.setOnClickListener(v ->
                Navigation.findNavController(view)
                        .navigate(R.id.action_newsListFragment_to_newsDetailFragment)
        );



    }
}
