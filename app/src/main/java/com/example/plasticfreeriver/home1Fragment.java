package com.example.plasticfreeriver;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.plasticfreeriver.ml.BestFloat16;
//import com.example.plasticfreeriver.ml.BestFloat32;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.firebase.storage.FirebaseStorage;

import org.tensorflow.lite.support.image.TensorImage;
import org.tensorflow.lite.support.label.Category;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link home1Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class home1Fragment extends Fragment implements View.OnClickListener{

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    View title_editText;
    String title;
    public String location;
    FirebaseStorage storage;
String resultfromModel;
View img;
Uri global_uri;

    public home1Fragment() {
        // Required empty public constructor
    }
    private String getSavedStringFromSharedPreferences() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(requireContext());
        return sharedPreferences.getString("key",""); // The second parameter is the default value if the key is not found.
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment home1Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static home1Fragment newInstance(String param1, String param2) {
        home1Fragment fragment = new home1Fragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        location = getSavedStringFromSharedPreferences();
        storage=FirebaseStorage.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

View rootview=inflater.inflate(R.layout.fragment_home1, container, false);

View buttonview=rootview.findViewById(R.id.btn);
View submit_post=rootview.findViewById(R.id.btn_post);
        img=rootview.findViewById(R.id.imageView);
        View locate=rootview.findViewById(R.id.locate);
        title_editText=(EditText)rootview.findViewById(R.id.editTextTitle);
        if (title_editText != null) {
          //  String text = title_editText.getText().toString();
            // 'text' contains the text entered by the user in the EditText
        }


locate.setOnClickListener(this);

buttonview.setOnClickListener(this);

        TextView tv=rootview.findViewById(R.id.textView);
submit_post.setOnClickListener(this);
        //Log.d(this.getArguments().getString("location"));
     //   tv.setText(resultfromModel);
        String s= "https://maps.google.com/maps?q="+location;
      global_uri=Uri.parse(s);
        return rootview;
    }

    @Override
    public void onClick(View view) {

            Log.d("YourFragment", "Button is clicked!");
            if(view.getId()==R.id.btn) {
                ImagePicker.with(this)

                        .crop()                    //Crop image(Optional), Check Customization for more option
                        .compress(1024)            //Final image size will be less than 1 MB(Optional)
                        .maxResultSize(1080, 1080)    //Final image resolution will be less than 1080 x 1080(Optional)
                        // Path: /storage/sdcard0/Android/data/package/files/ImagePicker
                        .start();
            }
            if(view.getId()==R.id.locate)
            {
                 openMap(global_uri);

            }
            if (view.getId()==R.id.btn_post)
            {
             title=   title_editText.getText();

            }

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            // Image Uri will not be null for RESULT_OK
            Uri uri = data.getData();
            // Use Uri object instead of File to avoid storage permissions
            ImageView im=getView().findViewById(R.id.imageView);
            im.setImageURI(uri);


           // model_32(uri);
           model_16(uri);

        } else if (resultCode == ImagePicker.RESULT_ERROR) {
            Toast.makeText(getActivity(), "ImagePicker.getError(data)", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getActivity(), "Task Cancelled", Toast.LENGTH_SHORT).show();
        }
    }

 void model_16(Uri uri) {
     try {
         BestFloat16 model = BestFloat16.newInstance(getContext());
         Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), uri);
         Bitmap resizedBitmap = Bitmap.createScaledBitmap(bitmap, 448, 448, false);

         // Creates inputs for reference.
         TensorImage image = TensorImage.fromBitmap(resizedBitmap);

         // Runs model inference and gets result.
         BestFloat16.Outputs outputs = model.process(image);
         List<Category> output = outputs.getOutputAsCategoryList();

         // Releases model resources if no longer used.
         model.close();
     } catch (IOException e) {
         // TODO Handle the exception
     }

 }

    void openMap(Uri uri)
    {


        Intent imap=new Intent(Intent.ACTION_VIEW,uri);
        imap.setPackage("com.google.android.apps.maps");
        //com.google.android.apps.maps
    //
        if (imap.resolveActivity(getContext().getPackageManager()) != null) {

         startActivity(imap);
        }
        else
        {
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
//            if (intent.resolveActivity(getContext().getPackageManager()) != null) {
                //Toast.makeText(getContext(), "locating", Toast.LENGTH_SHORT).show();
                startActivity(intent);
    //    }
    }
      // startActivity(imap);
    }
//    void model_32(Uri uri)
//    {
//        try {
//            BestFloat32 model = BestFloat32.newInstance(getContext());
//
//            // Creates inputs for reference.
//            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), uri);
//
//            TensorImage image = TensorImage.fromBitmap(bitmap);
//
//            // Runs model inference and gets result.
//            BestFloat32.Outputs outputs = model.process(image);
//            List<Category> output = outputs.getOutputAsCategoryList();
//            // Accessing the first element (index 0) in the output list
////            Category firstCategory = output.get(0);
////            String firstClassLabel = firstCategory.getLabel();
////            float firstConfidenceScore = firstCategory.getScore();
//
//
//// Accessing the second element (index 1) in the output list
////            Category secondCategory = output.get(1);
////            String secondClassLabel = secondCategory.getLabel();
////            float secondConfidenceScore = secondCategory.getScore();
////resultfromModel=firstClassLabel+","+Float.toString(firstConfidenceScore);
//
//
//            // Releases model resources if no longer used.
//            model.close();
//        } catch (IOException e) {
//            // TODO Handle the exception
//
//
//        }
//
//    }

}
