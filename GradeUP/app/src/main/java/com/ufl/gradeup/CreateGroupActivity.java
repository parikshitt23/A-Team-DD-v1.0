package com.ufl.gradeup;

import android.app.ListActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;



public class CreateGroupActivity extends AppCompatActivity {

    private android.support.v7.widget.Toolbar toolbar;
    EditText groupName;
    EditText addGroupMembers;
    ListView groupMembersListView;
    List<String> groupMemebers = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_group);

        toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);

        groupName = (EditText)findViewById(R.id.groupNameTxt);
        addGroupMembers = (EditText)findViewById(R.id.addGroupMemberTxt);
        groupMembersListView = (ListView)findViewById(R.id.memberListView);
        final CreateGroupCustomAdapter adapter =  new CreateGroupCustomAdapter(groupMemebers,this);




        addGroupMembers.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                final int DRAWABLE_RIGHT = 2;


                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= (addGroupMembers.getRight() - addGroupMembers.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        ParseQuery<ParseUser> query = ParseUser.getQuery();
                        query.whereEqualTo("username", addGroupMembers.getText().toString());
                        query.findInBackground(new FindCallback<ParseUser>() {
                            public void done(List<ParseUser> objects, ParseException e) {
                                if (e == null) {
                                    for (ParseUser object : objects
                                            ) {
                                        Toast.makeText(getApplicationContext(),
                                                object.getString("Name"),
                                                Toast.LENGTH_LONG).show();
                                        if (!groupMemebers.contains(object.getUsername())) {
                                            groupMemebers.add(object.getUsername());
                                        }
                                    }
                                    adapter.notifyDataSetChanged();
//                                    Toast.makeText(getApplicationContext(),
//                                            objects.getString(),
//                                            Toast.LENGTH_LONG).show();
                                } else {
                                    // Something went wrong.
                                }
                            }
                        });
                        return true;
                    }
                }
                return false;
            }
        });
//        adapter = new ArrayAdapter<String>(CreateGroupActivity.this, android.R.layout.simple_expandable_list_item_1, groupMemebers);
        groupMembersListView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                return false;
            }
        });

        groupMembersListView.setAdapter(adapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_create_group, menu);
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
