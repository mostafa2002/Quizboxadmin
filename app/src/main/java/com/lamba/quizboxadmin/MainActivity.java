package com.lamba.quizboxadmin;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.TextViewCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.PopupMenu;
import android.text.Editable;
import android.text.TextWatcher;
import android.transition.Scene;
import android.transition.TransitionInflater;
import android.transition.TransitionManager;
import android.util.DisplayMetrics;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.bumptech.glide.Glide;

import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private static final int PICK_IMAGE = 100;
    private static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 123;

    FrameLayout frameLayout;

    EditText quizName, question_context, answer1, answer2, answer3, answer4;

    ImageView image, addimage;

    TextView questionNumber, a, b, c, d, previous, next, finish, fadlkam;

    CardView card1, card2, card3, card4, card5;

    LottieAnimationView rightanswer1, rightanswer2, rightanswer3, rightanswer4, loading;

    FloatingActionButton bigTextdone;

    MediaPlayer mediaPlayer;

    int current_sending_question = 0;
    int current_question = 1;
    int rightanswer_temp = 0;

    boolean has_image_temp = false;
    String path_temp = "";

    String quiz_name, date, thesubject , quiz_id , S_answer1,S_answer2,S_answer3,S_answer4,S_question;

    ArrayList<String> array_question_context = new ArrayList<>();
    ArrayList<String> array_path = new ArrayList<>();

    ArrayList<String[]> answers = new ArrayList<>();

    ArrayList<Boolean> has_image = new ArrayList<>();

    ArrayList<Integer> right_answer = new ArrayList<>();


    boolean state = true;

    public boolean isConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm.getActiveNetworkInfo() != null
                && cm.getActiveNetworkInfo().isAvailable()
                && cm.getActiveNetworkInfo().isConnected()) {
            return true;
        } else
            return false;
    }

    public class UImage extends AsyncTask<String, Void, String> {

        String ata = "";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();


        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            current_sending_question++;

            if (current_sending_question > array_path.size() - 1) {
                finish();
                startActivity(new Intent(getApplicationContext(), start.class));
            } else {

                if (has_image.get(current_sending_question)) {
                    new UImage().execute(array_path.get(current_sending_question), array_question_context.get(current_sending_question), answers.get(current_sending_question)[0], answers.get(current_sending_question)[1], answers.get(current_sending_question)[2], answers.get(current_sending_question)[3], quiz_id, String.valueOf(right_answer.get(current_sending_question)));
                } else {
                    new SendQuestions().execute(array_question_context.get(current_sending_question), answers.get(current_sending_question)[0], answers.get(current_sending_question)[1], answers.get(current_sending_question)[2], answers.get(current_sending_question)[3], quiz_id, String.valueOf(right_answer.get(current_sending_question)));
                }


            }

            fadlkam.setText(String.valueOf(current_sending_question + " / " + array_path.size()));

        }

        @Override
        protected String doInBackground(String... strings) {


            HttpURLConnection conn;
            DataOutputStream dos;
            DataInputStream inStream = null;
            String existingFileName = strings[0];
            String lineEnd = "\r\n";
            String twoHyphens = "--";
            String boundary = "*****";
            int bytesRead, bytesAvailable, bufferSize;
            byte[] buffer;
            int maxBufferSize = 1 * 1024 * 1024;
            String urlString = ("https://httpwwwmystatuesrightnowesyes.000webhostapp.com/Al%20sohba/insert_questions.php"); // al3b henna
            try {
                //------------------ CLIENT REQUEST
                FileInputStream fileInputStream = new FileInputStream(new File(existingFileName)); // al3b henna
                // open a URL connection to the Servlet
                URL url = new URL(urlString);
                conn = (HttpURLConnection) url.openConnection();
                conn.setDoInput(true);
                conn.setDoOutput(true);
                conn.setUseCaches(false);
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Connection", "Keep-Alive");
                conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
                dos = new DataOutputStream(conn.getOutputStream());
                //el ba3t
                dos.writeBytes(twoHyphens + boundary + lineEnd);

                dos.writeBytes("Content-Disposition: form-data; name=\"image\";filename=\"" + strings[0].split("/")[strings[0].split("/").length - 1] + "\"" + lineEnd); // uploaded_file_name is the Name of the File to be uploaded
                dos.writeBytes(lineEnd);
                bytesAvailable = fileInputStream.available();
                bufferSize = Math.min(bytesAvailable, maxBufferSize);
                buffer = new byte[bufferSize];
                bytesRead = fileInputStream.read(buffer, 0, bufferSize);
                while (bytesRead > 0) {
                    dos.write(buffer, 0, bufferSize);
                    bytesAvailable = fileInputStream.available();
                    bufferSize = Math.min(bytesAvailable, maxBufferSize);
                    bytesRead = fileInputStream.read(buffer, 0, bufferSize);
                }

                dos.writeBytes(lineEnd);


                dos.writeBytes(twoHyphens + boundary + lineEnd);

                dos.writeBytes("Content-Disposition: form-data; name=\"" + "question" + "\"" + lineEnd);
                dos.writeBytes("Content-Type: text/plain" + lineEnd);
                dos.writeBytes(lineEnd);
                dos.writeBytes(strings[1]);
                dos.writeBytes(lineEnd);

                dos.writeBytes(twoHyphens + boundary + lineEnd);
                dos.writeBytes("Content-Disposition: form-data; name=\"" + "answer1" + "\"" + lineEnd);
                dos.writeBytes("Content-Type: text/plain" + lineEnd);
                dos.writeBytes(lineEnd);
                dos.writeBytes(strings[2]);
                dos.writeBytes(lineEnd);

                dos.writeBytes(twoHyphens + boundary + lineEnd);
                dos.writeBytes("Content-Disposition: form-data; name=\"" + "answer2" + "\"" + lineEnd);
                dos.writeBytes("Content-Type: text/plain" + lineEnd);
                dos.writeBytes(lineEnd);
                dos.writeBytes(strings[3]);
                dos.writeBytes(lineEnd);

                dos.writeBytes(twoHyphens + boundary + lineEnd);
                dos.writeBytes("Content-Disposition: form-data; name=\"" + "answer3" + "\"" + lineEnd);
                dos.writeBytes("Content-Type: text/plain" + lineEnd);
                dos.writeBytes(lineEnd);
                dos.writeBytes(strings[4]);
                dos.writeBytes(lineEnd);

                dos.writeBytes(twoHyphens + boundary + lineEnd);
                dos.writeBytes("Content-Disposition: form-data; name=\"" + "answer4" + "\"" + lineEnd);
                dos.writeBytes("Content-Type: text/plain" + lineEnd);
                dos.writeBytes(lineEnd);
                dos.writeBytes(strings[5]);
                dos.writeBytes(lineEnd);

                dos.writeBytes(twoHyphens + boundary + lineEnd);
                dos.writeBytes("Content-Disposition: form-data; name=\"" + "quiz" + "\"" + lineEnd);
                dos.writeBytes("Content-Type: text/plain" + lineEnd);
                dos.writeBytes(lineEnd);
                dos.writeBytes(strings[6]);
                dos.writeBytes(lineEnd);

                dos.writeBytes(twoHyphens + boundary + lineEnd);
                dos.writeBytes("Content-Disposition: form-data; name=\"" + "rightanswer" + "\"" + lineEnd);
                dos.writeBytes("Content-Type: text/plain" + lineEnd);
                dos.writeBytes(lineEnd);
                dos.writeBytes(strings[7]);
                dos.writeBytes(lineEnd);

                dos.writeBytes(twoHyphens + boundary + lineEnd);
                dos.writeBytes("Content-Disposition: form-data; name=\"" + "hasimage" + "\"" + lineEnd);
                dos.writeBytes("Content-Type: text/plain" + lineEnd);
                dos.writeBytes(lineEnd);
                dos.writeBytes("1");
                dos.writeBytes(lineEnd);


                dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

                fileInputStream.close();
                dos.flush();
                dos.close();

                InputStream inputStream = conn.getInputStream();
                InputStreamReader reader = new InputStreamReader(inputStream);

                int data = reader.read();

                while (data != -1) {

                    char current = (char) data;

                    ata += current;

                    data = reader.read();
                }

            } catch (FileNotFoundException e) {
                e.printStackTrace();
                ata = "file not found";
            } catch (ProtocolException e) {
                e.printStackTrace();
                ata = "url i guess";
            } catch (MalformedURLException e) {
                e.printStackTrace();
                ata = "url i guess";
            } catch (IOException e) {
                e.printStackTrace();
                ata = "io";
            }
            return ata;
        }
    }

    private class SendQuiz extends AsyncTask<String, Void, String> {

        String ata = "";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();


            loading.useHardwareAcceleration();

            frameLayout.setVisibility(View.VISIBLE);
            loading.playAnimation();

            fadlkam.setText(String.valueOf(0 + " / " + array_path.size()));

            if (!isConnected(getApplicationContext())) {
                Toast.makeText(MainActivity.this, "please check your Connection and try again", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            quiz_id = s;

            if (has_image.get(current_sending_question)) {
                new UImage().execute(array_path.get(current_sending_question), array_question_context.get(current_sending_question), answers.get(current_sending_question)[0], answers.get(current_sending_question)[1], answers.get(current_sending_question)[2], answers.get(current_sending_question)[3], quiz_id, String.valueOf(right_answer.get(current_sending_question)));
            } else {
                new SendQuestions().execute(array_question_context.get(current_sending_question), answers.get(current_sending_question)[0], answers.get(current_sending_question)[1], answers.get(current_sending_question)[2], answers.get(current_sending_question)[3], quiz_id, String.valueOf(right_answer.get(current_sending_question)));
            }
        }

        @Override
        protected String doInBackground(String... strings) {
            try {
                URL url = new URL("https://httpwwwmystatuesrightnowesyes.000webhostapp.com/Al%20sohba/insert_quiz.php");
                HttpURLConnection http = (HttpURLConnection) url.openConnection();
                http.setRequestMethod("POST");
                http.setDoInput(true);
                OutputStream out = http.getOutputStream();
                OutputStreamWriter writer = new OutputStreamWriter(out, "UTF-8");
                BufferedWriter bufferedWriter = new BufferedWriter(writer);

                String tempdata = URLEncoder.encode("subject", "UTF-8") + "=" + URLEncoder.encode(strings[0], "UTF-8") + "&" +
                        URLEncoder.encode("date", "UTF-8") + "=" + URLEncoder.encode(strings[1], "UTF-8") + "&" +
                        URLEncoder.encode("name", "UTF-8") + "=" + URLEncoder.encode(strings[2], "UTF-8");

                bufferedWriter.write(tempdata);
                bufferedWriter.flush();

                InputStream is = http.getInputStream();
                InputStreamReader reader = new InputStreamReader(is);

                int data2 = reader.read();

                while (data2 != -1) {
                    char current = (char) data2;

                    ata += current;

                    data2 = reader.read();
                }

            } catch (Exception e) {
                ata = "error";
                e.printStackTrace();
            }

            return ata;
        }
    }

    private class SendQuestions extends AsyncTask<String, Void, String> { // dh send question oxm bllah

        String ata = "";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            if (!isConnected(getApplicationContext())) {
                Toast.makeText(MainActivity.this, "please check your Connection and try again", Toast.LENGTH_SHORT).show();
            }

        }


        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            current_sending_question++;

            if (current_sending_question > array_path.size() - 1) {
                finish();
                startActivity(new Intent(getApplicationContext(), start.class));
            } else {

                if (has_image.get(current_sending_question)) {
                    new UImage().execute(array_path.get(current_sending_question), array_question_context.get(current_sending_question), answers.get(current_sending_question)[0], answers.get(current_sending_question)[1], answers.get(current_sending_question)[2], answers.get(current_sending_question)[3], quiz_id, String.valueOf(right_answer.get(current_sending_question)));
                } else {
                    new SendQuestions().execute(array_question_context.get(current_sending_question), answers.get(current_sending_question)[0], answers.get(current_sending_question)[1], answers.get(current_sending_question)[2], answers.get(current_sending_question)[3], quiz_id, String.valueOf(right_answer.get(current_sending_question)));
                }


            }

            fadlkam.setText(String.valueOf(current_sending_question + " / " + array_path.size()));


        }

        @Override
        protected String doInBackground(String... strings) {
            try {
                URL url = new URL("https://httpwwwmystatuesrightnowesyes.000webhostapp.com/Al%20sohba/insert_questions.php");
                HttpURLConnection http = (HttpURLConnection) url.openConnection();
                http.setRequestMethod("POST");
                http.setDoInput(true);
                OutputStream out = http.getOutputStream();
                OutputStreamWriter writer = new OutputStreamWriter(out, "UTF-8");
                BufferedWriter bufferedWriter = new BufferedWriter(writer);

                String tempdata = URLEncoder.encode("question", "UTF-8") + "=" + URLEncoder.encode(strings[0], "UTF-8") + "&" +
                        URLEncoder.encode("answer1", "UTF-8") + "=" + URLEncoder.encode(strings[1], "UTF-8") + "&" +
                        URLEncoder.encode("answer2", "UTF-8") + "=" + URLEncoder.encode(strings[2], "UTF-8") + "&" +
                        URLEncoder.encode("answer3", "UTF-8") + "=" + URLEncoder.encode(strings[3], "UTF-8") + "&" +
                        URLEncoder.encode("answer4", "UTF-8") + "=" + URLEncoder.encode(strings[4], "UTF-8") + "&" +
                        URLEncoder.encode("quiz", "UTF-8") + "=" + URLEncoder.encode(strings[5], "UTF-8") + "&" +
                        URLEncoder.encode("rightanswer", "UTF-8") + "=" + URLEncoder.encode(strings[6], "UTF-8") + "&" +
                        URLEncoder.encode("hasimage", "UTF-8") + "=" + URLEncoder.encode("0", "UTF-8");

                bufferedWriter.write(tempdata);
                bufferedWriter.flush();

                InputStream is = http.getInputStream();
                InputStreamReader reader = new InputStreamReader(is);

                int data2 = reader.read();

                while (data2 != -1) {
                    char current = (char) data2;

                    ata += current;

                    data2 = reader.read();
                }

            } catch (Exception e) {
                ata = "error";
                e.printStackTrace();
            }

            return ata;
        }
    }

    private void defineStuff(){
        frameLayout = findViewById(R.id.showorhide);

        quizName = findViewById(R.id.quizname);
        question_context = findViewById(R.id.question_context);
        answer1 = findViewById(R.id.answer1);
        answer2 = findViewById(R.id.answer2);
        answer3 = findViewById(R.id.answer3);
        answer4 = findViewById(R.id.answer4);

        questionNumber = findViewById(R.id.question_number);
        a = findViewById(R.id.a);
        b = findViewById(R.id.b);
        c = findViewById(R.id.c);
        d = findViewById(R.id.d);
        finish = findViewById(R.id.finish);
        fadlkam = findViewById(R.id.fadlkam);

        image = findViewById(R.id.image);
        addimage = findViewById(R.id.addphoto);

        previous = findViewById(R.id.previous);
        next = findViewById(R.id.next);

        card1 = findViewById(R.id.cardView3);
        card2 = findViewById(R.id.cardView4);
        card3 = findViewById(R.id.cardView5);
        card4 = findViewById(R.id.cardView6);
        card5 = findViewById(R.id.cardView7);

        rightanswer1 = findViewById(R.id.rightanswer1);
        rightanswer2 = findViewById(R.id.rightanswer2);
        rightanswer3 = findViewById(R.id.rightanswer3);
        rightanswer4 = findViewById(R.id.rightanswer4);
        loading = findViewById(R.id.loading);

        quiz_name = getIntent().getStringExtra("quizname");
        thesubject = getIntent().getStringExtra("subject");


        quizName.setText(quiz_name);

        mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.error);


        TextViewCompat.setAutoSizeTextTypeUniformWithConfiguration(questionNumber, 4, 112, 1, TextViewCompat.AUTO_SIZE_TEXT_TYPE_UNIFORM);
        TextViewCompat.setAutoSizeTextTypeUniformWithConfiguration(a, 4, 112, 1, TextViewCompat.AUTO_SIZE_TEXT_TYPE_UNIFORM);
        TextViewCompat.setAutoSizeTextTypeUniformWithConfiguration(b, 4, 112, 1, TextViewCompat.AUTO_SIZE_TEXT_TYPE_UNIFORM);
        TextViewCompat.setAutoSizeTextTypeUniformWithConfiguration(c, 4, 112, 1, TextViewCompat.AUTO_SIZE_TEXT_TYPE_UNIFORM);
        TextViewCompat.setAutoSizeTextTypeUniformWithConfiguration(d, 4, 112, 1, TextViewCompat.AUTO_SIZE_TEXT_TYPE_UNIFORM);


        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {

                if (quizName.getText().toString().isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please type the quiz name.", Toast.LENGTH_SHORT).show();

                    if (!mediaPlayer.isPlaying()) {
                        mediaPlayer.start();
                    }

                } else if (question_context.getText().toString().isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please type the question context.", Toast.LENGTH_SHORT).show();

                    if (!mediaPlayer.isPlaying()) {
                        mediaPlayer.start();
                    }
                } else if (answer1.getText().toString().isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please type the answer 1.", Toast.LENGTH_SHORT).show();

                    if (!mediaPlayer.isPlaying()) {
                        mediaPlayer.start();
                    }
                } else if (answer2.getText().toString().isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please type the answer 2.", Toast.LENGTH_SHORT).show();

                    if (!mediaPlayer.isPlaying()) {
                        mediaPlayer.start();
                    }
                } else if (answer3.getText().toString().isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please type the answer 3.", Toast.LENGTH_SHORT).show();

                    if (!mediaPlayer.isPlaying()) {
                        mediaPlayer.start();
                    }
                } else if (answer4.getText().toString().isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please type the answer 4.", Toast.LENGTH_SHORT).show();

                    if (!mediaPlayer.isPlaying()) {
                        mediaPlayer.start();
                    }
                } else if (rightanswer_temp == 0) {
                    Toast.makeText(MainActivity.this, "please choose The Right answer", Toast.LENGTH_SHORT).show();

                    if (!mediaPlayer.isPlaying()) {
                        mediaPlayer.start();
                    }

                } else {


                    swipe(true);

                }
            }
        });


        addimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkPermissionREAD_EXTERNAL_STORAGE(MainActivity.this)) {
                    Intent gallary = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                    startActivityForResult(gallary, PICK_IMAGE);
                }

            }
        });

        image.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                PopupMenu popup = new PopupMenu(MainActivity.this, image);

                popup.getMenuInflater().inflate(R.menu.selectimageway, popup.getMenu());

                setForceShowIcon(popup);

                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getTitle().toString()) {
                            case "Delete":
                                image.setImageResource(0);
                                path_temp = "";
                                has_image_temp = false;
                                break;
                            case "Change Image":
                                Intent gallary = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                                startActivityForResult(gallary, PICK_IMAGE);
                                break;
                        }
                        return true;
                    }
                });

                popup.show();
                return true;
            }
        });

        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (current_question <= 1) {
                    if (!mediaPlayer.isPlaying()) {
                        mediaPlayer.start();
                    }

                } else {
                    current_question--;
                    questionNumber.setText(String.valueOf(current_question));

                    swipe(false);
                }


            }
        });

        findViewById(R.id.finish).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (quizName.getText().toString().isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please type the quiz name.", Toast.LENGTH_SHORT).show();
                } else if (question_context.getText().toString().isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please type the question context.", Toast.LENGTH_SHORT).show();
                } else if (answer1.getText().toString().isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please type the answer 1.", Toast.LENGTH_SHORT).show();
                } else if (answer2.getText().toString().isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please type the answer 2.", Toast.LENGTH_SHORT).show();
                } else if (answer3.getText().toString().isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please type the answer 3.", Toast.LENGTH_SHORT).show();
                } else if (answer4.getText().toString().isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please type the answer 4.", Toast.LENGTH_SHORT).show();
                } else if (rightanswer_temp == 0) {
                    Toast.makeText(MainActivity.this, "please choose The Right answer", Toast.LENGTH_SHORT).show();
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);


                    builder.setTitle("Confirm");
                    builder.setMessage("Are you sure?");

                    builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int which) {

                            array_question_context.add(current_question - 1, question_context.getText().toString());
                            answers.add(current_question - 1, new String[]{answer1.getText().toString(), answer2.getText().toString(), answer3.getText().toString(), answer4.getText().toString()});
                            right_answer.add(current_question - 1, rightanswer_temp);
                            has_image.add(current_question - 1, has_image_temp);
                            array_path.add(current_question - 1, path_temp);


                            String timeStamp = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

                            new SendQuiz().execute(thesubject, timeStamp, quiz_name);

                            dialog.dismiss();
                        }
                    });

                    builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            // Do nothing
                            dialog.dismiss();
                        }
                    });

                    AlertDialog alert = builder.create();
                    alert.show();
                }


            }
        });


    }

    private void bigTextStarted(final int whichone , String answerdata){
        switch (whichone){
            case 1:
                answer1 = findViewById(R.id.answer);
                answer1.setText(answerdata);
                answer1.setSelection(answer1.length());
                break;
            case 2:
                answer2 = findViewById(R.id.answer);
                answer2.setText(answerdata);
                answer2.setSelection(answer2.length());
                break;
            case 3:
                answer3 = findViewById(R.id.answer);
                answer3.setText(answerdata);
                answer3.setSelection(answer3.length());
                break;
            case 4:
                answer4 = findViewById(R.id.answer);
                answer4.setText(answerdata);
                answer4.setSelection(answer4.length());
                break;
        }

        bigTextdone = findViewById(R.id.floatingActionButton);

        finish = findViewById(R.id.finish);

        quizName = findViewById(R.id.quizname);

        quizName.setText(quiz_name);



        bigTextdone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!state){
                    S_answer1 = answer1.getText().toString();
                    S_answer2 = answer2.getText().toString();
                    S_answer3 = answer3.getText().toString();
                    S_answer4 = answer4.getText().toString();

                    switch (whichone){
                        case 2:
                            findViewById(R.id.cardView5).setId(R.id.cardView4);
                            break;
                        case 3:
                            findViewById(R.id.cardView5).setId(R.id.cardView6);
                            break;
                        case 4:
                            findViewById(R.id.cardView5).setId(R.id.cardView7);
                            break;
                    }


                            ViewGroup root = (ViewGroup) findViewById(android.R.id.content);
                    TransitionManager.go(Scene.getSceneForLayout(root, R.layout.activity_main, MainActivity.this));
                    TransitionInflater.from(MainActivity.this).inflateTransition(R.transition.answer3);

                    state = true;
                    getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

                    defineStuff();


                    quizName.setText(quiz_name);
                    answer1.setText(S_answer1);
                    answer2.setText(S_answer2);
                    answer3.setText(S_answer3);
                    answer4.setText(S_answer4);
                    if (has_image_temp){
                        Glide.with(getApplicationContext()).load(path_temp).into(image);
                    }
                    question_context.setText(S_question);
                    switch (rightanswer_temp) {
                        case 1:
                            rightanswer1.setSpeed(3);
                            rightanswer1.setMinAndMaxProgress(0.25f, 0.9f);
                            rightanswer1.playAnimation();
                            break;
                        case 2:
                            rightanswer2.setSpeed(3);
                            rightanswer2.setMinAndMaxProgress(0.25f, 0.9f);
                            rightanswer2.playAnimation();
                            break;
                        case 3:
                            rightanswer3.setSpeed(3);
                            rightanswer3.setMinAndMaxProgress(0.25f, 0.9f);
                            rightanswer3.playAnimation();
                            break;
                        case 4:
                            rightanswer4.setSpeed(3);
                            rightanswer4.setMinAndMaxProgress(0.25f, 0.9f);
                            rightanswer4.playAnimation();
                            break;
                    }




                    answer1.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                        }

                        @Override
                        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                        }

                        @Override
                        public void afterTextChanged(Editable editable) {
                            if (editable.length() > 30){

                                if (state){
                                    ViewGroup root = (ViewGroup) findViewById(android.R.id.content);
                                    TransitionManager.go(Scene.getSceneForLayout(root, R.layout.activity_main_answer, MainActivity.this));
                                    TransitionInflater.from(MainActivity.this).inflateTransition(R.transition.answer1);
                                    state = false;
                                    getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

                                    bigTextStarted(1,editable.toString());

                                }

                            }
                        }
                    });

                    answer2.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                        }

                        @Override
                        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                        }

                        @Override
                        public void afterTextChanged(Editable editable) {
                            if (editable.length() > 30){

                                if (state){
                                    ViewGroup root = (ViewGroup) findViewById(android.R.id.content);
                                    TransitionManager.go(Scene.getSceneForLayout(root, R.layout.activity_main_answer, MainActivity.this));
                                    TransitionInflater.from(MainActivity.this).inflateTransition(R.transition.answer2);
                                    state = false;
                                    getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

                                    bigTextStarted(2,editable.toString());

                                }

                            }
                        }
                    });

                    answer3.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                        }

                        @Override
                        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                        }

                        @Override
                        public void afterTextChanged(Editable editable) {
                            if (editable.length() > 30){

                                if (state){
                                    ViewGroup root = (ViewGroup) findViewById(android.R.id.content);
                                    TransitionManager.go(Scene.getSceneForLayout(root, R.layout.activity_main_answer, MainActivity.this));
                                    TransitionInflater.from(MainActivity.this).inflateTransition(R.transition.answer3);
                                    state = false;
                                    getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

                                    bigTextStarted(3,editable.toString());

                                }

                            }
                        }
                    });

                    answer4.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                        }

                        @Override
                        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                        }

                        @Override
                        public void afterTextChanged(Editable editable) {
                            if (editable.length() > 30){

                                if (state){
                                    ViewGroup root = (ViewGroup) findViewById(android.R.id.content);
                                    TransitionManager.go(Scene.getSceneForLayout(root, R.layout.activity_main_answer, MainActivity.this));
                                    TransitionInflater.from(MainActivity.this).inflateTransition(R.transition.answer4);
                                    state = false;
                                    getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

                                    bigTextStarted(4,editable.toString());

                                }

                            }
                        }
                    });


                    answer1.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                        @Override
                        public void onFocusChange(View view, boolean b) {
                            if (b){
                            if (answer1.getText().toString().length() > 30) {

                                if (state) {

                                    ViewGroup root = (ViewGroup) findViewById(android.R.id.content);
                                    TransitionManager.go(Scene.getSceneForLayout(root, R.layout.activity_main_answer, MainActivity.this));
                                    TransitionInflater.from(MainActivity.this).inflateTransition(R.transition.answer1);
                                    state = false;
                                    getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

                                    bigTextStarted(1, answer1.getText().toString());

                                }
                            }

                            }
                        }
                    });

                    answer2.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                        @Override
                        public void onFocusChange(View view, boolean b) {
                            if (b){
                            if (answer2.getText().toString().length() > 30) {

                                if (state) {

                                    ViewGroup root = (ViewGroup) findViewById(android.R.id.content);
                                    TransitionManager.go(Scene.getSceneForLayout(root, R.layout.activity_main_answer, MainActivity.this));
                                    TransitionInflater.from(MainActivity.this).inflateTransition(R.transition.answer2);
                                    state = false;
                                    getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

                                    bigTextStarted(2, answer2.getText().toString());

                                }
                            }
                            }
                        }
                    });

                    answer3.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                        @Override
                        public void onFocusChange(View view, boolean b) {
                            if (b){
                            if (answer3.getText().toString().length() > 30) {

                                if (state) {

                                    ViewGroup root = (ViewGroup) findViewById(android.R.id.content);
                                    TransitionManager.go(Scene.getSceneForLayout(root, R.layout.activity_main_answer, MainActivity.this));
                                    TransitionInflater.from(MainActivity.this).inflateTransition(R.transition.answer3);
                                    state = false;
                                    getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

                                    bigTextStarted(3, answer3.getText().toString());

                                }
                            }

                            }
                        }
                    });



                    answer4.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                        @Override
                        public void onFocusChange(View view, boolean b) {
                            if (b){
                                if (answer4.getText().toString().length() > 30){

                                    if (state){

                                        ViewGroup root = (ViewGroup) findViewById(android.R.id.content);
                                        TransitionManager.go(Scene.getSceneForLayout(root, R.layout.activity_main_answer, MainActivity.this));
                                        TransitionInflater.from(MainActivity.this).inflateTransition(R.transition.answer4);
                                        state = false;
                                        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

                                        bigTextStarted(4,answer4.getText().toString());

                                    }

                                }
                            }
                        }
                    });


                }
            }
        });



    }

    private boolean checkPermissionREAD_EXTERNAL_STORAGE(
            final Context context) {
        int currentAPIVersion = Build.VERSION.SDK_INT;
        if (currentAPIVersion >= android.os.Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(context,
                    Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(
                        (Activity) context,
                        Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    showDialog("External storage", context,
                            Manifest.permission.READ_EXTERNAL_STORAGE);

                } else {
                    ActivityCompat
                            .requestPermissions(
                                    (Activity) context,
                                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                                    MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
                }
                return false;
            } else {
                return true;
            }

        } else {
            return true;
        }
    }

    private void showDialog(final String msg, final Context context,
                           final String permission) {
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);
        alertBuilder.setCancelable(true);
        alertBuilder.setTitle("Permission necessary");
        alertBuilder.setMessage(msg + " permission is necessary");
        alertBuilder.setPositiveButton(android.R.string.yes,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        ActivityCompat.requestPermissions((Activity) context,
                                new String[]{permission},
                                MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
                    }
                });
        AlertDialog alert = alertBuilder.create();
        alert.show();
    }

    private static void setForceShowIcon(PopupMenu popupMenu) {
        try {
            Field[] fields = popupMenu.getClass().getDeclaredFields();
            for (Field field : fields) {
                if ("mPopup".equals(field.getName())) {
                    field.setAccessible(true);
                    Object menuPopupHelper = field.get(popupMenu);
                    Class<?> classPopupHelper = Class.forName(menuPopupHelper
                            .getClass().getName());
                    Method setForceIcons = classPopupHelper.getMethod(
                            "setForceShowIcon", boolean.class);
                    setForceIcons.invoke(menuPopupHelper, true);
                    break;
                }
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    private String getRealPathFromURI(Uri contentURI) {
        String result;
        Cursor cursor = MainActivity.this.getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) { // Source is Dropbox or other similar local file path
            result = contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            result = cursor.getString(idx);
            cursor.close();
        }
        return result;
    }

    private void swipe(boolean isnext) {
        next.setClickable(false);
        previous.setClickable(false);
        if (isnext) {
            DisplayMetrics matrix = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(matrix);

            final int width = matrix.widthPixels;


            card1.animate().translationX(-width).setInterpolator(new AccelerateInterpolator()).start();
            card2.animate().translationX(-width).setInterpolator(new AccelerateInterpolator()).start();
            card3.animate().translationX(-width).setInterpolator(new AccelerateInterpolator()).start();
            card4.animate().translationX(-width).setInterpolator(new AccelerateInterpolator()).start();
            card5.animate().translationX(-width).setInterpolator(new AccelerateInterpolator()).withEndAction(new Runnable() {
                @Override
                public void run() {
                    card1.setTranslationX(width);
                    card2.setTranslationX(width);
                    card3.setTranslationX(width);
                    card4.setTranslationX(width);
                    card5.setTranslationX(width);

                    //  Al gded ________________________________-----------
                    if (current_question < array_path.size()) {
                        question_context.setText(array_question_context.get(current_question));

                        if (has_image.get(current_question)) {
                            Glide.with(getApplicationContext()).load(array_path.get(current_question )).into(image);
                        }
                        answer1.setText(answers.get(current_question)[0]);
                        answer2.setText(answers.get(current_question)[1]);
                        answer3.setText(answers.get(current_question)[2]);
                        answer4.setText(answers.get(current_question)[3]);

                        rightanswer1.setProgress(0);
                        rightanswer2.setProgress(0);
                        rightanswer3.setProgress(0);
                        rightanswer4.setProgress(0);

                        switch (right_answer.get(current_question)) {
                            case 1:
                                rightanswer_temp = 1;
                                rightanswer1.setSpeed(3);
                                rightanswer1.setMinAndMaxProgress(0.25f, 0.9f);
                                rightanswer1.playAnimation();
                                break;
                            case 2:
                                rightanswer_temp = 2;
                                rightanswer2.setSpeed(3);
                                rightanswer2.setMinAndMaxProgress(0.25f, 0.9f);
                                rightanswer2.playAnimation();
                                break;
                            case 3:
                                rightanswer_temp = 3;
                                rightanswer3.setSpeed(3);
                                rightanswer3.setMinAndMaxProgress(0.25f, 0.9f);
                                rightanswer3.playAnimation();
                                break;
                            case 4:
                                rightanswer_temp = 4;
                                rightanswer4.setSpeed(3);
                                rightanswer4.setMinAndMaxProgress(0.25f, 0.9f);
                                rightanswer4.playAnimation();
                                break;
                        }
                    } else {
                        if (array_path.size() == 0) {
                            array_question_context.add(current_question - 1, question_context.getText().toString());
                            answers.add(current_question - 1, new String[]{answer1.getText().toString(), answer2.getText().toString(), answer3.getText().toString(), answer4.getText().toString()});
                            right_answer.add(current_question - 1, rightanswer_temp);
                            has_image.add(current_question - 1, has_image_temp);
                            array_path.add(current_question - 1, path_temp);

                            rightanswer1.setProgress(0);
                            rightanswer2.setProgress(0);
                            rightanswer3.setProgress(0);
                            rightanswer4.setProgress(0);

                            question_context.setText("");
                            answer1.setText("");
                            answer2.setText("");
                            answer3.setText("");
                            answer4.setText("");
                            image.setImageResource(0);
                            has_image_temp = false;
                            rightanswer_temp = 0;
                            path_temp = "";
                        } else if (array_question_context.get(array_path.size() - 1).equals(question_context.getText().toString())) {

                            rightanswer1.setProgress(0);
                            rightanswer2.setProgress(0);
                            rightanswer3.setProgress(0);
                            rightanswer4.setProgress(0);

                            question_context.setText("");
                            answer1.setText("");
                            answer2.setText("");
                            answer3.setText("");
                            answer4.setText("");
                            image.setImageResource(0);
                            has_image_temp = false;
                            rightanswer_temp = 0;
                            path_temp = "";
                        } else {
                            array_question_context.add(current_question - 1, question_context.getText().toString());
                            answers.add(current_question - 1, new String[]{answer1.getText().toString(), answer2.getText().toString(), answer3.getText().toString(), answer4.getText().toString()});
                            right_answer.add(current_question - 1, rightanswer_temp);
                            has_image.add(current_question - 1, has_image_temp);
                            array_path.add(current_question - 1, path_temp);

                            rightanswer1.setProgress(0);
                            rightanswer2.setProgress(0);
                            rightanswer3.setProgress(0);
                            rightanswer4.setProgress(0);

                            question_context.setText("");
                            answer1.setText("");
                            answer2.setText("");
                            answer3.setText("");
                            answer4.setText("");
                            image.setImageResource(0);
                            has_image_temp = false;
                            rightanswer_temp = 0;
                            path_temp = "";
                        }
                    }
//_____________________________________________________________

                    View view = MainActivity.this.getCurrentFocus();

                    if (view != null) {
                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                    }

                    card1.animate().translationX(0).setInterpolator(new AccelerateInterpolator()).start();
                    card2.animate().translationX(0).setInterpolator(new AccelerateInterpolator()).start();
                    card3.animate().translationX(0).setInterpolator(new AccelerateInterpolator()).start();
                    card4.animate().translationX(0).setInterpolator(new AccelerateInterpolator()).start();
                    card5.animate().translationX(0).setInterpolator(new AccelerateInterpolator()).withEndAction(new Runnable() {
                        @Override
                        public void run() {
                            next.setClickable(true);
                            previous.setClickable(true);
                        }
                    }).start();

                    current_question++;

                    questionNumber.setText(String.valueOf(current_question));
                }
            }).start();
        } else {
            DisplayMetrics matrix = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(matrix);

            final int width = matrix.widthPixels;


            card1.animate().translationX(width).setInterpolator(new AccelerateInterpolator()).start();
            card2.animate().translationX(width).setInterpolator(new AccelerateInterpolator()).start();
            card3.animate().translationX(width).setInterpolator(new AccelerateInterpolator()).start();
            card4.animate().translationX(width).setInterpolator(new AccelerateInterpolator()).start();
            card5.animate().translationX(width).setInterpolator(new AccelerateInterpolator()).withEndAction(new Runnable() {
                @Override
                public void run() {
                    card1.setTranslationX(-width);
                    card2.setTranslationX(-width);
                    card3.setTranslationX(-width);
                    card4.setTranslationX(-width);
                    card5.setTranslationX(-width);


                    switch (rightanswer_temp) {
                        case 1:
                            rightanswer1.setProgress(0);
                            break;
                        case 2:
                            rightanswer2.setProgress(0);
                            break;
                        case 3:
                            rightanswer3.setProgress(0);
                            break;
                        case 4:
                            rightanswer4.setProgress(0);
                            break;
                    }

                    question_context.setText("");
                    answer1.setText("");
                    answer2.setText("");
                    answer3.setText("");
                    answer4.setText("");
                    image.setImageResource(0);
                    has_image_temp = false;
                    rightanswer_temp = 0;
                    path_temp = "";

                    if (current_question <= array_path.size()) {
                        question_context.setText(array_question_context.get(current_question - 1));

                        if (has_image.get(current_question - 1)) {
                            Glide.with(getApplicationContext()).load(array_path.get(current_question - 1)).into(image);
                        }

                        answer1.setText(answers.get(current_question - 1)[0]);
                        answer2.setText(answers.get(current_question - 1)[1]);
                        answer3.setText(answers.get(current_question - 1)[2]);
                        answer4.setText(answers.get(current_question - 1)[3]);

                        switch (right_answer.get(current_question - 1)) {
                            case 1:
                                rightanswer_temp = 1;
                                rightanswer1.setSpeed(3);
                                rightanswer1.setMinAndMaxProgress(0.25f, 0.9f);
                                rightanswer1.playAnimation();
                                break;
                            case 2:
                                rightanswer_temp = 2;
                                rightanswer2.setSpeed(3);
                                rightanswer2.setMinAndMaxProgress(0.25f, 0.9f);
                                rightanswer2.playAnimation();
                                break;
                            case 3:
                                rightanswer_temp = 3;
                                rightanswer3.setSpeed(3);
                                rightanswer3.setMinAndMaxProgress(0.25f, 0.9f);
                                rightanswer3.playAnimation();
                                break;
                            case 4:
                                rightanswer_temp = 4;
                                rightanswer4.setSpeed(3);
                                rightanswer4.setMinAndMaxProgress(0.25f, 0.9f);
                                rightanswer4.playAnimation();
                                break;
                        }
                    }


                    View view = MainActivity.this.getCurrentFocus();

                    if (view != null) {
                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                    }


                    card1.animate().translationX(0).setInterpolator(new AccelerateInterpolator()).start();
                    card2.animate().translationX(0).setInterpolator(new AccelerateInterpolator()).start();
                    card3.animate().translationX(0).setInterpolator(new AccelerateInterpolator()).start();
                    card4.animate().translationX(0).setInterpolator(new AccelerateInterpolator()).start();
                    card5.animate().translationX(0).setInterpolator(new AccelerateInterpolator()).withEndAction(new Runnable() {
                        @Override
                        public void run() {
                            next.setClickable(true);
                            previous.setClickable(true);
                        }
                    }).start();
                }
            }).start();
        }


    }

    public void checked(View view) {

        switch (rightanswer_temp) {
            case 0:

                break;
            case 1:
                rightanswer1.setSpeed(-3);
                rightanswer1.resumeAnimation();
                break;
            case 2:
                rightanswer2.setSpeed(-3);
                rightanswer2.resumeAnimation();
                break;
            case 3:
                rightanswer3.setSpeed(-3);
                rightanswer3.resumeAnimation();
                break;
            case 4:
                rightanswer4.setSpeed(-3);
                rightanswer4.resumeAnimation();
                break;
        }

        switch (view.getTag().toString()) {
            case "1":

                rightanswer1.setSpeed(3);
                rightanswer1.setMinAndMaxProgress(0.25f, 0.9f);
                rightanswer1.playAnimation();

                rightanswer_temp = 1;
                break;
            case "2":
                rightanswer2.setSpeed(3);
                rightanswer2.setMinAndMaxProgress(0.25f, 0.9f);
                rightanswer2.playAnimation();

                rightanswer_temp = 2;
                break;
            case "3":
                rightanswer3.setSpeed(3);
                rightanswer3.setMinAndMaxProgress(0.25f, 0.9f);
                rightanswer3.playAnimation();

                rightanswer_temp = 3;
                break;
            case "4":
                rightanswer4.setSpeed(3);
                rightanswer4.setMinAndMaxProgress(0.25f, 0.9f);
                rightanswer4.playAnimation();

                rightanswer_temp = 4;
                break;
        }


    }

    @Override
    public void onBackPressed() {
        if (current_question <= 1) {
            super.onBackPressed();
        } else {
            current_question--;
            questionNumber.setText(String.valueOf(current_question));

            swipe(false);
        }
    }

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        defineStuff();

        answer1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() > 30){

                    if (state){
                        S_answer1 = answer1.getText().toString();
                        S_answer2 = answer2.getText().toString();
                        S_answer3 = answer3.getText().toString();
                        S_answer4 = answer4.getText().toString();
                        S_question = question_context.getText().toString();

                        ViewGroup root = (ViewGroup) findViewById(android.R.id.content);
                        TransitionManager.go(Scene.getSceneForLayout(root, R.layout.activity_main_answer, MainActivity.this));
                        TransitionInflater.from(MainActivity.this).inflateTransition(R.transition.answer1);
                        state = false;
                        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

                        bigTextStarted(1,editable.toString());


                    }

                }
            }
        });

        answer2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() > 30){

                    if (state){
                        S_answer1 = answer1.getText().toString();
                        S_answer2 = answer2.getText().toString();
                        S_answer3 = answer3.getText().toString();
                        S_answer4 = answer4.getText().toString();
                        S_question = question_context.getText().toString();

                        ViewGroup root = (ViewGroup) findViewById(android.R.id.content);
                        TransitionManager.go(Scene.getSceneForLayout(root, R.layout.activity_main_answer, MainActivity.this));
                        TransitionInflater.from(MainActivity.this).inflateTransition(R.transition.answer2);
                        state = false;
                        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

                        bigTextStarted(2,editable.toString());


                    }

                }
            }
        });

        answer3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() > 30){

                    if (state){
                        S_answer1 = answer1.getText().toString();
                        S_answer2 = answer2.getText().toString();
                        S_answer3 = answer3.getText().toString();
                        S_answer4 = answer4.getText().toString();
                        S_question = question_context.getText().toString();

                        ViewGroup root = (ViewGroup) findViewById(android.R.id.content);
                        TransitionManager.go(Scene.getSceneForLayout(root, R.layout.activity_main_answer, MainActivity.this));
                        TransitionInflater.from(MainActivity.this).inflateTransition(R.transition.answer3);
                        state = false;
                        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

                        bigTextStarted(3,editable.toString());


                    }

                }
            }
        });

        answer4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() > 30){

                    if (state){
                        S_answer1 = answer1.getText().toString();
                        S_answer2 = answer2.getText().toString();
                        S_answer3 = answer3.getText().toString();
                        S_answer4 = answer4.getText().toString();
                        S_question = question_context.getText().toString();

                        ViewGroup root = (ViewGroup) findViewById(android.R.id.content);
                        TransitionManager.go(Scene.getSceneForLayout(root, R.layout.activity_main_answer, MainActivity.this));
                        TransitionInflater.from(MainActivity.this).inflateTransition(R.transition.answer4);
                        state = false;
                        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

                        bigTextStarted(4,editable.toString());

                    }

                }
            }
        });

        answer1.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b){
                if (answer1.getText().toString().length() > 30) {

                    if (state) {
                        S_answer1 = answer1.getText().toString();
                        S_answer2 = answer2.getText().toString();
                        S_answer3 = answer3.getText().toString();
                        S_answer4 = answer4.getText().toString();
                        S_question = question_context.getText().toString();

                        ViewGroup root = (ViewGroup) findViewById(android.R.id.content);
                        TransitionManager.go(Scene.getSceneForLayout(root, R.layout.activity_main_answer, MainActivity.this));
                        TransitionInflater.from(MainActivity.this).inflateTransition(R.transition.answer1);
                        state = false;
                        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

                        bigTextStarted(1, answer1.getText().toString());

                    }
                }
                }
            }
        });

        answer2.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b){
                if (answer2.getText().toString().length() > 30) {

                    if (state) {
                        S_answer1 = answer1.getText().toString();
                        S_answer2 = answer2.getText().toString();
                        S_answer3 = answer3.getText().toString();
                        S_answer4 = answer4.getText().toString();
                        S_question = question_context.getText().toString();

                        ViewGroup root = (ViewGroup) findViewById(android.R.id.content);
                        TransitionManager.go(Scene.getSceneForLayout(root, R.layout.activity_main_answer, MainActivity.this));
                        TransitionInflater.from(MainActivity.this).inflateTransition(R.transition.answer2);
                        state = false;
                        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

                        bigTextStarted(2, answer2.getText().toString());

                    }

                }
                }
            }
        });

        answer3.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b){
                if (answer3.getText().toString().length() > 30) {

                    if (state) {
                        S_answer1 = answer1.getText().toString();
                        S_answer2 = answer2.getText().toString();
                        S_answer3 = answer3.getText().toString();
                        S_answer4 = answer4.getText().toString();
                        S_question = question_context.getText().toString();

                        ViewGroup root = (ViewGroup) findViewById(android.R.id.content);
                        TransitionManager.go(Scene.getSceneForLayout(root, R.layout.activity_main_answer, MainActivity.this));
                        TransitionInflater.from(MainActivity.this).inflateTransition(R.transition.answer3);
                        state = false;
                        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

                        bigTextStarted(3, answer3.getText().toString());

                    }
                }
                }
            }
        });

        answer4.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b){
                if (answer4.getText().toString().length() > 30) {

                    if (state) {
                        S_answer1 = answer1.getText().toString();
                        S_answer2 = answer2.getText().toString();
                        S_answer3 = answer3.getText().toString();
                        S_answer4 = answer4.getText().toString();
                        S_question = question_context.getText().toString();

                        ViewGroup root = (ViewGroup) findViewById(android.R.id.content);
                        TransitionManager.go(Scene.getSceneForLayout(root, R.layout.activity_main_answer, MainActivity.this));
                        TransitionInflater.from(MainActivity.this).inflateTransition(R.transition.answer4);
                        state = false;
                        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

                        bigTextStarted(4, answer4.getText().toString());

                    }
                }
                }
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE) {

            path_temp = getRealPathFromURI(data.getData());
            Glide.with(MainActivity.this).load(data.getData()).into(image);
            has_image_temp = true;

        }


    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Intent gallary = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                    startActivityForResult(gallary, PICK_IMAGE);
                } else {
                    Toast.makeText(MainActivity.this, "GET_ACCOUNTS Denied",
                            Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions,
                        grantResults);
        }
    }
}
