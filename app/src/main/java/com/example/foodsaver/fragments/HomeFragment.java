package com.example.foodsaver.fragments;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.foodsaver.R;

import java.util.ArrayList;
import java.util.Random;

public class HomeFragment extends Fragment {

ArrayList<String> facts = new ArrayList<String>();
TextView textView;


    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        textView = view.findViewById(R.id.text_view_fact);
        textView.setText(getRandFact());



        return view;


}



    public static int getRandomNumberInts(int min, int max) {
        Random random = new Random();
        return random.ints(min, (max + 1)).findFirst().getAsInt();
    }

    public String getRandFact(){
        facts.add("Over 1/3 of all food produced globally goes to waste.");
        facts.add("The annual value of food wasted globally is $1 trillion, and it weighs 1.3 billion tonnes.");
        facts.add("All the world’s nearly one billion hungry people could be fed on less than a quarter of the food that is wasted in the US, UK and Europe.");
        facts.add("An area larger than China is used to grow food that is never eaten.");
        facts.add("25% of the world’s fresh water supply is used to grow food that is never eaten.");
        facts.add("If food waste were a country it would be the 3rd largest emitter of greenhouse gases (after China & the USA).");
        facts.add("In most developed countries, over half of all food waste takes place in the home.");
        facts.add("Food waste costs more than £800 per year to the average UK family ($2,275 in the USA).");

        return facts.get(getRandomNumberInts(0, facts.size() - 1 ));

    }
}
