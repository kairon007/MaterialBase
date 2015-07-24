package de.trbnb.materialbase.app;

import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static final String TOOLBAR_SHOWN = "toolbarShown";
    private static final String TOOLBAR_OFFSET = "mToolbarOffset";

    private Toolbar toolbar;
    private RecyclerView listView;
    private HidingScrollListener hidingScrollListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(hasApi(19)){
            getWindow().setFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
            );
        }

        if(hasApi(19) && isNavBarOnBottom()){
            getWindow().setFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION
            );
        }

        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(de.trbnb.materialbase.R.id.toolbar);
        listView = (RecyclerView) findViewById(android.R.id.list);
        listView.setLayoutManager(new LinearLayoutManager(this));
        listView.setAdapter(new Adapter());
        setupToolbar();
        listView.setPadding(
                0,
                0,//toolbar.getLayoutParams().height,
                0,
                hasApi(19) && isNavBarOnBottom()? getNavBarHeight() : 0
        );

        if(savedInstanceState != null){
            if(!savedInstanceState.getBoolean(TOOLBAR_SHOWN, true)){
                toolbar.post(new Runnable() {
                    @Override
                    public void run() {
                        toolbar.setTranslationY(-toolbar.getHeight());
                    }
                });
            }

            int offset = savedInstanceState.getInt(TOOLBAR_OFFSET, 0);
            hidingScrollListener = new HidingScrollListener(offset);
        } else {
            hidingScrollListener = new HidingScrollListener();
        }

        listView.addOnScrollListener(hidingScrollListener);

    }

    public boolean hasApi(int apiLevel){
        return Build.VERSION.SDK_INT >= apiLevel;
    }

    @TargetApi(19)
    public boolean isNavBarOnBottom(){
        Point size = new Point();
        getWindowManager().getDefaultDisplay().getRealSize(size);
        DisplayMetrics metrics = getResources().getDisplayMetrics();

        return size.x == metrics.widthPixels;
    }

    private void setupToolbar(){
        int height =
                getResources().getDimensionPixelSize(de.trbnb.materialbase.R.dimen.abc_action_bar_default_height_material)
                    + (hasApi(19) ? getStatusBarHeight() : 0);
        toolbar.getLayoutParams().height = height;
        toolbar.setPadding(0, hasApi(19) ? getStatusBarHeight() : 0, 0, 0);
        setSupportActionBar(toolbar);
    }

    public int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    public int getNavBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("navigation_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    public void hideToolbar(){
        final ValueAnimator animator = ValueAnimator.ofFloat(toolbar.getTranslationY(), -toolbar.getHeight());
        animator.setDuration(200);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                toolbar.setTranslationY((Float) animator.getAnimatedValue());
            }
        });
        animator.start();
    }

    public void showToolbar(){
        final ValueAnimator animator = ValueAnimator.ofFloat(toolbar.getTranslationY(), 0);
        animator.setDuration(200);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                toolbar.setTranslationY((Float) animator.getAnimatedValue());
            }
        });
        animator.start();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(de.trbnb.materialbase.R.menu.menu_main, menu);
        return true;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putBoolean(TOOLBAR_SHOWN, toolbar.getTranslationY() == 0);
        outState.putInt(TOOLBAR_OFFSET, hidingScrollListener.mToolbarOffset);
    }

    public class HidingScrollListener extends RecyclerView.OnScrollListener{

        private int mToolbarOffset = 0;

        public HidingScrollListener() {
        }

        public HidingScrollListener(int mToolbarOffset) {
            this.mToolbarOffset = mToolbarOffset;
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);

            //clipToolbarOffset();
            onMoved(dy);

            /*if((mToolbarOffset <toolbar.getHeight() && dy>0) || (mToolbarOffset >0 && dy<0)) {
                mToolbarOffset += dy;
            }*/
        }

        private void clipToolbarOffset() {
            if(mToolbarOffset > toolbar.getHeight()) {
                mToolbarOffset = toolbar.getHeight();
            } else if(mToolbarOffset < 0) {
                mToolbarOffset = 0;
            }
        }

        public void onMoved(int distance){
            //toolbar.setTranslationY(-distance);
            getSupportActionBar().setTitle(distance + "");
        }

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);

            if(newState == RecyclerView.SCROLL_STATE_IDLE){
                int middle = -toolbar.getHeight() / 2;
                if(toolbar.getTranslationY() > middle){
                    showToolbar();
                } else {
                    hideToolbar();
                }
            }
        }
    }

    public class Adapter extends RecyclerView.Adapter<ViewHolder>{

        String[] text = new String[50];

        public Adapter() {
            for(int i = 0; i < text.length; i++){
                text[i] = (i + 1) + "";
            }
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            return new ViewHolder(getLayoutInflater().inflate(android.R.layout.simple_list_item_1, viewGroup, false));
        }

        @Override
        public void onBindViewHolder(ViewHolder viewHolder, int i) {
            viewHolder.setup(text[i]);
        }

        @Override
        public int getItemCount() {
            return text.length;
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        private TextView textView;

        public ViewHolder(View itemView) {
            super(itemView);

            textView = (TextView) itemView.findViewById(android.R.id.text1);
        }

        public void setup(String text){
            textView.setText(text);
        }
    }
}
