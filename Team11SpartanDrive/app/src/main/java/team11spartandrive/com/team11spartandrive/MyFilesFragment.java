package team11spartandrive.com.team11spartandrive;

import android.app.FragmentManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import team11spartadrive.com.helper.DeleteFile;
import team11spartadrive.com.helper.DriveFiles;
import team11spartadrive.com.helper.SearchFile;

public class MyFilesFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    EditText myFilter;

    private OnFragmentInteractionListener mListener;

    // TODO: Rename and change types and number of parameters
    public static MyFilesFragment newInstance(String param1, String param2) {
        MyFilesFragment fragment = new MyFilesFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);

        return fragment;
    }

    static MyFilesFragment myFilesFragment_instance = null;
    ListView lv;
 //   ArrayAdapter<String> adapter;
    CustomListAdapter ad;
    android.support.v4.app.FragmentTransaction ft ;


    public static MyFilesFragment getFragmentInstance(){

        if(myFilesFragment_instance == null){
            myFilesFragment_instance = new MyFilesFragment();
            return myFilesFragment_instance;
        }
        else{
            return myFilesFragment_instance;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        //Add Menu Options
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(
            Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.fragment_myfiles, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle item selection
        switch (item.getItemId()) {
            case R.id.add_item:
                new AddActionHandler(getContext()).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    //=======================================

    public void refresh(){
        Log.d("Status", " >>> Going to refresh the fragment files content");
        getActivity().getSupportFragmentManager().beginTransaction().detach(this).attach(this).commit();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_myfiles, container, false);
        lv = (ListView) rootView.findViewById(R.id.listView);
        myFilter=(EditText) rootView.findViewById(R.id.myFilter);
    //    adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1,DriveFiles.getDriveFileInstance().getFileNameList());

         ad = new CustomListAdapter(getActivity(),DriveFiles.getDriveFileInstance().getFileNameList(),DriveFiles.getDriveFileInstance().getFile_desc_list());

        lv.setAdapter(ad);

        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View rootView, int position,
                                           long id) {
                // TODO Auto-generated method stub
                //DriveFiles driveFiles = (DriveFiles) rootView.getTag();

                //get ID from file name
                //Log.d("file name------> ", lv.getItemAtPosition((int) id).toString());
                String temp_ID = DriveFiles.getDriveFileInstance().getIdFromName(lv.getItemAtPosition((int)id).toString());
                //Log.d("ID -------> ",temp_ID);
                new PopupOfAction(getContext(), temp_ID).show();

                //Toast toast = Toast.makeText(getActivity(),String.valueOf(lv.getItemAtPosition((int) id)),Toast.LENGTH_LONG);
                //toast.show();

//                for(File file : DriveFiles.getDriveFileInstance().getfiles()){
//                    Log.d("LINK---->", file.getAlternateLink());
//                   // Log.d("Download", file.getDownloadUrl());
//                }

            Intent emailIntent = new Intent(Intent.ACTION_SEND);
                emailIntent.setType("vnd.android.cursor.dir/email");

                //String to[] = {"asd@gmail.com"};

                //emailIntent .putExtra(Intent.EXTRA_EMAIL, to);

// the attachment

                java.io.File file_t = new java.io.File("file///mnt/sdcard0/Resume_jainam.pdf");
                Uri uri = Uri.fromFile(file_t);


               emailIntent .putExtra(Intent.EXTRA_STREAM,Uri.parse("file///mnt/sdcard0/DCIM/"));

                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Subject");

                startActivity(Intent.createChooser(emailIntent , "Send email..."));

                // emailIntent.setType("text/plain");

                // emailIntent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(pic));d

                // startActivity(Intent.createChooser(emailIntent,"Share you on the jobing"));

                // startActivity(emailIntent);

                // Return true to consume the click event. In this case the

                // onListItemClick listener is not called anymore.

                //*Log.v("long clicked", "pos: " + pos);

                return true;
            }
        });


        myFilter.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                // When user changed the Text
                MyFilesFragment.this.ad.getFilter().filter(cs);
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                          int arg3) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub
            }
        });

        return rootView;

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }
    //========================================

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

}