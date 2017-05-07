package com.proyecto.tfg.superrunningnews.adapters;

import android.support.v7.widget.RecyclerView;
import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.SimpleItemAnimator;
import android.util.AttributeSet;

import com.stfalcon.chatkit.commons.models.IDialog;

/**
 * Created by SusVa on 5/05/17.
 */

public class DialogList extends RecyclerView {

    private DialogListStyle dialogStyle;

    public DialogList(Context context) {
        super(context);
    }

    public DialogList(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        parseStyle(context, attrs);
    }

    public DialogList(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        parseStyle(context, attrs);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();

        LinearLayoutManager layout = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        SimpleItemAnimator animator = new DefaultItemAnimator();

        setLayoutManager(layout);
        setItemAnimator(animator);
    }

    /**
     * Don't use this method for setting your adapter, otherwise exception will by thrown.
     * Call {@link #setAdapter(DialogsListAdapter)} instead.
     */
    @Override
    public void setAdapter(Adapter adapter) {
        throw new IllegalArgumentException("You can't set adapter to DialogsList. Use #setAdapter(DialogsListAdapter) instead.");
    }

    /**
     * Set adapter for DialogsList
     *
     * @param adapter  Adapter. Must extend DialogsListAdapter
     * @param <DIALOG> Dialog model class
     */
    public <DIALOG extends IDialog> void setAdapter(DialogsListAdapter<DIALOG> adapter) {
        SimpleItemAnimator itemAnimator = new DefaultItemAnimator();
        itemAnimator.setSupportsChangeAnimations(false);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL, true);

        setItemAnimator(itemAnimator);
        setLayoutManager(layoutManager);

        adapter.setStyle(dialogStyle);

        super.setAdapter(adapter);
    }

    @SuppressWarnings("ResourceType")
    private void parseStyle(Context context, AttributeSet attrs) {
        dialogStyle = DialogListStyle.parse(context, attrs);
    }
}