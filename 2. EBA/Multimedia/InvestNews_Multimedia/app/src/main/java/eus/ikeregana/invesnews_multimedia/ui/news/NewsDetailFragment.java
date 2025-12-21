package eus.ikeregana.invesnews_multimedia.ui.news;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import eus.ikeregana.invesnews_multimedia.R;
import eus.ikeregana.invesnews_multimedia.data.local.entity.FavoriteEntity;
import eus.ikeregana.invesnews_multimedia.viewmodel.NewsViewModel;

public class NewsDetailFragment extends Fragment {

    public NewsDetailFragment() {
        super(R.layout.fragment_news_detail);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView tvTitle = view.findViewById(R.id.tvTitleDetail);
        TextView tvDescription = view.findViewById(R.id.tvDescriptionDetail);

        Button btnOpenUrl = view.findViewById(R.id.btnOpenUrl);
        Button btnToggleFavorite = view.findViewById(R.id.btnToggleFavorite);

        // ViewModel (para Room)
        NewsViewModel viewModel = new ViewModelProvider(requireActivity()).get(NewsViewModel.class);

        Bundle args = getArguments();

        final String title = (args != null) ? args.getString("title", "") : "";
        final String description = (args != null) ? args.getString("description", "") : "";
        final String url = (args != null) ? args.getString("url", "") : "";

        tvTitle.setText(title);
        tvDescription.setText(description);

        // Abrir URL en navegador
        btnOpenUrl.setOnClickListener(v -> {
            if (url != null && !url.isEmpty()) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(intent);
            }
        });

        // Si no hay URL, no tiene sentido permitir favoritos
        if (url == null || url.isEmpty()) {
            btnToggleFavorite.setEnabled(false);
            btnToggleFavorite.setText("☆");
            return;
        }

        // Creamos el objeto FavoriteEntity que guardaremos/borraremos
        // imageUrl y publishedAt de momento no los tienes en el Bundle -> los dejamos null.
        FavoriteEntity favorite = new FavoriteEntity(
                url,
                title,
                description,
                null,
                null
        );

        // Estado actual (lo usamos en el click)
        final boolean[] currentIsFavorite = {false};

        // 1) Observar si esta noticia ya está en favoritos
        viewModel.isFavorite(url).observe(getViewLifecycleOwner(), isFav -> {
            boolean fav = (isFav != null && isFav);
            currentIsFavorite[0] = fav;
            btnToggleFavorite.setText(fav ? "★" : "☆");
        });

        // 2) Click: guardar/quitar
        btnToggleFavorite.setOnClickListener(v -> {
            if (currentIsFavorite[0]) {
                viewModel.removeFromFavorites(favorite);
            } else {
                viewModel.addToFavorites(favorite);
            }
        });
    }
}
