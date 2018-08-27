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

public class SHPSActivity extends Activity {

    private MediaPlayer player=new MediaPlayer();
    private RelativeLayout rl;
    private RelativeLayout rl2;
    private int i = 0;
    private String[] question = {
            "2、早上起床的時間不规律。",
            "3、早上醒來后会赖床。",
            "4、周末补眠。",
            "5、在床上做与睡眠无关的事（看电视、看书、性行为除外）。",
            "6、睡前太饥饿。",
            "7、睡前担心自己会睡不着。",
            "8、睡前有不愉快的谈话。",
            "9、睡眠没有足够的时间让自己放松。",
            "10、开着电视或音响入睡。",
            "11、躺上床后仍在脑海中思考未解决的问题。",
            "12、半夜会起来看时钟。",
            "13、白天小睡或躺在床上休息的时间超过一小时。",
            "14、白天缺乏接受太阳光照。",
            "15、缺乏规律的运动。",
            "16、白天担心晚上会睡不着。",
            "17、睡前四个小时饮用咖啡因的饮料。",
            "18、睡前两小时喝酒。",
            "19、睡前两小时使用刺激性物质（如：抽烟、槟榔等）。",
            "20、睡前两小时做剧烈的运动。",
            "21、睡前一小时吃太多食物。",
            "22、睡前一小时喝太多饮料。",
            "23、睡眠环境太吵或太安静。",
            "24、睡眠环境太亮或太暗。",
            "25、睡眠环境湿度太高或太低。",
            "26、睡眠环境室温太高或太低。",
            "27、卧室空气不流通。",
            "28、寝具不舒适（如：床、枕头、被子原因）。",
            "29、卧室摆放过多与睡眠无关甚至干扰睡眠的杂物。",
            "30、被床伴干扰睡眠。", "您的得分"};
    private Integer[] music = {R.raw.m2,R.raw.m3,R.raw.m4,R.raw.m5,R.raw.m6,R.raw.m7,
            R.raw.m8,R.raw.m9,R.raw.m10,R.raw.m11,R.raw.m12,R.raw.m13,R.raw.m14,R.raw.m15,R.raw.m16,
            R.raw.m17,R.raw.m18,R.raw.m19,R.raw.m20,R.raw.m21,R.raw.m22,R.raw.m23,R.raw.m24,R.raw.m25,
            R.raw.m26,R.raw.m27,R.raw.m28,R.raw.m29,R.raw.m30};
    private Button resultbutton;
    private int j;
    private String buttontext;
    private int resulttext[] = new int[40];
    private RadioButton button00;
    private RadioButton button01;
    private RadioButton button02;
    private RadioButton button03;
    private RadioButton button04;
    private RadioButton button05;
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
    //private Button submit;
    //private EditText edit_content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        //去除标题
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_evalute_self_shps);

        // this.helper=new DataBaseHelper(EvaluteSelfISIActivity.this);//数据库操作辅助类

        questiontext = (TextView) findViewById(R.id.questiontitle);
        play = (ImageView) findViewById(R.id.play);
        radioGroup = (RadioGroup) findViewById(R.id.radiogroup);
        button00 = (RadioButton) findViewById(R.id.button00);
        button01 = (RadioButton) findViewById(R.id.button01);
        button02 = (RadioButton) findViewById(R.id.button02);
        button03 = (RadioButton) findViewById(R.id.button03);
        button04 = (RadioButton) findViewById(R.id.button04);
        button05 = (RadioButton) findViewById(R.id.button05);
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
                player =MediaPlayer.create(SHPSActivity.this, R.raw.m1);
                player.start();
                //mTextToSpeech.speak("1、晚上上床睡觉的时间不规律。", TextToSpeech.QUEUE_FLUSH, null);
                return false;
            }
        });
        /*submit.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(EvaluteSelfISIActivity.this,"您提交了以下内容："+edit_content.getText().toString(),Toast.LENGTH_LONG).show();
            }
        });*/
        play.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                //播放音频
                if(player.isPlaying()){
                    player.seekTo(0);
                }else{
                    //player=MediaPlayer.create(DBASActivity.this, R.raw.test);
                    player.start();
                }
                //mTextToSpeech.speak(questiontext.getText().toString(), TextToSpeech.QUEUE_FLUSH, null);
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
                        Toast.makeText(SHPSActivity.this, "不支持当前语言！", Toast.LENGTH_LONG).show();
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
                        && button04.isChecked() == false && button05.isChecked() == false) {

                    new AlertDialog.Builder(SHPSActivity.this)
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
                        player = MediaPlayer.create(SHPSActivity.this, music[i]);
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
/*
                if (i == 4) {
                    button01.setText("非常满意");
                    button02.setText("满意");
                    button03.setText("不太满意");
                    button04.setText("不满意");
                    button05.setText("非常不满意");
                }

                if (i == 5 || i == 6 || i == 7) {
                    button01.setText("无");
                    button02.setText("轻度");
                    button03.setText("中度");
                    button04.setText("重度");
                    button05.setText("极重度");
                }*/

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
                    nextbutton.setVisibility(View.GONE);
                    radioGroup.setVisibility(View.INVISIBLE);

                   // PInformation();
//					finish();
                }

//				if(i == question.length ){
//					//finish();
//					radioGroup.setVisibility(View.INVISIBLE);
//					nextbutton.setVisibility(View.INVISIBLE);
//				}
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
            if (checkedId == button05.getId()) {
                j = 5;
                buttontext = button05.getText().toString();
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
        resulttext[29] = score;
        String shps = "";
        for(int i = 1;i < resulttext.length;i++){
            shps += String.valueOf(resulttext[i]);
        }
        System.out.println("SHPS----->" + shps);
        String date=getCurrentDate();
        System.out.println("DATE----->" + date);
        PatientTest patientTest = new PatientTest();
        patientTest.setDate(date);
        patientTest.setShps(shps);
        PatientTestDAO ptdao = new PatientTestDAO(SHPSActivity.this);
        ptdao.updateDbas(ptdao.getMaxId(), patientTest);
        System.out.println("当前ID----" + ptdao.getMaxId());
        Toast.makeText(SHPSActivity.this, "【SHPS】数据保存成功！", Toast.LENGTH_SHORT).show();
    }
}

