package com.example.plasticfreeriver;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Calendar;
import java.util.Date;
import androidx.fragment.app.Fragment;

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

//import com.example.plasticfreeriver.ml.BestFloat16;
////import com.example.plasticfreeriver.ml.BestFloat32;
//import com.example.plasticfreeriver.ml.ModelPlastic;
import com.example.plasticfreeriver.ml.ModelPlastic;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.tensorflow.lite.schema.ResizeBilinearOptions;
import org.tensorflow.lite.support.common.ops.NormalizeOp;
import org.tensorflow.lite.support.image.TensorImage;
import org.tensorflow.lite.support.image.ops.Rot90Op;
import org.tensorflow.lite.support.label.Category;

import java.io.IOException;
import java.util.List;
import org.tensorflow.lite.DataType;
import org.tensorflow.lite.support.image.ImageProcessor;
import org.tensorflow.lite.support.image.TensorImage;
import org.tensorflow.lite.support.image.ops.ResizeOp;
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer;

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
    EditText title_editText;
    Button submit_post,chooseImg;
    String title;
    ImageView im;
    public String location;
    FirebaseStorage storage;
    FirebaseDatabase database;
    ImageProcessor imageProcessor;
String resultfromModel;
View img;
Uri global_uriMap,imageUri;

    public home1Fragment() {
        // Required empty public constructor
    }
    private String getSavedStringFromSharedPreferences() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(requireContext());
        return sharedPreferences.getString("key",""); // The second parameter is the default value if the key is not found.
    }
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
        database=FirebaseDatabase.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

View rootview=inflater.inflate(R.layout.fragment_home1, container, false);

 chooseImg=rootview.findViewById(R.id.chooseImg);
 submit_post=rootview.findViewById(R.id.btn_post);
        img=rootview.findViewById(R.id.imageView);
        View locate=rootview.findViewById(R.id.locate);
        title_editText=(EditText)rootview.findViewById(R.id.editTextTitle);
submit_post.setOnClickListener(this);

locate.setOnClickListener(this);

