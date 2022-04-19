package com.example.khataapp.viewModel;

import static com.example.khataapp.utils.CONSTANTS.SERVER_ERROR;
import static com.example.khataapp.utils.CONSTANTS.SERVER_RESPONSE;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;

import com.example.khataapp.Repository.PartyRepository;
import com.example.khataapp.models.response.ServerResponse;
import com.example.khataapp.models.request.Voucher;
import com.example.khataapp.utils.SharedPreferenceHelper;

import java.math.BigDecimal;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

public class AddAmountViewModel extends BaseViewModel {

    private static final String TAG = AddAmountViewModel.class.getSimpleName();
    private ObservableField<String> amountLiveData;
    private ObservableField<String> amountDetailLiveData;
    private ObservableField<String> billNumLiveData;
    private ObservableField<String> remarksLiveData;
    private ObservableField<Boolean> amountDetailVisibilityLiveData;
    private final String  pattern = "+-/%xrc=";
    private String lastExpression = "", date="",billImage="",voucherType="",partyCode;
    private PartyRepository repository;

    private final static int IS_NUMBER = 0;
    private final static int IS_OPERAND = 1;
    private final static int IS_DOT = 4;

    private boolean dotUsed = false;

    private boolean equalClicked = false,isCalculated=false;
    private final ScriptEngine scriptEngine;
    private double finalAmount=0.0;



    public AddAmountViewModel(@NonNull Application application) {
        super(application);
        amountLiveData = new ObservableField<>("");
        amountDetailLiveData = new ObservableField<>("");
        billNumLiveData = new ObservableField<>("");
        remarksLiveData = new ObservableField<>("");
        amountDetailVisibilityLiveData = new ObservableField<>(false);
        scriptEngine = new ScriptEngineManager().getEngineByName("rhino");
        repository= new PartyRepository();

    }

    public void onClick(String key) {
        Log.e(TAG, key);



        if (!pattern.contains(key)) {
            if (key.equals("."))
            {
                if (addDot()) equalClicked = false;

            }
            else
            {
                if (addNumber(key)) equalClicked = false;

            }

        } else {
                if (checkSymbolIsNonArithmetics(key)) {

                    if (key.equals("/"))
                    {
                        if (addOperand("\u00F7")) equalClicked = false;

                    }
                    else
                    {
                        if (addOperand(key)) equalClicked = false;

                    }

                } else {

                    if (key.equals("c")) {
                        amountLiveData.set("");
                        amountDetailLiveData.set("");
                    }
                    else if (key.equals("="))
                    {
                        if (amountLiveData.get() != null && !amountLiveData.get().equals(""))
                            calculate(amountLiveData.get());
                    }
                    else {
                        if (amountLiveData.get().length() > 0)
                            try {
                                String amount = amountLiveData.get().substring(0, amountLiveData.get().length() - 1);
                                amountLiveData.set(amount);
                                if (amountLiveData.get().isEmpty())
                                {
                                    amountDetailLiveData.set("");
                                    finalAmount=0.0;
                                }


                            } catch (Exception e) {
                                Log.e(TAG, e.toString());
                            }

                    }
                }



        }


    }

    private boolean checkSymbolIsNonArithmetics(String key) {
        if (!key.equals("c") && !key.equals("r")&&!key.equals("=")) {
            return true;
        } else {
            return false;
        }
    }





    public ObservableField<String> getAmountLiveData() {
        return amountLiveData;
    }

    public void setAmountLiveData(ObservableField<String> amountLiveData) {
        this.amountLiveData = amountLiveData;
    }

    public ObservableField<String> getAmountDetailLiveData() {
        return amountDetailLiveData;
    }

    public void setAmountDetailLiveData(ObservableField<String> amountDetailLiveData) {
        this.amountDetailLiveData = amountDetailLiveData;
    }

    public ObservableField<Boolean> getAmountDetailVisibilityLiveData() {
        return amountDetailVisibilityLiveData;
    }

    public void setAmountDetailVisibilityLiveData(ObservableField<Boolean> amountDetailVisibilityLiveData) {
        this.amountDetailVisibilityLiveData = amountDetailVisibilityLiveData;
    }

    public ObservableField<String> getBillNumLiveData() {
        return billNumLiveData;
    }

