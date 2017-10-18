package com.kevin.demo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.kevin.baidu.location.Location;
import com.kevin.baidu.location.RxLocation;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView textView = (TextView) findViewById(R.id.text);

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RxLocation.get()
                        .location(MainActivity.this, 10000)
                        .subscribe(new Observer<Location>() {
                            @Override
                            public void onSubscribe(@NonNull Disposable disposable) {

                            }

                            @Override
                            public void onNext(@NonNull Location location) {

                            }

                            @Override
                            public void onError(@NonNull Throwable throwable) {

                            }

                            @Override
                            public void onComplete() {

                            }
                        });
            }
        });

    }
}
