package com.example.retrofit;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.retrofit.databinding.FragmentItunesBinding;
import com.example.retrofit.databinding.ViewholderContentBinding;

import java.util.List;

public class ItunesFragment extends Fragment {
    private FragmentItunesBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentItunesBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ContentsAdapter contentsAdapter = new ContentsAdapter();
        binding.recyclerviewContents.setAdapter(contentsAdapter);

        ItunesViewModel itunesViewModel = new ViewModelProvider(this).get(ItunesViewModel.class);

        binding.text.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                itunesViewModel.search(s);
                return false;
            }
        });

        itunesViewModel.responseMutableLiveData.observe(getViewLifecycleOwner(), new Observer<Itunes.Response>() {
            @Override
            public void onChanged(Itunes.Response response) {
                // to control the null pointer on results
                if (response != null && response.results != null) {
                    for (Itunes.Content content : response.results) {
                        contentsAdapter.establishContentList(response.results);
                        Log.d("ApiReplay", content.artistName + ", " + content.trackName + ", " + content.artworkUrl100);
                    }
                } else {
                    Log.d("ApiReplay", "No results found");
                }
            }
        });
    }

    static class ContentViewHolder extends RecyclerView.ViewHolder {
        ViewholderContentBinding binding;

        public ContentViewHolder (@NonNull ViewholderContentBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    class ContentsAdapter extends RecyclerView.Adapter<ContentViewHolder>{
        List<Itunes.Content> contentList;

        @NonNull
        @Override
        public ContentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ContentViewHolder(ViewholderContentBinding.inflate(getLayoutInflater(), parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull ContentViewHolder holder, int position) {
            Itunes.Content content = contentList.get(position);

            holder.binding.title.setText(content.trackName);
            holder.binding.artist.setText(content.artistName);
            Glide.with(requireActivity()).load(content.artworkUrl100).into(holder.binding.artwork);
        }

        @Override
        public int getItemCount() {
            return contentList == null ? 0 : contentList.size();
        }

        void establishContentList(List<Itunes.Content> contentList){
            this.contentList = contentList;
            notifyDataSetChanged();
        }
    }
}
