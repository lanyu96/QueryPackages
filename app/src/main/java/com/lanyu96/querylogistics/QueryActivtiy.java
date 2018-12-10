package com.lanyu96.querylogistics;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.reflect.TypeToken;
import com.lanyu96.querylogistics.adapter.DataInfoAdapter;
import com.lanyu96.querylogistics.bean.LocAndTimeInfo;
import com.lanyu96.querylogistics.bean.PackagesInfo;
import com.lanyu96.querylogistics.uitl.SimpleDividerItemDecoration;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static android.support.v7.widget.StaggeredGridLayoutManager.VERTICAL;


public class QueryActivtiy extends AppCompatActivity {
    private static String TAG = "TTTT";
    private EditText logisticsCompany_et;
    private TextView logisticsInfo_tv;
    private StringBuilder sb;
    private EditText logisticsDanhao_et;
    private String jsonData;
    private TextView query_company_tv;
    private TextView query_danhao_tv;
    private RecyclerView dataInfo_rv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_query_activtiy);
        initView();
//        sb = new StringBuilder();

    }

    private void initView() {
        logisticsCompany_et = findViewById(R.id.act_query_wuliugongsi_et);
//        logisticsInfo_tv = findViewById(R.id.act_query_info_tv);
        query_company_tv = findViewById(R.id.act_query_company_tv);
        query_danhao_tv = findViewById(R.id.act_query_danhao_tv);

        logisticsDanhao_et = findViewById(R.id.act_query_yundanhao_et);

        //初始化RecyclerView
        dataInfo_rv = findViewById(R.id.act_query_dateInfo_rv);
        //设置为线性布局管理器
        dataInfo_rv.setLayoutManager(new LinearLayoutManager(QueryActivtiy.this
                , LinearLayoutManager.VERTICAL, false));
        //瀑布流管理器
//        dataInfo_rv.setLayoutManager(new StaggeredGridLayoutManager(1, VERTICAL));

        //为recyclerView添加分割线
        dataInfo_rv.addItemDecoration(new SimpleDividerItemDecoration(this,30,3));

        //临时指定 edittext
        logisticsCompany_et.setText("yuantong");
        logisticsDanhao_et.setText("802990317202125904");
    }

    public void queryInfo(View view) {
        sb = new StringBuilder();
        sb.append("http://www.kuaidi100.com/query?type=");
        String logisticsCompany = logisticsCompany_et.getText().toString().trim();
        String logisticsDanhao = logisticsDanhao_et.getText().toString().trim();

        sb.append(logisticsCompany);
        sb.append("&postid=");
        sb.append(logisticsDanhao);
        Log.i(TAG, "字符串请求");

        new RequestNetworkDataTask().execute(sb.toString());


        //获取数据后,清空StringBuilder
//        sb.delete(0, sb.length() - 1);

    }

//    /**
//     *     解析
//     * @param jsonData
//     */
//    private void parseJSONWithGSON(String jsonData) {
//        Gson gson = new Gson();
//        List<JSONInfo> jsonInfos = gson.fromJson(jsonData, new TypeToken<List<JSONInfo>>() {
//        }.getType());
//        for (JSONInfo jsonInfo : jsonInfos) {
////            Log.i(TAG,jsonInfo.getState());
////            Log.i(TAG, jsonInfo.getMessage());
//        }
//
//    }

    @SuppressLint("StaticFieldLeak")
    class RequestNetworkDataTask extends AsyncTask<String, Integer, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
//            logisticsInfo_tv.setText("正在加载");
            query_company_tv.setText("正在加载");
            query_danhao_tv.setText("");
            List<DataInfoAdapter> list = new ArrayList<>();
            dataInfo_rv.setAdapter(new DataInfoAdapter(QueryActivtiy.this,list));
            Log.i(TAG, "正在获取jsonData");
        }

        @Override
        protected String doInBackground(String... strings) {
            //获取返回的JsonObject
            GetJsonData getJsonData = new GetJsonData();
            String result = getJsonData.getJsonData(strings[0]);
//            String result = GetJsonData.getJsonData(strings[0]);
            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            //调用解析方法
//            parseJSONWithGSON(s);

            try {
                JSONObject jsonObject = new JSONObject(s);

//                PackagesInfo packagesInfo = new PackagesInfo();

                //解析JSON
                //第一层解析
                String company = jsonObject.optString("com");
                String condition = jsonObject.optString("codition");
                String ischeck = jsonObject.optString("ischeck");
                String number = jsonObject.optString("nu");
                Log.i(TAG, "快递单号为 : " + number);
                Log.i(TAG, "快递公司为" + company);
                //通过textView 显示单号和物流公司
                query_danhao_tv.setText(number);
                query_company_tv.setText(company);

                String state = jsonObject.optString("state");
                String status = jsonObject.optString("status");

                //第二层解析
                JSONArray jsonArray = jsonObject.optJSONArray("data");

                //第三层解析
                List<LocAndTimeInfo> list = new ArrayList<>();
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonInfo = jsonArray.optJSONObject(i);

                    if (jsonInfo != null) {
                        String time = jsonInfo.getString("time");
                        String context = jsonInfo.getString("context");
                        Log.i(TAG, "时间 : " + time + "物流状态" + context);

                        list.add(new LocAndTimeInfo(time, context));
                    }
                }
                dataInfo_rv.setAdapter(new DataInfoAdapter(QueryActivtiy.this,list));


            } catch (JSONException e) {
                e.printStackTrace();
            }

//            logisticsInfo_tv.setText(s);
            Log.i(TAG, s);
        }
    }

}
