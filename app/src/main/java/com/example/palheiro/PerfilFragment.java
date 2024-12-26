package com.example.palheiro;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.palheiro.listeners.ProfileListener;
import com.example.palheiro.modelo.SingletonPalheiro;
import com.example.palheiro.modelo.UserProfile;


public class PerfilFragment extends Fragment implements ProfileListener {

    private TextView tvUsername, tvEmail, tvNIF;
    private EditText etMorada, etMorada2, etCodigoPostal;
    private Button btnGuardar;

    public PerfilFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_perfil, container, false);

        SingletonPalheiro.getInstance(getContext()).setProfileListener(this);

        tvUsername = view.findViewById(R.id.tvProfileUsername);
        tvEmail = view.findViewById(R.id.tvProfileEmail);
        tvNIF = view.findViewById(R.id.tvProfileNIF);
        etMorada = view.findViewById(R.id.etProfileMorada);
        etCodigoPostal = view.findViewById(R.id.etProfileCodigoPostal);
        etMorada2 = view.findViewById(R.id.etProfileMorada2);
        btnGuardar = view.findViewById(R.id.btnEditProfile);

        SingletonPalheiro.getInstance(getContext()).getProfileAPI(getContext());

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SingletonPalheiro.getInstance(getContext()).editProfileAPI(getContext(), etMorada.getText().toString(), etMorada2.getText().toString(), etCodigoPostal.getText().toString());
            }
        });


        return view;
    }

    @Override
    public void onRefreshProfile(UserProfile profile) {
        tvUsername.setText(profile.getUsername());
        tvEmail.setText(profile.getEmail());
        tvNIF.setText(profile.getNif());
        etMorada.setText(profile.getMorada());
        etMorada2.setText(profile.getMorada2());
        etCodigoPostal.setText(profile.getCodigoPostal());
    }

    @Override
    public void onUpdateProfile(Context context) {
        Toast.makeText(context, "Perfil editado com sucesso", Toast.LENGTH_SHORT).show();
    }
}