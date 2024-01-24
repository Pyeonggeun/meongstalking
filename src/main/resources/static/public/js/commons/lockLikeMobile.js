window.addEventListener("DOMContentLoaded", () => {

    if (/Mobi|Android/i.test(navigator.userAgent)) {
        // 모바일인 경우
        return;
    }

    //설정 
    const fixedWidthRatio = 9;
    const fixedHeightRatio = 16;

    const commonRatio = 3.3;

    //alert("PC 닷");


    //박스 생성...
    const mobileWrapper = document.createElement("div");

    mobileWrapper.style.width = fixedWidthRatio * commonRatio + "em" ;
    mobileWrapper.style.height = fixedHeightRatio * commonRatio + "em" ;
    mobileWrapper.style.margin = "2em auto";
    mobileWrapper.style.overflow = "auto";

    document.body.style.backgroundColor = "#EEEEEE";


    // 전체 옮기기
    while(document.body.childNodes.length != 0){
        mobileWrapper.appendChild(document.body.childNodes[0]);
    }

    // 예외 사항 처리

    // fixed
    // modal = offcanvas = fixed

    // 해결 방안?
    // 만약 fixed가 되어 있으면...크기를 강제로.. 위에 크기로...

    document.body.appendChild(mobileWrapper);

});