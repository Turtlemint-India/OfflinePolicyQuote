package OfflineQuotes.dao;

import OfflineQuotes.model.BranchIssuanceAnalysis;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface BranchIssuanceAnalysisDao extends MongoRepository<BranchIssuanceAnalysis, String> {
    Optional<BranchIssuanceAnalysis> findOneByMisID(String misID);
    Optional<BranchIssuanceAnalysis> findOneByStpRequestId(String stpRequestId);
}
