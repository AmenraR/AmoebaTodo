package android.amoeba.todo;

import android.app.Activity;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.content.Intent;
import android.widget.Button;

import org.json.JSONObject;


public class NewItemActivity extends Activity {
    protected Button saveItemButton;
    public static String title;
    public static String reminder;
    public static String date;
    public static String time;
    private static JSONObject jsonTask;
    private static Task task;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newitem);
        saveItemButton = (Button) findViewById(R.id.saveItemButton);
        saveItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final EditText titleData = (EditText) findViewById(R.id.title);
                title = titleData.getText().toString();

                if (title == "") {
                    title = null;
                }

                final EditText reminderData = (EditText) findViewById(R.id.reminder);
                reminder = reminderData.getText().toString();

                if (reminder == "") {
                    reminder = null;
                }

                final Button timeData = (Button) findViewById(R.id.button);
                time = timeData.getText().toString();

                if (time == "Select Time") {
                    time = null;
                }

                final Button dateData = (Button) findViewById(R.id.button2);
                date = dateData.getText().toString();

                if (date == "Select Date") {
                    date = null;
                }
                Intent myIntent = new Intent(getApplicationContext(), TodoActivity.class);

                myIntent.putExtra("title", titleData.getText().toString());
                myIntent.putExtra("reminder", reminderData.getText().toString());
                myIntent.putExtra("time", timeData.getText().toString());
                myIntent.putExtra("date", dateData.getText().toString());

                startActivity(myIntent);

                if (date == "Select Date") {
                    date = null;
                }
                task = new Task(title, reminder, date, time);
                PostActivity.get().add(task);
                jsonTask = JsonUtil.toJSon(task);

                Intent returnIntent = new Intent();
                returnIntent.putExtra("jsonTask", jsonTask.toString());
                setResult(RESULT_OK, returnIntent);
                finish();
            }
        });
    }

    public void showTimePickerDialog(View v) {
        DialogFragment newFragment = new TimePickerFragment();
        newFragment.show(getFragmentManager(), "timePicker");
    }

    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getFragmentManager(), "datePicker");
    }
}
