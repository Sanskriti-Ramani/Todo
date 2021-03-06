package com.example.sanskriti.todoapp;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

import static android.provider.Contacts.SettingsColumns.KEY;

public class EditTodoActivity extends AppCompatActivity implements View.OnClickListener {

    EditText name;
    EditText description;

    Button btnDatePicker, btnTimePicker;
    EditText txtDate, txtTime;
    private int mYear, mMonth, mDay, mHour, mMinute;
    long id;

    public static int EDIT_TODO_RESULT_CODE=5;
    public static final String NAME_KEY="name";
    public static final String DESCRIPTION_KEY = "description";
    public static final String DATE_KEY = "date";
    public static final String TIME_KEY = "time";
    public static final String ID_KEY = "id";

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_todo);

       // Intent intent = getIntent();
       // String IdS = intent.getStringExtra(ID_KEY);
       // id = Long.parseLong(IdS);


        Bundle b = getIntent().getExtras();
        String nameS = b.getCharSequence(NAME_KEY)+"";
        String desc = b.getCharSequence(DESCRIPTION_KEY)+"";
        String dateS = b.getCharSequence(DATE_KEY)+"";
        String timeS = b.getCharSequence(TIME_KEY)+"";
        btnDatePicker=(Button)findViewById(R.id.dateButton);
        btnTimePicker=(Button)findViewById(R.id.timeButton);
        txtDate=(EditText)findViewById(R.id.dateEditView);
        txtTime=(EditText)findViewById(R.id.time);

        name = (EditText)findViewById(R.id.nameEditView);
        description = (EditText)findViewById(R.id.descriptionEditView);
        Log.d("Abcdefghi", nameS);
        name.setText(nameS);
        description.setText(desc);
        txtDate.setText(dateS);
        txtTime.setText(timeS);



        btnDatePicker.setOnClickListener(this);
        btnTimePicker.setOnClickListener(this);

        sharedPreferences = getSharedPreferences("my_files", 0);;
        String nameSq = sharedPreferences.getString(nameS,null);
        Log.d("Abcdefghi", "name " + nameSq);





    }

    public void save(View view) {

        name = findViewById(R.id.nameEditView);
        description = findViewById(R.id.descriptionEditView);
        String names = name.getText().toString();
        String descriptions = description.getText().toString();
        String dates = txtDate.getText().toString();
        String times = txtTime.getText().toString();

        if (names.isEmpty() || descriptions.isEmpty() || dates.isEmpty() || times.isEmpty()) {
            Toast.makeText(EditTodoActivity.this, "Complete the details", Toast.LENGTH_LONG).show();
        } else {


            Intent data = new Intent();
            data.putExtra(NAME_KEY, names);
//                data.putExtra(ITEM_NO,item_no);
//                data.putExtra(DATE_KEY, date);
            data.putExtra(DESCRIPTION_KEY, descriptions);
            data.putExtra(DATE_KEY, dates);
            data.putExtra(TIME_KEY, times);

            setResult(EDIT_TODO_RESULT_CODE, data);
            finish();
        }
    }

    @Override
    public void onClick(View v) {
        if (v == btnDatePicker) {

            // Get Current Date
            final Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);


            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {

                            txtDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
        }
        if (v == btnTimePicker) {

            // Get Current Time
            final Calendar c = Calendar.getInstance();
            mHour = c.get(Calendar.HOUR_OF_DAY);
            mMinute = c.get(Calendar.MINUTE);

            // Launch Time Picker Dialog
            TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                    new TimePickerDialog.OnTimeSetListener() {

                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay,
                                              int minute) {

                            txtTime.setText(hourOfDay + ":" + minute);
                        }
                    }, mHour, mMinute, false);
            timePickerDialog.show();
        }
    }
    }

