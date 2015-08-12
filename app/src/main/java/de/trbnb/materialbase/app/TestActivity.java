package de.trbnb.materialbase.app;

import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import de.trbnb.materialbase.activities.MaterialRecyclerActivity;

public class TestActivity extends MaterialRecyclerActivity {

    @Override
    protected void onCreated() {
        super.onCreated();

        //TextView textView = new TextView(this);
        //setContentView(textView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        getRecyclerView().setLayoutManager(new LinearLayoutManager(this));
        getRecyclerView().setAdapter(new Adapter());

        getFloatingActionButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(getCoordinatorLayout(), "Hello Text", Snackbar.LENGTH_SHORT).show();
            }
        });

        View view = new View(this);
        view.setBackgroundColor(Color.BLUE);
        setDrawerView(view);
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
