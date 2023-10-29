// Generated by view binder compiler. Do not edit!
package com.example.inovafin.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.inovafin.R;
import com.google.android.material.imageview.ShapeableImageView;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActivityNovaContaBinding implements ViewBinding {
  @NonNull
  private final ScrollView rootView;

  @NonNull
  public final LinearLayout btAlterarDados;

  @NonNull
  public final LinearLayout btAlterarFoto;

  @NonNull
  public final LinearLayout btExcluir;

  @NonNull
  public final ImageView icFechar;

  @NonNull
  public final ShapeableImageView imagemUsuario;

  @NonNull
  public final EditText nomeUsuario;

  @NonNull
  public final EditText novaSenha;

  @NonNull
  public final EditText senhaUsuario;

  @NonNull
  public final Spinner spinner;

  private ActivityNovaContaBinding(@NonNull ScrollView rootView,
      @NonNull LinearLayout btAlterarDados, @NonNull LinearLayout btAlterarFoto,
      @NonNull LinearLayout btExcluir, @NonNull ImageView icFechar,
      @NonNull ShapeableImageView imagemUsuario, @NonNull EditText nomeUsuario,
      @NonNull EditText novaSenha, @NonNull EditText senhaUsuario, @NonNull Spinner spinner) {
    this.rootView = rootView;
    this.btAlterarDados = btAlterarDados;
    this.btAlterarFoto = btAlterarFoto;
    this.btExcluir = btExcluir;
    this.icFechar = icFechar;
    this.imagemUsuario = imagemUsuario;
    this.nomeUsuario = nomeUsuario;
    this.novaSenha = novaSenha;
    this.senhaUsuario = senhaUsuario;
    this.spinner = spinner;
  }

  @Override
  @NonNull
  public ScrollView getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityNovaContaBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityNovaContaBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_nova_conta, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityNovaContaBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.btAlterarDados;
      LinearLayout btAlterarDados = ViewBindings.findChildViewById(rootView, id);
      if (btAlterarDados == null) {
        break missingId;
      }

      id = R.id.btAlterarFoto;
      LinearLayout btAlterarFoto = ViewBindings.findChildViewById(rootView, id);
      if (btAlterarFoto == null) {
        break missingId;
      }

      id = R.id.btExcluir;
      LinearLayout btExcluir = ViewBindings.findChildViewById(rootView, id);
      if (btExcluir == null) {
        break missingId;
      }

      id = R.id.icFechar;
      ImageView icFechar = ViewBindings.findChildViewById(rootView, id);
      if (icFechar == null) {
        break missingId;
      }

      id = R.id.imagemUsuario;
      ShapeableImageView imagemUsuario = ViewBindings.findChildViewById(rootView, id);
      if (imagemUsuario == null) {
        break missingId;
      }

      id = R.id.nomeUsuario;
      EditText nomeUsuario = ViewBindings.findChildViewById(rootView, id);
      if (nomeUsuario == null) {
        break missingId;
      }

      id = R.id.novaSenha;
      EditText novaSenha = ViewBindings.findChildViewById(rootView, id);
      if (novaSenha == null) {
        break missingId;
      }

      id = R.id.senhaUsuario;
      EditText senhaUsuario = ViewBindings.findChildViewById(rootView, id);
      if (senhaUsuario == null) {
        break missingId;
      }

      id = R.id.spinner;
      Spinner spinner = ViewBindings.findChildViewById(rootView, id);
      if (spinner == null) {
        break missingId;
      }

      return new ActivityNovaContaBinding((ScrollView) rootView, btAlterarDados, btAlterarFoto,
          btExcluir, icFechar, imagemUsuario, nomeUsuario, novaSenha, senhaUsuario, spinner);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
