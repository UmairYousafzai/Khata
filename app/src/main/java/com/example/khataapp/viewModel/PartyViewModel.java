package com.example.khataapp.viewModel;

import static com.example.khataapp.utils.CONSTANTS.GET_PARTY;
import static com.example.khataapp.utils.CONSTANTS.SERVER_ERROR;
import static com.example.khataapp.utils.CONSTANTS.VOUCHER_RESPONSE;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;
import androidx.lifecycle.MutableLiveData;

import com.example.khataapp.Repository.PartyRepository;
import com.example.khataapp.models.GetPartiesServerResponse;
import com.example.khataapp.models.Party;
import com.example.khataapp.models.response.voucher.VoucherResponse;
import com.example.khataapp.utils.SharedPreferenceHelper;
import com.example.khataapp.views.party.adapter.VoucherDetailAdapter;

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
                    GetPartiesServerResponse partyServerResponse = (GetPartiesServerResponse) object;

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
                    if(voucherResponse.getVoucherData()!=null&& voucherResponse.getVoucherData().getVoucherSummary()!=null)
                    {
                        totalAmount.set(String.valueOf(voucherResponse.getVoucherData().getVoucherSummary().getTotalAmount()));

                    }
                    if (voucherResponse.getVoucherData() != null) {
                        adapter.setVoucherDetailList(voucherResponse.getVoucherData().getDetail());
                    }
                    toastMessage.setValue(voucherResponse.getMessage());
                    setShowProgressDialog(false);
                }
            }

        });
    }
}
