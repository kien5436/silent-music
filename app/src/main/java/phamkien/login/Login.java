package phamkien.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Hannibal Lecter on 01/19/19.
 */

public class Login extends AppCompatActivity implements View.OnClickListener {

    private EditText editUsername;
    private EditText editPassword;
    private TextView tvForgot;
    private TextView tvRes;
    private AppCompatButton btnLogin;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        connectView();
    }

    private void connectView() {
        editUsername = (EditText) findViewById(R.id.editUsername);
        editPassword = (EditText) findViewById(R.id.editPassword);
        tvForgot = (TextView) findViewById(R.id.tvForgot);
        tvRes = (TextView) findViewById(R.id.tvRes);
        btnLogin = (AppCompatButton) findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(this);
        tvForgot.setOnClickListener(this);
        tvRes.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnLogin:
                login();
                break;
            case R.id.tvForgot:
                forgotPassword();
                break;
            case R.id.tvRes:
                Intent intent = new Intent(this, Register.class);
                startActivity(intent);
                break;
        }
    }

    private void forgotPassword() {
    }

    private void login() {
        String username = editUsername.getText().toString().trim();
        String password = editPassword.getText().toString().trim();

//        if (!username.equals("Kien") || !password.equals("123456")) {
//            Toast.makeText(this, R.string.invalid_account, Toast.LENGTH_SHORT).show();
//            return;
//        }

        Intent intent = new Intent(this, Playlist.class);
        startActivity(intent);
    }
}
