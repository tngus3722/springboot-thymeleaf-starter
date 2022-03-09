$(document).ready(function () {
    console.log("hello world");
    $(".product-price-button").click(function () {
        appendProductPrice($(this));
    })
})

function appendProductPrice($button) {
    $(".product-price-button").find("p").remove(); // 버튼이 클릭된 경우 p태그를 활성화된 버튼을 모두 지운다.
    let price = $button.data("price");
    if (!$button.hasClass("button--active")) {
        $(".product-price-button").removeClass("button--active");
        let text = "<p>버튼의 가격은 " + price +"</p>";
        $button.append(text);
    }
    $button.toggleClass("button--active");
}