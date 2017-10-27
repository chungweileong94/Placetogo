package com.example.chungwei.placetogo.adapters;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.bumptech.glide.Glide;
import com.example.chungwei.placetogo.R;
import com.example.chungwei.placetogo.services.foursquare.FoursquareService;
import com.example.chungwei.placetogo.services.foursquare.IFoursquareResponse;
import com.example.chungwei.placetogo.services.foursquare.models.Item;
import com.example.chungwei.placetogo.services.foursquare.models.Photo;
import com.example.chungwei.placetogo.services.foursquare.models.VenuePhotoResult;
import com.github.mmin18.widget.RealtimeBlurView;

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

    class ViewHolder extends RecyclerView.ViewHolder {

        private Item item;
        private String imageURL = "";
        private ImageView photo_imageView;
        private TextView title_textView;
        private TextView distance_textView;
        private TextView ratingTextView;
        private Dialog dialog;

        ViewHolder(final View itemView) {
            super(itemView);

            photo_imageView = itemView.findViewById(R.id.photo_imageView);
            title_textView = itemView.findViewById(R.id.title_textView);
            distance_textView = itemView.findViewById(R.id.distance_textView);
            ratingTextView = itemView.findViewById(R.id.ratingTextView);

            itemView.setOnClickListener(view -> {
                final RealtimeBlurView blurView = ((Activity) view.getContext()).findViewById(R.id.blurLayout);
                blurView.setVisibility(View.VISIBLE);

                dialog = new Dialog(view.getContext(), android.R.style.Theme_Material_Light_Dialog);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.location_pop_up_dialog_layout);

                TextView textViewPlaceName = dialog.findViewById(R.id.textViewPlaceName);
                TextView textViewOpenHours = dialog.findViewById(R.id.textViewOpenHours);
                TextView textViewTip = dialog.findViewById(R.id.textViewTip);
                TextView textViewRatingText = dialog.findViewById(R.id.textViewRatingText);
                ImageView imageViewPlace = dialog.findViewById(R.id.imageViewPlace);
                ImageView imageViewNavigation = dialog.findViewById(R.id.imageViewNavigation);
                ImageView imageViewPhone = dialog.findViewById(R.id.imageViewPhone);
                Button buttonCancel = dialog.findViewById(R.id.buttonCancel);
                LinearLayout operationHoursLayout = dialog.findViewById(R.id.operationHoursLayout);

                textViewPlaceName.setText(item.getVenue().getName());
                textViewRatingText.setText(String.valueOf(item.getVenue().getRating()));

                if (item.getVenue().getHours() == null) {
                    operationHoursLayout.setVisibility(View.GONE);
                } else {
                    String richStatus = item.getVenue().getHours().getStatus();

                    if (richStatus == null) {
                        textViewOpenHours.setText("-");
                        operationHoursLayout.setVisibility(View.GONE);
                    } else {
                        textViewOpenHours.setText(richStatus);
                    }
                }

                String tips = String.valueOf(item.getTips().get(0).getText());
                if (tips.isEmpty() || tips.trim().length() < 0) {
                    textViewTip.setText(R.string.no_tips_provided);
                } else {
                    textViewTip.setText(tips);
                }

                imageViewPhone.setOnClickListener(v -> {
                    String phoneNumber = String.valueOf(item.getVenue().getContact().getPhone());
                    String shopName = item.getVenue().getName();

                    if (phoneNumber != null && phoneNumber.length() > 8) {
                        callCaller(itemView.getContext(), phoneNumber, shopName);
                    } else {
                        Snackbar.make(v, "Invalid Phone Number", Snackbar.LENGTH_LONG).setAction("Close", null).show();
                    }
                });


                imageViewNavigation.setOnClickListener(v -> {
                    String place = item.getVenue().getName();

                    double lat = item.getVenue().getLocation().getLat();
                    double lng = item.getVenue().getLocation().getLng();

//                    //Launch map activity together with the location marked on the map.
//                    Intent i = new Intent(v.getContext(), MapsActivity.class);
//                    i.putExtra("placeName", place);
//                    i.putExtra("place_long", lng);
//                    i.putExtra("place_lati", lat);
//                    v.getContext().startActivity(i);
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setPackage("com.google.android.apps.maps");
                    intent.setData(Uri.parse("geo:0,0?q=" + lat + "," + lng + "(" + place + ")"));

                    if (intent.resolveActivity(v.getContext().getPackageManager()) != null) {
                        v.getContext().startActivity(intent);
                    }
                });

                buttonCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });

                Glide.with(context)
                        .load(imageURL)
                        .placeholder(R.drawable.ic_image_placeholder_gray_24dp)
                        .centerCrop()
                        .into(imageViewPlace);

                dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialogInterface) {
                        blurView.setVisibility(View.GONE);
                    }
                });

                dialog.show();

                WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                Window window = dialog.getWindow();
                lp.copyFrom(window.getAttributes());
                //This makes the dialog take up the full width
                lp.width = WindowManager.LayoutParams.MATCH_PARENT;
                lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
                window.setAttributes(lp);
            });
        }

        void bind(@NonNull Item item) {
            this.item = item;
            title_textView.setText(item.getVenue().getName());
            distance_textView.setText(item.getVenue().getLocation().getDistance() + "m");
            ratingTextView.setText(String.valueOf(item.getVenue().getRating()));

            foursquareService.getVenuePhotos(new IFoursquareResponse<VenuePhotoResult>() {
                @Override
                public void onResponse(VenuePhotoResult result) {
                    String url;

                    if (result.getResponse().getPhotos().getItems().size() > 0) {
                        Photo photo = result.getResponse().getPhotos().getItems().get(0);

                        url = photo.getPrefix() +
                                photo.getWidth() + "x" + photo.getHeight() + photo.getSuffix();

                        imageURL = url;

                        Glide.with(context)
                                .load(url)
                                .placeholder(R.drawable.ic_image_placeholder_gray_24dp)
                                .centerCrop()
                                .into(photo_imageView);
                    }
                }

                @Override
                public void onErrorResponse(VolleyError error) {

                }
            }, item.getVenue().getId(), 1);
        }

        private void callCaller(Context context, String phoneNumber, String contactName) {
            final int REQUEST_CALL_CONTACTS = 456;

            if (ActivityCompat.checkSelfPermission(context, android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions((Activity) context, new String[]{android.Manifest.permission.CALL_PHONE}, REQUEST_CALL_CONTACTS);
                Snackbar.make(((Activity) context).findViewById(android.R.id.content), "Phone permission required.", Snackbar.LENGTH_LONG)
                        .setAction("CLOSE", null)
                        .show();
            } else {
                ActivityCompat.requestPermissions((Activity) context, new String[]{android.Manifest.permission.CALL_PHONE}, REQUEST_CALL_CONTACTS);
                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:" + phoneNumber));
                context.startActivity(intent);
                Toast.makeText(context, "Calling " + contactName, Toast.LENGTH_LONG).show();
            }
        }

        private String joinStrings(ArrayList<String> strings) {
            StringBuilder result = new StringBuilder();

            for (String s : strings) {
                result.append(s);
            }

            return result.toString();
        }
    }
}