package com.example.lbtest.lb;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.SharedPreferences;
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
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lbtest.BaseActivity;
import com.example.lbtest.R;
import com.example.lbtest.dao.PatientTestDAO;
import com.example.lbtest.model.PatientTest;
import com.example.lbtest.web.WebService;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

public class FirstActivity extends BaseActivity {

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
            "16、被环境的声音或噪音所困扰（时钟的滴答声、家人或交通的嘈杂声）。",
            "17、我需要睡足8小时白天才能够精力充沛和活动良好。",
            "18、当我一个晚上没有睡到足够时间，我需要在第二天午睡，或晚上睡更长的时间。",
            "19、因为我年纪越来越大，我睡觉时间应该减少。",
            "20、我担心如果我一或两个晚上没有睡觉，我可能会精神崩溃。",
            "21、我关心慢性失眠会对我的躯体健康产生严重影响。",
            "22、我躺在床上时间多，我通常睡觉时间也更多，第二天我感觉也会更好。",
            "23、当我入睡困难或晚上睡后醒来再难入睡时，我应该躺在床上，努力再睡。",
            "24、我担心我正失去睡觉的能力。",
            "25、因为我年纪越来越大，我应该晚上早上床睡觉。",
            "26、在经历一个晚上睡不好后，我知道这会影响我第二天白天的活动。",
            "27、如果服用安眠药物能睡好觉或者不服药就睡不好，为了使整个白天保持清醒和活动良好，我相信我应该服用安眠药物。",
            "28、我整天很烦躁，抑郁，焦虑是因为我在头一晚没有睡好觉。",
            "29、与我同睡的人一躺下就睡着，而且整个晚上睡得很好，我也能够做到。",
            "30、我觉得失眠基本上是一个年纪越来越大原因，对这样一个问题没有什么好办法解决。",
            "31、我有时还害怕在睡眠中死去。",
            "32、当我一个晚上睡觉好，我知道第二个晚上就会睡不好。",
            "33、当我一个晚上睡不好，我知道这会干扰我整个星期的睡眠时间。",
            "34、没有足够的睡眠时间，我第二天精力和活动都差。",
            "35、我不能够预测我睡得好还是睡不好。",
            "36、我对因睡眠被干扰后的负面影响无能为力。",
            "37、我整天感到疲劳，无精打采，原因是因为我头天晚上没有睡好觉。",
            "38、我整天头脑里想着晚上睡觉的问题，经常感到无法控制这种混乱思维。",
            "39、虽然我睡眠困难，但我仍然过着一种满意的生活。",
            "40、我相信失眠主要是化学物质不平衡的结果。",
            "41、我感到失眠正在破坏我享受生活乐趣的能力，并使我不能做我想做的事。",
            "42、临睡前喝酒是解决睡眠问题的好办法。",
            "43、催眠药物是解决睡眠问题的唯一办法。",
            "44、我睡眠问题越来越差，我不相信有人能帮我。",
            "45、从我外表可以看出我睡眠不好。",
            "46、在睡不好之后，我避免或取消要承担的事或工作。",
            "47、晚上上床睡觉的时间不规律。",
            "48、早上起床的时间不规律。",
            "49、早上醒來后会赖床。",
            "50、周末补眠。",
            "51、在床上做与睡眠无关的事（看电视、看书、性行为除外）。",
            "52、睡前太饥饿。",
            "53、睡前担心自己会睡不着。",
            "54、睡前有不愉快的谈话。",
            "55、睡眠没有足够的时间让自己放松。",
            "56、开着电视或音响入睡。",
            "57、躺上床后仍在脑海中思考未解决的问题。",
            "58、半夜会起来看时钟。",
            "59、白天小睡或躺在床上休息的时间超过一小时。",
            "60、白天缺乏接受太阳光照。",
            "61、缺乏规律的运动。",
            "62、白天担心晚上会睡不着。",
            "63、睡前四个小时饮用咖啡因的饮料。",
            "64、睡前两小时喝酒。",
            "65、睡前两小时使用刺激性物质（如：抽烟、槟榔等）。",
            "66、睡前两小时做剧烈的运动。",
            "67、睡前一小时吃太多食物。",
            "68、睡前一小时喝太多饮料。",
            "69、睡眠环境太吵或太安静。",
            "70、睡眠环境太亮或太暗。",
            "71、睡眠环境湿度太高或太低。",
            "72、睡眠环境室温太高或太低。",
            "73、卧室空气不流通。",
            "74、寝具不舒适（如：床、枕头、被子原因）。",
            "75、卧室摆放过多与睡眠无关甚至干扰睡眠的杂物。",
            "76、被床伴干扰睡眠。",
            " "};
    private Integer[] music = {R.raw.m2,R.raw.m3,R.raw.m4,R.raw.m5,R.raw.m6,R.raw.m7,
            R.raw.m8,R.raw.n9,R.raw.n10,R.raw.n11,R.raw.n12,R.raw.n13,R.raw.n14,R.raw.n15,
            R.raw.n16, R.raw.o17,R.raw.o18,R.raw.o19,R.raw.o20,R.raw.o21,R.raw.o22,
            R.raw.o23,R.raw.o24,R.raw.o25,R.raw.o26,R.raw.o27,R.raw.o28,R.raw.o29,R.raw.o30,R.raw.o31,
            R.raw.o32,R.raw.o33,R.raw.o34,R.raw.o35,R.raw.o36,R.raw.o37,R.raw.o38,R.raw.o39,R.raw.o40,
            R.raw.o41,R.raw.o42,R.raw.o43,R.raw.o44,R.raw.o45,R.raw.o46, R.raw.p47,R.raw.p48,R.raw.p49,
            R.raw.p50,R.raw.p51,R.raw.p52,R.raw.p53,R.raw.p54,R.raw.p55,R.raw.p56,R.raw.p57,R.raw.p58,
            R.raw.y60,R.raw.y61,R.raw.y62,R.raw.y63,R.raw.y64,R.raw.y65, R.raw.y66,R.raw.y67,R.raw.y68,
            R.raw.z69,R.raw.z70,R.raw.z71, R.raw.z72,R.raw.z73,R.raw.z74,R.raw.z75,R.raw.z76};
    private Button resultbutton;
    private int j;
    private String buttontext;
    private int resulttext[] = new int[80];
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
    private Button play;
    private int id=10087;
    public static String first= "";
    private SharedPreferences sp;
    private String sid;
    private String date2;
    private String info;
    private ProgressBar mProgressBarHorizontal;
    //private Button submit;
    //private EditText edit_content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        //去除标题
