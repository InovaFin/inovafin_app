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
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.airbnb.lottie.LottieAnimationView;
import com.example.inovafin.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActivityAddReceberBinding implements ViewBinding {
  @NonNull
  private final ScrollView rootView;

  @NonNull
  public final LinearLayout btAdicionar;

  @NonNull
  public final LottieAnimationView btAnimacao;

  @NonNull
  public final ImageView btCalendario;

  @NonNull
  public final LinearLayout btText;

  @NonNull
  public final EditText descricao;

  @NonNull
  public final ImageView icFechar;

  @NonNull
  public final EditText nomeReceber;

  @NonNull
  public final Spinner spinner;

  @NonNull
  public final TextView txtData;

  @NonNull
  public final EditText valorReceber;

  private ActivityAddReceberBinding(@NonNull ScrollView rootView, @NonNull LinearLayout btAdicionar,
      @NonNull LottieAnimationView btAnimacao, @NonNull ImageView btCalendario,
      @NonNull LinearLayout btText, @NonNull EditText descricao, @NonNull ImageView icFechar,
      @NonNull EditText nomeReceber, @NonNull Spinner spinner, @NonNull TextView txtData,
      @NonNull EditText valorReceber) {
    this.rootView = rootView;
    this.btAdicionar = btAdicionar;
    this.btAnimacao = btAnimacao;
    this.btCalendario = btCalendario;
    this.btText = btText;
    this.descricao = descricao;
    this.icFechar = icFechar;
    this.nomeReceber = nomeReceber;
    this.spinner = spinner;
    this.txtData = txtData;
    this.valorReceber = valorReceber;
  }

  @Override
  @NonNull
  public ScrollView getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityAddReceberBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityAddReceberBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_add_receber, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityAddReceberBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.btAdicionar;
      LinearLayout btAdicionar = ViewBindings.findChildViewById(rootView, id);
      if (btAdicionar == null) {
        break missingId;
      }

      id = R.id.btAnimacao;
      LottieAnimationView btAnimacao = ViewBindings.findChildViewById(rootView, id);
      if (btAnimacao == null) {
        break missingId;
      }

      id = R.id.btCalendario;
      ImageView btCalendario = ViewBindings.findChildViewById(rootView, id);
      if (btCalendario == null) {
        break missingId;
      }

      id = R.id.btText;
      LinearLayout btText = ViewBindings.findChildViewById(rootView, id);
      if (btText == null) {
        break missingId;
      }

      id = R.id.descricao;
      EditText descricao = ViewBindings.findChildViewById(rootView, id);
      if (descricao == null) {
        break missingId;
      }

      id = R.id.icFechar;
      ImageView icFechar = ViewBindings.findChildViewById(rootView, id);
      if (icFechar == null) {
        break missingId;
      }

      id = R.id.nomeReceber;
      EditText nomeReceber = ViewBindings.findChildViewById(rootView, id);
      if (nomeReceber == null) {
        break missingId;
      }

      id = R.id.spinner;
      Spinner spinner = ViewBindings.findChildViewById(rootView, id);
      if (spinner == null) {
        break missingId;
      }

      id = R.id.txtData;
      TextView txtData = ViewBindings.findChildViewById(rootView, id);
      if (txtData == null) {
        break missingId;
      }

      id = R.id.valorReceber;
      EditText valorReceber = ViewBindings.findChildViewById(rootView, id);
      if (valorReceber == null) {
        break missingId;
      }

      return new ActivityAddReceberBinding((ScrollView) rootView, btAdicionar, btAnimacao,
          btCalendario, btText, descricao, icFechar, nomeReceber, spinner, txtData, valorReceber);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
