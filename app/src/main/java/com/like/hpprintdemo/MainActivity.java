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
    }

    /**
     * print image
     */
    public void printImage() {

        //1.To load an ImageAsset from resources:
        //        ImageAsset imageAsset4x6 = new ImageAsset(this, R.drawable.template4x6, ImageAsset.MeasurementUnits.INCHES, 4, 6);
        //2.To load an image from storage:
        //        ImageAsset assetdirectory = new ImageAsset(this, "oceanwave.jpeg", ImageAsset.MeasurementUnits.INCHES, 4, 6);
        //3.To load an ImageAsset from an existing Bitmap object:
        //        ImageAsset bitmapAsset = new ImageAsset(this, bitmap, ImageAsset.MeasurementUnits.INCHES, 4,5);
        //4.Or, if you already saved the bitmap into the internal storage location for your app:
        ImageAsset imageAsset4x6 = new ImageAsset("/storage/emulated/0/1/aphoto.jpg", ImageAsset.MeasurementUnits.INCHES, 4, 6);

        PrintItem printItemDefault = new ImagePrintItem(PrintItem.ScaleType.CENTER, imageAsset4x6);

        PrintJobData printJobData = new PrintJobData(MainActivity.this, printItemDefault);
        printJobData.setJobName("Example");
        PrintUtil.setPrintJobData(printJobData);
        PrintUtil.print(MainActivity.this);
    }

    /**
     * print pdf
     */
    public void printPdf() {

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


        File file = new File("/storage/emulated/0/1/aletter.pdf");
        if (file.exists()) {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.addCategory("android.intent.category.DEFAULT");
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            Uri uri = Uri.fromFile(file);
            intent.setDataAndType(uri, "application/pdf");

            /**
             * 用指定应用打开pdf文件
             * "包名"，"活动名"
             */
            intent.setClassName("com.microsoft.office.word", "com.microsoft.office.apphost.LaunchActivity");

            try {
                this.startActivity(intent);
                //                this.startActivity(Intent.createChooser(intent, "Choose Email Client"));
            } catch (Exception e) {
                Toast.makeText(this, "设备上没有可浏览pdf文件的应用Microsoft Word！", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "pdf文件不存在！", Toast.LENGTH_SHORT).show();
        }

        // 获取包管理器  
        //                File file =  new File("/storage/emulated/0/1/aletter.doc");
        //                PackageManager pm = getPackageManager();
        //                Intent intent = pm.getLaunchIntentForPackage("com.microsoft.office.word");
        //                Uri uri = Uri.fromFile(file);
        //                intent.setDataAndType(uri, "application/vnd.openxmlformats-officedocument.wordprocessingml.document");
        //                intent.setClassName(packageName, className);
        //        //
        //                startActivity(intent);

    }

    public void printPdfByEmail() {

        File file = new File("/storage/emulated/0/1/aletter.pdf");
        if (file.exists()) {
            Intent intent = new Intent(Intent.ACTION_SEND);
            // i.setType("text/plain"); //模拟器请使用这行
            intent.setType("message/rfc822"); // 真机上使用这行
            //        i.putExtra(Intent.EXTRA_EMAIL,
            //                new String[] { "wuyunpeng@aegis-data.cn" });
            intent.putExtra(Intent.EXTRA_EMAIL,
                    new String[]{"fxr23mf47@hpeprint.com"});
//                    intent.putExtra(Intent.EXTRA_SUBJECT, "主题");//主题
            //        i.putExtra(Intent.EXTRA_TEXT, "这是一封打印机测试邮件的正文！");//正文
            intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));//附件
//            intent.setClassName("com.tencent.androidqqmail","com.tencent.qqmail.fragment.base.MailFragmentActivity");
            try{
//                startActivity(Intent.createChooser(intent, "Select email application."));
                startActivity(intent);
            }catch (Exception e){
                Toast.makeText(this, "设备上没有发送邮件的应用QQ邮箱！", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "pdf附件不存在！", Toast.LENGTH_SHORT).show();
        }
    }
}
