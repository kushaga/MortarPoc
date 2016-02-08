package com.example.akosha.sample1.poc2.mortarsupport;

import android.view.Menu;

public interface ActionBarModifier {
  boolean onPrepareOptionsMenu(Menu menu);

  String getTitle();
}
