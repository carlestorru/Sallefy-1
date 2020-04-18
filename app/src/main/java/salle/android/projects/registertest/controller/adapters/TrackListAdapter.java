package salle.android.projects.registertest.controller.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.like.LikeButton;

import java.util.ArrayList;

import salle.android.projects.registertest.R;
import salle.android.projects.registertest.controller.callbacks.FragmentCallback;
import salle.android.projects.registertest.controller.callbacks.TrackListCallback;
import salle.android.projects.registertest.controller.fragments.AddSongToPlaylistFragment;
import salle.android.projects.registertest.controller.fragments.PlaylistFragment;
import salle.android.projects.registertest.model.Track;

public class TrackListAdapter extends RecyclerView.Adapter<TrackListAdapter.ViewHolder> {

    private static final String TAG = "TrackListAdapter";
    private ArrayList<Track> mTracks;
    private Context mContext;
    private TrackListCallback mCallback;
    private Fragment backfragment;
    private int NUM_VIEWHOLDERS = 0;


    public TrackListAdapter(TrackListCallback callback, Context context, ArrayList<Track> tracks, Fragment fragment ) {
        mTracks = tracks;
        mContext = context;
        mCallback = callback;
        backfragment = fragment;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder: called. Num viewHolders: " + NUM_VIEWHOLDERS++);


        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.track_item, parent, false);
        ViewHolder vh = new TrackListAdapter.ViewHolder(itemView);
        Log.d(TAG, "onCreateViewHolder: called. viewHolder hashCode: " + vh.hashCode());
        return vh;
    }

    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        Log.d(TAG, "onBindViewHolder: called. viewHolder hashcode: " + holder.hashCode());

        if (mTracks.get(position).isLiked()){
            holder.likeButton.setLiked(true);
        }

        holder.likeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallback.onTrackLike(position);
                holder.likeButton.onClickAnimation(v);
            }
        });

        holder.imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = null;
                fragment = AddSongToPlaylistFragment.getInstance(mTracks.get(position), backfragment);
                mCallback.onTrackSelected(fragment);
            }
        });
        holder.mLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallback.onTrackSelected(position);
            }
        });
        holder.tvTitle.setText(mTracks.get(position).getName());
        holder.tvAuthor.setText(mTracks.get(position).getUserLogin());
        if (mTracks.get(position).getThumbnail() != null) {
            Glide.with(mContext)
                    .asBitmap()
                    .placeholder(R.drawable.ic_audiotrack)
                    .load(mTracks.get(position).getThumbnail())
                    .into(holder.ivPicture);
        }
    }

    @Override
    public int getItemCount() {
        return mTracks != null ? mTracks.size():0;
    }

    public void updateTrackLikeStateIcon(int position, boolean isLiked) {
        mTracks.get(position).setLiked(isLiked);
        notifyDataSetChanged();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout mLayout;
        TextView tvTitle;
        TextView tvAuthor;
        ImageView ivPicture;
        LikeButton likeButton;
        ImageButton imageButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mLayout = itemView.findViewById(R.id.track_item_layout);
            tvTitle = (TextView) itemView.findViewById(R.id.track_title);
            tvAuthor = (TextView) itemView.findViewById(R.id.track_author);
            ivPicture = (ImageView) itemView.findViewById(R.id.track_img);
            likeButton = (LikeButton) itemView.findViewById(R.id.heart_button);
            imageButton = (ImageButton) itemView.findViewById(R.id.add_song_to_playlist);
        }
    }
}