package phamkien.login;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Register extends AppCompatActivity implements View.OnClickListener {

    private EditText editName;
    private EditText editPassword;
    private EditText editEmail;
    private AppCompatButton btnBirthday;
    private RadioButton radMale;
    private RadioButton radFemale;
    private AppCompatButton btnRes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        connectView();
    }

    private void connectView() {
        editName = (EditText) findViewById(R.id.editName);
        editEmail = (EditText) findViewById(R.id.editEmail);
        editPassword = (EditText) findViewById(R.id.editPassword);

        radFemale = (RadioButton) findViewById(R.id.radFemale);
        radMale = (RadioButton) findViewById(R.id.radMale);

        btnBirthday = (AppCompatButton) findViewById(R.id.btnBirthday);
        btnRes = (AppCompatButton) findViewById(R.id.btnRes);

        btnBirthday.setOnClickListener(this);
        btnRes.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnBirthday:
                selectDoB();
                break;
            case R.id.btnRes:
                register();
                break;
        }
    }

    private void register() {
        // get data
        String name = editName.getText().toString().trim();
        String password = editPassword.getText().toString().trim();
        String email = editEmail.getText().toString().trim();
        String birthDay = btnBirthday.getText().toString().trim();
        String sex = radMale.isChecked() ? this.getResources().getString(R.string.male) : this.getResources().getString(R.string.female);
        // check data
        if (TextUtils.isEmpty(name)) {
            editName.requestFocus();
            Toast.makeText(this, getString(R.string.requiredName), Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            editPassword.requestFocus();
            Toast.makeText(this, getString(R.string.requiredPassword), Toast.LENGTH_SHORT).show();
            return;
        }
        if (password.length() < 6) {
            editPassword.requestFocus();
            Toast.makeText(this, getString(R.string.requiredPasswordLength), Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(email)) {
            editEmail.requestFocus();
            Toast.makeText(this, getString(R.string.requiredEmail), Toast.LENGTH_SHORT).show();
            return;
        }
        if (birthDay.equals(getString(R.string.dob))) {
            Toast.makeText(this, getString(R.string.requiredDoB), Toast.LENGTH_SHORT).show();
            return;
        }

        // if all is ok, we process data and show dialog to notify
        String notifyThanks = this.getResources().getString(R.string.notify_thanks);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.success)
                .setMessage(notifyThanks)
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // do something
                    }
                })
                .create()
                .show();

        Intent intent = new Intent(this, Playlist.class);
        intent.putExtra("name" ,editName.getText().toString());
        startActivity(intent);
    }

    private void selectDoB() {
        int defaultYear = 1990;
        int defaultMonth = 0; // month is counted from 0 in Java!
        int defaultDate = 1;
        // create and setting Datapicker
        DatePickerDialog dpd = DatePickerDialog.newInstance(
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {

                    Calendar calendar = Calendar.getInstance();
                    // set select date for calendar
                    calendar.set(year, monthOfYear, dayOfMonth);
                    // config string format to show date
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                    // set date for button birthday
                    btnBirthday.setText(sdf.format(calendar.getTime()));
                    }
                },
                defaultYear,
                defaultMonth,
                defaultDate
        );
        dpd.setOkText(this.getResources().getString(R.string.ok));
        dpd.setCancelText(this.getResources().getString(R.string.cancel));

        // show datepicker
        dpd.show(getFragmentManager(), "DatePickerDialog");
    }
}
