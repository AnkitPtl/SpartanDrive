package team11spartandrive.com.team11spartandrive;

/**
 * Created by harsh on 11/29/2015.
 */

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

import team11spartadrive.com.helper.DriveFiles;

public class CustomListAdapter extends ArrayAdapter<String> {

    private final Activity context;
    private final List<String> itemname;
    private final List<String> desc;
    //private final Integer [] imgid;
    private int imgid = R.mipmap.folder;
    Map<String,String> file_ext_list;

    public CustomListAdapter(Activity context, List<String> itemname,List<String> desc) {

        super(context, R.layout.mylist, itemname);
        // TODO Auto-generated constructor stub

        this.context=context;
        this.itemname=itemname;
        file_ext_list = DriveFiles.getDriveFileInstance().getFile_ext_list();
        this.desc=desc;
    }

    public View getView(int position,View view,ViewGroup parent) {

        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.mylist, null,true);

        TextView txtTitle = (TextView) rowView.findViewById(R.id.item);
        TextView subText =(TextView) rowView.findViewById(R.id.subItem);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
        txtTitle.setText(itemname.get(position).toString());//+ desc.get(position));
        subText.setText(desc.get(position).toString());

        try {
            if (file_ext_list.get(itemname.get(position).toString()).equals("pdf")) {
                imgid = R.mipmap.pdf;
            } else if (file_ext_list.get(itemname.get(position).toString()).equals("doc") || file_ext_list.get(itemname.get(position).toString()).equals("docx")) {
                imgid = R.mipmap.word;
            } else if (file_ext_list.get(itemname.get(position).toString()).equals("xls")) {
                imgid = R.mipmap.xls;
            } else if (file_ext_list.get(itemname.get(position).toString()).equals("ppt")) {
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
}
