/* ******************************************************************************
 * Copyright 2016 stfalcon.com
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/

package com.proyecto.tfg.superrunningnews.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.proyecto.tfg.superrunningnews.base.BaseChatFragment;
import com.proyecto.tfg.superrunningnews.activities.MessagesActivity;
import com.proyecto.tfg.superrunningnews.R;
import com.proyecto.tfg.superrunningnews.models.Dialog;
import com.proyecto.tfg.superrunningnews.models.Mensaje;
import com.proyecto.tfg.superrunningnews.models.Usuario;
import com.stfalcon.chatkit.commons.models.IDialog;
import com.stfalcon.chatkit.dialogs.DialogsList;
import com.stfalcon.chatkit.dialogs.DialogsListAdapter;
import com.stfalcon.chatkit.utils.DateFormatter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;

public class ChatFragment extends BaseChatFragment implements DateFormatter.Formatter {

    private ArrayList<Dialog> tGrupos;

    public ChatFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_chat, container, false);
        tGrupos = new ArrayList<>();
        crearReferenciaFirebase();

        return v;
    }

    private void crearReferenciaFirebase() {
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference ref = db.getReference("grupos/");
        ref.addListenerForSingleValueEvent(ref_ValueEventListener);
    }

    private ValueEventListener ref_ValueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            cargarTodosMensajes(dataSnapshot);
            Collections.sort(tGrupos);
            initAdapter();
        }

        private void cargarTodosMensajes(DataSnapshot dataSnapshot) {
            Mensaje lastMensaje = Mensaje.createEmtpy();
            ArrayList<Usuario> user = new ArrayList<>();
            for (DataSnapshot data : dataSnapshot.getChildren()) {
                tGrupos.add(crearGrupo(data, lastMensaje, user));
            }
        }

        private Dialog crearGrupo(DataSnapshot data, Mensaje lastMensaje, ArrayList<Usuario> user) {
            Dialog dialog = data.getValue(Dialog.class);
            dialog.setLastMessage(lastMensaje);
            dialog.setUsers(user);
            return dialog;
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {
            Toast.makeText(getContext(), "Error al cargar los grupos.", Toast.LENGTH_SHORT).show();
        }
    };

    private void initAdapter() {
        DialogsList dialogsList = (DialogsList) getView().findViewById(R.id.dialogsList);
        dialogsAdapter = new DialogsListAdapter<>(imageLoader);
        configurarAdaptadorGrupos();
        establecerAdaptadorEnLista(dialogsList);
    }

    private void configurarAdaptadorGrupos() {
        dialogsAdapter.setItems(tGrupos);
        anadirListenersAdaptadorGrupos();
    }

    private void anadirListenersAdaptadorGrupos() {
        dialogsAdapter.setOnDialogClickListener(clickListener);
        dialogsAdapter.setOnDialogLongClickListener(this);
        dialogsAdapter.setDatesFormatter(this);
    }

    private void establecerAdaptadorEnLista(DialogsList dialogsList) {
        dialogsList.setAdapter(dialogsAdapter);
        dialogsList.scrollToPosition(tGrupos.size() - 1);
    }

    private DialogsListAdapter.OnDialogClickListener clickListener = new DialogsListAdapter.OnDialogClickListener() {
        @Override
        public void onDialogClick(IDialog dialog) {
            Intent i = new Intent(getContext(), MessagesActivity.class);
            i.putExtra("titulo", dialog.getDialogName());
            startActivity(i);
        }
    };

    @Override
    public String format(Date date) {
        if (Calendar.getInstance().getTime().compareTo(date) == 0) {
            return "";
        } else if (DateFormatter.isToday(date)) {
            return DateFormatter.format(date, DateFormatter.Template.TIME);
        } else {
            return DateFormatter.format(date, DateFormatter.Template.STRING_DAY_MONTH_YEAR);
        }
    }

    @Override
    public void onDialogClick(Dialog dialog) {
    }

}
