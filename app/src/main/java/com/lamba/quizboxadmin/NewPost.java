package com.lamba.quizboxadmin;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

public class NewPost extends Fragment {

    View view;
    RecyclerView expanderRecyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.newpost,container,false);

        expanderRecyclerView = view.findViewById(R.id.recyclerView);

        initiateExpander();

        return view;
    }

    private void initiateExpander() {

        ArrayList<String> parentList = new ArrayList<>();
        ArrayList<ArrayList> childListHolder = new ArrayList<>();

        parentList.add("Fruits & Vegetables");
        parentList.add("Beverages & Health");
        parentList.add("Home & Kitchen");

        ArrayList<String> childNameList = new ArrayList<>();
        childNameList.add("Apple");
        childNameList.add("Mango");
        childNameList.add("Banana");

        childListHolder.add(childNameList);

        childNameList = new ArrayList<>();
        childNameList.add("Red bull");
        childNameList.add("Maa");
        childNameList.add("Horlicks");

        childListHolder.add(childNameList);

        childNameList = new ArrayList<>();
        childNameList.add("Knife");
        childNameList.add("Vessels");
        childNameList.add("Spoons");

        childListHolder.add(childNameList);

        ExpandableRecyclerViewAdapter expandableCategoryRecyclerViewAdapter =
                new ExpandableRecyclerViewAdapter(getActivity().getApplicationContext(), parentList,
                        childListHolder);

        expanderRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));

        expanderRecyclerView.setAdapter(expandableCategoryRecyclerViewAdapter);
    }


    public class ExpandableRecyclerViewAdapter extends RecyclerView.Adapter<ExpandableRecyclerViewAdapter.ViewHolder> {

        ArrayList<String> nameList = new ArrayList<String>();
        ArrayList<String> image = new ArrayList<String>();
        ArrayList<Integer> counter = new ArrayList<Integer>();
        ArrayList<ArrayList> itemNameList = new ArrayList<ArrayList>();
        Context context;

        public ExpandableRecyclerViewAdapter(Context context,
                                             ArrayList<String> nameList,
                                             ArrayList<ArrayList> itemNameList) {
            this.nameList = nameList;
            this.itemNameList = itemNameList;
            this.context = context;

            Log.d("namelist", nameList.toString());

            for (int i = 0; i < nameList.size(); i++) {
                counter.add(0);
            }

        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            TextView name;
            ImageButton dropBtn;
            RecyclerView cardRecyclerView;
            CardView cardView;

            public ViewHolder(View itemView) {
                super(itemView);
                name = itemView.findViewById(R.id.categoryTitle);
                dropBtn = itemView.findViewById(R.id.categoryExpandBtn);
                cardRecyclerView = itemView.findViewById(R.id.innerRecyclerView);
                cardView = itemView.findViewById(R.id.cardView);
            }
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.card_collapseview, parent, false);

            ExpandableRecyclerViewAdapter.ViewHolder vh = new ExpandableRecyclerViewAdapter.ViewHolder(v);

            return vh;

        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, final int position) {
            holder.name.setText(nameList.get(position));

            InnerRecyclerViewAdapter itemInnerRecyclerView = new InnerRecyclerViewAdapter(itemNameList.get(position));


            holder.cardRecyclerView.setLayoutManager(new GridLayoutManager(context, 2));


            holder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (counter.get(position) % 2 == 0) {
                        holder.cardRecyclerView.setVisibility(View.VISIBLE);
                    } else {
                        holder.cardRecyclerView.setVisibility(View.GONE);
                    }

                    counter.set(position, counter.get(position) + 1);

                }
            });
            holder.cardRecyclerView.setAdapter(itemInnerRecyclerView);

        }

        @Override
        public int getItemCount() {
            return nameList.size();
        }


    }

    public class InnerRecyclerViewAdapter extends RecyclerView.Adapter<InnerRecyclerViewAdapter.ViewHolder> {
        public ArrayList<String> nameList;

        public InnerRecyclerViewAdapter(ArrayList<String> nameList) {
            this.nameList = nameList;
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            TextView name;

            public ViewHolder(View itemView) {
                super(itemView);
                name = itemView.findViewById(R.id.itemTextView);
            }
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.card_expand_item_view, parent, false);


            InnerRecyclerViewAdapter.ViewHolder vh = new InnerRecyclerViewAdapter.ViewHolder(v);

            return vh;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            holder.name.setText(nameList.get(position));
        }

        @Override
        public int getItemCount() {
            return nameList.size();
        }


    }
}
