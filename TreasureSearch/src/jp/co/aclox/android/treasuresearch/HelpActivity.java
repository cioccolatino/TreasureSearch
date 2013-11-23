package jp.co.aclox.android.treasuresearch;

import android.app.Activity;
import android.os.Bundle;
import android.content.pm.PackageManager;
import android.content.pm.PackageInfo;
import android.widget.TextView;

public class HelpActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.help);

        PackageManager pm = getPackageManager();
		String versionName = "";
        try {
    		PackageInfo info = pm.getPackageInfo(getPackageName(), PackageManager.GET_META_DATA);
    		versionName += info.versionName;
    	} catch (PackageManager.NameNotFoundException e) {
    		versionName = "0.0.0";
    	}

    	TextView textVersionName = (TextView)findViewById(R.id.textVersionName);
    	textVersionName.setText("Ver " + versionName);
    }

}
