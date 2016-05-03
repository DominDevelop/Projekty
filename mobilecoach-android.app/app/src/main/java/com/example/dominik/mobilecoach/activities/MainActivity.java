package com.example.dominik.mobilecoach.activities;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.DrawerLayout;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.dominik.mobilecoach.R;
import com.example.dominik.mobilecoach.fragments.FragmentHistory;
import com.example.dominik.mobilecoach.fragments.FragmentPersonDetails;
import com.example.dominik.mobilecoach.fragments.FragmentPlan;
import com.example.dominik.mobilecoach.fragments.FragmentMain;
import com.example.dominik.mobilecoach.other.MyAdapter;

import static com.example.dominik.mobilecoach.other.TransactionManager.changeView;

public class MainActivity extends AppCompatActivity {

    private ListView listView;
    private DrawerLayout drawerLayout;
    public static FragmentManager fm;
    public static Activity activity;

    public static android.support.v7.app.ActionBar appActionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.container, new FragmentMain());
        fragmentTransaction.commit();
        fm = fragmentManager;
        activity = this;

        appActionBar = getSupportActionBar();
        appActionBar.setTitle(R.string.app_name);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        listView = (ListView) findViewById(R.id.drawer_list);
        int[] images = {R.mipmap.training_icon,R.mipmap.plan_icon,R.mipmap.history_icon,R.mipmap.healf_icon};

        String[] list = getResources().getStringArray(R.array.menu_items);

        MyAdapter arrayAdapter = new MyAdapter(getApplicationContext(),android.R.layout.simple_list_item_1,list,images);
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                switch (position) {
                    case 0:
                        Intent intent = new Intent(getApplicationContext(),TrainingActivity.class);
                        drawerLayout.closeDrawer(Gravity.LEFT);
                        startActivity(intent);
                        break;

                    case 1:
                        changeView(fragmentManager, new FragmentPlan(), "Plan");
                        drawerLayout.closeDrawer(Gravity.LEFT);
                        break;

                    case 2:
                        changeView(fragmentManager, new FragmentHistory(), "History");
                        drawerLayout.closeDrawer(Gravity.LEFT);
                        break;

                    case 3:
                        changeView(fragmentManager, new FragmentPersonDetails(), "Details");
                        drawerLayout.closeDrawer(Gravity.LEFT);
                        break;
                }
            }
        });
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

            changeView(fm, new FragmentPersonDetails(), "Details");
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {

        /*
        if(getSupportFragmentManager().getBackStackEntryCount() != 0){
            //getSupportFragmentManager().popBackStack();
        }else {
            super.onBackPressed();
        }*/

        Intent startMain = new Intent(Intent.ACTION_MAIN);
        startMain.addCategory(Intent.CATEGORY_HOME);
        startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(startMain);
    }
}
