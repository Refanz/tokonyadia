package com.refanzzzz.tokonyadia.constant;

public class Constant {
    public static final String TRANSACTION_API = "/api/transaction";
    public static final String PRODUCT_API = "/api/product";
    public static final String CUSTOMER_API = "/api/customer";
    public static final String STORE_API = "/api/store";
    public static final String TRANSACTION_DETAIL_API = "/api/transaction-detail";
    public static final String USER_ACCOUNT_API = "/api/user-account";
    public static final String AUTH_API = "/api/auth";

    public static final String LOGIN_API = AUTH_API + "/login";


    public static final String SUCCESS_GET_ALL_CUSTOMER = "Successfully get all customer";
    public static final String SUCCESS_GET_CUSTOMER = "Successfully get customer by id";
    public static final String SUCCESS_DELETE_CUSTOMER = "Successfully delete customer";
    public static final String SUCCESS_UPDATE_CUSTOMER = "Successfully update customer";
    public static final String SUCCESS_CREATE_CUSTOMER = "Successfully create customer";
    public static final String ERROR_GET_CUSTOMER = "Customer is not found!";

    public static final String ERROR_USERNAME_DUPLICATE = "Username has been registered!";
    public static final String MERCHANT_API = "/api/merchant";
}
