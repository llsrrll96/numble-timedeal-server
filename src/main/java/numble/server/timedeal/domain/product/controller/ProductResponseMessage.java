package numble.server.timedeal.domain.product.controller;

public enum ProductResponseMessage {
    SAVING_PRODUCT_SUCCESS("상품등록성공"),
    SAVING_PRODUCT_FAILED("상품등록실패"),
    UPDATING_PRODUCT_SUCCESS("상품수정성공"),
    UPDATING_PRODUCT_FAILED("상품등록실패"),
    DELETING_PRODUCT_SUCCESS("상품삭제성공"),
    DELETING_PRODUCT_FAILED("상품삭제실패"),
    FINDING_PRODUCTS_SUCCESS("상품리스트조회"),
    FINDING_PRODUCT_SUCCESS("상품조회"),
    SAVING_CATEGORY_SUCCESS("카테고리 등록"),
    DELETING_CATEGORY_SUCCESS("카테고리 삭제 성공"),
    DELETING_CATEGORY_FAILED("카테고리 삭제 실패")
    ;

    private final String respDesc;

    ProductResponseMessage(String respDesc) {
        this.respDesc = respDesc;
    }

    public String getRespDesc(){
        return respDesc;
    }
}
