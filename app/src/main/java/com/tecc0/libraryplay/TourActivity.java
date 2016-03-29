package com.tecc0.libraryplay;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;

import com.vlonjatg.android.apptourlibrary.AppTour;
import com.vlonjatg.android.apptourlibrary.MaterialSlide;

public class TourActivity extends AppTour {

    @Override
    public void init(Bundle savedInstanceState) {

        Context context = getApplicationContext();
        int firstColor = ContextCompat.getColor(context, R.color.flat_turquoise);
        int secondColor = ContextCompat.getColor(context, R.color.flat_emerald);
        int thirdColor = ContextCompat.getColor(context, R.color.flat_peter_river);
        int fourthColor = ContextCompat.getColor(context, R.color.flat_amethyst);

        Fragment firstSlide = MaterialSlide.newInstance(R.drawable.ic_menu_camera, "絶え間なく注ぐ愛の名を",
                "永遠と呼ぶことができたなら", Color.WHITE, Color.WHITE);

        Fragment secondSlide = MaterialSlide.newInstance(R.drawable.ic_menu_gallery, "言葉では伝えることがどうしてもできなかった",
                "愛しさの意味を知る", Color.BLACK, Color.WHITE);

        Fragment thirdSlide = MaterialSlide.newInstance(R.drawable.ic_menu_manage, "貴方を幸せにしたい",
                "胸に宿る未来図を", Color.WHITE, Color.BLACK);

        Fragment fourthSlide = MaterialSlide.newInstance(R.drawable.ic_menu_send, "悲しみの涙に濡らさぬよう",
                "つむぎあい生きてる", Color.WHITE, Color.WHITE);

        //Add slides
        addSlide(firstSlide, firstColor);
        addSlide(secondSlide, secondColor);
        addSlide(thirdSlide, thirdColor);
        addSlide(fourthSlide, fourthColor);

        //Custom slide
        //addSlide(new CustomSlide(), customSlideColor);

        setSkipButtonTextColor(Color.WHITE);
        setNextButtonColorToWhite();
        setDoneButtonTextColor(Color.WHITE);
    }

    @Override
    public void onSkipPressed() {
        finish();
    }

    @Override
    public void onDonePressed() {
        finish();
    }
}