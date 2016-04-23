package com.kitkat.wisatabudaya;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.kitkat.wisatabudaya.database.DatabaseHandler;
import com.kitkat.wisatabudaya.model.User;
import com.kitkat.wisatabudaya.utils.Validator;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import butterknife.OnFocusChange;
import butterknife.OnTextChanged;

public class RegisterActivity extends AppCompatActivity {

    @Bind(R.id.register_editxt_firstname) EditText firstNameET;
    @Bind(R.id.register_editxt_lastname) EditText lastNameET;
    @Bind(R.id.register_editxt_email) EditText emailET;
    @Bind(R.id.register_editxt_phone_num) EditText phoneNumET;
    @Bind(R.id.register_editxt_password) EditText passwordET;
    @Bind(R.id.register_editxt_conf_password) EditText confPasswordET;
    @Bind(R.id.register_checkbox_agree) CheckBox agreeCheckBox;
    @Bind(R.id.register_btn_sign_up) Button signupBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
    }

    @OnTextChanged(
            {
                    R.id.register_editxt_firstname,
                    R.id.register_editxt_lastname,
                    R.id.register_editxt_email,
                    R.id.register_editxt_phone_num,
                    R.id.register_editxt_password,
                    R.id.register_editxt_conf_password,
                    R.id.register_checkbox_agree
            })
    @OnCheckedChanged(R.id.register_checkbox_agree)
    public void isAllFormFilled(){
        boolean isFilled = firstNameET.getText().length() > 0 &&
                lastNameET.getText().length() > 0 &&
                emailET.getText().length() > 0 &&
                phoneNumET.getText().length() > 0 &&
                passwordET.getText().length() > 0 &&
                confPasswordET.getText().length() > 0 &&
                agreeCheckBox.isChecked();
        signupBtn.setEnabled(isFilled);
    }

    @OnFocusChange(R.id.register_editxt_email)
    public void emailValidation(){
        String email = emailET.getText().toString();
        if (email.length() > 0 && ! new Validator().isEmailValid(email)) {
            emailET.setError("Email Tidak Valid");
        }
    }

    @OnFocusChange({R.id.register_editxt_password, R.id.register_editxt_conf_password})
    public void passwordValidation(){
        String password = passwordET.getText().toString();
        String confPassword = confPasswordET.getText().toString();

        final int MIN_LENGTH = 8;

        boolean passwordNotFilled = password.length() < 1 && confPassword.length() < 1;

        if(password.length() < MIN_LENGTH){
            passwordET.setError("Minimal "+ MIN_LENGTH + " Karakter");
            return;
        }

        if (passwordNotFilled || !password.equals(confPassword)){
            passwordET.setError("Password Tidak Sesuai");
            confPasswordET.setError("Password Tidak Sesuai");
        } else {
            passwordET.setError(null);
            confPasswordET.setError(null);
        }

    }

    @OnClick(R.id.register_txtview_have_account)
    public void backToLoginActivity(){
        finish();
    }

    @OnClick(R.id.register_btn_sign_up)
    public void storeData(){
        emailValidation();
        passwordValidation();
        if(isEmailExist(emailET.getText().toString())){
            emailET.setError("Sudah Digunakan");
            return;
        }

        if (emailET.getError() == null && passwordET.getError() == null) {
            Toast.makeText(this, "Mendaftarkan", Toast.LENGTH_SHORT).show();
            User user = new User();
            user.setFirstName(firstNameET.getText().toString());
            user.setLastName(lastNameET.getText().toString());
            user.setEmail(emailET.getText().toString());
            user.setPhoneNumber(phoneNumET.getText().toString());
            user.setPassword(passwordET.getText().toString());

            new DatabaseHandler(this).addUser(user);

            Toast.makeText(this, "Akun anda berhasil terdaftar", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private boolean isEmailExist(String email){
        DatabaseHandler db = new DatabaseHandler(this);
        User user = db.findUser(email);
        if (user == null){
            return false;
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
