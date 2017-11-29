package com.like.hpprintdemo;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.hp.mss.hpprint.model.ImagePrintItem;
import com.hp.mss.hpprint.model.PrintItem;
import com.hp.mss.hpprint.model.PrintJobData;
import com.hp.mss.hpprint.model.asset.ImageAsset;
import com.hp.mss.hpprint.util.PrintUtil;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.image).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                printImage();
            }
        });

        findViewById(R.id.pdf).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                printPdf();
            }
        });

        findViewById(R.id.email).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                printPdfByEmail();
            }
        });

//        PDFView view = (PDFView) findViewById(R.id.v_test);
//        PDFView
        //        PDFView pdfView= (PDFView) lookContent.findViewById(R.id.pdfview);
//        view.fromAsset("aletter.pdf")
//                    .pages(0)
//                    .defaultPage(0)
//                    .showMinimap(true)
//                    .enableSwipe(true)
        // .onDraw(onDrawListener)
        // .onLoad(onLoadCompleteListener)
        // .onPageChange(onPageChangeListener)
//                        .load();
        //
//        view.zoomTo(2f);
    }

    /**
     * print image
     */
    public void printImage(){

        //1.To load an ImageAsset from resources:
//        ImageAsset imageAsset4x6 = new ImageAsset(this, R.drawable.template4x6, ImageAsset.MeasurementUnits.INCHES, 4, 6);
        //2.To load an image from storage:
//        ImageAsset assetdirectory = new ImageAsset(this, "oceanwave.jpeg", ImageAsset.MeasurementUnits.INCHES, 4, 6);
        //3.To load an ImageAsset from an existing Bitmap object:
//        ImageAsset bitmapAsset = new ImageAsset(this, bitmap, ImageAsset.MeasurementUnits.INCHES, 4,5);
        //4.Or, if you already saved the bitmap into the internal storage location for your app:
        ImageAsset imageAsset4x6  = new ImageAsset("/storage/emulated/0/1/aphoto.jpg", ImageAsset.MeasurementUnits.INCHES, 4, 6);

        PrintItem printItemDefault = new ImagePrintItem(PrintItem.ScaleType.CENTER, imageAsset4x6 );

        PrintJobData printJobData = new PrintJobData(MainActivity.this, printItemDefault);
        printJobData.setJobName("Example");
        PrintUtil.setPrintJobData(printJobData);
        PrintUtil.print(MainActivity.this);
    }

    /**
     * print pdf
     */
    public void printPdf(){

        //1.To load a PDFAsset from the assets folder:
//        PDFAsset pdfAsset4x6 = new PDFAsset("4x6.pdf", true);
        //2.If you already saved the PDF into a folder on the device:
//        PDFAsset pdfAsset4x6 = new PDFAsset("/storage/emulated/0/1/aletter.pdf", false);
////
//        PrintItem printItemDefault = new PDFPrintItem(PrintAttributes.MediaSize.NA_INDEX_4X6,
//                new PrintAttributes.Margins(0, 0, 0, 0), PrintItem.ScaleType.CENTER, pdfAsset4x6);
//
//        PrintJobData printJobData = new PrintJobData(MainActivity.this, printItemDefault);
//        printJobData.setJobName("Example");
//        PrintUtil.setPrintJobData(printJobData);
//        PrintUtil.print(MainActivity.this);


//        Intent printIntent = new Intent("org.androidprinting.intent.action.SEND");
//        startActivity(printIntent);

//        Uri uri = Uri.fromFile(new File(Environment.getExternalStorageDirectory(), "1/aletter.pdf"));
//        Intent intent = new Intent ("org.androidprinting.intent.action.PRINT");
//        intent.setDataAndType( uri, "text/plain" );
//        startActivityForResult(intent, 0);

//        Intent intent = new Intent();
//        intent.setAction(Intent.ACTION_VIEW);
//        Uri uri = Uri.fromFile(new File(Environment.getExternalStorageDirectory(), "1/aletter.pdf"));
//        intent.setDataAndType(uri, "image/*");
//        startActivity(intent);

//        Intent intent = new Intent("com.hp.android.print.PRINT");
//        intent.setPackage("com.hp.android.print");
//        startActivityForResult(intent, 0);

        File file =  new File("/storage/emulated/0/1/aletter.pdf");
        if(file.exists()){
            Intent intent = new Intent("android.intent.action.VIEW");
            intent.addCategory("android.intent.category.DEFAULT");
            intent.addFlags (Intent.FLAG_ACTIVITY_NEW_TASK);
            Uri uri = Uri.fromFile(file);
            intent.setDataAndType (uri, "application/pdf");
            try{
                this.startActivity(intent);
            }catch (Exception e){
                Toast.makeText(this, "设备上没有可浏览pdf文件的应用！", Toast.LENGTH_SHORT).show();
            }
        }else {
            Toast.makeText(this, "pdf文件不存在！", Toast.LENGTH_SHORT).show();
        }

    }

    public void printPdfByEmail(){

        Intent i = new Intent(Intent.ACTION_SEND);
        // i.setType("text/plain"); //模拟器请使用这行
        i.setType("message/rfc822"); // 真机上使用这行
//        i.putExtra(Intent.EXTRA_EMAIL,
//                new String[] { "wuyunpeng@aegis-data.cn" });
        i.putExtra(Intent.EXTRA_EMAIL,
                new String[] { "fxr23mf47@hpeprint.com" });
//        i.putExtra(Intent.EXTRA_SUBJECT, "邮件测试");
//        i.putExtra(Intent.EXTRA_TEXT, "这是一封打印机测试邮件！");
        i.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(new File("/storage/emulated/0/1/aletter.pdf")));
        startActivity(Intent.createChooser(i, "Select email application."));
    }
}
