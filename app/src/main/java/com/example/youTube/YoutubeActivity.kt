package com.example.youTube

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.youtube.player.YouTubeBaseActivity
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.google.android.youtube.player.YouTubePlayerView
const val YOUTUBE_VIDEO_ID="h9u39k19SiU"
const val YOUTUBE_PLAYLIST="PL6kBTGMTaktoNXNBe5FqG5ENxUCoCpQPQ"

class YoutubeActivity : YouTubeBaseActivity(), YouTubePlayer.OnInitializedListener {
private val TAG = "YoutubeActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_youtube)
//        val layout= findViewById<ConstraintLayout>(R.id.activity_youtube);

        //This is a replacement for the above code
        val layout = layoutInflater.inflate(R.layout.activity_youtube, null) as ConstraintLayout
        setContentView(layout)

        val playerView= YouTubePlayerView(this)
        playerView.layoutParams= ConstraintLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT)
        layout.addView(playerView)

        playerView.initialize(getString(R.string.GOOGLE_API_KEY),this)

    }

    override fun onInitializationSuccess(provider: YouTubePlayer.Provider?, youTubePlayer: YouTubePlayer?, wasRestored: Boolean) {
        Log.d(TAG,"OninitializationSuccess:provider is  ${provider?.javaClass}")
        Log.d(TAG,"OninitializationSuccess:YouTubePlayer is  ${youTubePlayer?.javaClass}")
        Toast.makeText(this,"Initialized Player successfully",Toast.LENGTH_LONG).show()

        youTubePlayer?.setPlayerStateChangeListener(playerStateChangeListener)
        youTubePlayer?.setPlaybackEventListener(playbackEventListener)


        if(!wasRestored){
            youTubePlayer?.cueVideo(YOUTUBE_VIDEO_ID)
        }

    }

    override fun onInitializationFailure(provider: YouTubePlayer.Provider?,YouTubeInitializationResult: YouTubeInitializationResult?) {
        val REQUEST_CODE=0
        if (YouTubeInitializationResult?.isUserRecoverableError==true){
            YouTubeInitializationResult.getErrorDialog(this,REQUEST_CODE).show()
        }

        else{
            val errorMessage="There was an error initializing the Youtubeplayer($YouTubeInitializationResult)"
            Toast.makeText(this,errorMessage,Toast.LENGTH_LONG).show()

        }
    }

    private val playbackEventListener= object: YouTubePlayer.PlaybackEventListener{
        override fun onPlaying() {
           Toast.makeText(this@YoutubeActivity,"Good, Video is playing ok", Toast.LENGTH_SHORT).show()
        }

        override fun onPaused() {
            Toast.makeText(this@YoutubeActivity,"video has paused",Toast.LENGTH_SHORT).show()
        }

        override fun onStopped() {
            Toast.makeText(this@YoutubeActivity,"video has stopped",Toast.LENGTH_SHORT).show()

        }

        override fun onBuffering(p0: Boolean) {
        }

        override fun onSeekTo(p0: Int) {
        }
    }

    private val playerStateChangeListener = object: YouTubePlayer.PlayerStateChangeListener{
        override fun onLoading() {
        }

        override fun onLoaded(p0: String?) {

        }

        override fun onAdStarted() {
            Toast.makeText(this@YoutubeActivity,"Click Ad now, make the video creator rich", Toast.LENGTH_SHORT).show()        }

        override fun onVideoStarted() {
            Toast.makeText(this@YoutubeActivity,"Video has started" ,Toast.LENGTH_SHORT).show()         }

        override fun onVideoEnded() {
            Toast.makeText(this@YoutubeActivity,"Congratulations! You've completed another video" ,Toast.LENGTH_SHORT).show()

    }

        override fun onError(p0: YouTubePlayer.ErrorReason?) {
        }
    }
}