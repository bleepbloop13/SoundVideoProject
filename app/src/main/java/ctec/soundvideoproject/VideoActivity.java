package ctec.soundvideoproject;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.*;
import android.net.Uri;
import android.os.Bundle;

/**
 * @author Rory Baker
 * @version 1.0 Basics from video added
 */

public class VideoActivity extends Activity
{
    private VideoView myPlayer;
    private Button returnButton;
    private MediaController myVideoController;
    private Uri videoLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

        myPlayer = (VideoView) findViewById(R.id.videoViewer);
        returnButton = (Button) findViewById(R.id.homeButton);

        videoLocation = Uri.parse(("android resource://") + getPackageName() + "/" + R.raw.wombo_combo);
        myVideoController = new MediaController(this);
        //Prepare the video
        setupMedia();
        setupListeners();
    }

    private void setupMedia()
    {
        myPlayer.setMediaController(myVideoController);
        myPlayer.setVideoURI(videoLocation);
    }

    private void setupListeners()
    {
        returnButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick (View view)
            {
                Intent returnIntent = new Intent();
                setResult(RESULT_OK, returnIntent);
                finish();
            }
        });
    }
}
