package com.example.plasticfreeriver;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;

import okhttp3.OkHttpClient;
import retrofit2.Callback;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

import androidx.annotation.NonNull;
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
import android.widget.Toast;

//import com.example.plasticfreeriver.ml.BestFloat16;
////import com.example.plasticfreeriver.ml.BestFloat32;
//import com.example.plasticfreeriver.ml.ModelPlastic;
import com.example.plasticfreeriver.databinding.FragmentHome1Binding;
import com.example.plasticfreeriver.ml.ModelPlastic;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.tensorflow.lite.support.image.TensorImage;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.tensorflow.lite.support.image.ImageProcessor;
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

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
    String imgUri;
    private String mParam2;
    EditText title_editText;
    Button submit_post,chooseImg;
    String title;
    ImageView im;
    private final String baseUrl = "https://plasticapi.onrender.com";
    private ApiService apiService;
    public String location;
    FirebaseStorage storage;
    FirebaseDatabase database;
    FragmentHome1Binding binding;
    ImageProcessor imageProcessor;
String resultfromModel;
View img;
Uri global_uriMap, imageuri;

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
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apiService = retrofit.create(ApiService.class);
        }
void IcanDoIt()
{
    OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .connectTimeout(300, TimeUnit.SECONDS)
            .readTimeout(300, TimeUnit.SECONDS)
            .writeTimeout(300, TimeUnit.SECONDS)
            .build();

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build();
apiService=retrofit.create(ApiService.class);
    // Example usage to get the welcome message
    Call<PredictionResponse> welcomeMessageCall = apiService.getWelcomeMessage();
    welcomeMessageCall.enqueue(new Callback<PredictionResponse>() {
        @Override
        public void onResponse(Call<PredictionResponse> call, Response<PredictionResponse> response) {
            if (response.isSuccessful()) {
                String welcomeMessage = response.body().getMessage();
                // Handle the welcome message as needed
                Log.d("result",welcomeMessage);
            }
            else
            {

                 }
        }

        @Override
        public void onFailure(Call<PredictionResponse> call, Throwable t) {
            t.printStackTrace();
            // Handle failure
        }

    });

    // Example usage to get the prediction
    String imgUrl = "https://firebasestorage.googleapis.com/v0/b/sagar-b4f59.appspot.com/o/Image%2FusernameMon%20Sep%2004%2019%3A54%3A44%20GMT%2B05%3A30%202023?alt=media&token=d4176501-2e1e-47be-a4b3-9a9b753a4519";
    ModelInput input = new ModelInput(imgUrl);
    Call<PredictionResponse> predictionCall = apiService.getPrediction(input);

    predictionCall.enqueue(new Callback<PredictionResponse>() {
        @Override
        public void onResponse(Call<PredictionResponse> call, Response<PredictionResponse> response) {
            if (response.isSuccessful()) {
                String predictionMessage = response.body().getMessage();
                Log.d("result",predictionMessage);
                // Handle the prediction message as needed
            } else {
                // Handle error
                Log.d("result","bruuuuuuuuuuuhhhhh fkd ");
            }
        }

        @Override
        public void onFailure(Call<PredictionResponse> call, Throwable t) {
            t.printStackTrace();
            // Handle failure
        }
    });


}
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

View rootview=inflater.inflate(R.layout.fragment_home1, container, false);
 chooseImg=rootview.findViewById(R.id.chooseImg);
 submit_post=rootview.findViewById(R.id.btn_post);
 Button button=rootview.findViewById(R.id.test);
        img=rootview.findViewById(R.id.imageView);
        View locate=rootview.findViewById(R.id.locate);
        title_editText=(EditText)rootview.findViewById(R.id.editTextTitle);
        button.setOnClickListener(this);
submit_post.setOnClickListener(this);

locate.setOnClickListener(this);

