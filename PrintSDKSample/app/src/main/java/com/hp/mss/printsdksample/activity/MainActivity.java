/*
 * Hewlett-Packard Company
 * All rights reserved.
 *
 * This file, its contents, concepts, methods, behavior, and operation
 * (collectively the "Software") are protected by trade secret, patent,
 * and copyright laws. The use of the Software is governed by a license
 * agreement. Disclosure of the Software to third parties, in any form,
 * in whole or in part, is expressly prohibited except as authorized by
 * the license agreement.
 */

package com.hp.mss.printsdksample.activity;

import android.os.Bundle;
import android.print.PrintAttributes;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.hp.mss.hpprint.model.PDFPrintItem;
import com.hp.mss.hpprint.model.PrintItem;
import com.hp.mss.hpprint.model.PrintJobData;
import com.hp.mss.hpprint.model.asset.PDFAsset;
import com.hp.mss.hpprint.util.PrintUtil;
import com.hp.mss.printsdksample.R;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.FileCallback;
import com.lzy.okgo.request.BaseRequest;

import java.io.File;

import okhttp3.Call;
import okhttp3.Response;


public class MainActivity extends AppCompatActivity {

    private String fileName = "/storage/emulated/0/download/行政上诉状.pdf";
    private File file = new File(fileName);
    public static Boolean isPrint = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.print_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("like", "start to print !");
                if(file.exists()){
                    print(file);
                }else {
                    isPrint = true;
                    downLoad(fileName);
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    /**
     * 下载文件
     */
    public void downLoad(String string){
        OkGo.get("https://question.aegis-info.com/static/doc_template/" + "行政上诉状.pdf")
                .execute(new FileCallback(string) {


                    @Override
                    public void onBefore(BaseRequest request) {
                        super.onBefore(request);
                        Log.d("like", "onBefore: ");
                    }

                    @Override
                    public void onSuccess(File file, Call call, Response response) {
                        Log.d("like", "onSuccess: file.getAbsolutePath() is "+file.getAbsolutePath());
                        if(isPrint){
                            print(file);
                        }
                    }

                    @Override
                    public void onAfter(File file, Exception e) {
                        super.onAfter(file, e);
                        Log.d("like", "onAfter: ");
                    }
                });
    }

    /**
     * 打印文书
     */

    public void print(File file){
        PDFAsset pdf = new PDFAsset(file.toString(), true);
        PrintItem printItemDefault = new PDFPrintItem(PrintAttributes.MediaSize.NA_INDEX_4X6,
                new PrintAttributes.Margins(0, 0, 0, 0), PrintItem.ScaleType.CENTER, pdf);
        PrintJobData printJobData = new PrintJobData(MainActivity.this, printItemDefault);
        printJobData.setJobName("like");
        PrintUtil.setPrintJobData(printJobData);
        PrintUtil.print(MainActivity.this);
    }
}
