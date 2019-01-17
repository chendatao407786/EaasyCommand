package easycommand.mbds.unice.fr.eaasycommand.fragment;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import easycommand.mbds.unice.fr.eaasycommand.R;

public class CourseRecyclerAdapter extends RecyclerView.Adapter<CourseRecyclerAdapter.MyViewHodler> {

    private Context context;
    private List<String> courseList;

    public CourseRecyclerAdapter(Context context, List<String> courseList) {
        this.context = context;
        this.courseList = courseList;
    }

    @NonNull
    @Override
    public MyViewHodler onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.course_recycle_list,viewGroup,false);
        MyViewHodler myViewHodler = new MyViewHodler(view);
        return myViewHodler;
    }

    @Override
    public void onBindViewHolder(@NonNull CourseRecyclerAdapter.MyViewHodler myViewHodler, int i) {
        myViewHodler.course.setText(courseList.get(i));
    }

    @Override
    public int getItemCount() {
        return courseList.size();
    }

    class MyViewHodler extends RecyclerView.ViewHolder {
        TextView course;
        public MyViewHodler(@NonNull View itemView) {
            super(itemView);
            course = itemView.findViewById(R.id.course);
        }
    }
}
