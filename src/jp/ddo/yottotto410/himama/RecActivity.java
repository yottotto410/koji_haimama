package jp.ddo.yottotto410.himama;

import java.io.File;
import java.io.IOException;

import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RecActivity extends Activity {
	public final static int REQUEST_CODE_REC = 411;

	String filePath;
    EditText et;
    MediaRecorder recorder;
    File audioFile;

    Button start_btn;
    Button stop_btn;
    Button playButton;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rec);

        start_btn = (Button) findViewById(R.id.rec_btn);
        stop_btn = (Button) findViewById(R.id.stop_btn);
        stop_btn.setEnabled(false);
        System.out.println("onCreate");
        et = (EditText) findViewById(R.id.title_et);
        
        start_btn.setOnClickListener(new View.OnClickListener() {
        	@Override
            public void onClick(View v) {
        		start_btn.setEnabled(false);
                stop_btn.setEnabled(true);
                
                File file = new File(Environment.getExternalStorageDirectory().getPath()+"/himama/");
                if(!file.exists()){
                    file.mkdir();
                }
                filePath = file.getAbsolutePath()+"/"+et.getText().toString()+ ".3gp";
                
                recorder = new MediaRecorder();
                recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
                recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
                recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
                recorder.setOutputFile(filePath);
                try {
					recorder.prepare();
				} catch (IOException e) {
					System.out.println("レコーダ準備失敗");
				}
                recorder.start();
                System.out.println("録音開始");
                Toast.makeText(RecActivity.this, "「"+ et.getText() +"」をろくおんします", Toast.LENGTH_SHORT).show();                
            }
        });    
        stop_btn.setOnClickListener(new View.OnClickListener() {
        	@Override
        	public void onClick(View v) {
        		start_btn.setEnabled(true);
        		stop_btn.setEnabled(false);
        		recorder.stop();
        		Toast.makeText(RecActivity.this, "「"+ et.getText() +"」をほぞんしました", Toast.LENGTH_SHORT).show();                
        		recorder.reset();
        		recorder.release();
         		recorder = null;
        		//Rec -> Setに送るデータ
        		Intent recData = new Intent(RecActivity.this, SetActivity.class);
				recData.putExtra("URI", filePath);
				setResult(RESULT_OK, recData);
				System.out.println("URI -> "+filePath);
				System.out.println("RecActivityの終了");
				finish();
        	}
        });
    }
}