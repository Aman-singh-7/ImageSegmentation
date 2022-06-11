package com.example.imagesegmentation.UI;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager2.widget.ViewPager2;

import android.Manifest;
import android.content.pm.PackageManager;
import android.hardware.biometrics.BiometricManager;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.example.imagesegmentation.R;
import com.example.imagesegmentation.UI.adapters.viewPagerAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class MainActivity extends AppCompatActivity {
    private viewPagerAdapter pagerAdapter;
    private ViewPager2 fragmentPager;
    private TabLayout tabLayout;
    private ActivityResultLauncher singleRequest;
    private  ActivityResultLauncher multipleRequest;
    private String [] permissions=new String[]{
            Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.CAMERA};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        multipleRequest=registerForActivityResult(new ActivityResultContracts.RequestMultiplePermissions(),isGranted->{
            for(String s:permissions) {
                if (isGranted.get(s))
                {

                }else
                {
                    Log.v("Permissions ","Not granted "+s);
                    finish();
                }
            }
        });
        if(!checkpermission(permissions))
            multipleRequest.launch(permissions);

        pagerAdapter=new viewPagerAdapter(getSupportFragmentManager(),getLifecycle());
        fragmentPager=findViewById(R.id.viewPager);
        fragmentPager.setAdapter(pagerAdapter);
        tabLayout=findViewById(R.id.tabLayout);
        new TabLayoutMediator(tabLayout,fragmentPager,(tab, position) -> {
            if(position==0)
            {
                tab.setText(R.string.Real_time);
            }
            else if(position==1)
            {
                tab.setText(R.string.snap);
            }
        }).attach();
    }

    private boolean checkpermission(String... permissions)
    {
        for(String s:permissions)
        {
            if(ContextCompat.checkSelfPermission(getApplicationContext(),s)!= PackageManager.PERMISSION_GRANTED)
            {
                return false;
            }
        }

        return true;
    }


}