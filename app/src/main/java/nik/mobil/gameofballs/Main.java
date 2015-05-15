package nik.mobil.gameofballs;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.File;
import java.io.InputStream;


public class Main extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        File file=new File(getFilesDir(),"score.xml");
        Score.AddFile(file);
    }

    public void exit(View v) {
        finish();
    }

    public void openHelp(View view){
        Intent intent = new Intent(this, Help_Activity.class);
        startActivity(intent);
    }

    public void openGame(View view){
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("Kérlek adj meg egy nevet!");
        final EditText input = new EditText(this);

        input.setInputType(InputType.TYPE_CLASS_TEXT);

        builder.setView(input);

        builder.setPositiveButton("OK",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String data2;
                if(!(data2=input.getText().toString()).isEmpty())
                {
                    Intent intent = new Intent(Main.this, LevelSelect_Activity.class);
                    intent.putExtra("name",data2);
                    startActivity(intent);
                }
            }
        });

        builder.show();

    }

    public void openScore(View view){
        Intent intent = new Intent(this, Score_Activity.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
}
