package com.example.minaring.hackaton;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
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


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment
{
    View thisFragment;
    String accNum = "101745604587";

    public String JSON_String;
    JSONObject jsonObject;
    JSONArray jsonArray;

    public ProfileFragment()
    {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        thisFragment =  inflater.inflate(R.layout.fragment_profile2, container, false);

        getJSON();

        return thisFragment;
    }

    public class CustBGW extends AsyncTask<String, Void, String>
    {
        ProfileFragment pf;

        public CustBGW(ProfileFragment pf)
        {
            this.pf = pf;
        }

        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params)
        {
            try
            {
                //String query = params[1];
                String urlEstimate = "http://rnsantos227-001-site1.etempurl.com/apigetaccount.php";
                URL url = new URL(urlEstimate);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("GET");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);

                //OutputStream outputStream = httpURLConnection.getOutputStream();
                //BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));

                //String data = URLEncoder.encode("AccNum", "UTF-8") + "=" + URLEncoder.encode(accNum, "UTF-8");

                //Log.d("WOW", accNum);

               // bufferedWriter.write(data);
                //bufferedWriter.flush();
               // bufferedWriter.close();
                //outputStream.close();

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

                return stringBuilder.toString().trim();

            } catch (MalformedURLException e)
            {
                e.printStackTrace();
            } catch (IOException e)
            {
                e.printStackTrace();
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
            Log.d("WOW", s);
            pf.JSON_String = s;
            pf.parseJSON();
            //Toast.makeText(pf.getContext(), pf.JSON_String, Toast.LENGTH_LONG).show();
        }
    }

    public void getJSON()
    {
        CustBGW custBGW = new CustBGW(this);
        custBGW.execute();
    }

    public void parseJSON()
    {
        try
        {
            jsonObject = new JSONObject(JSON_String);
            jsonArray =  jsonObject.getJSONArray("ResultingTable");

            Log.d("WOWWOW", jsonObject.toString());

            int count = 0;

            TextView uidTextView = (TextView)thisFragment.findViewById(R.id.textViewUID);
            TextView balanceTextView = (TextView)thisFragment.findViewById(R.id.textViewBalance);
            TextView accountNameTextView = (TextView)thisFragment.findViewById(R.id.textViewFullName);
            TextView currencyTextView = (TextView)thisFragment.findViewById(R.id.textViewCurrency);
            TextView statusTextView = (TextView)thisFragment.findViewById(R.id.textViewStatus);

            Log.d("WOW", jsonObject.length() + "");

            while (count < jsonArray.length())
            {
                JSONObject jo = jsonArray.getJSONObject(count);

                uidTextView.setText("Account # : " + jo.getString("account_no"));
                balanceTextView.setText("Current Balance : " + jo.getString("current_balance"));
                accountNameTextView.setText("Account Name : " + jo.getString("account_name"));
                currencyTextView.setText("Currency" + jo.getString("currency"));
                statusTextView.setText("Status" + jo.getString("status"));

                count++;
            }
        } catch (JSONException e)
        {
            Toast.makeText(getContext(), e.toString(), Toast.LENGTH_LONG).show();

        }
    }
}
