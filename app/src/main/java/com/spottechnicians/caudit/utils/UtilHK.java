package com.spottechnicians.caudit.utils;

import android.app.Activity;
import android.widget.TextView;

import com.spottechnicians.caudit.ModuleHK.HKQuestions;
import com.spottechnicians.caudit.R;

/**
 * Created by onestech on 27/06/16.
 */
public class UtilHK {
    public static int[] getResourceQuestion() {
        int array[] = {
                R.id.tvHKQuestion1, R.id.tvHKQuestion2, R.id.tvHKQuestion3, R.id.tvHKQuestion4, R.id.tvHKQuestion5,
                R.id.tvHKQuestion6, R.id.tvHKQuestion7, R.id.tvHKQuestion8, R.id.tvHKQuestion9,
                R.id.tvHKQuestion10, R.id.tvHKQuestion11, R.id.tvHKQuestion12, R.id.tvHKQuestion13, R.id.tvHKQuestion14,
                R.id.tvHKQuestion15, R.id.tvHKQuestion16, R.id.tvHKQuestion17
        };
        return array;
    }

    public static int[] getPhotoTextIds() {

        int array[] = {R.id.tvHKPhoto1, R.id.tvHKPhoto2, R.id.tvHKPhoto3, R.id.tvHKPhoto4, R.id.tvHKPhoto5, R.id.tvHKPhoto6,
                R.id.tvHKPhoto7, R.id.tvHKPhoto8, R.id.tvHKPhoto9, R.id.tvHKPhoto10, R.id.tvHKPhoto11};

        return array;
    }

    public static int[] getPhotoStringHidniIds() {

        int array[] = {R.string.hkphototextHindi1, R.string.hkphototextHindi2, R.string.hkphototextHindi3, R.string.hkphototextHindi4, R.string.hkphototextHindi5,
                R.string.hkphototextHindi6, R.string.hkphototextHindi7, R.string.hkphototextHindi8, R.string.hkphototextHindi9, R.string.hkphototextHindi10, R.string.hkphototextHindi11};

        return array;
    }

    public static int[] getYesButtonIdsArray() {
        int array[] = {R.id.btnHKQuestionYes1, R.id.btnHKQuestionYes2, R.id.btnHKQuestionYes3, R.id.btnHKQuestionYes4,
                R.id.btnHKQuestionYes5, R.id.btnHKQuestionYes6, R.id.btnHKQuestionYes7, R.id.btnHKQuestionYes8, R.id.btnHKQuestionYes9
                , R.id.btnHKQuestionYes10, R.id.btnHKQuestionYes11, R.id.btnHKQuestionYes12, R.id.btnHKQuestionYes13, R.id.btnHKQuestionYes14,
                R.id.btnHKQuestionYes15, R.id.btnHKQuestionYes16, R.id.btnHKQuestionYes17};
        return array;
    }

    public static int[] getNoButtonIdsArray() {
        int array[] = {R.id.btnHKQuestionNo1, R.id.btnHKQuestionNo2, R.id.btnHKQuestionNo3, R.id.btnHKQuestionNo4,
                R.id.btnHKQuestionNo5, R.id.btnHKQuestionNo6, R.id.btnHKQuestionNo7, R.id.btnHKQuestionNo8, R.id.btnHKQuestionNo9
                , R.id.btnHKQuestionNo10, R.id.btnHKQuestionNo11, R.id.btnHKQuestionNo12, R.id.btnHKQuestionNo13, R.id.btnHKQuestionNo14,
                R.id.btnHKQuestionNo15, R.id.btnHKQuestionNo16, R.id.btnHKQuestionNo17};
        return array;
    }

    public static String[] getYesSubQuestion() {
        String Yes[] = {"null", "Side Glass-Floor machine-Main door-Other", "null", "null", "null",
                "null", "Proper cleaning require-Ladder require-Other", "Painting require-Wall damaged-Proper cleaning require-Other", "Proper cleaning require-Poster frame not available-Brochure holder not available-Other",
                "Proper cleaning require-Fire bottle not available-Other", "Proper cleaning require-AC not available-Other", "Proper cleaning require-Open wiring-Other", "null", "null", "date", "date", "date"};

        return Yes;
    }

