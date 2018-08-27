package com.example.lbtest.lb;

import android.app.Activity;
import android.app.AlertDialog;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.speech.tts.TextToSpeech;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lbtest.R;
import com.example.lbtest.dao.PatientTestDAO;
import com.example.lbtest.model.PatientTest;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

public class FirstActivity extends Activity {

    private MediaPlayer player=new MediaPlayer();
    private RelativeLayout rl;
    private RelativeLayout rl2;
    private int i = 0;
    private String[] question = {
            "2、感觉身体紧张不安。",
            "3、感觉踹不过气或呼吸困难。",
            "4、感觉肌肉紧绷。",
            "5、感觉手、脚或身体冰冷。",
            "6、感觉胃不适（翻搅、纠结、恶心、灼热、反胃、胀气等）。",
            "7、感觉手掌心或身体其他部位出汗。",
            "8、感觉口干舌燥。",
            "9、担心无法入睡。",
            "10、开始回忆或思考一天所发生的事情。",
            "11、忧愁或焦虑的想法。",
            "12、担心睡眠以外的问题。",
            "13、思想清楚、活跃。",
            "14、无法停止思考。",
            "15、思想持续盘旋在心里。",
            "16、被环境的声音或噪音所困扰（时钟的滴答声、家人或交通的嘈杂声）。","您的得分"};
    private Integer[] music = {R.raw.m2,R.raw.m3,R.raw.m4,R.raw.m5,R.raw.m6,R.raw.m7,
            R.raw.m8,R.raw.m9,R.raw.m10,R.raw.m11,R.raw.m12,R.raw.m13,R.raw.m14,R.raw.m15,R.raw.m16};
    private Button resultbutton;
    private int j;
    private String buttontext;
    private int resulttext[] = new int[20];
    private RadioButton button00;
    private RadioButton button01;
    private RadioButton button02;
    private RadioButton button03;
    private RadioButton button04;
    private RadioGroup radioGroup;
    private TextView questiontext;
    private int score;

    private String date;

    static int minute = -1;
    static int second = -1;
    final static String tag = "tag";
    TextView timeView;
    Timer timer;
    TimerTask timerTask;

