package com.refanzzzz.tokonyadia.service;

import com.refanzzzz.tokonyadia.entitiy.Store;
import com.refanzzzz.tokonyadia.entitiy.TransactionDetail;

import java.util.List;

public interface TransactionDetailService {

    List<TransactionDetail> getAllTransactionDetail();

    TransactionDetail getTransactionDetailById(String id);

    TransactionDetail addTransactionDetail(TransactionDetail transactionDetail);

    TransactionDetail updateStore(String id, TransactionDetail transactionDetail);

    String deleteTransactionDetailById(String id);

}
