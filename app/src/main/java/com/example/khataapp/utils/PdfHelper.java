package com.example.khataapp.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.pdf.PdfDocument;

public class PdfHelper {

    private PdfDocument pdfDocument;

//    private void createPDF( ) {


//        pdfDocument = new PdfDocument();
//        int height = (productModelList.size() * 20) + 330 + 100 + 100;
//
//
//        String[] pdfColumnName = {"Customer Name :","Contact No. :", "Delivery Date :","Delivery Address", "Ledger Balance :", "Credit Limit :"};
//        Bitmap bitMapHeaderFooter, headerFooterScaleableBitMap, bitMapLogo, LogoScaledBitMap;
//
//        bitMapHeaderFooter = BitmapFactory.decodeResource(getResources(), R.drawable.pdf_header_footer);
//        bitMapLogo = BitmapFactory.decodeResource(getResources(), R.drawable.img_erp_cloud_logo);
//        headerFooterScaleableBitMap = Bitmap.createScaledBitmap(bitMapHeaderFooter, 350, 20, false);
//        LogoScaledBitMap = Bitmap.createScaledBitmap(bitMapLogo, 30, 30, false);
//
//
//        Paint paint = new Paint();
//
//        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(350, height, 1).create();
//
//        PdfDocument.Page page = pdfDocument.startPage(pageInfo);
//
//        Canvas canvas = page.getCanvas();
//
//        canvas.drawBitmap(headerFooterScaleableBitMap, 0, 0, paint);
//        canvas.drawBitmap(headerFooterScaleableBitMap, 0, pageInfo.getPageHeight() - 10, paint);
//        canvas.drawBitmap(LogoScaledBitMap, 300, pageInfo.getPageHeight() - 50, paint);
//
////        paint.setTextAlign(Paint.Align.LEFT);
////        paint.setTextSize(10f);
////        canvas.drawText("FFC",10,20,paint);
//
//        paint.setTextAlign(Paint.Align.CENTER);
//        paint.setTextSize(16f);
//        paint.setColor(Color.rgb(40, 106, 156));
//        paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
//        canvas.drawText("Sale Order", pageInfo.getPageWidth() / 2, 70, paint);
//
//        paint.setTextAlign(Paint.Align.LEFT);
//        paint.setTextSize(14);
//        paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
//        paint.setColor(Color.rgb(40, 106, 156));
//        canvas.drawText("Sale Order Detail", 10, 130, paint);
//
//        int startXPosition = 10;
//        int startYPosition = 160;
//        int endXPosition = pageInfo.getPageWidth() - 10;
//        paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
//        paint.setTextSize(12);
//
//
//        for (int i = 0; i < pdfColumnName.length; i++) {
//            paint.setColor(Color.rgb(40, 106, 156));
//            canvas.drawText(pdfColumnName[i], startXPosition, startYPosition, paint);
//            paint.setColor(Color.GRAY);
//            canvas.drawLine(startXPosition, startYPosition + 5, endXPosition, startYPosition + 5, paint);
//            paint.setColor(Color.BLACK);
//
//            if (i == 0) {
//                if (customerModel.getPartyName() != null) {
//                    canvas.drawText(customerModel.getPartyName(), 130, startYPosition, paint);
//
//                }
//
//            } else if (i == 1) {
//                if (customerModel.getContacts()!=null&&customerModel.getContacts().length()>=11)
//                {
//
//                    String contact= customerModel.getContacts().substring(0,11);
//                    canvas.drawText(contact, 130, startYPosition, paint);
//
//                }
//                else
//                {
//                    canvas.drawText("empty", 130, startYPosition, paint);
//
//                }
//
//            } else if (i == 2) {
//                canvas.drawText(saleOrderModel.getDelivery_Date(), 130, startYPosition, paint);
//
//            } else if (i == 3) {
//                if (deliveryAddress!=null && deliveryAddress.length()>0) {
//                    canvas.drawText(deliveryAddress, 130, startYPosition, paint);
//
//                } else {
//                    canvas.drawText("empty", 130, startYPosition, paint);
//
//                }
//
//            }else if (i == 4) {
//                if (saleOrderModel.getLedger_Balance() != 0) {
//                    canvas.drawText(String.valueOf(saleOrderModel.getLedger_Balance()), 130, startYPosition, paint);
//
//                } else {
//                    canvas.drawText("0", 130, startYPosition, paint);
//
//                }
//
//            } else  {
//                if (saleOrderModel.getCredit_Limit() != 0) {
//                    canvas.drawText(String.valueOf(saleOrderModel.getCredit_Limit()), 130, startYPosition, paint);
//
//                } else {
//                    canvas.drawText("0", 130, startYPosition, paint);
//
//                }
//
//            }
//            startYPosition += 20;
//        }
//
//        startYPosition += 20;
//
//        paint.setTextAlign(Paint.Align.LEFT);
//        paint.setTextSize(14);
//        paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
//        paint.setColor(Color.rgb(40, 106, 156));
//        canvas.drawText("Products", 10, startYPosition, paint);
//        startYPosition += 20;
//
//        paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
//        String[] productColumnName = {"Name", "Unit", "Quantity", "Rate", "Amount"};
//        int xPosition = 10;
//        for (int i = 0; i < 5; i++) {
//            paint.setTextSize(12);
//            paint.setColor(Color.rgb(40, 106, 156));
//            canvas.drawText(productColumnName[i], xPosition, startYPosition, paint);
//
//            xPosition += 66;
//
//        }
//
//        startYPosition += 20;
//
//
//        paint.setColor(Color.GRAY);
//        canvas.drawLine(10, startYPosition, pageInfo.getPageWidth() - 10, startYPosition, paint);
//        startYPosition += 30;
//
//        double totalAmount = 0, quantity = 0;
//
//        if (productModelList!=null)
//        {
//            if (productModelList.size()>0)
//            {
//                for (InsertProductModel model : productModelList) {
//
//                    totalAmount += model.getAmount();
//                    quantity += model.getQty();
//
//                    xPosition = 10;
//
//                    paint.setColor(Color.BLACK);
//
//                    for (int i = 0; i < 5; i++) {
//                        paint.setTextSize(10);
//                        if (i == 0) {
//                            if (model.getItHead().length() > 12) {
//                                canvas.drawText(model.getItHead().substring(0, 12), xPosition, startYPosition, paint);
//                            } else {
//                                canvas.drawText(model.getItHead(), xPosition, startYPosition, paint);
//
//                            }
//
//                        } else if (i == 1) {
//                            canvas.drawText(model.getUnit_Name(), xPosition + 10, startYPosition, paint);
//
//                        } else if (i == 2) {
//                            double quantity1 = model.getQty();
//                            if (quantity != 0) {
//                                canvas.drawText(String.valueOf(quantity1), xPosition, startYPosition, paint);
//
//                            } else {
//                                canvas.drawText("0", xPosition, startYPosition, paint);
//
//                            }
//
//                        } else if (i == 3) {
//                            double rate = model.getRate();
//                            if (rate != 0) {
//                                canvas.drawText(String.valueOf(rate), xPosition, startYPosition, paint);
//
//                            } else {
//                                canvas.drawText("0", xPosition, startYPosition, paint);
//
//                            }
//
//                        } else {
//                            double amount = model.getAmount();
//                            if (amount != 0) {
//                                canvas.drawText(String.valueOf(amount), xPosition, startYPosition, paint);
//
//                            } else {
//                                canvas.drawText("0", xPosition, startYPosition, paint);
//
//                            }
//                        }
//
//                        xPosition += 66;
//
//                    }
//                    paint.setColor(Color.GRAY);
//                    canvas.drawLine(10, startYPosition + 5, pageInfo.getPageWidth() - 10, startYPosition + 5, paint);
//                    startYPosition += 20;
//
//
//                }
//                paint.setColor(Color.BLACK);
//                startYPosition += 10;
//                xPosition = 180;
//
//                canvas.drawText("Total Quantity: ", xPosition, startYPosition, paint);
//
//                canvas.drawText(String.valueOf(quantity), xPosition + 90, startYPosition, paint);
//                paint.setColor(Color.GRAY);
//
//                canvas.drawLine(10, startYPosition + 5, pageInfo.getPageWidth() - 10, startYPosition + 5, paint);
//                startYPosition += 15;
//                paint.setColor(Color.BLACK);
//                canvas.drawText("Total Amount: ", xPosition, startYPosition, paint);
//
//                canvas.drawText(String.valueOf(totalAmount), xPosition + 90, startYPosition, paint);
//
//            }
//        }
//
//
//
//
//        pdfDocument.finishPage(page);
//        String stringFilePath;
////        if (android.os.Build.VERSION.SDK_INT== Build.VERSION_CODES.Q) {
////            stringFilePath = Environment.getExternalStorageDirectory().getPath() + "/Download/SaleOrder.pdf";
////
////        }
////        else
////        {
////            stringFilePath = Environment.getExternalStorageDirectory().getPath() + "/Download/SaleOrder.pdf";
////
////        }
//
//        int numbers = new Random().nextInt(Integer.MAX_VALUE);
//        stringFilePath = Environment.getExternalStorageDirectory().getPath() + "/Download/"+"SaleOrder"+numbers+".pdf";
//        Date now = new Date();
//        DateFormat.format("hh:mm:ss", now);
//
//
//        file = new File(stringFilePath);
//        savePdf(file);
//
//
//        showPdfDialog(height);
//    }
}
