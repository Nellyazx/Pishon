package alien.com.category;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import alien.com.api.ApiService;
import alien.com.dashboard.Dashboard;
import alien.com.pishongroceries.R;
import alien.com.utils.MySingleTone;

public class EditCategory extends AppCompatActivity {
    EditText updcategoryName;
    RelativeLayout updaddImage;
    Button updatecategory;
    ProgressDialog progressDialog;
    String newName;
    int count=0;
    TextView selectImage;
    private final int IMG_REQUEST=1;
    ImageView catImage;
    Bitmap bitmap;
    AlertDialog.Builder alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_category);
        updcategoryName = findViewById(R.id.updcategoryName);
        updaddImage = findViewById(R.id.updaddImage);
        updatecategory = findViewById(R.id.updaddCat);
        selectImage = findViewById(R.id.selectImage);
        alertDialog = new AlertDialog.Builder(EditCategory.this);

        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Add Categories");
        setSupportActionBar(toolbar);
        updaddImage.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                selectImage();
            }
        });
        toolbar.setNavigationIcon(R.drawable.back);
        progressDialog = new ProgressDialog(this);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(getApplicationContext(), Dashboard.class));
            }
        });
        updatecategory.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                newName = updcategoryName.getText().toString();
                updateCategory();
            }
        });

    }
    public void updateCategory()
    {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ApiService.ADD_CATEGORY, new Response.Listener<String>() {
            @Override
            public void onResponse(String response)
            {
                progressDialog.dismiss();
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    JSONObject jsonObject = jsonArray.getJSONObject(0);
                    String code = jsonObject.getString("code");
                    String message = jsonObject.getString("message");
                    alertDialog.setTitle("Server Response");
                    alertDialog.setMessage(message);
                    displayAlert(code);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getBaseContext(), " something went wrong ", Toast.LENGTH_SHORT).show();

                progressDialog.dismiss();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("categoryname", newName);
                params.put("image", imageToString(bitmap));
                return params;
            }
        };

        MySingleTone.getInstance(EditCategory.this).addToRequestQueue(stringRequest);
    }
    //convert imagebitmap to strng
    public String imageToString(Bitmap mbitmap)
    {
        ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
        mbitmap.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
        byte[] imagebytes = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(imagebytes,Base64.DEFAULT);

    }
    public void selectImage()
    {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,IMG_REQUEST);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode,  Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==IMG_REQUEST && resultCode==RESULT_OK && data!=null)
        {
            Uri uri = data.getData();
            try
            {
                bitmap= MediaStore.Images.Media.getBitmap(getContentResolver(),uri);
                catImage.setImageBitmap(bitmap);
                selectImage.setVisibility(View.GONE);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
    public void displayAlert(final String code) {
        alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which)
            {
                if (code.equals("input_error")) {
                    updcategoryName.setText("");

                }
                else if(code.equals("ins_failed"))
                {
                    alertDialog.setTitle("Upload Failed");
                    alertDialog.setMessage("Data Upload Failed");
                    updcategoryName.setText("");

                }
                else
                {
                    Intent intent = new Intent(EditCategory.this,Dashboard.class);
                    startActivity(intent);
                }

            }

        });
        AlertDialog theDialog = alertDialog.create();
        theDialog.show();
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (count == 0) {
                Toast.makeText(EditCategory.this, "Click again to exit application ", Toast.LENGTH_SHORT).show();
                count++;
            } else {
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        }
        return true;
    }
}
