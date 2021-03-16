package com.DYI.android.bulletin;

import android.content.Context;
import android.content.Intent;

import com.DYI.android.bulletin.Bulletin_add;
import com.DYI.android.bulletin.Bulltins;
import com.DYI.android.bulletin.BulltinsTitleFragment;
import com.DYI.android.bulletin.bulletin;
import com.baronzhang.android.weather.R;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import org.litepal.LitePal;
import org.litepal.crud.LitePalSupport;

import java.util.List;

import static android.webkit.ConsoleMessage.MessageLevel.LOG;

public class BulltinsContentActivity extends AppCompatActivity {

    String newsTitle;
    String newsContent;
    public static void actionStart(Context context, String newsTitle, String newsContent) {
        Intent intent = new Intent(context, BulltinsContentActivity.class);
        intent.putExtra("news_title", newsTitle);
        intent.putExtra("news_content", newsContent);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_content);
        androidx.appcompat.widget.Toolbar toolbar=(Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        newsTitle = getIntent().getStringExtra("news_title"); // 获取传入的新闻标题
        newsContent = getIntent().getStringExtra("news_content"); // 获取传入的新闻内容
        com.DYI.android.bulletin.BulltinsContentFragment bulltinsContentFragment = (com.DYI.android.bulletin.BulltinsContentFragment) getSupportFragmentManager().findFragmentById(R.id.news_content_fragment);
        bulltinsContentFragment.refresh(newsTitle, newsContent); // 刷新NewsContentFragment界面
    }

}
