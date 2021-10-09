package OfflineQuotes.controller;

import OfflineQuotes.model.BranchIssuanceAnalysis;
import OfflineQuotes.services.BranchIssuanceAnalysisService;
import OfflineQuotes.services.ninjaRecordService;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.*;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import java.util.List;


@RestController
@RequestMapping("/api/tma/v1/offlineQuoteAnalysis/")
public class BranchIssuanceAnalysisController {

    @Autowired
    BranchIssuanceAnalysisService branchIssuanceAnalysisService;

    @GetMapping()
    public void main(String args[]) throws IOException, ParseException, UnirestException {
//        branchIssuanceAnalysisService.initiateService();
//        ninjaRecordService.getPolicyDetailRecords();
        branchIssuanceAnalysisService.premiumRequestUsingIDV();

    }

}