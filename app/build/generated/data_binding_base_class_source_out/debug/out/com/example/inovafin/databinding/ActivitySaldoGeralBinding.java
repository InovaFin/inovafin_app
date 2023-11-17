// Generated by view binder compiler. Do not edit!
package com.example.inovafin.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.inovafin.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActivitySaldoGeralBinding implements ViewBinding {
  @NonNull
  private final ScrollView rootView;

  @NonNull
  public final ImageView btAjuda;

  @NonNull
  public final ImageView btCalculadora;

  @NonNull
  public final ImageView btExpandir;

  @NonNull
  public final LinearLayout btSaldoGeralGuardado;

  @NonNull
  public final LinearLayout btSaldoGeralPagar;

  @NonNull
  public final LinearLayout btSaldoGeralReceber;

  @NonNull
  public final ImageView icFechar;

  @NonNull
  public final TextView saldoGeral;

  private ActivitySaldoGeralBinding(@NonNull ScrollView rootView, @NonNull ImageView btAjuda,
      @NonNull ImageView btCalculadora, @NonNull ImageView btExpandir,
      @NonNull LinearLayout btSaldoGeralGuardado, @NonNull LinearLayout btSaldoGeralPagar,
      @NonNull LinearLayout btSaldoGeralReceber, @NonNull ImageView icFechar,
      @NonNull TextView saldoGeral) {
    this.rootView = rootView;
    this.btAjuda = btAjuda;
    this.btCalculadora = btCalculadora;
    this.btExpandir = btExpandir;
    this.btSaldoGeralGuardado = btSaldoGeralGuardado;
    this.btSaldoGeralPagar = btSaldoGeralPagar;
    this.btSaldoGeralReceber = btSaldoGeralReceber;
    this.icFechar = icFechar;
    this.saldoGeral = saldoGeral;
  }

  @Override
  @NonNull
  public ScrollView getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivitySaldoGeralBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivitySaldoGeralBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_saldo_geral, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivitySaldoGeralBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.btAjuda;
      ImageView btAjuda = ViewBindings.findChildViewById(rootView, id);
      if (btAjuda == null) {
        break missingId;
      }

      id = R.id.btCalculadora;
      ImageView btCalculadora = ViewBindings.findChildViewById(rootView, id);
      if (btCalculadora == null) {
        break missingId;
      }

      id = R.id.btExpandir;
      ImageView btExpandir = ViewBindings.findChildViewById(rootView, id);
      if (btExpandir == null) {
        break missingId;
      }

      id = R.id.btSaldoGeralGuardado;
      LinearLayout btSaldoGeralGuardado = ViewBindings.findChildViewById(rootView, id);
      if (btSaldoGeralGuardado == null) {
        break missingId;
      }

      id = R.id.btSaldoGeralPagar;
      LinearLayout btSaldoGeralPagar = ViewBindings.findChildViewById(rootView, id);
      if (btSaldoGeralPagar == null) {
        break missingId;
      }

      id = R.id.btSaldoGeralReceber;
      LinearLayout btSaldoGeralReceber = ViewBindings.findChildViewById(rootView, id);
      if (btSaldoGeralReceber == null) {
        break missingId;
      }

      id = R.id.icFechar;
      ImageView icFechar = ViewBindings.findChildViewById(rootView, id);
      if (icFechar == null) {
        break missingId;
      }

      id = R.id.saldoGeral;
      TextView saldoGeral = ViewBindings.findChildViewById(rootView, id);
      if (saldoGeral == null) {
        break missingId;
      }

      return new ActivitySaldoGeralBinding((ScrollView) rootView, btAjuda, btCalculadora,
          btExpandir, btSaldoGeralGuardado, btSaldoGeralPagar, btSaldoGeralReceber, icFechar,
          saldoGeral);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
