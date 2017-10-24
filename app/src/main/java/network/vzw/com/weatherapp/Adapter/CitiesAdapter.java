package network.vzw.com.weatherapp.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import java.util.ArrayList;

import network.vzw.com.weatherapp.R;


public class CitiesAdapter extends BaseAdapter {

	private ArrayList<CitiesDTO> cityList = null;
	private Context ctx = null;
	private final String NOT_AVAILABLE="Not Available";

	@Override
	public int getCount() {
		return (cityList !=null)? cityList.size():0;
	}

	@Override
	public Object getItem(int position) {

		return cityList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}



	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		final ViewHolder holder;
		try {
			convertView = null;
			if (convertView == null) {
				holder = new ViewHolder();
				convertView = (LayoutInflater.from(ctx)).inflate(R.layout.cities_layout, null);
				holder.cityTxt = (TextView) convertView.findViewById(R.id.textView);
				holder.position = position;
				final CitiesDTO cityDto = cityList.get(position);

				Log.d("TAG","$$$: "+cityDto.getCityName());
				if (cityDto != null) {
					holder.cityTxt.setText(cityDto.getCityName());
				}

			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			return convertView;

		} catch (NullPointerException e) {
			e.printStackTrace();
			return convertView;
		}
	}


	public CitiesAdapter(Context ctx, ArrayList<CitiesDTO> cityList) {
		this.ctx = ctx;
		this.cityList = cityList;

	}
	static class ViewHolder {
		int position;

		TextView cityTxt;

	}

}
