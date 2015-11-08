package com.edus.apollo.funny.ui.adapter;

import android.view.View;

public interface CommonItemClickListener {
	void onItemViewClicked(int adapterPosition, int type, View view, View rootView);

	void onItemViewLongClicked(int adapterPosition, int type, View view, View rootView);

}
