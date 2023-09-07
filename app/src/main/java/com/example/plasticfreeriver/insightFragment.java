package com.example.plasticfreeriver;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.plasticfreeriver.databinding.FragmentInsightBinding;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.interfaces.datasets.IPieDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link insightFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class insightFragment extends Fragment {
    BarChart barChart;

    // variable for our bar data.
    BarData barData;
    public int arRegTotal,arCleanedTotal;
    ArrayList<Integer> arRegistered,arCleaned;


    // variable for our bar data set.
    BarDataSet barDataSet;
    FirebaseDatabase database = FirebaseDatabase.getInstance();

    // array list for storing entries.
    ArrayList barEntriesArrayList,pieList,pieList2;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    FragmentInsightBinding binding;

    @SuppressWarnings("FieldCanBeLocal")
    private PieChart chart;

    public insightFragment() {
        // Required empty public constructor
    }

    public static insightFragment newInstance(String param1, String param2) {
        insightFragment fragment = new insightFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
arRegistered=new ArrayList<>();
        arCleaned=new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
             Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.fragment_insight, container, false);
        // Inflate the layout for this fragment
        chart = v.findViewById(R.id.pieChart1);
        chart.getDescription().setEnabled(false);

//        Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "OpenSans-Light.ttf");
//
//        chart.setCenterTextTypeface(tf);
//        chart.setCenterText(generateCenterText());
//        chart.setCenterTextSize(10f);
//        chart.setCenterTextTypeface(tf);

        // radius of the center hole in percent of maximum radius
        chart.setHoleRadius(40f);
        chart.setTransparentCircleRadius(50f);

        Legend l = chart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(false);
       ArrayList<PieEntry> pieList=new ArrayList<>();
       ArrayList<PieEntry> pieList2=new ArrayList<>();


        pieList2.add(0,new PieEntry(25));
        pieList2.add(1,new PieEntry(6));
        pieList.add(new PieEntry(1,5));
        pieList.add(new PieEntry(0,85));
        IPieDataSet iPieDataSet=new PieDataSet(pieList,"cleaned");
        IPieDataSet iPieDataSet2=new PieDataSet(pieList2,"remaining plastic");

        PieData pieData=new PieData(iPieDataSet);
        chart.setData(pieData);
        chart.setData(new PieData(iPieDataSet2));
        barChart = v.findViewById(R.id.idBarChart);

        // calling method to get bar entries.
        getBarEntries();

        // creating a new bar data set.
        barDataSet = new BarDataSet(barEntriesArrayList, "Clean");

        // creating a new bar data and
        // passing our bar data set.
        barData = new BarData(barDataSet);

        // below line is to set data
        // to our bar chart.
        barChart.setData(barData);

        // adding color to our bar data set.
        barDataSet.setColors(ColorTemplate.MATERIAL_COLORS);

        // setting text color.
        barDataSet.setValueTextColor(Color.BLACK);

        // setting text size
        barDataSet.setValueTextSize(16f);
        barChart.getDescription().setEnabled(false);
        return v;
    }

    private void getBarEntries() {

        // creating a new array list
        barEntriesArrayList = new ArrayList<>();

        // adding new entry to our array list with bar
        // entry and passing x and y axis value to it.
        for(int i=1;i<20;i++)
        barEntriesArrayList.add(new BarEntry(i, i));
//        barEntriesArrayList.add(new BarEntry(2f, 6));
//        barEntriesArrayList.add(new BarEntry(3f, 8));
//        barEntriesArrayList.add(new BarEntry(4f, 2));
//        barEntriesArrayList.add(new BarEntry(5f, 4));
//        barEntriesArrayList.add(new BarEntry(6f, 1));

    }
    private SpannableString generateCenterText() {
        SpannableString s = new SpannableString("Revenues\nQuarters 2015");
        s.setSpan(new RelativeSizeSpan(2f), 0, 8, 0);
        s.setSpan(new ForegroundColorSpan(Color.GRAY), 8, s.length(), 0);
        return s;
    }
}