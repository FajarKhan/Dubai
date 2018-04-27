package com.example.root.dubai;

import android.app.Activity;
import android.content.Context;
import android.graphics.SurfaceTexture;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Registry;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.module.AppGlideModule;
import com.example.root.dubai.Adapter.ExploreDubaiAdapter;
import com.example.root.dubai.Model.ExploreModel;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends Activity implements TextureView.SurfaceTextureListener, MediaPlayer.OnBufferingUpdateListener, MediaPlayer.OnCompletionListener, MediaPlayer.OnPreparedListener, MediaPlayer.OnVideoSizeChangedListener {

    private RecyclerView rvExplore, rvExperience, rvBestApps, rvShopping;
    private TextureView vvDubai;
    private MediaPlayer mMediaPlayer;
    private FirestoreRecyclerAdapter<ExploreModel, ExploreViewHolder> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mMediaPlayer!=null) {
            mMediaPlayer.stop();
        }
    }

    private class ExploreViewHolder extends RecyclerView.ViewHolder {

        CardView cvExplore;
        TextView tvTitleText, tvSubText;
        ImageView ivExplore;

        ExploreViewHolder(View itemView) {
            super(itemView);

            cvExplore = (CardView) itemView.findViewById(R.id.cv_explore);
            tvTitleText = (TextView) itemView.findViewById(R.id.tv_explore_text);
            tvSubText = (TextView) itemView.findViewById(R.id.tv_explore_sub_text);
            ivExplore = (ImageView) itemView.findViewById(R.id.iv_explore);
        }

        void setTitleName(String TitleText) {
            tvTitleText.setText(TitleText);
        }
        void setSubTitleName(String SubText) {
            tvSubText.setText(SubText);
        }
    }

    private void initView() {

        rvExplore = (RecyclerView) findViewById(R.id.rv_explore);
        rvExperience = (RecyclerView) findViewById(R.id.rv_experience);
        rvBestApps = (RecyclerView) findViewById(R.id.rv_best_apps);
        rvShopping = (RecyclerView) findViewById(R.id.rv_shopping);
        vvDubai = (TextureView) findViewById(R.id.vv_dubai);

        FirebaseFirestore rootRef = FirebaseFirestore.getInstance();
        Query query = rootRef.collection("Explore");

        FirestoreRecyclerOptions<ExploreModel> options = new FirestoreRecyclerOptions.Builder<ExploreModel>()
                .setQuery(query, ExploreModel.class)
                .build();

        rvExplore.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        adapter = new FirestoreRecyclerAdapter<ExploreModel, ExploreViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull ExploreViewHolder holder, int position, @NonNull ExploreModel model) {
                holder.setTitleName(model.getTxtTitle());
                holder.setSubTitleName(model.getTxtSubTitle());

//                StorageReference  mStorageRef = FirebaseStorage.getInstance().getReference();
//                StorageReference pathReference = mStorageRef.child("Explore/angel_logo.png ");
//
//           com.example.root.dubai.MainActivity.GlideApp.with(getApplicationContext())
//                        .load(pathReference)
//                        .into(holder.ivExplore);

                if (position == 0) {
                    ViewGroup.MarginLayoutParams layoutParams =
                            (ViewGroup.MarginLayoutParams) holder.cvExplore.getLayoutParams();
                    layoutParams.setMargins(30, 6, 0, 6);
                    holder.cvExplore.requestLayout();
                } else if (position == getItemCount() - 1) {
                    ViewGroup.MarginLayoutParams layoutParams =
                            (ViewGroup.MarginLayoutParams) holder.cvExplore.getLayoutParams();
                    layoutParams.setMargins(16, 6, 30, 6);
                    holder.cvExplore.requestLayout();
                } else {
                    ViewGroup.MarginLayoutParams layoutParams =
                            (ViewGroup.MarginLayoutParams) holder.cvExplore.getLayoutParams();
                    layoutParams.setMargins(16, 6, 0, 6);
                    holder.cvExplore.requestLayout();
                }
            }
            @NonNull
            @Override
            public ExploreViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.explore_dubai_row, parent, false);
                return new ExploreViewHolder(view);
            }
        };
        rvExplore.setAdapter(adapter);


//        ExploreDubaiAdapter adapter = new ExploreDubaiAdapter(this);
//        rvExplore.setAdapter(adapter);


        rvExperience.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        ExploreDubaiAdapter adapter1 = new ExploreDubaiAdapter(this);
        rvExperience.setAdapter(adapter1);

        rvBestApps.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        ExploreDubaiAdapter adapter2 = new ExploreDubaiAdapter(this);
        rvBestApps.setAdapter(adapter2);

        rvShopping.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        ExploreDubaiAdapter adapter3 = new ExploreDubaiAdapter(this);
        rvShopping.setAdapter(adapter2);

        vvDubai.setOpaque(false);
        vvDubai.setSurfaceTextureListener(this);
    }

    @Override
    public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
        Surface s = new Surface(surface);

        try {
            Uri uri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.dubai_v);
            mMediaPlayer = new MediaPlayer();
            mMediaPlayer.setDataSource(this,uri);
            mMediaPlayer.setSurface(s);
            mMediaPlayer.setLooping(true);
            mMediaPlayer.prepare();
            mMediaPlayer.setOnBufferingUpdateListener(this);
            mMediaPlayer.setOnCompletionListener(this);
            mMediaPlayer.setOnPreparedListener(this);
            mMediaPlayer.setOnVideoSizeChangedListener(this);
            mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mMediaPlayer.start();
        } catch (IllegalArgumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SecurityException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalStateException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Override
    public void onSurfaceTextureSizeChanged(SurfaceTexture surfaceTexture, int i, int i1) {

    }

    @Override
    public boolean onSurfaceTextureDestroyed(SurfaceTexture surfaceTexture) {
        return false;
    }

    @Override
    public void onSurfaceTextureUpdated(SurfaceTexture surfaceTexture) {

    }

    @Override
    public void onBufferingUpdate(MediaPlayer mediaPlayer, int i) {

    }

    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {

    }

    @Override
    public void onPrepared(MediaPlayer mediaPlayer) {

    }

    @Override
    public void onVideoSizeChanged(MediaPlayer mediaPlayer, int i, int i1) {

    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();

        if (adapter != null) {
            adapter.stopListening();
        }
    }

    @GlideModule
    public class MyAppGlideModule extends AppGlideModule {

        @Override
        public void registerComponents(Context context, Glide glide, Registry registry) {
            // Register FirebaseImageLoader to handle StorageReference
            registry.append(StorageReference.class, InputStream.class,
                    new FirebaseImageLoader.Factory());
        }
    }
}
