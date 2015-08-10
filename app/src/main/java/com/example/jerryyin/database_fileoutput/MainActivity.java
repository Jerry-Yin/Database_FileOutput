package com.example.jerryyin.database_fileoutput;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;


public class MainActivity extends ActionBarActivity {

    private EditText mEtInput;

    private String inputText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        ReLoadData();
    }

    public void initViews() {
        mEtInput = (EditText) findViewById(R.id.et_input);
    }

    public void ReLoadData(){
        FileInputStream inputStream = null;
        BufferedReader reader = null;
        StringBuilder content = new StringBuilder();
        try {
            inputStream = openFileInput("TestData");
            reader = new BufferedReader(new InputStreamReader(inputStream));
            String line = "";
            while ( (line = reader.readLine()) != null ){
                content.append(line);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                if (reader != null){
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (!TextUtils.isEmpty(content)){
            mEtInput.setText(content);
            mEtInput.setSelection(content.length());    //光标移动到句末
            Toast.makeText(this, "Reload successful !!!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        inputText = mEtInput.getText().toString();
        SaveInput(inputText);
    }

    public void SaveInput(String in) {
        FileOutputStream out = null;
        BufferedWriter writer = null;
        try {
            out = openFileOutput("TestData", MODE_PRIVATE);
            writer = new BufferedWriter(new OutputStreamWriter(out));
            writer.write(in);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (writer != null) {
                    writer.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}
