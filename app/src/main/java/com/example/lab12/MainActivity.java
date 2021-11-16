package com.example.lab12;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {
    boolean langueage=false;
    final ArrayList<String> staff= new ArrayList<>();
    ListView lvMain;
    ArrayAdapter<String> adapter;

    final Looper looper = Looper.getMainLooper();
    final Handler handler = new Handler(looper) {
        @Override
        public void handleMessage(Message msg) {
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException ie) {
                Thread.currentThread().interrupt();
            }
            Toast.makeText(getApplicationContext(), (String) msg.obj, Toast.LENGTH_SHORT).show();

        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


      //final TextView txt = (TextView) findViewById(R.id.txt);
        lvMain = (ListView) findViewById(R.id.lv);
        final EditText edText=(EditText) findViewById(R.id.edit_txt);
        Button button_lan=(Button) findViewById(R.id.Button_language);
        Button button_add=(Button) findViewById(R.id.Button_add);
        Button button_rem=(Button) findViewById(R.id.Button_remove);
        final String[] remove_Item = new String[1];

        adapter=new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, staff);
        lvMain.setAdapter(adapter);

        //ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.arr_sot, android.R.layout.simple_list_item_1);
        //vMain.setAdapter(adapter);

        button_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(edText.getText().toString()!="") {
                    staff.add(0, edText.getText().toString());
                    adapter.notifyDataSetChanged();
                    edText.setText("");
                }}
        });

        lvMain.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                remove_Item[0] =adapter.getItem(i);
            }
        });

        button_rem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adapter.remove(remove_Item[0]);
                adapter.notifyDataSetChanged();
            }
        });

        button_lan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             Runnable runnable = new Runnable() {
                 @Override
                 public void run() {
                     final Message message = Message.obtain();
                     message.obj = "This is message from Thread";
                     handler.sendMessage(message);
                 }
             };
             Thread thread = new Thread(runnable);
             thread.start();
            }
        });

    }
    @Override
    protected void onRestoreInstanceState(Bundle state){
        super.onRestoreInstanceState(state);
        staff.addAll(state.getStringArrayList("st"));
        lvMain.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
    @Override
    protected void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
        outState.putStringArrayList("st",staff);
    }
}