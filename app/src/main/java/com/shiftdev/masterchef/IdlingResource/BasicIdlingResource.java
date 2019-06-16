package com.shiftdev.masterchef.IdlingResource;

import androidx.annotation.Nullable;
import androidx.test.espresso.IdlingResource;

import java.util.concurrent.atomic.AtomicBoolean;


public class BasicIdlingResource implements IdlingResource {

     @Nullable
     private volatile ResourceCallback mCallback;

     private AtomicBoolean currentlyIdling = new AtomicBoolean(true);

     @Override
     public String getName() {
          return this.getClass().getName();
     }

     @Override
     public boolean isIdleNow() {
          return currentlyIdling.get();
     }

     @Override
     public void registerIdleTransitionCallback(ResourceCallback callback) {
          mCallback = callback;
     }

     public void setIdleState(boolean currentlyAtIdle) {
          currentlyIdling.set(currentlyAtIdle);
          if (currentlyAtIdle && mCallback != null) {
               mCallback.onTransitionToIdle();
          }
     }

}
