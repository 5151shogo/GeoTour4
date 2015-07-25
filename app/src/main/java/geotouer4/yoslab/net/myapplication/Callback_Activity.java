package geotouer4.yoslab.net.myapplication;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.widget.TextView;

import twitter4j.auth.AccessToken;

public class Callback_Activity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_callback);

        AccessToken token = null;

        //Twitterの認証画面から発行されるIntentからUriを取得
        Uri uri = getIntent().getData();

        if(uri != null && uri.toString().startsWith("Callback://Callback_Activity")){
            //oauth_verifierを取得する
            String verifier = uri.getQueryParameter("oauth_verifier");
            try {
                //AccessTokenオブジェクトを取得
                System.out.println("認証しました！");
//                token = TwitterOAuthActivity._oauth.getOAuthAccessToken(TwitterOAuthActivity._req, verifier);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        TextView tv = (TextView)findViewById(R.id.textView1);
        CharSequence cs = "token：" + token.getToken() + "\r\n" + "token secret：" + token.getTokenSecret();
        tv.setText(cs);
    }

}