package com.example.lbtest.lb;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.media.AudioManager;
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
import com.example.lbtest.dao.ExportToCSV;
import com.example.lbtest.dao.PatientTestDAO;
import com.example.lbtest.model.PatientTest;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

public class DBASActivity extends Activity {


    private RelativeLayout rl;
    private RelativeLayout rl2;
    private int i = 0;
    private String[] question = {
            "2、当我一个晚上没有睡到足够时间，我需要在第二天午睡，或晚上睡更长的时间。",
            "3、因为我年纪越来越大，我睡觉时间应该减少。",
            "4、我担心如果我一或两个晚上没有睡觉，我可能会精神崩溃。",
            "5、我关心慢性失眠会对我的躯体健康产生严重影响。",
            "6、我躺在床上时间多，我通常睡觉时间也更多，第二天我感觉也会更好。",
            "7、当我入睡困难或晚上睡后醒来再难入睡时，我应该躺在床上，努力再睡。",
            "8、我担心我正失去睡觉的能力。",
            "9、因为我年纪越来越大，我应该晚上早上床睡觉。",
            "10、在经历一个晚上睡不好后，我知道这会影响我第二天白天的活动。",
            "11、如果服用安眠药物能睡好觉或者不服药就睡不好，为了使整个白天保持清醒和活动良好，我相信我应该服用安眠药物。",
            "12、我整天很烦躁，抑郁，焦虑是因为我在头一晚没有睡好觉。",
            "13、与我同睡的人一躺下就睡着，而且整个晚上睡得很好，我也能够做到。",
            "14、我觉得失眠基本上是一个年纪越来越大原因，对这样一个问题没有什么好办法解决。",
            "15、我有时还害怕在睡眠中死去。",
            "16、当我一个晚上睡觉好，我知道第二个晚上就会睡不好。",
            "17、当我一个晚上睡不好，我知道这会干扰我整个星期的睡眠时间。",
            "18、没有足够的睡眠时间，我第二天精力和活动都差。",
            "19、我不能够预测我睡得好还是睡不好。",
            "20、我对因睡眠被干扰后的负面影响无能为力。",
            "21、我整天感到疲劳，无精打采，原因是因为我头天晚上没有睡好觉。",
            "22、我整天头脑里想着晚上睡觉的问题，经常感到无法控制这种混乱思维。",
            "23、虽然我睡眠困难，但我仍然过着一种满意的生活。",
            "24、我相信失眠主要是化学物质不平衡的结果。",
            "25、我感到失眠正在破坏我享受生活乐趣的能力，并使我不能做我想做的事。",
            "26、临睡前喝酒是解决睡眠问题的好办法。",
            "27、催眠药物是解决睡眠问题的唯一办法。",
            "28、我睡眠问题越来越差，我不相信有人能帮我。",
            "29、从我外表可以看出我睡眠不好。",
            "30、在睡不好之后，我避免或取消要承担的事或工作。", "您的得分"};
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

    private MediaPlayer player=new MediaPlayer();

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
    private ImageView play;
    private boolean isPause;
    private int id=10086;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        //去除标题
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_evalute_self_dbas);
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
                player =MediaPlayer.create(DBASActivity.this, R.raw.m1);
                player.start();
                return false;
            }
        });

        play.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                getCurrentDate();
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


        final Button nextbutton = (Button) findViewById(R.id.nextbutton);
        nextbutton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                if (button00.isChecked() == false &&button01.isChecked() == false && button02.isChecked() == false && button03.isChecked() == false
                        && button04.isChecked() == false) {

                    new AlertDialog.Builder(DBASActivity.this)
                            .setTitle("温馨提示")
                            .setMessage("您还没有选择任何一个选项！")
                            .setPositiveButton("确定", null)
                            .show();
                    return;
                } else {
                    //播放音频
                    if (i<music.length-1){
                        // 切歌之前先重置，释放掉之前的资源
                        player.reset();
                        // 设置播放源
                        player = MediaPlayer.create(DBASActivity.this, music[i]);;
                        // 开始播放
                        player.start();
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

                if (i == question.length) {
                    nextbutton.setText("确定");
                    int total = score;
                    TextView tv = (TextView) findViewById(R.id.resulttitle);
                    tv.setVisibility(View.VISIBLE);

                    date = System.currentTimeMillis() + "";//记录做完最后一题的时间搓

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
        String dbas = "";
        for(int i = 1;i < resulttext.length;i++){
            dbas += String.valueOf(resulttext[i]);
        }
        System.out.println("DBAS----->" + dbas);
        String date=getCurrentDate();
        System.out.println("DATE----->" + date);
        PatientTest patientTest = new PatientTest();
        patientTest.setDate(date);
        patientTest.setDbas(dbas);
        PatientTestDAO ptdao = new PatientTestDAO(DBASActivity.this);
        //ptdao.insert(patientTest);
        ptdao.updateDbas(patientTest.getId(), patientTest);
        System.out.println("当前ID----" + patientTest.getId());
        Toast.makeText(DBASActivity.this, "【DBAS】数据保存成功！", Toast.LENGTH_SHORT).show();

        Cursor c = ptdao.export();
        ExportToCSV export2CSV = new ExportToCSV();
        export2CSV.exportToCSV(c, "test_table.csv");
    }
}

