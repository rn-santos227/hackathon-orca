package com.example.minaring.hackaton;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.util.DebugUtils;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
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
import java.util.ArrayList;
import java.util.zip.Inflater;


/**
 * A simple {@link Fragment} subclass.
 */
public class SquadInside extends Fragment
{
    View thisFragment;
    ListView goalsListView;
    public ProgressBar prog;
    Button addButton;
    public ArrayList<Goals> goalsArrayList;
    public ArrayList<SquadMembers> membersArrayList;
    public String squadID = "S00000000001";
    LayoutInflater inf;

    public String JSON_String;
    JSONObject jsonObject;
    JSONArray jsonArray;

    public SquadInside()
    {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        thisFragment = inflater.inflate(R.layout.fragment_squad_inside, container, false);
        inf = inflater;
        InitControls();
        InitEvents();

        prog.setVisibility(View.VISIBLE);
        getJSON("ViewGoals");

        return thisFragment;
    }

    public class CustBGW extends AsyncTask<String, Void, String> {

        SquadInside si;

        public CustBGW(SquadInside si)
        {
            this.si = si;
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

            if(method.equalsIgnoreCase("ViewGoals"))
            {
                try
                {
                    //String query = params[1];
                    String urlEstimate = "http://rnsantos227-001-site1.etempurl.com/getGoals.php";
                    URL url = new URL(urlEstimate);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setDoInput(true);

                    OutputStream outputStream = httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));

                    String data = URLEncoder.encode("SQID", "UTF-8") + "=" + URLEncoder.encode(squadID, "UTF-8");

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
                    String urlEstimate = "http://rnsantos227-001-site1.etempurl.com/getResult.php";
                    URL url = new URL(urlEstimate);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setDoInput(true);

                    OutputStream outputStream = httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));

                    String data = URLEncoder.encode("SQID", "UTF-8") + "=" + URLEncoder.encode(squadID, "UTF-8");

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

            return null;
        }

        @Override
        protected void onProgressUpdate(Void... values)
        {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String aVoid)
        {
            String[] result = aVoid.split("<3");

            //Toast.makeText(getActivity(), aVoid, Toast.LENGTH_LONG).show();
            if(result[1].equalsIgnoreCase("ViewGoals"))
            {
                si.JSON_String = result[0];
                si.populateListView();
                si.prog.setVisibility(View.INVISIBLE);
            }
        }
    }

    public void addGoal()
    {
        Intent i = new Intent(this.getContext(), AddGoal.class);
        i.putExtra("SquadID", squadID);
        startActivity(i);
    }

    public class CustAdapter extends ArrayAdapter {

        public CustAdapter(Context context, int resource)
        {
            super(context, resource);
        }

        @Override
        public int getCount()
        {
            return goalsArrayList.size();
        }

        @Override
        public Object getItem(int position)
        {
            return goalsArrayList.get(position);
        }

        @Override
        public long getItemId(int position)
        {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent)
        {
            View row;

            LayoutInflater layoutInflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = layoutInflater.inflate(R.layout.listview_layout_goals, parent, false);

            TextView title = (TextView) row.findViewById(R.id.textViewTitle);
            TextView percent = (TextView) row.findViewById(R.id.textViewPercentage);
            ProgressBar p = (ProgressBar)row.findViewById(R.id.progressBar);

            title.setText(goalsArrayList.get(position).title);
            percent.setText(goalsArrayList.get(position).getPercentage() + "%");
            p.setProgress((int) goalsArrayList.get(position).getPercentage());

            Log.d("WOW", goalsArrayList.size() + "");

            return row;
        }
    }

    public void InitControls()
    {
        goalsArrayList = new ArrayList<Goals>();
        prog = (ProgressBar)thisFragment.findViewById(R.id.progressBar2);
        goalsListView = (ListView)thisFragment.findViewById(R.id.listViewGoals);
        addButton = (Button)thisFragment.findViewById(R.id.addButton);
    }

    public void InitEvents()
    {
        addButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                addGoal();
            }
        });
    }

    public void getJSON(String method)
    {
        CustBGW custBGW = new CustBGW(this);
        custBGW.execute(method);
    }

    public void inflateMembers(String name)
    {
        LayoutInflater inflater = inf;
        LinearLayout linearLayout = (LinearLayout)thisFragment.findViewById(R.id.linearLayoutForMembers);

        TextView image = (TextView) inflater.inflate(R.layout.memberimage, null);
        image.setText(name);
        linearLayout.addView(image);
    }

    public void populateListView()
    {
        try
        {
            jsonObject = new JSONObject(JSON_String);
            jsonArray =  jsonObject.getJSONArray("ResultingTable");

            int count = 0;

            String goalID, squadID, title, body, date;
            double currBalance, budget;

            while (count < jsonArray.length())
            {
                JSONObject jo = jsonArray.getJSONObject(count);

                goalID = jo.getString("GoalID");
                squadID = jo.getString("SquadID");
                date = jo.getString("TargetDate");
                title = jo.getString("Title");
                body = jo.getString("Body");
                currBalance = jo.getDouble("CurrentBalance");
                budget = jo.getDouble("Budget");

                Goals g = new Goals(title, body, squadID, goalID, date, currBalance, budget);

                goalsArrayList.add(g);

                count++;
            }
        } catch (JSONException e)
        {
            e.printStackTrace();
        }

        goalsListView.setAdapter(new CustAdapter(this.getContext(), R.layout.listview_layout_goals));
    }

    public void getMembers()
    {
        try
        {
            jsonObject = new JSONObject(JSON_String);
            jsonArray =  jsonObject.getJSONArray("ResultingTable");

            int count = 0;

            String name, uid, encString;


            while (count < jsonArray.length())
            {
                JSONObject jo = jsonArray.getJSONObject(count);

                name = jo.getString("FirstName");
                uid = jo.getString("UserID");
                encString = jo.getString("Picture");

                SquadMembers g = new SquadMembers(name, uid, encString);

                membersArrayList.add(g);

                count++;
            }

            for(SquadMembers sm : membersArrayList)
            {
                inflateMembers(sm.getName());
            }
        } catch (JSONException e)
        {
            e.printStackTrace();
        }

    }

    public Bitmap StringToBitMap(String encodedString){
        try {
            byte [] encodeByte= Base64.decode(encodedString,Base64.DEFAULT);
            Bitmap bitmap= BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        } catch(Exception e) {
            e.getMessage();
            return null;
        }
    }

}
