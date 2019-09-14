package alien.com.pishongroceries;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import alien.com.api.ApiService;
import alien.com.dashboard.Dashboard;
import alien.com.utils.MySingleTone;

public class RegisterCustomer extends AppCompatActivity
{
    Button nextDetail;
    EditText name,email,phonenumber;
    String getname,getemail,getphone;
    AlertDialog.Builder alertDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        } else {
            requestWindowFeature(Window.FEATURE_NO_TITLE);
        }
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);

        setContentView(R.layout.activity_register_customer);


        nextDetail = findViewById(R.id.nextDetail);
        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        phonenumber = findViewById(R.id.phone);
        alertDialog = new AlertDialog.Builder(RegisterCustomer.this);
        nextDetail.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if(name.getText().toString().equals("") || email.getText().toString().equals("") || phonenumber.getText().toString().equals(""))
                {
                    alertDialog.setTitle("Empty Fields");
                    // alertDialog.setMessage(message);
                    displayAlert("Please fill all the fields");
                }
                else
                {
                    getname = name.getText().toString().trim();
                    getemail = email.getText().toString().trim();
                    getphone = phonenumber.getText().toString().trim();
                    Log.e("RES",getname+getemail+getphone);

                    Intent intent = new Intent(RegisterCustomer.this,CustomerDetails.class);
                    intent.putExtra("name",getname);
                    intent.putExtra("email",getemail);
                    intent.putExtra("phone",getphone);
                    startActivity(intent);
                }
            }
        });
    }
    public void displayAlert(final String message) {
        alertDialog.setMessage(message);
        alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
               name.setText("");
                email.setText("");
                phonenumber.setText("");
            }
        });
        AlertDialog alertDiag = alertDialog.create();
        alertDiag.show();
    }


}
