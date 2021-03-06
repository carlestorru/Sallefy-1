package salle.android.projects.registertest.restapi.callback;

import java.util.List;

import salle.android.projects.registertest.model.Track;

public interface TrackCallback extends FailureCallback {
    void onTracksReceived(List<Track> tracks);
    void onNoTracks(Throwable throwable);
    void onPersonalTracksReceived(List<Track> tracks);
    void onUserTracksReceived(List<Track> tracks);
    void onCreateTrack();
    void onLikeSuccess(Track track);
    void getTrack(Track track);
}