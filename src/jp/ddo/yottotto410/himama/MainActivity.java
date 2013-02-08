package jp.ddo.yottotto410.himama;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

public class MainActivity extends Activity {
	public final static int REQUEST_CODE = 410;
	TextView setHour1_tv, setMin1_tv, setMin01_tv, situ1_et;
	

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
		setContentView(R.layout.activity_main); 
		System.out.println("MainActivity running onCreate");
		
		setHour1_tv = (TextView) findViewById(R.id.setHour1);
		setMin1_tv = (TextView) findViewById(R.id.setMin1);
		setMin01_tv = (TextView) findViewById(R.id.setMin01);
		situ1_et = (TextView) findViewById(R.id.how1);
	    /*
	     * トグルbutton (今は使わない)
	     */		
	    /*ToggleButton toggleButton1 = (ToggleButton) findViewById(R.id.toggleButton1);
	    // ToggleButton が On のときのテキストを設定  
	    toggleButton1.setTextOn("ON");  
	    // ToggleButton が Off のときのテキストを設定  
	    toggleButton1.setTextOff("OFF");  
	    // ToggleButton が デフォルトでonになるように  
	    //toggleButton.setChecked(true);  	    
	    // ToggleButton が On かどうかを取得  
	    boolean checked = toggleButton1.isChecked(); */
	    /*
	     * 変更button
	     */
	    Button change1_btn = (Button)this.findViewById(R.id.change1);
	    change1_btn.setOnClickListener(new View.OnClickListener() {
        	@Override
            public void onClick(View v) {
        		Toast.makeText(MainActivity.this, " せっていをへんこうします ", Toast.LENGTH_SHORT).show();
        		System.out.println("SetActivity起動のintent");
        	    Intent i = new Intent(MainActivity.this, SetActivity.class);
        	    i.putExtra("alarmFlg", 1);
        	    i.putExtra("setHour", setHour1_tv.getText());
	    		i.putExtra("setMin", setMin01_tv.getText());
	    		i.putExtra("situ_et", situ1_et.getText().toString());
	    		System.out.println("==========intentで送る内容==========\n"+
	    						   "==========  Set -> Main  =========\n"+
	    						   "時刻-> "+setHour1_tv.getText().toString()+":"+setMin01_tv.getText().toString());
	    		System.out.println("場面-> "+situ1_et.getText().toString());  
	    		setResult(RESULT_OK, i);
        	    startActivityForResult( i , REQUEST_CODE );
            }
        });
	}
	
	
	/*
	 * SetActivityからデータを受け取る
	 * @see android.app.Activity#onActivityResult(int, int, android.content.Intent)
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == REQUEST_CODE) {
			if (resultCode == RESULT_OK) {
				Bundle extra = data.getExtras();
				String hour = extra.getString("setHour"); //textViewでgetTextしてるからgetIntはおかしいよね
				String min = extra.getString("setMin");
				String min0 = extra.getString("setMin0");
				String situ = extra.getString("situ_et");
				System.out.println("==========intentで受けた内容==========\n"+
								   "==========  Set -> Main   ==========\n"+
								   "時刻-> "+hour+":"+min+"\n"+
								   "場面-> "+situ);
				//画面の書き換え
				setHour1_tv.setText(hour);
				try { //TimePickerの値が入ってる場合書き換え
					Integer.valueOf(min);
					setMin1_tv.setText(min);
					setMin01_tv.setText(""+getString(R.string.min_keta, Integer.valueOf(min)));//桁合わせ
				} catch (NumberFormatException e) {
					System.out.println("Integer型に変換出来ないときここ通る");
				}					
				situ1_et.setText(situ);
				
	        }
		} else if(resultCode == RESULT_CANCELED) {
			// Handle cancel
			System.out.println("RESULT_CANCELED");
		}else{
			System.out.println("requestCode != REQUEST_CODE");
		}
	}

	
	
	/*
	 * 新規登録をさせる場合 (今は使わない)
	 *
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    // メニューの要素を追加して取得
	    MenuItem actionItem = menu.add("Set Button");	 
	    // SHOW_AS_ACTION_IF_ROOM:余裕があれば表示
	    actionItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);	 
	    // アイコンを設定
	    actionItem.setIcon(android.R.drawable.ic_input_add);	 
	    return true;
	}
	 
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    //Toast.makeText(this, "Selected Item: " + item.getTitle(), Toast.LENGTH_LONG).show();
		Toast.makeText(this, " 新規登録をします ", Toast.LENGTH_SHORT).show();
	    Intent intent = new Intent(MainActivity.this, SetActivity.class);
	    startActivity(intent);
	    return true;
	}
	*/


}
