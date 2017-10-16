package com.example.chungwei.placetogo.adapters;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
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
import com.example.chungwei.placetogo.helpers.AllRounderClass;
import com.example.chungwei.placetogo.services.foursquare.FoursquareService;
import com.example.chungwei.placetogo.services.foursquare.IFoursquareResponse;
import com.example.chungwei.placetogo.services.foursquare.models.Item;
import com.example.chungwei.placetogo.services.foursquare.models.Photo;
import com.example.chungwei.placetogo.services.foursquare.models.VenuePhotoResult;
import com.example.chungwei.placetogo.services.foursquare.models.RichStatus;
import com.example.chungwei.placetogo.services.foursquare.models.Venue;
import com.example.chungwei.placetogo.services.foursquare.models.Hours;
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

    public class ViewHolder extends RecyclerView.ViewHolder {

        private Item item;
        private String imageURL = "";
        private ImageView photo_imageView;
        private TextView title_textView;
        private TextView distance_textView;
        private TextView address_textView;
        private TextView status_textView;
        private Dialog dialog;


        public ViewHolder(final View itemView) {
            super(itemView);

            photo_imageView = itemView.findViewById(R.id.photo_imageView);
            title_textView = itemView.findViewById(R.id.title_textView);
            distance_textView = itemView.findViewById(R.id.distance_textView);
            address_textView = itemView.findViewById(R.id.address_textView);
            status_textView = itemView.findViewById(R.id.status_textView);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    final AllRounderClass arc = new AllRounderClass();
                    final Hours hour = item.getVenue().getHours();

                    final RealtimeBlurView blurView= ((Activity)view.getContext()).findViewById(R.id.blurLayout);
                    blurView.setVisibility(View.VISIBLE);

                    dialog = new Dialog(view.getContext(), android.R.style.Theme_Material_Light_Dialog);

                    dialog.setCancelable(false);
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setContentView(R.layout.pop_up_dialog);

                            TextView textViewPlaceName = dialog.findViewById(R.id.textViewPlaceName);
                            TextView textViewOpenHours = dialog.findViewById(R.id.textViewOpenHours);
                            TextView textViewReason = dialog.findViewById(R.id.textViewReason);
                            TextView textViewTip = dialog.findViewById(R.id.textViewTip);
                            TextView textViewRatingText = dialog.findViewById(R.id.textViewRatingText);
                            ImageView imageViewPlace = dialog.findViewById(R.id.imageViewPlace);
                            ImageView imageViewNavigation = dialog.findViewById(R.id.imageViewNavigation);
                            ImageView imageViewPhone = dialog.findViewById(R.id.imageViewPhone);
                            Button buttonCancel = dialog.findViewById(R.id.buttonCancel);
                            FloatingActionButton ratingBack = dialog.findViewById(R.id.ratingBack);
                            LinearLayout operationHoursLayout = dialog.findViewById(R.id.operationHoursLayout);

                            textViewPlaceName.setText(item.getVenue().getName());
                            textViewReason.setText(String.valueOf(item.getReasons().getItems().get(0).getSummary()));
                            textViewRatingText.setText(String.valueOf(item.getVenue().getRating()));
                            String color = "#"+item.getVenue().getRatingColor();
                            ratingBack.setBackgroundColor(Color.parseColor(color));

                    if(hour==null){
                        textViewOpenHours.setText("-");
                        operationHoursLayout.setVisibility(View.GONE);
                    }
                    else{

                        String richStatus = item.getVenue().getHours().getStatus();

                        if(richStatus==null){
                            textViewOpenHours.setText("-");
                            operationHoursLayout.setVisibility(View.GONE);
                        }
                        else{
                            textViewOpenHours.setText(richStatus);
                        }
                    }
                                    String tips = String.valueOf(item.getTips().get(0).getText());
                                    if(tips.isEmpty() || tips.length()<0){
                                        textViewTip.setText("No tips provided.");
                                    }
                                    else{
                                        textViewTip.setText(tips);
                                    }

                                    imageViewNavigation.setOnLongClickListener(new View.OnLongClickListener() {
                                        @Override
                                        public boolean onLongClick(View view) {
                                            Log.i("navigation","working");
                                            return false;
                                        }
                                    });

                                    imageViewPhone.setOnLongClickListener(new View.OnLongClickListener() {
                                        @Override
                                        public boolean onLongClick(View view) {

                                            String phoneNumber = String.valueOf(item.getVenue().getContact().getPhone());
                                            String shopName = item.getVenue().getName();

                                            if(phoneNumber!=null && phoneNumber.length()>8){
                                                arc.callcaller(itemView.getContext(),phoneNumber,shopName);
                                            }
                                            else{
                                                arc.SnackbarMessage(view,"Invalid Phone Number","CLOSE");
                                            }

                                            return false;
                                        }
                                    });

                                    Glide.with(context)
                                            .load(imageURL)
                                            .placeholder(R.drawable.ic_image_placeholder_gray_24dp)
                                            .centerCrop()
                                            .into(imageViewPlace);

                    dialog.getWindow().getAttributes().windowAnimations = R.anim.fade_in_animation;
                    dialog.show();

                    buttonCancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            blurView.setVisibility(View.GONE);
                            dialog.dismiss();
                        }
                    });
                }
            });



        }

        public void bind(@NonNull Item item) {
            this.item = item;
            title_textView.setText(item.getVenue().getName());
            distance_textView.setText(item.getVenue().getLocation().getDistance() + "m");
            address_textView.setText(joinStrings(item.getVenue().getLocation().getFormattedAddress()));

            Hours hour = item.getVenue().getHours();

            if(hour==null){
                status_textView.setText("UNKNOWN");
            }
            else{
                boolean isOpenHour = hour.getIsOpen();

                if(isOpenHour=false){
                    status_textView.setText("CLOSE");
                }
                else if(isOpenHour=true){
                    status_textView.setText("OPEN");
                }
            }




            foursquareService.getVenuePhotos(new IFoursquareResponse<VenuePhotoResult>() {
                @Override
                public void onResponse(VenuePhotoResult result) {
                    String url = "";

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

        private String joinStrings(ArrayList<String> strings) {
            String result = "";

            for (String s : strings) {
                result += s;
            }

            return result;
        }
    }

    public static Bitmap takeScreenShot(Activity activity) {
        View view = activity.getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();


        Bitmap b1 = view.getDrawingCache();
        Rect frame = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        int statusBarHeight = frame.top;

        Display display = activity.getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;

        Bitmap b = Bitmap.createBitmap(b1, 0, statusBarHeight, width, height - statusBarHeight);
        view.destroyDrawingCache();
        return b;
    }

}