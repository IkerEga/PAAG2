package eus.ikeregana.invesnews_multimedia.ui.news;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.net.Uri;
import android.widget.Button;


import eus.ikeregana.invesnews_multimedia.R;

public class NewsDetailFragment extends Fragment {

    public NewsDetailFragment() {
        super(R.layout.fragment_news_detail); // <-- CLAVE: este layout
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView tvTitle = view.findViewById(R.id.tvTitleDetail);
        TextView tvDescription = view.findViewById(R.id.tvDescriptionDetail);

        Bundle args = getArguments();
        if (args != null) {
            tvTitle.setText(args.getString("title", ""));
            tvDescription.setText(args.getString("description", ""));
        }

        Button btnOpenUrl = view.findViewById(R.id.btnOpenUrl);

        final String url = (args != null) ? args.getString("url", "") : "";

        btnOpenUrl.setOnClickListener(v -> {
            if (url != null && !url.isEmpty()) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(intent);
            }
        });

    }
}
