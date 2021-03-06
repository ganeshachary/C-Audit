package com.spottechnicians.caudit.utils;

import android.app.Activity;
import android.widget.TextView;

import com.spottechnicians.caudit.ModuleCT.CT_Questions;
import com.spottechnicians.caudit.R;

/**
 * Created by onestech on 10/06/16.
 */
public class UtilCT {


    public static int[] getResourceQuestion() {
        int array[] = {
                R.id.tvCTQuestion1, R.id.tvCTQuestion2, R.id.tvCTQuestion3, R.id.tvCTQuestion4, R.id.tvCTQuestion5,
                R.id.tvCTQuestion6, R.id.tvCTQuestion7, R.id.tvCTQuestion8, R.id.tvCTQuestion9,
                R.id.tvCTQuestion10, R.id.tvCTQuestion11, R.id.tvCTQuestion12
        };
        return array;
    }

    public static int[] getPhotoTextIds() {

        int array[] = {R.id.ctPhotoText1, R.id.ctPhotoText2, R.id.ctPhotoText3};

        return array;
    }

    public static int[] getPhotoStringHidniIds() {

        int array[] = {R.string.ctphoto1Hinditext1, R.string.ctphoto1Hinditext2, R.string.ctphoto1Hinditext3};

        return array;
    }

    public static int[] getYesButtonIdsArray() {
        int array[] = {R.id.btnCTQuestionYes1, R.id.btnCTQuestionYes2, R.id.btnCTQuestionYes3, R.id.btnCTQuestionYes4, R.id.btnCTQuestionYes5,
                R.id.btnCTQuestionYes6, R.id.btnCTQuestionYes7, R.id.btnCTQuestionYes8, R.id.btnCTQuestionYes9,
                R.id.btnCTQuestionYes10, R.id.btnCTQuestionYes11, R.id.btnCTQuestionYes12};
        return array;
    }

    public static int[] getNoButtonIdsArray() {
        int array[] = {R.id.btnCTQuestionNo1, R.id.btnCTQuestionNo2, R.id.btnCTQuestionNo3, R.id.btnCTQuestionNo4, R.id.btnCTQuestionNo5,
                R.id.btnCTQuestionNo6, R.id.btnCTQuestionNo7, R.id.btnCTQuestionNo8, R.id.btnCTQuestionNo9, R.id.btnCTQuestionNo10,
                R.id.btnCTQuestionNo11, R.id.btnCTQuestionNo12};
        return array;
    }

    public static String[] getYesSubQuestion() {
        String Yes[] = {"null", "ID expired-ID is valid", "null", "null", "null",
                "Reliever not come-CT on leave-Other", "null", "null", "null", "null", "null",
                "Power disconnection-Load shedding-Electrical fault-ATM is oout of service-Other"};

        return Yes;
    }

    public static String[] getNoSubQuestion() {
        String No[] = {"Went for nature call-went for lunch or dinner-medical emergency-Other", "Not Provided-ID misplaced-Forgot to bring",
                "Not provided-Under washing-Torn-Other", "Not provided-Technical issue-Other", "Damaged-Not provided-Other", "null",
                "Attendance reg n/a-Break reg n/a-Vendor reg n/a-LogBook n/a-Other", "New Caretaker-Other", "New Caretaker-Other",
                "New Caretaker-Other", "Not provided by bank-Other", "null"};
        return No;
    }

/*

    नहीं दिया गया
            अन्य

    नया केअरटेकर-अन्य

    बिजली कट-बिजली की कटौती-बिजली समस्या-एटीएम सेवा से बाहर है-अन्य
*/

    public static String[] getYesSubQuestionH() {
        String Yes[] = {"null", "आईडी की तारीख समाप्त हो गई है - आईडी वैध है", "null", "null", "null",
                "रिलिवर नहीं आया -दूसरा केअरटेकर छुट्टी पर है-अन्य", "null", "null", "null", "null", "null",
                "बिजली कटी है-बिजली की कटौती-बिजली समस्या-एटीएम सेवा से बाहर है-अन्य"};

        return Yes;
    }

    public static String[] getNoSubQuestionH() {
        String No[] = {"शौचालय के लिए गया है- खाना खाने गया है- स्वास्थ्य समस्या-अन्य", "आईडी नहीं दिया गया है-आईडी खो दिया है-आईडी लाना भूल गया है",
                "यूनिफॉर्म नहीं दी गयी है-यूनिफॉर्म धोने दी है-यूनिफॉर्म फटी है-अन्य", "टेलिफोन नहीं दिया गया है-तकनीकी समस्या -अन्य", "कुर्सी टूटी हुई है-कुर्सी नही दी गयी-अन्य", "null",
                "अटेंडेन्स रजिस्टर नही हैं-ब्रेक रजिस्टर नही हैं-वेंडर रजिस्टर नही हैं-लॉगबुक नही हैं- अन्य", "नया केअरटेकर है-अन्य", "नया केअरटेकर है-अन्य",
                "नया केअरटेकर है-अन्य", "बैंक द्वारा प्राप्त नही-अन्य", "null"};
        return No;
    }


    public static void setEnglishQuestion(String questionArray[], int textViewIds[], CT_Questions context) {

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
            array = UtilCT.getYesButtonIdsArray();
        } else {
            array = UtilCT.getNoButtonIdsArray();
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
