package com.example.kamalpreetgrewal.taskmanager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {

    // UI references.
    private EditText mUsername;
    private EditText mPassword;
    private Button mLoginButton;
    private SharedPreferences mSharedPreferences;
    private String userName, password;

    public static final String PREFERENCE = "preference";
    public static final String PREF_USERNAME = "name";
    public static final String PREF_PASSWORD = "passwd";
    public static final String PREF_SKIP_LOGIN = "skip_login";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mSharedPreferences = getSharedPreferences(PREFERENCE, MODE_PRIVATE);

        if (mSharedPreferences.contains(PREF_SKIP_LOGIN)) {
            Intent intent = new Intent(LoginActivity.this, TaskListActivity.class);
            startActivity(intent);
            finish();
        } else {
            mUsername = (EditText) findViewById(R.id.email);
            mPassword = (EditText) findViewById(R.id.password);
            mLoginButton = (Button) findViewById(R.id.email_sign_in_button);
            mLoginButton.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (validUserData()) {
                        if (mSharedPreferences.contains(PREF_USERNAME) &&
                                mSharedPreferences.contains(PREF_PASSWORD)) {
                            SharedPreferences.Editor editor = mSharedPreferences.edit();
                            editor.putString(PREF_SKIP_LOGIN, "skip");
                            editor.apply();
                            Intent intent = new Intent(LoginActivity.this, TaskListActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            SharedPreferences mSharedPreference = getSharedPreferences(PREFERENCE,
                                    MODE_PRIVATE);
                            SharedPreferences.Editor editor = mSharedPreference.edit();
                            editor.putString(PREF_USERNAME, userName);
                            editor.putString(PREF_PASSWORD, password);
                            editor.apply();
                            Intent intent = new Intent(LoginActivity.this, TaskListActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "Please enter valid data.", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    private boolean validUserData() {
        userName = mUsername.getText().toString().trim();
        password = mPassword.getText().toString().trim();
        if (isUserNameValid(userName) && isPasswordValid(password))
            return !(userName.isEmpty() || password.isEmpty());
        return false;
    }

    private boolean isUserNameValid(String username) {
        if (userName.matches("^[A-Za-z]{4,20}$")) {
            return true;
        } else {
            mUsername.setError(getString(R.string.error_invalid_email));
        }
        return false;
    }

    private boolean isPasswordValid(String password) {
        if (password.length() > 6) {
            return true;
        } else {
            mPassword.setError(getString(R.string.error_invalid_password));
        }
        return false;
    }
}