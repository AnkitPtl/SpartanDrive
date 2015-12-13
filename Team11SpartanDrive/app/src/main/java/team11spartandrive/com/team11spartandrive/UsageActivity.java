package team11spartandrive.com.team11spartandrive;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class UsageActivity extends AppCompatActivity {

    TextView uname;
    TextView mail;
    TextView totalUsedSpacev;
    TextView totalFreeSpacev;
    ImageView imageview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usage);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        String username= bundle.getString("name");
        String useremail= bundle.getString("mail");
        String totalUsedSpace= bundle.getString("totalUsedSpace");
        String totalFreeSpace= bundle.getString("totalFreeSpace");
        String url = bundle.getString("imageUrl");

        uname= (TextView) findViewById(R.id.uname);
        mail= (TextView) findViewById(R.id.umail);
        totalUsedSpacev= (TextView) findViewById(R.id.totalUsedSpace);
        totalFreeSpacev= (TextView) findViewById(R.id.totalFreeSpace);
        imageview = (ImageView)  findViewById(R.id.imgV);

        uname.setText("Name: "+username);
        mail.setText("Email: "+useremail);
        totalUsedSpacev.setText("Total Used Space: "+totalUsedSpace +"GB");
        totalFreeSpacev.setText("Total Free Space: "+totalFreeSpace+"GB");
        new MyAsyncTask().execute(url);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_usage, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



    /*public static Bitmap getBitmapFromURL(String src) {
        try {
            Log.e("src", src);
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            Log.e("Bitmap","returned");
            return myBitmap;
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("Exception",e.getMessage());
            return null;
        }
    }
*/
    public class MyAsyncTask extends AsyncTask<String,Void,Bitmap>{

        @Override
        protected Bitmap doInBackground(String... params) {

            try {
                Log.e("src", params[0]);
                URL url = new URL(params[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream input = connection.getInputStream();
                Bitmap myBitmap = BitmapFactory.decodeStream(input);
                Log.e("Bitmap","returned");

                return myBitmap;
            } catch (IOException e) {
                e.printStackTrace();
                Log.e("Exception",e.getMessage());
                return null;
            }

        }

        @Override
        protected void onPostExecute(Bitmap result) {
            super.onPostExecute(result);
            imageview.setImageBitmap(result);
        }

    }

}

