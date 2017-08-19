package com.lvgou.qdd.activity.verify;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.android.volley.VolleyError;
import com.lvgou.qdd.R;
import com.lvgou.qdd.activity.BaseActivity;
import com.lvgou.qdd.http.RequestCallback;
import com.lvgou.qdd.http.URLConst;
import com.lvgou.qdd.http.upload.ResponseListener;
import com.lvgou.qdd.http.upload.UploadApi;
import com.lvgou.qdd.util.Constant;
import com.lvgou.qdd.util.Logger;
import com.lvgou.qdd.util.ToastUtil;
import com.lvgou.qdd.util.TokenUtil;
import com.lvgou.qdd.view.SmsCoeView;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class EnterpriseActivity extends BaseActivity implements SmsCoeView.ISmsCoeView{

    private LinearLayout parentView;

    private Button backbutton;

    private Button completeButton;

    private EditText enterpriseNameInput;
    private EditText bankNumInput;
    private EditText licenseNumInput;
    private EditText enterpriseCodeInput;
    private EditText lawNameInput;
    private EditText lawIdNumInput;
    private EditText lawPhoneInput;


    private Button addViceLicernseButton;
    private Button addEnterpriseCodeButton;
    private Button addTaxiRegisterButton;
    private Button addIdNumFrontButton;
    private Button addIdNumBackButton;


    private Button enterpriseThree;
    private Button threeToOne;
    private Button fiveToOne;


    private ImageView addViceLicernseImage;
    private ImageView addEnterpriseCodeImage;
    private ImageView addTaxiRegisterImage;
    private ImageView addIdNumFrontImage;
    private ImageView addIdNumBackImage;

    private String viceLicernseImagePath;
    private String enterpriseCodeImagePath;
    private String taxiRegisterImagePath;
    private String idNumFrontImagePath;
    private String idNumBackImagePath;

    private SmsCoeView smsCoeView;

    private  static  final  int VICE_LICENSE_CODE = 1;
    private  static  final  int ENTERPRISE_CODE = 2;
    private  static  final  int TAXI_REGISTER_CODE = 3;
    private  static  final  int IDNUM_FRONT_CODE = 4;
    private  static  final  int IDNUM_BACK_CODE = 5;


    private int pictureStatus = 1;  //1证件执照副本图片 2组织机构代码证  3税务登记证  4身份证正面  5身份证反面

    private int signType = 0;//认证类型	0：企业三证1：三证合一2：五证合一


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void childImpl(Bundle savedInstanceState) {
        setContentView(R.layout.activity_enterprise);


        parentView = (LinearLayout) findViewById(R.id.EnterpriseActivity_scrollView);
        parentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager imm = (InputMethodManager)
                        getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
            }
        });

        setView();
        setClickListener();
    }


    private void setView() {
        backbutton = (Button) findViewById(R.id.EnterpriseActivity_backButton);
        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        completeButton = (Button) findViewById(R.id.EnterpriseActivity_complete);
        completeButton.setBackgroundColor(Color.TRANSPARENT);

        completeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                netRequest();
            }
        });

        enterpriseNameInput = (EditText) findViewById(R.id.EnterpriseActivity_enterprise_name_input);
        bankNumInput = (EditText) findViewById(R.id.EnterpriseActivity_enterprise_bankNum_input);
        licenseNumInput = (EditText) findViewById(R.id.EnterpriseActivity_enterprise_license_input);
        enterpriseCodeInput = (EditText) findViewById(R.id.EnterpriseActivity_enterprise_code_input);
        lawNameInput = (EditText) findViewById(R.id.EnterpriseActivity_law_name_input);
        lawIdNumInput = (EditText) findViewById(R.id.EnterpriseActivity_law_idNum_input);
        lawPhoneInput = (EditText) findViewById(R.id.EnterpriseActivity_law_phone_input);


        addViceLicernseButton = (Button) findViewById(R.id.addViceLicense_button);
        addEnterpriseCodeButton = (Button) findViewById(R.id.add_enterprise_code_button);
        addTaxiRegisterButton = (Button) findViewById(R.id.add_taxi_register_button);
        addIdNumFrontButton = (Button) findViewById(R.id.enterprise_person_front_add);
        addIdNumBackButton = (Button) findViewById(R.id.enterprise_person_back_add);

        enterpriseThree = (Button) findViewById(R.id.EnterpriseActivity_enterprise_certificate);
        threeToOne = (Button) findViewById(R.id.EnterpriseActivity_three_certificate);
        fiveToOne = (Button) findViewById(R.id.EnterpriseActivity_five_certificate);

        addViceLicernseImage = (ImageView) findViewById(R.id.addViceLicense_image);
        addEnterpriseCodeImage = (ImageView) findViewById(R.id.addViceLicense_image);
        addTaxiRegisterImage = (ImageView) findViewById(R.id.addViceLicense_image);
        addIdNumFrontImage = (ImageView) findViewById(R.id.addViceLicense_image);
        addIdNumBackImage = (ImageView) findViewById(R.id.addViceLicense_image);

        //设置bitmap铺满imageView大小
        addViceLicernseImage.setScaleType(ImageView.ScaleType.FIT_XY);
        addEnterpriseCodeImage.setScaleType(ImageView.ScaleType.FIT_XY);
        addTaxiRegisterImage.setScaleType(ImageView.ScaleType.FIT_XY);
        addIdNumFrontImage.setScaleType(ImageView.ScaleType.FIT_XY);
        addIdNumBackImage.setScaleType(ImageView.ScaleType.FIT_XY);


        smsCoeView = (SmsCoeView) findViewById(R.id.EnterpriseActivity_smsCode);
        smsCoeView.activity = this;
        smsCoeView.callback = this;
    }


    private void setClickListener() {
        addViceLicernseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAlbum();
                pictureStatus = 1;

            }
        });

        addEnterpriseCodeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAlbum();
                pictureStatus = 2;

            }
        });

        addTaxiRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAlbum();
                pictureStatus = 3;

            }
        });

        addIdNumFrontButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAlbum();
                pictureStatus = 4;

            }
        });

        addIdNumBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAlbum();
                pictureStatus = 5;

            }
        });



        enterpriseThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enterpriseThree.setBackgroundResource(R.drawable.button_radius_background);
                threeToOne.setBackgroundResource(R.drawable.button_radius);
                fiveToOne.setBackgroundResource(R.drawable.button_radius);


                //设置字体颜色
                enterpriseThree.setTextColor(getResources().getColorStateList(R.color.systemWhite));
                threeToOne.setTextColor(getResources().getColorStateList(R.color.systemBlack));
                fiveToOne.setTextColor(getResources().getColorStateList(R.color.systemBlack));
            }
        });

        threeToOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                threeToOne.setBackgroundResource(R.drawable.button_radius_background);
                enterpriseThree.setBackgroundResource(R.drawable.button_radius);
                fiveToOne.setBackgroundResource(R.drawable.button_radius);


                //设置字体颜色
                threeToOne.setTextColor(getResources().getColorStateList(R.color.systemWhite));
                enterpriseThree.setTextColor(getResources().getColorStateList(R.color.systemBlack));
                fiveToOne.setTextColor(getResources().getColorStateList(R.color.systemBlack));
            }
        });

        fiveToOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fiveToOne.setBackgroundResource(R.drawable.button_radius_background);
                enterpriseThree.setBackgroundResource(R.drawable.button_radius);
                threeToOne.setBackgroundResource(R.drawable.button_radius);


                //设置字体颜色
                fiveToOne.setTextColor(getResources().getColorStateList(R.color.systemWhite));
                enterpriseThree.setTextColor(getResources().getColorStateList(R.color.systemBlack));
                threeToOne.setTextColor(getResources().getColorStateList(R.color.systemBlack));
            }
        });

    }

    //打开相册
    private void openAlbum(){
        Intent albumIntent = new Intent(Intent.ACTION_PICK, null);
        albumIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        startActivityForResult(albumIntent, VICE_LICENSE_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        try {
            Bitmap bitmap = setImageViewAfterAblum(data);
            switch (requestCode){
                case VICE_LICENSE_CODE:
                    pictureStatus = 1;
                    addViceLicernseImage.setImageBitmap(bitmap);
                    break;
                case ENTERPRISE_CODE:
                    pictureStatus = 2;
                    addEnterpriseCodeImage.setImageBitmap(setImageViewAfterAblum(data));
                    break;
                case TAXI_REGISTER_CODE:
                    pictureStatus = 3;
                    addTaxiRegisterImage.setImageBitmap(setImageViewAfterAblum(data));
                    break;
                case IDNUM_FRONT_CODE:
                    pictureStatus = 4;
                    addIdNumFrontImage.setImageBitmap(setImageViewAfterAblum(data));
                    break;
                case IDNUM_BACK_CODE:
                    pictureStatus = 5;
                    addIdNumBackImage.setImageBitmap(setImageViewAfterAblum(data));
                    break;

                default:
                    break;
            }

            uploadPicture(bitmap);
        }catch (Exception e){
            Log.i("exception",e.toString());
        }


    }


    /********************************************************************/
    //关于 相册 详见  http://blog.csdn.net/jdsjlzx/article/details/51181229
    private Bitmap setImageViewAfterAblum(Intent data) throws FileNotFoundException, IOException {
        Uri originalUri = null;
        File file = null;
        Logger.getInstance(getApplicationContext()).info("newbitmap is :" + data.getData());
        if (null != data && data.getData() != null) {
            originalUri = data.getData();
            file = getFileFromMediaUri(this, originalUri);
        }
        //                    Bitmap photoBmp = getBitmapFormUri(this, Uri.fromFile(file));
        Bitmap photoBmp = getBitmapFormUri(this, originalUri);
        addViceLicernseImage.setImageBitmap(photoBmp);

        int degree = getBitmapDegree(file.getAbsolutePath());
        /**
         * 把图片旋转为正的方向
         */
        Bitmap newbitmap = rotateBitmapByDegree(photoBmp, degree);

        return newbitmap;

    }


    public static Bitmap getBitmapFormUri(Activity ac, Uri uri) throws FileNotFoundException, IOException {
        InputStream input = ac.getContentResolver().openInputStream(uri);
        BitmapFactory.Options onlyBoundsOptions = new BitmapFactory.Options();
        onlyBoundsOptions.inJustDecodeBounds = true;
        onlyBoundsOptions.inDither = true;//optional
        onlyBoundsOptions.inPreferredConfig = Bitmap.Config.ARGB_8888;//optional
        BitmapFactory.decodeStream(input, null, onlyBoundsOptions);
        input.close();
        int originalWidth = onlyBoundsOptions.outWidth;
        int originalHeight = onlyBoundsOptions.outHeight;
        if ((originalWidth == -1) || (originalHeight == -1))
            return null;
        //图片分辨率以480x800为标准
        float hh = 800f;//这里设置高度为800f
        float ww = 480f;//这里设置宽度为480f
        //缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
        int be = 1;//be=1表示不缩放
        if (originalWidth > originalHeight && originalWidth > ww) {//如果宽度大的话根据宽度固定大小缩放
            be = (int) (originalWidth / ww);
        } else if (originalWidth < originalHeight && originalHeight > hh) {//如果高度高的话根据宽度固定大小缩放
            be = (int) (originalHeight / hh);
        }
        if (be <= 0)
            be = 1;
        //比例压缩
        BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
        bitmapOptions.inSampleSize = be;//设置缩放比例
        bitmapOptions.inDither = true;//optional
        bitmapOptions.inPreferredConfig = Bitmap.Config.ARGB_8888;//optional
        input = ac.getContentResolver().openInputStream(uri);
        Bitmap bitmap = BitmapFactory.decodeStream(input, null, bitmapOptions);
        input.close();

        return compressImage(bitmap);//再进行质量压缩
    }


    /**
     * 质量压缩方法
     *
     * @param image
     * @return
     */
    public static Bitmap compressImage(Bitmap image) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);//质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        int options = 100;
        while (baos.toByteArray().length / 1024 > 100) {  //循环判断如果压缩后图片是否大于100kb,大于继续压缩
            baos.reset();//重置baos即清空baos
            //第一个参数 ：图片格式 ，第二个参数： 图片质量，100为最高，0为最差  ，第三个参数：保存压缩后的数据的流
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);//这里压缩options%，把压缩后的数据存放到baos中
            options -= 10;//每次都减少10
        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());//把压缩后的数据baos存放到ByteArrayInputStream中
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);//把ByteArrayInputStream数据生成图片
        return bitmap;
    }


    /**
     * 通过Uri获取文件
     * @param ac
     * @param uri
     * @return
     */
    public static File getFileFromMediaUri(Context ac, Uri uri) {
        if(uri.getScheme().toString().compareTo("content") == 0){
            ContentResolver cr = ac.getContentResolver();
            Cursor cursor = cr.query(uri, null, null, null, null);// 根据Uri从数据库中找
            if (cursor != null) {
                cursor.moveToFirst();
                String filePath = cursor.getString(cursor.getColumnIndex("_data"));// 获取图片路径
                cursor.close();
                if (filePath != null) {
                    return new File(filePath);
                }
            }
        }else if(uri.getScheme().toString().compareTo("file") == 0){
            return new File(uri.toString().replace("file://",""));
        }
        return null;
    }


    /**
     * 读取图片的旋转的角度
     *
     * @param path 图片绝对路径
     * @return 图片的旋转角度
     */
    public static int getBitmapDegree(String path) {
        int degree = 0;
        try {
            // 从指定路径下读取图片，并获取其EXIF信息
            ExifInterface exifInterface = new ExifInterface(path);
            // 获取图片的旋转信息
            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return degree;
    }


    /**
     * 将图片按照某个角度进行旋转
     *
     * @param bm     需要旋转的图片
     * @param degree 旋转角度
     * @return 旋转后的图片
     */
    public static Bitmap rotateBitmapByDegree(Bitmap bm, int degree) {
        Bitmap returnBm = null;

        // 根据旋转角度，生成旋转矩阵
        Matrix matrix = new Matrix();
        matrix.postRotate(degree);
        try {
            // 将原始图片按照旋转矩阵进行旋转，并得到新的图片
            returnBm = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(), bm.getHeight(), matrix, true);
        } catch (OutOfMemoryError e) {
        }
        if (returnBm == null) {
            returnBm = bm;
        }
        if (bm != returnBm) {
            bm.recycle();
        }
        return returnBm;
    }


    private void uploadPicture(Bitmap bitmap){
        Logger.getInstance(getApplicationContext()).info("企业认证上传图片: "  + bitmap);

        Logger.getInstance(getApplicationContext()).info("企业认证上传图片url: "  + URLConst.URL_UPLOAD_PICTURE+ TokenUtil.token);


        UploadApi.uploadImg(bitmap,new ResponseListener<String>() {
            @Override
            public void onErrorResponse(VolleyError error) {
                ToastUtil.showToast(getApplicationContext(),error.toString());
            }

            @Override
            public void onResponse(String response) {
                Logger.getInstance(getApplicationContext()).info("企业认证上传图片成功,response is :" + response);
                JSONObject jsonObject = JSON.parseObject(response,JSONObject.class);

                ToastUtil.showToast(getApplicationContext(),(String) jsonObject.get("info"));


                switch (pictureStatus){
                    case 1:
                        viceLicernseImagePath = (String) jsonObject.get("data");
                        break;
                    case 2:
                        enterpriseCodeImagePath = (String) jsonObject.get("data");
                        break;
                    case 3:
                        taxiRegisterImagePath = (String) jsonObject.get("data");
                        break;
                    case 4:
                        idNumFrontImagePath = (String) jsonObject.get("data");
                        break;
                    case 5:
                        idNumBackImagePath = (String) jsonObject.get("data");
                        break;

                    default:
                        break;
                }
            }
        },getApplicationContext(), URLConst.URL_UPLOAD_PICTURE+ TokenUtil.token) ;
    }


    @Override
    protected void netRequest() {
        super.netRequest();

        request.url = URLConst.URL_ENTERPRISE_VERIFY + TokenUtil.token;
        final Map<String,String> map = new HashMap<>();
        map.put("name",enterpriseNameInput.getText().toString());

        map.put("bankid",bankNumInput.getText().toString());

        map.put("siden",signType+"");

        map.put("businessid",licenseNumInput.getText().toString());

        map.put("businesspath",viceLicernseImagePath);

        map.put("ocode",enterpriseCodeInput.getText().toString());

        map.put("oripath",enterpriseCodeImagePath);

        map.put("taxpath",taxiRegisterImagePath);

        map.put("sname",lawNameInput.getText().toString());

        map.put("sfz",lawIdNumInput.getText().toString());

        map.put("mobile",lawPhoneInput.getText().toString());

        map.put("mobile_code",smsCoeView.getSmsCode());


        Logger.getInstance(getApplicationContext()).info("企业认证参数是 :" + map.toString());

        request.setCallback(new RequestCallback() {
            @Override
            public void sucess(String response) {
                Logger.getInstance(getApplicationContext()).info("企业认证成功");

                JSONObject map = JSON.parseObject(response,JSONObject.class);

                ToastUtil.showToast(getApplicationContext(),(String) map.get("info"));

                Intent intent = new Intent();
                setResult(Constant.GO_HOME_ACTIVITY_FROM_VERIFY_ACTIVITY,intent);
                finish();

            }

            @Override
            public void fail(String response) {
                gotoLoginActivity();
            }
        });
        Logger.getInstance(getApplicationContext()).info("企业认证：" + request.url);
        request.postRequest(getApplicationContext(),map);

    }


    @Override
    public void getSmsCode() {
       smsCoeView.phone = lawPhoneInput.getText().toString();
    }
}