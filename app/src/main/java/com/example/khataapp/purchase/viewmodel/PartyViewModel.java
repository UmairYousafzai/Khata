package com.example.khataapp.purchase.viewmodel;

import static com.example.khataapp.utils.CONSTANTS.GET_PARTY;
import static com.example.khataapp.utils.CONSTANTS.SERVER_ERROR;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.khataapp.Interface.CallBackListener;
import com.example.khataapp.models.GetPartyServerResponse;
import com.example.khataapp.models.Party;
import com.example.khataapp.purchase.adapter.PartyRecyclerAdapter;
import com.example.khataapp.purchase.repository.PartyRepository;
import com.example.khataapp.utils.SharedPreferenceHelper;

public class PartyViewModel extends AndroidViewModel {

    private final PartyRepository partyRepository;
    private final PartyRecyclerAdapter adapter;
    private final MutableLiveData<String> toastMessage;
    private final MutableLiveData<Integer> progressMutableLiveData;
    private final MutableLiveData<Party> partyMutableLiveData;



    public PartyViewModel(@NonNull Application application) {
        super(application);
        partyRepository= new PartyRepository();
        adapter = new PartyRecyclerAdapter(this);
        toastMessage= new MutableLiveData<>();
        progressMutableLiveData= new MutableLiveData<>();
        partyMutableLiveData= new MutableLiveData<>();

    }

    public void onClick(Party party)
    {
        if (party!=null)
        {
            partyMutableLiveData.setValue(party);

        }
    }

    public MutableLiveData<Party> getPartyMutableLiveData() {
        return partyMutableLiveData;
    }

    public MutableLiveData<Integer> getProgressMutableLiveData() {
        return progressMutableLiveData;
    }

    public MutableLiveData<String> getToastMessage() {
        return toastMessage;
    }

    public PartyRecyclerAdapter getAdapter() {
        return adapter;
    }

    public void getSuppliers()
    {
        String businessID= SharedPreferenceHelper.getInstance(getApplication()).getBUSINESS_ID();

        partyRepository.getPartiesFromServer("s",businessID);

        getServerResponse();
    }

    private void getServerResponse() {

        partyRepository.setCallBackListener(new CallBackListener() {
            @Override
            public void getServerResponse(Object object, int key) {
                if (object!=null)
                {
                    if (key== GET_PARTY)
                    {
                        GetPartyServerResponse partyServerResponse = (GetPartyServerResponse) object;

                        adapter.setPartyListFull(partyServerResponse.getPartyList());
                        progressMutableLiveData.setValue(1);
                    }
                    else if (key==SERVER_ERROR)
                    {
                        toastMessage.setValue((String) object);
                        progressMutableLiveData.setValue(1);
                    }
                }

            }
        });
    }
}
