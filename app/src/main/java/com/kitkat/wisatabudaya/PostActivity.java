package com.kitkat.wisatabudaya;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;

public class PostActivity extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;
    @Bind(R.id.post_spinner_category)
    Spinner categoriesSpinner;
    @Bind(R.id.post_spinner_state)
    Spinner provinsiSpinner;
    @Bind(R.id.post_editxt_name)
    EditText nameET;
    @Bind(R.id.post_editxt_city)
    EditText cityET;
    @Bind(R.id.post_editxt_district)
    EditText districtET;
    @Bind(R.id.post_editxt_village)
    EditText villageET;
    @Bind(R.id.post_editxt_desc)
    EditText descET;
    @Bind(R.id.post_editxt_actor)
    EditText actorET;
    @Bind(R.id.post_editxt_photo_desc)
    EditText photoDescET;
    @Bind(R.id.post_radiogroup_condition)
    RadioGroup conditionRadioGroup;
    @Bind(R.id.post_txtview_filename)
    TextView filenameTV;
    @Bind(R.id.post_btn_submit)
    Button submitBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        ButterKnife.bind(this);
        List<String> list = new ArrayList<String>();
        list.add("Tradisi Lisan");
        list.add("Bahasa");
        list.add("Naskah Kuno");
        list.add("Permainan Tradisional");
        list.add("Seni Tradisi");
        list.add("Upacara / Ritus");
        list.add("Kearifan Lokal");
        list.add("Teknologi Tradisional");
        list.add("Arsitektur");
        list.add("Kain Tradisional");
        list.add("Kerajinan Tradisional");
        list.add("Kuliner Tradisional");
        list.add("Pakaian Adat");
        list.add("Senjata Tradisional");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categoriesSpinner.setAdapter(dataAdapter);

        list = new ArrayList<String>();
        list.add("Aceh");
        list.add("Bali");
        list.add("Banten");
        list.add("Bengkulu");
        list.add("DI Yogyakarta");
        list.add("DKI Jakarta");
        list.add("Gorontalo");
        list.add("Jambi");
        list.add("Jawa Barat");
        list.add("Jawa Tengah");
        list.add("Kalimantan Barat");
        list.add("Kalimantan Selatan");
        list.add("Kalimantan Tengah");
        list.add("Kalimantan Timur");
        list.add("Kalimantan Utara");
        list.add("Kepulauan Bangka Belitung");
        list.add("Kepulauan Riau");
        list.add("Lampung");
        list.add("Maluku");
        list.add("Maluku Utara");
        list.add("Nusa Tenggara Timur");
        list.add("Nusa Tenggara Barat");
        list.add("Papua");
        list.add("Papua Barat");
        list.add("Riau");
        list.add("Sulawesi Barat");
        list.add("Sulawesi Selatan");
        list.add("Sulawesi Tengah");
        list.add("Sulawesi Tenggara");
        list.add("Sulawesi Utara");
        list.add("Sumatera Barat");
        list.add("Sumatera Selatan");
        list.add("Sumatera Utara");
        dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        provinsiSpinner.setAdapter(dataAdapter);
    }

    @OnTextChanged(
            {
                    R.id.post_editxt_name,
                    R.id.post_editxt_city,
                    R.id.post_editxt_district,
                    R.id.post_editxt_village,
                    R.id.post_editxt_desc,
                    R.id.post_editxt_actor,
                    R.id.post_editxt_photo_desc,
                    R.id.post_txtview_filename
            })
    @OnClick({R.id.post_radiobtn1, R.id.post_radiobtn2, R.id.post_radiobtn3})
    public void isAllFormFilled() {
        boolean isFilled = nameET.getText().length() > 0 &&
                cityET.getText().length() > 0 &&
                districtET.getText().length() > 0 &&
                villageET.getText().length() > 0 &&
                descET.getText().length() > 0 &&
                actorET.getText().length() > 0 &&
                photoDescET.length() > 0 &&
                conditionRadioGroup.getCheckedRadioButtonId() != -1 &&
                !filenameTV.getText().toString().equals("belum ada file yang dipilih");
        submitBtn.setEnabled(isFilled);
    }


    @OnClick(R.id.post_btn_logout)
    public void logout() {
        SharedPreferences sharedpreferences = getSharedPreferences("LOGIN_DATA", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.clear();
        editor.commit();
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    @OnClick(R.id.post_btn_upload)
    public void pickImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST &&
                resultCode == RESULT_OK && data != null &&
                data.getData() != null) {

            Uri selectedImageUri = data.getData();
            String path = getRealPathFromURI(selectedImageUri);
            String filename = path.substring(path.lastIndexOf("/") + 1);
            filenameTV.setText(filename);
        }
    }

    public String getRealPathFromURI(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        @SuppressWarnings("deprecation")
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        int column_index = cursor
                .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    @OnClick(R.id.post_btn_submit)
    public void submit() {
        Toast.makeText(this, "Data Berhasil Disimpan", Toast.LENGTH_SHORT).show();
        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }

}
