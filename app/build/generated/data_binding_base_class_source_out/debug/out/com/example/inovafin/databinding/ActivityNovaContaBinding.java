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
import com.example.inovafin.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActivityNovaContaBinding implements ViewBinding {
  @NonNull
  private final ScrollView rootView;

  @NonNull
  public final LinearLayout btAlterarDados;

  @NonNull
  public final ImageView icFechar;

  @NonNull
  public final TextView msgErro;

  @NonNull
  public final EditText nomeConta;

  @NonNull
  public final EditText saldoAtual;

  @NonNull
  public final Spinner spinner;

  private ActivityNovaContaBinding(@NonNull ScrollView rootView,
      @NonNull LinearLayout btAlterarDados, @NonNull ImageView icFechar, @NonNull TextView msgErro,
      @NonNull EditText nomeConta, @NonNull EditText saldoAtual, @NonNull Spinner spinner) {
    this.rootView = rootView;
    this.btAlterarDados = btAlterarDados;
    this.icFechar = icFechar;
    this.msgErro = msgErro;
    this.nomeConta = nomeConta;
    this.saldoAtual = saldoAtual;
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

      id = R.id.icFechar;
      ImageView icFechar = ViewBindings.findChildViewById(rootView, id);
      if (icFechar == null) {
        break missingId;
      }

      id = R.id.msgErro;
      TextView msgErro = ViewBindings.findChildViewById(rootView, id);
      if (msgErro == null) {
        break missingId;
      }

      id = R.id.nomeConta;
      EditText nomeConta = ViewBindings.findChildViewById(rootView, id);
      if (nomeConta == null) {
        break missingId;
      }

      id = R.id.saldoAtual;
      EditText saldoAtual = ViewBindings.findChildViewById(rootView, id);
      if (saldoAtual == null) {
        break missingId;
      }

      id = R.id.spinner;
      Spinner spinner = ViewBindings.findChildViewById(rootView, id);
      if (spinner == null) {
        break missingId;
      }

      return new ActivityNovaContaBinding((ScrollView) rootView, btAlterarDados, icFechar, msgErro,
          nomeConta, saldoAtual, spinner);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
