package com.example.widgets;
 
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
public class MainActivity extends Activity {

	private String[] list = {"item1","item2","item3","item4"};
	private PopupWindow popwindow;
	private ListView listview;
	private int NUM_OF_VISIBLE_LIST_ROWS = 3;
	private ImageButton SettingBtn;
	private HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
    }
    
	private void iniPopupWindow() {
		
		LayoutInflater inflater = (LayoutInflater) this
				.getSystemService(LAYOUT_INFLATER_SERVICE);
		View layout = inflater.inflate(R.layout.sensor_listview, null);
		listview = (ListView) layout.findViewById(R.id.list);
		popwindow = new PopupWindow(layout);
		popwindow.setFocusable(true);// 

		final RadioAdapter adapter = new RadioAdapter(this);
		listview.setAdapter(adapter);
		listview.setOnItemClickListener(new OnItemClickListener() {

                        @Override
                        public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                        long arg3) {
                        	map.clear();
                            map.put(arg2, 100);
                                // TODO Auto-generated method stub
                                Log.e("clicked", "===arg2:" + arg2 + "===arg3:" + arg3);
                                Log.e("clicked", "===txt : " + list[arg2]);
                                adapter.notifyDataSetChanged();
                        }
                });
		listview.measure(View.MeasureSpec.UNSPECIFIED,
				View.MeasureSpec.UNSPECIFIED);
		popwindow.setWidth(listview.getMeasuredWidth()*2);
		popwindow.setHeight((listview.getMeasuredHeight() + 20)
				* NUM_OF_VISIBLE_LIST_ROWS);
		popwindow.setBackgroundDrawable(new BitmapDrawable());
		popwindow.setOutsideTouchable(true);
	}
    
    class RadioHolder{
        
        private TextView item;
        private RadioButton radio;
        public RadioHolder(View view){
                
                this.item = (TextView) view.findViewById(R.id.item_text);
                this.radio = (RadioButton) view.findViewById(R.id.item_radio);
        }
    }

    class RadioAdapter extends BaseAdapter{
         
        private Context context;
        public RadioAdapter(Context context){
                this.context = context;
        }
         
        @Override
        public int getCount() {
                // TODO Auto-generated method stub
                return list.length;
        }

        @Override
        public Object getItem(int arg0) {
                // TODO Auto-generated method stub
                return list[arg0];
        }

        @Override
        public long getItemId(int position) {
                // TODO Auto-generated method stub
                return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
                // TODO Auto-generated method stub
                RadioHolder holder;
                if(convertView == null){
                        convertView = LayoutInflater.from(context).inflate(
                                        R.layout.sensor_item, null);
                        holder = new RadioHolder(convertView);
                        convertView.setTag(holder);
                }else{
                        holder = (RadioHolder) convertView.getTag();
                }
                holder.radio.setChecked(map.get(position) == null ? false : true);
                holder.item.setText(list[position]);
                return convertView;
        }
         
}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
    public void showListView(){

		iniPopupWindow();
		SettingBtn = (ImageButton) findViewById(R.id.setting);
		
		if (popwindow.isShowing()) {
			
			Log.e("listview","====dismiss popwindow====");

			popwindow.dismiss();
		} else {
			Log.e("listview","====show popwindow====");
			popwindow.showAsDropDown(SettingBtn);
			
		}
    }
    
    public void onClickedBtn(View view){
    	showListView();
    }
}
