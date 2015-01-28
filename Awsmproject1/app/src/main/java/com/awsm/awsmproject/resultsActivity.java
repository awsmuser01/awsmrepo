package com.awsm.awsmproject;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class resultsActivity extends ActionBarActivity {
    private RecyclerView mRecyclerView;
    private MyAdapter mAdapter;
    //private RecyclerView.LayoutManager mLayoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);
        TextView tv= (TextView)findViewById(R.id.storeName);

        //Init recycler view
        mRecyclerView = (RecyclerView)findViewById(R.id.my_recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        String testStore[]={"Android","Apple","Microsoft","Android","Apple","Microsoft","Android","Apple","Microsoft"};
        mAdapter= new MyAdapter(testStore,this);
        mRecyclerView.setAdapter(mAdapter);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_results, menu);
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
