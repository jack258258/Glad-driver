package com.example.user.glad_driver;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class LineIn extends BaseActivity {
    private ListView listview;
    private String[] Order1={"台南火車站","12","有寵物"};
    private String[] Order2={"台中火車站","13","有寵物"};
    private String[] Order3={"台北火車站","14","有寵物"};
    private String[] Order4={"彰化火車站","15","有寵物"};

    private ArrayAdapter adapterBalls;
    private ArrayList<Map<String,Object>> OrderData;
    private Button curDel_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_line_in);
        listview = (ListView)findViewById(R.id.list);
        OrderData = getData();
        MyAdapter myadapter = new MyAdapter(this);
        listview.setAdapter(myadapter);
        //listview.setOnItemClickListener(listViewListener);
    }
    /** 自定義的Adapter，繼承android.widget.BaseAdapter */
    public class MyAdapter extends BaseAdapter {

        private LayoutInflater mInflater;

        // 因繼承BaseAdapter，需覆寫以下method
        public MyAdapter(Context context) {
            this.mInflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            // 限制只有三個item出現
            Log.d("Log", "長度");
            if (OrderData.size()>=3) {
                Log.d("Log", ">3項");
                return 3;
            }else{
                Log.d("Log", "<3項");
                return OrderData.size();
            }
        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return OrderData.get(position);
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return 0;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            ViewHolder holder = null;
            if (convertView == null) {
                // 初始化holder的text與icon
                holder = new ViewHolder();
                // 使用自定義的Layout
                convertView = mInflater.inflate(R.layout.receive_order_list, null);
                holder.place = (TextView) convertView.findViewById(R.id.getplace);
                //holder.minute = (TextView) convertView.findViewById(R.id.timeminute);
                holder.remark = (TextView) convertView.findViewById(R.id.remarks);
                holder.viewCancel = (Button) convertView
                        .findViewById(R.id.btncancel);
                holder.viewCertain = (Button) convertView
                        .findViewById(R.id.certain);
                convertView.setTag(holder);

            } else {

                holder = (ViewHolder) convertView.getTag();
            }

            // 設定要顯示的資訊
            holder.place.setText((String) OrderData.get(position).get("place"));
            //holder.minute.setText((String) OrderData.get(position).get("minute"));
            holder.remark.setText((String) OrderData.get(position).get("remark"));

            //自訂mylayout 內Button的用途
            holder.viewCancel.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    Log.d("myLog", "輸出訊息");
                    OrderData.remove(position);
                    notifyDataSetChanged();
                }
            });
            holder.viewCertain.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    //搶的指令
                    Intent intent = new Intent();
                    intent.setClass(LineIn.this, MapsActivity.class);
                    startActivity(intent);
                    notifyDataSetChanged();
                }
            });

            return convertView;
        }
    }

    public final class ViewHolder {
        public TextView place;
        public TextView minute;
        public TextView remark;
        public Button viewCancel;
        public Button viewCertain;
    }
    private ArrayList<Map<String,Object>> getData() {
        ArrayList arraylist = new ArrayList();

        Map map = new HashMap();        //key, value
        map.put("place", Order1[0]);
        //map.put("minute", Order1[1]);
        map.put("remark", Order1[2]);
        arraylist.add(map);

        map = new HashMap();
        map.put("place", Order2[0]);
        //map.put("minute", Order2[1]);
        map.put("remark", Order2[2]);
        arraylist.add(map);

        map = new HashMap();
        map.put("place", Order3[0]);
        //map.put("minute", Order3[1]);
        map.put("remark", Order3[2]);
        arraylist.add(map);

        map = new HashMap();
        map.put("place", Order4[0]);
        //map.put("minute", Order4[1]);
        map.put("remark", Order4[2]);
        arraylist.add(map);
        return arraylist;
    }
    //定義onItemClick方法
    /*private ListView.OnItemClickListener listViewListener = new ListView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            //顯示ListView的選項內容
            //String sel = mData.get(position).get("title").toString();   //getItemAtPosition取得選項內容，position是選項的索引，最後顯示在txtResult上
            switch(position){
                case 0:
                    txtResult.setText("1");
                    break;
                case 1:
                    txtResult.setText("2");
                    break;
                case 2:
                    txtResult.setText("3");
                    break;

            }
        }
    };*/


}
