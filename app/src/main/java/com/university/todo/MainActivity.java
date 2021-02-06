package com.university.todo;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;
import androidx.annotation.NonNull;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private FloatingActionButton floatingActionButton;
    private   Intent menint;
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener fireAuthListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView=findViewById(R.id.recycl_view);
        floatingActionButton = findViewById(R.id.floatingActionButton);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, InputActivity.class);
                startActivity(intent);
            }
        });

//    Authentication

        firebaseAuth = FirebaseAuth.getInstance();

        //get current user
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        fireAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user1 = firebaseAuth.getCurrentUser();

                if (user1 == null) {
                    //user not login
                    MainActivity.this.startActivity(new Intent(MainActivity.this, LoginActivity.class));
                    MainActivity.this.finish();
                }
            }
        };
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.main_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.about:
                menint = new Intent(this,About.class);
                startActivity(menint);
                return true;
            case R.id.accsettings:
                menint = new Intent(this,AccountSettings.class);
                startActivity(menint);
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
