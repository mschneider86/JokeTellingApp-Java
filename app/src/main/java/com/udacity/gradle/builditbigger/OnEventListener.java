package com.udacity.gradle.builditbigger;

public interface OnEventListener<T>  {
    public void onSuccess(T object);
    public void onFailure(Exception e);
}