    public static String[] getNoSubQuestion() {
        String No[] = {"Proper cleaning require-It is on height-Flex damaged-Other", "null", "Proper cleaning require-Dust on top of ATM machine-Other",
                "Floor damaged-Proper cleaning require-Other", "Doormat not available-Dustbin not available-Other", "Leakage spot-Proper cleaning require-Ladder require-Other",
                "null", "null", "null", "null",
                "null", "null", "Backroom key not available-Proper cleaning require-Bank or CRA material available-Other"
                , "Backroom key not available-Proper cleaning require-Bank or CRA material available-Other", "Phenyl not available-Collin not available-Broom not available-Duster not available-Other"
                , "no date", "no date"};
        return No;
    }

    public static String[] getYesSubQuestionH() {
        String Yes[] = {"null", "साइड ग्लास पे दाग- फ्लोर मशीन पे दाग- मेन दरवाजे पे दाग- अन्य", "null", "null", "null",
                "null", "अच्छी सफाई की ज़रूरत है- सीडी की ज़रूरत है- अन्य ", "पेंटिंग की ज़रूरत है-दीवार टूटी हुई है-अच्छी सफाई की ज़रूरत है-अन्य", "अच्छी सफाई की ज़रूरत है- पोस्टर फ्रेम नही है-ब्रोचर होल्डर नही है - अन्य",
                "अच्छी सफाई की ज़रूरत ह- फायर बोटल नही है- अन्य", " अच्छी सफाई की ज़रूरत है- एसी नही है- अन्य", "अच्छी सफाई की ज़रूरत ह- खुली हुई वायरे है-अन्य", "null", "null", "date", "date", "date"};

        return Yes;
    }

    public static String[] getNoSubQuestionH() {
        String No[] = {"अच्छी सफाई की ज़रूरत है- साइनेज बोर्ड  उचाई पे है- फ्लेक्स खराब है- अन्य", "null", "अच्छी सफाई की ज़रूरत है- एटीएम मशीन के उपर धूल है- अन्य ",
                "फ्लोर टूटा है- अच्छी सफाई की ज़रूरत है- अन्य ", "डोरमॅट नही है -डस्टबिन नही है- अन्य", "पानी लीकेज दाग- अच्छी सफाई की ज़रूरत है- सीडी की ज़रूरत है- अन्य ",
                "null", "null", "null", "null",
                "null", "null", "बॅकरूम की चाबी नही है-अच्छी सफाई की ज़रूरत है-बॅंक या सीआरए का समान है-अन्य"
                , "बॅकरूम की चाबी नही है-अच्छी सफाई की ज़रूरत है-बॅंक या सीआरए का समान है-अन्य", "फिनाइल नही है-कोलिन नही है-झाड़ू नही है-पोछा नही है-अन्य"
                , "no date", "no date"};
        return No;
    }



    public static void setEnglishQuestion(String questionArray[], int textViewIds[], HKQuestions context) {

        for (int i = 0; i < textViewIds.length; i++) {
            ((TextView) context.findViewById(textViewIds[i])).setText(questionArray[i]);
        }

    }

    //this method cab be used to set TextViews form any one activity
    //HK and SRM Questions are getting set on Textview's using this method
    public static void setEnglishQuestionUsingActivity(String questionArray[], int textViewIds[], Activity context) {

        for (int i = 0; i < textViewIds.length; i++) {
            ((TextView) context.findViewById(textViewIds[i])).setText(questionArray[i]);
        }

    }


    public static int getPositionOfYesButton(long id, int buttonType) {
        int array[];
        if (buttonType == 1) {
            array = getYesButtonIdsArray();
        } else {
            array = getNoButtonIdsArray();
        }

        int position = 0;
        for (int i = 0; i < array.length; i++) {
            if (array[i] == id) {
                position = i;
                return position;
            }
        }
        return position;
    }


}
