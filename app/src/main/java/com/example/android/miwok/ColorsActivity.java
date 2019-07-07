package com.example.android.miwok;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

public class ColorsActivity extends AppCompatActivity {

    private MediaPlayer mediaPlayer;
    private AudioManager am;

    private  AudioManager.OnAudioFocusChangeListener mAudioFocusChangeListener=new AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int i) {
            if(i==AudioManager.AUDIOFOCUS_LOSS_TRANSIENT || i==AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK){
                mediaPlayer.pause();
                mediaPlayer.seekTo(0);
                releaseMedia();
            }else if(i==AudioManager.AUDIOFOCUS_GAIN){
                mediaPlayer.start();
            }else if(i==AudioManager.AUDIOFOCUS_LOSS){
                releaseMedia();
            }
        }
    };

    private void releaseMedia(){
        if(mediaPlayer!=null){
            mediaPlayer.release();
        }

        mediaPlayer=null;

        if(am!=null)
            am.abandonAudioFocus(mAudioFocusChangeListener);
    }

    MediaPlayer.OnCompletionListener media=new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mediaPlayer) {
            releaseMedia();
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_words);

        am= (AudioManager) getSystemService(AUDIO_SERVICE);

        final ArrayList<Words> words=new ArrayList<Words>(Arrays.asList(new Words("red","weṭeṭṭi",R.drawable.color_red,R.raw.color_red)));
        words.add(new Words("green","chokokki",R.drawable.color_green,R.raw.color_green));
        words.add(new Words("brown","ṭakaakki",R.drawable.color_brown,R.raw.color_brown));
        words.add(new Words("gray","ṭopoppi",R.drawable.color_gray,R.raw.color_gray));
        words.add(new Words("black","kululli",R.drawable.color_black,R.raw.color_black));
        words.add(new Words("white","kelelli",R.drawable.color_white,R.raw.color_white));
        words.add(new Words("dusty yellow","ṭopiisә",R.drawable.color_dusty_yellow,R.raw.color_dusty_yellow));
        words.add(new Words("mustard yellow","chiwiiṭә",R.drawable.color_mustard_yellow,R.raw.color_mustard_yellow));

        ListView listView= (ListView) findViewById(R.id.list);
        WordAdapter adapter=new WordAdapter(this,words,R.color.category_colors);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                releaseMedia();
               int requestResult=am.requestAudioFocus(mAudioFocusChangeListener,AudioManager.STREAM_MUSIC,AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
               if(requestResult==AudioManager.AUDIOFOCUS_REQUEST_GRANTED){
                   mediaPlayer=MediaPlayer.create(ColorsActivity.this,words.get(i).getMediaId());
                   mediaPlayer.start();

                   mediaPlayer.setOnCompletionListener(media);
               }else {
                   Toast.makeText(ColorsActivity.this, "Try Releasing Background Media, if Playing !!", Toast.LENGTH_SHORT).show();
               }
            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStop() {
        super.onStop(); //use ctrl+O shortcut to get this overridden definition automatically
        releaseMedia();  //obviously this part of code is what user wants to do as activity ColorActivitty transitions to "stop" state .
    }
}
