package com.example.imagesegmentation.UI.viewmodals;

import androidx.lifecycle.ViewModel;

public class camXFragmentViewModal extends ViewModel {
    int State=0;
    public static  int STATE_STARTED=0;
    public static  int STATE_RESTARTED=1;
    camXFragmentViewModal()
    {
        State=1;
    }
    public int getState()
    {
        if(State==0)
            return STATE_STARTED;
        else
            return STATE_RESTARTED;
    }
}
