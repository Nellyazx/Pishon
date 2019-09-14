package alien.com.vieworders;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import alien.com.dashboard.Dashboard;
import alien.com.model.OrderInfo;
import alien.com.pishongroceries.R;

public class ViewPendingOrders extends AppCompatActivity
{
    SwipeRefreshLayout ordersswiperefreshlayout;
    RecyclerView ordersrecyclerView;
    List<OrderInfo> orderInfoList;
    OrdersAdapter ordersAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pending_orders);
        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Pending Orders");
        toolbar.setNavigationIcon(R.drawable.back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(getApplicationContext(), Dashboard.class));
            }
        });

        ordersrecyclerView = findViewById(R.id.ordersrecyclerview);
        ordersswiperefreshlayout = findViewById(R.id.ordersswipeRefreshLayout);
        ordersrecyclerView.setLayoutManager(new LinearLayoutManager(this.getApplicationContext()));
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(ordersrecyclerView.getContext(),DividerItemDecoration.VERTICAL);
        dividerItemDecoration.setDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.divider));
        ordersrecyclerView.addItemDecoration(dividerItemDecoration);
        ordersrecyclerView.setHasFixedSize(true);
        orderInfoList = new ArrayList<>();
        getData();
        ordersswiperefreshlayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh()
            {
                refreshPage();
            }
        });

    }
    public void refreshPage()
    {
        ordersAdapter = new OrdersAdapter(getApplicationContext(),orderInfoList);
        ordersAdapter.clearAdapter();
        getData();
    }
    public void getData()
    {

    }
}
