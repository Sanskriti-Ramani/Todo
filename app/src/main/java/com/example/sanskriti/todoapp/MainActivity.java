package com.example.sanskriti.todoapp;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import static com.example.sanskriti.todoapp.Contract.Todo.COLUMN_NAME;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemLongClickListener,
        AdapterView.OnItemClickListener{

    ArrayList<Todo> todos = new ArrayList<>();


    Todo todo;
    TodoAdaptor adaptor;
    public  static  int ADD_REQUEST_CODE = 1;
    public static int ADD_RESULTCODE = 2;
    public static int VIEW_TODO_REQUEST_CODE = 3;
    public int pos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d("MainActivity", "onCreate");
        ListView listview = findViewById(R.id.listView);

        TodoOpenHelper  openHelper = TodoOpenHelper.getInstance(getApplicationContext());
        SQLiteDatabase database=openHelper.getReadableDatabase();



        Cursor cursor= database.query(Contract.Todo.TABLE_NAME,null,null,null,null,null,null);

        while(cursor.moveToNext()){
            String name= cursor.getString(cursor.getColumnIndex(COLUMN_NAME));
            String description = cursor.getString(cursor.getColumnIndex(Contract.Todo.COLUMN_DESCRIPTION));

            String date = cursor.getString(cursor.getColumnIndex(Contract.Todo.COLUMN_DATE));
            String time = cursor.getString(cursor.getColumnIndex(Contract.Todo.COLUMN_TIME));
            long id=cursor.getLong(cursor.getColumnIndex(Contract.Todo.COLUMN_ID));

            Todo todo= new Todo(name, description, date, time);
            todo.setId(id);
            todos.add(todo);

        }
        cursor.close();

        adaptor = new TodoAdaptor(this, todos);
        listview.setAdapter(adaptor);

        listview.setOnItemLongClickListener(this);
        listview.setOnItemClickListener(this);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        if(id==R.id.add){
            Intent intent=new Intent(this, AddTodoActivity.class);
            startActivityForResult(intent,ADD_REQUEST_CODE);
        }
        else if(id == R.id.name){

            Collections.sort(todos, new Comparator() {
                @Override
                public int compare(Object o1, Object o2) {
                    Todo t1 = (Todo) o1;
                    Todo t2 = (Todo) o2;
                    return t1.getName().compareToIgnoreCase(t2.getName());
                }
            });

            adaptor.notifyDataSetChanged();

            TodoOpenHelper  openHelper = TodoOpenHelper.getInstance(getApplicationContext());
            SQLiteDatabase database= openHelper.getWritableDatabase();
            Cursor cursor = database.query(Contract.Todo.TABLE_NAME, null, null, null, null, null, COLUMN_NAME + " ASC");

        }

        else if(id == R.id.date){
            Collections.sort(todos, new Comparator() {
                @Override
                public int compare(Object o1, Object o2) {
                    Todo t1 = (Todo) o1;
                    Todo t2 = (Todo) o2;

                    return (t1.getDate()+"").compareToIgnoreCase(t2.getDate()+"");
                }
            });

            adaptor.notifyDataSetChanged();
        }
        else if(id == R.id.time){
            Collections.sort(todos, new Comparator() {
                @Override
                public int compare(Object o1, Object o2) {
                    Todo t1 = (Todo) o1;
                    Todo t2 = (Todo) o2;

                    return (t1.getTime()).compareToIgnoreCase(t2.getTime());
                }
            });

            adaptor.notifyDataSetChanged();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode==ADD_REQUEST_CODE) {
            if (resultCode == AddTodoActivity.ADD_TODO_RESULT_CODE) {

                if (data != null) {
                    String name = data.getStringExtra(AddTodoActivity.NAME_KEY);
                    String date = data.getStringExtra(AddTodoActivity.DATE_KEY);
                    String description = data.getStringExtra(AddTodoActivity.DESCRIPTION_KEY);
                    String time = data.getStringExtra(AddTodoActivity.TIME_KEY);
                    //int val = Integer.parseInt(item);
                    //   String date= data.getStringExtra(AddTodoActivity.DATE_KEY);
                    Todo todo = new Todo(name, description, date, time);

                    TodoOpenHelper openHelper = TodoOpenHelper.getInstance(getApplicationContext());
                    SQLiteDatabase database = openHelper.getWritableDatabase();

                    ContentValues contentValues = new ContentValues();
                    contentValues.put(COLUMN_NAME, todo.getName());
                    contentValues.put(Contract.Todo.COLUMN_DESCRIPTION, todo.getDescription());
                    contentValues.put(Contract.Todo.COLUMN_DATE, todo.getDate());
                    contentValues.put(Contract.Todo.COLUMN_TIME, todo.getTime());

                    long id = database.insert(Contract.Todo.TABLE_NAME, null, contentValues);
                    AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
                    Intent intent = new Intent(this, MyReceiver.class);
                    intent.putExtra("intent_id", id);
                    PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 1, intent, 0);
                    long currentTime = System.currentTimeMillis();

                    alarmManager.set(AlarmManager.RTC_WAKEUP, currentTime+5*1000L, pendingIntent);
                    if (id > -1) {
                        todo.setId(id);
                        todos.add(todo);
                        adaptor.notifyDataSetChanged();
                    }
                }

            }
        }
        else if(requestCode == VIEW_TODO_REQUEST_CODE)
        {

            if(resultCode==ViewTodoActivity.VIEW_TODO_RESULT_CODE){
                Todo todo =todos.get(pos);
                if(data!=null) {
                    String name = data.getStringExtra(AddTodoActivity.NAME_KEY);
                    String date = data.getStringExtra(AddTodoActivity.DATE_KEY);
                    String description = data.getStringExtra(AddTodoActivity.DESCRIPTION_KEY);
                    String time = data.getStringExtra(AddTodoActivity.TIME_KEY);

                    if(!name.isEmpty()){
                        todo.setName(name);

                        todo.setDate(date);
                        todo.setDescription(description);
                        todo.setTime(time);


                        todos.set(pos,todo);
                        adaptor.notifyDataSetChanged();
                    }

                }

                }
            }



        super.onActivityResult(requestCode, resultCode, data);
    }





    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

        pos=i;
        final Todo todo = todos.get(i);
        Intent intent = new Intent(getApplicationContext(), ViewTodoActivity.class);
        Bundle b = new Bundle();

        b.putString(ViewTodoActivity.NAME_KEY, todo.getName());

        b.putString(ViewTodoActivity.DESCRIPTION_KEY, todo.getDescription() + "");
        b.putString(ViewTodoActivity.DATE_KEY, todo.getDate());
        b.putString(ViewTodoActivity.TIME_KEY, todo.getTime());
        b.putString(ViewTodoActivity.ID_KEY, todo.getId()+"");
        intent.putExtras(b);
        //startActivity(intent);
        startActivityForResult(intent,VIEW_TODO_REQUEST_CODE);

    }



    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
        final Todo todo = todos.get(i);


        final int position = i;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirm Delete");
        builder.setCancelable(false);
        builder.setMessage("Do you really want to delete?");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                //Removing from db
                TodoOpenHelper openHelper= TodoOpenHelper.getInstance(getApplicationContext());
                SQLiteDatabase database= openHelper.getWritableDatabase();
                long id=todo.getId();
                String[] selectionArgs={id + ""};
                database.delete(Contract.Todo.TABLE_NAME, Contract.Todo.COLUMN_ID + " = ? ", selectionArgs);

                //Removing from list
                todos.remove(position);
                adaptor.notifyDataSetChanged();

                Toast.makeText(MainActivity.this, "Ok Pressed", Toast.LENGTH_LONG).show();
                //  Toast.makeText(MainActivity.this,todo.get()+" removed.",Toast.LENGTH_LONG).show();
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //TODO
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
        return true;

    }
}
