package com.DYI.android.bulletin;

import android.os.Bundle;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.DYI.android.bulletin.Bulltins;
import com.DYI.android.bulletin.BulltinsContentActivity;
import com.DYI.android.bulletin.BulltinsContentFragment;
import com.baronzhang.android.weather.R;
import com.kongzue.dialog.interfaces.OnDialogButtonClickListener;
import com.kongzue.dialog.interfaces.OnInputDialogButtonClickListener;
import com.kongzue.dialog.interfaces.OnMenuItemClickListener;
import com.kongzue.dialog.util.BaseDialog;
import com.kongzue.dialog.v3.BottomMenu;
import com.kongzue.dialog.v3.InputDialog;
import com.kongzue.dialog.v3.MessageDialog;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuPopupHelper;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import org.litepal.LitePal;
import org.litepal.crud.LitePalSupport;
import org.litepal.tablemanager.Connector;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BulltinsTitleFragment extends Fragment  {
    private boolean isTwoPane;
    private SwipeRefreshLayout swipeRefresh;
    NewsAdapter adapter;
    List<com.DYI.android.bulletin.Bulltins> bulltinsList = new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.news_tiile_fragment1, container, false);
        RecyclerView newsTitleRecyclerView = (RecyclerView) view.findViewById(R.id.news_title_recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        newsTitleRecyclerView.setLayoutManager(layoutManager);
        adapter = new NewsAdapter(getNews());
        newsTitleRecyclerView.setAdapter(adapter);
        swipeRefresh =(SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh);
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                       getNews();
                       adapter.notifyDataSetChanged();
                        // 加载完数据设置为不刷新状态，将下拉进度收起来
                        swipeRefresh.setRefreshing(false);
                    }
                }, 1200);

            }
        });
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getActivity().findViewById(R.id.news_content_fragment) != null) {
            isTwoPane = true; // 可以找到news_content_layout布局时，为双页模式
        } else {
            isTwoPane = false; // 找不到news_content_layout布局时，为单页模式
        }
    }
    public List<com.DYI.android.bulletin.Bulltins> getNews() {
        bulltinsList.clear();
        List<Bulltins> Bulltins= LitePal.order("id desc").find(Bulltins.class);
        for (Bulltins bulltins1:Bulltins){
         com.DYI.android.bulletin.Bulltins bulltins =new com.DYI.android.bulletin.Bulltins();
         bulltins.setTitle(bulltins1.getTitle());
         bulltins.setContent(bulltins1.getContent());
         bulltinsList.add(bulltins);
     }
        return bulltinsList;
    }


    class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {

        private List<com.DYI.android.bulletin.Bulltins> mBulltinsList;

        class ViewHolder extends RecyclerView.ViewHolder {

            TextView newsTitleText;

            public ViewHolder(View view) {
                super(view);
                newsTitleText = (TextView) view.findViewById(R.id.news_title);
            }

        }


        public NewsAdapter(List<com.DYI.android.bulletin.Bulltins> bulltinsList) {
            mBulltinsList = bulltinsList;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_item, parent, false);
            final ViewHolder holder = new ViewHolder(view);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    com.DYI.android.bulletin.Bulltins bulltins = mBulltinsList.get(holder.getAdapterPosition());
                    if (isTwoPane) {
                        BulltinsContentFragment newsContentFragment = (BulltinsContentFragment)
                                getFragmentManager().findFragmentById(R.id.news_content_fragment);
                        newsContentFragment.refresh(bulltins.getTitle(), bulltins.getContent());
                    } else {
                        BulltinsContentActivity.actionStart(getActivity(), bulltins.getTitle(), bulltins.getContent());
                    }
                }
            });
            view.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    BottomMenu.show((AppCompatActivity) getContext(), new String[]{"修改", "删除"}, new OnMenuItemClickListener() {
                        @Override
                        public void onClick(String text, int index) {
                            com.DYI.android.bulletin.Bulltins bulltins = mBulltinsList.get(holder.getAdapterPosition());
                            switch (text){
                                case "删除":
                                    if(UsersLoginState.getLoginstate()){
                                    MessageDialog.show((AppCompatActivity)getActivity(),"温馨提示","公告内容删除后无法恢复，您确定要删除吗","确定","取消").
                                            setOkButton(new OnDialogButtonClickListener() {
                                                @Override
                                                public boolean onClick(BaseDialog baseDialog, View v) {
                                                    mBulltinsList.remove(holder.getAdapterPosition());
                                                    LitePal.deleteAll(Bulltins.class,"title==?",bulltins.getTitle());
                                                    notifyDataSetChanged();
                                                    return false;
                                                }
                                            });
                                    }else if (!UsersLoginState.getLoginstate()){
                                        MessageDialog.show((AppCompatActivity)getActivity(),"提示","你还未登录管理员身份，请先点击右上角用户按钮进行登录后再进行后续操作。") ;
                                    }
                                    break;
                                case "修改":
                                    if (UsersLoginState.getLoginstate()) {
                                        Bulletin_add.actionStart(getContext(), bulltins.getTitle(), bulltins.getContent());

                                    }else {
                                        MessageDialog.show((AppCompatActivity)getActivity(),"提示","你还未登录管理员身份，请先点击右上角用户按钮进行登录后再进行后续操作。") ;
                                    }
                                    break;
                                default:
                                    break;
                            }
                        }
                    });
                    return true;
                }
            });

            return holder;
        }
        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            com.DYI.android.bulletin.Bulltins bulltins = mBulltinsList.get(position);
            holder.newsTitleText.setText(bulltins.getTitle());
        }
        @Override
        public int getItemCount() {
            return mBulltinsList.size();
        }
    }
}
