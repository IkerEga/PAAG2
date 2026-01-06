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
import android.widget.Toast;


import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.lifecycle.ViewModelProvider;
import eus.ikeregana.invesnews_multimedia.viewmodel.NewsViewModel;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

public class NewsListFragment extends Fragment {

    private int currentPage = 1;
    private boolean isLastPage = false;
    private boolean isLoading = false;
    private NewsViewModel viewModel;


    public NewsListFragment() {
        super(R.layout.fragment_news_list);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        ProgressBar progressBar = view.findViewById(R.id.progressLoading);

        RecyclerView recyclerView = view.findViewById(R.id.rvNews);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        NewsAdapter adapter = new NewsAdapter();

        recyclerView.setAdapter(adapter);

        LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if (dy <= 0) return; // solo cuando se hace scroll hacia abajo

                int visibleItemCount = layoutManager.getChildCount();
                int totalItemCount = layoutManager.getItemCount();
                int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();

                // cuando queden pocos elementos por mostrar, cargamos la siguiente página
                int threshold = 2;

                boolean shouldLoadMore =
                        !isLoading &&
                                !isLastPage &&
                                (visibleItemCount + firstVisibleItemPosition) >= (totalItemCount - threshold) &&
                                firstVisibleItemPosition >= 0;

                if (shouldLoadMore) {
                    loadNextPage(viewModel);
                }
            }
        });


        adapter.setOnItemClickListener(article -> {
            Bundle bundle = new Bundle();
            bundle.putString("title", article.getTitle());
            bundle.putString("description", article.getDescription());
            bundle.putString("url", article.getUrl());

            Navigation.findNavController(view)
                    .navigate(R.id.action_newsListFragment_to_newsDetailFragment, bundle);
        });

        viewModel = new ViewModelProvider(this).get(NewsViewModel.class);

        viewModel.getNews().observe(getViewLifecycleOwner(), articles -> {
            if (articles != null) {
                adapter.setArticles(articles);

                // Si aún NO se puede hacer scroll hacia abajo (muy pocos items),
                // pedimos otra página automáticamente.
                // Esto evita quedarnos "atascados" en 3.
                if (!recyclerView.canScrollVertically(1) && !isLoading && !isLastPage) {
                    loadNextPage(viewModel);
                }
            }
        });


        viewModel.getError().observe(getViewLifecycleOwner(), errorMsg -> {
            if (errorMsg != null && !errorMsg.isEmpty()) {
                Toast.makeText(requireContext(), errorMsg, Toast.LENGTH_LONG).show();

                if (errorMsg.contains("402")) {
                    isLastPage = true; // parar paginación por cuota
                }
            }
        });


        loadPage(viewModel, currentPage);


        viewModel.getLoading().observe(getViewLifecycleOwner(), loading -> {
            isLoading = (loading != null && loading);

            if (isLoading) {
                progressBar.setVisibility(View.VISIBLE);
            } else {
                progressBar.setVisibility(View.GONE);
            }
        });

        viewModel.getLastFetchCount().observe(getViewLifecycleOwner(), count -> {

            // Ignorar emisiones nulas
            if (count == null) return;

            // IMPORTANTE:
            // - No marcamos fin mientras está cargando
            // - No marcamos fin en page 1 (evita el 0 inicial)
            if (!isLoading && currentPage > 1 && count == 0) {
                isLastPage = true;
            }
        });


    }

    private void loadPage(NewsViewModel viewModel, int page) {
        viewModel.loadNewsIfNeeded(
                "g9cgr5hNeJG51DUkti4a0aJ6jvVH6CoNEId1liiT",
                "TSLA,BTC,ETH",
                null,
                "en",
                page
        );
    }

    private void loadNextPage(NewsViewModel viewModel) {
        if (isLastPage) return;
        currentPage++;
        loadPage(viewModel, currentPage);
    }

}
