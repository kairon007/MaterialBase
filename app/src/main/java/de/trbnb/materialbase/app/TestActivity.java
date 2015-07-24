package de.trbnb.materialbase.app;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.ksoichiro.android.observablescrollview.ObservableRecyclerView;

import java.util.Random;

import de.trbnb.materialbase.MaterialScrollActivity;


public class TestActivity extends MaterialScrollActivity<ObservableRecyclerView> {

    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ObservableRecyclerView recyclerView = getScrollable();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new Adapter());

        fab = findView(de.trbnb.materialbase.R.id.fab);
    }

    @Override
    protected int contentId() {
        return R.layout.activity_test;
    }

    @Override
    protected int scrollableId() {
        return R.id.recycler;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(de.trbnb.materialbase.R.menu.menu_test, menu);
        return true;
    }

    Random random = new Random();

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //startActivity(new Intent(this, MainActivity.class));

        int newColor = random.nextInt(2);
        setPrimaryColorRes(newColor == 0 ? android.R.color.holo_blue_dark : android.R.color.holo_orange_dark, true);

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onMoved(int scrollY) {
        fab.setTranslationY(-scrollY);
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
