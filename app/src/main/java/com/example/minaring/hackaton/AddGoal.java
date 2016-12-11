package com.example.minaring.hackaton;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Date;
import java.util.StringTokenizer;

public class AddGoal extends Activity
{

    String squadID;
    Button saveButton;
    EditText titleET, bodyET, budgetET;
    DatePicker datePicker;
    String goalID;

    public String JSON_String;
    JSONObject jsonObject;
    JSONArray jsonArray;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_goal);

        squadID = getIntent().getExtras().get("SquadID").toString();

        InitControls();
        InitEvents();

        reset();
    }

    public class CustBGW extends AsyncTask<String, Void, String>
    {
        AddGoal addGoal;

        public CustBGW(AddGoal addGoal)
        {
            this.addGoal = addGoal;
        }

        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params)
        {
            String method = params[0];

            if (method.equalsIgnoreCase("Save"))
            {
                String title = params[1];
                String body = params[2];
                double budget = Double.parseDouble(params[3]);
                String date = params[4];
                String status = "On Going";

                Log.d("WOW", body);

                try
                {
                    String urlEstimate = "http://rnsantos227-001-site1.etempurl.com/addGoal.php";
                    URL url = new URL(urlEstimate);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setDoInput(true);

                    OutputStream outputStream = httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));

                    String data = URLEncoder.encode("SQID", "UTF-8") + "=" + URLEncoder.encode(squadID, "UTF-8") + "&" +URLEncoder.encode("GoalID", "UTF-8") + "="
                            + URLEncoder.encode(goalID, "UTF-8") +  "&" + URLEncoder.encode("Title", "UTF-8") + "="
                            + URLEncoder.encode(title, "UTF-8") + "&" + URLEncoder.encode("Budget", "UTF-8") + "="
                            + URLEncoder.encode(Double.toString(budget), "UTF-8") + "&" + URLEncoder.encode("Body", "UTF-8") + "="
                            + URLEncoder.encode(body, "UTF-8") + "&" + URLEncoder.encode("Date", "UTF-8") + "="
                            + URLEncoder.encode(date, "UTF-8") + "&" + URLEncoder.encode("Status", "UTF-8") + "="
                            + URLEncoder.encode(status, "UTF-8");

                    bufferedWriter.write(data);
                    bufferedWriter.flush();
                    bufferedWriter.close();
                    outputStream.close();

                    InputStream inputStream = httpURLConnection.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                    StringBuilder stringBuilder = new StringBuilder();
                    String tempJSONSTRING = "";

                    while ((tempJSONSTRING = bufferedReader.readLine()) != null)
                    {
                        stringBuilder.append(tempJSONSTRING + "\n");
                    }

                    bufferedReader.close();
                    inputStream.close();
                    httpURLConnection.disconnect();

                    return stringBuilder.toString().trim() + "<3" + method;

                } catch (MalformedURLException e)
                {
                    e.printStackTrace();
                } catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
            else
            {
                try
                {
                    String urlEstimate = "http://rnsantos227-001-site1.etempurl.com/getGoalID.php";
                    URL url = new URL(urlEstimate);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setDoInput(true);

                    InputStream inputStream = httpURLConnection.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                    StringBuilder stringBuilder = new StringBuilder();
                    String tempJSONSTRING = "";

                    while ((tempJSONSTRING = bufferedReader.readLine()) != null)
                    {
                        stringBuilder.append(tempJSONSTRING + "\n");
                    }

                    bufferedReader.close();
                    inputStream.close();
                    httpURLConnection.disconnect();

                    return stringBuilder.toString().trim() + "<3" + method;

                } catch (MalformedURLException e)
                {
                    e.printStackTrace();
                } catch (IOException e)
                {
                    e.printStackTrace();
                }
            }

            return null;
        }

        @Override
        protected void onProgressUpdate(Void... values)
        {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String s)
        {
            String[] res = s.split("<3");

            if(res[1].equalsIgnoreCase("Save"))
            {
                if (s.equalsIgnoreCase("Successful!"))
                {
                    Toast.makeText(addGoal, "New Goal Saved!", Toast.LENGTH_LONG).show();
                    addGoal.reset();
                } else
                {
                    Toast.makeText(addGoal, res[0], Toast.LENGTH_LONG).show();
                }
            }
            else
            {
                addGoal.JSON_String = res[0];
                parseGoalID();
            }
        }
    }

    public void InitControls()
    {
        saveButton = (Button)findViewById(R.id.saveButton);
        datePicker = (DatePicker)findViewById(R.id.datePicker);
        titleET = (EditText)findViewById(R.id.editTextTitle);
        budgetET = (EditText)findViewById(R.id.editTextBudget);
        bodyET = (EditText)findViewById(R.id.editTextBody);
    }

    public void InitEvents()
    {
        saveButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                save();
            }
        });
    }

    public void getGoalID()
    {
        CustBGW custBGW = new CustBGW(this);
        custBGW.execute("GetGoalID");
    }

    public void parseGoalID()
    {
        try
        {
            JSONObject jsonObject = new JSONObject(JSON_String);
            jsonArray =  jsonObject.getJSONArray("ResultingTable");

            int count = 0;

            int goalIDL = 0;

            while (count < jsonArray.length())
            {
                JSONObject jo = jsonArray.getJSONObject(count);

                goalIDL = jo.getInt("Total");

                count++;
            }

            goalID = "G" + String.format("%011d", (goalIDL + 1));
        } catch (JSONException e)
        {
            e.printStackTrace();
        }
    }

    public void save()
    {
        CustBGW custBGW = new CustBGW(this);
        String date = datePicker.getYear() + "-" + (datePicker.getMonth() + 1) + "-" + datePicker.getDayOfMonth();
        custBGW.execute("Save", titleET.getText().toString(), bodyET.getText().toString(), budgetET.getText().toString(), date);

        reset();
    }

    public void reset()
    {
        titleET.setText("");
        bodyET.setText("");
        budgetET.setText("");

        getGoalID();
    }
}
