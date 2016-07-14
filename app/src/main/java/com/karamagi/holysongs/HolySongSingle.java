package com.karamagi.holysongs;

import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.Toast;

import java.io.IOException;


public class HolySongSingle extends AppCompatActivity implements Runnable,
        SeekBar.OnSeekBarChangeListener {
    WebView webView;
    SeekBar seek_bar;
    MediaPlayer player;
    ImageButton play_button, pause_button , stop_button;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_holy_song_content);
        Bundle extras = getIntent().getExtras();
        final String holySongUrl = extras.getString("EXTRA_HOLYSONG_URL");
        final String holySongLang = extras.getString("EXTRA_HOLYSONG_LG");
        String holySongTitle = extras.getString("EXTRA_HOLYSONG_TITLE");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(holySongTitle);


        webView = (WebView) findViewById(R.id.webview);
        seek_bar = (SeekBar)findViewById(R.id.seek);
        play_button = (ImageButton)findViewById(R.id.watchman_player_play);
        pause_button = (ImageButton)findViewById(R.id.watchman_player_pause);
        stop_button = (ImageButton)findViewById(R.id.watchman_player_stop);

        webView.loadUrl("file:///android_asset/html/"+holySongLang+"/"+holySongUrl+".html");


        stop_button.setEnabled(false);
        pause_button.setEnabled(false);

        play_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(player == null ){
                    player = new MediaPlayer();

                    try {
                        AssetFileDescriptor descriptor = getAssets().openFd("midi/"+holySongUrl+".mid");
                        player.setDataSource(descriptor.getFileDescriptor(), descriptor.getStartOffset(), descriptor.getLength());
                        descriptor.close();

                    } catch (IllegalArgumentException e) {
                        Toast.makeText(getApplicationContext(), "You might not set the URI correctly!", Toast.LENGTH_LONG).show();
                    }  catch (IllegalStateException e) {
                        Toast.makeText(getApplicationContext(), "You might not set the URI correctly!", Toast.LENGTH_LONG).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    try {
                        player.prepare();
                    } catch (IllegalStateException e) {
                        Toast.makeText(getApplicationContext(), "You might not set the URI correctly!", Toast.LENGTH_LONG).show();
                    } catch (IOException e) {
                        Toast.makeText(getApplicationContext(), "You might not set the URI correctly!", Toast.LENGTH_LONG).show();
                    }

                }


                player.start();
                play_button.setEnabled(false);
                stop_button.setEnabled(true);
                pause_button.setEnabled(true);
                seek_bar.setMax(player.getDuration());
                new Thread(HolySongSingle.this).start();


            }
        });



        pause_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                if(player!=null && player.isPlaying()){
                    player.pause();
                    play_button.setEnabled(true);
                    stop_button.setEnabled(true);
                    pause_button.setEnabled(false);

                }

            }
        });
        stop_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (player != null && player.isPlaying()) {
                    player.stop();
                }
                else if(player != null && player.getDuration() > 0){
                    player.stop();
                }

                player.release();
                player = null;
                seek_bar.setProgress(0);
                play_button.setEnabled(true);
                stop_button.setEnabled(false);
                pause_button.setEnabled(false);

            }
        });


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                //NavUtils.navigateUpFromSameTask(this);
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override

    public void run() {
        int currentPosition = player.getCurrentPosition();
        int total = player.getDuration();

        while (player != null && currentPosition < total) {
            try {
                Thread.sleep(1000);
                currentPosition = player.getCurrentPosition();
            } catch (InterruptedException e) {
                return;
            } catch (Exception e) {
                return;
            }
            seek_bar.setProgress(currentPosition);
        }
        
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress,
                                  boolean fromUser) {

        player.pause();

        try {
            if (player.isPlaying() || player != null) {
                    player.pause();
                player.seekTo(player.getCurrentPosition()+ progress);
                    player.start();
            } else if (player == null) {
                Toast.makeText(getApplicationContext(), "Media is not running",
                        Toast.LENGTH_SHORT).show();
                seekBar.setProgress(0);
            }
        } catch (Exception e) {
            seekBar.setEnabled(false);
        }
    }
    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        // TODO Auto-generated method stub

    }

    private void releaseMediaPlayer() {
        if (player!= null) {
            if(player.isPlaying()) {
                player.stop();
            }
            player.release();
            player= null;
        }
    }

    @Override
    public void onBackPressed() {
        releaseMediaPlayer();
        super.onBackPressed();
    }

}
