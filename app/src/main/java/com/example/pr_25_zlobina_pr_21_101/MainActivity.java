package com.example.pr_25_zlobina_pr_21_101;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.TargetApi;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.IOException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private SoundPool mSoundPool;
    private AssetManager mAssetManager;
    private int mCatSound, mChickenSound, mCucamberSound, mFrogSound, mDuckSound, mHorseSound;
    private int mStreamID;

@Override
protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageButton catImageButton = findViewById(R.id.cat);
       // catImageButton.setOnClickListener(onClickListener);

        ImageButton chickenImageButton = findViewById(R.id.chicken);
   //     chickenImageButton.setOnClickListener(onClickListener);

        ImageButton horseImageButton = findViewById(R.id.horse);
      //  horseImageButton.setOnClickListener(onClickListener);

        ImageButton duckImageButton = findViewById(R.id.dusk);
      //  duckImageButton.setOnClickListener(onClickListener);

        ImageButton frogImageButton = findViewById(R.id.frog);
     //   frogImageButton.setOnClickListener(onClickListener);

        ImageButton cucamberImageButton = findViewById(R.id.cucamber);
     //   cucamberImageButton.setOnClickListener(onClickListener);

        }

      //  View.OnClickListener onClickListener = new View.OnClickListener() {
@Override
public void onClick(View v) {
        switch (v.getId()) {
        case R.id.cat:
                playSound(mCatSound);
        break;
        case R.id.chicken:
                playSound(mChickenSound);
        break;
        case R.id.cucamber:
                playSound(mCucamberSound);
        break;
        case R.id.dusk:
                playSound(mDuckSound);
        break;
        case R.id.horse:
                playSound(mHorseSound);
        break;
        case R.id.frog:
                playSound(mFrogSound);
        break;
                }
            }
       // };

@TargetApi(Build.VERSION_CODES.LOLLIPOP)                                                // создание обектоа, который воспроизводит звук
                                                                                        //  API-уровень Lollipop (версия Android 5.0)
private void createNewSoundPool() {
        AudioAttributes attributes = new AudioAttributes.Builder()
        .setUsage(AudioAttributes.USAGE_GAME)                                           //звук будет использоваться для игровых целей
        .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)                      //звука как звуковые эффекты
        .build();
        mSoundPool = new SoundPool.Builder()                                            // новый паттерн Строитель(позволяет расширить настраевымае поля)
        .setAudioAttributes(attributes)
        .build();                                                                        //создать экземпляр SoundPool
        }

@SuppressWarnings("deprecation")                                                        // игнорир предупреждения о устаревшем (deprecated) код или API.

private void createOldSoundPool() { //создания экземпляра класса SoundPool
        mSoundPool = new SoundPool(3, AudioManager.STREAM_MUSIC, 0);
        // 3 - звука одновременно
        //STREAM_MUSIC - поток звука, что производиться
        // 0 - резервное значение, какие то аудио фокусы
        }

private int playSound(int sound) {
        if (sound > 0) { // проверка на существования дорожки
        mStreamID = mSoundPool.play(sound, 1, 1, 1, 0, 1);
        // воспроизведение звука 1: громкость звука слева (от 0.0 до 1.0).
                //1: громкость звука справа (от 0.0 до 1.0)
                //1: приоритет звука (от 0 до 2)
                //0: повторение звука (0 - без повторения, -1 - бесконечное повторение).
                //1: скорость воспроизведения звука (от 0.5 до 2.0)
        }
        return mStreamID;
        }

private int loadSound(String fileName) {
        AssetFileDescriptor afd;                                                        // нужна для открытия файла
        try {
        afd = mAssetManager.openFd(fileName);                                           // попытка открытия файла по имени
        }
        catch (IOException e) {
        e.printStackTrace();
        Toast.makeText(getApplicationContext(), "Не могу загрузить файл " + fileName,
        Toast.LENGTH_SHORT).show();
        return -1;
        }
        return mSoundPool.load(afd, 1);                                           // загрузка файда звука и возращает его идентификатор
        }

