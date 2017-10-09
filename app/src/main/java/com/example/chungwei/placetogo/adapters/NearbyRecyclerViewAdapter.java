package com.example.chungwei.placetogo.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.bumptech.glide.Glide;
import com.example.chungwei.placetogo.R;
import com.example.chungwei.placetogo.services.foursquare.FoursquareService;
import com.example.chungwei.placetogo.services.foursquare.IFoursquareResponse;
import com.example.chungwei.placetogo.services.foursquare.models.Item;
import com.example.chungwei.placetogo.services.foursquare.models.Photo;
import com.example.chungwei.placetogo.services.foursquare.models.VenuePhotoResult;

import java.util.ArrayList;

public class NearbyRecyclerViewAdapter extends RecyclerView.Adapter<NearbyRecyclerViewAdapter.ViewHolder> {

    private Context context;
    private ArrayList<Item> list;
    private FoursquareService foursquareService;

    public NearbyRecyclerViewAdapter(Context context, ArrayList<Item> list, FoursquareService foursquareService) {
        this.context = context;
        this.list = list;
        this.foursquareService = foursquareService;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.location_item_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView photo_imageView;
        private TextView title_textView;

        public ViewHolder(View itemView) {
            super(itemView);

            photo_imageView = itemView.findViewById(R.id.photo_imageView);
            title_textView = itemView.findViewById(R.id.title_textView);
        }

        public void bind(@NonNull Item item) {
            title_textView.setText(item.getVenue().getName());
            foursquareService.getVenuePhotos(new IFoursquareResponse<VenuePhotoResult>() {
                @Override
                public void onResponse(VenuePhotoResult result) {
                    String url = "";

                    if (result.getResponse().getPhotos().getItems().size() > 0) {
                        Photo photo = result.getResponse().getPhotos().getItems().get(0);

                        url = photo.getPrefix() +
                                photo.getWidth() + "x" + photo.getHeight() + photo.getSuffix();
                    }

                    Glide.with(context)
                            .load(url)
                            .placeholder(R.drawable.ic_image_placeholder_gray_24dp)
                            .centerCrop()
                            .into(photo_imageView);
                }

                @Override
                public void onErrorResponse(VolleyError error) {

                }
            }, item.getVenue().getId(), 1);
        }
    }
}
