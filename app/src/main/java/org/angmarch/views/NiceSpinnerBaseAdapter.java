package org.angmarch.views;

import android.content.Context;
import android.os.Build.VERSION;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import org.angmarch.views.R.id;
import org.angmarch.views.R.layout;

public abstract class NiceSpinnerBaseAdapter<T> extends BaseAdapter {
    private final PopUpTextAlignment horizontalAlignment;
    private final SpinnerTextFormatter spinnerTextFormatter;
    private int textColor;
    private int backgroundSelector;
    int selectedIndex;

    NiceSpinnerBaseAdapter(Context context, int textColor, int backgroundSelector, SpinnerTextFormatter spinnerTextFormatter, PopUpTextAlignment horizontalAlignment) {
        this.spinnerTextFormatter = spinnerTextFormatter;
        this.backgroundSelector = backgroundSelector;
        this.textColor = textColor;
        this.horizontalAlignment = horizontalAlignment;
    }

    public View getView(int position, @Nullable View convertView, ViewGroup parent) {
        Context context = parent.getContext();
        TextView textView;
        if (convertView == null) {
            convertView = View.inflate(context, layout.spinner_list_item, (ViewGroup)null);
            textView = (TextView)convertView.findViewById(id.text_view_spinner);
            if (VERSION.SDK_INT >= 16) {
                textView.setBackground(ContextCompat.getDrawable(context, this.backgroundSelector));
            }

            convertView.setTag(new NiceSpinnerBaseAdapter.ViewHolder(textView));
        } else {
            textView = ((NiceSpinnerBaseAdapter.ViewHolder)convertView.getTag()).textView;
        }

        textView.setText(this.spinnerTextFormatter.format(this.getItem(position).toString()));
        textView.setTextColor(this.textColor);
        this.setTextHorizontalAlignment(textView);
        return convertView;
    }

    private void setTextHorizontalAlignment(TextView textView) {
        switch(this.horizontalAlignment) {
            case START:
                textView.setGravity(8388611);
                break;
            case END:
                textView.setGravity(8388613);
                break;
            case CENTER:
                textView.setGravity(1);
        }

    }

    public int getSelectedIndex() {
        return this.selectedIndex;
    }

    void setSelectedIndex(int index) {
        this.selectedIndex = index;
    }

    public abstract T getItemInDataset(int var1);

    public long getItemId(int position) {
        return (long)position;
    }

    public abstract T getItem(int var1);

    public abstract int getCount();

    static class ViewHolder {
        TextView textView;

        ViewHolder(TextView textView) {
            this.textView = textView;
        }
    }
}