    public void setBillNumLiveData(ObservableField<String> billNumLiveData) {
        this.billNumLiveData = billNumLiveData;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setBillImage(String billImage) {
        this.billImage = billImage;
    }

    public void setVoucherType(String voucherType) {
        this.voucherType = voucherType;
    }

    public void setPartyCode(String partyCode) {
        this.partyCode = partyCode;
    }

    public ObservableField<String> getRemarksLiveData() {
        return remarksLiveData;
    }

    public void setRemarksLiveData(ObservableField<String> remarksLiveData) {
        this.remarksLiveData = remarksLiveData;
    }

    private boolean addDot()
    {
        boolean done = false;

        if (amountLiveData.get().length() == 0)
        {
            amountLiveData.set("0.");
            dotUsed = true;
            done = true;
        } else if (dotUsed == true)
        {
        } else if (defineLastCharacter(amountLiveData.get().charAt(amountLiveData.get().length() - 1) + "") == IS_OPERAND)
        {
            amountLiveData.set(amountLiveData.get() + "0.");
            done = true;
            dotUsed = true;
        } else if (defineLastCharacter(amountLiveData.get().charAt(amountLiveData.get().length() - 1) + "") == IS_NUMBER)
        {
            amountLiveData.set(amountLiveData.get() + ".");
            done = true;
            dotUsed = true;
        }
        return done;
    }

    private boolean addOperand(String operand)
    {
        boolean done = false;
        int operationLength = amountLiveData.get().length();
        if (operationLength > 0)
        {
            String lastInput = amountLiveData.get().charAt(operationLength - 1) + "";

            if ((lastInput.equals("+") || lastInput.equals("-") || lastInput.equals("*") || lastInput.equals("\u00F7") || lastInput.equals("%")))
            {
                amountDetailVisibilityLiveData.set(true);
                amountDetailLiveData.set("Wrong Format");
            } else if (operand.equals("%") && defineLastCharacter(lastInput) == IS_NUMBER)
            {
                amountLiveData.set(amountLiveData.get() + operand);
                dotUsed = false;
                equalClicked = false;
                lastExpression = "";
                done = true;
            } else if (!operand.equals("%"))
            {
                amountLiveData.set(amountLiveData.get() + operand);
                dotUsed = false;
                equalClicked = false;
                lastExpression = "";
                done = true;
            }
        } else
        {
            amountDetailVisibilityLiveData.set(true);

            amountDetailLiveData.set("Wrong Format. Operand Without any numbers?");
        }
        return done;
    }

    private boolean addNumber(String number)
    {
        boolean done = false;
        int operationLength = amountLiveData.get().length();
        if (operationLength > 0)
        {
            String lastCharacter = amountLiveData.get().charAt(operationLength - 1) + "";
            int lastCharacterState = defineLastCharacter(lastCharacter);

            if (operationLength == 1 && lastCharacterState == IS_NUMBER && lastCharacter.equals("0"))
            {
                amountLiveData.set(number);
                done = true;
            }  else if (lastCharacterState == IS_NUMBER || lastCharacterState == IS_OPERAND || lastCharacterState == IS_DOT)
            {
                amountLiveData.set(amountLiveData.get() + number);
                done = true;
            }
        } else
        {
            amountLiveData.set(amountLiveData.get() + number);
            done = true;
        }
        return done;
    }


    private void calculate(String input)
    {
        String result = "";
        try
        {
            String temp = input;
            if (equalClicked)
            {
                temp = input + lastExpression;
            } else
            {
                saveLastExpression(input);
            }
            result = scriptEngine.eval(temp.replaceAll("%", "/100").replaceAll("x", "*").replaceAll("[^\\x00-\\x7F]", "/")).toString();
            BigDecimal decimal = new BigDecimal(result);
            result = decimal.setScale(8, BigDecimal.ROUND_HALF_UP).toPlainString();
            equalClicked = true;

        } catch (Exception e)
        {
            amountDetailVisibilityLiveData.set(true);
            amountDetailLiveData.set("Wrong Format");
            return;
        }

        if (result.equals("Infinity"))
        {
            amountDetailVisibilityLiveData.set(true);

            amountDetailLiveData.set("Division by zero is not allowed");
            amountLiveData.set(input);

        }
        else if (result.contains("."))
        {
            amountDetailVisibilityLiveData.set(true);
            result = result.replaceAll("\\.?0*$", "");
            amountLiveData.set(result);
            amountDetailLiveData.set(amountDetailLiveData.get()+"\n"+input+" = "+result);
            finalAmount= Double.parseDouble(result);
            isCalculated=true;

//            amountResultLiveData.set(amountResultLiveData.get()+"\n"+result);


        }
    }

    private void saveLastExpression(String input)
    {
        String lastOfExpression = input.charAt(input.length() - 1) + "";
        if (input.length() > 1)
        {
            if (lastOfExpression.equals(")"))
            {
                lastExpression = ")";
                int numberOfCloseParenthesis = 1;

                for (int i = input.length() - 2; i >= 0; i--)
                {
                    if (numberOfCloseParenthesis > 0)
                    {
                        String last = input.charAt(i) + "";
                        if (last.equals(")"))
                        {
                            numberOfCloseParenthesis++;
                        } else if (last.equals("("))
                        {
                            numberOfCloseParenthesis--;
                        }
                        lastExpression = last + lastExpression;
                    } else if (defineLastCharacter(input.charAt(i) + "") == IS_OPERAND)
                    {
                        lastExpression = input.charAt(i) + lastExpression;
                        break;
                    } else
                    {
                        lastExpression = "";
                    }
                }
            } else if (defineLastCharacter(lastOfExpression + "") == IS_NUMBER)
            {
                lastExpression = lastOfExpression;
                for (int i = input.length() - 2; i >= 0; i--)
                {
                    String last = input.charAt(i) + "";
                    if (defineLastCharacter(last) == IS_NUMBER || defineLastCharacter(last) == IS_DOT)
                    {
                        lastExpression = last + lastExpression;
                    } else if (defineLastCharacter(last) == IS_OPERAND)
                    {
                        lastExpression = last + lastExpression;
                        break;
                    }
                    if (i == 0)
                    {
                        lastExpression = "";
                    }
                }
            }
        }
    }

    private int defineLastCharacter(String lastCharacter)
    {
        try
        {
            Integer.parseInt(lastCharacter);
            return IS_NUMBER;
        } catch (NumberFormatException e)
        {
        }

        if ((lastCharacter.equals("+") || lastCharacter.equals("-") || lastCharacter.equals("x") || lastCharacter.equals("\u00F7") || lastCharacter.equals("%")))
            return IS_OPERAND;


        if (lastCharacter.equals("."))
            return IS_DOT;

        return -1;
    }

    public void saveVoucher()
    {
        if (!isCalculated)
        {
           try
           {
               finalAmount= Double.parseDouble(amountLiveData.get());

           }
           catch (Exception e)
           {
               Log.e(TAG,e.toString());
               finalAmount=0.0;

           }

        }
        if (finalAmount>0)
        {
            if (date!=null && !date.isEmpty())
            {
                String businessId= SharedPreferenceHelper.getInstance(getApplication()).getBUSINESS_ID();
                String userId= SharedPreferenceHelper.getInstance(getApplication()).getUserID();
                Voucher voucher= new Voucher();
                voucher.setAction("INSERT");
                voucher.setVoucherType(voucherType);
                voucher.setPartyCode(partyCode);
                voucher.setVoucherAmount(finalAmount);
                voucher.setVoucherDate(date);
                voucher.setRefDoc(billImage);
                voucher.setBusinessId(businessId);
                voucher.setUserId(userId);
                voucher.setRemarks(remarksLiveData.get());
                repository.saveVoucher(voucher);
                getServerResponse();
                showProgressDialog.setValue(true);


            }
            else
            {
                toastMessage.setValue("Please Select Date");
            }
        }
        else
        {
            toastMessage.setValue("Enter Amount or Invalid Amount ");
        }


    }


    public void getServerResponse() {
        repository.setCallBackListener((object, key) -> {
            showProgressDialog.setValue(false);


            if (object != null) {
                if (key == SERVER_RESPONSE) {
                    ServerResponse serverResponse = (ServerResponse) object;
                    toastMessage.setValue(serverResponse.getMessage());
                    showProgressDialog.setValue(false);
                } else if (key == SERVER_ERROR) {
                    String error = (String) object;
                    showProgressDialog.setValue(false);

                    toastMessage.setValue(error);
                }
            }

        });

    }


}
