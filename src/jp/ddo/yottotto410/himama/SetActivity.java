package jp.ddo.yottotto410.himama;

import java.io.IOException;
import java.util.Calendar;
import java.util.TimeZone;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class SetActivity extends Activity {
	public final static int REQUEST_CODE = 410;
	public final static int REQUEST_CODE_REC = 411;
	public final static int REQUEST_CODE_REC_HONTAI = 334;
	private static final String PREFERRENCES_FILE_NAME = "HimamaPrefrences";
	public static final String URI = "URI";
	private Calendar selectCal = null;
	TextView setHour_tv, setMin0_tv, setMin_tv;
	String recFilePath = null;
	AlarmManager am;
	Button timePick_btn;
	CheckBox chkbox;
	int alarmFlg; //アラームの番号フラグ(1〜5まで)
	private int setHour = 0; //時
	private int setMin = 0; //分
	Uri uri = null;
	

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_set);		
		/*
		 * MainActivityからのintent data
		 * どのアラームの設定をするのかを判別
		 */
		Intent intent = getIntent();
		alarmFlg = intent.getIntExtra("alarmFlg",0);
		/*
		 *almのon/off 
		 */
		 chkbox = (CheckBox)findViewById(R.id.checkBox1);
		/*
		 * TimePicker
		 */
		timePick_btn = (Button)this.findViewById(R.id.timePick);
		setHour_tv = (TextView) findViewById(R.id.setHour);
		setMin0_tv = (TextView) findViewById(R.id.setMin0);
		setMin_tv = (TextView) findViewById(R.id.setMin);
	    timePick_btn.setOnClickListener(new View.OnClickListener() {
        	@Override
            public void onClick(View v) {
        		Toast.makeText(SetActivity.this, " じこくをへんこうします ", Toast.LENGTH_SHORT).show();
        		System.out.println("TimePickerActivity起動のintent");
        	    Intent i = new Intent(SetActivity.this, TimePickerActivity.class);
        		startActivityForResult( i , REQUEST_CODE ); 	            		
        	}
        });
	    
	    
	    /*
		 * situation選択
		 */
		final EditText situ_et = (EditText)findViewById(R.id.situ_et);
		
		/*
		 *  メッセージ設定
		 */
		final LinearLayout msg1_row = (LinearLayout)findViewById(R.id.msg1_row);
		final EditText msg1_et = (EditText)findViewById(R.id.ms1_et);
		final ImageView add1 = (ImageView) findViewById(R.id.add1_btn);
		final LinearLayout msg2_row = (LinearLayout)findViewById(R.id.msg2_row);
		final EditText msg2_et = (EditText)findViewById(R.id.ms2_et);
		final ImageView add2 = (ImageView)findViewById(R.id.add2_btn);
		final LinearLayout msg3_row = (LinearLayout)findViewById(R.id.msg3_row);
		final EditText msg3_et = (EditText)findViewById(R.id.ms3_et);
		final ImageView add3 = (ImageView)findViewById(R.id.add3_btn);
		final LinearLayout msg4_row = (LinearLayout)findViewById(R.id.msg4_row);
		final EditText msg4_et = (EditText)findViewById(R.id.ms4_et);
		final ImageView add4 = (ImageView)findViewById(R.id.add4_btn);
		final LinearLayout msg5_row = (LinearLayout)findViewById(R.id.msg5_row);
		final EditText msg5_et = (EditText)findViewById(R.id.ms5_et);
		final ImageView add5 = (ImageView)findViewById(R.id.add5_btn);
		add1.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                if (add1.getVisibility() == View.VISIBLE){
                    add1.setVisibility(View.INVISIBLE);
                    msg2_row.setVisibility(View.VISIBLE);
                } 
            }
        });
		add2.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                if (add2.getVisibility() == View.VISIBLE){
                    add2.setVisibility(View.INVISIBLE);
                    msg3_row.setVisibility(View.VISIBLE);
                } 
            }
        });
		add3.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                if (add3.getVisibility() == View.VISIBLE){
                    add3.setVisibility(View.INVISIBLE);
                    msg4_row.setVisibility(View.VISIBLE);
                } 
            }
        });
		add4.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                if (add4.getVisibility() == View.VISIBLE){
                    add4.setVisibility(View.INVISIBLE);
                    msg5_row.setVisibility(View.VISIBLE);
                } 
            }
        });
		
		
		
		/*
		 * 再生button
		 */		
		ImageButton play_btn = (ImageButton)findViewById(R.id.play_btn);
		play_btn.setOnClickListener(new View.OnClickListener() {
        	@Override
            public void onClick(View v) {
        		MediaPlayer mp;
        		mp = new MediaPlayer();
        		if(recFilePath!=null){
        			try {
        				mp.setDataSource(recFilePath);
        			} catch (IllegalArgumentException e) {
        				// TODO Auto-generated catch block
        				e.printStackTrace();
        			} catch (SecurityException e) {
        				// TODO Auto-generated catch block
        				e.printStackTrace();
        			} catch (IllegalStateException e) {
        				// TODO Auto-generated catch block
        				e.printStackTrace();
        			} catch (IOException e) {
        				// TODO Auto-generated catch block
        				e.printStackTrace();
        			}
        			try {
        				mp.prepare();
        			} catch (IllegalStateException e) {
        				// TODO Auto-generated catch block
        				e.printStackTrace();
        			} catch (IOException e) {
        				// TODO Auto-generated catch block
        				e.printStackTrace();
        			}
        			mp.start();
        			Toast.makeText(SetActivity.this, "さいせい中", Toast.LENGTH_SHORT).show();
        		}
        		else {
        			Toast.makeText(SetActivity.this, "ファイルをせんたくしてください", Toast.LENGTH_SHORT).show();    			
        		}
        	}
        });
		
		/*
		 * alarm選択
		 */
		Button alSet_btn = (Button)this.findViewById(R.id.al_set);
		alSet_btn.setOnClickListener(new View.OnClickListener() {
        	@Override
            public void onClick(View v) {
        		new AlertDialog.Builder(SetActivity.this)
        		 .setMessage("アラーム音をえらびます")
        		 .setCancelable(false)//キャンセルを押せなくする
        		 .setPositiveButton("ろくおん", new DialogInterface.OnClickListener() {
        		     public void onClick(DialogInterface dialog, int id) {
        		    	 Toast.makeText(SetActivity.this,"ろくおんします", Toast.LENGTH_SHORT).show();
        		         System.out.println("RecActivityを起動するintent送信");
        		         Intent recSend = new Intent(SetActivity.this, RecActivity.class); 
        		         startActivityForResult( recSend , REQUEST_CODE_REC );
        		         //startActivity(recSend);
        		    }
        		})
        		.setNegativeButton("ほんたい", new DialogInterface.OnClickListener() {
        		    public void onClick(DialogInterface dialog, int id) {
        		    	Toast.makeText(SetActivity.this,"ほんたいからさがします", Toast.LENGTH_SHORT).show();
        		    	Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        		    	intent.setType("audio/*");
        		    	startActivityForResult(Intent.createChooser(intent, "おんせいをえらびます"), REQUEST_CODE_REC_HONTAI );
       		         	System.out.println("****起動するintent送信");		 
        		    }
        		})
        		.show();

        	}        	
		});
		/*
		 * かんりょうbutton
		 */
		// AlarmManagerのインスタンス取得
		am = (AlarmManager) getSystemService(ALARM_SERVICE);
		Button ok_btn = (Button)this.findViewById(R.id.ok);
	    ok_btn.setOnClickListener(new View.OnClickListener() {
        	@Override
            public void onClick(View v) {
                Intent mainSend = new Intent(SetActivity.this, MainActivity.class);
                switch(alarmFlg){
                case 1: 
                	mainSend.putExtra("setHour", setHour_tv.getText());
    	    		mainSend.putExtra("setMin", setMin_tv.getText());
    	    		mainSend.putExtra("setMin0", setMin0_tv.getText());
    	    		mainSend.putExtra("situ_et", situ_et.getText().toString());
    	    		System.out.println("==========intentで送る内容==========\n"+
    	    						   "==========  Set -> Main  =========\n"+
    	    						   "時刻-> "+setHour_tv.getText().toString()+":"+setMin_tv.getText().toString()+"\n分桁合わせ"+setMin0_tv.getText().toString());
    	    		System.out.println("場面-> "+situ_et.getText().toString());  
    	    		setResult(RESULT_OK, mainSend);
    	    		/*
    	    		 * SharedPreferencesへの保存
    	    		 */
    	    		Editor editor = getSharedPreferences(PREFERRENCES_FILE_NAME, MODE_PRIVATE).edit();
    	    	    editor.putString("setHour1",setHour_tv.getText().toString());
    	    	    editor.putString("setMin1",setMin_tv.getText().toString());
    	    	    editor.putString("setMin01",setMin0_tv.getText().toString());
    	    	    editor.putString("situ1_et",situ_et.getText().toString());	
    	    	    editor.putString("recFilePath1", recFilePath);
    	    	    editor.commit();
    	    	    /*
    	    	     * BroadcastReceiverへのインテント
    	    	     */
    	    	    Intent brIntent = new Intent(SetActivity.this, BroadcastReceiverActivity.class);
    	    	    brIntent.putExtra(URI, uri);
    	    	    //第四引数がアラームを上書き状態になってることに注意 5つアラームするためには FLAG_UPDATE_CURRENT
    	    	    PendingIntent sender = PendingIntent.getBroadcast(SetActivity.this, 0, brIntent, PendingIntent.FLAG_CANCEL_CURRENT);
    	    	    // アラームON/OFFボタンがOFFの場合
    				if (chkbox.isChecked() == true) {
    					System.out.println("chkbox is true.");
    					// 起動時間の取得
    					long timeInMillis = createTimeInMillis();
    					// アラームの設定
    					am.setRepeating(AlarmManager.RTC_WAKEUP, timeInMillis,
    							AlarmManager.INTERVAL_DAY, sender);
    				} else {
    					// アラーム解除
    					am.cancel(sender);
    				}

                	break;
                case 2: break;
                case 3: break;
                case 4: break;
                case 5: break;
                
                default: System.out.println("完了button -> default通過");
                break;
              }
                finish();        
            }
        });
	}
	
	
	
	/*
	 * TimePickerActivity&アラーム音を設定するintentからデータを受け取る
	 * @see android.app.Activity#onActivityResult(int, int, android.content.Intent)
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == REQUEST_CODE) {	//TimePickerActivityから
			if (resultCode == RESULT_OK) {
				Bundle extra = data.getExtras();
				setHour = extra.getInt("setHour");
				setMin = extra.getInt("setMin");
				int hour = setHour;	int min = setMin;
				System.out.println("受け取った時刻は"+hour+":"+min);
				setHour_tv.setText(""+hour);
				setMin0_tv.setText(""+getString(R.string.min_keta, min));//桁合わせ
				setMin_tv.setText(""+min);			
	        	}
			}	
		else if(requestCode == REQUEST_CODE_REC_HONTAI) {	//アラーム音を設定するintentから
			if(resultCode == RESULT_OK) {
				uri = (Uri)data.getData();
		        recFilePath = uri.getPath();
		        System.out.println("RequestCodeRecのURI = "+recFilePath);
			}
		}
		else if(requestCode == REQUEST_CODE_REC) {	//RecActivityより
			if(resultCode == RESULT_OK) {
				Bundle extra = data.getExtras();
				recFilePath = extra.getString("URI");
			}
		}
	}
	
	/*
	 * createTimeInMillisメソッド(起動時間のUTCミリ秒値を算出処理)
	 */
	private long createTimeInMillis() {

		// 日本時間に設定
		TimeZone tz = TimeZone.getTimeZone("Asia/Tokyo");
		Calendar nowCal = Calendar.getInstance(tz);
		
		// 設定時間を取得
		selectCal = Calendar.getInstance(tz);
		System.out.println(setHour+":"+setMin);
		selectCal.set(Calendar.HOUR_OF_DAY, setHour);
		selectCal.set(Calendar.MINUTE, setMin);

		// 現在時間より前の時間を選択した場合は翌日に設定
		if (selectCal.before(nowCal)) {
			selectCal.add(Calendar.DATE, 1);
		}
		System.out.println("計算された時刻-> "+selectCal.getTimeInMillis());
		// 起動時間のUTCミリ秒値
		return selectCal.getTimeInMillis();

	}
}