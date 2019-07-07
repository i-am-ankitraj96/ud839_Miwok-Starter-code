package com.example.android.miwok;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.provider.MediaStore;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

public class PhrasesActivity extends AppCompatActivity {

    private MediaPlayer mediaPlayer;

    private AudioManager.OnAudioFocusChangeListener mAudioFocusChangeListener= new AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int i) {
            if(i==AudioManager.AUDIOFOCUS_LOSS_TRANSIENT || i==AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK) {
                mediaPlayer.pause();
                mediaPlayer.seekTo(0);
            } else if(i==AudioManager.AUDIOFOCUS_GAIN){
                mediaPlayer.start();
            }else if(i==AudioManager.AUDIOFOCUS_LOSS){
                releaseMedia();
            }
        }
    };

    private  AudioManager am;

    private  void releaseMedia(){
        if(mediaPlayer!=null){
            mediaPlayer.release();
        }
        mediaPlayer=null;

        am.abandonAudioFocus(mAudioFocusChangeListener);
    }

    MediaPlayer.OnCompletionListener media = new MediaPlayer.OnCompletionListener() {
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
//        setContentView(R.layout.activity_phrases);
//        String[] wordsTemp={"ONE","TWO","THREE","FOUR","FIVE","SIX","SEVEN","EIGHT","NINE","TEN","ONE","TWO","THREE","FOUR","FIVE","SIX","SEVEN","EIGHT","NINE","TEN"};
//        ArrayList<String> words=new ArrayList<>(Arrays.asList(wordsTemp));
//        ArrayAdapter<String> listAdapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,words);
//        GridView listView= (GridView) findViewById(R.id.list);
//        listView.setAdapter(listAdapter);

        final ArrayList<Words> words=new ArrayList<Words>(Arrays.asList(new Words("Where are you going? ","minto wuksus ",R.raw.phrase_where_are_you_going)));
        words.add(new Words("What is your name? ","tinnә oyaase'nә ",R.raw.phrase_what_is_your_name));
        words.add(new Words("My name is...","oyaaset...",R.raw.phrase_my_name_is));
        words.add(new Words("How are you feeling?","michәksәs?",R.raw.phrase_how_are_you_feeling));
        words.add(new Words("I’m feeling good.","kuchi achit",R.raw.phrase_im_feeling_good));
        words.add(new Words("Are you coming?","әәnәs'aa?",R.raw.phrase_are_you_coming));
        words.add(new Words("Yes, I’m coming.","hәә’ әәnәm",R.raw.phrase_yes_im_coming));
        words.add(new Words("I’m coming.","әәnәm",R.raw.phrase_im_coming));
        words.add(new Words("Let’s go.","yoowutis",R.raw.phrase_lets_go));
        words.add(new Words("Come here.","әnni'nem",R.raw.phrase_come_here));

//        final ArrayList<Words> words=new ArrayList<Words>(Arrays.asList(new Words("Where are you going? ","minto wuksus ",R.raw.phrase_where_are_you_going)));
//        words.add(new Words("What is your name? ","tinnә oyaase'nә ",R.raw.phrase_what_is_your_name));
//        words.add(new Words("My name is...","oyaaset...",R.raw.phrase_my_name_is));
//        words.add(new Words("How are you feeling?","michәksәs?",R.raw.phrase_how_are_you_feeling));
//        words.add(new Words("I’m feeling good.","kuchi achit",R.raw.phrase_im_feeling_good));
//        words.add(new Words("Are you coming?","әәnәs'aa?",R.raw.phrase_are_you_coming));
//        words.add(new Words("Yes, I’m coming.","hәә’ әәnәm",R.raw.phrase_yes_im_coming));
//        words.add(new Words("I’m coming.","әәnәm",R.raw.phrase_im_coming));
//        words.add(new Words("Let’s go.","yoowutis",R.raw.phrase_lets_go));
//        words.add(new Words("Come here.","әnni'nem",R.raw.phrase_come_here));

        ListView listView= (ListView) findViewById(R.id.list);
        WordAdapter adapter=new WordAdapter(this,words,R.color.category_phrases);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                releaseMedia();
                int request=am.requestAudioFocus(mAudioFocusChangeListener,AudioManager.STREAM_MUSIC,AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
               if(request==AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                   mediaPlayer = MediaPlayer.create(PhrasesActivity.this, words.get(i).getMediaId());
                   mediaPlayer.start();

                   mediaPlayer.setOnCompletionListener(media);
               }
               else{
                   Toast.makeText(PhrasesActivity.this, "Try Releasing Background Media, if Playing !!", Toast.LENGTH_SHORT).show();
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
