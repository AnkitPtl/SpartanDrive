package team11spartandrive.com.team11spartandrive;

/**
 * Created by jainam on 11/29/2015.
 */

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import team11spartadrive.com.helper.DriveFiles;

public class CustomListAdapter extends ArrayAdapter<String> implements Filterable {

    private final Activity context;
    private List<String> filename=null;
    private List<String> searchFile=null;
    private Filter filter;
    private List<String> desc=null;
    DriveFiles dp;
    //private final Integer [] imgid;
    private int imgid = R.mipmap.folder;
    Map<String,String> file_ext_list;

    public CustomListAdapter(Activity context, List<String> filename,List<String> desc) {

        super(context, R.layout.mylist, filename);
        // TODO Auto-generated constructor stub

        this.context=context;
        this.filename=filename;
        this.searchFile=filename;
        file_ext_list = DriveFiles.getDriveFileInstance().getFile_ext_list();
        this.desc=desc;
    }

    public View getView(int position,View view,ViewGroup parent) {

        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.mylist, null,true);

        TextView txtTitle = (TextView) rowView.findViewById(R.id.item);
        TextView subText =(TextView) rowView.findViewById(R.id.subItem);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
        txtTitle.setText(searchFile.get(position).toString());//+ desc.get(position));
        subText.setText(desc.get(position).toString());

        try {
            if (file_ext_list.get(searchFile.get(position).toString()).equals("pdf")) {
                imgid = R.mipmap.pdf;
            } else if (file_ext_list.get(searchFile.get(position).toString()).equals("doc") || file_ext_list.get(searchFile.get(position).toString()).equals("docx")) {
                imgid = R.mipmap.word;
            } else if (file_ext_list.get(searchFile.get(position).toString()).equals("xls")) {
                imgid = R.mipmap.xls;
            } else if (file_ext_list.get(searchFile.get(position).toString()).equals("ppt")) {
                imgid = R.mipmap.ppt;
            } else {
                imgid = R.mipmap.folder;
            }
        }
        catch (Exception e){
            imgid = R.mipmap.folder;
        }
        imageView.setImageResource(imgid);
        return rowView;

    };

    @Override
    public Filter getFilter() {
        if (filter == null)
            filter = new AppFilter<String>(filename);
        return filter;
    }


    private class AppFilter<T> extends Filter {

        private ArrayList<T> sourceObjects;

        public AppFilter(List<T> filename) {
            sourceObjects = new ArrayList<T>();
            synchronized (this) {
                sourceObjects.addAll(filename);
            }
        }

        @Override
        protected FilterResults performFiltering(CharSequence cs) {
            String filterSeq = cs.toString().toLowerCase();
            FilterResults result = new FilterResults();
            if (filterSeq != null && filterSeq.length() > 0) {
                ArrayList<T> filter = new ArrayList<T>();

                for (T object : sourceObjects) {
                    // the filtering itself:
                    if (object.toString().toLowerCase().contains(filterSeq))
                        filter.add(object);
                }
                result.count = filter.size();
                result.values = filter;
            } else {
                // add all objects
                synchronized (this) {
                    result.values = sourceObjects;
                    result.count = sourceObjects.size();
                }
            }
            return result;
        }

        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint,
                                      FilterResults results) {
            // NOTE: this function is *always* called from the UI thread.
            ArrayList<T> filtered = (ArrayList<T>) results.values;
            notifyDataSetChanged();
            clear();
            for (int i = 0, l = filtered.size(); i < l; i++)
                add((String) filtered.get(i));
            notifyDataSetInvalidated();
        }
    }

    /*public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        searchFile.clear();
        if (charText.length()==0) {

            searchFile = this.filename;

        }
        else
        {

            final String text = charText;
            new Thread() {
                public void run() {
                    for (String s : filename)
                    {
                        if (s.toLowerCase(Locale.getDefault())
                                .contains(text))

                        {
                            searchFile.add(s);
                *//*if (s.substring(0).contains(charText)) {
                    searchFile.add(s);
                }*//*
                        }
                    }
                }
            }.run();
        }
        notifyDataSetChanged();
    }*/
}
