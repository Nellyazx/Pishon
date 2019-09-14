package alien.com.dashboard;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import alien.com.pishongroceries.CustomerDetails;
import alien.com.pishongroceries.R;
import alien.com.pishongroceries.RegisterCustomer;
import alien.com.products.AddCategory;
import alien.com.viewcustomers.AllCustomersFragment;
import alien.com.viewcustomers.UpdateUserInfo;
import alien.com.viewcustomers.ViewCustomers;
import alien.com.vieworders.ViewDeliveredOrders;
import alien.com.vieworders.ViewPendingOrders;

public class Dashboard extends AppCompatActivity
{
    RelativeLayout addcustomer,showcustomer,viewpending,viewdelivered,addcategory,addproducts;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Dashboard");

        addcustomer = findViewById(R.id.addcustomer);
        showcustomer = findViewById(R.id.showcustomers);
        viewpending = findViewById(R.id.pending);
        viewdelivered = findViewById(R.id.delivered);
        addcategory = findViewById(R.id.category);
        addproducts = findViewById(R.id.products);
        addcustomer.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                addCustomer();
            }
        });
        showcustomer.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                viewCustomers();
            }
        });
        viewpending.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                viewPendingOrders();
            }
        });
        viewdelivered.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                viewDeliveredOrders();
            }
        });
        addcategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addCategory();
            }
        });
        addproducts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addProducts();
            }
        });
    }
    public void addCustomer()
    {
        startActivity(new Intent(Dashboard.this, RegisterCustomer.class));
    }
    public void viewCustomers() {
        android.support.v4.app.FragmentManager fragmentManager = Dashboard.this.getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        AllCustomersFragment fragment = new AllCustomersFragment();

        fragmentTransaction.add(R.id.reportDrugs, fragment);
        fragmentTransaction.commit();
    }

//    public void updateCustomers()
//    {
//        startActivity(new Intent(Dashboard.this, UpdateUserInfo.class));
//    }
    public void viewPendingOrders()
    {
        startActivity(new Intent(Dashboard.this, ViewPendingOrders.class));
    }
    public void viewDeliveredOrders()
    {
        startActivity(new Intent(Dashboard.this, ViewDeliveredOrders.class));
    }
    public void addCategory()
    {
        startActivity(new Intent(Dashboard.this, AddCategory.class));
    }
    public void addProducts()
    {
        startActivity(new Intent(Dashboard.this, AddCategory.class));
    }
}
