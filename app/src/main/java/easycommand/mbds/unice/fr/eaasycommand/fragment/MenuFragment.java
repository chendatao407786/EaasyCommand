package easycommand.mbds.unice.fr.eaasycommand.fragment;


import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import easycommand.mbds.unice.fr.eaasycommand.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class MenuFragment extends Fragment {
    private View view;
    private List<String> mDataset = new ArrayList<>();
    JSONArray mCourses;
    private RecyclerView mCourseRecyclerView;
    private RecyclerView.Adapter mCourseRecycleAdapter;
    private RecyclerView.LayoutManager mLayoutManager;


    public MenuFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_menu, container, false);
        TextView title = view.findViewById(R.id.title);
        Bundle bundle = getArguments();
        String titleString = bundle.getString("title");
        try{
            mCourses = new JSONArray(bundle.getString("courses"));
        }catch (JSONException e){
            e.printStackTrace();
        }
        title.setText(titleString);
        initData();
        return view;
    }

    private void initData(){
        for(int i = 0;i<mCourses.length();i++){
            try {
                mDataset.add(mCourses.getJSONObject(i).getString("courseNameLoc"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }



        initializeAdapter();
    }

    private void initializeAdapter() {
        mCourseRecyclerView=view.findViewById(R.id.courseList);
        mCourseRecycleAdapter = new CourseRecyclerAdapter(getActivity(),mCourses);
        mCourseRecyclerView.setAdapter(mCourseRecycleAdapter);
//        mCategoryRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        mLayoutManager = new LinearLayoutManager(getActivity());
        mCourseRecyclerView.setLayoutManager(mLayoutManager);
        mCourseRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL));
        //RecyclerView中没有item的监听事件，需要自己在适配器中写一个监听事件的接口。参数根据自定义
//        mCourseRecycleAdapter.setOnItemClickListener(new mCourseRecycleAdapter.OnItemClickListener() {
//            @Override
//            public void OnItemClick(View view, GoodsEntity data) {
//                //此处进行监听事件的业务处理
//                Toast.makeText(getActivity(),"我是item",Toast.LENGTH_SHORT).show();
//            }
//        });
    }
}
