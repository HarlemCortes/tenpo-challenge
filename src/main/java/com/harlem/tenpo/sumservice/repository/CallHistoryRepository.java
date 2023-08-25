package com.harlem.tenpo.sumservice.repository;

import com.harlem.tenpo.sumservice.model.CallHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CallHistoryRepository extends JpaRepository<CallHistory, Long> {

}
