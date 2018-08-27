package com.example.lbtest.lb;

import android.app.Activity;
import android.app.AlertDialog;
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

import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

public class EvaluteSelfPSASActivity extends Activity {


    private RelativeLayout rl;
    private RelativeLayout rl2;
    private int i = 0;
    private String[] question = {
            "2、感觉紧张不安。",
            "3、气短、呼吸不畅。",
            "4、感觉肌肉紧张。",
            "5、感觉手、脚或身体发冷。",
            "6、胃部灼热、恶心等不适。",
            "7、手部或身体其他部位有汗。",
            "8、口腔或者喉咙觉得干燥。",
            "9、担心难以入睡。",
            "10、回忆或者思考白天的事情。",
            "11、有焦虑或者抑郁的想法。",
            "12、担心睡眠以外的问题。",
            "13、思维活跃。",
            "14、不能终止自己的想法。",
            "15、有想法蹦出到脑海中。",
            "16、因钟表、交通等环境噪声心烦意乱。","您的得分"};
    private Button resultbutton;
    private int j;
    private String buttontext;
    private int resulttext[] = new int[20];
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
    private ImageView play;
    /*private LinearLayout linear;
    private Button submit;
    private EditText edit_content;*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        //去除标题
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_evalute_self_psas);

        // this.helper=new DataBaseHelper(EvaluteSelfISIActivity.this);//数据库操作辅助类

        questiontext = (TextView) findViewById(R.id.questiontitle);
        play = (ImageView) findViewById(R.id.play);
        //linear = (LinearLayout) findViewById(R.id.linear);
        radioGroup = (RadioGroup) findViewById(R.id.radiogroup);
        button01 = (RadioButton) findViewById(R.id.button01);
        button02 = (RadioButton) findViewById(R.id.button02);
        button03 = (RadioButton) findViewById(R.id.button03);
        button04 = (RadioButton) findViewById(R.id.button04);
        button05 = (RadioButton) findViewById(R.id.button05);
        resultbutton = (Button) findViewById(R.id.resultbutton);
        //submit = (Button) findViewById(R.id.submit);
       // edit_content = (EditText) findViewById(R.id.edit_content);
        rl = (RelativeLayout) findViewById(R.id.relativeLayout);
        rl2 = (RelativeLayout) findViewById(R.id.relativeLayout2);
        radioGroup.setOnCheckedChangeListener(new RadioGroupListener());
        rl.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View arg0, MotionEvent arg1) {
                rl.setVisibility(View.GONE);
                /*questiontext.setVisibility(View.VISIBLE);
                resultbutton.setVisibility(View.VISIBLE);*/
                rl2.setVisibility(View.VISIBLE);
                //linear.setVisibility(View.VISIBLE);
                mTextToSpeech.speak("1、心跳加快，或者不规则。", TextToSpeech.QUEUE_FLUSH, null);
                return false;
            }
        });
       /* submit.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(EvaluteSelfPSASActivity.this,"您提交了以下内容："+edit_content.getText().toString(),Toast.LENGTH_LONG).show();
            }
        });*/
        play.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                mTextToSpeech.speak(questiontext.getText().toString(), TextToSpeech.QUEUE_FLUSH, null);
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
                        Toast.makeText(EvaluteSelfPSASActivity.this, "不支持当前语言！", Toast.LENGTH_LONG).show();
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
                if (button01.isChecked() == false && button02.isChecked() == false && button03.isChecked() == false
                        && button04.isChecked() == false && button05.isChecked() == false) {

                    new AlertDialog.Builder(EvaluteSelfPSASActivity.this)
                            .setTitle("温馨提示")
                            .setMessage("您还没有选择任何一个选项！")
                            .setPositiveButton("确定", null)
                            .show();
                    return;
                } else {

                    //朗读EditText里的内容
                    mTextToSpeech.speak(question[i], TextToSpeech.QUEUE_FLUSH, null);

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

        if (mTextToSpeech != null) {
            mTextToSpeech.shutdown();//关闭TTS
        }
    }

   /* //获得要保存的数据
    public void PInformation() {
        int[] result = new int[7];
        result[0] = resulttext[2];
        result[1] = resulttext[3];
        result[2] = resulttext[4];
        result[3] = resulttext[5];
        result[4] = resulttext[6];
        result[5] = resulttext[7];
        result[6] = resulttext[8];
//		result[7] = score;
        String isi = "";
        for (int i = 0; i < result.length; i++) {
            isi += String.valueOf(result[i]);
        }
        System.out.println("严重失眠指数----->" + isi);
        if (score < 10) {
            isi = isi + "0" + score;
        } else {
            isi = isi + score;
        }
    }*/
}

