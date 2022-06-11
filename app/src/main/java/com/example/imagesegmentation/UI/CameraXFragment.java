package com.example.imagesegmentation.UI;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.media.Image;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.camera.core.Camera;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageAnalysis;
import androidx.camera.core.ImageProxy;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.util.Size;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.imagesegmentation.R;
import com.example.imagesegmentation.UI.viewmodals.camXFragmentViewModal;
import com.google.common.util.concurrent.ListenableFuture;

import org.jetbrains.annotations.NotNull;

import java.nio.ByteBuffer;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CameraXFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CameraXFragment extends Fragment {
    private PreviewView cameraPreview;
    private camXFragmentViewModal viewModal;
    public CameraXFragment() {
        // Required empty public constructor
    }
    String Key="Started";
    public static CameraXFragment newInstance() {
        CameraXFragment fragment = new CameraXFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("CameraXFragment", "onCreate: " );
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View root= inflater.inflate(R.layout.fragment_camera_x, container, false);
        cameraPreview=root.findViewById(R.id.camera_preview);
        return  root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.e("CamreaXFragment","onViewCreated");

    }


    private void startCamera()
    {
        ListenableFuture<ProcessCameraProvider> cameraProviderFuture=  ProcessCameraProvider.getInstance(getContext());
        cameraProviderFuture.addListener(new Runnable() {
            @Override
            public void run() {
                try {
                    ProcessCameraProvider cameraProvider=cameraProviderFuture.get();
                    bindPreview(cameraProvider);
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },ContextCompat.getMainExecutor(getContext()));
    }



    public  void bindPreview(@NotNull ProcessCameraProvider cameraProvider)
    {
        cameraProvider.unbindAll();
        Preview preview=new Preview.Builder().build();
        CameraSelector cameraSelector=new CameraSelector.Builder().requireLensFacing(CameraSelector.LENS_FACING_BACK).build();
        preview.setSurfaceProvider(cameraPreview.getSurfaceProvider());
        ImageAnalysis imageAnalysis=new ImageAnalysis.Builder().setOutputImageFormat(ImageAnalysis.OUTPUT_IMAGE_FORMAT_RGBA_8888)
                        .setTargetResolution(new Size(25,25))
                                .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                                        .build();
        imageAnalysis.setAnalyzer(Executors.newSingleThreadExecutor(), new ImageAnalysis.Analyzer() {
            @Override
            public void analyze(@NonNull ImageProxy image) {

               Bitmap imageBitmap=toBitmap(image);
                Canvas canvas=new Canvas();
                Paint paint = new Paint();
                paint.setAntiAlias(true);
                paint.setFilterBitmap(true);
                paint.setDither(true);
                canvas.drawBitmap(imageBitmap,25,25,paint);


              // Log.e("ImageAnalysis",imageBitmap.toString());

               image.close();
            }
        });
        cameraProvider.bindToLifecycle(getViewLifecycleOwner(),cameraSelector,preview);
        
    }

    private Bitmap toBitmap(ImageProxy imageProxy)
    {
        byte[] R=imageProxy.getPlanes()[0].getBuffer().array();
        byte[] G=imageProxy.getPlanes()[1].getBuffer().array();
        byte[] B=imageProxy.getPlanes()[2].getBuffer().array();
        byte[] A=imageProxy.getPlanes()[3].getBuffer().array();
        int  [] pixels=new int[R.length];
        for(int i=0;i<R.length;i++)
        {
            pixels[i]=Color.argb(A[i],R[i],G[i],B[i]);
        }
        return  Bitmap.createBitmap(pixels,imageProxy.getWidth(),imageProxy.getHeight(), Bitmap.Config.ARGB_8888);


    }



    @Override
    public void onPause() {
        super.onPause();
        Log.e("CamreaXFragment", "onPause: ");

    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e("CameraXFragment", "onResume: " );

        startCamera();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e("CameraXFragment", "onDestroy: " );
    }
}