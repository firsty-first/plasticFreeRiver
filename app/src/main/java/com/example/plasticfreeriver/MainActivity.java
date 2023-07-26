package com.example.plasticfreeriver;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.example.plasticfreeriver.databinding.ActivityMainBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
binding=ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        replaceFrag(new homeFragment());//by default home frag
        binding.bottomNavView.setOnItemSelectedListener(item -> {
            if(item.getItemId()==R.id.home)
                replaceFrag(new homeFragment());
                else if(item.getItemId()==R.id.feed)
                replaceFrag(new feedFragment());
                    else
                        replaceFrag(new insightFragment());


//            switch (item.getItemId()) {
//                case R.id.home:
//                    replaceFrag(new homeFragment());
//                    break;
//                case R.id.feed:
//                    replaceFrag(new feedFragment());
//                    break;
//                case R.id.insight:
//                    replaceFrag(new insightFragment());
//                    break;
//
//            }

            return  true;
        });
    }
    private  void replaceFrag(Fragment fragment)
    {
        FragmentManager fragmentManager=getSupportFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.framelayout,fragment);
        fragmentTransaction.commit();
    }
}