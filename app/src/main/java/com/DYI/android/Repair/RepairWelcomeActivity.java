package com.DYI.android.Repair;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.DYI.android.home.MagaCommunity;
import com.baronzhang.android.weather.R;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RepairWelcomeActivity extends AppCompatActivity {
    private List<Repairee> repaireeList = new ArrayList<>();

    private SwipeRefreshLayout swipeRefreshLayout;
    private RepaireeAdapter repaireeAdapter;
    private Repairee[] Repairee = {new Repairee(R.drawable.ic_repairee101, "黄涛", "国家一级电工"),
            new Repairee(R.drawable.ic_repairee102, "麦晓华", "国家一级电工"),
            new Repairee(R.drawable.ic_repairee103, "佟湘玉", "国家一级水工"),
            new Repairee(R.drawable.ic_repairee104, "黄蓉", "国家一级开锁匠"),
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repair_welcome);
        androidx.appcompat.widget.Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        addRepairee();
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(layoutManager);
        repaireeAdapter = new RepaireeAdapter(repaireeList);
        recyclerView.setAdapter(repaireeAdapter);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        swipeRefreshLayout.setOnRefreshListener(() -> refreshRepairees());
    }

    private void refreshRepairees() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        addRepairee();
                        Toast.makeText(RepairWelcomeActivity.this, "刷新成功", Toast.LENGTH_SHORT).show();
                        repaireeAdapter.notifyDataSetChanged();
                        swipeRefreshLayout.setRefreshing(false);
                    }
                });
            }
        }).start();
    }

    private void addRepairee() {
        repaireeList.clear();
        for (int i = 0; i < Repairee.length; i++) {
            repaireeList.add(Repairee[i]);
        }
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent intent=new Intent(RepairWelcomeActivity.this, MagaCommunity.class);
            startActivity(intent);
            finish();
        }
        return false;
    }
}