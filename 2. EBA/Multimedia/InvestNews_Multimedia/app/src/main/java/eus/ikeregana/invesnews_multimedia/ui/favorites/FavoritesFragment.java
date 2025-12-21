package eus.ikeregana.invesnews_multimedia.ui.favorites;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.ArrayList;
import java.util.List;

import eus.ikeregana.invesnews_multimedia.R;
import eus.ikeregana.invesnews_multimedia.data.local.entity.FavoriteEntity;
import eus.ikeregana.invesnews_multimedia.data.model.ArticleDto;
import eus.ikeregana.invesnews_multimedia.databinding.FragmentFavoritesBinding;
import eus.ikeregana.invesnews_multimedia.ui.news.NewsAdapter;
import eus.ikeregana.invesnews_multimedia.viewmodel.NewsViewModel;

public class FavoritesFragment extends Fragment {

    private FragmentFavoritesBinding binding;
    private NewsViewModel viewModel;
    private NewsAdapter adapter;

    public FavoritesFragment() {
        super(R.layout.fragment_favorites);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding = FragmentFavoritesBinding.bind(view);

        // RecyclerView + Adapter
        adapter = new NewsAdapter();
        binding.rvFavorites.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.rvFavorites.setAdapter(adapter);

        // Click -> detalle (igual que en NewsListFragment)
        adapter.setOnItemClickListener(article -> {
            Bundle bundle = new Bundle();
            bundle.putString("title", article.getTitle());
            bundle.putString("description", article.getDescription());
            bundle.putString("url", article.getUrl());

            Navigation.findNavController(view)
                    .navigate(R.id.action_favoritesFragment_to_newsDetailFragment, bundle);
        });

        // ViewModel
        viewModel = new ViewModelProvider(requireActivity()).get(NewsViewModel.class);

        // Observar favoritos y mostrarlos
        viewModel.getAllFavorites().observe(getViewLifecycleOwner(), favorites -> {

            boolean empty = (favorites == null || favorites.isEmpty());
            binding.tvEmptyFavorites.setVisibility(empty ? View.VISIBLE : View.GONE);
            binding.rvFavorites.setVisibility(empty ? View.GONE : View.VISIBLE);

            if (empty) {
                adapter.setArticles(new ArrayList<>());
                return;
            }

            // Convertimos FavoritesEntity -> ArticleDto (sin setters: usamos objetos "anonimos" con getters)
            List<ArticleDto> articles = mapFavoritesToArticles(favorites);
            adapter.setArticles(articles);
        });
    }

    private List<ArticleDto> mapFavoritesToArticles(List<FavoriteEntity> favorites) {
        List<ArticleDto> articles = new ArrayList<>();

        for (FavoriteEntity f : favorites) {
            ArticleDto a = new ArticleDto() {
                @Override public String getUuid() { return null; }
                @Override public String getTitle() { return f.getTitle(); }
                @Override public String getDescription() { return f.getDescription(); }
                @Override public String getUrl() { return f.getUrl(); }
                @Override public String getImageUrl() { return f.getImageUrl(); }
                @Override public String getPublishedAt() { return f.getPublishedAt(); }
                @Override public String getSource() { return "Favorito"; }
            };

            articles.add(a);
        }

        return articles;
    }
}
