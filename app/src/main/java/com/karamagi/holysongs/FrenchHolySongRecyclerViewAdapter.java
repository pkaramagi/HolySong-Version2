package com.karamagi.holysongs;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.karamagi.holysongs.FrenchHolySongFragment.OnListFragmentInteractionListener;
import com.karamagi.holysongs.holysong.HolySongItem;


import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link HolySongItem} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class FrenchHolySongRecyclerViewAdapter extends RecyclerView.Adapter<FrenchHolySongRecyclerViewAdapter.ViewHolder> {

    private final List<HolySongItem> mValues;
    private final OnListFragmentInteractionListener mListener;
    private final ColorGenerator colorGenerator = ColorGenerator.MATERIAL;

    public FrenchHolySongRecyclerViewAdapter(List<HolySongItem> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_french_holysong, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mContentView.setText(mValues.get(position).getTitle());
        String letter =  String.valueOf(mValues.get(position).getId());
        TextDrawable drawable = TextDrawable.builder() .buildRound(letter, colorGenerator.getColor(letter));
        holder.imageView.setImageDrawable(drawable);

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    //  mListener.onListFragmentInteraction(holder.mItem);
                }
                HolySongItem item = mValues.get(holder.getAdapterPosition());
                Intent intent = new Intent(v.getContext(), HolySongSingle.class);
                intent.putExtra("EXTRA_HOLYSONG_URL", item.getUrl());
                intent.putExtra("EXTRA_HOLYSONG_TITLE", item.getTitle());
                intent.putExtra("EXTRA_HOLYSONG_LG", "fr");
                v.getContext().startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final ImageView imageView;
        public final TextView mContentView;
        public HolySongItem mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mContentView = (TextView) view.findViewById(R.id.content);
            imageView = (ImageView)view.findViewById(R.id.holysong_letter);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}