    private TextToSpeech mTextToSpeech;
    private Button backBtn;
    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            if (minute == 0) {
                if (second == 0) {
                    timeView.setText("你已超时 !");
                    if (timer != null) {
                        timer.cancel();
                        timer = null;
                    }
                    if (timerTask != null) {
                        timerTask = null;
                    }
                } else {
                    second--;
                    if (second >= 10) {
                        timeView.setText("0" + minute + ":" + second);
                    } else {
                        timeView.setText("0" + minute + ":0" + second);
                    }
                }
            } else {
                if (second == 0) {
                    second = 59;
                    minute--;
                    if (minute >= 10) {
                        timeView.setText(minute + ":" + second);
                    } else {
                        timeView.setText("0" + minute + ":" + second);
                    }
                } else {
                    second--;
                    if (second >= 10) {
                        if (minute >= 10) {
                            timeView.setText(minute + ":" + second);
                        } else {
                            timeView.setText("0" + minute + ":" + second);
                        }
                    } else {
                        if (minute >= 10) {
                            timeView.setText(minute + ":0" + second);
                        } else {
                            timeView.setText("0" + minute + ":0" + second);
                        }
                    }
                }
            }
        }

        ;
    };
    //private LinearLayout linear;
    private ImageView play;
    private int id=10087;
    //private Button submit;
    //private EditText edit_content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        //去除标题
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_evalute_self_first);

        // this.helper=new DataBaseHelper(EvaluteSelfISIActivity.this);//数据库操作辅助类

        questiontext = (TextView) findViewById(R.id.questiontitle);
        play = (ImageView) findViewById(R.id.play);
        radioGroup = (RadioGroup) findViewById(R.id.radiogroup);
        button00 = (RadioButton) findViewById(R.id.button00);
        button01 = (RadioButton) findViewById(R.id.button01);
        button02 = (RadioButton) findViewById(R.id.button02);
        button03 = (RadioButton) findViewById(R.id.button03);
        button04 = (RadioButton) findViewById(R.id.button04);
        resultbutton = (Button) findViewById(R.id.resultbutton);
        //submit = (Button) findViewById(R.id.submit);
        //edit_content = (EditText) findViewById(R.id.edit_content);
        rl = (RelativeLayout) findViewById(R.id.relativeLayout);
        rl2 = (RelativeLayout) findViewById(R.id.relativeLayout2);
        //linear = (LinearLayout) findViewById(R.id.linear);
        radioGroup.setOnCheckedChangeListener(new RadioGroupListener());
        rl.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View arg0, MotionEvent arg1) {
                rl.setVisibility(View.GONE);
                /*questiontext.setVisibility(View.VISIBLE);
                resultbutton.setVisibility(View.VISIBLE);*/
                rl2.setVisibility(View.VISIBLE);
                //linear.setVisibility(View.VISIBLE);
                //播放音频
                player =MediaPlayer.create(FirstActivity.this, R.raw.m1);
                player.start();
                //mTextToSpeech.speak("1、感觉心脏快速、剧烈或不规则的跳动。", TextToSpeech.QUEUE_FLUSH, null);
                return false;
            }
        });

        play.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                //mTextToSpeech.speak(questiontext.getText().toString(), TextToSpeech.QUEUE_FLUSH, null);
                //播放音频
                if(player.isPlaying()){
                    player.seekTo(0);
                }else{
                    //player=MediaPlayer.create(DBASActivity.this, R.raw.test);
                    player.start();
                }
            }
        });
        backBtn = (Button) findViewById(R.id.back_btn);
        backBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });


        //实例并初始化TTS对象
        mTextToSpeech = new TextToSpeech(this, new TextToSpeech.OnInitListener() {

            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    //设置朗读语言
                    int supported = mTextToSpeech.setLanguage(Locale.US);
                    if ((supported != TextToSpeech.LANG_AVAILABLE) && (supported != TextToSpeech.LANG_COUNTRY_AVAILABLE)) {
                        Toast.makeText(FirstActivity.this, "不支持当前语言！", Toast.LENGTH_LONG).show();
                    }
                }

            }
        });
        final Button nextbutton = (Button) findViewById(R.id.nextbutton);
        nextbutton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                //questiontext.setText(question[i]);
                //i ++;
                if (button00.isChecked() == false &&button01.isChecked() == false && button02.isChecked() == false && button03.isChecked() == false
                        && button04.isChecked() == false) {

                    new AlertDialog.Builder(FirstActivity.this)
                            .setTitle("温馨提示")
                            .setMessage("您还没有选择任何一个选项！")
                            .setPositiveButton("确定", null)
                            .show();
                    return;
                } else {
                    //播放音频
                    if (i<music.length-1) {
                        // 切歌之前先重置，释放掉之前的资源
                        player.reset();
                        // 设置播放源
                        player = MediaPlayer.create(FirstActivity.this, music[i]);
                        ;
                        // 开始播放
                        player.start();
                        //朗读EditText里的内容
                        //mTextToSpeech.speak(question[i], TextToSpeech.QUEUE_FLUSH, null);
                    }
                    questiontext.setText(question[i]);
                    if (i < question.length + 1) {
                        i++;
                    }
                    if (i == 1) {
                        radioGroup.setVisibility(View.VISIBLE);
                    }
                    //i++;
                    radioGroup.clearCheck();
                }
                score += j;
                System.out.println("result----->" + score);
                resulttext[i] = j;
                System.out.println("选择的结果----》" + i + "   " + resulttext[i]);

                //question[7] = "您的得分: " + score;

                if (i == question.length) {
                    nextbutton.setText("确定");
                    //score += j;
                    int total = score;
                    TextView tv = (TextView) findViewById(R.id.resulttitle);
                    tv.setVisibility(View.VISIBLE);

                    date = System.currentTimeMillis() + "";//记录做完最后一题的时间搓

//					question[7] = "您的得分: " + total;

                    tv.setText("你的得分: " + score + " 分");
                    System.out.println("您的得分---" + total);

                    //保存数据
                    PInformation();
                    nextbutton.setVisibility(View.GONE);
                    radioGroup.setVisibility(View.INVISIBLE);
                }
            }
        });


        timeView = (TextView) findViewById(R.id.myTime);
        minute = 5;
        second = 00;
        timeView.setText(minute + ":" + second);

        timerTask = new TimerTask() {

            @Override
            public void run() {
                Message msg = new Message();
                msg.what = 0;
                handler.sendMessage(msg);
            }
        };

        timer = new Timer();
        timer.schedule(timerTask, 0, 1000);


    }

    public class RadioGroupListener implements OnCheckedChangeListener {

        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            if (checkedId == button00.getId()) {
                j = 0;
                buttontext = button00.getText().toString();
                System.out.println("buttontext ---" + buttontext);
            }
            if (checkedId == button01.getId()) {
                j = 1;
                buttontext = button01.getText().toString();
                System.out.println("buttontext ---" + buttontext);
            }
            if (checkedId == button02.getId()) {
                j = 2;
                System.out.println("result11----->" + j);
                buttontext = button02.getText().toString();
                System.out.println("buttontext ---" + buttontext);
            }
            if (checkedId == button03.getId()) {
                j = 3;
                buttontext = button03.getText().toString();
                System.out.println("buttontext ---" + buttontext);
            }
            if (checkedId == button04.getId()) {
                j = 4;
                buttontext = button04.getText().toString();
                System.out.println("buttontext ---" + buttontext);
            }
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
            if (play != null) {
                player.stop();
                player.release();
            }
        if (mTextToSpeech != null) {
            mTextToSpeech.shutdown();//关闭TTS
        }
    }
    public String getCurrentDate() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");// HH:mm:ss
        //获取当前时间
        Date date = new Date(System.currentTimeMillis());
        String currentDate = simpleDateFormat.format(date);
        return currentDate;
    }
    //获得要保存的数据
    public void PInformation() {
        resulttext[15] = score;
        String first = "";
        for(int i = 1;i < resulttext.length;i++){
            first += String.valueOf(resulttext[i]);
        }
        System.out.println("FIRST----->" + first);
        String date=getCurrentDate();
        System.out.println("DATE----->" + date);
        PatientTest patientTest = new PatientTest();
        patientTest.setDate(date);
        patientTest.setDbas(first);
        PatientTestDAO ptdao = new PatientTestDAO(FirstActivity.this);
        ptdao.updateDbas(id, patientTest);
        System.out.println("当前ID----" + id);
        Toast.makeText(FirstActivity.this, "【FIRST】数据保存成功！", Toast.LENGTH_SHORT).show();
    }
}

