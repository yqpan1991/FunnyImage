package com.edus.apollo.funny.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.edus.apollo.funny.R;
import com.edus.apollo.funny.net.api.EsUserApiHelper;
import com.edus.apollo.funny.net.model.MakeModule;
import com.edus.apollo.funny.ui.activity.ClassifyActivity;
import com.edus.apollo.funny.ui.activity.EditDetailActivity;
import com.edus.apollo.funny.ui.adapter.CommonItemClickListener;
import com.edus.apollo.funny.ui.adapter.MakePhotoAdapter;

/**
 * Created by Panda on 2015/9/20.
 */
public class MakePhotoFragment extends BaseFragment {
    private RelativeLayout mRlSearch;
    private SwipeRefreshLayout mSrlRefresh;
    private ListView mLvList;
    MakePhotoAdapter mAdapter;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.make_fragment_layout, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRlSearch = (RelativeLayout) view.findViewById(R.id.rl_search);
        mSrlRefresh = (SwipeRefreshLayout) view.findViewById(R.id.srl_refresh);
        mLvList = (ListView) view.findViewById(R.id.lv_list);

        mSrlRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                MakePhotoFragment.this.onRefresh();
            }
        });
    }

    private void onRefresh() {
        getView().postDelayed(new Runnable() {
            @Override
            public void run() {
                fetchData();
            }
        }, 3000);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mAdapter = new MakePhotoAdapter(getActivity());
        mAdapter.setItemClickListener(mClickListener);
        mLvList.setAdapter(mAdapter);
        mSrlRefresh.setRefreshing(true);
        fetchData();
    }



    private void fetchData(){
        EsUserApiHelper.getTemplateList(new Response.Listener<MakeModule>() {
            @Override
            public void onResponse(MakeModule makeModule) {
                mAdapter.setData(makeModule.templates);
                mSrlRefresh.setRefreshing(false);
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(getActivity(), volleyError.toString(), Toast.LENGTH_SHORT).show();
                mSrlRefresh.setRefreshing(false);
            }
        });
    }

    private CommonItemClickListener mClickListener = new CommonItemClickListener() {

        @Override
        public void onItemViewClicked(int adapterPosition, int type, View view, View rootView) {
            switch (type){
                case MakePhotoAdapter.CLICK_1_IMAGE:
                    processImageClicked(adapterPosition,0);
                    break;
                case MakePhotoAdapter.CLICK_2_IMAGE:
                    processImageClicked(adapterPosition,1);
                    break;
                case MakePhotoAdapter.CLICK_3_IMAGE:
                    processImageClicked(adapterPosition,2);
                    break;
                case MakePhotoAdapter.CLICK_1_TEXT:
                    processCategoryClicked();
                    break;
                case MakePhotoAdapter.CLICK_2_TEXT:
                    processHotClicked();
                    break;
                case MakePhotoAdapter.CLICK_3_TEXT:
                    processLatestClicked();
                    break;

            }
        }

        @Override
        public void onItemViewLongClicked(int adapterPosition, int type, View view, View rootView) {

        }
    };

    private void processLatestClicked() {

    }

    private void processHotClicked() {

    }

    private void processCategoryClicked() {
        Intent intent = new Intent(getActivity(), ClassifyActivity.class);
        startActivity(intent);
    }

    private void processImageClicked(int adapterPosition, int index) {
        MakeModule.Template[] templates = (MakeModule.Template[]) mAdapter.getItem(adapterPosition);
        if(templates != null &&templates.length == 3 && index >=0 && index < 3){
            MakeModule.Template template = templates[index];
            startActivity(EditDetailActivity.getEditDetailIntent(getActivity(),template));
        }
    }

    private void processImageClicked(MakeModule.Template template) {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
