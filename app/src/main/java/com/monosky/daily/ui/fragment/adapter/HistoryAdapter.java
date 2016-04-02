package com.monosky.daily.ui.fragment.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.monosky.daily.BaseApplication;
import com.monosky.daily.R;
import com.monosky.daily.module.entity.PostsEntity;
import com.monosky.daily.ui.activity.ContentDetailActivity;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersAdapter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 往期内容适配器
 */
public class HistoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
        implements StickyRecyclerHeadersAdapter<RecyclerView.ViewHolder> {

    private List<PostsEntity> mPostsEntities = new ArrayList<>();
    private Context mContext;
    private ImageLoader imageLoader = ImageLoader.getInstance();
    private View.OnClickListener mHistoryClickListener;
    private SimpleDateFormat parseSdf = new SimpleDateFormat("yyyy-MM-dd");
    private SimpleDateFormat formatSdf = new SimpleDateFormat("dd", Locale.ENGLISH);
    private SimpleDateFormat formatSdfMonth = new SimpleDateFormat("MMM", Locale.ENGLISH);

    public HistoryAdapter(List<PostsEntity> list) {
        this.mPostsEntities.clear();
        this.mPostsEntities.addAll(list);
        this.mHistoryClickListener = historyClickListener;
        this.mContext = BaseApplication.getContext();
    }

    View.OnClickListener historyClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int pos = (int) v.getTag(R.id.history_content_layout);
            Intent intent = new Intent(mContext, ContentDetailActivity.class);
            mContext.startActivity(intent);
        }
    };

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public long getHeaderId(int position) {
        return getPublishDate(position).getTime();
    }

    public Date getPublishDate(int position) {
        try {
            return parseSdf.parse(mPostsEntities.get(position).getDate());
        } catch (ParseException e) {
            e.printStackTrace();
            return new Date();
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateHeaderViewHolder(ViewGroup parent) {
        // 创建一个View
        View view = View.inflate(parent.getContext(), R.layout.fragment_history_header, null);
        return new HeaderViewHolder(view);
    }

    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder, int position) {
        HeaderViewHolder headerViewHolder = (HeaderViewHolder) holder;
        Date publishTime = getPublishDate(position);
        headerViewHolder.mItemHeader.setText(formatSdf.format(publishTime));
        headerViewHolder.mItemHeaderMonth.setText(formatSdfMonth.format(publishTime));
    }

    @Override
    public int getItemCount() {
        return mPostsEntities.size();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // 创建一个View
        View view = View.inflate(parent.getContext(), R.layout.fragment_history_item, null);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ItemViewHolder itemHolder = (ItemViewHolder) holder;

        PostsEntity postsEntity = mPostsEntities.get(position);
        itemHolder.mHistoryContentTitle.setText(postsEntity.getTitle());

    }

    public void refresh(List<PostsEntity> refreshList) {
        mPostsEntities.clear();
        mPostsEntities.addAll(refreshList);
        this.notifyDataSetChanged();
    }

    static class HeaderViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.item_header)
        TextView mItemHeader;
        @Bind(R.id.item_header_month)
        TextView mItemHeaderMonth;

        HeaderViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    static class ItemViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.history_content_catalog)
        TextView mHistoryContentCatalog;
        @Bind(R.id.catalog_layout)
        RelativeLayout mCatalogLayout;
        @Bind(R.id.history_content_title)
        TextView mHistoryContentTitle;
        @Bind(R.id.history_content_img)
        ImageView mHistoryContentImg;
        @Bind(R.id.history_content_label)
        TextView mHistoryContentLabel;
        @Bind(R.id.history_content_layout)
        RelativeLayout mHistoryContentLayout;

        ItemViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }
}