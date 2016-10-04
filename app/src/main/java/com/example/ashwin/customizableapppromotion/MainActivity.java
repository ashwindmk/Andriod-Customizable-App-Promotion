package com.example.ashwin.customizableapppromotion;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private TextView mFooter;
    private String mFooterUrl = "", mFooterName = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //recycler view
        DatabaseReference ref = FirebaseDatabase.getInstance().getReferenceFromUrl("https://android-firebase-test-a2516.firebaseio.com/makeup-app-list/apps/");

        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new FirebaseRecyclerAdapter<App, AppViewHolder>(App.class, R.layout.app_list_row, AppViewHolder.class, ref) {
            @Override
            public void populateViewHolder(AppViewHolder avh, App app, final int position) {
                avh.setTitle(app.getTitle());
                avh.setSubTitle(app.getSubtitle());
                avh.setAppLogo(getApplicationContext(), app.getApplogo());
                avh.setAppImage(getApplicationContext(), app.getAppimage());
                avh.setCallToActionText(app.getCalltoactiontext());

                avh.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        App app = getItem(position);

                        //takes you to app's link page provided in firebase database
                        try {
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(app.getUrl())));
                        } catch(Exception e) {
                            if( app.getUrl().startsWith("market") && !isPlayStoreInstalled() ) {
                                Toast.makeText(MainActivity.this, "Play store is not installed", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(MainActivity.this, "Network Error, please try later", Toast.LENGTH_SHORT).show();
                            }
                        }

                    }
                });
            }
        };

        mRecyclerView.setAdapter(mAdapter);

        //footer
        DatabaseReference footer = FirebaseDatabase.getInstance().getReferenceFromUrl("https://android-firebase-test-a2516.firebaseio.com/makeup-app-list/footer/");

        mFooter = (TextView) findViewById(R.id.footer);
        mFooter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse( mFooterUrl )));
            }
        });

        footer.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot)
            {
                for(DataSnapshot urlSnapshot : snapshot.getChildren())
                {
                    Footer footerClass = urlSnapshot.getValue(Footer.class);
                    mFooterUrl = footerClass.getFooterurl();
                    mFooterName = footerClass.getFootername();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getMessage());
            }
        });
    }

    //checks if play store is installed
    private boolean isPlayStoreInstalled() {
        boolean playStoreInstalled = false;
        PackageManager packageManager = getApplication().getPackageManager();
        List<PackageInfo> packages = packageManager.getInstalledPackages(PackageManager.GET_UNINSTALLED_PACKAGES);
        for (PackageInfo packageInfo : packages) {
            if (packageInfo.packageName.equals("com.google.market") || packageInfo.packageName.equals("com.android.vending")) {
                playStoreInstalled = true;
                break;
            }
        }
        return playStoreInstalled;
    }
}
