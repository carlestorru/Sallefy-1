package salle.android.projects.registertest.controller.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import salle.android.projects.registertest.R;
import salle.android.projects.registertest.controller.adapters.PlaylistListAdapter;
import salle.android.projects.registertest.controller.adapters.TrackListAdapter;
import salle.android.projects.registertest.controller.callbacks.FragmentCallback;
import salle.android.projects.registertest.controller.callbacks.PlaylistAdapterCallback;
import salle.android.projects.registertest.model.Playlist;
import salle.android.projects.registertest.model.Track;
import salle.android.projects.registertest.restapi.callback.MeCallback;
import salle.android.projects.registertest.restapi.callback.PlaylistCallback;
import salle.android.projects.registertest.restapi.manager.MeManager;
import salle.android.projects.registertest.restapi.manager.PlaylistManager;

public class AddSongToPlaylistFragment extends Fragment implements PlaylistAdapterCallback, MeCallback, PlaylistCallback, FragmentCallback {

    public static final String TAG = AddSongToPlaylistFragment.class.getName();

    private RecyclerView mRecyclerView;
    private ArrayList<Playlist> mPlaylists;
    private ArrayList<Track> mTracks;
    private Playlist playlist;
    private Fragment fragment;
    private int currentPlaylist = 0;
    private FragmentCallback callback;
    private Track track;


    public AddSongToPlaylistFragment(Track track, Fragment fragment){
        this.track = track;
        this.fragment = fragment;
    }

    public static AddSongToPlaylistFragment getInstance(Track track, Fragment fragment) {
        return new AddSongToPlaylistFragment(track, fragment);
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        this.callback = (FragmentCallback) getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =inflater.inflate(R.layout.fragment_addsongtoplaylist, container, false);
        initViews(v);
        getData();
        return v;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private void initViews(View v) {
        mRecyclerView = (RecyclerView) v.findViewById(R.id.dynamic_recyclerView);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
        PlaylistListAdapter adapter = new PlaylistListAdapter(null, getContext(), null, R.layout.playlist_item);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(adapter);
    }

    private void getData() {
        MeManager.getInstance(getActivity()).getMyPlaylists(this);
        mPlaylists = new ArrayList<>();
    }

    public int getIndex(){
        return currentPlaylist;
    }

    private void addTrackToPlaylist() {
        PlaylistManager.getInstance(getContext()).addSong(playlist, this);
    }


    /**********************************************************************************************
     *   *   *   *   *   *   *   *   PlaylistAdapterCallback   *   *   *   *   *   *   *   *   *
     **********************************************************************************************/

    @Override
    public void onPlaylistClick(Playlist playlist) {
        boolean existeix = false;
        this.playlist = playlist;
        mTracks = (ArrayList<Track>) playlist.getTracks();
        for (int i = 0; i < playlist.getTracks().size(); i++) {
            if (track.getId() == playlist.getTracks().get(i).getId()) {
                existeix = true;
                break;
            }
        }
        if (!existeix) {
            mTracks.add(track);
            playlist.setTracks(mTracks);
            addTrackToPlaylist();
            Toast.makeText(getContext(), track.getName() + " added to " + playlist.getName() , Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(getContext(), track.getName() + " already exists in " + playlist.getName() , Toast.LENGTH_LONG).show();
        }
        onChangeFragment(fragment);
    }

    @Override
    public void onPlaylistClick(int index) {

    }

    /**********************************************************************************************
     *   *   *   *   *   *   *   *   MeCallback   *   *   *   *   *   *   *   *   *
     **********************************************************************************************/

    @Override
    public void myPlaylistsReceived(List<Playlist> playlists) {
        mPlaylists = (ArrayList) playlists;
        PlaylistListAdapter adapter = new PlaylistListAdapter(mPlaylists, getContext(), this, R.layout.playlist_item);
        mRecyclerView.setAdapter(adapter);
    }

    @Override
    public void playlistsFollowingReceived(List<Playlist> playlists) {

    }
    @Override
    public void myTracksReceived(List<Track> tracks) {

    }
    @Override
    public void tracksLikedReceived(List<Track> tracks) {

    }
    @Override
    public void noPlaylistsReceived(Throwable throwable) {

    }
    @Override
    public void noTracksReceived(Throwable throwable) {

    }

    /**********************************************************************************************
     *   *   *   *   *   *   *   *   PlaylistCallback   *   *   *   *   *   *   *   *   *
     **********************************************************************************************/

    @Override
    public void onFailure(Throwable throwable) {

    }
    @Override
    public void onShowPlaylist(List<Playlist> playlists) {
    }
    @Override
    public void onShowPlaylistFailure(Throwable throwable) {

    }
    @Override
    public void onCreateSuccess(Playlist playlist) {
    }
    @Override
    public void onCreateFailed(Throwable throwable) {

    }
    @Override
    public void onUpdateSucces(Playlist playlist) {

    }
    @Override
    public void onFollowSucces(Playlist playlist) {

    }
    @Override
    public void getIsFollowed(Playlist playlist) {

    }

    @Override
    public void getPlaylist(Playlist playlist) {

    }

    /**********************************************************************************************
     *   *   *   *   *   *   *   *   FragmentCallback   *   *   *   *   *   *   *   *   *
     **********************************************************************************************/

    @Override
    public void onChangeFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
    @Override
    public void updateTrack(ArrayList<Track> mTracks, int index) {

    }
}