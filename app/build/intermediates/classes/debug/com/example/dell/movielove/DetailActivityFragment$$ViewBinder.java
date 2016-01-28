// Generated code from Butter Knife. Do not modify!
package com.example.dell.movielove;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class DetailActivityFragment$$ViewBinder<T extends com.example.dell.movielove.DetailActivityFragment> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131492931, "field 'mtitle'");
    target.mtitle = finder.castView(view, 2131492931, "field 'mtitle'");
    view = finder.findRequiredView(source, 2131492994, "field 'moverView'");
    target.moverView = finder.castView(view, 2131492994, "field 'moverView'");
    view = finder.findRequiredView(source, 2131492998, "field 'mrate'");
    target.mrate = finder.castView(view, 2131492998, "field 'mrate'");
    view = finder.findRequiredView(source, 2131492995, "field 'mrelease'");
    target.mrelease = finder.castView(view, 2131492995, "field 'mrelease'");
    view = finder.findRequiredView(source, 2131492940, "field 'mscroller'");
    target.mscroller = finder.castView(view, 2131492940, "field 'mscroller'");
    view = finder.findRequiredView(source, 2131492991, "field 'you_t1'");
    target.you_t1 = finder.castView(view, 2131492991, "field 'you_t1'");
    view = finder.findRequiredView(source, 2131492992, "field 'you_t2'");
    target.you_t2 = finder.castView(view, 2131492992, "field 'you_t2'");
    view = finder.findRequiredView(source, 2131492989, "field 'you_tube'");
    target.you_tube = finder.castView(view, 2131492989, "field 'you_tube'");
    view = finder.findRequiredView(source, 2131492990, "field 'you_tube1'");
    target.you_tube1 = finder.castView(view, 2131492990, "field 'you_tube1'");
  }

  @Override public void unbind(T target) {
    target.mtitle = null;
    target.moverView = null;
    target.mrate = null;
    target.mrelease = null;
    target.mscroller = null;
    target.you_t1 = null;
    target.you_t2 = null;
    target.you_tube = null;
    target.you_tube1 = null;
  }
}
