package alien.com.category;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import alien.com.api.ApiService;
import alien.com.model.Category;
import alien.com.model.UserInfo;
import alien.com.pishongroceries.CustomerDetails;
import alien.com.pishongroceries.R;
import alien.com.utils.MySingleTone;
import alien.com.viewcustomers.CustomersAdapter;
import alien.com.viewcustomers.UpdateUserInfo;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>
{
    public Context context;
    public String categoryName;
    public List<Category> categoryInfo;
    public CategoryAdapter(Context context, List<Category>categoryInfo)
    {
        this.context = context;
        this.categoryInfo = categoryInfo;
    }
    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
    {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.category,viewGroup,false);
        CategoryAdapter.CategoryViewHolder categoryViewHolder = new CategoryAdapter.CategoryViewHolder(view);
        return categoryViewHolder;
    }
    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder categoryViewHolder, final int position)
    {
        final Context context = categoryViewHolder.itemView.getContext();
        categoryViewHolder.categoryName.setText(categoryInfo.get(position).getCategoryName());
        //getImageUsingGlide
        categoryViewHolder.editCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(context,EditCategory.class);
                intent.putExtra("categoryname",categoryInfo.get(position).getCategoryName());
                context.startActivity(intent);
            }
        });
        categoryViewHolder.deleteCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
               categoryName = categoryInfo.get(position).getCategoryName();
               deleteCategory();
            }
        });

    }
    public void deleteCategory()
    {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ApiService.DELETE_CATEGORY, new Response.Listener<String>() {
            @Override
            public void onResponse(String response)
            {
                Toast.makeText(context, " Success "+response, Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, " Error "+error, Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("categoryname", categoryName);
                return params;
            }
        };
        MySingleTone.getInstance(context).addToRequestQueue(stringRequest);

    }
    @Override
    public int getItemCount()
    {
        return categoryInfo.size();
    }
    public void clearAdapter()
    {
        categoryInfo.clear();
        notifyDataSetChanged();
    }
    public static class CategoryViewHolder extends RecyclerView.ViewHolder
    {
        public ImageView categoryImage;
        public TextView categoryName;
        public Button editCategory;
        public Button deleteCategory;
        public CategoryViewHolder(@NonNull View itemView)
        {
            super(itemView);
            categoryImage = itemView.findViewById(R.id.categoryImage);
            categoryName = itemView.findViewById(R.id.categoryImage);
            editCategory = itemView.findViewById(R.id.editcategory);
            deleteCategory = itemView.findViewById(R.id.deletecategory);
        }
    }

}
