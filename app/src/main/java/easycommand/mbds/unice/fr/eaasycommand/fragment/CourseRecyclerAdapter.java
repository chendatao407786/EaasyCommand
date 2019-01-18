package easycommand.mbds.unice.fr.eaasycommand.fragment;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;

import easycommand.mbds.unice.fr.eaasycommand.R;

public class CourseRecyclerAdapter extends RecyclerView.Adapter<CourseRecyclerAdapter.MyViewHodler> {

    private Context context;
//    private List<String> courseList;
    private JSONArray courseList;
    public CourseRecyclerAdapter(Context context, JSONArray courseList) {
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
        try {
            Glide.with(context)
                    .asBitmap()
                    .load(courseList.getJSONObject(i).getString("image"))
                    .into(myViewHodler.imageView);
            myViewHodler.name_loc.setText(courseList.getJSONObject(i).getString("courseNameLoc"));
            myViewHodler.name_en.setText(courseList.getJSONObject(i).getString("courseNameEn"));
            myViewHodler.price.setText(courseList.getJSONObject(i).getString("coursePrice")+" €");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return courseList.length();
    }

    class MyViewHodler extends RecyclerView.ViewHolder {
        TextView name_loc;
        TextView name_en;
        TextView price;
        ImageView imageView;
        public MyViewHodler(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.courseImage);
            name_loc = itemView.findViewById(R.id.name_loc);
            name_en = itemView.findViewById(R.id.name_en);
            price = itemView.findViewById(R.id.price);
        }
    }
}
