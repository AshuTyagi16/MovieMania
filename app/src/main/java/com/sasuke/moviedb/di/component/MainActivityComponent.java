package com.sasuke.moviedb.di.component;

import com.sasuke.moviedb.activity.MainActivity;
import com.sasuke.moviedb.di.module.MainActivityModule;
import com.sasuke.moviedb.di.scope.PerActivityScope;

import dagger.Component;

/**
 * Created by abc on 5/3/2018.
 */

@PerActivityScope
@Component(modules = MainActivityModule.class, dependencies = {MovieManiaApplicationComponent.class})
public interface MainActivityComponent {

    void injectMainActivity(MainActivity mainActivity);
}
