package OfflineQuotes.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.json.simple.JSONObject;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;


@Data
@Document("BranchIssuanceAnalysis")
public class BranchIssuanceAnalysis {
    @Id
    public String id;

    String misID;
    String vertical;
    private String insurer;
    private String misRequestID;
    private String misPrice;
    private DataStatus dataStatus;
    private String missingData;
    private String stpRequestId;
    private ResultStatus resultStatus;
    private String totalResults;
    private PriceComparisionStatus priceComparisionStatus;
    private String proposalStatus;
    private JSONObject policyDetailRecord;
    private JSONObject premiumRequestResponse;
    private JSONObject premiumResultResponse;
    private String errorMsg;
    RecordStatus recordStatus;
    private String tmMakeModelId;
    private String misPolicyIssuanceDate;
    private JSONObject onlineQuotes;


    public enum DataStatus {
        COMPLETE,
        INSUFFICIENT
    };

    public  enum ResultStatus {
        SUCCESS,
        FAILURE
    };

    public enum PriceComparisionStatus {
        MATCHING,
        MATCHING_WITH_THRESHOLD,
        MISMATCH
    };

    public enum RecordStatus {
        NOT_STARTED,
        EXCEPTION_DURING_NOT_STARTED,
        DATA_VERIFIED,
        DATA_VERIFICATION_FAILED,
        EXCEPTION_DURING_DATA_VERIFICATION,
        EXCEPTION_DURING_PREMIUM_REQUEST,
        PREMIUM_REQUEST_IS_0,
        PREMIUM_REQUEST_INITIATED,
        PREMIUM_RESULT_INITIATED,
        IDV_REQUEST_INITIATED,
        IDV_RESULT_INITIATED,
        PRICE_COMPARED,
        FINAL_RESULT_GENERATED
    };

    @JsonIgnore
    public String getEntityId() {
        return id;
    }

    public String getVertical() {
        return vertical;
    }

    public void setVertical(String vertical) {
        this.vertical = vertical;
    }

    public String getInsurer() { return insurer; }

    public void setInsurer(String insurer) { this.insurer = insurer; }

    public String getMisID() {
        return misID;
    }

    public void setMisID(String misID) {
        this.misID = misID;
    }

    public String getMisRequestID() {
        return misRequestID;
    }

    public void setMisRequestID(String misRequestID) {
        this.misRequestID = misRequestID;
    }

    public String getMisPrice() {
        return misPrice;
    }

    public void setMisPrice(String misPrice) {
        this.misPrice = misPrice;
    }

    public DataStatus getDataStatus() {
        return dataStatus;
    }

    public void setDataStatus(DataStatus dataStatus) {
        this.dataStatus = dataStatus;
    }

    public String getMissingData() {
        return missingData;
    }

    public void setMissingData(String missingData) {
        this.missingData = missingData;
    }

    public String getStpRequestId() {
        return stpRequestId;
    }

    public void setStpRequestId(String stpRequestId) {
        this.stpRequestId = stpRequestId;
    }

    public ResultStatus getResultStatus() {
        return resultStatus;
    }

    public void setResultStatus(ResultStatus resultStatus) {
        this.resultStatus = resultStatus;
    }

    public String getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(String totalResults) {
        this.totalResults = totalResults;
    }

    public PriceComparisionStatus getPriceComparisionStatus() {
        return priceComparisionStatus;
    }

    public void setPriceComparisionStatus(PriceComparisionStatus priceComparisionStatus) {
        this.priceComparisionStatus = priceComparisionStatus;
    }

    public String getProposalStatus() {
        return proposalStatus;
    }

    public void setProposalStatus(String proposalStatus) {
        this.proposalStatus = proposalStatus;
    }

    public JSONObject getPolicyDetailRecord() {
        return policyDetailRecord;
    }

    public void setPolicyDetailRecord(JSONObject policyDetailRecord) {
        this.policyDetailRecord = policyDetailRecord;
    }

    public RecordStatus getRecordStatus() {
        return recordStatus;
    }

    public void setRecordStatus(RecordStatus recordStatus) {
        this.recordStatus = recordStatus;
    }

    public JSONObject getPremiumRequestResponse() {
        return premiumRequestResponse;
    }

    public void setPremiumRequestResponse(JSONObject premiumRequestResponse) {
        this.premiumRequestResponse = premiumRequestResponse;
    }

    public JSONObject getPremiumResultResponse() {
        return premiumResultResponse;
    }

    public void setPremiumResultResponse(JSONObject premiumResultResponse) {
        this.premiumResultResponse = premiumResultResponse;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public String getTmMakeModelId() {
        return tmMakeModelId;
    }

    public void setTmMakeModelId(String tmMakeModelId) {
        this.tmMakeModelId = tmMakeModelId;
    }



    public String getMisPolicyIssuanceDate() {
        return misPolicyIssuanceDate;
    }

    public void setMisPolicyIssuanceDate(String misPolicyIssuanceDate) {
        this.misPolicyIssuanceDate = misPolicyIssuanceDate;
    }

    public JSONObject getOnlineQuotes() {
        return onlineQuotes;
    }

    public void setOnlineQuotes(JSONObject onlineQuotes) {
        this.onlineQuotes = onlineQuotes;
    }
}
