package jp.ddo.yottotto410.himama;

import java.io.IOException;

import android.app.Activity;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AlmExeActivity extends Activity {
    private MediaPlayer player;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_almexe);
		/*
		 * intent取得
		 */
        Bundle bundle = getIntent().getExtras();
        Uri uri = (Uri) bundle.get(SetActivity.URI);
        // アラーム音がサイレントの場合はデフォルトアラーム音
        if (uri == null) {
            uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
        }
        player = MediaPlayer.create(this, uri);
        //player.setLooping(true);
        try {
			player.prepare();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("prepare Err");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("prepare Err");

		}
        player.start();
        /*
         * もういっかいbutton
         */
        Button more_btn = (Button)this.findViewById(R.id.more);
        more_btn.setOnClickListener(new View.OnClickListener() {
        	@Override
            public void onClick(View v) {
                player.start();
            }
        });
        /*
         * だいじょうぶbutton
         */
        Button ok_btn = (Button)this.findViewById(R.id.ok);
        ok_btn.setOnClickListener(new View.OnClickListener() {
        	@Override
            public void onClick(View v) {
        		player.release();
        		finish();
            }
        });

	}
}