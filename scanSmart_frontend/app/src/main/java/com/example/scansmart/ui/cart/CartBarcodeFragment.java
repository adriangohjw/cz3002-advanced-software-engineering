package com.example.scansmart.ui.cart;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.scansmart.MainActivity2;
import com.example.scansmart.R;
import com.example.scansmart.RequestSingleton;
import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class CartBarcodeFragment extends Fragment {

    //displays camera preview images
    SurfaceView surfaceView;
    TextView txtBarcodeValue;
    TextView productId;
    private BarcodeDetector barcodeDetector;
    private CameraSource cameraSource;
    private static final int REQUEST_CAMERA_PERMISSION = 201;
    ImageButton btnAction;
    String intentData = "";
    boolean goNext;
    int userID;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        container.removeAllViews();
        View root = inflater.inflate(R.layout.fragment_cartbarcode, container, false);
        userID = ((MainActivity2) getActivity()).getUserID();
        txtBarcodeValue = root.findViewById(R.id.txtBarcodeValue);

        surfaceView = root.findViewById(R.id.surfaceView1);
        productId = (TextView) root.findViewById(R.id.product_id);
        btnAction = root.findViewById(R.id.btnAction1);
        AndroidNetworking.initialize(getActivity().getApplicationContext());


        surfaceView = root.findViewById(R.id.surfaceView);
        btnAction = root.findViewById(R.id.btnAction);
        //AndroidNetworking.initialize(getActivity().getApplicationContext());

        btnAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String product = productId.getText().toString();

                String url = String.format("https://cz-3002-scansmart-api-7ndhk.ondigitalocean.app/cart_products?shopper_id=%1$s&product_id=%2$s",
                        userID,
                        product);
                // Request a string response from the provided URL.
                StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                // Display the first 500 characters of the response string.
                                Log.wtf("Yay123", "Yay123");
                                //go back to shopping cart
                                Fragment fragment = new ShoppingCartFragment();
                                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                fragmentTransaction.replace(R.id.fragment_cartbarcode, fragment);
                                fragmentTransaction.addToBackStack(null);
                                fragmentTransaction.commit();
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.v("error" , "error");
                    }


                });
                // Add the request to the RequestQueue.
                RequestSingleton.getInstance(getActivity()).addToRequestQueue(stringRequest);
            }});


        return root;
    }

    private void initialiseDetectorsAndSources() {

        barcodeDetector = new BarcodeDetector.Builder(getActivity())
                .setBarcodeFormats(Barcode.ALL_FORMATS)
                .build();

        //creates cameraSource
        //manages camera in conjunction with an underlying detector ie. SurfaceView
        cameraSource = new CameraSource.Builder(getActivity(), barcodeDetector)
                .setRequestedPreviewSize(1920, 1080)
                .setAutoFocusEnabled(true) //you should add this feature
                .build();

        //SurfaceHolder.Callback() used to receive information about changes that occur in the surface ie. camera preview
        surfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            //called when size or the format of the surface changes
            public void surfaceCreated(SurfaceHolder holder) {
                try {
                    if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                        //opens the camera and starts sending preview frames to the SurfaceView
                        cameraSource.start(surfaceView.getHolder());
                    } else {
                        ActivityCompat.requestPermissions(getActivity(), new
                                String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION);
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            //called in the first instance when the surface is created
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            }

            @Override
            //called when the surface is destroyed
            public void surfaceDestroyed(SurfaceHolder holder) {
                cameraSource.stop();
            }
        });

        //assign a processor on the barcode detector
        barcodeDetector.setProcessor(new Detector.Processor<Barcode>() {
            @Override
            public void release() {
                //Toast.makeText(getActivity().getApplicationContext(), "To prevent memory leaks barcode scanner has been stopped", Toast.LENGTH_SHORT).show();
            }

            @Override
            //receives the qr code from the camera preview and adds them in the SparseArray
            public void receiveDetections(Detector.Detections<Barcode> detections) {
                final SparseArray<Barcode> barcodes = detections.getDetectedItems();
                if (barcodes.size() != 0) {

                    txtBarcodeValue.post(new Runnable() {

                        @Override
                        public void run() {
                            intentData = barcodes.valueAt(0).displayValue;
                            //displays value of qr code in a runnable because barcodes are detected in a background thread
                            txtBarcodeValue.setText(intentData);
                            String url = String.format("https://cz-3002-scansmart-api-7ndhk.ondigitalocean.app/cart_products?shopper_id=%1$s&product_id=%2$s",
                                    userID,
                                    intentData);
                            // Request a string response from the provided URL.
                            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                                    new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String response) {
                                            // Display the first 500 characters of the response string.
                                            Log.wtf("Yay123", "Yay123");
                                            //go back to shopping cart
                                            Fragment fragment = new ShoppingCartFragment();
                                            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                            fragmentTransaction.replace(R.id.fragment_cartbarcode, fragment);
                                            fragmentTransaction.addToBackStack(null);
                                            fragmentTransaction.commit();
                                        }
                                    }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Log.v("error" , "error");
                                }


                            });
                            // Add the request to the RequestQueue.
                            RequestSingleton.getInstance(getActivity()).addToRequestQueue(stringRequest);
                        }
                    });
                }
            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();
        cameraSource.release();
    }

    @Override
    public void onResume() {
        super.onResume();
        initialiseDetectorsAndSources();
    }
}


