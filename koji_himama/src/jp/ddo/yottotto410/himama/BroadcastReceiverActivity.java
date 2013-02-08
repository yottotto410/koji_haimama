package jp.ddo.yottotto410.himama;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

// ブロードキャストレシーバー
public class BroadcastReceiverActivity extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
    	System.out.println("BR onReceive");
        // インテントの取得
        Bundle bundle = intent.getExtras();
        // 選択されたアラーム音情報取得
        Uri uri = (Uri) bundle.get(SetActivity.URI);

        // インテントの生成
        Intent newIntent = new Intent(context, AlmExeActivity.class);
        newIntent.putExtra(SetActivity.URI, uri);

        // アクティビティ以外からアクティビティを呼び出すための設定
        newIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        // アクティビティ開始
        context.startActivity(newIntent);
    }
}
