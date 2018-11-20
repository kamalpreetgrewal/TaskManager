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
 * A login screen that offers login via username and password.
 */
public class LoginActivity extends AppCompatActivity {

    // UI references.
    private EditText mUsername;
    private EditText mPassword;
    private Button mLoginButton;
    private SharedPreferences mSharedPreferences;
    private String userName, password;

    // String for saving data in shared preferences.
    public static final String PREFERENCE = "preference";
    public static final String PREF_USERNAME = "name";
    public static final String PREF_PASSWORD = "passwd";
    public static final String PREF_SKIP_LOGIN = "skip_login";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mSharedPreferences = getSharedPreferences(PREFERENCE, MODE_PRIVATE);

        /**
         * If the user has previously looged in, the Login Activity is skipped and the user
         * is presented with the activity that contains the list of tasks.
         * If the data entered by the user is valid, then it is checked if the user is a returning
         * user or a new one. If the user is new, user is registered and taken to list activity
         * otherwise the user is logged in and presented with task list activity.
         */
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
                        Toast.makeText(getApplicationContext(), "Please enter valid data.",
                                Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    /**
     * This function checks if the data i.e. username and password entered by the user
     * are valid.
     * @return
     */
    private boolean validUserData() {
        userName = mUsername.getText().toString().trim();
        password = mPassword.getText().toString().trim();
        if (isUserNameValid(userName) && isPasswordValid(password))
            return !(userName.isEmpty() || password.isEmpty());
        return false;
    }

    /**
     * This method matches the username with a regular expression to check if the username
     * entered is valid or not. If not, an appropriate error message is displayed.
     * @param username
     * @return
     */
    private boolean isUserNameValid(String username) {
        if (userName.matches("^[A-Za-z]{4,20}$")) {
            return true;
        } else {
            mUsername.setError(getString(R.string.error_invalid_email));
        }
        return false;
    }

    /**
     * This method checks if the password entered by the user meets a certain condition. If not,
     * an appropriate error message is shown to the user.
     * @param password
     * @return
     */
    private boolean isPasswordValid(String password) {
        if (password.length() > 6) {
            return true;
        } else {
            mPassword.setError(getString(R.string.error_invalid_password));
        }
        return false;
    }
}