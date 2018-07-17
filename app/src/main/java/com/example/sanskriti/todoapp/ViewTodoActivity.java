package com.example.sanskriti.todoapp;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class ViewTodoActivity extends AppCompatActivity {

    public static final String NAME_KEY="name";
    public static final String DESCRIPTION_KEY = "description";
    public static final String DATE_KEY = "date";
    public static final String TIME_KEY = "time";
    public static final String ID_KEY = "id";

    TextView name;
    TextView description;
    TextView date;
    TextView time;
    long intent_id;
    long id;
    public static final int EDIT_TODO_REQUEST_CODE=3;
    public static final int VIEW_TODO_RESULT_CODE=6;

    String desc;
    String nameS;
    String dateS;
    String timeS;

    //SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_todo);




//        NotificationManager manager = (NotificationManager)this.getSystemService(Context.NOTIFICATION_SERVICE);
//        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
//            NotificationChannel channel = new NotificationChannel("myChannelId", "Expenses Channel", NotificationManager.IMPORTANCE_HIGH);
//            manager.createNotificationChannel(channel);
//        }
//
//        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "myChannelId");
//        builder.setContentTitle("Expense Notification");
//        builder.setContentText("ABCD");
//        builder.setSmallIcon(R.drawable.ic_launcher_foreground);
//        Intent intent1 = new Intent(this, MainActivity.class);
//        PendingIntent pendingIntent = PendingIntent.getActivity(this, 2,intent1, 0);
//
//        builder.setContentIntent(pendingIntent);
//        Notification notification = builder.build();
//        manager.notify(1, notification);


        Bundle b = getIntent().getExtras();
        name= findViewById(R.id.nameTodo);
        description = findViewById(R.id.descriptionTodo);
        date = findViewById(R.id.dateTodo);
        time = findViewById(R.id.timeTodo);


        //Log.d("Abcdefg", b.getCharSequence(NAME_KEY)+"");
        name.setText("Name:- " + b.getCharSequence(NAME_KEY));

        description.setText("Description:- " + b.getCharSequence(DESCRIPTION_KEY));
        date.setText("Date:- " + b.getCharSequence(DATE_KEY));
        time.setText("Time:- " + b.getCharSequence(TIME_KEY));
        String idS = b.getCharSequence(ID_KEY)+"";
        id = Long.parseLong(idS);
        desc = b.getCharSequence(DESCRIPTION_KEY)+"";
        nameS = b.getCharSequence(NAME_KEY) + "";
        dateS = b.getCharSequence(DATE_KEY)+"";
        timeS = b.getCharSequence(TIME_KEY) + "";






    }

    public void newTodo(View view) {

        Intent intent=new Intent(getApplicationContext(), EditTodoActivity.class);


        Bundle b = new Bundle();

        b.putString(EditTodoActivity.NAME_KEY, nameS);

        b.putString(EditTodoActivity.DESCRIPTION_KEY, desc);
        b.putString(EditTodoActivity.DATE_KEY, dateS);
        b.putString(EditTodoActivity.TIME_KEY, timeS);
       // b.putString(ViewTodoActivity.ID_KEY, todo.getId()+"");
        intent.putExtras(b);
        //intent.putExtra(EditTodoActivity.ID_KEY, id);
        startActivityForResult(intent,EDIT_TODO_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==EDIT_TODO_REQUEST_CODE){
            if(resultCode==EditTodoActivity.EDIT_TODO_RESULT_CODE){
                if(data!=null){
                    String names = data.getStringExtra(AddTodoActivity.NAME_KEY);
                    String dates = data.getStringExtra(AddTodoActivity.DATE_KEY);
                    String descriptions = data.getStringExtra(AddTodoActivity.DESCRIPTION_KEY);
                    String times = data.getStringExtra(AddTodoActivity.TIME_KEY);
                    name.setText("Name:- " + names);
                    description.setText("Description:- " + descriptions);
                    date.setText("Date:- " + dates);
                    time.setText("Time:- " + times);
                    setResult(VIEW_TODO_RESULT_CODE,data);
                }
            }
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        TodoOpenHelper openHelper =new TodoOpenHelper(this);
        SQLiteDatabase database = openHelper.getReadableDatabase();
        intent_id  = intent.getLongExtra("id", 0);
        String[] selectionargs = {intent_id + ""};
        Cursor cursor = database.query(Contract.Todo.TABLE_NAME, null, Contract.Todo.COLUMN_ID + "= ?", selectionargs, null, null, null);
        while (cursor.moveToNext()){
            String names = cursor.getString(cursor.getColumnIndex(Contract.Todo.COLUMN_NAME));
            String descriptions = cursor.getString(cursor.getColumnIndex(Contract.Todo.COLUMN_DESCRIPTION));
            String dates = cursor.getString(cursor.getColumnIndex(Contract.Todo.COLUMN_DATE));
            String times = cursor.getString(cursor.getColumnIndex(Contract.Todo.COLUMN_TIME));

            name.setText("Name:- " + names);
            description.setText("Description:- " + descriptions);
            date.setText("Date:- " + dates);
            time.setText("Time:- " + times);


        }

        super.onNewIntent(intent);
    }
}