@Override
protected void onResume() {
        super.onResume();                                                               // все методы стобы вернуть активность в нормальное состояние

        if (android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
        // Для устройств до Android 5
        createOldSoundPool();
        } else {
        // Для новых устройств
        createNewSoundPool();
        }

        mAssetManager = getAssets(); // это доступ к ресурсам у AssetManager

        // получим идентификаторы и загружаем файлы
        mCatSound = loadSound("cat.mp3");
        mChickenSound = loadSound("chicken.mp3");
        mCucamberSound = loadSound("cucumber.mp3");
        mFrogSound = loadSound("frog.mp3");
        mDuckSound = loadSound("dusk.mp3");
        mHorseSound= loadSound("horse.mp3");

                                                                                         /* происходит создание объектов SoundPool в зависимости от
                                                                                         версии Android, получение доступа к ресурсам через AssetManager
                                                                                          и загрузка звуковых файлов для дальнейшего использования в приложении.*/

        }

@Override
protected void onPause() {
        super.onPause();
        mSoundPool.release();// освобождение от ресурсов
        mSoundPool = null; //SoundPool не оспользуется
        }
}

/*  Я: - билдер это метод, которые позволяет обращаться к нестандартным вызовам
        методов для передачи данных?

  GPT: - Верно! Builder - это паттерн проектирования, который используется для
  создания объектов с большим количеством настраиваемых параметров.
  Он позволяет упростить создание объектов, позволяя указывать только необходимые
  параметры и предоставляя методы для установки других параметров в удобной форме.

  Паттерн - повторяемое решение для проекта
 */

/*

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private SoundPool mSoundPool;
    private AssetManager mAssetManager;
    private int mCatSound, mChickenSound, mCowSound, mDogSound, mDuckSound, mSheepSound;
    private int mStreamID;
    ImageButton cowImageButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cowImageButton = findViewById(R.id.image_chicken);
        cowImageButton.setOnClickListener(this);

        mChickenSound = loadSound("chicken.mp3");
    }


    private void createNewSoundPool() {
        AudioAttributes attributes = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_GAME)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build();
    }


    @SuppressWarnings("deprecation")


    private void createOldSoundPool(){
        mSoundPool = new SoundPool(3, AudioManager.STREAM_MUSIC,0);
    }


    private int playSound (int sound){
        if (sound > 0)
        {
            mStreamID = mSoundPool.play(sound,1,1,1,0,1);
        }
        return mStreamID;
    }

    private int loadSound (String fileName) {
        AssetFileDescriptor afd;
        try{
            afd = mAssetManager.openFd(fileName);
           }
        catch (IOException e){
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "Не могу загрузить файл" + fileName,
                    Toast.LENGTH_SHORT).show();
            return -1;
        }
        return mSoundPool.load(afd, 1);
    }


    @Override
    protected void onResume()
    {
        super.onResume();
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP)
        {
            createOldSoundPool();
        }
        else
        {
            createNewSoundPool();
        }
        mAssetManager = getAssets();
        mCatSound = loadSound("chicken.mp3");

    }

/*
    @Override
    protected void onPause(){
        super.onPause();
        mSoundPool.release();
        mSoundPool = null;
    }
}
        */

  /*   catImageButton.setOnTouchListener(new View.OnTouchListener() { // слушает касание пальцем на экран

public boolean onTouch(View v, MotionEvent event) { какой то запрет на слушанье
        int eventAction = event.getAction();
        if (eventAction == MotionEvent.ACTION_UP) {
        // Отпускаем палец
        if (mStreamID > 0)
        mSoundPool.stop(mStreamID);
        }
        if (eventAction == MotionEvent.ACTION_DOWN) {
        // Нажимаем на кнопку
        mStreamID = playSound(mCatSound);
        }
        if (event.getAction() == MotionEvent.ACTION_CANCEL) {
        mSoundPool.stop(mStreamID);
        }
        return true;
        }
        });*/