//        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);// 设置全屏
        setContentView(R.layout.activity_evalute_self_first);

        // this.helper=new DataBaseHelper(EvaluteSelfISIActivity.this);//数据库操作辅助类

        questiontext = (TextView) findViewById(R.id.questiontitle);
        play = (Button) findViewById(R.id.play);
        radioGroup = (RadioGroup) findViewById(R.id.radiogroup);
        button00 = (RadioButton) findViewById(R.id.button00);
        button01 = (RadioButton) findViewById(R.id.button01);
        button02 = (RadioButton) findViewById(R.id.button02);
        button03 = (RadioButton) findViewById(R.id.button03);
        button04 = (RadioButton) findViewById(R.id.button04);
        resultbutton = (Button) findViewById(R.id.resultbutton);
        mProgressBarHorizontal = (ProgressBar) findViewById(R.id.progressBarHorizontal);
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
                mProgressBarHorizontal.setVisibility(View.VISIBLE);
                //linear.setVisibility(View.VISIBLE);
                //播放音频
                player =MediaPlayer.create(FirstActivity.this, R.raw.m2);
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
        /*backBtn = (Button) findViewById(R.id.back_btn);
        backBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });*/


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
                        // 开始播放
                        player.start();
                        //朗读EditText里的内容
                        //mTextToSpeech.speak(question[i], TextToSpeech.QUEUE_FLUSH, null);
                        mProgressBarHorizontal.setProgress(i);
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

                    //tv.setText("你的得分: " + score + " 分");
                    tv.setText("您已完成所有测试。 ");
                    play.setVisibility(View.INVISIBLE);
                    mProgressBarHorizontal.setVisibility(View.INVISIBLE);
                    System.out.println("您的得分---" + total);

                    //保存数据
                    PInformation();
                    nextbutton.setVisibility(View.GONE);
                    radioGroup.setVisibility(View.INVISIBLE);
                }
            }
        });


        timeView = (TextView) findViewById(R.id.myTime);
        minute = 25;
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
        resulttext[77] = score;
        for(int i = 1;i < resulttext.length;i++){
            first += String.valueOf(resulttext[i]);
        }
        System.out.println("FIRST----->" + first);
        date2=getCurrentDate();
        System.out.println("DATE----->" + date);
        PatientTest patientTest = new PatientTest();
        //patientTest.setDate(date);
        patientTest.setFirst(first);
        PatientTestDAO ptdao = new PatientTestDAO(FirstActivity.this);
        // 读取已保存的数据
        sp = getSharedPreferences("config", 0);
        sid = null;
        // 获取保存的数据 key ,默认值
     //   sid = sp.getString("id", "");
//        ptdao.updateFirst(Integer.parseInt(sid), patientTest);
        //UpdateInfo();
    //    System.out.println("当前ID----" + Integer.parseInt(sid));
        Toast.makeText(FirstActivity.this, "【FIRST】数据保存成功！", Toast.LENGTH_SHORT).show();
    }

    /* private void UpdateInfo() {
        // 读取已保存的数据
         sp = getSharedPreferences("config", 0);
         // 获取保存的数据 key ,默认值
         sid = sp.getString("id", "");
        new Thread(new MyThread()).start();
    }
    public class MyThread implements Runnable {
        @Override
        public void run() {
            info = WebService.executeHttpGet2("TestLet",Integer.parseInt(sid),date2,
                    FirstActivity.first);
            handler.post(new Runnable() {
                @Override
                public void run() {
                    //infotv.setText(info);
                    Toast.makeText(FirstActivity.this, info, Toast.LENGTH_SHORT).show();
                    System.out.print("INFO--"+info);
                }
            });
        }
    }*/
}

