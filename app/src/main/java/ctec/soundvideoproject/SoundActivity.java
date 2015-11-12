package ctec.soundvideoproject;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.view.View;
import android.widget.*;
import android.os.Bundle;

/**
 * @author Rory Baker
 * @version 1.0 Base fundamentals covered in video.
 */

public class SoundActivity extends Activity implements Runnable
{
    private Button startButton;
    private Button stopButton;
    private Button pauseButton;
    private Button videoButton;
    private MediaPlayer soundPlayer;
    private SeekBar soundSeekBar;
    private Thread soundThread;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sound);

        startButton = (Button) findViewById(R.id.playButton);
        pauseButton = (Button) findViewById(R.id.pauseButton);
        stopButton = (Button) findViewById(R.id.stopButton);
        videoButton = (Button) findViewById(R.id.videoButton);
        soundSeekBar = (SeekBar) findViewById(R.id.soundSeekBar);
        soundPlayer = MediaPlayer.create(this.getBaseContext(), R.raw.in_the_garage);

        setupListeners();

        soundThread = new Thread(this);
        soundThread.start();
    }

    private void setupListeners()
    {
        startButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                soundPlayer.start();
            }
        });

        pauseButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                soundPlayer.pause();
            }
        });

        stopButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                soundPlayer.stop();
                soundPlayer = MediaPlayer.create(getBaseContext(), R.raw.in_the_garage);
            }
        });

        videoButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent myIntent = new Intent(videoButton.getContext(), VideoActivity.class);
                startActivityForResult(myIntent, 0);
            }
        });

        soundSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener()
        {
            @Override
            public void onStopTrackingTouch(SeekBar seekBar)
            {
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar)
            {
            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser)
            {
                if (fromUser)
                {
                    soundPlayer.seekTo(progress);
                }
            }
        });
    }

    /**
     * Required since we are implementing Runnable.
     * Allows the SeekBar to update.
     */
    @Override
    public void run()
    {
        int currentPosition = 0;
        int soundTotal = soundPlayer.getDuration();
        soundSeekBar.setMax(soundTotal);

        while (soundPlayer != null && currentPosition < soundTotal)
        {
            try
            {
                Thread.sleep(300);
                currentPosition = soundPlayer.getCurrentPosition();
            }
            catch(InterruptedException soundException)
            {
                return;
            }
            catch(Exception otherException)
            {
                return;
            }
            soundSeekBar.setProgress(currentPosition);
        }
    }
}
