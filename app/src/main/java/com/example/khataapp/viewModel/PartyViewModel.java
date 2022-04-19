package com.example.khataapp.viewModel;

import static com.example.khataapp.utils.CONSTANTS.GET_PARTY;
import static com.example.khataapp.utils.CONSTANTS.SERVER_ERROR;
import static com.example.khataapp.utils.CONSTANTS.VOUCHER_RESPONSE;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.khataapp.Interface.CallBackListener;
import com.example.khataapp.Repository.PartyRepository;
import com.example.khataapp.models.GetPartyServerResponse;
import com.example.khataapp.models.Party;
import com.example.khataapp.models.response.voucher.VoucherDetail;
import com.example.khataapp.models.response.voucher.VoucherResponse;
import com.example.khataapp.utils.SharedPreferenceHelper;
import com.example.khataapp.views.party.adapter.VoucherDetailAdapter;

import java.util.List;

public class PartyViewModel extends BaseViewModel {

    private final PartyRepository partyRepository;
    private final MutableLiveData<Party> partyMutableLiveData;
    private final VoucherDetailAdapter adapter;
    private final ObservableField<String> totalAmount;



    public PartyViewModel(@NonNull Application application) {
        super(application);
        partyRepository= new PartyRepository();
        partyMutableLiveData= new MutableLiveData<>();
        adapter= new VoucherDetailAdapter(this);
        totalAmount= new ObservableField<>("");
        getServerResponse();


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

    public VoucherDetailAdapter getAdapter() {
        return adapter;
    }

    public ObservableField<String> getTotalAmount() {
        return totalAmount;
    }

    public void getParty()
    {
        String businessID= SharedPreferenceHelper.getInstance(getApplication()).getBUSINESS_ID();

        partyRepository.getPartiesFromServer("s",businessID);

    }

    public void voucherDetail(String partyCode)
    {
        String businessId= SharedPreferenceHelper.getInstance(getApplication()).getBUSINESS_ID();

        partyRepository.getVoucherDetails(partyCode,businessId);
    }

    private void getServerResponse() {

        partyRepository.setCallBackListener((object, key) -> {
            if (object!=null)
            {
                if (key== GET_PARTY)
                {
                    GetPartyServerResponse partyServerResponse = (GetPartyServerResponse) object;

                    setShowProgressDialog(false);
                }
                else if (key==SERVER_ERROR)
                {
                    toastMessage.setValue((String) object);
                    setShowProgressDialog(false);
                }
                else if (key==VOUCHER_RESPONSE)
                {
                    VoucherResponse voucherResponse = (VoucherResponse) object;
                    totalAmount.set(String.valueOf(voucherResponse.getVoucherData().getVoucherSummary().getTotalAmount()));
                    adapter.setVoucherDetailList(voucherResponse.getVoucherData().getDetail());
                    toastMessage.setValue(voucherResponse.getMessage());
                    setShowProgressDialog(false);
                }
            }

        });
    }
}