chooseImg.setOnClickListener(this);

      //  TextView tv=rootview.findViewById(R.id.textView);

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
        if(view.getId()==R.id.test)
        {
           IcanDoIt();
        }
            if (view.getId()==R.id.btn_post)
            {

             title=   title_editText.getText().toString();

                Date currentTime = Calendar.getInstance().getTime();
                final StorageReference reference=storage.getReference().child("Image").child("username"+currentTime);
                 if(imageuri !=null ) {//do not accept empty ...//that lead to crash
                    // plastic(imageUri);
                     //  model_32(imageUri);
                     //model_32(imageUri);
                     if(title.length()>5){
                     reference.putFile(imageuri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                             @Override
                         public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                             Log.d("zzzz","uploaded");
                             reference.getDownloadUrl().addOnFailureListener(new OnFailureListener() {
                                 @Override
                                 public void onFailure(@NonNull Exception e) {
                                     Log.d("zzzz","uploadeded but failed to get link");
                                 }
                             });
                             reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {






/*

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
                                             .setValue(p1).a
 */
                                 @Override
                                 public void onSuccess(Uri uri) {

                                     Log.d("zzzz","uploaded hurrah");
                                     post p1=new post();
                                     p1.setImg(uri.toString());
                                     p1.setGeotag_url(global_uriMap.toString());
                                     p1.setPostedBy("gaurav");

                                     p1.setCount("Count:"+Double.toString((Math.random()*1000)%28).substring(0,2));
                                     p1.setPostedAt(Long.toString(new Date().getTime()));
                                     p1.setTitle(title_editText.getText().toString());

                                    // Log.d("check",title_editText.getText().toString());
                                     database.getReference().child("posts")
                                             .push()
                                             .setValue(p1).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                 @Override
                                                 public void onSuccess(Void unused) {
                                                     Toast.makeText(getContext(), "posted succesfully", Toast.LENGTH_SHORT).show();
                                              }
                                             }).addOnFailureListener(new OnFailureListener() {
                                                 @Override
                                                 public void onFailure(@NonNull Exception e) {
                                                     Log.d("zzzz","failed for realtime db"+e.toString());
                                                 }
                                             });
                                 }

                             });
                             Toast.makeText(getContext(), "Uploaded", Toast.LENGTH_SHORT).show();

                         }

                     });
                 }
                 else
                         Toast.makeText(getContext(), "Enter title", Toast.LENGTH_SHORT).show();
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
            imageuri = data.getData();
            // Use Uri object instead of File to avoid storage permissions
            ImageView im=getView().findViewById(R.id.imageView);
            im.setImageURI(imageuri);
//            model_32(imageUri);
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


    void model_32(Uri uri) {
        try {
            ModelPlastic model = ModelPlastic.newInstance(getContext());
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), uri);
            Bitmap resizedBitmap = Bitmap.createScaledBitmap(bitmap, 1024, 1024, true);

            // Preprocess the image using the same ImageProcessor you've built
            TensorImage tensorImage = TensorImage.fromBitmap(resizedBitmap);
            ImageProcessor processor = new ImageProcessor.Builder().build();
            TensorImage preprocessedImage = processor.process(tensorImage);

            // Run model inference and get the output
            ModelPlastic.Outputs outputs = model.process(preprocessedImage);

            // Access the output tensor
            TensorBuffer outputTensor = outputs.getOutputAsTensorBuffer();

            // Process and display the output tensor
            int count =0;
            StringBuilder resultBuilder = new StringBuilder();
            int[] shape = outputTensor.getShape();
            for (int i = 0; i < shape[1]; i++) {
                resultBuilder.append("Label ").append(i).append(": ").append(Arrays.toString(outputTensor.getFloatArray()));
                resultBuilder.append("\n");
                float core[]=outputTensor.getFloatArray();
            //    for(int x=0;x<core.length/5;x++)
                //{

//                    if(core[x]>0.9) {
//                        count++;
//                        Log.d("count",Float.toString(core[x]));
//                    }
             //   }
            }
//            int count =0;
//            for (int i = 0; i < shape[1]; i++) {
//                float[] floatArray = outputTensor.getFloatArray();
//                if(floatArray[i]>0.5)
//                    count++;
//                resultBuilder.append("Label ").append(i).append(": ").append(Arrays.toString(outputTensor.getFloatArray()));
//
//            }
           // Log.d("count",Integer.toString(count));
            // Releases model resources if no longer used.
            model.close();

            // Display the result using a Toast message
//            Toast.makeText(getContext(), resultBuilder.toString(), Toast.LENGTH_LONG).show();
            Log.i("result", resultBuilder.toString());
        } catch (IOException e) {
            // TODO Handle the exception
        }
    }
    public static void extractLatLongFromImage(Context context, Uri imageUri) {
        try {
            ExifInterface exifInterface = new ExifInterface(context.getContentResolver().openInputStream(imageUri));

            float[] latLong = new float[2];
            if (exifInterface.getLatLong(latLong)) {
                float latitude = latLong[0];
                float longitude = latLong[1];

                Log.d("ImageExifExtractor", "Latitude: " + latitude);
                Log.d("ImageExifExtractor", "Longitude: " + longitude);
            } else {
                Log.d("ImageExifExtractor", "No Latitude and Longitude found in EXIF.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("ImageExifExtractor", "Error extracting EXIF data from image.");
        }
    }
}
