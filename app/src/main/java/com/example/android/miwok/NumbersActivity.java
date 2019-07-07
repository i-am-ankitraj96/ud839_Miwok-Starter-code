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

public class NumbersActivity extends AppCompatActivity {

    private MediaPlayer mMediaPlayer;

    private AudioManager.OnAudioFocusChangeListener mAudioFocusChangeListener=new AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int i) {
            if(i==AudioManager.AUDIOFOCUS_LOSS_TRANSIENT || i==AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK){
                mMediaPlayer.pause();
                mMediaPlayer.seekTo(0);
            }
            else if(i==AudioManager.AUDIOFOCUS_GAIN){
                mMediaPlayer.start(); //there is no resume method in MediaPlayer ..... start() picks up from where it ended or a fresh start at all
            }
            else if (i==AudioManager.AUDIOFOCUS_LOSS){
                releaseMedia();
            }
        }
    };

    private AudioManager am;

    private void releaseMedia(){
        if(mMediaPlayer!=null){
            mMediaPlayer.release();
        }
        mMediaPlayer=null;
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
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        am= (AudioManager) getSystemService(AUDIO_SERVICE);
//        String[] wordsTemp={"ONE","TWO","THREE","FOUR","FIVE","SIX","SEVEN","EIGHT","NINE","TEN"};
//        ArrayList <String> words=new ArrayList<String>(Arrays.asList("ONE","TWO","THREE","FOUR","FIVE","SIX","SEVEN","EIGHT","NINE","TEN","ELEVEN","TWELVE","THIRTEEN","FOURTEEN","FIFTEEN","SIXTEEN","SEVENTEEN","EIGHTEEN","NINETEEN","TWENTY"));
 //       LinearLayout linearLayout= (LinearLayout) findViewById(R.id.rootView);//       TextView textView;
//        for(int i=0;i<words.size();i++){
//            textView=new TextView(this);
//            textView.setText(words.get(i));
//            linearLayout.addView(textView);
//        }

        final ArrayList<Words> words=new ArrayList<Words>(Arrays.asList(new Words("one","lutti",R.drawable.number_one,R.raw.number_one)));
        words.add(new Words("two","otiiko ",R.drawable.number_two,R.raw.number_two));
        words.add(new Words("three","tolookosu ",R.drawable.number_three,R.raw.number_three));
        words.add(new Words("four","oyyisa ",R.drawable.number_four,R.raw.number_four));
        words.add(new Words("five","massokka ",R.drawable.number_five,R.raw.number_five));
        words.add(new Words("six","temmokka ",R.drawable.number_six,R.raw.number_six));
        words.add(new Words("seven","kenekaku ",R.drawable.number_seven,0x7f0b0017));
        words.add(new Words("eight","kawinta ",R.drawable.number_eight,R.raw.number_eight));
        words.add(new Words("nine","wo’e ",R.drawable.number_nine,R.raw.number_nine));
        words.add(new Words("ten","na’aacha ",R.drawable.number_ten,R.raw.number_ten));
        //ArrayAdapter<String> itemsAdapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,words);//can use wordsTemp also here...


        //ArrayAdapter<Words> itemsAdapter=new ArrayAdapter<Words>(this,R.layout.list_item,words);
        ListView listView= (ListView) findViewById(R.id.list);
        //listView.setAdapter(itemsAdapter);

        WordAdapter adapter=new WordAdapter(this,words,R.color.category_numbers);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
               // mMediaPlayer=MediaPlayer.create(NumbersActivity.this,R.raw.number_one);
                releaseMedia();
                int requestResult=am.requestAudioFocus(mAudioFocusChangeListener ,AudioManager.STREAM_MUSIC,AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);

                if(requestResult==AudioManager.AUDIOFOCUS_REQUEST_GRANTED){
                    mMediaPlayer=MediaPlayer.create(NumbersActivity.this,words.get(i).getMediaId());
                    mMediaPlayer.start();
                    mMediaPlayer.setOnCompletionListener(media);
                }

                else {
                    Toast.makeText(NumbersActivity.this, "Try Releasing Background Media, if Playing !!", Toast.LENGTH_SHORT).show();
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