chooseImg.setOnClickListener(this);

        TextView tv=rootview.findViewById(R.id.textView);

        //Log.d(this.getArguments().getString("location"));
     //   tv.setText(resultfromModel);
        String s= "https://maps.google.com/maps?q="+location;
      global_uriMap =Uri.parse(s);
        return rootview;
    }

    @Override
    public void onClick(View view) {

            Log.d("YourFragment", "Button is clicked!");
            if(view.getId()==R.id.chooseImg) {
                ImagePicker.with(this)

                        .crop()                    //Crop image(Optional), Check Customization for more option
                        .compress(1024)            //Final image size will be less than 1 MB(Optional)
                        .maxResultSize(1024, 1024)    //Final image resolution will be less than 1080 x 1080(Optional)
                        // Path: /storage/sdcard0/Android/data/package/files/ImagePicker
                        .start();
            }
            if(view.getId()==R.id.locate)
            {
              openMap(global_uriMap);
            }
            if (view.getId()==R.id.btn_post)
            {

             title=   title_editText.getText().toString();

                Date currentTime = Calendar.getInstance().getTime();
final StorageReference reference=storage.getReference().child("Image").child("username"+currentTime);
                 if(imageUri!=null ) {//do not accept empty ...//that lead to crash
                    // plastic(imageUri);

                                          //  model_32(imageUri);



                  //hereeeeeeeeeeeeee









                  //   model_32(imageUri);
                     reference.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                         @Override
                         public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                             reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                 @Override
                                 public void onSuccess(Uri uri) {
                                     post p1=new post();
                                     p1.setImg(uri.toString());
                                     p1.setGeotag_url("huu");
                                     p1.setPostedBy("gaurav");
                                     p1.setPostedAt(Long.toString(new Date().getTime()));
                                     p1.setTitle(title);
                                     database.getReference().child("posts")
                                             .push()
                                             .setValue(p1).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                 @Override
                                                 public void onSuccess(Void unused) {
                                                     Toast.makeText(getContext(), "posted succesfully", Toast.LENGTH_SHORT).show();
                                                 }
                                             });

                                 }
                             });
                             Toast.makeText(getContext(), "Uploaded", Toast.LENGTH_SHORT).show();
                         }
                     });
                 }
                 else
                     Toast.makeText(getContext(), "Please choose image", Toast.LENGTH_SHORT).show();
            }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            // Image Uri will not be null for RESULT_OK
            imageUri = data.getData();
            // Use Uri object instead of File to avoid storage permissions
            ImageView im=getView().findViewById(R.id.imageView);
            im.setImageURI(imageUri);
           // model_32(uri);
           //model_16(imageUri);
        } else if (resultCode == ImagePicker.RESULT_ERROR) {
            Toast.makeText(getActivity(), "ImagePicker.getError(data)", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getActivity(), "Task Cancelled", Toast.LENGTH_SHORT).show();
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
//    void plastic(Uri uri)
//    {
//        try {
//            BestFloat16 model = BestFloat16.newInstance(getContext());
//
//            ImageProcessor imageProcessor =
//                    new ImageProcessor.Builder()
//                            .add(new ResizeOp(1024, 1024, ResizeOp.ResizeMethod.BILINEAR))
//                            .build();
//            TensorImage tensorImage = new TensorImage(DataType.FLOAT32);
//            // Preprocess the image
//            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), uri);
//
//            tensorImage.load(bitmap);
//            tensorImage = imageProcessor.process(tensorImage);
//            // Creates inputs for reference.
//            TensorImage image = TensorImage.fromBitmap(bitmap);
//
//            // Runs model inference and gets result.
//            BestFloat16.Outputs outputs = model.process(tensorImage);
//            List<Category> output = outputs.getOutputAsCategoryList();
//
//
//
//
//            // Releases model resources if no longer used.
//            //model.close();
//        } catch (IOException e) {
//            Toast.makeText(getContext(), "model crashed", Toast.LENGTH_SHORT).show();// TODO Handle the exception
//        }
//
//    }
// void model_16(Uri uri) {
//     try {
//         BestFloat16 model = BestFloat16.newInstance(getContext());
//         Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), uri);
//         Bitmap resizedBitmap = Bitmap.createScaledBitmap(bitmap, 1024, 1024, false);
//         // Creates inputs for reference.
//         TensorImage image = TensorImage.fromBitmap(resizedBitmap);
//
//
//
//
//
//
//         ByteBuffer inputBuffer = getInputBuffer(resizedBitmap);
//
//         // Perform inference
//         float[][][][] outputArray = new float[1][1024][1024][3];
//         //model.run(inputBuffer, outputArray);
//
//         // Runs model inference and gets result.
//         BestFloat16.Outputs outputs = model.process(image);
//         List<Category> output = outputs.getOutputAsCategoryList();
//
//         // Releases model resources if no longer used.
//         model.close();
//     } catch (IOException e) {
//         // TODO Handle the exception
//     }
//
// }
//
//
    void model_32(Uri uri)
    {
        String className = "plastic";
        try {

            ModelPlastic model = ModelPlastic.newInstance(getContext());
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), uri);
            Bitmap resizedBitmap = Bitmap.createScaledBitmap(bitmap, 1024, 1024, false);
            ImageProcessor processor = new ImageProcessor.Builder().build();

            //TensorImage anotherTensorImage = processor.process();
           // resizedBitmap=imageProcessor.process(processor);
//            TensorBuffer inputfeature=TensorBuffer.createFixedSize(new int[]{1,1024,1024,3},DataType.FLOAT32);
//            inputfeature.loadBuffer(TensorImage.fromBitmap(resizedBitmap).getBuffer());
            // Creates inputs for reference.
            //TensorImage image = TensorImage.fromBitmap(resizedBitmap);

            // Runs model inference and gets result.
            ModelPlastic.Outputs output=model.process(TensorImage.fromBitmap(resizedBitmap));

            //ModelPlastic.Outputs outputs = model.process(inputfeature);
           // List<Category> output = outputs.getOutputAsCategoryList();

            // Releases model resources if no longer used.

            // Accessing the first element (index 0) in the output list
//            Category firstCategory = output.get(0);
//            String firstClassLabel = firstCategory.getLabel();
//            float firstConfidenceScore = firstCategory.getScore();


// Accessing the second element (index 1) in the output list
//            Category secondCategory = output.get(1);
//            String secondClassLabel = secondCategory.getLabel();
//            float secondConfidenceScore = secondCategory.getScore();
//resultfromModel=firstClassLabel+","+Float.toString(firstConfidenceScore);


            // Releases model resources if no longer used.
            model.close();
        } catch (IOException e) {
            // TODO Handle the exception


        }

    }
//
//    private ByteBuffer getInputBuffer(Bitmap inputBitmap) {
//        int bytesPerChannel = 4; // Assuming float32 input
//        int inputSize = 1024 * 1024 * 3 * bytesPerChannel; // 1024x1024x3x4
//        ByteBuffer inputBuffer = ByteBuffer.allocateDirect(inputSize);
//        inputBuffer.order(ByteOrder.nativeOrder());
//
//        // Convert Bitmap to ByteBuffer
//        int[] pixels = new int[1024 * 1024];
//        inputBitmap.getPixels(pixels, 0, inputBitmap.getWidth(), 0, 0, inputBitmap.getWidth(), inputBitmap.getHeight());
//        for (int pixel : pixels) {
//            inputBuffer.putFloat(((pixel >> 16) & 0xFF) / 255.0f);
//            inputBuffer.putFloat(((pixel >> 8) & 0xFF) / 255.0f);
//            inputBuffer.putFloat((pixel & 0xFF) / 255.0f);
//        }
//        return inputBuffer;
//    }

}
