
package com.example.moengage_newapp.purchase;


//
//@SuppressWarnings("unused")
//public class PurchaseDetail {
//
//    @Expose
//    private Object bill;
//    @SerializedName("FarmerVariety")
//    private FarmerVariety farmerVariety;
//    @Expose
//    private Long farmerVarietyId;
//    @Expose
//    private Object iForm;
//    @Expose
//    private Long id;
//    @Expose
//    private Long isPurchased;
//    @Expose
//    private Object jFormDate;
//    @Expose
//    private Object jFormName;
//    @Expose
//    private Object jFormNumber;
//    @Expose
//    private Object jFormQuantity;
//    @SerializedName("KacchaAarthi")
//    private KacchaAarthi kacchaAarthi;
//    @Expose
//    private Long kacchaAarthiId;
//    @SerializedName("PakkaAarthi")
//    private PakkaAarthi pakkaAarthi;
//    @Expose
//    private Long pakkaAarthiId;
//    @Expose
//    private Object po;
//    @Expose
//    private Object purchaseDenialReason;
//    @SerializedName("PurchaseDetail")
//    private PurchaseDetail purchaseDetail;
//    @Expose
//    private Object reason;
//    @Expose
//    private String slipDate;
//    @Expose
//    private Long slipNumber;
//    @Expose
//    private Long slipQuantity;
//
//    public Object getBill() {
//        return bill;
//    }
//
//    public void setBill(Object bill) {
//        this.bill = bill;
//    }
//
//    public FarmerVariety getFarmerVariety() {
//        return farmerVariety;
//    }
//
//    public void setFarmerVariety(FarmerVariety farmerVariety) {
//        this.farmerVariety = farmerVariety;
//    }
//
//    public Long getFarmerVarietyId() {
//        return farmerVarietyId;
//    }
//
//    public void setFarmerVarietyId(Long farmerVarietyId) {
//        this.farmerVarietyId = farmerVarietyId;
//    }
//
//    public Object getIForm() {
//        return iForm;
//    }
//
//    public void setIForm(Object iForm) {
//        this.iForm = iForm;
//    }
//
//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public Long getIsPurchased() {
//        return isPurchased;
//    }
//
//    public void setIsPurchased(Long isPurchased) {
//        this.isPurchased = isPurchased;
//    }
//
//    public Object getJFormDate() {
//        return jFormDate;
//    }
//
//    public void setJFormDate(Object jFormDate) {
//        this.jFormDate = jFormDate;
//    }
//
//    public Object getJFormName() {
//        return jFormName;
//    }
//
//    public void setJFormName(Object jFormName) {
//        this.jFormName = jFormName;
//    }
//
//    public Object getJFormNumber() {
//        return jFormNumber;
//    }
//
//    public void setJFormNumber(Object jFormNumber) {
//        this.jFormNumber = jFormNumber;
//    }
//
//    public Object getJFormQuantity() {
//        return jFormQuantity;
//    }
//
//    public void setJFormQuantity(Object jFormQuantity) {
//        this.jFormQuantity = jFormQuantity;
//    }
//
//    public KacchaAarthi getKacchaAarthi() {
//        return kacchaAarthi;
//    }
//
//    public void setKacchaAarthi(KacchaAarthi kacchaAarthi) {
//        this.kacchaAarthi = kacchaAarthi;
//    }
//
//    public Long getKacchaAarthiId() {
//        return kacchaAarthiId;
//    }
//
//    public void setKacchaAarthiId(Long kacchaAarthiId) {
//        this.kacchaAarthiId = kacchaAarthiId;
//    }
//
//    public PakkaAarthi getPakkaAarthi() {
//        return pakkaAarthi;
//    }
//
//    public void setPakkaAarthi(PakkaAarthi pakkaAarthi) {
//        this.pakkaAarthi = pakkaAarthi;
//    }
//
//    public Long getPakkaAarthiId() {
//        return pakkaAarthiId;
//    }
//
//    public void setPakkaAarthiId(Long pakkaAarthiId) {
//        this.pakkaAarthiId = pakkaAarthiId;
//    }
//
//    public Object getPo() {
//        return po;
//    }
//
//    public void setPo(Object po) {
//        this.po = po;
//    }
//
//    public Object getPurchaseDenialReason() {
//        return purchaseDenialReason;
//    }
//
//    public void setPurchaseDenialReason(Object purchaseDenialReason) {
//        this.purchaseDenialReason = purchaseDenialReason;
//    }
//
//    public PurchaseDetail getPurchaseDetail() {
//        return purchaseDetail;
//    }
//
//    public void setPurchaseDetail(PurchaseDetail purchaseDetail) {
//        this.purchaseDetail = purchaseDetail;
//    }
//
//    public Object getReason() {
//        return reason;
//    }
//
//    public void setReason(Object reason) {
//        this.reason = reason;
//    }
//
//    public String getSlipDate() {
//        return slipDate;
//    }
//
//    public void setSlipDate(String slipDate) {
//        this.slipDate = slipDate;
//    }
//
//    public Long getSlipNumber() {
//        return slipNumber;
//    }
//
//    public void setSlipNumber(Long slipNumber) {
//        this.slipNumber = slipNumber;
//    }
//
//    public Long getSlipQuantity() {
//        return slipQuantity;
//    }
//
//    public void setSlipQuantity(Long slipQuantity) {
//        this.slipQuantity = slipQuantity;
//    }
//
//}

public class PurchaseDetail {

    private String id;

    public PurchaseDetail(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}