package com.example.chords;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaRecorder;
import android.os.Environment;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;


import com.example.chords.activities.MainActivity;
import com.example.chords.activities.SongActivity;
import com.example.chords.adapters.SongListAdapter;
import com.example.chords.api.ApiModule;
import com.example.chords.model.ListSongResponse;
import com.example.chords.model.SongRequest;
import com.example.chords.preference.UserPreference;
import com.example.chords.preference.interfaces.UserPreferenceName;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Observable;
import java.util.Timer;
import java.util.TimerTask;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;


public class RecordingService extends Service {

    private static final String LOG_TAG = "RecordingService";

    private SongListAdapter adapter;

    private String mFileName = null;
    private String mFilePath = null;

    private MediaRecorder mRecorder = null;

    private DBHelper mDatabase;

    private long mStartingTimeMillis = 0;
    private long mElapsedMillis = 0;
    private int mElapsedSeconds = 0;
    private OnTimerChangedListener onTimerChangedListener = null;
    private static final SimpleDateFormat mTimerFormat = new SimpleDateFormat("mm:ss", Locale.getDefault());

    private Timer mTimer = null;
    private TimerTask mIncrementTimerTask = null;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public interface OnTimerChangedListener {
        void onTimerChanged(int seconds);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mDatabase = new DBHelper(getApplicationContext());
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        startRecording();
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        if (mRecorder != null) {
            stopRecording();
            uploadFile();
        }

        super.onDestroy();
    }

    public void startRecording() {
        setFileNameAndPath();

        mRecorder = new MediaRecorder();
        mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
        mRecorder.setOutputFile(mFilePath);
        mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
        mRecorder.setAudioChannels(1);
        if (MySharedPreferences.getPrefHighQuality(this)) {
            mRecorder.setAudioSamplingRate(44100);
            mRecorder.setAudioEncodingBitRate(192000);
        }

        try {
            mRecorder.prepare();
            mRecorder.start();
            mStartingTimeMillis = System.currentTimeMillis();

            //startTimer();
            //startForeground(1, createNotification());

        } catch (IOException e) {
            Log.e(LOG_TAG, "prepare() failed");
        }
    }

    public void setFileNameAndPath() {
        int count = 0;
        File f;

        do {
            count++;

            mFileName = getString(R.string.default_file_name)
                    + "_" + (mDatabase.getCount() + count) + ".mp4";
            mFilePath = Environment.getExternalStorageDirectory().getAbsolutePath();
            mFilePath += "/Chords/" + mFileName;

            f = new File(mFilePath);
        } while (f.exists() && !f.isDirectory());
    }

    public void stopRecording() {
        mRecorder.stop();
        mElapsedMillis = (System.currentTimeMillis() - mStartingTimeMillis);
        mRecorder.release();
        Toast.makeText(this, getString(R.string.toast_recording_finish) + " " + mFilePath, Toast.LENGTH_LONG).show();

        //remove notification
        if (mIncrementTimerTask != null) {
            mIncrementTimerTask.cancel();
            mIncrementTimerTask = null;
        }

        mRecorder = null;

        try {
            mDatabase.addRecording(mFileName, mFilePath, mElapsedMillis);

        } catch (Exception e) {
            Log.e(LOG_TAG, "exception", e);
        }

    }

    private void uploadFile() {

        File file = new File(mFilePath);

        // Создаем RequestBody
        RequestBody requestFile =
                RequestBody.create(MediaType.parse("multipart/form-data"), file);

        // MultipartBody.Part используется, чтобы передать имя файла
        MultipartBody.Part body =
                MultipartBody.Part.createFormData("audio/mp4", mFileName, requestFile);

        // Добавляем описание
        String descriptionString = "description";
        RequestBody description =
                RequestBody.create(
                        MediaType.parse("multipart/form-data"), descriptionString);

        // Выполняем запрос
//        ApiModule.getSongApi()
//                .getSong(description, body)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Action1<ListSongResponse>(){
//                    @Override
//                    public void call(ListSongResponse songResponse) {
//                        //adapter.updateData(songResponse.getSong());
//                        //UserPreference  set songreponseList
//                        Log.v("Upload", "success");
//                    }
//                });
    }

    private void startTimer() {
        mTimer = new Timer();
        mIncrementTimerTask = new TimerTask() {
            @Override
            public void run() {
                mElapsedSeconds++;
                if (onTimerChangedListener != null)
                    onTimerChangedListener.onTimerChanged(mElapsedSeconds);
                NotificationManager mgr = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                mgr.notify(1, createNotification());
            }
        };
        mTimer.scheduleAtFixedRate(mIncrementTimerTask, 1000, 1000);
    }

    //TODO:
    private Notification createNotification() {
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(getApplicationContext())
                        .setSmallIcon(R.drawable.ic_mic_white_36dp)
                        .setContentTitle(getString(R.string.notification_recording))
                        .setContentText(mTimerFormat.format(mElapsedSeconds * 1000))
                        .setOngoing(true);

        mBuilder.setContentIntent(PendingIntent.getActivities(getApplicationContext(), 0,
                new Intent[]{new Intent(getApplicationContext(), MainActivity.class)}, 0));

        return mBuilder.build();
    }
}
