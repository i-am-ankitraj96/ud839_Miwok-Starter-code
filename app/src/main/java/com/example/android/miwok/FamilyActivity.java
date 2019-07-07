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

public class FamilyActivity extends AppCompatActivity {

    private MediaPlayer mediaPlayer;

    private AudioManager.OnAudioFocusChangeListener mAudioFocusChangeListener=new AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int i) {
            if(i==AudioManager.AUDIOFOCUS_LOSS_TRANSIENT || i==AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK){
                mediaPlayer.pause();
                mediaPlayer.seekTo(0);
                //below line added is exclusive in this activity just to check possibility of various usages
                releaseMedia();
            }else if(i==AudioManager.AUDIOFOCUS_GAIN){
                mediaPlayer.start();
            }else {
                releaseMedia();
            }
        }
    };

    private AudioManager am;
    private void releaseMedia(){
        if(mediaPlayer!=null){
            mediaPlayer.release();
        }
        mediaPlayer=null;
        //below line is in accordance with the  change made to onAudioFocusChange overridin mentioned and done above
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

        final ArrayList<Words> words=new ArrayList<Words>(Arrays.asList(new Words("father","әpә",R.drawable.family_father,R.raw.family_father)));
        words.add(new Words("mother","әpә",R.drawable.family_mother,R.raw.family_mother));
        words.add(new Words("son","angsi",R.drawable.family_son,R.raw.family_son));
        words.add(new Words("daughter","tune",R.drawable.family_daughter,R.raw.family_daughter));
        words.add(new Words("older brother","taachi",R.drawable.family_older_brother,R.raw.family_older_brother));
        words.add(new Words("younger brother","chalitti",R.drawable.family_younger_brother,R.raw.family_younger_brother));
        words.add(new Words("older sister","teṭe",R.drawable.family_older_sister,R.raw.family_older_sister));
        words.add(new Words("younger sister","kolliti ",R.drawable.family_younger_sister,R.raw.family_younger_sister));
        words.add(new Words("grandmother","ama",R.drawable.family_grandmother,R.raw.family_grandmother));
        words.add(new Words("grandfather","paapa",R.drawable.family_grandfather,R.raw.family_grandfather));

        ListView listView= (ListView) findViewById(R.id.list);
        WordAdapter adapter=new WordAdapter(this,words,R.color.category_family);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                releaseMedia();

                int request=am.requestAudioFocus(mAudioFocusChangeListener,AudioManager.STREAM_MUSIC,AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
                if(request==AudioManager.AUDIOFOCUS_REQUEST_GRANTED){
                    mediaPlayer=MediaPlayer.create(FamilyActivity.this,words.get(i).getMediaId());
                    mediaPlayer.start();
                    mediaPlayer.setOnCompletionListener(media);
                }else {
                    Toast.makeText(FamilyActivity.this, "Try Releasing Background Media, if Playing !!", Toast.LENGTH_SHORT).show();
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
        super.onStop();
        releaseMedia();
    }
}
