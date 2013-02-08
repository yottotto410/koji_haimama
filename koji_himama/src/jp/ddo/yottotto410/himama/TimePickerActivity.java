package jp.ddo.yottotto410.himama;

import java.util.Calendar;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.TimePicker;

public class TimePickerActivity extends Activity {
	AlertDialog.Builder alertDialogBuilder;
	public final static int REQUEST_CODE = 410;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_timepic);
		// TextViewインスタンスを取得(結果表示用)
		final TextView textview = (TextView) findViewById(R.id.textview);
		// カレンダーインスタンスを取得
		Calendar date = Calendar.getInstance();
		// TimePickerDialogインスタンスを生成
		TimePickerDialog timePickerDialog = new TimePickerDialog(this,
				new TimePickerDialog.OnTimeSetListener() {
					public void onTimeSet(TimePicker view, int hourOfDay,
							int minute) {
						// セットされた時刻を取得してtextviewに反映
						textview.setText(hourOfDay + ":" + minute);
						Intent timePicData = new Intent(TimePickerActivity.this, SetActivity.class);
						timePicData.putExtra("setHour",hourOfDay);
						timePicData.putExtra("setMin", minute);
						setResult(RESULT_OK, timePicData);
						System.out.println("設定された時刻="+hourOfDay+":"+minute);
						System.out.println("TimePickerActivityの終了");
						finish();
					}
				}, date.get(Calendar.HOUR_OF_DAY), date.get(Calendar.MINUTE),
				true);
		// タイトルをセット
		timePickerDialog.setTitle("じこく");
		// ダイアログを表示
		timePickerDialog.show();
	}

}
