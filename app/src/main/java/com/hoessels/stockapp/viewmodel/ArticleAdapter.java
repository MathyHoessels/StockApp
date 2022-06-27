package com.hoessels.stockapp.viewmodel;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;
import com.hoessels.stockapp.R;
import com.hoessels.stockapp.domain.Article;
import com.hoessels.stockapp.domain.Order;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

public class ArticleAdapter extends ArrayAdapter<Integer> implements View.OnClickListener {
    private Order order;
    private Article[] mKeys;
    private Context mContext;
    private int lastPosition = -1; // used for animation

    private static class ViewHolder {
        TextView articleNr;
        TextView articleName;
        TextView amountOrdered;
        TextView articlePrice;
    }

    public ArticleAdapter(Context context, Order order) {
        super(context, R.layout.list_item, order.getArticles().values().toArray(new Integer[order.getArticles().size()]));

        this.mContext = context;
        this.order = order;
        this.mKeys = order.getArticles().keySet().toArray(new Article[order.getArticles().size()]); // list with the keys
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        // get the data for this position
        int amount = getAmount(position);
        Article article = mKeys[position];
        // check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder;

        final View result;

        if (view == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            view = inflater.inflate(R.layout.list_item, parent, false);
            viewHolder.articleNr = (TextView) view.findViewById(R.id.articleNr);
            viewHolder.articleName = (TextView) view.findViewById(R.id.articleName);
            viewHolder.amountOrdered = (TextView) view.findViewById(R.id.amountOrdered);
            viewHolder.articlePrice = (TextView) view.findViewById(R.id.articlePrice);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        Animation animation = AnimationUtils.loadAnimation(mContext, (position > lastPosition) ? R.anim.up_from_bottom : R.anim.down_from_top);
        view.startAnimation(animation);
        lastPosition = position;

        viewHolder.articleNr.setText(article.getNr()+"");
        viewHolder.articleName.setText(article.getName());
        viewHolder.articlePrice.setText(article.getPrice()+"");
        viewHolder.amountOrdered.setText(amount+"");

        return view;
    }

    @Override
    public void onClick(View v) {
        int position = (Integer) v.getTag();
        Object object = getItem(position);
        Article article = (Article) object;

        switch (v.getId()) {
            case R.layout.list_item:
                Snackbar.make(v, "Testje: "+article.getName(), Snackbar.LENGTH_SHORT).setAction("No action", null).show();
                break;
        }
    }

    /**
     * Get the amount ordered of a product
     * @param position int representation of the position at which
     *                 the article is in the TreeMap
     * @return int representation of the amount ordered of a specified product
     */
    public int getAmount(int position) {
        return order.getArticles().get(mKeys[position]);
    }
}
