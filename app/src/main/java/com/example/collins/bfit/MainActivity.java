//package
package com.example.collins.bfit;

//imports
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

//class MainActivity
public class MainActivity extends AppCompatActivity {
    private Button mFindMealsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFindMealsButton = (Button) findViewById(R.id.findMealsButton);
        //added a listener
        mFindMealsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MealsActivity.class);
            }
        });
    }
}
