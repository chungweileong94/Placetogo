package com.example.chungwei.placetogo;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.bumptech.glide.Glide;
import com.example.chungwei.placetogo.helpers.PreferencesManager;
import com.example.chungwei.placetogo.services.placetogo.IPlacetogoResponse;
import com.example.chungwei.placetogo.services.placetogo.PlacetogoService;
import com.example.chungwei.placetogo.services.placetogo.models.Challenge;
import com.example.chungwei.placetogo.services.placetogo.models.ChallengeResult;

public class ChallengeActivity extends AppCompatActivity {

    //Object Declaration for the layout swipe screen.
    private int[] layouts;
    private TextView[] dots;
    private ViewPager viewPager;
    private ChallengeActivity.MyViewPagerAdapter myViewPagerAdapter;
    private PreferencesManager preferencesManager;
    private LinearLayout dotsLayout;

    private PlacetogoService placetogoService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Making notification bar transparent
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }

        setContentView(R.layout.activity_challenge);
        viewPager = findViewById(R.id.view_pager);
        dotsLayout = findViewById(R.id.layoutDots);

        layouts = new int[]{
                R.layout.challenge_1_layout,
                R.layout.challenge_2_layout,
                R.layout.challenge_3_layout,
                R.layout.challenge_4_layout};

        addBottomDots(0);
        changeStatusBarColor();

        placetogoService = new PlacetogoService(this);

        Context context = this;

        placetogoService.getChallenges(new IPlacetogoResponse<ChallengeResult>() {
            @Override
            public void onResponse(ChallengeResult result) {
                findViewById(R.id.loading_Layout).setVisibility(View.GONE);
                myViewPagerAdapter = new ChallengeActivity.MyViewPagerAdapter(result);
                viewPager.setAdapter(myViewPagerAdapter);
                viewPager.addOnPageChangeListener(viewPagerPageChangeListener);
            }

            @Override
            public void onErrorResponse(VolleyError error) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Connection Error");
                builder.setMessage("We not able to get the challenges data, please try again later.");
                builder.setPositiveButton("Okay", null);
                builder.setOnDismissListener(dialog -> ((AppCompatActivity) context).finish());
                builder.create().show();
            }
        });
    }

    private void addBottomDots(int currentPage) {

        dots = new TextView[layouts.length];

        int[] colorsActive = getResources().getIntArray(R.array.array_dot_active);
        int[] colorsInactive = getResources().getIntArray(R.array.array_dot_inactive);

        dotsLayout.removeAllViews();
        for (int i = 0; i < dots.length; i++) {
            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(35);
            dots[i].setTextColor(colorsInactive[currentPage]);
            dotsLayout.addView(dots[i]);
        }

        if (dots.length > 0) {
            dots[currentPage].setTextColor(colorsActive[currentPage]);
        }
    }

    //Method changing notification bar transparent
    private void changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }

    public class MyViewPagerAdapter extends PagerAdapter {

        private ChallengeResult challengeResult;

        MyViewPagerAdapter(ChallengeResult challengeResult) {
            this.challengeResult = challengeResult;
        }

        //Changing view to new welcome screen.
        public Object instantiateItem(ViewGroup container, int position) {
            LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = layoutInflater.inflate(layouts[position], container, false);

            Challenge challenge = challengeResult.getChallenges()[position];

            ((TextView) view.findViewById(R.id.title_textView)).setText(challenge.getTitle());
            ((TextView) view.findViewById(R.id.content_textView)).setText(challenge.getContent());

            Glide.with(view.getContext())
                    .load("https://placetogo.herokuapp.com/" + challenge.getImageUrl())
                    .placeholder(R.drawable.ic_image_placeholder_gray_24dp)
                    .centerCrop()
                    .into((ImageView) view.findViewById(R.id.challenge_imageView));

            container.addView(view);
            return view;
        }

        @Override
        public int getCount() {
            return layouts.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object obj) {
            return view == obj;
        }


        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            View view = (View) object;
            container.removeView(view);
        }
    }

    ViewPager.OnPageChangeListener viewPagerPageChangeListener = new ViewPager.OnPageChangeListener() {

        //Changing visibility and button text according to the foreground welcome screen.
        @Override
        public void onPageSelected(int position) {
            addBottomDots(position);
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }

        @Override
        public void onPageScrollStateChanged(int arg0) {
        }
    };

}





























