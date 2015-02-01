package com.awsm.database;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.util.Log;

public class Query {
	String query;
	String name;
	InputStream is=null;
	String result=null;
	String line=null;
	String url="http://130.211.181.40/";

	public Query() {
		// TODO Auto-generated constructor stub
	}

	//Interface for getting response
	public interface AsyncResponse {
	    void processFinish(JSONArray output);
	}
	
	/*
	 
	 1. BrandOutlet
	 		getBrandOutlets(query) , listBrandOutlets()
	 2. Suggestions
	 		getSuggestions(query)
	 
	 
	 */
	
	//Deprecated
	public PostTask getAllBrandOutlets()
	{
		String suggestionURL = url + "getAllBrandOutlets.php";
		PostTask postTask = new PostTask(suggestionURL, query);
		postTask.execute();
		return postTask;
	}	
	
	//Deprecated
	public PostTask getSuggestionsForSearchString(String query)
	{
		String suggestionURL = url + "getSuggestionsForSearchTerm.php";
		PostTask postTask = new PostTask(suggestionURL, query);
		postTask.execute();
		return postTask;
	}
	
	//Deprecated
	public PostTask listOutletsForSearchString(String query)
	{
		String listOutletsURL = url + "listOutletsForSearchString.php";
		PostTask postTask = new PostTask(listOutletsURL, query);
		postTask.execute();
		return postTask;
	}
	
	
	public PostTask getBrandOutlets(String query)
	{
		String listOutletsURL = url + "getBrandOutlets.php";
		PostTask postTask = new PostTask(listOutletsURL, query);
		postTask.execute();
		return postTask;
	}
	
	public PostTask listBrandOutlets()
	{
		String suggestionURL = url + "listBrandOutlets.php";
		PostTask postTask = new PostTask(suggestionURL, query);
		postTask.execute();
		return postTask;
	}
	
	public PostTask getSuggestions(String query)
	{
		String suggestionURL = url + "getSuggestions.php";
		PostTask postTask = new PostTask(suggestionURL, query);
		postTask.execute();
		return postTask;
	}

	
	public class PostTask extends AsyncTask<Void, Void, JSONArray> {
		String query;
		String url;
		public AsyncResponse delegate=null;
		
		public PostTask(String url, String query) {
	        super();
	        this.query=query;
	        this.url=url;
	    }

		@Override
		protected JSONArray doInBackground(Void... params) {

			try {
				ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
				nameValuePairs.add(new BasicNameValuePair("query",query));

				try
				{
					HttpClient httpclient = new DefaultHttpClient();
					HttpPost httppost = new HttpPost(url);
					
					HttpEntity entity = new UrlEncodedFormEntity(nameValuePairs);
					httppost.addHeader(entity.getContentType());
					httppost.setEntity(entity);
					
					HttpResponse response = httpclient.execute(httppost); 
					entity = response.getEntity();
					is = entity.getContent();
					
					Log.i("pass 1", "connection success ");
										
				}
				catch(Exception e)
				{
					Log.e("Fail 1", e.toString());
				}     

				try
				{
					BufferedReader reader = new BufferedReader(new InputStreamReader(is,"iso-8859-1"),8);
					StringBuilder sb = new StringBuilder();
					while ((line = reader.readLine()) != null)
					{
						sb.append(line + "\n");
					}
					is.close();
					result = sb.toString();
					Log.i("pass 2", "connection success ");
				}
				catch(Exception e)
				{
					Log.e("Fail 2", e.toString());
				}     

				try
				{
					JSONArray json_array = new JSONArray(result);
					Log.i("JSON Array",	json_array.toString());
					
					// Sample data
					name =(json_array.getJSONObject(0).getString("name"));
					Log.i("name of value",	name.toString());

					return json_array;
				}
				catch(Exception e)
				{
					Log.e("Fail 3", e.toString());
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return null;
		}

		@Override
		protected void onPostExecute(JSONArray result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			delegate.processFinish(result);
		}
	}

}
