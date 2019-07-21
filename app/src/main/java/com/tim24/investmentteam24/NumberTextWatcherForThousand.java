package com.tim24.investmentteam24;

import android.support.design.widget.TextInputEditText;
import android.text.Editable;
import android.text.TextWatcher;

import java.util.StringTokenizer;

/**
 * Created by skb on 12/14/2015.
 */
public class NumberTextWatcherForThousand implements TextWatcher {

    private TextInputEditText editText;
    private Boolean nullable;

    public NumberTextWatcherForThousand(TextInputEditText editText) {
        this.editText = editText;
        this.nullable = true;
    }

    public NumberTextWatcherForThousand(TextInputEditText editText, Boolean nullable) {
        this.editText = editText;
        this.nullable = nullable;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        String value = editText.getText().toString();

        try
        {
            editText.removeTextChangedListener(this);
            if (!nullable) {
                if (value.equals(""))
                    editText.setError("Bagian ini tidak boleh kosong.");
            }
            if (!value.equals(""))
            {
                if(value.startsWith(".")){
                    editText.setText("0.");
                }
                if(value.startsWith("0") && !value.startsWith("0.")){
                    editText.setText("");
                }
                String str = editText.getText().toString().replace(".", "");
                editText.setText(getDecimalFormattedString(str));
                editText.setSelection(editText.getText().toString().length());
            }
            editText.addTextChangedListener(this);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            editText.addTextChangedListener(this);
        }

    }

    public static String getDecimalFormattedString(String value)
    {
        StringTokenizer lst = new StringTokenizer(value, ".");
        String str1 = value;
        String str2 = "";
        if (lst.countTokens() > 1)
        {
            str1 = lst.nextToken();
            str2 = lst.nextToken();
        }
        StringBuilder str3 = new StringBuilder();
        int i = 0;
        int j = -1 + str1.length();
        if (str1.charAt( -1 + str1.length()) == ',')
        {
            j--;
            str3 = new StringBuilder(",");
        }
        for (int k = j;; k--) {
            if (k < 0) {
                if (str2.length() > 0)
                    str3.append(",").append(str2);
                return str3.toString();
            }
            if (i == 3) {
                str3.insert(0, ".");
                i = 0;
            }
            str3.insert(0, str1.charAt(k));
            i++;
        }

    }

    static String trimCommaOfString(String string) {
        // String returnString;
        if (string.contains(".")) { return string.replace(".","");}
        else { return string; }

    }
}
