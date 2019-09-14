package alien.com.utils;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class MySingleTone
{
    private RequestQueue mRequestQueue;
    private static MySingleTone mInstance;
    private static Context mctx;

    public MySingleTone(Context context)
    {
        mctx=context;
        mRequestQueue=getRequestQueue();
    }
    public RequestQueue getRequestQueue()
    {
        if (mRequestQueue == null)
        {
            mRequestQueue = Volley.newRequestQueue(mctx.getApplicationContext());
        }

        return mRequestQueue;
    }
    public static synchronized MySingleTone getInstance(Context context)
    {
        if(mInstance==null)
        {
            mInstance=new MySingleTone(context);
        }
        return mInstance;
    }
    public <T> void addToRequestQueue(Request<T> req) {
        mRequestQueue.add(req);
    }


}
