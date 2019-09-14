package alien.com.vieworders;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import alien.com.api.ApiService;
import alien.com.model.OrderInfo;
import alien.com.model.UserInfo;
import alien.com.pishongroceries.R;
import alien.com.utils.MySingleTone;
import alien.com.viewcustomers.CustomersAdapter;


public class VIewAllPendingORders extends Fragment
{
    SwipeRefreshLayout swipeRefreshLayout;
    RecyclerView recyclerView;
    List<OrderInfo> orders;
    OrdersAdapter adapter;



    public VIewAllPendingORders()
    {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_view_all_pending_orders,container,false);
        swipeRefreshLayout = v.findViewById(R.id.swipeRefreshLayout);
        recyclerView = v.findViewById(R.id.orderInfo);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),DividerItemDecoration.VERTICAL);
        dividerItemDecoration.setDrawable(ContextCompat.getDrawable(getContext(),R.drawable.divider));
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setHasFixedSize(true);
        orders = new ArrayList<>();
        getData();
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshPage();
            }
        });
        return v;
    }
    public void refreshPage()
    {
        adapter = new OrdersAdapter(getActivity(),orders);
        adapter.clearAdapter();
        getData();
    }
    public void getData()
    {
        swipeRefreshLayout.setRefreshing(true);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, ApiService.VIEW_ALL_CUSTOMERS_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response)
            {
                try
                {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("results");
                    for(int i=0;i<jsonArray.length();i++)
                    {
                        JSONObject myObject = jsonArray.getJSONObject(i);
                        OrderInfo orderInfo = new OrderInfo(myObject.getString("name"),
                                myObject.getString("email"),
                                myObject.getString("phone"),
                                myObject.getString("location"));
                        orders.add(orderInfo);
                    }
                    swipeRefreshLayout.setRefreshing(false);
                    adapter = new OrdersAdapter(getActivity(),orders);
                    recyclerView.setAdapter(adapter);
                }


                catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                swipeRefreshLayout.setRefreshing(false);
                //   Toast.makeText(getActivity(), ""+error, Toast.LENGTH_SHORT).show();
                Log.e("RES", String.valueOf(error));
            }
        });
        MySingleTone.getInstance(getActivity()).addToRequestQueue(stringRequest);
    }

}
