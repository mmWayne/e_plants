package com.example.e_plants;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.SurfaceView;
import android.view.WindowManager;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.CameraActivity;
import org.opencv.android.CameraBridgeViewBase;
import org.opencv.android.CameraBridgeViewBase.CvCameraViewListener2;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class frag_second_A extends CameraActivity implements CvCameraViewListener2 {
    private Button buttonRecord;
    private Button buttonStop;
    private CameraBridgeViewBase mOpenCvCameraView;
    List<MatOfPoint> cnts;
    Mat mRgba;
    Mat curr_gray;
    Mat prev_gray;
    Mat diff;
    Mat mPicture;
    Bitmap bitmap;
    Boolean is_in;
    private int PICTURE_CODE = 100;
    private boolean mIsJavaCamera = true;
    private MenuItem mItemSwitchCamera = null;
    private static final String TAG = "OCVSample::Activity";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "called onCreate");
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        is_in = false;
        setContentView(R.layout.fragment_second);

        mOpenCvCameraView = (CameraBridgeViewBase) findViewById(R.id.surface_view);

        mOpenCvCameraView.setVisibility(SurfaceView.VISIBLE);

        mOpenCvCameraView.setCvCameraViewListener(this);

       /* buttonRecord = findViewById(R.id.record);
        buttonRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mRgba != null) {
                    if (!mRgba.empty()) {
                        Bitmap bmp = null;
                        try {
                            bmp = Bitmap.createBitmap(mRgba.cols(), mRgba.rows(), Bitmap.Config.ARGB_8888);
                            Utils.matToBitmap(mRgba, bmp); //将MAT对象转换为bmp对象
                            Log.d(TAG, "mat to bmp.");
                        } catch (CvException e) {
                            Log.d(TAG, e.getMessage());
                        }

                        FileOutputStream out = null;
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
                        String fileName = sdf.format(new Date()) + ".png";

                        File sd = new File(Environment.getExternalStorageDirectory() + "/Pictures/OpenCV");
                        boolean success = true;
                        if(!sd.exists()) {
                            success = sd.mkdir();
                        }
                        if(success) {
                            File dest = new File(sd, fileName);

                            try {
                                out = new FileOutputStream(dest);
                                bmp.compress(Bitmap.CompressFormat.PNG, 100, out);
                                //Imgcodecs.imwrite(dest.toString(), mRgba);
                                Toast.makeText(frag_second_A.this, "Picture has saved in " + dest.toString(), Toast.LENGTH_SHORT).show();
                            } catch (Exception e) {
                                e.printStackTrace();
                                Log.d(TAG, e.getMessage());
                            } finally {
                                try {
                                    if(out != null) {
                                        out.close();
                                        Log.d(TAG, "success save");
                                    }
                                } catch (IOException e) {
                                    Log.d(TAG, e.getMessage() + "Error");
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                }
            }

        });*/

        //buttonStop = findViewById(R.id.stoprecord);

    }

    private BaseLoaderCallback mLoaderCallback = new BaseLoaderCallback(this) {
        @Override
        public void onManagerConnected(int status) {
            switch (status) {
                case LoaderCallbackInterface.SUCCESS: {
                    Log.i(TAG, "OpenCV loaded successfully");
                    mOpenCvCameraView.enableView();
                }
                break;
                default: {
                    super.onManagerConnected(status);
                }
                break;
            }
        }
    };

   /* @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICTURE_CODE && data != null) {

            bitmap = (Bitmap) data.getExtras().get("data");
            mPicture = new Mat();
            Imgproc.cvtColor(mPicture, mPicture, Imgproc.COLOR_RGB2GRAY);
            Utils.bitmapToMat(bitmap, mPicture);


        }
    }*/

    @Override
    public void onPause() {
        super.onPause();
        if (mOpenCvCameraView != null)
            mOpenCvCameraView.disableView();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!OpenCVLoader.initDebug()) {
            Log.d(TAG, "Internal OpenCV library not found. Using OpenCV Manager for initialization");
            OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_3_0_0, this, mLoaderCallback);
        } else {
            Log.d(TAG, "OpenCV library found inside package. Using it!");
            mLoaderCallback.onManagerConnected(LoaderCallbackInterface.SUCCESS);
        }
    }

    @Override
    protected List<? extends CameraBridgeViewBase> getCameraViewList() {
        return Collections.singletonList(mOpenCvCameraView);
    }

    public void onDestroy() {
        super.onDestroy();
        if (mOpenCvCameraView != null)
            mOpenCvCameraView.disableView();
    }

    public void onCameraViewStarted(int width, int height) {
        mRgba = new Mat(height, width, CvType.CV_8UC4);
        curr_gray = new Mat(height, width, CvType.CV_8UC4);
        prev_gray = new Mat(height, width, CvType.CV_8UC1);
        diff = new Mat();
        cnts = new ArrayList<MatOfPoint>();
    }

    public void onCameraViewStopped() {
        mRgba.release();
        curr_gray.release();
        prev_gray.release();
        diff.release();

    }

    public Mat onCameraFrame(CameraBridgeViewBase.CvCameraViewFrame inputFrame) {
        if (!is_in) {
            prev_gray = inputFrame.gray();
            is_in = true;
            return prev_gray;
        }
        mRgba = inputFrame.rgba();
        curr_gray = inputFrame.gray();

        Core.absdiff(curr_gray, prev_gray, diff);
        prev_gray = curr_gray.clone();
        Imgproc.threshold(diff, diff, 40, 255, Imgproc.THRESH_BINARY);
        Imgproc.findContours(diff, cnts, new Mat(), Imgproc.RETR_TREE, Imgproc.CHAIN_APPROX_SIMPLE);
        Imgproc.drawContours(mRgba, cnts, -1, new Scalar(0, 255, 0), 1);


        for (MatOfPoint m : cnts) {
            Rect r = Imgproc.boundingRect(m);
            Imgproc.rectangle(mRgba, r, new Scalar(0, 0, 255), 3);
        }
        cnts.clear();
        /*mRgba = inputFrame.rgba();
        Imgproc.Canny(inputFrame.gray(), mIntermediateMat, 80, 100);
        Imgproc.cvtColor(mIntermediateMat, mRgba, Imgproc.COLOR_GRAY2RGBA, 4);*/
        //measureContours(inputFrame.rgba(), mRgba);
        return mRgba;
    }

    /*private void measureContours(Mat src, Mat dst) {

        Mat gray = new Mat();

        Mat binary = new Mat();

// 二值
        Imgproc.cvtColor(src, gray, Imgproc.COLOR_BGR2GRAY);

        Imgproc.threshold(gray, binary, 0, 255, Imgproc.THRESH_BINARY | Imgproc.THRESH_OTSU);

// 輪廓發現

        List<MatOfPoint> contours = new ArrayList<MatOfPoint>();

        Mat hierarchy = new Mat();

        Imgproc.findContours(binary, contours, hierarchy, Imgproc.RETR_TREE, Imgproc.CHAIN_APPROX_SIMPLE, new Point(0, 0));

// 測量輪廓

        dst.create(src.size(), src.type());

        for (int i = 0; i < contours.size(); i++) {

            Rect rect = Imgproc.boundingRect(contours.get(i));

            double w = rect.width;

            double h = rect.height;

            double rate = Math.min(w, h) / Math.max(w, h);

            Log.i("Bound Rect", "rate：" + rate);

            RotatedRect minRect = Imgproc.minAreaRect(new MatOfPoint2f(contours.get(i).toArray()));

            w = minRect.size.width;

            h = minRect.size.height;

            rate = Math.min(w, h) / Math.max(w, h);

            Log.i("Min Bound Rect", "rate：" + rate);

            double area = Imgproc.contourArea(contours.get(i), false);

            double arclen = Imgproc.arcLength(new MatOfPoint2f(contours.get(i).toArray()), true);

            Log.i("contourArea", "area：" + rate);

            Log.i("arcLength", "arcLength：" + arclen + "\n");

            Imgproc.drawContours(dst, contours, i, new Scalar(0, 0, 255), 1);
            Imgproc.putText(dst, area + "", new Point(0, 0), 1, 2.0, new Scalar(255, 0, 0));

        }
    }*/


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.setting:
                Intent intent = new Intent(frag_second_A.this, com.example.e_plants.frag_sixth_A.class);
                startActivity(intent);
                return true;

            case R.id.it_first2:
                Intent first2 = new Intent(frag_second_A.this, com.example.e_plants.diary.class);
                startActivity(first2);
                return true;

            case R.id.it_add:
                Intent adds = new Intent(frag_second_A.this, com.example.e_plants.new_plant_A.class);
                startActivity(adds);
                return true;

            case R.id.it_first:
                Intent first = new Intent(frag_second_A.this, com.example.e_plants.frag_first_A.class);
                startActivity(first);
                return true;
            case R.id.logout:
                FirebaseAuth.getInstance().signOut();
                finish();
                Intent logOut = new Intent(frag_second_A.this, com.example.e_plants.login_A.class);
                startActivity(logOut);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}

