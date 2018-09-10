package com.example.hamza.camerademo;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceView;
import android.widget.Toast;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.CameraBridgeViewBase;
import org.opencv.android.JavaCameraView;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.core.CvType;
import org.opencv.core.Mat;

public class MainActivity extends AppCompatActivity implements CameraBridgeViewBase.CvCameraViewListener2 {

    public static final int t  = 8675309;
    public static String TAG = "MainActivity";
    JavaCameraView javacameraview;
    Mat mrgba;
    BaseLoaderCallback mLoaderCallback= new BaseLoaderCallback(this) {
        @Override
        public void onManagerConnected(int status) {
            super.onManagerConnected(status);
            switch(status){
                case BaseLoaderCallback.SUCCESS:{
                    javacameraview.enableView();
                    break;
                }
                default:{
                    super.onManagerConnected(status);
                    break;
                }
            }
        }

    };


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       // if(checkSelfPermission(Manifest.permission.CAMERA)== PackageManager.PERMISSION_GRANTED){
            javacameraview=(JavaCameraView)findViewById(R.id.java_camera_view);
            javacameraview.setVisibility(SurfaceView.VISIBLE);
            javacameraview.setCvCameraViewListener(this);
        //}
        //else {
          //  String[] permissionRequest = {Manifest.permission.CAMERA};
            //requestPermissions(permissionRequest,  CAMERA_PERMISSION_REQUEST_CODE);

//        }


    }

    //@Override
    //public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
     //   super.onRequestPermissionsResult(requestCode, permissions, grantResults);
      //  if(requestCode == CAMERA_PERMISSION_REQUEST_CODE){
       //     if(grantResults[0]== PackageManager.PERMISSION_GRANTED){
        //        javacameraview=(JavaCameraView)findViewById(R.id.java_camera_view);
         //       javacameraview.setVisibility(SurfaceView.VISIBLE);
          //      javacameraview.setCvCameraViewListener(this);
           // }
            //else {
             //   Toast.makeText(this, "can not open camera without permission",Toast.LENGTH_LONG).show();
            //}
       // }
   // }

    @Override
    protected void onPause(){
        super.onPause();
        if(javacameraview!=null)
            javacameraview.disableView();
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        if(javacameraview!=null)
            javacameraview.disableView();
    }
    @Override
    protected void onResume(){
        super.onResume();
        if(OpenCVLoader.initDebug()){
            Log.i(TAG,"openCV loaded successfully");
            mLoaderCallback.onManagerConnected(LoaderCallbackInterface.SUCCESS);
        }
        else{
            Log.i(TAG,"openCV not loaded");
            OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_2_4_9,this,mLoaderCallback);
        }
    }

    @Override
    public void onCameraViewStarted(int width, int height) {
        mrgba = new Mat(width,width, CvType.CV_8UC4);
    }

    @Override
    public void onCameraViewStopped() {
        mrgba.release();
    }

    @Override
    public Mat onCameraFrame(CameraBridgeViewBase.CvCameraViewFrame inputFrame) {
        return mrgba = inputFrame.rgba();
    }
}
