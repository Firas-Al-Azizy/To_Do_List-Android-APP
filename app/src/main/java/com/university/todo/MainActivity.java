package com.university.todo;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.options_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.about:
                Toast.makeText(this,"Item 1 is selected",Toast.LENGTH_SHORT).show();
                return true;
            case R.id.accsettings:
                Toast.makeText(this,"Item 2 is selected",Toast.LENGTH_SHORT).show();
                return true;
            case R.id.theme:
                Toast.makeText(this,"Item 3 is selected",Toast.LENGTH_SHORT).show();
                return true;
            case R.id.dark_mode_switch:
                Toast.makeText(this,"Sub Item 1 is selected",Toast.LENGTH_SHORT).show();
                return true;
            default:return super.onOptionsItemSelected(menuItem);
        }

    }
}
