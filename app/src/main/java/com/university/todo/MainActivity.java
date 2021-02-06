package com.university.todo;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.university.todo.Models.ToDo;
import com.university.todo.ViewHolder.ToDoViewHolder;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private FloatingActionButton floatingActionButton;
    private   Intent menint;
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener fireAuthListener;
    //Firebase
    FirebaseDatabase database;
    DatabaseReference todoDB;
    FirebaseRecyclerOptions<ToDo> options ;
    FirebaseRecyclerAdapter<ToDo, ToDoViewHolder> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Firebase
        database=FirebaseDatabase.getInstance() ;
        todoDB=database.getReference("ToDo");
        //recyclerView
        recyclerView=findViewById(R.id.recycl_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        showTask();
        //floatingActionButton
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

    private void showTask() {
        options= new FirebaseRecyclerOptions.Builder<ToDo>()
                .setQuery(todoDB,ToDo.class)
                .build();
        adapter=new FirebaseRecyclerAdapter<ToDo, ToDoViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull ToDoViewHolder holder, int position, @NonNull ToDo model) {
                holder.text_task.setText(model.getTask());
                holder.text_priority.setText(model.getPriority());
            }

            @NonNull
            @Override
            public ToDoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View itemView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.todo_row,parent,false);
                return new ToDoViewHolder(itemView);
            }
        };
recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        
        if (item.getTitle().equals("Update")){
            showUpdateDialog(adapter.getRef(item.getOrder()).getKey(),adapter.getItem(item.getOrder()));
        }else if (item.getTitle().equals("Delete")){
            deleteTask(adapter.getRef(item.getOrder()).getKey());
        }
        return super.onContextItemSelected(item);
    }
// Delete Task
    private void deleteTask(String key) {
        todoDB.child(key).removeValue();
    }
// update Dialog
    private void showUpdateDialog(String key, ToDo item) {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("Update");
        builder.setMessage("Please Update this Field");

        View update_layout = LayoutInflater.from(this).inflate(R.layout.custom_layout,null);

        final EditText edit_update_task=update_layout.findViewById(R.id.edit_update_task);
        final EditText edit_update_priority=update_layout.findViewById(R.id.edit_update_priority);

        edit_update_task.setText(item.getTask());
        edit_update_priority.setText(item.getPriority());

        builder.setView(update_layout);
        builder.setPositiveButton("Update", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String task=edit_update_task.getText().toString();
                String priority=edit_update_priority.getText().toString();

                ToDo toDo =new ToDo(task,priority);
                todoDB.child(key).setValue(toDo);

                Toast.makeText(MainActivity.this, "Task updated successfully", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.show();

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
            case R.id.delete_all:
                todoDB.removeValue();
                Toast.makeText(this,"All Tasks are Deleted",Toast.LENGTH_SHORT).show();

            default:return super.onOptionsItemSelected(menuItem);
        }

    }
}
