package com.DYI.android.home;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.DYI.android.Repair.RepairMangerActivity;
import com.DYI.android.Repair.RepairWelcomeActivity;
import com.DYI.android.Repair.repairee_actitvity;
import com.DYI.android.bulletin.UsersLoginState;
import com.DYI.android.bulletin.bulletin;
import com.DYI.android.fee.BarChartActivityMultiDataset;
import com.DYI.android.fee.fee_activity;
import com.baronzhang.android.weather.R;
import com.DYI.android.weather.feature.home.MainActivity;
import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.google.android.material.navigation.NavigationView;
import com.j256.ormlite.stmt.query.In;
import com.kongzue.dialog.interfaces.OnDialogButtonClickListener;
import com.kongzue.dialog.util.BaseDialog;
import com.kongzue.dialog.util.DialogSettings;
import com.kongzue.dialog.v3.MessageDialog;

import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class MagaCommunity extends AppCompatActivity implements BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener {

        private SliderLayout mDemoSlider;
        private Context mContext;
        private GridView grid_photo;
        private BaseAdapter mAdapter = null;
        private ArrayList<Icon> mData = null;
        private DrawerLayout drawerLayout;
        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_maga_community);
            androidx.appcompat.widget.Toolbar toolbar=(Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            DialogSettings.style = DialogSettings.STYLE.STYLE_IOS;
            mDemoSlider = (SliderLayout) findViewById(R.id.slider);
            drawerLayout=(DrawerLayout) findViewById(R.id.drawer_layout);
            ActionBar actionBar=getSupportActionBar();
            if (actionBar!=null){
                actionBar.setDisplayHomeAsUpEnabled(true);
                actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
            }
            NavigationView navigationView=(NavigationView) findViewById(R.id.id_nav);;
            View headerView=(View) navigationView.getHeaderView(0);
            CircleImageView circleImageView=(CircleImageView) headerView.findViewById(R.id.icon_image);
            TextView textView=(TextView) headerView.findViewById(R.id.username);
            Menu menu=(Menu) navigationView.getMenu();
            MenuItem menuItem= (MenuItem) menu.findItem(R.id.ic_login_state);
            SetLoginText(menuItem);
            SetHeaderView(circleImageView,textView);
            navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    switch (menuItem.getItemId()){
                        case R.id.ic_login_state:
                            String title=menuItem.getTitle().toString();
                            if (title.equals("????????????")) {
                                Intent intent=new Intent(MagaCommunity.this,log_in_activity.class);
                                startActivity(intent);
                                finish();
                            }else if(title.equals("????????????")){
                                MessageDialog.show(MagaCommunity.this,"????????????","?????????????????????????","??????","??????").setOkButton(new OnDialogButtonClickListener() {
                                    @Override
                                    public boolean onClick(BaseDialog baseDialog, View v) {
                                        UsersLoginState.setLoginstateNegative();
                                        if (UsersLoginState.getIsManger())UsersLoginState.setIsManagerstateNegative();
                                        SetLoginText(menuItem);
                                        SetHeaderView(circleImageView,textView);
                                        return false;
                                    }
                                }).setCancelButton(new OnDialogButtonClickListener() {
                                    @Override
                                    public boolean onClick(BaseDialog baseDialog, View v) {
                                        return false;
                                    }
                                });

                            }
                            break;
                        case R.id.ic_help:
                            break;
                        default:
                            break;
                     }
                    return false;
                }
            });
            HashMap<String, Integer> file_maps = new HashMap<String, Integer>();
            file_maps.put("?????????????????????????????????", R.drawable.one);
            file_maps.put("??????????????????", R.drawable.ic_virus);
            for (String name : file_maps.keySet()) {
                TextSliderView textSliderView = new TextSliderView(this);
                // initialize a SliderLayout
                textSliderView
                        .description(name)
                        .image(file_maps.get(name))
                        .setScaleType(BaseSliderView.ScaleType.Fit)
                        .setOnSliderClickListener(this);

                //add your extra information
                textSliderView.bundle(new Bundle());
                textSliderView.getBundle()
                        .putString("extra", name);
                mDemoSlider.addSlider(textSliderView);
            }
            mDemoSlider.setPresetTransformer(SliderLayout.Transformer.Accordion);
            mDemoSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
            mDemoSlider.setCustomAnimation(new DescriptionAnimation());
            mDemoSlider.setDuration(4000);
            mDemoSlider.addOnPageChangeListener(this);
            mContext = MagaCommunity.this;
            grid_photo = (GridView) findViewById(R.id.grid_photo);
            mData = new ArrayList<Icon>();
            mData.add(new Icon(R.drawable.ic_bbulletin, "?????????"));
            mData.add(new Icon(R.drawable.ic_water, "?????????"));
            mData.add(new Icon(R.drawable.ic_wrong, "????????????"));
            mData.add(new Icon(R.drawable.ic_park, "?????????"));
            mData.add(new Icon(R.drawable.ic_weather, "??????"));
            mData.add(new Icon(R.drawable.ic_map, "??????"));
            mData.add(new Icon(R.drawable.ic_food, "??????"));
            mData.add(new Icon(R.drawable.ic_star, "??????"));
            mData.add(new Icon(R.drawable.ic_travel, "??????"));
            mAdapter = new MyAdapter<Icon>(mData, R.layout.item_grid_icon) {
                @Override
                public void bindView(MyAdapter.ViewHolder holder, Icon obj) {
                    holder.setImageResource(R.id.img_icon, obj.getiId());
                    holder.setText(R.id.txt_icon, obj.getiName());
                }
            };
            grid_photo.setAdapter(mAdapter);
            grid_photo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
    //                Toast.makeText(mContext, "????????????~" + position + "~???", Toast.LENGTH_SHORT).show();
                    if (position==0){
                        gotoBulletin();
                    }
                    if (position==1){
                        gotoFeeactivity();
                    }
                    if (position==2){
                        gotoRepairWelcomeActivity();
                    }
                    if (position==4){
                        gotoMainPage();
                    }
                }
            });

        }
        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            switch (item.getItemId()) {
                case android.R.id.home:
                    drawerLayout.openDrawer(GravityCompat.START);
                    break;

                default:
                    break;
            }
            return true;
        }
        private void gotoRepairWelcomeActivity() {
            Intent intent;
            if (!UsersLoginState.getIsManger()) {
                intent = new Intent(MagaCommunity.this, RepairWelcomeActivity.class);
                startActivity(intent);
            }else  if (UsersLoginState.getIsManger()){
                intent =new Intent(MagaCommunity.this, RepairMangerActivity.class);
                startActivity(intent);
            }

            finish();
        }

        private void gotoFeeactivity() {
            Intent intent =new Intent(MagaCommunity.this, BarChartActivityMultiDataset.class);
            startActivity(intent);
            finish();
        }

        private void gotoBulletin() {
            Intent intent = new Intent(this, bulletin.class);

            startActivity(intent);
            // ?????? Android 9.0 ??? Activity ?????????????????????????????????????????????
            overridePendingTransition(0, 0);
            finish();
        }

        @Override
        public void onSliderClick (BaseSliderView slider){
            Toast.makeText(this, slider.getBundle().get("extra") + "", Toast.LENGTH_SHORT).show();

        }
        @Override
        public void onPageScrolled ( int position, float positionOffset, int positionOffsetPixels){

        }
        @Override
        public void onPageSelected ( int position){
            Log.d("Slider Demo", "Page Changed: " + position);
        }
        @Override
        public void onPageScrollStateChanged ( int state){

        }
        private void gotoMainPage() {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            // ?????? Android 9.0 ??? Activity ?????????????????????????????????????????????
            overridePendingTransition(0, 0);
            finish();
        }
        private void SetLoginText(MenuItem menuItem){
            if (UsersLoginState.getLoginstate()){
                menuItem.setTitle("????????????");
                menuItem.setIcon(R.drawable.ic_log_in);
            }else {
                menuItem.setTitle("????????????");
                menuItem.setIcon(R.drawable.ic_log_out);
            }

        }
        private void SetHeaderView(CircleImageView circleImageView,TextView textView){
            if (UsersLoginState.getIsManger()){
                circleImageView.setImageResource(R.drawable.ic_manger);
                textView.setText("?????????");
            }else if (UsersLoginState.getLoginstate()&&!UsersLoginState.getIsManger()){
                circleImageView.setImageResource(R.drawable.ic_user);
                textView.setText("1001???????????????");
            }else {
                circleImageView.setImageResource(R.drawable.ic_traveler);
                textView.setText("??????");

            }
        }
}
